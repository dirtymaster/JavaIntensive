package edu.school21.reflection.classes;

import java.util.StringJoiner;

public class Car {
    private String brand;
    private int price;
    private int maxSpeed;

    public Car() {
        this.brand = "Default brand";
        this.price = 0;
        this.maxSpeed = 0;
    }

    public Car(String brand, int price, int maxSpeed) {
        this.brand = brand;
        this.price = price;
        this.maxSpeed = maxSpeed;
    }

    public int increasePrice(int value) {
        this.price += value;
        return price;
    }

    @Override
    public String toString() {
        return new StringJoiner(
                ", ", Car.class.getSimpleName() + "[", "]")
                .add("brand='" + brand + "'")
                .add("price=" + price)
                .add("maxSpeed=" + maxSpeed)
                .toString();
    }
}
