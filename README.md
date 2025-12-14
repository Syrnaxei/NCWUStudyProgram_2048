# 2048 游戏 - Java Swing 实现

旨在完成我的期末作业

---

##  快速开始

### 1. 克隆仓库
```bash
git clone https://github.com/Syrnaxei/NCWUStudyProject_2048.git
cd NCWUStudyProject_2048
```
### 2. 运行游戏

```bash
javac -cp src/main/java src/main/java/com/syrnaxei/game2048/GameMainGui.java
java -cp src/main/java GameMainGui
```
## 程序结构

```
NCWUStudyProject_2048
└── src
    └── main
        ├── java
        │   ├── com
        │   │   └── syrnaxei
        │   │       └── game2048
        │   │           ├── BoardConfig.java       # 棋盘配置
        │   │           ├── BoardControl.java      # 棋盘控制
        │   │           ├── GameMainTerminal.java  # 测试入口
        │   │           ├── InputHandle.java       # 输入处理
        │   │           ├── MergeLogic.java        # 数字合并逻辑
        │   │           └── gui
        │   │               ├── GameGUI.java        # 游戏GUI核心
        │   │               └── InfoGUI.java        # 信息展示GUI
        │   └── GameMainGui.java                    # 游戏入口
        │
        └── resources
            ├── images
            │   ├── 2048
            │   │   ├── github-mark.png
            │   │   ├── logo_ncwu.png
            │   │   └── ncwu_emblem.png
            │   └── icon
            │       ├── game_icon.png
            │       └── info_icon.png
            └── member
                ├── liev.png
                ├── syrnaxei.png
                └── wang.png
```
##  开发环境
 jdk25
