/*
 * Created by JFormDesigner on Sun Mar 04 14:57:35 EET 2018
 */

package com.company.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.company.controller.Main;
import net.miginfocom.swing.*;
import org.apache.log4j.Logger;

/**
 * class that creates window for selection action with user
 */
public class ActionWithUser extends JFrame {
    private String nickname;
    private String clearNickname;
    private static Logger logger = Logger.getRootLogger();

    /**
     * creates new window for selection action with user
     * @param nickname nickname of user
     */
    public ActionWithUser(String nickname) {
        initComponents();
        if (Main.isAdmin()) {
            banBtn.setEnabled(true);
            delBtn.setEnabled(true);
        } else {
            banBtn.setEnabled(false);
        }
        if (nickname.contains("<html>")) {
            startChatBtn.setEnabled(false);
            //banBtn.setEnabled(false);
            banBtn.setText("Unban");
        } else {
            startChatBtn.setEnabled(true);
            banBtn.setText("Ban");
        }
        this.setSize(300, this.getHeight());
        this.nickname = nickname;
        clearNickname = nickname.replace("<html><strike>", "");
        clearNickname = clearNickname.replace("</html></strike>", "");
        label.setText("What you want to do with " + clearNickname + "?");
    }

    private void startChatBtnActionPerformed(ActionEvent e) {
        if (!Main.getChats().containsKey(nickname)) {
            /*PrivateChat chat = new PrivateChat(nickname);
            chat.setCheck(true);
            Main.getChats().put(nickname, chat);
            JFrame frame = new JFrame();
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    Main.getChats().remove(nickname);
                }
            });
            frame.setSize(398, 300);
            frame.setTitle(nickname);
            frame.add(chat);
            frame.setVisible(true);*/
            String msg = "<?xml version='1.0' encoding='utf-8'?><class event = \"ask for private chat\"> <from>"
                    + Main.getNick() + "</from><to>" + clearNickname + "</to> </class>";
            Main.getOut().println(msg);
            Main.getOut().flush();
        }
        dispose();
    }

    private void banBtnActionPerformed(ActionEvent e) {
        String msg = "<?xml version='1.0' encoding='utf-8'?><class event = \"ban\"> <name>" + clearNickname + "</name> </class>";
        Main.getOut().println(msg);
        Main.getOut().flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            System.out.println("Error in \"ActionWithUser.banBtnActionPerformed\" during pause of Thread");
            logger.error("Error in \"ActionWithUser.banBtnActionPerformed\" during pause of Thread", e1);
        }
        if (Main.getClient().isCheck()) {
            if(banBtn.getText().equals("Ban")) {
                JOptionPane.showMessageDialog(null, "User " + clearNickname + " is banned!");
            } else {
                JOptionPane.showMessageDialog(null, "User " + clearNickname + " is unbanned!");
            }
        } else {
            if(banBtn.getText().equals("Ban")) {
                JOptionPane.showMessageDialog(null, "User " + clearNickname + " is not banned!");
            } else {
                JOptionPane.showMessageDialog(null, "User " + clearNickname + " is not unbanned!");
            }
        }
        this.dispose();
    }
    private void delBtnActionPerformed(ActionEvent e) {
        String msg = "<?xml version='1.0' encoding='utf-8'?><class event = \"delete\"> <name>" + clearNickname + "</name> </class>";
        Main.getOut().println(msg);
        Main.getOut().flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            System.out.println("Error in \"ActionWithUser.delBtnActionPerformed\" during pause of Thread");
            logger.error("Error in \"ActionWithUser.delBtnActionPerformed\" during pause of Thread", e1);
        }
        if (Main.getClient().isCheck()) {
            JOptionPane.showMessageDialog(null, "User " + clearNickname + " is deleted!");
        } else {
            JOptionPane.showMessageDialog(null, "User " + clearNickname + " is not deleted!");
        }
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Anton Mishchenko
        label = new JLabel();
        panel = new JPanel();
        startChatBtn = new JButton();
        banBtn = new JButton();
        delBtn = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(16, 39));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(5, 5));

        //---- label ----
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setText("ddd");
        contentPane.add(label, BorderLayout.CENTER);

        //======== panel ========
        {

            // JFormDesigner evaluation mark
            panel.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                            "", javax.swing.border.TitledBorder.CENTER,
                            javax.swing.border.TitledBorder.BOTTOM, new Font("Dialog", Font.BOLD, 12),
                            Color.red), panel.getBorder()));
            panel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent e) {
                    if ("border".equals(e.getPropertyName())) throw new RuntimeException();
                }
            });

            panel.setLayout(new GridLayout(1, 2, 4, 4));

            //---- startChatBtn ----
            startChatBtn.setText("Start chat");
            startChatBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startChatBtnActionPerformed(e);
                }
            });
            panel.add(startChatBtn);

            //---- banBtn ----
            banBtn.setText("Ban");
            banBtn.setEnabled(false);
            banBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    banBtnActionPerformed(e);
                }
            });
            panel.add(banBtn);

            //---- delBtn ----
            delBtn.setText("Delete");
            delBtn.setEnabled(false);
            delBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    delBtnActionPerformed(e);
                }
            });
            panel.add(delBtn);
        }
        contentPane.add(panel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Anton Mishchenko
    private JLabel label;
    private JPanel panel;
    private JButton startChatBtn;
    private JButton banBtn;
    private JButton delBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
