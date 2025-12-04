package com.syrnaxei.terminal2048;

public class Main {
    public static void main() {
        BoardControl board = new BoardControl();
        MergeLogic mergeLogic = new MergeLogic(board);  //逻辑类需要操控棋盘，传棋盘对象进去
        InputHandle inputHandle = new InputHandle(mergeLogic);  //输入类需要操控逻辑模块，穿逻辑类对象进去
        board.CreateBoard();
        while(true){
            board.testfunc(); //后面方法改名printboard;
            if(board.isGameOver()){
                System.out.println("GameOver"); //考虑后续写个记分
            }
            inputHandle.handle();
            board.AddNumber();
        }
    }

}
