package shelter;

public class Plate {

    private final int capacity;
    private int amount;

    public Plate() {
        this.capacity = (int)Math.round(Math.random() * 5) + 50;
        refill();
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("В тарелке %d еды", amount);
    }

    public void refill() {
        this.amount = this.capacity;
    }

    public void decreaseFood(int amount) {
        this.amount -= amount;
    }
}
