package com.wpm.account.persist;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

public class AccountPersistServiceImpl {
    private String file;
    private SAXReader reader = new SAXReader();

    private Document readDocument() throws AccountPersistException{
        File dataFile = new File(file);
        if(!dataFile.exists()){
            dataFile.getParentFile().mkdirs();
            Document doc = DocumentFactory.getInstance().createDocument();
            Element rootEle = doc.addElement(ELEMENT_ROOT);
            ((Element) rootEle).addElement(ELEMENT_ACCOUNTS);
            writeDocument(doc);
        }
    }
}
