package zoo.animals;

public class Cat extends Animal {

    public Cat(String name) {
        super(name);
        catCounter++;
        maxRunDistance = 200;
    }

    @Override
    public void swim(int distance) {
        System.out.printf("Коты не плавают (%s (c)).\n", name);
    }
}
