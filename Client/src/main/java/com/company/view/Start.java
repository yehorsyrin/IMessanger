/*
 * Created by JFormDesigner on Sat Mar 03 14:12:57 EET 2018
 */

package com.company.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.company.controller.Client;
import com.company.controller.Main;
import net.miginfocom.swing.*;

/**
 * @author Anton Mishchenko
 */
public class Start extends JFrame {

    public Start() {
        initComponents();
    }

    private void logInActionPerformed(ActionEvent e) {
        Main.setListOfUsers((new ListOfUsers(Main.getUsers())));
        Main.setClient(new Client(Main.getHost(), Main.getPort()));
        Main.setProfile(new Profile(Main.getNick(), Main.getStatus()));
        Main.setMainChat(new Chat());
        Main.setNick(nickField.getText());
        String msg = "<?xml version='1.0' encoding='utf-8'?><class event = \"login\"> <name>" + nickField.getText()
                + "</name> <password>" + passField.getText() + "</password> </class>";
        Main.getOut().println(msg);
        Main.getOut().flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        if(Main.getClient().isLogedIn()) {
            dispose();
            Main.setMainWindow(new MainWindow());
            Main.getMainWindow().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Error in login");
        }

    }

    private void registerActionPerformed(ActionEvent e) {
        Main.setClient(new Client(Main.getHost(), Main.getPort()));
        Main.setProfile(new Profile(Main.getNick(), Main.getStatus()));
        Main.setMainChat(new Chat());
        Main.setNick(nickField.getText());
        String msg = "<?xml version='1.0' encoding='utf-8'?><class event = \"create user\"> <name>" + nickField.getText()
                + "</name> <password>" + passField.getText() + "</password> </class>";
        Main.getOut().println(msg);
        Main.getOut().flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        if(Main.getClient().isCreated()) {
            logInActionPerformed(e);
        } else {
            JOptionPane.showMessageDialog(null, "Error in creating user");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Anton Mishchenko
        nickLabel = new JLabel();
        nickField = new JTextField();
        passLabel = new JLabel();
        passField = new JTextField();
        panel = new JPanel();
        logInBtn = new JButton();
        registerBtn = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill,hidemode 3",
            // columns
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- nickLabel ----
        nickLabel.setText("Enter your nickname");
        contentPane.add(nickLabel, "cell 0 0");
        contentPane.add(nickField, "cell 0 1");

        //---- passLabel ----
        passLabel.setText("Enter your password");
        contentPane.add(passLabel, "cell 0 2");
        contentPane.add(passField, "cell 0 3");

        //======== panel ========
        {

            // JFormDesigner evaluation mark
            panel.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new Font("Dialog", Font.BOLD, 12),
                    Color.red), panel.getBorder())); panel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            panel.setLayout(new MigLayout(
                "fillx,hidemode 3",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]"));

            //---- logInBtn ----
            logInBtn.setText("Log In");
            logInBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    logInActionPerformed(e);
                }
            });
            panel.add(logInBtn, "cell 0 0");

            //---- registerBtn ----
            registerBtn.setText("Register");
            registerBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    registerActionPerformed(e);
                }
            });
            panel.add(registerBtn, "cell 1 0");
        }
        contentPane.add(panel, "cell 0 4");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Anton Mishchenko
    private JLabel nickLabel;
    private JTextField nickField;
    private JLabel passLabel;
    private JTextField passField;
    private JPanel panel;
    private JButton logInBtn;
    private JButton registerBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
