package org.task.result;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseFile {

	public void read() {
		try {
			File file = getFileFromResource("struct.xml");
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			System.out.println("Root element: " + document.getDocumentElement().getNodeName());
			if (document.hasChildNodes()) {
				printNodeList(document.getChildNodes());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void printNodeList(NodeList nodeList) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node elemNode = nodeList.item(count);
			if (elemNode.getNodeType() == Node.ELEMENT_NODE) {
				// get node name and value
				System.out.println("\nNode Name =" + elemNode.getNodeName() + " [OPEN]");
				System.out.println("Node Content =" + elemNode.getTextContent());
				if (elemNode.hasAttributes()) {
					NamedNodeMap nodeMap = elemNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						System.out.println("attr name : " + node.getNodeName());
						System.out.println("attr value : " + node.getNodeValue());
					}
				}
				if (elemNode.hasChildNodes()) {
					// recursive call if the node has child nodes
					printNodeList(elemNode.getChildNodes());
				}
				System.out.println("Node Name =" + elemNode.getNodeName() + " [CLOSE]");
			}
		}

	}

	private File getFileFromResource(String fileName) throws URISyntaxException {

		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return new File(resource.toURI());
		}
	}
}
