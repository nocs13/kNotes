package com.kgm.kNotes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Notes {
	private XmlPullParserFactory xml_object = null;
	private XmlPullParser xml_parser = null;

	private String dbFile = "";
	private Document xml_doc = null;

	public Notes(String dbFolder) {
		try {
			dbFile = dbFolder + "/kdbnotes.db";
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);

			DocumentBuilder db = dbf.newDocumentBuilder();
			DocumentBuilder builder = dbf.newDocumentBuilder();

			File file = new File(dbFile);

			if (!file.exists()) {
				xml_doc = builder.newDocument();
				
				Element e = xml_doc.createElement("Notes");
				xml_doc.appendChild(e);
				
				update();
			} else {
				xml_doc = db.parse(new FileInputStream(file));
			}
		} catch (Exception e) {
			String msg = e.getMessage();
			System.out.print("Notes::Notes error: " + msg);
		}
	}

	private void update() {
		String data = xml_doc.toString();
		
		data = xml_doc.getTextContent();
		
		System.out.print("kDbPricer::update saving: " + data);

		try {
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(xml_doc), new StreamResult(writer));
			data = writer.getBuffer().toString().replaceAll("\n|\r", "");
			
			FileWriter file = new FileWriter(dbFile);

			file.write(data);
			file.close();
		} catch (Exception e) {
			String msg = e.getMessage();			
			System.out.print("Notes::update error: " + e.getMessage());
		}
	}

	ArrayList<Note> getNotes() {
		ArrayList<Note> list = new ArrayList<Note>();
		
		NodeList nodes = null;
		
		try {
			nodes = xml_doc.getElementsByTagName("Note");
		} catch (Exception e) {
			String msg = e.getMessage();			
			System.out.print("Notes::getNotes error: " + e.getMessage());
		}
		
		if (nodes == null)
			return list;
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Element el = (Element) nodes.item(i);
			
			String title = ((Element)el.getElementsByTagName("Title").item(0)).getTextContent();
			String date = ((Element)el.getElementsByTagName("Date").item(0)).getTextContent();
			String data = ((Element)el.getElementsByTagName("Data").item(0)).getFirstChild().getNodeValue();
			
			data = data.replaceAll("&amp;#13;", "\n");
			
			Note note = new Note(data, date);
			
			list.add(note);
		}
		
		return list;
	}
	
	boolean add(Note note) {
		System.out.println("Adding note");
		if (note == null)
			return false;
		System.out.println("Adding note 1");
		
		Element doc = null;
		
		try {
			doc = xml_doc.getDocumentElement();
		} catch (Exception e) {
			String msg = e.getMessage();
			System.out.println("Notes::getNotes error: " + e.getMessage());
		}
		
		if (doc == null)
			return false;
		
		Element el = xml_doc.createElement("Note");
		
		Element e = null;
		
		e = xml_doc.createElement("Date");
		e.setTextContent(note.getDate());
		el.appendChild(e);
		
		String cnote = note.getNote();
		
		cnote = cnote.replace("\n", "&amp;#13;");
		
		e = xml_doc.createElement("Data");
		e.appendChild(xml_doc.createCDATASection(cnote));
		el.appendChild(e);
		
		doc.appendChild(el);
		
		update();

		return true;
	}
	
	boolean remove(Note note) {
		if (note == null)
			return false;
		
		Element doc = xml_doc.getDocumentElement();
		
		NodeList nl = xml_doc.getElementsByTagName("Title");

		for (int i = 0; i < nl.getLength(); i++) {
			Element el = (Element) nl.item(i);
			
			el = (Element) el.getParentNode();
			
			String title = el.getElementsByTagName("Title").item(0).getTextContent();
			String date = el.getElementsByTagName("Date").item(0).getTextContent();
			
			if (date.equals(note.getDate())) {
				doc.removeChild(el);
			}
		}
		
		update();

		return false;
	}
}
