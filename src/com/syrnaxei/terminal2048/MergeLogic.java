package com.syrnaxei.terminal2048;

public class MergeLogic {
    private BoardControl board;

    public MergeLogic(BoardControl board){
        this.board = board;
    }

    //====================================merge方法====================================
    public void mergeRight() {

    }

    public void mergeLeft() {

    }

    public void mergeDown() {

    }

    public void mergeUp() {

    }

    //====================================compact方法====================================
    public int[] compactRight(int[] row){
        int[] newRow = new int[GameConfig.BoardSize];
        int index = GameConfig.BoardSize - 1; // 从右侧开始填充
        for (int num : row) {
            if (num != 0) {
                newRow[index--] = num;
            }
        }
        return newRow;
    }

    public int[] compactLeft(int[] row){
        int[] newRow = new int[GameConfig.BoardSize];
        int index = 0;
        for(int num : row){
            if(num != 0){
                newRow[index++] = num;
            }
        }
        return newRow;
    }

    public int[] compactDown(int[] col) {
        int[] result = new int[GameConfig.BoardSize];
        int index = GameConfig.BoardSize - 1;
        for (int num : col) {
            if (num != 0) {
                result[index--] = num;
            }
        }
        return result;
    }

    public int[] compactUp(int[] col){
        int[] result = new int[GameConfig.BoardSize];
        int index = 0;
        for(int num : col){
            result[index++] = num;
        }
        return result;
    }
}
