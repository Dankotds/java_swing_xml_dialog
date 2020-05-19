package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NewItemFrame extends JFrame {
    public List<Item> allItems = new ArrayList<Item>();
    MainFrame mainFrame = new MainFrame();
    Controller controller = new Controller();
    private JPanel jPanelFind = new JPanel();
    private boolean listCreated = false, tableCreated = false;
    private JButton createBt;

    //ITEM & MANUFACTURER
    private JTextField itemNameField, manufacturerNameField, manufacturerPANField;
    private JTextArea itemNameArea, manufacturerNameArea, manufacturerPANArea;
    //STOCK
    private JTextField itemsInStockField, stockAddressField;
    private JTextArea itemsInStockArea, stockAddressArea;

    public NewItemFrame(String pathFile){
        this.setTitle("Добавление товара");
        jPanelFind.setLayout(null);

        //ITEM & MANUFACTURER
        itemNameField = controller.createField(itemNameField, 0, 0, jPanelFind);
        itemNameArea = controller.createTextArea(itemNameArea, 255,  0, "Название товара", jPanelFind);

        manufacturerNameField = controller.createField(manufacturerNameField, 0, 50, jPanelFind);
        manufacturerNameArea = controller.createTextArea(manufacturerNameArea, 255,  50, "Название производителя", jPanelFind);

        manufacturerPANField = controller.createField(manufacturerPANField, 0, 100, jPanelFind);
        manufacturerPANArea = controller.createTextArea(manufacturerPANArea,  255, 100, "УНП производителя", jPanelFind);

        //STOCK
        itemsInStockField = controller.createField(itemsInStockField, 0,  150, jPanelFind);
        itemsInStockArea = controller.createTextArea(itemsInStockArea, 255,   150, "Количество на складе", jPanelFind);

        stockAddressField = controller.createField(stockAddressField, 0, 200, jPanelFind);
        stockAddressArea = controller.createTextArea(stockAddressArea,  255, 200, "Адрес склада", jPanelFind);


        //BUTTON
        createBt = new JButton("Добавить товар");
        createBt.setBounds(0, 250, 650, 50);
        createBt.setBackground(Color.BLUE);
        jPanelFind.add(createBt);

        action(this, pathFile);
        this.add(jPanelFind);
        this.setSize(700, 375);
        this.setVisible(true);

    }

    private void action(JFrame frame, String pathFile){
        createBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadFromXMLToList(pathFile, allItems, false, "","");
                if(!itemNameField.getText().equals("") && !manufacturerNameField.getText().equals("") && !manufacturerPANField.getText().equals("") && !itemsInStockField.getText().equals("") && !stockAddressField.getText().equals("")) {
                    controller.addItem(allItems, itemNameField.getText(), manufacturerNameField.getText(), manufacturerPANField.getText(), itemsInStockField.getText(), stockAddressField.getText());
                    controller.updateXMLFile(allItems);
                    JOptionPane.showMessageDialog(frame, "Поздравляю!\nТовар успешно добавлен!");
                } else JOptionPane.showMessageDialog(frame, "Ошибка!\nТовар не добавлен!\nОдно из полей осталось пустым!");
            }
        });
    }


}

