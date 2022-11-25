package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Car {
    private String name;
    @JsonProperty("car_number")
    private String carNumber;
    @JsonProperty("TYPE")
    private String type;

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Car() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Car(String name, String carNumber, String type) {
        this.name = name;
        this.carNumber = carNumber;
        this.type = type;
    }
}
