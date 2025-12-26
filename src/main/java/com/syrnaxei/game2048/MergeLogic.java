package com.syrnaxei.game2048;

import java.util.Arrays;

public class MergeLogic {
    private final Board board;

    public MergeLogic(Board board){
        this.board = board;
    }

    //==================================  merge方法  ====================================
    public void mergeRight() {
        int[][] data = board.getBoard();
        for (int row = 0; row < GameConfig.BOARD_SIZE; row++) {
            int[] currRow = data[row];

            // Step1: 先紧凑（非0数字向右靠，消除中间0）
            int[] compacted = compactRight(currRow);

            // Step2: 再合并（从右往左遍历，合并相邻相同数字）
            int[] merged = adjacentRight(compacted);

            // Step3: 替换原行
            data[row] = merged;
        }
        board.setBoard(data);
    }

    public void mergeLeft() {
        int[][] data = board.getBoard();
        for(int row = 0; row < GameConfig.BOARD_SIZE; row++){
            int[] currRow = data[row];
            int[] compacted = compactLeft(currRow);
            int[] merged = adjacentLeft(compacted);
            data[row] = merged;
        }
        board.setBoard(data);
    }

    public void mergeDown() {
        int[][] data = board.getBoard();
        for (int col = 0; col < GameConfig.BOARD_SIZE; col++){
            // 提取当前列
            int[] tempCol = extractCol(data,col);
            int[] compactedCol = compactDown(tempCol); // 向下紧凑
            int[] mergedCol = adjacentDown(compactedCol); // 向下合并
            // 写回列
            writeColumn(data, mergedCol, col);
        }
        board.setBoard(data);
    }

    public void mergeUp() {
        int[][] data = board.getBoard();
        for(int col = 0;col < GameConfig.BOARD_SIZE;col++){
            int[] tempCol = extractCol(data,col);
            int[] compactedCol = compactUp(tempCol);
            int[] mergedCol = adjacentUp(compactedCol);
            writeColumn(data,mergedCol,col);
        }
        board.setBoard(data);
    }

    //==================================  adjacent方法  =================================
    public int[] adjacentRight(int[] compactedRow) {
        int[] newRow = new int[GameConfig.BOARD_SIZE];
        int index = GameConfig.BOARD_SIZE - 1;
        int i = GameConfig.BOARD_SIZE - 1;

        while(i >= 0){
            int tempNum = compactedRow[i];
            if(tempNum == 0){
                i--;
                continue;
            }
            if(i - 1 >= 0 && tempNum == compactedRow[i - 1]){
                newRow[index] = tempNum * 2;
                board.setScore(tempNum * 2);
                index--;
                i -= 2;
            }else{
                newRow[index] = tempNum;
                index--;
                i--;
            }
        }

        return newRow;
    }

    public int[] adjacentLeft(int[] compactedRow){
        int[] newRow = new int[GameConfig.BOARD_SIZE];
        int index = 0;
        int i = 0;

        while(i < GameConfig.BOARD_SIZE){
            int tempNum = compactedRow[i];
            if(tempNum == 0){
                i++;
                continue;
            }
            if(i + 1 < GameConfig.BOARD_SIZE && tempNum == compactedRow[i + 1]){
                newRow[index] = tempNum * 2;
                board.setScore(tempNum * 2);
                index++;
                i += 2;
            }else{
                newRow[index] = tempNum;
                index++;
                i++;
            }
        }
        return newRow;
    }

    public int[] adjacentDown(int[] compactedCol){
        int[] newCol = new int[GameConfig.BOARD_SIZE];
        int index = GameConfig.BOARD_SIZE - 1;
        int i = GameConfig.BOARD_SIZE - 1;

        while (i >= 0) {
            int tempNum = compactedCol[i];
            if (tempNum == 0) {
                i--;
                continue;
            }
            if (i - 1 >= 0 &&  tempNum == compactedCol[i - 1]) {
                newCol[index] = tempNum * 2;
                board.setScore(tempNum * 2);
                index--;
                i -= 2;
            } else {
                newCol[index] = tempNum;
                index--;
                i -= 1;
            }
        }
        return newCol;
    }

    public int[] adjacentUp(int[] compactedCol){
        int[] newCol = new int[GameConfig.BOARD_SIZE];
        int index = 0;
        int i = 0;

        while (i < GameConfig.BOARD_SIZE) {
            int tempNum = compactedCol[i];
            if (tempNum == 0) {
                i++;
                continue;
            }
            if (i + 1 < GameConfig.BOARD_SIZE &&  tempNum == compactedCol[i + 1]) {
                newCol[index] = tempNum * 2;
                board.setScore(tempNum * 2);
                index++;
                i += 2;
            } else {
                newCol[index] = tempNum;
                index++;
                i += 1;
            }
        }
        return newCol;
    }

    //==================================  compact方法  ==================================
    private int[] compactRight(int[] row){
        int[] newRow = new int[GameConfig.BOARD_SIZE];
        int index = GameConfig.BOARD_SIZE - 1; // 从右侧开始填充
        for (int i = GameConfig.BOARD_SIZE - 1;i >= 0;i--) {
            if (row[i] != 0) {
                newRow[index--] = row[i];
            }
        }
        return newRow;
    }

