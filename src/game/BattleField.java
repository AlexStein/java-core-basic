package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BattleField extends JPanel {
    private final GameWindow gameWindow;

    private int mode;
    private int fieldSize;
    private char currentPlayerDot;

    private boolean isInit;

    private int cellWidth;
    private int cellHeight;

    private int cellPaddingX;
    private int cellPaddingY;

    public BattleField(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        cellHeight = getWidth();
        cellWidth = getHeight();

        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                clickBattleField(e);
            }
        });
    }

    private void clickBattleField(MouseEvent e) {

        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;

        if (!Logic.isFinished) {

            if (!Logic.humanTurn(cellX, cellY, currentPlayerDot)){
                // Ход не сделан, ждем следующий ввод
                return;
            }

            // Это был последний ход
            if (Logic.isFinished) {
                repaint();
                gameWindow.showFinishDialog();
                return;
            }

            if (mode == SettingWindow.GAME_MODE_H_VS_H) {
                // Поменяем символ для следующего хода
                currentPlayerDot = (currentPlayerDot == Logic.DOT_X) ? Logic.DOT_O : Logic.DOT_X;
            } else {
                Logic.aiTurn();
                // Это был последний ход
                if (Logic.isFinished) {
                    repaint();
                    gameWindow.showFinishDialog();
                    return;
                }
            }

            repaint();
        }
    }

    public void startNewGame(int mode, int fieldSize) {
        this.mode = mode;
        this.fieldSize = fieldSize;
        // Установим символ первого игрока
        this.currentPlayerDot = Logic.DOT_X;

        isInit = true;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!isInit) {
            return;
        }

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        cellHeight = panelHeight / fieldSize;
        cellWidth = panelWidth / fieldSize;

        // Отступы в 20% для красоты
        cellPaddingX = cellWidth / 5;
        cellPaddingY = cellHeight / 5;

        int strokeWidth = 4;

        g.setColor(Color.GRAY);
        ((Graphics2D) g).setStroke(new BasicStroke(strokeWidth));

        for (int i = 0; i < fieldSize; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, panelWidth, y);
        }

        for (int i = 0; i < fieldSize; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                // Фон для победной линии
                if (Logic.winMap[i][j] != Logic.DOT_EMPTY) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(cellWidth * j + strokeWidth / 2,
                            cellHeight * i + strokeWidth / 2,
                            cellWidth - strokeWidth,
                            cellHeight - strokeWidth);
                }

                if (Logic.map[i][j] == Logic.DOT_X) {
                    drawX(g, j, i);
                }

                if (Logic.map[i][j] == Logic.DOT_O) {
                    drawO(g, j, i);
                }
            }
        }
    }

    private void drawX(Graphics g, int x, int y) {

        int x1 = cellWidth * x + cellPaddingX;
        int y1 = cellHeight * y + cellPaddingY;

        int x2 = cellWidth * (x + 1) - cellPaddingX;
        int y2 = cellHeight * (y + 1) - cellPaddingY;

        ((Graphics2D) g).setStroke(new BasicStroke(10));
        g.setColor(Color.RED);

        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x2, y1, x1, y2);
    }

    private void drawO(Graphics g, int x, int y) {

        int x1 = cellWidth * x + cellPaddingX;
        int y1 = cellHeight * y + cellPaddingY;

        int w = cellWidth - cellPaddingX * 2;
        int h = cellHeight - cellPaddingY * 2;

        ((Graphics2D) g).setStroke(new BasicStroke(10));
        g.setColor(Color.BLUE);

        g.drawOval(x1, y1, w, h);
    }
}
