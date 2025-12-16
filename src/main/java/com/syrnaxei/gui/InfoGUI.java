package com.syrnaxei.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.util.Objects;

public class InfoGUI extends JFrame {
    // 信息窗口需要的资源
    ImageIcon ncwuLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/2048/logo_ncwu.png")));
    Image githubLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/2048/github-mark.png"))).getImage().getScaledInstance(65,65,Image.SCALE_SMOOTH);
    Image ncwuEmblem = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/2048/ncwu_emblem.png"))).getImage().getScaledInstance(65,65,Image.SCALE_SMOOTH);


    // 构造方法：接收主窗口和图标
    public InfoGUI(GameGUI mainFrame, ImageIcon infoIcon) {
        initializeInfoWindow(mainFrame, infoIcon);
    }


    private void initializeInfoWindow(GameGUI mainFrame, ImageIcon infoIcon) {
        setTitle("About this program");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIconImage(infoIcon.getImage());
        setSize(new Dimension(500,600));
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        setLayout(new BorderLayout());

        //=====  top area of the info window  =====
        JPanel ncwuPicPanel = new JPanel(new BorderLayout());
        ncwuPicPanel.setBackground(new Color(100,160,205));
        ncwuPicPanel.setPreferredSize(new Dimension(500,100)); //the high of top-part
        //Ncwu picture part
        JLabel ncwuPicLabel = new JLabel();
        ncwuPicLabel.setIcon(ncwuLogo);

        ncwuPicPanel.add(ncwuPicLabel,BorderLayout.CENTER);
        add(ncwuPicPanel,BorderLayout.NORTH);

        //=====  mid-area of the info window  =====
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // 垂直布局
        textPanel.setPreferredSize(new Dimension(500, 400));
        textPanel.setBackground(new Color(180, 200, 215));

        // 标题
        JLabel titleLabel = new JLabel("关于");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 26));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 0)); // 上下边距
        textPanel.add(titleLabel);

        textPanel.add(createPersonItem("/images/member/syrnaxei.png", "Syrnaxei","FRAME & LOGIC"));
        textPanel.add(createPersonItem("/images/member/liew.png", "LiewYoung","GUI Support"));
        textPanel.add(createPersonItem("/images/member/wang.png", "王兵","PPT & Other"));

        add(textPanel, BorderLayout.CENTER);

        //===== end area of the info window  =====
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,80,10));
        linkPanel.setBackground(new Color(100,160,205));
        linkPanel.setPreferredSize(new Dimension(500,80));

        JLabel link1 = createLinkLabel(githubLogo,"https://github.com/Syrnaxei/NCWUStudyProject_2048");
        JLabel link2 = createLinkLabel(ncwuEmblem,"https://www.ncwu.edu.cn/");

        linkPanel.add(link1);
        linkPanel.add(link2);

        add(linkPanel,BorderLayout.SOUTH);


        //omg I cant touch anything // bugfix
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mainFrame.requestFocusInWindow();
                mainFrame.setFocusable(true);
                mainFrame.toFront();
            }
        });

        setVisible(true);
    }


    // 创建带链接的Label
    private JLabel createLinkLabel(Image image,String url) {
        JLabel label = new JLabel();

        label.setIcon(new ImageIcon(image));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(InfoGUI.this, "Failed to open link: " + ex.getMessage());
                }
            }
        });

        return label;
    }

    // 生成成员信息条目
    private JPanel createPersonItem(String avatarPath, String name, String responsibleSection) {
        // 外层FlowLayout左对齐
        JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        itemPanel.setOpaque(false);
        itemPanel.setMaximumSize(new Dimension(400, 80)); // 限制宽度，避免文字太分散

        // 1. 头像
        ImageIcon avatarIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(avatarPath)));
        Image scaledAvatar = avatarIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        JLabel avatarLabel = new JLabel(new ImageIcon(scaledAvatar));
        itemPanel.add(avatarLabel);

        // 2. 文字区域
        JPanel textItemPanel = new JPanel();
        textItemPanel.setLayout(new BoxLayout(textItemPanel, BoxLayout.Y_AXIS));
        textItemPanel.setOpaque(false);
        textItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // 文字区域左对齐

        // 姓名
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textItemPanel.add(nameLabel);

        // 职责
        JLabel sectionLabel = new JLabel(responsibleSection);
        sectionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        sectionLabel.setForeground(new Color(80,80,80));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textItemPanel.add(sectionLabel);

        itemPanel.add(textItemPanel);
        return itemPanel;
    }
}