package com.syrnaxei.terminal2048;

public class MergeLogic {
    private BoardControl board;

    public MergeLogic(BoardControl board){
        this.board = board;
    }

    /*合并逻辑不会写怎么办
    如果操作向右，先将每一行的元素遍历，从数组右边开始遍历，
    step1: 如果元素为0，则结束当前循环开始下一次循环，
    step2: 如果元素不为0，保存元素在临时变量里面，检查后续元素有没有相等的，则将二者相加（或x2）并放置到第二个元素的位置，
    如果不相等，则继续遍历直到有相等的；
    四行执行完上述操作之后，开始从右侧遍历数组，填充为0的部分（空位置）
     */
    public void mergeRight() {
        int[][] data = board.getBoard();
        for(int row = 0;row < GameConfig.BoardSize;row++){
            int[] currRow = data[row];
            int[] newrow = new int[GameConfig.BoardSize];
            int newRowIndex = GameConfig.BoardSize - 1;
            int i = GameConfig.BoardSize - 1;
            while(i >= 0){
                //step1: 保存元素在临时变量里面，如果元素为0，则结束当前循环开始下一次循环，
                int tempNum = currRow[i];
                if(tempNum == 0){
                    i--;
                    continue;
                }
                /*step2： 如果元素不为0，检查后续元素有没有相等的，则将二者相加（或x2）并放置到新的row数组的开始位
                需要注意如果step1过了，但是后面没有可以合并的元素,需要将不能合并的元素一并放置到新的row数组里面
                 */
                if(i - 1>=0 && tempNum == currRow[i-1]){
                    newrow[newRowIndex] = tempNum * 2;
                    newRowIndex--;
                    i -= 2;
                }else{
                    newrow[newRowIndex] = tempNum;
                    newRowIndex--;
                    i--;
                }
            }//while
            data[row] = newrow;
        }//for
        board.setBoard(data);
    }//MergeRight
}
