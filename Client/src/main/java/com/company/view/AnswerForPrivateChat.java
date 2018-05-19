package com.company.view;

import com.company.controller.Main;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AnswerForPrivateChat extends JFrame {
    private String nickname;
    private static Logger logger = Logger.getRootLogger();

    /**
     * creates new window for selection action with user
     * @param nickname nickname of user
     */
    public AnswerForPrivateChat(String nickname) {
        initComponents();
        yesBtn.setEnabled(true);
        noBtn.setEnabled(true);
        this.setSize(300, this.getHeight());
        this.nickname = nickname;
        label.setText("User " + nickname + "wants to start private chat with you");
    }

    private void yesBtnActionPerformed(ActionEvent e) {
        if (!Main.getChats().containsKey(nickname)) {
            PrivateChat chat = new PrivateChat(nickname);
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
            frame.setVisible(true);
            String msg = "<?xml version='1.0' encoding='utf-8'?><class event = \"ban\"> <from>" + Main.getNick() + "</from><to>"
                    + nickname +"</to><result>true</result></class>";
            Main.getOut().println(msg);
            Main.getOut().flush();
        }
        dispose();
    }

    private void noBtnActionPerformed(ActionEvent e) {
        String msg = "<?xml version='1.0' encoding='utf-8'?><class event = \"ban\"> <from>" + Main.getNick() + "</from><to>"
                + nickname +"</to><result>false</result></class>";
        Main.getOut().println(msg);
        Main.getOut().flush();
        this.dispose();
    }

    private void initComponents() {
        label = new JLabel();
        panel = new JPanel();
        yesBtn = new JButton();
        noBtn = new JButton();

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

            //---- yesBtn ----
            yesBtn.setText("Agree");
            yesBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    yesBtnActionPerformed(e);
                }
            });
            panel.add(yesBtn);

            //---- noBtn ----
            noBtn.setText("Decline");
            noBtn.setEnabled(false);
            noBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    noBtnActionPerformed(e);
                }
            });
            panel.add(noBtn);
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
    private JButton yesBtn;
    private JButton noBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
