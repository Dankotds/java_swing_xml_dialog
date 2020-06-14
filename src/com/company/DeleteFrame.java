package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DeleteFrame extends JFrame {
    private List<Item> itemsForDelete = new ArrayList<Item>();
    private List<Item> allItems = new ArrayList<Item>();
    MainFrame mainFrame = new MainFrame();
    Controller controller;
    private JPanel jPanelFind = new JPanel();
    private boolean listCreated = false, tableCreated = false;
    private JButton acceptBt;

    //ITEM & MANUFACTURER
    private JTextField itemNameField, manufacturerNameField, manufacturerPANField;
    private JCheckBox itemNameCheckBox, manufacturerNameCheckBox, manufacturerPANCheckBox;
    //STOCK
    private JTextField itemsInStockField, stockAddressField;
    private JCheckBox itemsInStockCheckBox, stockAddressCheckBox;

    public DeleteFrame(Controller controller, String pathFile){
        this.setTitle("Удаление по фильтру");
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
        acceptBt = new JButton("Подтвердить удаление по фильтру");
        acceptBt.setBounds(100, 300, 500, 50);
        acceptBt.setBackground(Color.RED);
        jPanelFind.add(acceptBt);

        action(this, pathFile);
        this.add(jPanelFind);
        this.setSize(720, 400);
        this.setVisible(true);

    }

    private void action(JFrame frame, String pathFile){
        acceptBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listCreated = false;
                itemsForDelete.clear();
                listCreated = controller.scanJPanel(pathFile, itemNameCheckBox, itemNameField, "itemName", frame, listCreated, itemsForDelete);
                listCreated = controller.scanJPanel(pathFile, manufacturerNameCheckBox, manufacturerNameField,"manufacturerName", frame, listCreated, itemsForDelete);
                listCreated = controller.scanJPanel(pathFile, manufacturerPANCheckBox, manufacturerPANField,"manufacturerPAN", frame, listCreated, itemsForDelete);
                listCreated = controller.scanJPanel(pathFile, itemsInStockCheckBox, itemsInStockField,"itemsInStock", frame, listCreated, itemsForDelete);
                listCreated = controller.scanJPanel(pathFile, stockAddressCheckBox, stockAddressField,"stockAddress", frame, listCreated, itemsForDelete);

                if (itemsForDelete.size() != 0) {
                    if (tableCreated) tableCreated = controller.removeTable(frame, tableCreated);
                    frame.add(controller.createTable(frame, itemsForDelete));
                    tableCreated = true;
                    frame.setSize(1350, 730);
                    frame.setVisible(true);
                    JOptionPane.showMessageDialog(frame, "Поздравляю!\nВы удалили следующие товары!");
                    controller.loadFromXMLToList(pathFile, allItems, false, "","");
                    controller.updateXMLFile(controller.updateListOfItems(allItems, itemsForDelete));
                } else {
                    JOptionPane.showMessageDialog(frame, "Сожалею!\nНо ничего не найдено!");
                }
            }
        });
    }
}
