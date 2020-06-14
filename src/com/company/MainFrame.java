package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFrame {
    ControllerMainJTable controllerMainJTable = new ControllerMainJTable();
    Controller controller = new Controller();
    private JFrame mainFrame = new JFrame("Сведения о товарах на складах");
    private JPanel mainPanel = new JPanel();
    private JPanel tablePanel = new JPanel();
    private JButton addBt, findBt, deleteBt, showTableBt;
    private JTextField numberOfItemsOnPageField;
    private JTextArea numberOfItemsOnPageArea;
    private boolean tableCreated = false;
    private String pathFile;

    public void initialize() {
        mainPanel.setLayout(null);
        addBt = new JButton("Добавить в таблицу новый товар");
        addBt.setBounds(0, 0, 300, 50);
        mainPanel.add(addBt);
        mainPanel.setLayout(null);
        findBt = new JButton("Найти по фильтру");
        findBt.setBounds(0, 70, 300, 50);
        mainPanel.add(findBt);
        mainPanel.setLayout(null);
        deleteBt = new JButton("Удалить по фильтру");
        deleteBt.setBounds(0, 140, 300, 50);
        mainPanel.add(deleteBt);
        showTableBt = new JButton("Показать данные XML файла");
        showTableBt.setBounds(0, 210, 300, 50);
        mainPanel.add(showTableBt);
        numberOfItemsOnPageField = new JTextField("5");
        numberOfItemsOnPageField.setBounds(330, 220, 30, 30);
        mainPanel.add(numberOfItemsOnPageField);
        numberOfItemsOnPageArea = new JTextArea(" количество товаров на одной странице");
        numberOfItemsOnPageArea.setBounds(370, 225, 300, 15);
        mainPanel.add(numberOfItemsOnPageArea);
        mainFrame.add(mainPanel);
        mainFrame.setSize(670, 300);
        action(numberOfItemsOnPageField);
        mainFrame.setVisible(true);
    }

    private void action(JTextField numberOfItemsOnPageField){
        addBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewItemFrame newItemFrame = new NewItemFrame(pathFile);
            }
        });

        findBt.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  FindFrame findFrame = new FindFrame(controller, pathFile);
              }
        });

        deleteBt.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  DeleteFrame deleteFrame = new DeleteFrame(controller, pathFile);
              }
        });

        showTableBt.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   JFileChooser fileopen = new JFileChooser();
                   int ret = fileopen.showDialog(null, "Открыть файл");
                   if (ret == JFileChooser.APPROVE_OPTION) {
                       File file = fileopen.getSelectedFile();
                       pathFile = file.getName();
                   }
                   
                   if(tableCreated){
                       removeTable();
                   }
                   mainFrame.add(createTable());
                   mainFrame.setSize(1200, 600  + (Integer.parseInt(numberOfItemsOnPageField.getText())-5)*16);
                   mainFrame.setVisible(true);
               }
        });
    }

    private JPanel createTable(){
        controller.loadFromXMLToList(pathFile, controller.getItems(), false, "","");
        tablePanel = controllerMainJTable.CreateJTable(controller.getItems(), mainFrame, 1200, 600, 0, Integer.parseInt(numberOfItemsOnPageField.getText()));
        tableCreated = true;
        return tablePanel;
    }

    private void removeTable(){
        mainFrame.remove(tablePanel);
        tableCreated = false;
    }
}
