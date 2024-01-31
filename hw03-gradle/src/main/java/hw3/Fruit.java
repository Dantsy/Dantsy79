package hw3;

import java.util.ArrayList;
import java.util.List;

class Fruit {
    double getWeight() {
        return 0.0;
    }
}

class Apple extends Fruit {
    @Override
    double getWeight() {
        return 0.6;
    }
}

class Orange extends Fruit {
    @Override
    double getWeight() {
        return 0.9;
    }
}

class Box<T extends Fruit> {
    private List<T> fruits;

    public Box() {
        fruits = new ArrayList<>();
    }

    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    public double weight() {
        return fruits.stream().mapToDouble(Fruit::getWeight).sum();
    }

    public boolean compare(Box<?> anotherBox) {
        return Math.abs(this.weight() - anotherBox.weight()) < 0.0001;
    }

    public void transfer(Box<? super T> anotherBox) {
        if (anotherBox instanceof Box<?>) {
            for (T fruit : this.fruits) {
                try {
                    anotherBox.addFruit(fruit);
                } catch (Exception e) {
                    System.out.println("Нельзя пересыпать фрукты в коробку с другим типом фруктов");
                    return;
                }
            }
            this.fruits.clear();
        } else {
            System.out.println("Нельзя пересыпать фрукты в коробку с другим типом фруктов");
        }
    }
}