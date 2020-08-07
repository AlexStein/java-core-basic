package zoo;

import zoo.animals.*;

/**
 * Класс для трестирование объектов к ДЗ №6 Продвинутое ООП.
 */
public class Main {

    public static void main(String[] args) {
        Cat cat1 = new Cat("Барсик");
        Cat cat2 = new Cat("Мурзик");

        Dog dog1 = new Dog("Полкан");
        Dog dog2 = new Dog("Шарик");
        Dog dog3 = new Dog("Тузик");
        Dog dog4 = new Dog("Мухтар");

        dog2.run(200);
        cat1.run(100);

        dog1.run(1000);
        dog3.swim(100);
        cat2.swim(10000);

        dog2.swim(5);
        dog4.run(0);

        Animal.printObjectCounter();
    }
}
