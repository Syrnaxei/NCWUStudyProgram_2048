package com.syrnaxei.terminal2048;

import java.util.Random;

public class BoardControl {
    private int[][] board;
    Random random = new Random();

    //创建棋盘方法
    public void CreateBoard() {
        board = new int[GameConfig.BoardSize][GameConfig.BoardSize];
        AddNumber();
        AddNumber();
    }

    //添加数字方法
    public void AddNumber() {
        int row,col;
        //random到的坐标如果没数字（0）结束循环
        do{
            row = random.nextInt(GameConfig.BoardSize);
            col = random.nextInt(GameConfig.BoardSize);
        }while(board[row][col]!=0);
        //随机函数生成0-100的数，大于SFP（生成4的概率数字20）即百分之八十概率生成2
        if(random.nextInt(100)>GameConfig.S_Four_P){
            board[row][col] = 2;
        }else{
            board[row][col] = 4;
        }
    }

    //20251204测试函数
    public void testfunc() {
        System.out.println("\n======2048  Game======");
        for (int i = 0; i < GameConfig.BoardSize; i++) {
            System.out.print("|");
            for (int j = 0; j < GameConfig.BoardSize; j++) {
                String content = board[i][j] == 0 ? "-" : String.valueOf(board[i][j]);
                System.out.printf("%4s|", content); // 右对齐，占4位
            }
            System.out.println();
        }
        System.out.println("======================");
    }

    //判断游戏结束方法
    public boolean isGameOver() {
        //检查棋盘上是否有空位
        for(int i = 0;i < GameConfig.BoardSize;i++){
            for(int j = 0;j < GameConfig.BoardSize;j++){
                if(board[i][j] == 0){
                    return false;
                }
            }
        }
        //检查棋盘横向是否有相同的可合并的数字
        for(int i = 0;i < GameConfig.BoardSize;i++){
            for(int j = 0;j < GameConfig.BoardSize - 1;j++){
                if(board[i][j] == board[i][j+1]){
                    return false;
                }
            }
        }
        //检查棋盘纵向是否有相同的可合并的数字
        for(int i = 0;i < GameConfig.BoardSize - 1;i++){
            for(int j = 0;j < GameConfig.BoardSize;j++){
                if(board[i][j] == board[i+1][j]){
                    return false;
                }
            }
        }
        return true;
    }
    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board){
        this.board = board;
    }
}
