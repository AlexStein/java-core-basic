public class Main {

    /* 1. Создать пустой проект в IntelliJ IDEA и прописать метод main(). */
    public static void main(String[] args) {

        /* 2. Создать переменные всех пройденных типов данных и
         * инициализировать их значения.
         */

        // Целые
        byte b = 8;
        short sh = 256;
        int i = 1000;
        long l = 1000L;

        // Булево
        boolean bool = false;

        // Дробные
        float f = 12.34f;
        double d = 123.45;

        // Символ
        char c = 'z';

        // Проверка методов

        // 3.
        float result = calculateExpression(10.1f, 2f, 5.6f, 3.43f);
        System.out.printf("3. a * (b + (c / d)) = %.4f\n\n", result);

        // 4.
        int n1 = 8;
        int n2 = 5;
        boolean isInbound = isSumInbound(n1, n2);
        System.out.printf("4. %d + %d isInbound = %b\n\n", n1, n2, isInbound);

        // 5.
        printNumberIsPositive(20);
        printNumberIsPositive(-10);

        // 6.
        int n = -1;
        boolean isNegative = isNumberNegative(n);
        System.out.printf("6а. %d isNegative = %b\n\n", n, isNegative);
        n = 55;
        isNegative = isNumberNegative(n);
        System.out.printf("6b. %d isNegative = %b\n\n", n, isNegative);

        // 7.
        System.out.print("7. ");
        helloName("Твоё имя");

        // 8.
        System.out.print("8. ");
        isLeap(1900);
        isLeap(1980);
        isLeap(2000);
        isLeap(2021);

    }

    /* 3. Написать метод вычисляющий выражение a * (b + (c / d))
     *    и возвращающий результат, где a, b, c, d – аргументы этого метода,
     *    имеющие тип float.
     */
    public static float calculateExpression(float a, float b, float c, float d) {
        float result = 0.0f;

        if (d != 0) {
            result = a * (b + (c / d));
        }

        return result;
    }

    /* 4. Написать метод, принимающий на вход два целых числа и проверяющий,
     *    что их сумма лежит в пределах от 10 до 20 (включительно),
     *    если да – вернуть true, в противном случае – false.
     */
    public static boolean isSumInbound(int n1, int n2) {
        int sum = n1 + n2;

        return (10 <= sum && sum <= 20);
    }


    /* 5. Написать метод, которому в качестве параметра передается целое число,
     *    метод должен напечатать в консоль, положительное ли число передали или отрицательное.
     *    Замечание: ноль считаем положительным числом.
     */
    public static void printNumberIsPositive(int num) {
        String res = "отрицательное";

        if (num >= 0) {
            res = "положительное";
        }

        System.out.printf("5. Число %d %s\n\n", num, res);
    }


    /* 6. Написать метод, которому в качестве параметра передается целое число.
     *    Метод должен вернуть true, если число отрицательное,
     *    и вернуть false если положительное.
     */
    public static boolean isNumberNegative(int num) {
        return (num < 0);
    }


    /* 7. Написать метод, которому в качестве параметра передается строка,
     *    обозначающая имя. Метод должен вывести в консоль сообщение «Привет, указанное_имя!».
     */
    public static void helloName(String name) {
        System.out.printf("Hello, %s!\n\n", name);
    }


    /* 8*. Написать метод, который определяет, является ли год високосным, и выводит сообщение в консоль.
     *     Каждый 4-й год является високосным, кроме каждого 100-го, при этом каждый 400-й – високосный.
     */
    public static void isLeap(int year) {
        String res = "Високосный";
        if ((year % 4 > 0) || ((year % 400 > 0) && (year % 100 == 0))) {
            res = "Невисокосный";
        }
        System.out.printf("Год %d %s\n", year, res);

    }
}
