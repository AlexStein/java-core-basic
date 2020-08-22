package game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    static final int WINDOW_POS_X = 500;
    static final int WINDOW_POS_Y = 300;
    static final int WINDOW_HEIGHT = 540;
    static final int WINDOW_WIDTH = 500;

    private final SettingWindow settingWindow;
    private final BattleField battleField;

    public GameWindow() {
        setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setTitle("Игра Крестики нолики");

        settingWindow = new SettingWindow(this);
        battleField = new BattleField(this);

        add(battleField, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(1, 2));
        JButton buttonOk = new JButton("Начать игру");
        JButton buttonExit = new JButton("Выход");

        panel.add(buttonOk);
        panel.add(buttonExit);

        add(panel, BorderLayout.SOUTH);

        buttonExit.addActionListener(e -> {
            System.exit(0);
        });

        buttonOk.addActionListener(e -> {
            settingWindow.setVisible(true);
        });

        setVisible(true);
    }

    public void startNewGame(int mode, int fieldSize){
        battleField.startNewGame(mode, fieldSize);
    }

    /**
     * Функция создания диалогового окна.
     *
     * @param title   Заголовок окна
     * @param message Сообщение
     */
    private JDialog createDialog(String title, String message) {

        int dialogHeight = 240;
        int dialogLeft = 50;

        int x = getX() + dialogLeft;
        int y = getHeight() / 2 + getY() - dialogHeight / 2;

        JDialog dialog = new JDialog(this, title, true);
        dialog.setBounds(x, y, getWidth() - 2 * dialogLeft, dialogHeight);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Font font = new Font("Arial", Font.BOLD, 24);

        JLabel labelMessage = new JLabel(message);
        labelMessage.setHorizontalAlignment(SwingConstants.CENTER);
        labelMessage.setFont(font);
        dialog.add(labelMessage);

        JButton buttonOk = new JButton("OK");
        buttonOk.addActionListener(e -> {
            dialog.setVisible(false);
        });

        dialog.add(buttonOk, BorderLayout.SOUTH);

        return dialog;
    }

    public void showFinishDialog() {
        JDialog dialog = createDialog("Игра завершена", Logic.getMessage());
        dialog.setVisible(true);
    }
}
