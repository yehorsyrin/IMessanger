/*
 * Created by JFormDesigner on Sun Mar 04 14:44:30 EET 2018
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
public class ChangeNick extends JFrame {
    public ChangeNick() {
        initComponents();
    }

    private void okButtonActionPerformed(ActionEvent e) {
        String msg = "<?xml version='1.0' encoding='utf-8'?><class event = \"change name\"> <name>" + Main.getNick()
                + "</name> <newname>" + newNickField.getText() + "</newname> </class>";
        Main.getOut().println(msg);
        Main.getOut().flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            System.out.println("Error in \"ChangeNick.okButtonActionPerformed\" during pause of Thread");
        }
        boolean check = Main.getClient().isCheck();
        if(check) {
            Main.setNick(newNickField.getText());
            Main.getProfile().getNickLabel().setText(newNickField.getText());
            JOptionPane.showMessageDialog(null, "Nickname changed successfully");
            Main.getMainWindow().setTitle(Main.getNick());
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error in changing nickname");
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
        newNickLabel = new JLabel();
        newNickField = new JTextField();
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
                    "[]"));

                //---- newNickLabel ----
                newNickLabel.setText("Enter new nickname");
                contentPanel.add(newNickLabel, "cell 0 0");
                contentPanel.add(newNickField, "cell 0 1");
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
    private JLabel newNickLabel;
    private JTextField newNickField;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
