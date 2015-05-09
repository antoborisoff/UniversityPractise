package main.java.XMLStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import main.java.Message.Message;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class XMLHistoryUtil {
	private static final String STORAGE_LOCATION = "D:\\UP2015\\WebChatApplication" +  File.separator + "history.xml";
	private static final String HISTORY = "HISTORY";
	private static final String MESSAGES = "MESSAGES";
	private static final String ACTIONS = "ACTIONS";

	private static final String MESSAGE = "message";
    private static final String ACTION = "action";
	private static final String MESSAGETEXT = "messagetext";
	private static final String ID = "id";
	private static final String USERNAME = "username";
	private static final String IDCLIENT = "idClient";

	private XMLHistoryUtil() {
	}

	public static synchronized void createStorage() throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement(HISTORY);
		doc.appendChild(rootElement);

		Element messages=doc.createElement(MESSAGES);
		rootElement.appendChild(messages);
		Element actions=doc.createElement(ACTIONS);
		rootElement.appendChild(actions);

		Transformer transformer = getTransformer();

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(STORAGE_LOCATION));
		transformer.transform(source, result);
	}

	public static synchronized void addMessage(Message message) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(STORAGE_LOCATION);
		document.getDocumentElement().normalize();
		
		Element root = document.getDocumentElement();

		Node messages=root.getElementsByTagName(MESSAGES).item(0);
		
		Element messageElement = document.createElement(MESSAGE);
		messages.appendChild(messageElement);

		messageElement.setAttribute(ID, message.getId());

		Element messagetext = document.createElement(MESSAGETEXT);
		messagetext.appendChild(document.createTextNode(message.getMessageText()));
		messageElement.appendChild(messagetext);

		Element username = document.createElement(USERNAME);
		username.appendChild(document.createTextNode(message.getUsername()));
		messageElement.appendChild(username);

		Element idClient = document.createElement(IDCLIENT);
		idClient.appendChild(document.createTextNode(message.getIDClient()));
		messageElement.appendChild(idClient);


		DOMSource source = new DOMSource(document);

		Transformer transformer = getTransformer();

		StreamResult result = new StreamResult(STORAGE_LOCATION);
		transformer.transform(source, result);
	}

	public static synchronized void addAction(Message action) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(STORAGE_LOCATION);
		document.getDocumentElement().normalize();

		Element root = document.getDocumentElement();

		Node messages=root.getElementsByTagName(ACTIONS).item(0);

		Element actionElement = document.createElement(ACTION);
		messages.appendChild(actionElement);

		actionElement.setAttribute(ID, action.getId());

		Element messagetext = document.createElement(MESSAGETEXT);
		messagetext.appendChild(document.createTextNode(action.getMessageText()));
		actionElement.appendChild(messagetext);

		Element username = document.createElement(USERNAME);
		username.appendChild(document.createTextNode(action.getUsername()));
		actionElement.appendChild(username);

		Element idClient = document.createElement(IDCLIENT);
		idClient.appendChild(document.createTextNode(action.getIDClient()));
		actionElement.appendChild(idClient);


		DOMSource source = new DOMSource(document);

		Transformer transformer = getTransformer();

		StreamResult result = new StreamResult(STORAGE_LOCATION);
		transformer.transform(source, result);
	}

	public static synchronized boolean doesStorageExist() {
		File file = new File(STORAGE_LOCATION);
		return file.exists();
	}

	public static synchronized List<Message> getMessages() throws SAXException, IOException, ParserConfigurationException {
		List<Message> messages = new ArrayList<Message>();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(STORAGE_LOCATION);
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
        Element mes=(Element) root.getElementsByTagName(MESSAGES).item(0);
		NodeList messageList = mes.getElementsByTagName(MESSAGE);
		for (int i = 0; i < messageList.getLength(); i++) {
			Element messageElement = (Element) messageList.item(i);
			String id = messageElement.getAttribute(ID);
			String username = messageElement.getElementsByTagName(USERNAME).item(0).getTextContent();
            String idClient = messageElement.getElementsByTagName(IDCLIENT).item(0).getTextContent();
            String messagetext = messageElement.getElementsByTagName(MESSAGETEXT).item(0).getTextContent();
			messages.add(new Message(id, messagetext, username,idClient));
		}
		return messages;
	}

    public static synchronized List<Message> getActions() throws SAXException, IOException, ParserConfigurationException {
        List<Message> actions = new ArrayList<Message>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(STORAGE_LOCATION);
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        Element act=(Element)root.getElementsByTagName(ACTIONS).item(0);
        NodeList actionList = act.getElementsByTagName(ACTION);
        for (int i = 0; i < actionList.getLength(); i++) {
            Element actionElement = (Element) actionList.item(i);
            String id = actionElement.getAttribute(ID);
            String username = actionElement.getElementsByTagName(USERNAME).item(0).getTextContent();
            String idClient = actionElement.getElementsByTagName(IDCLIENT).item(0).getTextContent();
            String messagetext = actionElement.getElementsByTagName(MESSAGETEXT).item(0).getTextContent();
            actions.add(new Message(id, messagetext, username, idClient));
        }
        return actions;
    }

	private static Transformer getTransformer() throws TransformerConfigurationException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		// Formatting XML properly
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		return transformer;
	}

}
