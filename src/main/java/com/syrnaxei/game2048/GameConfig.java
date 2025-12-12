package com.syrnaxei.game2048;

public class GameConfig {
    //====================================  静态 变量  ====================================
    public static final int BOARD_SIZE = 4; //棋盘大小
    public static final int S_FOUR_P = 10; //生成4的概率，设为20%

    //====================================  按键 映射  ====================================
    public static final String UP = "w";
    public static final  String DOWN = "s";
    public static final String LEFT = "a";
    public static final String RIGHT = "d";

    //====================================  其他 配置  ====================================
    public static boolean isTestMode = false; //是否处在测试模式
}
