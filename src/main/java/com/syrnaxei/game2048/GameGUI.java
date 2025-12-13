package com.syrnaxei.game2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.Objects;

public class GameGUI extends JFrame {
    private BoardControl boardControl;
    private MergeLogic mergeLogic;
    private TilePanel[][] tilePanels;
    private JLabel scoreLabel;

    ImageIcon gameIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon/game_icon.png")));
    ImageIcon infoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon/info_icon.png")));
    ImageIcon ncwuLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/2048/logo_ncwu.png")));
    Image githubLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/2048/github-mark.png"))).getImage().getScaledInstance(65,65,Image.SCALE_SMOOTH);
    Image ncwuEmblem = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/2048/ncwu_emblem.png"))).getImage().getScaledInstance(65,65,Image.SCALE_SMOOTH);

    public GameGUI(BoardControl boardControl, MergeLogic mergeLogic) {
        this.boardControl = boardControl;
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

        //info Button
        JButton infoButton = createInfoButton();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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

    //infoButton achievement Part
    private JButton createInfoButton() {
        JButton infoButton = new JButton("i");

        infoButton.addActionListener(_ -> showInfoWindow());
        return infoButton;
    }

    private void showInfoWindow() {
        JFrame infoFrame = new JFrame("About this program");

        infoFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        infoFrame.setIconImage(infoIcon.getImage());
        infoFrame.setSize(new Dimension(500,600));
        infoFrame.setResizable(false);
        infoFrame.setLocationRelativeTo(this);
        infoFrame.setLayout(new BorderLayout());

        //=====  top area of the info window  =====
        JPanel ncwuPicPanel = new JPanel(new BorderLayout());
        ncwuPicPanel.setBackground(new Color(100,160,205));
        ncwuPicPanel.setPreferredSize(new Dimension(500,100)); //the high of top-part
        //Ncwu picture part
        JLabel ncwuPicLabel = new JLabel();
        ncwuPicLabel.setIcon(ncwuLogo);

        ncwuPicPanel.add(ncwuPicLabel,BorderLayout.CENTER);
        infoFrame.add(ncwuPicPanel,BorderLayout.NORTH);

        //=====  mid-area of the info window  =====
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setPreferredSize(new Dimension(500,400)); //the high of mid-part

        //text Part
        JTextArea textArea = new JTextArea("\n    This is a Java learning program, designed to help  me complete my final Java assignment " +
                "by  implementing the classic 2048 game with a GUI.\n\n" +
                " Special thanks to Liew for his GUI assistance\n\n" +
                "                        Developed by NCWU java study team\n" +
                "                                 2025.12.13 22:34 program done\n");
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textArea.setLineWrap(true); //自动换行
        textArea.setWrapStyleWord(true); //单词边界换行
        textArea.setEditable(false);
        textArea.setBackground(new Color(180, 200, 215));

        textPanel.add(textArea,BorderLayout.CENTER);
        infoFrame.add(textPanel,BorderLayout.CENTER);

        //===== end area of the info window  =====
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,60,20));
        linkPanel.setBackground(new Color(100,160,205));
        linkPanel.setPreferredSize(new Dimension(500,100));

        JLabel link1 = createLinkLabel(githubLogo,"https://github.com/Syrnaxei/NCWUStudyProject_2048");
        JLabel link2 = createLinkLabel(ncwuEmblem,"https://www.ncwu.edu.cn/");

        linkPanel.add(link1);
        linkPanel.add(link2);

        infoFrame.add(linkPanel,BorderLayout.SOUTH);


        //wtf!!  lost target!!!!!!!!!!!
        infoFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // 主窗口重新请求焦点
                GameGUI.this.requestFocusInWindow();
                // 确保主窗口可聚焦
                GameGUI.this.setFocusable(true);
                GameGUI.this.toFront(); // 主窗口置顶
            }
        });

        infoFrame.setVisible(true);
    }

    private JLabel createLinkLabel(Image image,String url) {
        JLabel label = new JLabel();

        label.setIcon(new ImageIcon(image));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(GameGUI.this, "Failed to open link: " + ex.getMessage());
                }
            }
        });

        return label;
    }


    private void setupKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean moved = false;
                int[][] boardBefore = copyBoard(boardControl.getBoard());
                
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        mergeLogic.mergeUp();
                        moved = !boardsEqual(boardBefore, boardControl.getBoard());
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        mergeLogic.mergeDown();
                        moved = !boardsEqual(boardBefore, boardControl.getBoard());
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        mergeLogic.mergeLeft();
                        moved = !boardsEqual(boardBefore, boardControl.getBoard());
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        mergeLogic.mergeRight();
                        moved = !boardsEqual(boardBefore, boardControl.getBoard());
                        break;
                }
                
                if (moved) {
                    boardControl.addNumber();
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
        int[][] board = boardControl.getBoard();
        int score = boardControl.getScore();
        
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
        if (boardControl.isGameOver()) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + boardControl.getScore());
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
                    setBackground(new Color(0, 45, 95));     // 最深蓝（主色极致版）
                    valueLabel.setForeground(Color.WHITE);
                    break;
                default:
                    setBackground(new Color(30, 30, 40));    // 暗蓝色（替代原灰色）
                    valueLabel.setForeground(Color.WHITE);
                    break;
            }
        }
    }
}