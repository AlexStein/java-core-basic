import java.util.Arrays;
import java.util.Random;

public class ArraysAndLoops {
    public static void main(String[] args) {

        Random rand = new Random();

        // 1
        System.out.println("Задание 1. Тест.");
        //int[] arr1 = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        int[] arr1 = new int[rand.nextInt(5) + 5];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = rand.nextInt(2);  // 0 или 1
        }
        printArray(arr1);
        invertArray(arr1);
        printArray(arr1);

        // 2
        System.out.println("\nЗадание 2. Тест.");
        int[] arr2 = new int[8];
        printArray(arr2);
        fillArray(arr2);
        printArray(arr2);

        // 3
        System.out.println("\nЗадание 3. Тест.");
        int[] arr3 = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        printArray(arr3);
        multiplyArray(arr3);
        printArray(arr3);

        // 4
        System.out.println("\nЗадание 4. Тест.");
        int[][] arr4 = new int[6][6];
        fillDiagonalsForArray(arr4);
        print2DArray(arr4);

        // 5
        int[] arr5 = new int[rand.nextInt(5) + 5];
        for (int i = 0; i < arr5.length; i++) {
            arr5[i] = rand.nextInt(100) - 50;
        }

        System.out.println("\nЗадание 5. Тест.");
        printArray(arr5);

        int min = minForArray(arr5);
        int max = maxForArray(arr5);

        System.out.printf("Минимальное занчение = %d\n", min);
        System.out.printf("Максимальное занчение = %d\n", max);

        // 6
        System.out.println("\nЗадание 6. Тест.");
        int[] arr61 = {2, 2, 2, 1, 2, 2, 10, 1};
        printArray(arr61);
        boolean balance = checkBalance(arr61);
        System.out.printf("Массив %sделится на 2 равные суммы.\n", balance ? "" : "не ");

        int[] arr62 = {1, 1, 1, 2, 1 };
        printArray(arr62);
        balance = checkBalance(arr62);
        System.out.printf("Массив %sделится на 2 равные суммы.\n", balance ? "" : "не ");

        // Массив случайных чисел
        int[] arr63 = new int[rand.nextInt(5) + 5];
        for (int i = 0; i < arr63.length; i++) {
            arr63[i] = rand.nextInt(6) + 1;
        }
        printArray(arr63);
        balance = checkBalance(arr63);
        System.out.printf("Массив %sделится на 2 равные суммы.\n", balance ? "" : "не ");

        // 7
        System.out.println("\nЗадание 7. Тест.");
        int[] arr7 = new int[rand.nextInt(5) + 8];
        for (int i = 0; i < arr7.length; i++) {
            arr7[i] = rand.nextInt(25);
        }
        printArray(arr7);

        System.out.println("\nСместить на 2.");
        shiftArrayByNumber(arr7, 2);
        printArray(arr7);

        System.out.println("\nСместить на -3.");
        shiftArrayByNumber(arr7, -3);
        printArray(arr7);

        System.out.println("\nСместить на 1.");
        shiftArrayByNumber(arr7, 1);
        printArray(arr7);
    }

    /**
     * Вспомогательный метод печати целочисленного массива
     */
    public static void printArray(int[] arr) {
        System.out.print(Arrays.toString(arr));
        System.out.println();
    }

    public static void print2DArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.printf("%5d", arr[i][j]);
            }
            System.out.println();
        }
    }

    /*
    1. Задать целочисленный массив, состоящий из элементов 0 и 1.
    Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ]. С помощью цикла и условия заменить 0 на 1, 1 на 0;
    */
    public static void invertArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (arr[i] == 0) ? 1 : 0;
        }
    }

    /*
    2. Задать пустой целочисленный массив размером 8. С помощью цикла заполнить
    его значениями 0 3 6 9 12 15 18 21;
    */
    public static void fillArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i * 3;
        }
    }

    /*
    3. Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ] пройти по нему циклом,
    и числа меньшие 6 умножить на 2;
    */
    public static void multiplyArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 6) {
                arr[i] *= 2;
            }
        }
    }

    /*
    4. Создать квадратный двумерный целочисленный массив (количество строк и столбцов одинаковое),
    и с помощью цикла(-ов) заполнить его диагональные элементы единицами;
    */
    public static void fillDiagonalsForArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i][i] = 1;
            arr[i][arr.length - 1 - i] = 1;
        }
    }

    /*
    5. ** Задать одномерный массив и найти в нем минимальный и максимальный
    элементы (без помощи интернета);
    */
    public static int minForArray(int[] arr) {
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    public static int maxForArray(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    /*
    6. ** Написать метод, в который передается не пустой одномерный целочисленный
    массив, метод должен вернуть true, если в массиве есть место, в котором сумма
    левой и правой части массива равны.
    Примеры: checkBalance([2, 2, 2, 1, 2, 2, || 10, 1]) → true,
    checkBalance([1, 1, 1, || 2, 1]) → true, граница показана символами ||,
    эти символы в массив не входят.
    */
    public static boolean checkBalance(int[] arr) {
        int sum1;
        int sum2;

        for (int i = 1; i < arr.length; i++) {

            sum1 = 0;
            sum2 = 0;

            for (int j = 0; j < i; j++) {
                sum1 += arr[j];
            }

            for (int j = i; j < arr.length; j++) {
                sum2 += arr[j];
            }

            if (sum1 == sum2) {
                return true;
            }
        }

        return false;
    }


    /*
     7. **** Написать метод, которому на вход подается одномерный массив и число n
     (может быть положительным, или отрицательным), при этом метод должен сместить
     все элементымассива на n позиций. Для усложнения задачи нельзя
     пользоваться вспомогательными массивами.
    */
    public static void shiftArrayByNumber(int[] arr, int n) {
        if (n == 0) {
            return;  // Нечего делать
        }

        int cycles = Math.abs(n);  // Количество смещений
        for (int i = 0; i < cycles; i++) {
            if (n > 0) {
                shiftArrayRight(arr);
            } else {
                shiftArrayLeft(arr);
            }
        }
    }

    /**
     * Сдвигает значения в массиве на одну яцейку влево
     *
     * @param arr Изменяемый массив
     */
    public static void shiftArrayLeft(int[] arr) {
        int first = arr[0];
        for (int i = 0; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[arr.length - 1] = first;
    }

    /**
     * Сдвигает значения в массиве на одну яцейку вправо
     *
     * @param arr Изменяемый массив
     */
    public static void shiftArrayRight(int[] arr) {
        int last = arr[arr.length - 1];
        for (int i = arr.length - 1; i > 0; i--) {
            arr[i] = arr[i - 1];
        }
        arr[0] = last;
    }
}
