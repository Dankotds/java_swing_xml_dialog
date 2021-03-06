package com.company;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class ControllerMainJTable/* extends JPanel*/ {
    private JPanel panelWithTable;
    private List<String> columnNames;
    private JTable table;
    private JButton leftBorderBt, toLeftBt, rightBorderBt, toRightBt;
    private JTextArea numberOfAllPagesTextArea;
    private JTextField numberOfPageTextField;
    private int tableCurrentPage = 1;
    private int tableRows = 4;
    private int tableColumns = 5;
    private AbstractTableModel tableModel;

    @SuppressWarnings("serial")
    public JPanel CreateJTable(List<Item> items, JFrame frame, int width, int height, int plusY, int numberOfItemsOnPage ){
        panelWithTable = new JPanel();
        addToList();
        drawTable(items, numberOfItemsOnPage);
        panelWithTable.setLayout(null);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 350 + plusY, 1200, 102 + (numberOfItemsOnPage - 5)*16);
        leftBorderBt = new JButton("<<");
        leftBorderBt.setBounds(0, 290 + plusY, 65, 50);
        panelWithTable.add(leftBorderBt);
        toLeftBt = new JButton("<");
        toLeftBt.setBounds(70, 290 + plusY, 65, 50);
        panelWithTable.add(toLeftBt);
        toRightBt = new JButton(">");
        toRightBt.setBounds(1065, 290 + plusY, 65, 50);
        panelWithTable.add(toRightBt);
        rightBorderBt = new JButton(">>");
        rightBorderBt.setBounds(1135, 290 + plusY, 65, 50);
        panelWithTable.add(rightBorderBt);

        numberOfPageTextField = new JTextField(String.valueOf(tableCurrentPage));
        numberOfPageTextField.setBounds(555, 315 + plusY, 35, 20);
        panelWithTable.add(numberOfPageTextField);
        numberOfAllPagesTextArea = new JTextArea(" / " + numberOfAllPages(items, numberOfItemsOnPage));
        numberOfAllPagesTextArea.setBounds(600, 315 + plusY, 35, 20);
        panelWithTable.add(numberOfAllPagesTextArea);

        action(items, panelWithTable, frame, width, height, plusY, numberOfItemsOnPage, scrollPane);

        panelWithTable.add(scrollPane);
        panelWithTable.setVisible(true);
        return panelWithTable;
    }

    private void action(List<Item> items, JPanel panel, JFrame frame, int width, int height, int plusY, int numberOfItemsOnPage, JScrollPane pane){

        toLeftBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tableCurrentPage == 1){
                    tableCurrentPage = numberOfAllPages(items, numberOfItemsOnPage);
                } else {
                    tableCurrentPage--;
                }
                numberOfPageTextField.setText(String.valueOf(tableCurrentPage));
                pane.removeAll();
                drawTable(items, numberOfItemsOnPage);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBounds(0, 350 + plusY, 1200, 102 + (numberOfItemsOnPage - 5)*16);
                panel.add(scrollPane);
                panel.setVisible(true);
            }
        });

        toRightBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tableCurrentPage == numberOfAllPages(items, numberOfItemsOnPage)){
                    tableCurrentPage = 1;
                } else {
                    tableCurrentPage++;
                }
                numberOfPageTextField.setText(String.valueOf(tableCurrentPage));
                drawTable(items, numberOfItemsOnPage);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBounds(0, 350 + plusY, 1200, 102 + (numberOfItemsOnPage - 5)*16);
                panel.add(scrollPane);
                panel.setVisible(true);
            }
        });

        leftBorderBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableCurrentPage = 1;
                numberOfPageTextField.setText(String.valueOf(tableCurrentPage));
                drawTable(items, numberOfItemsOnPage);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBounds(0, 350 + plusY, 1200, 102 + (numberOfItemsOnPage - 5)*16);
                panel.add(scrollPane);
                panel.setVisible(true);
            }
        });

        rightBorderBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableCurrentPage = numberOfAllPages(items, numberOfItemsOnPage);
                numberOfPageTextField.setText(String.valueOf(tableCurrentPage));
                drawTable(items, numberOfItemsOnPage);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBounds(0, 350 + plusY, 1200, 102 + (numberOfItemsOnPage - 5)*16);
                panel.add(scrollPane);
                panel.setVisible(true);
            }
        });
    }

    private int numberOfAllPages(List<Item> items, int numberOfItemsOnPage){
        if(items.size()%numberOfItemsOnPage == 0){
            return items.size()/numberOfItemsOnPage;
        } else {
            return items.size()/numberOfItemsOnPage + 1;
        }
    }

    private void drawTable(List<Item> items, int numberOfItemsOnPage){
        if (tableCurrentPage == numberOfAllPages(items, numberOfItemsOnPage) && items.size() % numberOfItemsOnPage != 0){
            tableRows = items.size() % numberOfItemsOnPage;
        } else{
            tableRows = numberOfItemsOnPage;
        }

        tableModel = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return tableRows;
            }

            @Override
            public int getColumnCount() {
                return tableColumns;
            }

            @Override
            public String getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0: {
                        return items.get(rowIndex + numberOfItemsOnPage*(tableCurrentPage -1)).getItemName();

                    }
                    case 1: {
                        return items.get(rowIndex + numberOfItemsOnPage*(tableCurrentPage -1)).getManufacturerName();

                    }
                    case 2: {
                        return items.get(rowIndex + numberOfItemsOnPage*(tableCurrentPage -1)).getManufacturerPAN();

                    }
                    case 3: {
                        return items.get(rowIndex + numberOfItemsOnPage*(tableCurrentPage -1)).getItemsInStock();

                    }
                    case 4: {
                        return items.get(rowIndex + numberOfItemsOnPage*(tableCurrentPage -1)).getStockAddress();

                    }
                    default:
                        return null;
                }
            }

            public String getColumnName(int c){
                return columnNames.get(c);
            }
        };
        table = new JTable(tableModel);
        table.setAutoCreateColumnsFromModel(true);
        table.setIgnoreRepaint(false);
    }

    private void addToList(){
        columnNames = new LinkedList<String>();
        columnNames.add("Название товара");
        columnNames.add("Название производителя");
        columnNames.add("УНП производителя");
        columnNames.add("Количество на складе");
        columnNames.add("Адрес склада");
    }
}
