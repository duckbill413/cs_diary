package design_pattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** 정책 패턴(Policy Pattern)
 * 전략 패턴은 정책 패턴이라고도 하며, 객체의 행위를 바꾸고 싶은 경우 '직접' 수정하지 않고 전략이라고 부르는 '캡슐화한 알고리즘'을
 * 컨텍스트 안에서 바꿔주면서 상호 교체가 가능하게 만드는 패턴입니다.
 */

interface PaymentStrategy{
    public void pay(int amount);
}

class KAKAOCardStrategy implements PaymentStrategy{
    private String name;
    private String cardNumber;
    private String cvc;
    private String dateOfExpire;

    public KAKAOCardStrategy(String name, String cardNumber, String cvc, String dateOfExpire) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.dateOfExpire = dateOfExpire;
    }

    @Override
    public void pay(int amount) {
        System.out.println(amount + " paid using KAKAOCard");
    }
}

class LUNACardStrategy implements PaymentStrategy{
    private String emailId;
    private String password;

    public LUNACardStrategy(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    @Override
    public void pay(int amount) {
        System.out.println(amount + " paid using LUNACard");
    }
}

class Item{
    private String name;
    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

class ShoppingCart {
    List<Item> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }
    public void addItem(Item item){
        this.items.add(item);
    }
    public void removeItem(Item item){
        this.items.remove(item);
    }
    public int calculateTotal(){
        int sum = 0;
        for (Item item : items){
            sum += item.getPrice();
        }
        return sum;
    }
    public void pay(PaymentStrategy paymentStrategy){
        int amount = calculateTotal();
        paymentStrategy.pay(amount);
    }

}
public class Strategy_Pattern {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        Item apple = new Item("apple", 1000);
        Item banana = new Item("banana", 500);

        cart.addItem(apple);
        cart.addItem(banana);

        // pay by LUNA
        cart.pay(new LUNACardStrategy("user@gmail.com", "password"));
        // pay by KAKAO
        cart.pay(new KAKAOCardStrategy("ju hongchul", "123456789", "123", "12/01"));

    }
}
