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
        Element elemento = documento.getDocumentElement();
        //test sull'aggiunta di un service
        NodeList services = documento.getElementsByTagName("Services");
        int dim = services.getLength();
        NodeList service = documento.getElementsByTagName("Service");
        logger.info("Dime inziaile lista dei service: {}", service.getLength());
        //int serviceDim = service.getLength();
        //Node servicesNode = services.item(dim);
        //servicesNode.appendChild(services.item(0));
        Node testservice = service.item(0);
        Node figlio = testservice.getFirstChild();
        Node figlioClone = figlio.cloneNode(true);
        Node rimozione = testservice.removeChild(figlio);
        logger.info("Dime finale lista dei service: {}", service.getLength());
        Element elemento2 = documento.getDocumentElement();
        documento.getDocumentElement().appendChild(figlioClone);
        String versione = documento.getXmlVersion();
        NodeList groups = documento.getElementsByTagName("Groups");
        documento.setUserData(String.valueOf(Node.DOCUMENT_NODE), figlioClone, null);
        logger.info("Size documento: {}", groups.getLength());
        NodeList group = documento.getElementsByTagName("Group");
        logger.info("Versione: {}", versione);
        logger.info("Groups: {}", groups.getLength());
        logger.info("Group: {}", group.getLength());
        Document pdfDoc = docrepo.getDocument("pdfdoc"); //pdfdoc
        logger.info("info documento: {}", documento.getDocumentElement());
        Node n = groups.item(0);
        String n_locale_name = n.getLocalName();
        NamedNodeMap n_attributes = n.getAttributes();
        Object n_userdata = n.getUserData("");
        String n_node_name = n.getNodeName();
        String n_value = n.getNodeValue();
        n.setNodeValue("TEST2");
        logger.info("Size documento: {}", groups.getLength());
        logger.info("Nodo 1: {}", n);
        documento.createElement("test");//documento.appendChild(root);
        //documento.getFirstChild().appendChild(documento);
        //documento.appendChild(documento);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(documento);
        Node firstChild = documento.getFirstChild();
        logger.info("FirstChild: {}",firstChild.getOwnerDocument());
        StreamResult streamResult = new StreamResult(new File("test.xml"));
        logger.info("DocumentClass: {}", documento.getClass());
        //DocumentProxy docproxyInfoBusService3 = docrepo.getDocumentProxy("InfoBUS");
        docproxy.save(documento);
        //FileSystemDocProxy fsdp = new FileSystemDocProxy();
        //fsdp.init(documento);
        //DocumentProxy docproxyInfoBusService3 = docrepo.getDocumentProxy("document");
        //fsdp.init(documento);
        //docproxyInfoBusService3.save(documento);
        return null;
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