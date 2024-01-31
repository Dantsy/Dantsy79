package hw3;

public class Main {

    public static void main(String[] args) {
        // Создаем коробки с яблоками и апельсинами
        Box<Apple> appleBox = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        // Добавляем фрукты в коробки
        appleBox.addFruit(new Apple());
        appleBox.addFruit(new Apple());
        orangeBox.addFruit(new Orange());
        orangeBox.addFruit(new Orange());

        // Проверяем вес коробок
        System.out.println("Вес яблочной коробки: " + appleBox.weight());
        System.out.println("Вес апельсиновой коробки: " + orangeBox.weight());

        // Проверяем метод compare
        System.out.println("Вес коробок одинаковый? " + appleBox.compare(orangeBox));

        // Создаем еще одну коробку для яблок
        Box<Apple> anotherAppleBox = new Box<>();

        // Перемещаем фрукты из одной коробки в другую
        appleBox.transfer(anotherAppleBox);

        // Проверяем, что фрукты переместились
        System.out.println("Вес яблочной коробки перед перемещением: " + appleBox.weight());
        System.out.println("Вес яблочной коробки после перемещения: " + anotherAppleBox.weight());

        // Перемещаем яблоки в коробку с апельсинами
        //appleBox.transfer(orangeBox); // Выводит сообщение об ошибке
    }
}