package com.syrnaxei.terminal2048;

import java.util.Scanner;

public class InputHandle {
    private Scanner scanner;
    private  MergeLogic mergeLogic;

    public InputHandle(MergeLogic mergeLogic){
        this.scanner = new Scanner(System.in); //scanner的this 好像加不加都行，这里加上好看
        this.mergeLogic = mergeLogic;
    }

    public void handle() {
        while(true){
            String input = scanner.next().toLowerCase(); //全部转化为小写
            switch(input){
                case GameConfig.RIGHT:
                    mergeLogic.mergeRight();
                    return;
                case GameConfig.LEFT:
                    mergeLogic.mergeLeft();
                    return;
                case GameConfig.UP:
                    mergeLogic.mergeUp();
                    return;
                case GameConfig.DOWN:
                    mergeLogic.mergeDown();
                    return;
                default :
                    System.out.println("20251204目前只写了左右功能，重新输入");
                    return;
            }
        }
    }
}
