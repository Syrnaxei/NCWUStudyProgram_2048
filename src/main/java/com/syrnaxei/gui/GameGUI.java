package com.syrnaxei.gui;

import com.syrnaxei.game2048.BoardControl;
import com.syrnaxei.game2048.GameConfig;
import com.syrnaxei.game2048.MergeLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class GameGUI extends JFrame {
    private final BoardControl board;
    private final MergeLogic mergeLogic;
    private TilePanel[][] tilePanels;
    private JLabel scoreLabel;

    ImageIcon gameIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon/game_icon.png")));
    ImageIcon infoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon/info_icon.png")));


    public GameGUI(BoardControl board, MergeLogic mergeLogic) {
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

        //分数标签
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(scoreLabel,BorderLayout.WEST);

        //refresh button
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        refreshPanel.setOpaque(false);
        JButton refreshButton = createRefreshButton();
        refreshPanel.add(refreshButton);
        topPanel.add(refreshPanel,BorderLayout.CENTER);


        //info Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton infoButton = createInfoButton();
        buttonPanel.setOpaque(false);
        buttonPanel.add(infoButton);
        topPanel.add(buttonPanel,BorderLayout.EAST);

        //add topPanel to mainPanel
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 创建游戏网格面板
        JPanel gridPanel = new JPanel(new GridLayout(GameConfig.BOARD_SIZE, GameConfig.BOARD_SIZE, 5, 5));
        gridPanel.setBackground(new Color(150, 170, 185)); //背景颜色
        gridPanel.setPreferredSize(new Dimension(400, 400));
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

        // 创建底部面板（说明）
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel instructionLabel = new JLabel("Use WASD or Arrow Keys to move tiles");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bottomPanel.add(instructionLabel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    //infoButton create fuc
    private JButton createInfoButton() {
        JButton infoButton = new JButton("i");
        infoButton.addActionListener(_ -> new InfoGUI(this, infoIcon)); // 调用 InfoGUI
        return infoButton;
    }

    //refreshButton creat fuc
    private JButton createRefreshButton() {
        JButton refreshBotton = new JButton("R");
        refreshBotton.addActionListener(_ ->{
            board.resetBoard();
            refreshBoard();
            this.requestFocusInWindow();
        });
        return refreshBotton;
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
            scoreLabel.setText("Score: " + score);

            // 更新方格显示
            for (int i = 0; i < GameConfig.BOARD_SIZE; i++) {
                for (int j = 0; j < GameConfig.BOARD_SIZE; j++) {
                    tilePanels[i][j].setValue(board[i][j]);
                }
            }
        });
    }

    private void checkGameOver() {
        if (board.isGameOver()) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + board.getScore());
                board.resetBoard();
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

            setBorder(BorderFactory.createRaisedBevelBorder());
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