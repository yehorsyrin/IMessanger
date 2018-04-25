/*
 * Created by JFormDesigner on Sat Mar 03 13:33:19 EET 2018
 */

package com.company.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.company.controller.Main;
import net.miginfocom.swing.*;

/**
 * class that creates main window
 */
public class MainWindow extends JFrame {
    /**
     * creates new main window
     */
    public MainWindow() {
        initComponents();
        setSize(400, 400);
        setResizable(false);
        layeredPane.setPreferredSize(new Dimension(300, 300));
        layeredPane.add(Main.getMainChat(), 50);
        Main.getMainChat().setBounds(0, 0, 398, 300);
        layeredPane.add(Main.getListOfUsers(), 100);
        Main.getListOfUsers().setBounds(0, 0, 398, 300);
        Main.getListOfUsers().setVisible(false);
        layeredPane.add(Main.getProfile(), 200);
        Main.getProfile().setBounds(0, 0, 398, 300);
        Main.getProfile().setVisible(false);
        this.add(layeredPane);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                String msg = "<?xml version='1.0' encoding='utf-8'?><class event = \"logout\"> <logout>true</logout> </class>";
                Main.getOut().println(msg);
                Main.getOut().flush();
                System.exit(0);
            }
        });
    }

    public JButton getChatBtn() {
        return chatBtn;
    }

    public JButton getListOfUsersBtn() {
        return listOfUsersBtn;
    }

    public JButton getProfileBtn() {
        return profileBtn;
    }

    private void listOfUsersActionPerformed(ActionEvent e) {
        Main.getUsers().clear();
        Main.getUsers().add("You are banned");
        String msg = "<class event=\"get online list\"></class>";
        Main.getOut().println(msg);
        Main.getOut().flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            System.out.println("Error in \"MainWindow.listOfUsersActionPerformed\" during pause of Thread");
        }
        Main.getListOfUsers().setList(Main.getUsers());
        Main.getMainChat().setVisible(false);
        Main.getListOfUsers().setVisible(true);
        Main.getProfile().setVisible(false);
    }

    private void chatActionPerformed(ActionEvent e) {
        Main.getMainChat().setVisible(true);
        Main.getListOfUsers().setVisible(false);
        Main.getProfile().setVisible(false);
    }

    private void profileBtnActionPerformed(ActionEvent e) {
        Main.getProfile().getStatusLabel().setText(Main.getStatus());
        Main.getProfile().getNickLabel().setText(Main.getNick());
        Main.getMainChat().setVisible(false);
        Main.getListOfUsers().setVisible(false);
        Main.getProfile().setVisible(true);
        Main.getProfile().getNickLabel().setText(Main.getNick());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Anton Mishchenko
        panel = new JPanel();
        chatBtn = new JButton();
        listOfUsersBtn = new JButton();
        profileBtn = new JButton();
        layeredPane = new JLayeredPane();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(5, 5));

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

            panel.setLayout(new MigLayout(
                    "fillx,hidemode 3",
                    // columns
                    "[fill]" +
                            "[fill]" +
                            "[fill]",
                    // rows
                    "[]"));

            //---- chatBtn ----
            chatBtn.setText("Chat");
            chatBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chatActionPerformed(e);
                }
            });
            panel.add(chatBtn, "cell 0 0");

            //---- listOfUsersBtn ----
            listOfUsersBtn.setText("List of users");
            listOfUsersBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    listOfUsersActionPerformed(e);
                }
            });
            panel.add(listOfUsersBtn, "cell 1 0");

            //---- profileBtn ----
            profileBtn.setText("Profile");
            profileBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    profileBtnActionPerformed(e);
                }
            });
            panel.add(profileBtn, "cell 2 0");
        }
        contentPane.add(panel, BorderLayout.NORTH);
        contentPane.add(layeredPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Anton Mishchenko
    private JPanel panel;
    private JButton chatBtn;
    private JButton listOfUsersBtn;
    private JButton profileBtn;
    private JLayeredPane layeredPane;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
