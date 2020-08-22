package game;

import javax.swing.*;
import java.awt.*;

public class SettingWindow extends JFrame {
    private final GameWindow gameWindow;

    private final int BORDER = 16;

    private static final int WINDOW_HEIGHT = 480;
    private static final int WINDOW_WIDTH = 420;

    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;

    static final int GAME_MODE_H_VS_A = 0;
    static final int GAME_MODE_H_VS_H = 1;

    private JButton buttonStartGame;

    private JRadioButton jrbHumanVsAi;
    private JRadioButton jrbHumanVsHuman;
    private ButtonGroup bgGameMode;

    private JSlider jsFieldSize;
    private JSlider jsWinningLength;

    public SettingWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        int x = gameWindow.getX() + (gameWindow.getWidth() - WINDOW_WIDTH) / 2;
        int y = gameWindow.getY() + (gameWindow.getHeight() - WINDOW_HEIGHT) / 2;

        setBounds(x, y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Настройки игры");

        createUI();

        buttonStartGame.addActionListener(e -> {
            startGameClicked();
            setVisible(false);
        });

        setVisible(false);
    }

//    @Override
//    public Insets getInsets()
//    {
//        return new Insets(BORDER * 2,BORDER,BORDER,BORDER);
//    }

    private void startGameClicked() {

        int mode = jrbHumanVsAi.isSelected() ? GAME_MODE_H_VS_A : GAME_MODE_H_VS_H;
        int fieldSize = jsFieldSize.getValue();

        Logic.SIZE = fieldSize;
        Logic.DOTS_TO_WIN = jsWinningLength.getValue();
        Logic.initMaps();
        Logic.isFinished = false;

        this.gameWindow.startNewGame(mode, fieldSize);
    }

    private void createUI() {

        GridLayout grid = new GridLayout(8, 1);

        setLayout(grid);

        setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        add(new JLabel("Режим игры:"));
        jrbHumanVsAi = new JRadioButton("Игрок против компьютера", true);
        jrbHumanVsHuman = new JRadioButton("Игрок против игрока");

        bgGameMode = new ButtonGroup();
        bgGameMode.add(jrbHumanVsAi);
        bgGameMode.add(jrbHumanVsHuman);

        add(jrbHumanVsAi);
        add(jrbHumanVsHuman);

        add(new JLabel("Размер поля:"));

        jsFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        jsFieldSize.setMajorTickSpacing(1);
        jsFieldSize.setPaintTicks(true);
        jsFieldSize.setPaintLabels(true);

        add(jsFieldSize);

        add(new JLabel("Длина линии для победы:"));

        jsWinningLength = new JSlider(MIN_FIELD_SIZE, MIN_FIELD_SIZE, MIN_FIELD_SIZE);
        jsWinningLength.setMajorTickSpacing(1);
        jsWinningLength.setPaintTicks(true);
        jsWinningLength.setPaintLabels(true);

        add(jsWinningLength);

        jsFieldSize.addChangeListener(e -> {
            jsWinningLength.setMaximum(jsFieldSize.getValue());
        });

        buttonStartGame = new JButton("Начать игру");
        add(buttonStartGame);
    }
}
