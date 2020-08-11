package shelter;

public class Cat {

    /** Имя */
    private String name;
    /** Аппетит */
    private int appetite;
    /** Признак сытости */
    private boolean isFull;

    public Cat(String name) {
        this.name = name;
        this.appetite = (int)Math.round(Math.random() * 10) + 5;
        this.isFull = false;
    }

    public boolean isFull() {
        return isFull;
    }

    @Override
    public String toString() {
        return String.format("Кот %s %s", name, isFull() ? "сыт" : "голоден");
    }

    public void eat(Plate plate) {
        if (plate.getAmount() < this.appetite) {
            System.out.println(name + " остался голодным");
            return;
        }

        plate.decreaseFood(this.appetite);
        this.isFull = true;

        System.out.println(name + " поел");
    }
}
