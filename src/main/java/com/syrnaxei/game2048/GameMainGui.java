package com.syrnaxei.game2048;

import com.syrnaxei.game2048.controller.Board;
import com.syrnaxei.game2048.model.GameConfig;
import com.syrnaxei.game2048.model.MergeLogic;
import com.syrnaxei.game2048.gui.GameGUI;

import javax.swing.*;

public class GameMainGui {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameConfig.loadConfig();
            Board board = new Board();
            MergeLogic mergeLogic = new MergeLogic(board);
            board.createBoard();

            // 创建并显示游戏窗口
            GameGUI gameGameGUI = new GameGUI(board, mergeLogic);
            gameGameGUI.setVisible(true);
        });}

}