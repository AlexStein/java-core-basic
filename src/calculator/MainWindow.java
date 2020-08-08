package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

    private final int GAP = 8;
    private final int BORDER = 16;

    private String operand1;
    private String operand2;

    private char operation = 0;

    private boolean newCalculation = true;

    private final Label previewLabel;
    private final TextField entry;

    public MainWindow() {
        setBounds(200, 200, 480, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simple calculator");

        BorderLayout mainLayout = new BorderLayout(GAP, GAP);
        setLayout(mainLayout);

        Font previewFont = new Font("Arial", Font.PLAIN, 16);
        Font font = new Font("Arial", Font.PLAIN, 32);
        setFont(font);

        // JPanel topPanel = new JPanel(new FlowLayout());
        JPanel topPanel = new JPanel() {
            public Insets getInsets() {
                return new Insets(BORDER, BORDER, BORDER * 2, BORDER);
            }
        };
        topPanel.setLayout(new GridLayout(2, 1));

        previewLabel = new Label();
        previewLabel.setFont(previewFont);
        previewLabel.setAlignment(Label.RIGHT);
        previewLabel.setMinimumSize(new Dimension(480 - BORDER * 2, 32));
        topPanel.add(previewLabel);

        entry = new TextField();
        entry.setFont(font);
        topPanel.add(entry);

        add(topPanel, BorderLayout.NORTH);

        JPanel numberPanel = new JPanel() {
            public Insets getInsets() {
                return new Insets(0, BORDER, 0, BORDER);
            }
        };
        numberPanel.setLayout(new GridLayout(4, 4, GAP, GAP));

        char[] buttonLetters = {
                '7', '8', '9', '+',
                '4', '5', '6', '-',
                '1', '2', '3', '*',
                '.', '0', 0x00B1, '/'
        };

        // Создадим кнопки чисел
        for (int i = 0; i < buttonLetters.length; i++) {
            JButton button = new JButton(String.valueOf(buttonLetters[i]));
            button.setName("button" + buttonLetters[i]);
            button.setFont(font);
            button.addActionListener(e -> {
                processButton(e);
            });
            numberPanel.add(button);
        }

        add(numberPanel, BorderLayout.CENTER);

        //JPanel bottomPanel = new JPanel(new FlowLayout());
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, GAP, GAP)) {
            public Insets getInsets() {
                return new Insets(0, BORDER, BORDER, BORDER);
            }
        };

        JButton buttonEquals = new JButton("=");
        buttonEquals.setName("buttonEquals");
        buttonEquals.setFont(font);
        buttonEquals.addActionListener(e -> {
            processButton(e);
        });
        bottomPanel.add(buttonEquals);

        JButton buttonClear = new JButton("C");
        buttonClear.setName("buttonClear");
        buttonClear.setFont(font);
        buttonClear.addActionListener(e -> {
            processButton(e);
        });
        bottomPanel.add(buttonClear);

        add(bottomPanel, BorderLayout.SOUTH);

        // Начальные значения переменныъ и полей
        entry.setText("0");
        reset();
        buildPreview();

        setVisible(true);
    }

    @Override
    public Insets getInsets() {
        return new Insets(BORDER * 2, 0, 0, 0);
    }

    /**
     * Обработка нажатия клавиши. Читается значение с кнопки.
     * В зависимости от того это кнопка с цифрой или знаком,
     * совершается действие.
     *
     * @param e Событие нажатия кнопки
     */
    private void processButton(ActionEvent e) {

        // Получим символ с кнопки
        char c = ((JButton) (e.getSource())).getText().charAt(0);

        if (Character.isDigit(c)) {
            // Добавляем к операнду
            if (newCalculation) {
                entry.setText("");
                newCalculation = false;
            }

            if (entry.getText().equals("0")) {
                entry.setText("");
            }

            entry.setText(entry.getText() + c);
        } else if (c == '.') {
            String s = entry.getText();
            if (s.contains(".")) {
                // Уже есть, ничего не делаем
                return;
            }
            entry.setText(s + c);

        } else if (c == '*' || c == '/' || c == '+' || c == '-') {
            // Операция
            operation = c;

            // Сразу нажали знак после предыдущего вычисления,
            // Берем предыдущее вычисление как первы операнд
            if (newCalculation) {
                newCalculation = false;
            }
            operand1 = entry.getText();
            entry.setText("0");

        } else if (c == 0x00B1) {
            // Смена знака текущего операнда
            String s = entry.getText();
            if (s.charAt(0) == '-') {
                entry.setText(s.substring(1));
            } else {
                entry.setText("-" + s);
            }

        } else if (c == '=') {
            operand2 = entry.getText();
            // Попытка вычислить выражение
            calculate(operand1, operand2, operation);
            return;

        } else {
            entry.setText("0");
            reset(); // Сброс
        }

        buildPreview();
    }

    /**
     * Вычисление выражения
     *
     * @param operand1 Первый операнд выражения в виде строки
     * @param operand2 Второй операнд выражения в виде строки
     * @param operation Знак действия +, -, * или /
     */
    private void calculate(String operand1, String operand2, char operation) {
        float result = 0;
        float o1;
        float o2;

        try {
            o1 = Float.parseFloat(operand1);
            o2 = Float.parseFloat(operand2);

        } catch (Exception e) {
            setError();
            return;
        }

        try {
            switch (operation) {
                case '+':
                    result = o1 + o2;
                    break;
                case '-':
                    result = o1 - o2;
                    break;
                case '*':
                    result = o1 * o2;
                    break;
                case '/':
                    if (o2 == 0) {
                        // Чтобы не получать Infinity
                        throw new ArithmeticException();
                    }
                    result = o1 / o2;
                    break;
            }

        } catch (Exception e) {
            setError();
            return;
        }

        if (result == Math.round(result)) {
            entry.setText(String.valueOf(Math.round(result)));
        } else {
            entry.setText(String.valueOf(result));
        }

        reset();
    }

    /**
     * Выводит строковое представение вычисляемого выражения в строку
     * экране
     */
    private void buildPreview() {

        if (newCalculation) {
            previewLabel.setText("Новое вычисление");
            return;
        }

        StringBuilder sb = new StringBuilder();
        if (operand1 == null) {
            previewLabel.setText(entry.getText());
            return;
        } else {
            sb.append(operand1);
        }

        if (operation != 0) {
            sb.append(" " + operation + " ");
        }

        if (operand2 != null) {
            sb.append(operand2);
        } else {
            sb.append(entry.getText());
        }

        previewLabel.setText(sb.toString());
    }

    /**
     * При ошибке вычисления или исходных данных, выводим в строке
     * примера, что результат Ошибка и сбрасываем поле ввода на 0
     */
    private void setError() {
        previewLabel.setText(previewLabel.getText() + " = Ошибка");
        entry.setText("0");
        reset();
    }

    /**
     * Сброс состояния калькулятора
     */
    private void reset() {
        newCalculation = true;

        operation = 0;
        operand1 = null;
        operand2 = null;
    }
}
