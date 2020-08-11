package zoo.animals;

public class Animal {

    static int animalCounter = 0;

    String name;
    protected int maxSwimDistance;
    protected int maxRunDistance;

    Animal(String name) {
        animalCounter++;

        this.name = name;
        maxRunDistance = 0;
        maxSwimDistance = 0;
    }

    /**
     * Вывод счетсчика созданных объектов
     */
    public static void printObjectCounter() {
        System.out.printf("Создано %d объектов класса Animal, %d Cat, %d Dog",
                animalCounter, Cat.catCounter, Dog.dogCounter);
    }

    public void run(int distance) {
        if (distance <= 0) {
            System.out.printf("%s никуда не побежал\n", name);
            return;
        }
        String tired = "";
        if (distance > maxRunDistance) {
            tired = " и устал";
        }

        System.out.printf("%s пробежал %dм%s.\n", name, Math.min(distance, maxRunDistance), tired);
    }

    public void swim(int distance) {
        if (distance <= 0) {
            System.out.printf("%s никуда не поплыл\n", name);
            return;
        }
        String drown = "";
        if (distance > maxSwimDistance) {
            drown = " и утонул";
        }

        System.out.printf("%s проплыл %dм%s.\n", name, Math.min(distance, maxSwimDistance), drown);
    }
}
