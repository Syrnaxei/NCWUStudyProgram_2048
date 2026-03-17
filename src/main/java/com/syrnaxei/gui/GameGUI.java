package com.syrnaxei.gui;

import com.syrnaxei.game2048.Board;
import com.syrnaxei.game2048.GameConfig;
import com.syrnaxei.game2048.MergeLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class GameGUI extends JFrame {
    private final Board board;
    private final MergeLogic mergeLogic;
    private TilePanel[][] tilePanels;
    private JLabel scoreValueLabel;
    private JLabel bestScoreValueLabel;
    private boolean gameWonAndContinuing = false;
    private int easterEggCounter;

    ImageIcon gameIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon/game_icon.png")));
    ImageIcon infoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon/info_icon.png")));


    public GameGUI(Board board, MergeLogic mergeLogic) {
        this.board = board;
        this.mergeLogic = mergeLogic;
        initializeUI();
        setupKeyListener();
        refreshBoard(); // 显示初始方块
    }

    private void initializeUI() {
        setTitle("NCWUStudyProgram2048");
        setIconImage(gameIcon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(205, 220, 230));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // 创建顶部面板
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // 左侧：大标题 "2048"
        JLabel titleLabel = new JLabel("2048");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 44));
        titleLabel.setForeground(new Color(0, 60, 120));
        topPanel.add(titleLabel, BorderLayout.WEST);

        // 右侧：分数框 + 最高分框 + 按钮
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        rightPanel.setOpaque(false);

        // 当前分数框
        scoreValueLabel = new JLabel("0");
        JPanel scoreBox = createScoreBox("分数", scoreValueLabel);

        // 最高分框
        bestScoreValueLabel = new JLabel(String.valueOf(GameConfig.bestScore));
        JPanel bestBox = createScoreBox("最高", bestScoreValueLabel);

        // 添加分数框的点击彩蛋
        easterEggCounter = 0;
        scoreBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                easterEggCounter++;
                if (easterEggCounter == 20) {
                    JOptionPane.showMessageDialog(GameGUI.this, "?", "?", JOptionPane.INFORMATION_MESSAGE);
                    board.addScore(-999999999);
                    refreshBoard();
                }
                GameGUI.this.requestFocusInWindow();
            }
        });
        scoreBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton refreshButton = createRefreshButton();
        JButton infoButton = createInfoButton();

        rightPanel.add(scoreBox);
        rightPanel.add(bestBox);
        rightPanel.add(refreshButton);
        rightPanel.add(infoButton);

        topPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 创建游戏网格面板
        JPanel gridPanel = new JPanel(new GridLayout(GameConfig.BOARD_SIZE, GameConfig.BOARD_SIZE, 8, 8));
        gridPanel.setBackground(new Color(120, 150, 175));
        gridPanel.setPreferredSize(new Dimension(GameConfig.windowWidth, GameConfig.windowHeight));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 初始化方块面板数组
        tilePanels = new TilePanel[GameConfig.BOARD_SIZE][GameConfig.BOARD_SIZE];

        // 创建每个方格
        for (int i = 0; i < GameConfig.BOARD_SIZE; i++) {
            for (int j = 0; j < GameConfig.BOARD_SIZE; j++) {
                tilePanels[i][j] = new TilePanel();
                gridPanel.add(tilePanels[i][j]);
            }
        }

        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // 创建底部面板
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        JLabel instructionLabel = new JLabel("使用 WASD 或 方向键 移动方块，按 R 键重置棋盘。");
        instructionLabel.setForeground(new Color(60, 80, 100));
        instructionLabel.setFont(new Font("微软雅黑", Font.BOLD, 13));
        bottomPanel.add(instructionLabel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    // 创建分数显示框（标题 + 数值）
    private JPanel createScoreBox(String title, JLabel valueLabel) {
        JPanel box = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(new Color(100, 160, 205));
        box.setOpaque(false);
        box.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        box.setPreferredSize(new Dimension(80, 46));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("微软雅黑", Font.BOLD, 11));
        titleLbl.setForeground(new Color(220, 235, 250));
        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        valueLabel.setFont(new Font("Arial", Font.BOLD, 17));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(titleLbl);
        box.add(valueLabel);
        return box;
    }

    // 创建功能按钮
    private JButton createInfoButton() {
        JButton infoButton = new JButton("i");
        styleFlatButton(infoButton);
        infoButton.addActionListener(e -> new InfoGUI(this, infoIcon));
        return infoButton;
    }

    private JButton createRefreshButton() {
        JButton refreshButton = new JButton("R");
        styleFlatButton(refreshButton);
        refreshButton.addActionListener(e -> {
            board.resetBoard();
            gameWonAndContinuing = false;
            easterEggCounter = 0;
            refreshBoard();
            this.requestFocusInWindow();
        });
        return refreshButton;
    }

    private void styleFlatButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(100, 160, 205));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setPreferredSize(new Dimension(34, 46));
        // 圆角绘制
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(100, 160, 205));
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10);
                g2.dispose();
                super.paint(g, c);
            }
        });
    }


    private void setupKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean moved = false;
                int[][] boardBefore = copyBoard(board.getBoard());

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        mergeLogic.mergeUp();
                        moved = !boardsEqual(boardBefore, board.getBoard());
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        mergeLogic.mergeDown();
                        moved = !boardsEqual(boardBefore, board.getBoard());
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        mergeLogic.mergeLeft();
                        moved = !boardsEqual(boardBefore, board.getBoard());
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        mergeLogic.mergeRight();
                        moved = !boardsEqual(boardBefore, board.getBoard());
                        break;
                    case KeyEvent.VK_R:
                        board.resetBoard();
                        gameWonAndContinuing = false;
                        easterEggCounter = 0;
                        refreshBoard();
                        break;
                    case KeyEvent.VK_C:
                        board.cheat();
                        refreshBoard();
                        checkGameOver();
                        break;
                }

                if (moved) {
                    board.addNumber();
                    refreshBoard();
                    checkGameOver();
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow();
    }

    // 复制棋盘用于比较
    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }

    // 比较两个棋盘是否相同
    private boolean boardsEqual(int[][] a, int[][] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void refreshBoard() {
        int[][] boardData = this.board.getBoard();
        int score = this.board.getScore();

        SwingUtilities.invokeLater(() -> {
            // 更新分数和最高分显示
            scoreValueLabel.setText(String.valueOf(score));
            bestScoreValueLabel.setText(String.valueOf(GameConfig.bestScore));

            // 更新方格显示
            for (int i = 0; i < GameConfig.BOARD_SIZE; i++) {
                for (int j = 0; j < GameConfig.BOARD_SIZE; j++) {
                    tilePanels[i][j].setValue(boardData[i][j]);
                }
            }
        });
    }

    private void checkGameOver() {
        // 胜利检测：未在继续模式时才弹出胜利对话框
        if (!gameWonAndContinuing && board.isGameWin()) {
            SwingUtilities.invokeLater(() -> {
                // 更新最高分
                if (GameConfig.bestScore < board.getScore()) {
                    GameConfig.bestScore = board.getScore();
                    GameConfig.saveBestScore();
                }
                int choice = JOptionPane.showOptionDialog(
                        this,
                        "恭喜！您达到了 2048！\n当前得分: " + board.getScore() + "\n历史最高分: " + GameConfig.bestScore,
                        "游戏胜利！",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"继续游戏", "重新开始"},
                        "继续游戏"
                );
                if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                    board.resetBoard();
                    gameWonAndContinuing = false;
                    easterEggCounter = 0;
                } else {
                    gameWonAndContinuing = true;
                    easterEggCounter = 0;
                }
                refreshBoard();
            });
            return;
        }

        // 失败检测
        if (board.isGameOver()) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                        "游戏结束！最终得分: " + board.getScore() + "\n历史最高分: " + GameConfig.bestScore,
                        "游戏结束", JOptionPane.INFORMATION_MESSAGE);
                if (GameConfig.bestScore < board.getScore()) {
                    GameConfig.bestScore = board.getScore();
                    GameConfig.saveBestScore();
                }
                board.resetBoard();
                gameWonAndContinuing = false;
                easterEggCounter = 0;
                refreshBoard();
            });
        }
    }

    // 自定义方块面板类（带圆角）
    private static class TilePanel extends JPanel {
        private static final int ARC = 10;
        private JLabel valueLabel;
        private Color tileColor = new Color(180, 200, 215);

        public TilePanel() {
            initializeTile();
        }

        private void initializeTile() {
            setPreferredSize(new Dimension(90, 90));
            setOpaque(false); // 关闭默认填充以便自定义圆角绘制
            setLayout(new BorderLayout());

            valueLabel = new JLabel("", SwingConstants.CENTER);
            valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
            add(valueLabel, BorderLayout.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(tileColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);
            g2.dispose();
            super.paintComponent(g);
        }

        private void setTileColor(Color color) {
            this.tileColor = color;
            repaint();
        }

        public void setValue(int value) {
            if (value == 0) {
                valueLabel.setText("");
                tileColor = new Color(180, 200, 215);
            } else {
                valueLabel.setText(String.valueOf(value));
                // 根据数值大小调整字体
                int fontSize = value >= 1000 ? 18 : (value >= 100 ? 22 : 24);
                valueLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
                setColorByValue(value);
            }
            repaint();
        }

        private void setColorByValue(int value) {
            switch (value) {
                case 2:
                    tileColor = new Color(220, 235, 245);
                    valueLabel.setForeground(new Color(40, 60, 80));
                    break;
                case 4:
                    tileColor = new Color(180, 215, 235);
                    valueLabel.setForeground(new Color(30, 50, 75));
                    break;
                case 8:
                    tileColor = new Color(100, 160, 205);
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 16:
                    tileColor = new Color(50, 130, 195);
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 32:
                    tileColor = new Color(1, 110, 190);
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 64:
                    tileColor = new Color(1, 96, 176);
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 128:
                    tileColor = new Color(0, 85, 155);
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 256:
                    tileColor = new Color(0, 75, 140);
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 512:
                    tileColor = new Color(0, 65, 125);
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 1024:
                    tileColor = new Color(0, 55, 110);
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 2048:
                    tileColor = new Color(0, 45, 95);
                    valueLabel.setForeground(new Color(255, 215, 0)); // 金色高亮
                    break;
                default:
                    tileColor = new Color(30, 30, 40);
                    valueLabel.setForeground(Color.WHITE);
                    break;
            }
        }
    }
}