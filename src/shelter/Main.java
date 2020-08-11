package shelter;

class Main {
    public static void main(String[] args) {

        Plate plate = new Plate();

        Cat[] cats = new Cat[] {
                new Cat("Барсик"),
                new Cat("Мурзик"),
                new Cat("Рыжик"),
                new Cat("Бандит"),
                new Cat("Василий"),
                new Cat("Марсик"),
                new Cat("Маркиз"),
                new Cat("Сеня"),
                new Cat("Жиробасик"),
                new Cat("Кот 0110100111001")
        };

        for (int i = 0; i < cats.length; i++) {
            cats[i].eat(plate);
            System.out.println(plate);
        }

        System.out.println("Коты и их сытость:");
        for (Cat cat: cats) {
            System.out.println(cat);
        }
    }
}
