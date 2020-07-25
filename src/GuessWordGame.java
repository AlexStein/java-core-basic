import java.util.Random;
import java.util.Scanner;

/**
 * Класс для игры Угадай слово.
 * компьютер загадывает слово, запрашивает ответ у пользователя,
 * сравнивает его с загаданным словом и сообщает правильно ли
 * ответил пользователь. Если слово не угадано, компьютер показывает
 * буквы которые стоят на своих местах.
 */
public class GuessWordGame {

    /** Сканнер ввода пользователя */
    public static Scanner sc = new Scanner(System.in);

    /** Массив вариантов слов */
    final static String[] words = {"apple", "orange", "lemon", "banana", "apricot",
            "avocado", "broccoli", "carrot", "cherry", "garlic", "grape", "melon",
            "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut",
            "pear", "pepper", "pineapple", "pumpkin", "potato"};

    /** Длина строки до которой заполняется сторка подсказки маскирующими символами */
    final static int fillCapacity = 15;

    public static void main(String[] args) {

        Random rand = new Random();
        String guessedWord = words[rand.nextInt(words.length)];

        // Подсказка для отладки
        // System.out.println(guessedWord);

        System.out.println("Попробуйте угадать слово. Игра до победного.");
        while (true) {
            System.out.println("Введите ваше слово:");
            String userAnswer = sc.nextLine();

            if (guessedWord.equals(userAnswer.toLowerCase())) {
                System.out.println("Поздравляю, Вы угадали!");
                break;
            } else {
                showCorrectLetters(guessedWord, userAnswer.toLowerCase());
            }
        }
    }

    /**
     * Метод выводит на экран буквы, которые стоят на своих местах. Остальные маскируются
     * символом # до 15 символов, чтобы пользователь не мог узнать длину слова.
     *
     * Пример:
     * apple – загаданное слово
     * apricot - вариант пользователя
     * ap#############
     *
     * @param guessed Загаданное слово
     * @param answer Вариант пользователя
     */
    public static void showCorrectLetters(String guessed, String answer) {
        StringBuilder sb = new StringBuilder(fillCapacity);

        int minWordLength = Math.min(guessed.length(), answer.length());

        for (int i = 0; i < minWordLength; i++) {
            sb.append(guessed.charAt(i) == answer.charAt(i) ? guessed.charAt(i) : '#');
        }

        for (int i = minWordLength; i < fillCapacity; i++) {
            sb.append('#');
        }

        // Вывод подсказки
        System.out.println("Посмотрите какие буквы оказались на своих местах:");
        System.out.println(sb.toString());
        System.out.println();
    }
}
