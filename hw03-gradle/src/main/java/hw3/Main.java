package hw3;

public class Main {

    public static void main(String[] args) {
        Box<Apple> appleBox = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        appleBox.addFruit(new Apple());
        appleBox.addFruit(new Apple());
        orangeBox.addFruit(new Orange());
        orangeBox.addFruit(new Orange());

        System.out.println("Вес яблочной коробки: " + appleBox.weight());
        System.out.println("Вес апельсиновой коробки: " + orangeBox.weight());

        System.out.println("Вес коробок одинаковый? " + appleBox.compare(orangeBox));

        Box<Apple> anotherAppleBox = new Box<>();

        appleBox.transfer(anotherAppleBox);

        System.out.println("Вес яблочной коробки перед перемещением: " + appleBox.weight());
        System.out.println("Вес яблочной коробки после перемещения: " + anotherAppleBox.weight());

    }
}