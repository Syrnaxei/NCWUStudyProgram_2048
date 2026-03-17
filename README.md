# NCWUStudyProgram_2048 运行指南

## 1. 软件概述

基于 Java Swing 实现的 2048 游戏。

## 2. 环境准备

在运行本项目之前，请确保您的系统已安装 Java Development Kit (JDK)。根据项目 README，建议使用 JDK 25。您可以通过以下命令检查 JDK 版本：

```bash
java -version
```

如果未安装或版本不符，请访问 Oracle 官网或您操作系统的包管理器进行安装和配置。

## 3. 克隆仓库

首先，您需要将 GitHub 仓库克隆到本地。打开您的终端或命令行工具，执行以下命令：

```bash
git clone https://github.com/Syrnaxei/NCWUStudyProgram_2048.git
```

克隆完成后，进入项目目录：

```bash
cd NCWUStudyProgram_2048\src\main\java
```

## 4. 编译与运行

本项目使用 Java 编写，需要先编译源代码，然后才能运行。

### 4.1. 编译源代码

使用 `javac` 命令编译 `GameMainGui.java` 文件。

```bash
javac -cp . com\syrnaxei\game2048\GameMainGui.java
```

### 4.2. 运行游戏

编译成功后，使用 `java` 命令运行游戏。

```bash
java -cp .;../../resources com.syrnaxei.game2048.GameMainGui
```

## 5. 程序结构

项目的核心文件结构如下所示：

```
NCWUStudyProgram_2048
└── src
    └── main
        ├── java
        │   └── com
        │       └── syrnaxei
        │           └── game2048
        │               ├── controller
        │               │   └── ...
        │               ├── gui
        │               │   └── ...
        │               ├── model
        │               │   └── ...
        │               ├── GameMainGui.java       # 游戏入口
        │               └── GameMainTest.java      # 测试入口 
        │
        └── resources
            ├── images
            │   └── ... (图片资源)
            └── member
                └── ... (成员图片)
```

---
