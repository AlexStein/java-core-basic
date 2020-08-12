package game;

import java.util.Random;

public class Logic {
    static int SIZE;
    static int DOTS_TO_WIN;

    static final char DOT_X = 'X';
    static final char DOT_O = 'O';
    static final char DOT_EMPTY = '.';

    //static char winningSymbol = DOT_EMPTY;
    static String winningMessage;

    static char[][] map;
    static char[][] winMap;

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

    static Random random = new Random();

    static boolean isFinished;

    /**
     * Создание и заполенение исходного поля заданной размерности
     */
    public static void initMaps() {
        map = new char[SIZE][SIZE];
        winMap = new char[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
                winMap[i][j] = DOT_EMPTY;
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
     * Вывод игрового поля с координатами в консоль
     */
    public static void printWinMap() {
        System.out.println("Победная комбинация");
        System.out.print("  ");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(winMap[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
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

        if (isCellOutbound(y, x)) {
            return false;
        }

        return map[y][x] == DOT_EMPTY;
    }

    /**
     * Проверяет, входит ли ячейка в границы игрового поля.
     *
     * @param y Номер строки на поле
     * @param x Номер столбца на поле
     * @return Координаты ячейки соотвествуют игровому полю
     */
    public static boolean isCellOutbound(int y, int x) {
        return x < 0 || y < 0 || x >= SIZE || y >= SIZE;
    }

    public static void goHuman(char dot) {

        printMap();
        if (checkWin(dot)) {
            String playerName = "Игрок";

            if (dot == DOT_O) {
                playerName = "Игрок 2";
            }
            getWinLine(dot);
            printWinMap();

            isFinished = true;
            winningMessage = String.format("%s победил!", playerName);
            return;
        }

        if (isFull()) {
            winningMessage = "Ничья!";
            return;
        }

        isFinished = false;
    }

    /**
     * Ход игрока
     *
     * @param x   Координата ячейки
     * @param y   Координата ячейки
     * @param dot Символ игрока на поле
     * @return Ход сделан
     */
    public static boolean humanTurn(int x, int y, char dot) {
        if (isCellValid(y, x)) {
            map[y][x] = dot;
            goHuman(dot);

            return true;
        }

        return false;
    }

    /**
     * Ход компьютера:
     * 1. Пыьается найти свою победную комбинацию
     * 2. Пытается блокировать победную комбинацию игрока
     * 3. Пытается блокировать комбинацию на 2 короче победной
     * 4. Пытается продолжить свою линию
     * 5. Ставит новых знак рядом со своим
     * 6. Если не выполнились пункты 1, 2, 3 - произвольный ход
     */
    public static void aiTurn() {
        int x, y;

        // Попытка найти свою победу
        if (aiWinSearchTurn(DOT_O)) {
            goAi();
            return;
        }

        // Попытка блокировать ход игрока
        if (aiWinSearchTurn(DOT_X)) {
            goAi();
            return;
        }

        // Попытка блокировать ход игрока на 2 короче победы
        if (aiWinSearchTurn(DOT_X, DOTS_TO_WIN - 1)) {
            goAi();
            return;
        }

        // Попытка продолжить свою линию
        if (aiBuildLineTurn()) {
            goAi();
            return;
        }

        // Попытка поставить в свободную соседнюю
        if (aiSetNeighborTurn()) {
            goAi();
            return;
        }

        // Не вышло, тогда рандом
        do {
            // Random
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(y, x));

        map[y][x] = DOT_O;
        goAi();
    }

    public static void goAi() {

        printMap();
        if (checkWin(DOT_O)) {

            isFinished = true;
            winningMessage = "Компьютер победил!";

            getWinLine(DOT_O);
            printWinMap();

            return;
        }

        if (isFull()) {
            winningMessage = "Ничья!";
            return;
        }

        isFinished = false;
    }

    /**
     * Поиск победной линии заданной в настройках длины.
     * Блокирование.
     *
     * @param c Символ для которого делаем проверку: Х или О.
     * @return Если ход сделан, возарщает Истина
     */
    public static boolean aiWinSearchTurn(char c) {
        return aiWinSearchTurn(c, DOTS_TO_WIN);
    }

    /**
     * Поиск возможного хода, приводящего к победе и замена своим знаком.
     * В случае поиска победы игрока, ход игрока блокируется, в случае
     * поиска победы компьютера - компьютер выигрывает.
     *
     * @param c    Символ для которого делаем проверку: Х или О.
     * @param dots Длина победной линии
     * @return Если ход сделан, возарщает Истина
     */
    public static boolean aiWinSearchTurn(char c, int dots) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {

                    // Ставим знак O
                    map[i][j] = c;

                    // Проверяем условие победы и ставим знак О
                    if (checkWin(c, dots)) {

                        // При проверке победы, ставим символ всегда
                        if (dots == DOTS_TO_WIN) {
                            map[i][j] = DOT_O;
                            return true;
                        }

                        // При проверке коротких линий, можем блокировать ход,
                        // а можем и не блокировать.
                        if (random.nextInt(2) == 1) {
                            map[i][j] = DOT_O;
                            return true;
                        }
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
     *
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
                        if (isCellOutbound(y1, x1)) {
                            continue;
                        }

                        // Если нашли рядом символ, пытаемся поставить,
                        // с противоположной стороны
                        if (map[x1][y1] == DOT_O) {
                            x2 = i + searchPath[searchPath.length - 1 - k][0];
                            y2 = j + searchPath[searchPath.length - 1 - k][1];

                            // Ячейка за пределами - пропускаем
                            if (isCellOutbound(y2, x2)) {
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
     *
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
                        if (isCellOutbound(y1, x1)) {
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
     * Проверка критерия победы для линии DOTS_TO_WIN
     *
     * @param c Символ для которого делаем проверку: Х или О
     * @return Истина, если на поле есть победная последовательность.
     */
    public static boolean checkWin(char c) {
        return checkWin(c, DOTS_TO_WIN);
    }

    /**
     * Проверка критерия победы. Идея проверить все
     * массивы размерности DOTS_TO_WIN x DOTS_TO_WIN впысывающиеся
     * в игровое поле.
     *
     * @param c    Символ для которого делаем проверку: Х или О
     * @param dots Длина победной линии
     * @return Истина, если на поле есть победная последовательность.
     */
    public static boolean checkWin(char c, int dots) {
        if (dots > SIZE || dots <= 0) {
            return false;
        }

        // Проверим строки и столбцы
        for (int i = 0; i < SIZE - dots + 1; i++) {
            for (int j = 0; j < SIZE - dots + 1; j++) {
                if (checkAreaWin(c, dots, i, j)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Проверка массива размерности dots x dots на наличие
     * победного ряда символов в строке, столбце или диагонялях.
     *
     * @param c      Символ для поиска
     * @param dots   Длина победной линии и размерность матрицы поиска
     * @param startX начальная координата X на игровом поле для отсчета массива
     * @param startY начальная координата X на игровом поле для отсчета массива
     * @return Победа найдена
     */
    public static boolean checkAreaWin(char c, int dots, int startX, int startY) {

        char[] diagonal = new char[dots];
        char[] second_diagonal = new char[dots];
        char[] line = new char[dots];
        char[] column = new char[dots];

        for (int i = 0; i < dots; i++) {

            for (int j = 0; j < dots; j++) {
                line[j] = map[i + startX][j + startY];
                column[j] = map[j + startY][i + startX];
            }

            // Строка i
            if (checkSequence(line, c, dots)) {
                return true;
            }

            // Стобец i
            if (checkSequence(column, c, dots)) {
                return true;
            }

            diagonal[i] = map[i + startX][i + startY];
            second_diagonal[i] = map[dots - 1 - i + startX][i + startY];
        }

        // Диагональ 1
        if (checkSequence(diagonal, c, dots)) {
            return true;
        }

        // Диагональ 2
        if (checkSequence(second_diagonal, c, dots)) {
            return true;
        }

        return false;
    }

    /**
     * Получить матрицу с победной линией
     *
     * @param c    Символ победивщего игрока
     */
    private static void getWinLine(char c) {

        int dots = DOTS_TO_WIN;

        char[] diagonal = new char[SIZE];
        char[] second_diagonal = new char[SIZE];
        char[] line = new char[SIZE];
        char[] column = new char[SIZE];

        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {
                line[j] = map[i][j];
                column[j] = map[j][i];
            }

            // Строка i
            if (checkSequence(line, c, dots)) {
                for (int j = 0; j < SIZE; j++) {
                    winMap[i][j] = (map[i][j] == c) ? c : DOT_EMPTY;
                }
                return;
            }

            // Стобец i
            if (checkSequence(column, c, dots)) {
                for (int j = 0; j < SIZE; j++) {
                    winMap[j][i] = (map[j][i] == c) ? c : DOT_EMPTY;
                }
                return;
            }

            diagonal[i] = map[i][i];
            second_diagonal[i] = map[SIZE - 1 - i][i];
        }

        // Диагональ 1
        if (checkSequence(diagonal, c, dots)) {
            for (int i = 0; i < SIZE; i++) {
                winMap[i][i] = (map[i][i] == c) ? c : DOT_EMPTY;
            }

            return;
        }

        // Диагональ 2
        if (checkSequence(second_diagonal, c, dots)) {
            for (int i = 0; i < SIZE; i++) {
                winMap[SIZE - 1 - i][i] = (map[SIZE - 1 - i][i] == c) ? c : DOT_EMPTY;
            }
        }

    }

    /**
     * Проверить, если ли в последовательности символов заданное количество
     * подряд идущих символов.
     *
     * @param sequence Последовательность для поиска
     * @param c        Проверяемы символ последовательности
     * @param required Длина искомой последовательности
     * @return Истина, если последовательность состоит из требуемого количества символов
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

    public static String getMessage() {
        return winningMessage;
    }
}