    private int[] compactLeft(int[] row){
        int[] newRow = new int[GameConfig.BOARD_SIZE];
        int index = 0;
        for(int num : row){
            if(num != 0){
                newRow[index++] = num;
            }
        }
        return newRow;
    }

    private int[] compactDown(int[] col){
        int[] newCol = new int[GameConfig.BOARD_SIZE];
        int index = GameConfig.BOARD_SIZE - 1;
        for (int i = GameConfig.BOARD_SIZE - 1;i >= 0;i--) {
            if (col[i] != 0) {
                newCol[index--] = col[i];
            }
        }
        return newCol;
}

    private int[] compactUp(int[] col){
        int[] newCol = new int[GameConfig.BOARD_SIZE];
        int index = 0;
        for (int num : col) {
            if (num != 0) {
                newCol[index++] = num;
            }
        }
        return newCol;
    }

    //===========================  UP && DOWNs extra works  ===========================
    private int[] extractCol(int[][] data,int col){
        int[] extractedcol = new int[GameConfig.BOARD_SIZE];
        for(int row = 0;row < GameConfig.BOARD_SIZE;row++){
            extractedcol[row] = data[row][col];
        }
        return extractedcol;
    }

    private void writeColumn(int[][] data,int[] newCol,int col){
        for(int row = 0;row < GameConfig.BOARD_SIZE;row++){
            data[row][col] = newCol[row];
        }
    }

    //====================================  测试 函数  ====================================
    public void testfill() {
        int[][] data = board.getBoard();

        // 遍历所有行和列
        for (int row = 0; row < GameConfig.BOARD_SIZE; row++) {
            for (int col = 0; col < GameConfig.BOARD_SIZE; col++) {
                // 隔一行（row%2 == 0）且 隔一列（col%2 == 0）
                if (row % 2 == 0 && col % 2 == 0) {
                    data[row][col] = 3; // 填充3
                } else {
                    data[row][col] = 0; // 其他位置置0
                }
            }
            System.out.println("行" + row + "填充后：" + Arrays.toString(data[row]));
        }

        board.setBoard(data);
    }

    public void testMergeRight() {
        int[][] data = board.getBoard();
        System.out.println("\n=== 执行向右合并 ===");
        for (int row = 0; row < GameConfig.BOARD_SIZE; row++) {
            int[] currRow = data[row];
            System.out.println("原行" + row + "：" + Arrays.toString(currRow));

            int[] compacted = compactRight(currRow);
            System.out.println("行" + row + "紧凑后：" + Arrays.toString(compacted));

            int[] merged = adjacentRight(compacted);
            System.out.println("行" + row + "合并后：" + Arrays.toString(merged));

            data[row] = merged;
        }
        board.setBoard(data);
        System.out.println("=== 向右合并结束 ===");
    }

    public void testMergeLeft() {
        int[][] data = board.getBoard();
        System.out.println("\n=== 执行向左合并 ===");
        for (int row = 0; row < GameConfig.BOARD_SIZE; row++) {
            int[] currRow = data[row];
            System.out.println("原行" + row + "：" + Arrays.toString(currRow));

            int[] compacted = compactLeft(currRow);
            System.out.println("行" + row + "紧凑后：" + Arrays.toString(compacted));

            int[] merged = adjacentLeft(compacted);
            System.out.println("行" + row + "合并后：" + Arrays.toString(merged));

            data[row] = merged;
        }
        board.setBoard(data);
        System.out.println("=== 向左合并结束 ===");
    }

    public void testMergeDown() {
        int[][] data = board.getBoard();
        System.out.println("\n=== 执行向下合并 ===");
        for (int col = 0; col < GameConfig.BOARD_SIZE; col++) {
            int[] tempCol = extractCol(data, col);
            System.out.println("原列" + col + "：" + Arrays.toString(tempCol));

            int[] compactedCol = compactDown(tempCol);
            System.out.println("列" + col + "紧凑后：" + Arrays.toString(compactedCol));

            int[] mergedCol = adjacentDown(compactedCol);
            System.out.println("列" + col + "合并后：" + Arrays.toString(mergedCol));

            writeColumn(data, mergedCol, col);
        }
        board.setBoard(data);
        System.out.println("=== 向下合并结束 ===");
    }

    public void testMergeUp() {
        int[][] data = board.getBoard();
        System.out.println("\n=== 执行向上合并 ===");
        for (int col = 0; col < GameConfig.BOARD_SIZE; col++) {
            int[] tempCol = extractCol(data, col);
            System.out.println("原列" + col + "：" + Arrays.toString(tempCol));

            int[] compactedCol = compactUp(tempCol);
            System.out.println("列" + col + "紧凑后：" + Arrays.toString(compactedCol));

            int[] mergedCol = adjacentUp(compactedCol);
            System.out.println("列" + col + "合并后：" + Arrays.toString(mergedCol));

            writeColumn(data, mergedCol, col);
        }
        board.setBoard(data);
        System.out.println("=== 向上合并结束 ===");
    }
}
