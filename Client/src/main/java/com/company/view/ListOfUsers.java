/*
 * Created by JFormDesigner on Sat Mar 03 16:22:22 EET 2018
 */

package com.company.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * class that creates panel with user list
 */
public class ListOfUsers extends JPanel {
    /**
     * creates new panel with user list
     *
     * @param users list of users
     */
    public ListOfUsers(ArrayList<String> users) {
        initComponents();
        DefaultListModel model = new DefaultListModel();
        model.addElement(users.get(0));
        model.addElement("two");
        list = new JList(model);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(0);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(100, 100));
        this.add(scrollPane);
        list.addMouseListener(mouseListener);
    }

    public void setList(ArrayList<String> users) {
        DefaultListModel model = new DefaultListModel();
        //jList1 = new JList(model);
        for (int i = 0; i < users.size(); i++) {
            model.addElement(users.get(i));
        }
        list.setModel(model);
    }

    MouseListener mouseListener = new MouseAdapter() {
        public void mouseClicked(MouseEvent mouseEvent) {
            JList theList = (JList) mouseEvent.getSource();
            if (mouseEvent.getClickCount() == 2) {
                int index = theList.locationToIndex(mouseEvent.getPoint());
                if (index >= 0) {
                    Object o = theList.getModel().getElementAt(index);
                    if (!o.toString().equals("You are banned")) {
                        ActionWithUser actionWithUser = new ActionWithUser(o.toString());
                        actionWithUser.setVisible(true);
                    }
                    System.out.println("Double-clicked on: " + o.toString());
                }
            }
        }
    };

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Anton Mishchenko
        scrollPane1 = new JScrollPane();
        list = new JList();

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

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(list);
        }
        add(scrollPane1, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Anton Mishchenko
    private JScrollPane scrollPane1;
    private JList list;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
