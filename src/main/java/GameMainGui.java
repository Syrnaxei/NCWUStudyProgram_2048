import com.syrnaxei.game2048.BoardControl;
import com.syrnaxei.game2048.GameConfig;
import com.syrnaxei.game2048.MergeLogic;
import com.syrnaxei.gui.GameGUI;

import javax.swing.*;

public class GameMainGui {
    public static void main(String[] args) {
        // 在事件调度线程中启动GUI应用
        SwingUtilities.invokeLater(() -> {
            GameConfig.loadConfig();
            BoardControl board = new BoardControl();
            MergeLogic mergeLogic = new MergeLogic(board);  //逻辑类需要操控棋盘，传棋盘对象进去
            board.createBoard();

            // 创建并显示游戏窗口
            GameGUI gameGameGUI = new GameGUI(board, mergeLogic);
            gameGameGUI.setVisible(true);
        });
    }
}