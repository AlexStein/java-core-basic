package zoo.animals;

public class Dog extends Animal {

    static int dogCounter = 0;

    public Dog(String name) {
        super(name);
        dogCounter++;

        maxRunDistance = 500;
        maxSwimDistance = 10;
    }
}
