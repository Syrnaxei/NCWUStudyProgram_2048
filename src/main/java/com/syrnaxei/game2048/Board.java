package com.syrnaxei.game2048;

import java.util.Random;

public class Board {
    private int[][] board;
    private int score = 0;
    Random random = new Random();

    //===================================  创建棋盘 方法  ===================================
    public void createBoard() {
        board = new int[GameConfig.BOARD_SIZE][GameConfig.BOARD_SIZE];
        addNumber();
        addNumber();
    }

    //===================================  添加数字 方法  ===================================
    public void addNumber() {
        int row,col;
        if(!hasEmptyLocation()){
            return;
        }
        //random到的坐标如果没数字（0）结束循环
        do{
            row = random.nextInt(GameConfig.BOARD_SIZE);
            col = random.nextInt(GameConfig.BOARD_SIZE);
        }while(board[row][col]!=0);
        //随机函数生成0-100的数，大于SFP（生成4的概率数字20）即百分之八十概率生成2
        if(random.nextInt(100)>GameConfig.S_FOUR_P){
            board[row][col] = 2;
        }else{
            board[row][col] = 4;
        }
    }

    public boolean hasEmptyLocation() {
        for(int[] row : board){
            for(int num : row){
                if(num == 0){
                    return true;
                }
            }
        }
        return false;
    }

    //====================================  打印 方法  ====================================
    public void printfBoard() {
        if(GameConfig.isTestMode) {
            System.out.println("\n===== TEST  MODE =====       分数：" + score);
        }else{
            System.out.println("\n===== 2048  Game =====       分数：" + score);
        }
        for (int i = 0; i < GameConfig.BOARD_SIZE; i++) {
            System.out.print("|");
            for (int j = 0; j < GameConfig.BOARD_SIZE; j++) {
                String content = board[i][j] == 0 ? "-" : String.valueOf(board[i][j]);
                System.out.printf("%4s|", content); // 右对齐，占4位
            }
            System.out.println();
        }
        System.out.println("======================");
    }

    //====================================  计分 方法  ====================================
    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score += score;
    }

    //===================================  游戏失败 方法  ===================================
    public boolean isGameOver() {
        //检查棋盘上是否有空位
        for(int i = 0; i < GameConfig.BOARD_SIZE; i++){
            for(int j = 0; j < GameConfig.BOARD_SIZE; j++){
                if(board[i][j] == 0){
                    return false;
                }
            }
        }
        //检查棋盘横向是否有相同的可合并的数字
        for(int i = 0; i < GameConfig.BOARD_SIZE; i++){
            for(int j = 0; j < GameConfig.BOARD_SIZE - 1; j++){
                if(board[i][j] == board[i][j+1]){
                    return false;
                }
            }
        }
        //检查棋盘纵向是否有相同的可合并的数字
        for(int i = 0; i < GameConfig.BOARD_SIZE - 1; i++){
            for(int j = 0; j < GameConfig.BOARD_SIZE; j++){
                if(board[i][j] == board[i+1][j]){
                    return false;
                }
            }
        }
        return true;
    }

    //===================================  游戏胜利 方法  ===================================
    public boolean isGameWin() {
        for(int i = 0; i < GameConfig.BOARD_SIZE; i++){
            for(int j = 0; j < GameConfig.BOARD_SIZE; j++){
                if(board[i][j] >= 2048){
                    return true;
                }
            }
        }
        return false;
    }

    //===================================  棋盘调用 方法  ===================================
    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board){
        this.board = board;
    }

    public void resetBoard() {
        board = new int[GameConfig.BOARD_SIZE][GameConfig.BOARD_SIZE];
        score = 0;
        addNumber();
        addNumber();
    }

    //====================================  作弊 方法  ====================================
    public void cheat() {
        String input = javax.swing.JOptionPane.showInputDialog("请输入行数 列数 数字（用空格分隔）:");
        if(input != null && !input.trim().isEmpty()) {
            String[] parts = input.split(" ");
            if(parts.length >= 3) {
                try {
                    int row = Integer.parseInt(parts[0]);
                    int col = Integer.parseInt(parts[1]);
                    int num = Integer.parseInt(parts[2]);
                    if(row >= 0 && row < GameConfig.BOARD_SIZE && col >= 0 && col < GameConfig.BOARD_SIZE) {
                        board[row][col] = num;
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "行列数超出范围!");
                    }
                } catch(NumberFormatException e) {
                    javax.swing.JOptionPane.showMessageDialog(null, "输入格式错误!");
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "输入参数不足!");
            }
        }
    }
}
