package dto;

import java.util.List;

public class User {
    private String name;
    private int age;
    private List<Car> cars;

    public User(String name, int age, List<Car> cars) {
        this.name = name;
        this.age = age;
        this.cars = cars;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", cars=" + cars +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<Car> getCars() {
        return cars;
    }
}
