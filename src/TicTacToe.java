import java.util.Random;
import java.util.Scanner;


/**
 * Класс для игры в крестики нолики
 * Размер игрового поля и критерий победы варьируется.
 * Компьютер должен блокировать победные ходы игрока.
 */
public class TicTacToe {

    static final int SIZE = 5;
    static final int DOTS_TO_WIN = 4;

    static final char DOT_X = 'X';
    static final char DOT_O = 'O';
    static final char DOT_EMPTY = '.';

    // Координаты для поиска ячеек вокруг текущей
    // 1 2 3
    // 4   5
    // 6 7 8
    static int[][] searchPath = {
        {-1, -1},
        {0, -1},
        {1, -1},
        {-1, 0},
        {1, 0},
        {1, -1},
        {1, 0},
        {1, 1}
    };

    static char[][] map;

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {

        initMap();
        printMap();

        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Ты победил! ");
                break;
            }
            if (isFull()) {
                System.out.println("Ничья!");
                break;
            }

            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Компьютер победил! ");
                break;
            }
            if (isFull()) {
                System.out.println("Ничья!");
                break;
            }

        }
    }

    /**
     * Создание и заполенение исходного поля заданной размерности
     */
    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    /**
     * Вывод игрового поля с координатами в консоль
     */
    public static void printMap() {
        System.out.print("  ");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Ход игрока
     */
    public static void humanTurn() {
        int x, y;

        do {
            System.out.println("Input X, Y ");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(y, x));

        map[y][x] = DOT_X;
    }

    /**
     * Проверка допустимости хода. Проверяются координаты, и
     * доступность ячейки для ввода.
     *
     * @param y Номер строки на поле
     * @param x Номер столбца на поле
     * @return Ход допустим, ячейка свободна
     */
    public static boolean isCellValid(int y, int x) {

        if (!isCellInbound(y, x)) {
//            System.out.println("Cell invalid");
            return false;
        }

        if (map[y][x] != DOT_EMPTY) {
//            System.out.println("Cell invalid");
            return false;
        }

        return true;
    }

    /**
     * Проверяет, входит ли ячейка в границы игрового поля.
     * @param y Номер строки на поле
     * @param x Номер столбца на поле
     * @return Координаты ячейки соотвествуют игровому полю
     */
    public static boolean isCellInbound(int y, int x) {
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) {
//            System.out.println("Cell invalid");
            return false;
        }

        return true;
    }

    /**
     * Ход компьютера:
     * 1. Пытается блокировать победную комбинацию игрока
     * 2. Пытается продолжить свою линию
     * 3. Ставит новых знак рядом со своим
     * 4. Если не выполнились пункты 1, 2, 3 - произвольный ход
     */
    public static void aiTurn() {
        int x, y;

        // Попытка блокировать ход игрока
        System.out.println("Пытаюсь блокировать...");
        if (aiBlockingTurn()) {
            return;
        }

        // Попытка продолжить свою линию
        System.out.println("Пытаюсь вывести линию...");
        if (aiBuildLineTurn()) {
            return;
        }

        // Попытка поставить в свободную соседнюю
        System.out.println("Ставлю рядышком...");
        if (aiSetNeighborTurn()) {
            return;
        }

        // Не вышло, тогда рандом
        System.out.println("Эх, от балды...");
        do {
            // Random
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(y, x));

        map[y][x] = DOT_O;
    }

    /**
     * Блокирующий ход компьютера. Поиск возможного хода игрока,
     * приводящего к победе и замена своим знаком.
     * @return Если ход сделан, возарщает Истина
     */
    public static boolean aiBlockingTurn() {
        int x, y;

        // Найти пустые ячейки и проверить условие выигрыша играка
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {

                    // Ставим знак Х
                    map[i][j] = DOT_X;

                    // Проверяем условие победы и ставим знак О
                    if (checkWin(DOT_X)) {
                        map[i][j] = DOT_O;
                        return true;
                    }

                    // Очищаем обратно
                    map[i][j] = DOT_EMPTY;
                }
            }
        }
        return false;
    }

    /**
     * Попытка продолжить свою линию
     * @return Если ход сделан, возарщает Истина
     */
    public static boolean aiBuildLineTurn() {
        int x1, y1;
        int x2, y2;

        // Прокрутить массив поиска ячеек случайным образом,
        // чтобы поиск начинался с разных ячеек каждый раз
        rotateSearchPathArray();

        // Найти пустые ячейки и проверить условие выигрыша играка
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_O) {

                    // Смотрим линии позиции вокруг и ставим рядом,
                    // а если получается продолжить последовательность
                    // из 2 символов - отлично вообще
                    for (int k = 0; k < searchPath.length; k++) {
                        x1 = i + searchPath[k][0];
                        y1 = j + searchPath[k][1];

                        // Ячейка за пределами - пропускаем
                        if (!isCellInbound(x1, y1)) {
                            continue;
                        }

                        // Если нашли рядом символ, пытаемся поставить,
                        // с противоположной стороны
                        if (map[x1][y1] == DOT_O) {
                            x2 = i + searchPath[searchPath.length - 1 - k][0];
                            y2 = j + searchPath[searchPath.length - 1 - k][1];

                            // Ячейка за пределами - пропускаем
                            if (!isCellInbound(x2, y2)) {
                                continue;
                            }

                            if (map[x2][y2] == DOT_EMPTY) {
                                map[x2][y2] = DOT_O;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Попытка оставить в свободную соседнюю
     * @return Если ход сделан, возарщает Истина
     */
    public static boolean aiSetNeighborTurn() {
        int x1, y1;

        // Прокрутить массив поиска ячеек случайным образом,
        // чтобы поиск начинался с разных ячеек каждый раз
        rotateSearchPathArray();

        // Найти пустые ячейки и проверить условие выигрыша играка
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_O) {

                    // Просто ставим в первую попавшуюся свободную вокруг
                    for (int k = 0; k < searchPath.length; k++) {
                        x1 = i + searchPath[k][0];
                        y1 = j + searchPath[k][1];

                        // Ячейка за пределами - пропускаем
                        if (!isCellInbound(x1, y1)) {
                            continue;
                        }
                        if (map[x1][y1] == DOT_EMPTY) {
                            map[x1][y1] = DOT_O;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Проверка заполненности игрового поля.
     *
     * @return Истина, если нет свободных ячеек.
     */
    public static boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Проверка критерия победы. Идея проверить все
     * массивы размерности DOTS_TO_WIN x DOTS_TO_WIN впысывающиеся
     * в игровое поле.
     *
     * @param c Символ для которого делаем проверку: Х или О
     * @return Истина, если на поле есть победная последовательность.
     */
    public static boolean checkWin(char c) {
        // Проверим строки и столбцы
        for (int i = 0; i < SIZE - DOTS_TO_WIN + 1; i++) {
            for (int j = 0; j < SIZE - DOTS_TO_WIN + 1; j++) {
                if (checkAreaWin(c, i, j)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Проверка массива размерности DOTS_TO_WIN x DOTS_TO_WIN на наличие
     * победного ряда символов в строке, столбце или диагонялях.
     * @param c Символ для поиска
     * @param startX начальная координата X на игровом поле для отсчета массива
     * @param startY начальная координата X на игровом поле для отсчета массива
     * @return
     */
    public static boolean checkAreaWin(char c, int startX, int startY) {

        char[] diagonal = new char[DOTS_TO_WIN];
        char[] second_diagonal = new char[DOTS_TO_WIN];

        for (int i = 0; i < DOTS_TO_WIN; i++) {
            char[] line = new char[DOTS_TO_WIN];
            char[] column = new char[DOTS_TO_WIN];

            for (int j = 0; j < DOTS_TO_WIN; j++) {
                line[j] = map[i + startX][j + startY];
                column[j] = map[j + startY][i + startX];
            }

            // Строка i
            if (checkSequence(line, c, DOTS_TO_WIN)) {
                return true;
            }

            // Стобец i
            if (checkSequence(column, c, DOTS_TO_WIN)) {
                return true;
            }

            diagonal[i] = map[i + startX][i + startY];
            second_diagonal[i] = map[DOTS_TO_WIN - 1 - i + startX][i + startY];
        }

        // Диагональ 1
        if (checkSequence(diagonal, c, DOTS_TO_WIN)) {
            return true;
        }

        // Диагональ 2
        if (checkSequence(second_diagonal, c, DOTS_TO_WIN)) {
            return true;
        }

        return false;
    }

    /**
     * Проверить, если ли в последовательности символов заданное количество
     * подряд идущих символов.
     *
     * @param sequence Последовательность для поиска
     * @param c        Проверяемы символ последовательности
     * @param required Длина искомой последовательности
     * @return
     */
    public static boolean checkSequence(char[] sequence, char c, int required) {
        int counter = 0;
        for (int i = 0; i < sequence.length; i++) {
            if (sequence[i] == c) {
                counter++;
            } else {
                counter = 0;
                // Осталось меньше символов чем надо, нет смысла продолжать
                if (sequence.length - i < required) {
                    return false;
                }
            }

            if (counter >= required) {
                return true;
            }
        }

        return false;
    }

    /**
     * Прокрутить массив случайным образом
     */
    public static void rotateSearchPathArray() {
        int n = random.nextInt(searchPath.length);

        if (n == 0) {
            return;  // Нечего делать
        }

        int cycles = Math.abs(n) % searchPath.length;
        for (int i = 0; i < cycles; i++) {
            shiftArrayLeft(searchPath);
        }
    }

    /**
     * Сдвигает значения в массиве поиска пути на одну ячейку
     *
     * @param arr Изменяемый массив
     */
    public static void shiftArrayLeft(int[][] arr) {
        int[] first = arr[0];
        for (int i = 0; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[arr.length - 1] = first;
    }

}
