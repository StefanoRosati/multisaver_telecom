package tim.infovus.newicon.service;

import max.core.MaxException;
import max.documents.*;
import max.xml.DocumentModel;
import max.xml.XMLBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import tim.infobus.configuration.XMLConfigException;
import tim.infovus.newicon.bean.response.DocumentResponse;
import tim.infovus.newicon.controller.ControllerXML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class DocumentService {
    private static final Logger logger = LoggerFactory.getLogger(ControllerXML.class);

    public String[] getDocumentRepository() throws XMLConfigException, MaxException {
        DocumentRepository documentRepository = getDocumentList();
        logger.info(documentRepository.toString());
        logger.info(documentRepository.getDocumentNames().toString());
        String[] documentNames = documentRepository.getDocumentNames();
        List documentRepositoryNamesList = Arrays.asList(documentNames);
        documentRepositoryNamesList.stream().forEach(i -> logger.info("DocumentRepositoryName: {}", i));
        //logger.info("DocumetRepository:{}",documentRepository.getDocumentNames());
        logger.info("DocumentRepository:{}", documentRepository.getDocumentNames().length);
        return documentNames;
    }

    private DocumentRepository getDocumentList() throws XMLConfigException, MaxException {
        DocumentRepository dr = null;
        String [] documentList = new String[0];
        dr = DocumentRepository.instance();
        documentList = dr.getDocumentNames();
        Arrays.sort(documentList);
        XMLBuilder s;
        return dr;
    }
    public String setDocumentRepository() throws XMLConfigException, MaxException, ParserConfigurationException, TransformerConfigurationException {
        DocumentRepository docrepo = DocumentRepository.instance();
        DocumentProxy docproxy = docrepo.getDocumentProxy("documents");
        logger.info("instanceof:{}",docproxy.getClass());
        logger.info("DocumentRepositoryName: {}", docrepo.getDocumentNames());
        logger.info("DocumentProxy: {}", docproxy);
        DocumentProxy fs = docrepo.getDocumentProxy("InfobusServicesConfig-R4J-3");
        Document documento = docrepo.getDocument("InfobusServicesConfig-R4J-3"); //pdfdoc
        documento = modificaInfoBusConfigService3(documento);
        fs.save(documento);
        docproxy.save(documento);
        return "OK";
    }

    private Document modificaInfoBusConfigService3(Document documento){
        NodeList services = documento.getElementsByTagName("Services");
        NodeList service = documento.getElementsByTagName("Service");
        Node ser;
        NamedNodeMap attributes;
        Node attribute;
        int dim = service.getLength();
        for(int i=0;i<dim;i++){
            ser = service.item(i);
            attributes = ser.getAttributes();
            attribute = attributes.item(0);
            attribute.setNodeValue("TEST_STEFANO");
        }
        return documento;
    }
}