package com.syrnaxei.game2048;

import java.util.Scanner;

public class InputHandle {
    private final Scanner scanner;
    private final MergeLogic mergeLogic;

    public InputHandle(MergeLogic mergeLogic) {
        this.scanner = new Scanner(System.in);
        this.mergeLogic = mergeLogic;
    }

    public void handle() {
        String input = scanner.next().toLowerCase(); //全部转化为小写
        if (GameConfig.isTestMode) {
            switch (input) {
                case GameConfig.RIGHT:
                    mergeLogic.testMergeRight();
                    return;
                case GameConfig.LEFT:
                    mergeLogic.testMergeLeft();
                    return;
                case GameConfig.UP:
                    mergeLogic.testMergeUp();
                    return;
                case GameConfig.DOWN:
                    mergeLogic.testMergeDown();
                    return;
                case "fill":
                        mergeLogic.testfill();
                        return;
                default:
                    System.out.println("重新输入");
            }
        } else {
            switch (input) {
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
                default:
                    System.out.println("重新输入");
            }
        }
    }
}