package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FindFrame extends JFrame {
    private List<Item> itemsForFind = new ArrayList<Item>();
    Controller controller;
    MainFrame mainFrame = new MainFrame();
    private JPanel jPanelFind = new JPanel();
    private boolean listCreated = false, tableCreated = false;
    private JButton acceptBt;

    //ITEM & MANUFACTURER
    private JTextField itemNameField, manufacturerNameField, manufacturerPANField;
    private JCheckBox itemNameCheckBox, manufacturerNameCheckBox, manufacturerPANCheckBox;
    //STOCK
    private JTextField itemsInStockField, stockAddressField;
    private JCheckBox itemsInStockCheckBox, stockAddressCheckBox;

    public FindFrame(Controller controller, String pathFile) {
        this.setTitle("Поиск по фильтру");
        jPanelFind.setLayout(null);
        this.controller = controller;

        //ITEM & MANUFACTURER
        itemNameField = this.controller.createField(itemNameField, 0, 0, jPanelFind);
        itemNameCheckBox = this.controller.createChBox(itemNameCheckBox, 0,  30, "Использовать название товара", jPanelFind);

        manufacturerNameField = this.controller.createField(manufacturerNameField, 0, 100, jPanelFind);
        manufacturerNameCheckBox = this.controller.createChBox(manufacturerNameCheckBox, 0,  130, "Использовать название производителя", jPanelFind);

        manufacturerPANField = this.controller.createField(manufacturerPANField, 0, 200, jPanelFind);
        manufacturerPANCheckBox = this.controller.createChBox(manufacturerPANCheckBox,  0, 230, "Использовать УНП производителя", jPanelFind);

        //STOCK
        itemsInStockField = this.controller.createField(itemsInStockField, 420,  50, jPanelFind);
        itemsInStockCheckBox = this.controller.createChBox(itemsInStockCheckBox, 420,   80, "Использовать количество на складе", jPanelFind);

        stockAddressField = this.controller.createField(stockAddressField, 420, 150, jPanelFind);
        stockAddressCheckBox = this.controller.createChBox(stockAddressCheckBox,  420, 180, "Использовать адрес склада", jPanelFind);

        //BUTTON
        acceptBt = new JButton("Подтвердить поиск по фильтру");
        acceptBt.setBounds(100, 300, 500, 50);
        acceptBt.setBackground(Color.GREEN);
        jPanelFind.add(acceptBt);

        action(this, pathFile);
        this.add(jPanelFind);
        this.setSize(720, 400);
        this.setVisible(true);

    }

    private void action(JFrame frame, String pathFile) {
        acceptBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listCreated = controller.getItems().size() != 0;
                itemsForFind.clear();
                controller.loadFromXMLToList(pathFile, itemsForFind, false, "","");
                listCreated = true;
                listCreated = controller.scanJPanel(pathFile, itemNameCheckBox, itemNameField, "itemName", frame, listCreated, itemsForFind);
                listCreated = controller.scanJPanel(pathFile, manufacturerNameCheckBox, manufacturerNameField,"manufacturerName", frame, listCreated, itemsForFind);
                listCreated = controller.scanJPanel(pathFile, manufacturerPANCheckBox, manufacturerPANField,"manufacturerPAN", frame, listCreated, itemsForFind);
                listCreated = controller.scanJPanel(pathFile, itemsInStockCheckBox, itemsInStockField,"itemsInStock", frame, listCreated, itemsForFind);
                listCreated = controller.scanJPanel(pathFile, stockAddressCheckBox, stockAddressField,"stockAddress", frame, listCreated, itemsForFind);
            
                if (itemsForFind.size() != 0) {
                    if (tableCreated) tableCreated = controller.removeTable(frame, tableCreated);
                    frame.add(controller.createTable(frame, itemsForFind));
                    tableCreated = true;
                    frame.setSize(1350, 730);
                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Сожалею!\nНо ничего не найдено!");
                }
            }
        });
    }
}