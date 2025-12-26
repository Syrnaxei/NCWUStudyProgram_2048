package com.syrnaxei.game2048;

public class GameMainTerminal {
    public static void main(String[] args) {
        //==================================  棋盘初始化  ==================================
        Board board = new Board();
        MergeLogic mergeLogic = new MergeLogic(board);  //逻辑类需要操控棋盘，传棋盘对象进去
        InputHandle inputHandle = new InputHandle(mergeLogic);  //输入类需要操控逻辑模块，传逻辑类对象进去
        board.createBoard();
        //=================================  棋盘 逻辑循环  =================================
        while(true){
            board.printfBoard();
            if(board.isGameOver()){
                System.out.println("游戏结束，得分为：" + board.getScore());
                break;
            }
            inputHandle.handle();
            board.addNumber();
        }
    }
}