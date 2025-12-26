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
    private JButton scoreButton;
    private boolean scoreButtonState;
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
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 创建顶部面板（分数）
        JPanel topPanel = new JPanel(new BorderLayout());

        //分数/历史最高分切换按键
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        scoreButton = createScoreButton();
        styleScoreButton(scoreButton);
        scorePanel.add(scoreButton);
        topPanel.add(scorePanel);

        //info Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = createRefreshButton();
        JButton infoButton = createInfoButton();
        buttonPanel.setOpaque(false);
        buttonPanel.add(refreshButton);
        buttonPanel.add(infoButton);
        topPanel.add(buttonPanel,BorderLayout.EAST);

        //add topPanel to mainPanel
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 创建游戏网格面板
        JPanel gridPanel = new JPanel(new GridLayout(GameConfig.BOARD_SIZE, GameConfig.BOARD_SIZE, 5, 5));
        gridPanel.setBackground(new Color(150, 170, 185)); //背景颜色
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
        JLabel instructionLabel = new JLabel("使用 WASD 或 方向键 移动方块，按 R 键重置棋盘。");
        instructionLabel.setForeground(new Color(80,80,80));
        instructionLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        bottomPanel.add(instructionLabel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private JButton createScoreButton() {
        JButton scoreButton = new JButton();
        styleScoreButton(scoreButton);
        scoreButtonState = false;
        easterEggCounter = 0;
        scoreButton.addActionListener(_ -> {
            easterEggCounter++;
            if(!scoreButtonState){
                scoreButton.setText("历史最高分: " + GameConfig.bestScore);
                scoreButtonState = true;
            }else{
                scoreButton.setText("分数: " + board.getScore());
                scoreButtonState = false;
            }
            if(easterEggCounter == 20){
                JOptionPane.showMessageDialog(this, "?", "?", JOptionPane.INFORMATION_MESSAGE);
                board.setScore(-999999999);
                refreshBoard();
            }
            this.requestFocusInWindow();
        });

        return scoreButton;
    }

    //infoButton create fuc
    private JButton createInfoButton() {
        JButton infoButton = new JButton("i");
        styleFlatButton(infoButton);
        infoButton.addActionListener(_ -> new InfoGUI(this, infoIcon)); // 调用 InfoGUI
        return infoButton;
    }

    //refreshButton creat fuc
    private JButton createRefreshButton() {
        JButton refreshBotton = new JButton("R");
        styleFlatButton(refreshBotton);
        refreshBotton.addActionListener(_ ->{
            board.resetBoard();
            refreshBoard();
            this.requestFocusInWindow();
        });
        return refreshBotton;
    }

    private void styleFlatButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(100, 160, 205));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置相等的边距
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(30, 30)); // 设置按钮为正方形
    }

    private void styleScoreButton(JButton button) {
        button.setLayout(new FlowLayout(FlowLayout.LEFT));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBackground(new Color(100, 160, 205));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("微软雅黑", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置相等的边距
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
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
        int[][] board = this.board.getBoard();
        int score = this.board.getScore();

        // 更新分数显示
        SwingUtilities.invokeLater(() -> {
            if(!scoreButtonState){
                scoreButton.setText("分数: " + score);
            }

            // 更新方格显示
            for (int i = 0; i < GameConfig.BOARD_SIZE; i++) {
                for (int j = 0; j < GameConfig.BOARD_SIZE; j++) {
                    tilePanels[i][j].setValue(board[i][j]);
                }
            }
        });
    }

    private void checkGameOver() {
        if (board.isGameWin()) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "游戏胜利！最终得分: " + board.getScore() + "\n历史最高分: " + GameConfig.bestScore , "游戏结束", JOptionPane.INFORMATION_MESSAGE);
                if(GameConfig.bestScore < board.getScore()){
                    GameConfig.bestScore = board.getScore();
                    GameConfig.saveBestScore();
                }
                board.resetBoard();
                easterEggCounter = 0;
                refreshBoard();
            });
            return;
        }
        if (board.isGameOver()) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "游戏结束！最终得分: " + board.getScore() + "\n历史最高分:  " + GameConfig.bestScore , "游戏结束", JOptionPane.INFORMATION_MESSAGE);
                if(GameConfig.bestScore < board.getScore()){
                    GameConfig.bestScore = board.getScore();
                    GameConfig.saveBestScore();
                }
                board.resetBoard();
                easterEggCounter = 0;
                refreshBoard();
            });
        }
    }

    // 自定义方块面板类
    private static class TilePanel extends JPanel {
        private JLabel valueLabel;

        public TilePanel() {
            initializeTile();
        }

        private void initializeTile() {
            setPreferredSize(new Dimension(90, 90));
            setBackground(new Color(180, 200, 215)); // 空方块背景色
            setLayout(new BorderLayout());

            valueLabel = new JLabel("", SwingConstants.CENTER);
            valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
            add(valueLabel, BorderLayout.CENTER);

            setBorder(BorderFactory.createLineBorder(new Color(150, 170, 185), 2));
        }

        public void setValue(int value) {
            if (value == 0) {
                valueLabel.setText("");
                setBackground(new Color(180, 200, 215)); // 空方块背景色
            } else {
                valueLabel.setText(String.valueOf(value));
                setColorByValue(value);
            }
        }

        private void setColorByValue(int value) {
            switch (value) {
                case 2:
                    setBackground(new Color(220, 235, 245)); // 浅蓝（主色浅化）
                    valueLabel.setForeground(Color.BLACK);
                    break;
                case 4:
                    setBackground(new Color(180, 215, 235)); // 淡蓝（主色弱化）
                    valueLabel.setForeground(Color.BLACK);
                    break;
                case 8:
                    setBackground(new Color(100, 160, 205)); // 浅湖蓝（主色微调）
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 16:
                    setBackground(new Color(50, 130, 195));  // 天蓝色（接近主色）
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 32:
                    setBackground(new Color(1, 110, 190));   // 主色强化（蓝调加深）
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 64:
                    setBackground(new Color(1, 96, 176));    // 核心主色
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 128:
                    setBackground(new Color(0, 85, 155));    // 主色加深
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 256:
                    setBackground(new Color(0, 75, 140));    // 主色进一步加深
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 512:
                    setBackground(new Color(0, 65, 125));    // 深蓝
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 1024:
                    setBackground(new Color(0, 55, 110));    // 更深蓝
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 2048:
                    setBackground(new Color(0, 45, 95));     // 最深蓝
                    valueLabel.setForeground(Color.WHITE);
                    break;
                default:
                    setBackground(new Color(30, 30, 40));    // 暗蓝色
                    valueLabel.setForeground(Color.WHITE);
                    break;
            }
        }
    }
}