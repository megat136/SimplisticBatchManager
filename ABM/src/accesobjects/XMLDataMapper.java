package accesobjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import models.BatFileModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import utils.ExecutionTimePredictor;
import exceptions.PersistenceException;

public class XMLDataMapper implements IDataMapper {
	String fileLocation = System.getProperty("user.dir")
			+ "\\abmfiles\\batfiles.xml";
	Logger logger = LoggerFactory.getLogger(XMLDataMapper.class);

	private XMLDataMapper() {

	}

	public boolean add(BatFileModel batFile) {
		Document doc = getDoc();

		Element rootElement = doc.getDocumentElement();

		Element batFileElement = doc.createElement("BatFile");
		rootElement.appendChild(batFileElement);

		Element batJobName = doc.createElement("Name");
		batJobName.appendChild(doc.createTextNode(batFile.getName()));
		batFileElement.appendChild(batJobName);

		Element batFileBriefDesc = doc.createElement("Desc");
		batFileBriefDesc.appendChild(doc.createTextNode(batFile.getDesc()));
		batFileElement.appendChild(batFileBriefDesc);

		Element batFileName = doc.createElement("FileName");
		batFileName.appendChild(doc.createTextNode(batFile.getFileName()));
		batFileElement.appendChild(batFileName);

		Element batCommandArgs = doc.createElement("CommandArgs");

		for (int k = 0; k < batFile.getCommandArgs().size(); k++) {
			Element commandArg = doc.createElement("CommandArg");
			// commandArg.setAttribute("ID", String.valueOf(k));
			commandArg.appendChild(doc.createTextNode(batFile.getCommandArgs()
					.get(k)));
			batCommandArgs.appendChild(commandArg);

		}
		batFileElement.appendChild(batCommandArgs);

		Element batCreationTime = doc.createElement("CreationTime");
		batCreationTime.appendChild(doc.createTextNode(batFile
				.getCreationTime()));
		batFileElement.appendChild(batCreationTime);

		Element batSchedulerPattern = doc.createElement("SchedulerPattern");
		batSchedulerPattern.appendChild(doc.createTextNode(batFile
				.getExecutionPattern()));
		batFileElement.appendChild(batSchedulerPattern);

		Element Active = doc.createElement("Active");
		Active.appendChild(doc.createTextNode(batFile.getActiveState()));
		batFileElement.appendChild(Active);

		saveDoc(doc);
		return true;

	}

	public BatFileModel find(String name) {

		Document doc = getDoc();
		BatFileModel foundedFile = new BatFileModel();

		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("BatFile");

		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				if (eElement.getElementsByTagName("Name").item(0)
						.getTextContent().equals(name)) {
					foundedFile.setName(eElement.getElementsByTagName("Name")
							.item(0).getTextContent());
					foundedFile.setDesc(eElement.getElementsByTagName("Desc")
							.item(0).getTextContent());
					foundedFile.setFileName(eElement
							.getElementsByTagName("FileName").item(0)
							.getTextContent());
					for (int k = 0; k < eElement.getElementsByTagName(
							"CommandArg").getLength(); k++) {
						foundedFile.addCommandArgs(eElement
								.getElementsByTagName("CommandArg").item(k)
								.getTextContent());
					}
					foundedFile.setCreationTime(eElement
							.getElementsByTagName("CreationTime").item(0)
							.getTextContent());
					foundedFile.setExecutionPattern(eElement
							.getElementsByTagName("SchedulerPattern").item(0)
							.getTextContent());

					foundedFile.setActiveState(eElement
							.getElementsByTagName("Active").item(0)
							.getTextContent());
					if (!foundedFile.getExecutionPattern().isEmpty()
							&& foundedFile.isActive()) {
						String nextExecutionTime = ExecutionTimePredictor
								.predictNext(foundedFile.getExecutionPattern());
						foundedFile.setNextExecution(nextExecutionTime);
					}
					return foundedFile;
				}

			}
		}
		return foundedFile;
	}

	public List<BatFileModel> findAll() {
		Document doc = getDoc();
		List<BatFileModel> foundedFiles = new ArrayList<BatFileModel>();

		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("BatFile");

		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				BatFileModel foundedFile = new BatFileModel();
				Element eElement = (Element) node;
				foundedFile.setName(eElement.getElementsByTagName("Name")
						.item(0).getTextContent());
				foundedFile.setDesc(eElement.getElementsByTagName("Desc")
						.item(0).getTextContent());
				foundedFile.setFileName(eElement
						.getElementsByTagName("FileName").item(0)
						.getTextContent());
				for (int k = 0; k < eElement.getElementsByTagName("CommandArg")
						.getLength(); k++) {
					foundedFile.addCommandArgs(eElement
							.getElementsByTagName("CommandArg").item(k)
							.getTextContent());
				}
				foundedFile.setCreationTime(eElement
						.getElementsByTagName("CreationTime").item(0)
						.getTextContent());
				foundedFile.setExecutionPattern(eElement
						.getElementsByTagName("SchedulerPattern").item(0)
						.getTextContent());
				foundedFile.setActiveState(eElement
						.getElementsByTagName("Active").item(0)
						.getTextContent());
				if (!foundedFile.getExecutionPattern().isEmpty()
						&& foundedFile.isActive()) {
					String nextExecutionTime = ExecutionTimePredictor
							.predictNext(foundedFile.getExecutionPattern());
					foundedFile.setNextExecution(nextExecutionTime);
				}

				foundedFiles.add(foundedFile);
			}

		}

		return foundedFiles;
	}

	public boolean remove(String name) {
		Document doc = getDoc();
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("BatFile");

		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				if (eElement.getElementsByTagName("Name").item(0)
						.getTextContent().equalsIgnoreCase(name)) {
					node.getParentNode().removeChild(eElement);
				}
			}

		}
		saveDoc(doc);
		return true;
	}

	public boolean update(BatFileModel updateFile) {
		remove(updateFile.getName());
		add(updateFile);
		return true;
	}

	public static XMLDataMapper getInstance() {
		return InstanceHolder.bao;
	}

	public static class InstanceHolder {
		static final XMLDataMapper bao = new XMLDataMapper();
	}

	public Document getDoc() throws PersistenceException {
		File inputFile;
		DocumentBuilderFactory docFactory;
		DocumentBuilder docBuilder;
		Document doc;
		try {
			inputFile = new File(fileLocation);
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(inputFile);
			return doc;
		} catch (ParserConfigurationException pce) {
			throw new PersistenceException("Parser Configuration fails.");
		} catch (SAXException saxe) {
			throw new PersistenceException(
					"Document is corrupted. Localized Message is: "
							+ saxe.getLocalizedMessage(), saxe);
		} catch (IOException ioe) {
			throw new PersistenceException(ioe);
		}
	}

	public void saveDoc(Document doc) throws PersistenceException {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileOutputStream(
					fileLocation));
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(domSource, result);
		} catch (FileNotFoundException fnfe) {
			throw new PersistenceException(
					"XML File not found! Please check directory", fnfe);
		} catch (TransformerException te) {
			throw new PersistenceException("Transformer exception.", te);
		}

	}
}
