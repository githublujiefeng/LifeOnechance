package com.learn.model_GoF_23.Builder;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

public class ReadXML {
    public static Object getObject(){
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc= builder.parse(new File(""));
            NodeList n1 = doc.getElementsByTagName("className");
            Node classNode = n1.item(0).getFirstChild();
            String cName = ""+classNode.getNodeValue();
            System.out.println("新类名："+cName);
            Class<?> c = Class.forName(cName);
            Object object = c.newInstance();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
