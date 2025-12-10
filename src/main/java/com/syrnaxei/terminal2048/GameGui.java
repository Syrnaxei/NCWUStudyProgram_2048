package com.syrnaxei.terminal2048;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

class NcwuLogo extends JLabel {
    public NcwuLogo() {
        setHorizontalAlignment(JLabel.LEFT); // 文字/图片居中
        setVerticalAlignment(JLabel.TOP);
        setIcon(new ImageIcon(Objects.requireNonNull(GameGui.class.getResource("/images/2048/NCWU_Logo.png"))));
        setBackground(new Color(129,216,207));
        setOpaque(true);
        setBounds(0,0,960,80);
    }
}

public class GameGui extends JFrame {
    public GameGui() {

        NcwuLogo ncwuLogo = new NcwuLogo();
        setTitle("2048 Game");
        setSize(960, 540);
        Container contentPane = getContentPane();
        contentPane.setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(ncwuLogo);
        setLayout(null);
        setResizable(false);
        setVisible(true);

    }
}
