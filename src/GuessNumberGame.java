import java.util.Random;
import java.util.Scanner;

/**
 * Класс для игры Угадай число.
 * Программа загадывает случайное число от 0 до 9, и пользователю
 * дается 3 попытки угадать это число. При каждой попытке компьютер
 * сообщает больше ли указанное пользователем число чем загаданное, или меньше.
 */
public class GuessNumberGame {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Random rand = new Random();

        int tries = 3; // Количество попыток

        do {
            int guessedNumber = rand.nextInt(10);

            System.out.println("Попробуйте угадать число от 0 до 9.");
            for (int i = 1; i <= tries; i++) {

                System.out.printf("Количество попыток: %d\n", tries - i + 1);
                int userInput =  sc.nextInt();

                if (userInput == guessedNumber) {
                    System.out.println("Поздравляю, Вы угадали!");
                    break;

                } else if (userInput > guessedNumber) {
                    System.out.println("Вы ввели слишком большое число.");

                } else if (userInput < guessedNumber) {
                    System.out.println("Вы ввели слишком маленькое число.");
                }

                if (tries == i) {
                    System.out.println("Вы не угадали! Загаданное число: " + guessedNumber);
                }
            }
        } while(!requestFinishGame());

    }

    /**
     * Выводит запрос на завершение или продолжение игры.
     * @return Истина, если пользователь выбрал завершить игру
     */
    public static boolean requestFinishGame() {
        System.out.println("Повторить игру еще раз? 1 – да / 0 – нет:");
        int userInput =  sc.nextInt();
        return (userInput == 0);
    }
}
