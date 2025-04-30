package com.prabhu.myapp.utils.testdata;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.File;

public class XmlDataReader {
    public static Document readXml(String filePath) throws Exception {
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new File(filePath));
    }
}
