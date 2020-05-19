package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private JPanel tablePanel = new JPanel();
    ControllerMainJTable controllerMainJTable = new ControllerMainJTable();
    List<Item> items = new ArrayList<Item>();

    public List<Item> getItems(){
        return items;
    }
    public void setItems(List<Item> newItems){
        items = newItems;
    }

    public void addItem( List<Item> list, String itemName, String manufacturerName, String manufacturerPAN, String itemsInStock, String stockAddress){
        Item item = new Item();
        item.setItemName(itemName);
        item.setManufacturerName(manufacturerName);
        item.setManufacturerPAN(manufacturerPAN);
        item.setItemsInStock(itemsInStock);
        item.setStockAddress(stockAddress);
        list.add(item);
    }

    public JTextField createField(JTextField field, int xField, int yField, JPanel panel){
        field = new JTextField();
        field.setBounds(xField, yField, 250, 25);
        panel.add(field);
        return field;
    }

    public JCheckBox createChBox(JCheckBox box, int xBox, int yBox, String text, JPanel panel){
        box = new JCheckBox(text);
        box.setBounds(xBox, yBox, 400, 30);
        panel.add(box);
        return box;
    }

    public JTextArea createTextArea(JTextArea area, int xBox, int yBox, String text, JPanel panel){
        area = new JTextArea(text);
        area.setBounds(xBox, yBox, 400, 30);
        panel.add(area);
        return area;
    }

    public void checkAndFindFromXML(String pathFile, String box, String field, List<Item> itemsList){
        loadFromXMLToList(pathFile, itemsList, true, box, field);
    }

    public boolean scanJPanel(String pathFile, JCheckBox box, JTextField field, String boxText, JFrame frame, boolean listCreated, List<Item> itemsList){
        if(box.isSelected() && !field.getText().equals("")){
            if (listCreated){
                if(boxText.equals("itemName")){
                    for(int i = 0; i < itemsList.size(); i++){
                        if(!itemsList.get(i).getItemName().equals(field.getText())){
                            itemsList.remove(i);
                            i--;
                        }
                    }
                } else if(boxText.equals("manufacturerName")){
                    for(int i = 0; i < itemsList.size(); i++){
                        if(!itemsList.get(i).getManufacturerName().equals(field.getText())){
                            itemsList.remove(i);
                            i--;
                        }
                    }
                } else if(boxText.equals("manufacturerPAN")){
                    for(int i = 0; i < itemsList.size(); i++){
                        if(!itemsList.get(i).getManufacturerPAN().equals(field.getText())){
                            itemsList.remove(i);
                            i--;
                        }
                    }
                } else if(boxText.equals("itemsInStock")){
                    for(int i = 0; i < itemsList.size(); i++){
                        if(!itemsList.get(i).getItemsInStock().equals(field.getText())){
                            itemsList.remove(i);
                            i--;
                        }
                    }
                } else if(boxText.equals("stockAddress")){
                    for(int i = 0; i < itemsList.size(); i++){
                        if(!itemsList.get(i).getStockAddress().equals(field.getText())){
                            itemsList.remove(i);
                            i--;
                        }
                    }
                }
            }else {
                checkAndFindFromXML(pathFile, boxText, field.getText(), itemsList);
                listCreated = true;
            }
        } else if(box.isSelected() && field.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "Ошибка!\nТекстовое поле пустое, а галочка нажата!");
        }
        return listCreated;
    }

    public JPanel createTable(JFrame frame, List<Item> itemsList){
        tablePanel = controllerMainJTable.CreateJTable(itemsList, frame, 1350, 650, 80, 5);
        return tablePanel;
    }

    public boolean removeTable(JFrame frame, boolean tableCreated){
        frame.remove(tablePanel);
        tableCreated = false;
        return tableCreated;
    }

    public void loadFromXMLToList(String filePath, List<Item> list, boolean filter, String box, String field){
        if (list.size() != 0){
            list.clear();
        }
        try {
            // Cтpoиm oбъekтнyю moдeль иcхoднoгo XML фaйлa            
            final File xmlFile = new File(filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            //Выпoлнять нopmaлизaцию нe oбязaтeльнo, нo pekomeндyeтcя            
            doc.getDocumentElement().normalize();

            //Пoлyчaem вce yзлы c иmeнem "item"
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element element = (Element) node;
                    if (filter && element.getElementsByTagName(box).item(0).getTextContent().equals(field)) {
                        addItem(list, element.getElementsByTagName("itemName").item(0).getTextContent(), element.getElementsByTagName("manufacturerName").item(0).getTextContent(), element.getElementsByTagName("manufacturerPAN").item(0).getTextContent(), element.getElementsByTagName("itemsInStock").item(0).getTextContent(), element.getElementsByTagName("stockAddress").item(0).getTextContent());
                    }
                    if (!filter){
                        addItem(list, element.getElementsByTagName("itemName").item(0).getTextContent(), element.getElementsByTagName("manufacturerName").item(0).getTextContent(), element.getElementsByTagName("manufacturerPAN").item(0).getTextContent(), element.getElementsByTagName("itemsInStock").item(0).getTextContent(), element.getElementsByTagName("stockAddress").item(0).getTextContent());
                    }
                }
            }
        }
        catch (ParserConfigurationException | SAXException | IOException ex) {
            //Logger.getLogger(ReadXMLFileDOMExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Item> updateListOfItems(List<Item> items, List<Item> itemsForDelete){
        for(int i = 0; i < items.size(); i++){
            for(int j = 0; j < itemsForDelete.size(); j++){
                if(items.get(i).getItemName().equals(itemsForDelete.get(j).getItemName()) && items.get(i).getManufacturerName().equals(itemsForDelete.get(j).getManufacturerName()) && items.get(i).getManufacturerPAN().equals(itemsForDelete.get(j).getManufacturerPAN()) && items.get(i).getItemsInStock().equals(itemsForDelete.get(j).getItemsInStock()) && items.get(i).getStockAddress().equals(itemsForDelete.get(j).getStockAddress())) {
                    items.remove(i);
                    i--;
                }
            }
        }
        return items;
    }

    public static void updateXMLFile(List <Item> list){
        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("stock");
            document.appendChild(rootElement);
            Node root = document.getDocumentElement();
                for(int i = 0; i<list.size(); i++) {
                    addNewItem(root, document, list.get(i).getItemName(), list.get(i).getManufacturerName(), list.get(i).getManufacturerPAN(), list.get(i).getItemsInStock(), list.get(i).getStockAddress());
                }
            // Записываем XML в файл
            writeDocument(document);
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        }
    }

    // Функция добавления нового товара и записи результата в файл
    private static void addNewItem(Node root, Document document, String itemNameText, String manufacturerNameText, String manufacturerPANText, String itemsInStockText, String stockAddressText) throws TransformerFactoryConfigurationError, DOMException {
        // Создаем новый товар по элементам
        // Сам товар <item>
        Element item = document.createElement("item");
        // <itemName>
        Element itemName = document.createElement("itemName");
        // Устанавливаем значение текста внутри тега
        itemName.setTextContent(itemNameText);
        // <manufacturerName>
        Element manufacturerName = document.createElement("manufacturerName");
        manufacturerName.setTextContent(manufacturerNameText);
        // <manufacturerPAN>
        Element manufacturerPAN = document.createElement("manufacturerPAN");
        manufacturerPAN.setTextContent(manufacturerPANText);
        // <itemsInStock>
        Element itemsInStock = document.createElement("itemsInStock");
        itemsInStock.setTextContent(itemsInStockText);
        Element stockAddress = document.createElement("stockAddress");
        stockAddress.setTextContent(stockAddressText);

        // Добавляем внутренние элементы товара в элемент <item>
        item.appendChild(itemName);
        item.appendChild(manufacturerName);
        item.appendChild(manufacturerPAN);
        item.appendChild(itemsInStock);
        item.appendChild(stockAddress);
        // Добавляем товар в корневой элемент
        root.appendChild(item);
    }

    // Функция для сохранения DOM в файл
    private static void writeDocument(Document document) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream("stocks.xml");
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
