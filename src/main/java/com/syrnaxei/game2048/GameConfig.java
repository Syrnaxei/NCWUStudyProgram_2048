package com.syrnaxei.game2048;

import java.io.*;
import java.util.Properties;

public class GameConfig {
    //====================================  固定方向键  ====================================
    public static final String UP = "w";
    public static final String DOWN = "s";
    public static final String LEFT = "a";
    public static final String RIGHT = "d";

    //====================================  静态 常量  ====================================
    public static int BOARD_SIZE = 4;
    public static int S_FOUR_P = 10;
    public static boolean isTestMode = false;
    public static int windowWidth = 400;
    public static int windowHeight = 400;

    //=====================================  最高分  =====================================
    public static int bestScore = 0;

    private static final String CONFIG_FILE = "config.properties";   // 仅用于 BOARD_SIZE / S_FOUR_P / isTestMode
    private static final String SCORE_FILE = "score.properties";     // 仅用于 bestScore

    //====================================  加载 配置  ====================================
    public static void loadConfig() {
        // 1. 加载 config.properties（仅读取非方向键配置）
        Properties configProps = new Properties();
        InputStream input = GameConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE);

        File externalConfig = new File(CONFIG_FILE);
        if (externalConfig.exists()) {
            try {
                input = new FileInputStream(externalConfig);
            } catch (FileNotFoundException ignored) {}
        }

        if (input != null) {
            try {
                configProps.load(input);
                BOARD_SIZE = getIntProperty(configProps, "BOARD_SIZE", 4);
                S_FOUR_P = getIntProperty(configProps, "S_FOUR_P", 10);
                isTestMode = getBooleanProperty(configProps, "isTestMode", false);
            } catch (IOException e) {
                System.err.println("加载 config.properties 失败: " + e.getMessage());
            } finally {
                try {
                    input.close();
                } catch (IOException ignored) {}
            }
        } else {
            if(isTestMode){
                System.out.println("未找到 config.properties，使用默认游戏设置");
            }
        }

        //checkWindowSize();

        // 2. 加载 score.properties
        File scoreFile = new File(SCORE_FILE);
        if (scoreFile.exists()) {
            Properties scoreProps = new Properties();
            try (FileInputStream in = new FileInputStream(scoreFile)) {
                scoreProps.load(in);
                bestScore = getIntProperty(scoreProps, "bestScore", 0);
            } catch (IOException e) {
                System.err.println("加载 score.properties 失败，使用默认最高分 0: " + e.getMessage());
            }
        } else {
            System.out.println("未找到 score.properties，最高分初始化为 0");
        }
    }

    //====================================  保存最高分  ====================================
    public static void saveBestScore() {
        Properties props = new Properties();
        File scoreFile = new File(SCORE_FILE);

        // 如果已存在，先读取
        if (scoreFile.exists()) {
            try (FileInputStream in = new FileInputStream(scoreFile)) {
                props.load(in);
            } catch (IOException e) {
                System.err.println("读取 score.properties 失败，将覆盖创建: " + e.getMessage());
            }
        }

        props.setProperty("bestScore", String.valueOf(bestScore));

        try (FileOutputStream out = new FileOutputStream(scoreFile)) {
            props.store(out, "Game2048 - 最高分自动保存");
        } catch (IOException e) {
            System.err.println("无法保存最高分到 score.properties: " + e.getMessage());
        }
    }

    //====================================  辅助 方法  ====================================
    private static int getIntProperty(Properties props, String key, int defaultValue) {
        String val = props.getProperty(key);
        if (val == null || val.trim().isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(val.trim());
        } catch (NumberFormatException e) {
            System.err.println("无效整数: " + key + "=" + val + ", 使用默认值 " + defaultValue);
            return defaultValue;
        }
    }

    private static boolean getBooleanProperty(Properties props, String key, boolean defaultValue) {
        String val = props.getProperty(key);
        if (val == null || val.trim().isEmpty()) return defaultValue;
        return Boolean.parseBoolean(val.trim());
    }
}