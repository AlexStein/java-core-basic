package zoo.animals;

public class Dog extends Animal {

    public Dog(String name) {
        super(name);
        dogCounter++;

        maxRunDistance = 500;
        maxSwimDistance = 10;
    }
}
