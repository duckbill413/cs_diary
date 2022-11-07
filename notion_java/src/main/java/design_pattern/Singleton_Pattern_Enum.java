package design_pattern;

enum Singleton_Enum{
    INSTANCE;
    int value;

    public int getValue(){
        return value;
    }
    public void setValue(int value){
        this.value = value;
    }
}

public class Singleton_Pattern_Enum {
    public static void main(String[] args) {
        Singleton_Enum singleton = Singleton_Enum.INSTANCE;

        System.out.println(singleton.getValue());
        singleton.setValue(2);
        System.out.println(singleton.getValue());

        Singleton_Enum singleton2 = Singleton_Enum.INSTANCE;
        System.out.println(singleton2.getValue());
    }
}

/**
 * Enum 타입은 기본적으로 직렬화가 가능하므로 Serializable 인터페이스를 구현할 필요가 없고,
 * 리플렉션 문제도 발생하지 않는다.
 * 인스턴스가 JVM 내에 하나만 존재하는 것이 보장 되므로, Java에서 싱글톤 패턴을 만드는 가장 좋은 방법으로 권장된다.
 */