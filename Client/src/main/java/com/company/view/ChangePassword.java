/*
 * Created by JFormDesigner on Sun Mar 04 14:42:43 EET 2018
 */

package com.company.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.company.controller.Main;
import net.miginfocom.swing.*;

/**
 * @author Anton Mishchenko
 */
public class ChangePassword extends JFrame {
    public ChangePassword() {
        initComponents();
    }

    private void okButtonActionPerformed(ActionEvent e) {
        if(oldPassField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter old password");
        } else if (newPassField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter new password");
        } else if (oldPassField.getText().equals(newPassField.getText())) {
            JOptionPane.showMessageDialog(null, "New and old passwords are the same");
        } else {
            String msg = "<?xml version='1.0' encoding='utf-8'?><class event = \"change password\"> <name>" + Main.getNick()
                    + "</name> <password>" + oldPassField.getText() + "</password><newpassword>" + newPassField.getText() +
                    "</newpassword></class>";
            Main.getOut().println(msg);
            Main.getOut().flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            boolean check = Main.getClient().isCheck();
            if (check) {
                JOptionPane.showMessageDialog(null, "Password changed successfully");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Old password is wrong");
            }
        }
    }

    private void cancelButtonActionPerformed(ActionEvent e) {
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Anton Mishchenko
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        oldPassLabel = new JLabel();
        oldPassField = new JTextField();
        newPassLabel = new JLabel();
        newPassField = new JTextField();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {

            // JFormDesigner evaluation mark
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new Font("Dialog", Font.BOLD, 12),
                    Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new MigLayout(
                    "fill,insets dialog,hidemode 3",
                    // columns
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //---- oldPassLabel ----
                oldPassLabel.setText("Enter old password");
                contentPanel.add(oldPassLabel, "cell 0 0");
                contentPanel.add(oldPassField, "cell 0 1");

                //---- newPassLabel ----
                newPassLabel.setText("Enter new password");
                contentPanel.add(newPassLabel, "cell 0 2");
                contentPanel.add(newPassField, "cell 0 3");
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setLayout(new MigLayout(
                    "insets dialog,alignx right",
                    // columns
                    "[button,fill]" +
                    "[button,fill]",
                    // rows
                    null));

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        okButtonActionPerformed(e);
                    }
                });
                buttonBar.add(okButton, "cell 0 0");

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cancelButtonActionPerformed(e);
                    }
                });
                buttonBar.add(cancelButton, "cell 1 0");
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Anton Mishchenko
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel oldPassLabel;
    private JTextField oldPassField;
    private JLabel newPassLabel;
    private JTextField newPassField;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
