/*
 * Created by JFormDesigner on Sun Mar 04 14:04:36 EET 2018
 */

package com.company.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.miginfocom.swing.*;

/**
 * class that creates profile panel
 */
public class Profile extends JPanel {
    /**
     * creates new profile panel
     *
     * @param nick nickname of user
     * @param status status of user(Admin/User)
     */
    public Profile(String nick, String status) {
        initComponents();
        nickLabel.setText("Your nickname: " + nick);
        statusLabel.setText("Your status: " + status);
    }

    public JLabel getNickLabel() {
        return nickLabel;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    private void nickBtnActionPerformed(ActionEvent e) {
        ChangeNick changeNick = new ChangeNick();
        changeNick.setVisible(true);
    }

    private void passBtnActionPerformed(ActionEvent e) {
        ChangePassword changePassword = new ChangePassword();
        changePassword.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Anton Mishchenko
        panel1 = new JPanel();
        nickLabel = new JLabel();
        statusLabel = new JLabel();
        panel2 = new JPanel();
        nickBtn = new JButton();
        passBtn = new JButton();

        //======== this ========

        // JFormDesigner evaluation mark
        setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                        "", javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.BOTTOM, new Font("Dialog", Font.BOLD, 12),
                        Color.red), getBorder()));
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                if ("border".equals(e.getPropertyName())) throw new RuntimeException();
            }
        });

        setLayout(new BorderLayout(5, 5));

        //======== panel1 ========
        {
            panel1.setLayout(new MigLayout(
                    "fill,hidemode 3",
                    // columns
                    "[fill]",
                    // rows
                    "[]" +
                            "[]" +
                            "[]"));
            panel1.add(nickLabel, "cell 0 0");
            panel1.add(statusLabel, "cell 0 1");

            //======== panel2 ========
            {
                panel2.setLayout(new MigLayout(
                        "fillx,hidemode 3",
                        // columns
                        "[fill]" +
                                "[fill]",
                        // rows
                        "[]"));

                //---- nickBtn ----
                nickBtn.setText("Change nickname");
                nickBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        nickBtnActionPerformed(e);
                    }
                });
                panel2.add(nickBtn, "cell 0 0");

                //---- passBtn ----
                passBtn.setText("Change password");
                passBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        passBtnActionPerformed(e);
                    }
                });
                panel2.add(passBtn, "cell 1 0");
            }
            panel1.add(panel2, "cell 0 2");
        }
        add(panel1, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Anton Mishchenko
    private JPanel panel1;
    private JLabel nickLabel;
    private JLabel statusLabel;
    private JPanel panel2;
    private JButton nickBtn;
    private JButton passBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
