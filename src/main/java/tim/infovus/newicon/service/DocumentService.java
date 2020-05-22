package tim.infovus.newicon.service;

import max.core.MaxException;
import max.documents.*;
import max.xml.XMLBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import tim.infobus.configuration.XMLConfigException;
import tim.infovus.newicon.controller.ControllerXML;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//i documenti con <!DOCTYPE ...> generano excpetion alla seconda esecuzione dei metodi

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
        try {
            DocumentRepository docrepo = DocumentRepository.instance();
            DocumentProxy docproxy = docrepo.getDocumentProxy("documents");
            logger.info("instanceof:{}", docproxy.getClass());
            logger.info("DocumentRepositoryName: {}", docrepo.getDocumentNames());
            logger.info("DocumentProxy: {}", docproxy);
            DocumentProxy fs = docrepo.getDocumentProxy("InfobusServicesConfig-R4J-3");
            Document documento = docrepo.getDocument("InfobusServicesConfig-R4J-3"); //pdfdoc
            documento = modificaInfoBusConfigService3(documento);
            fs.save(documento);
            //docproxy.save(documento);
            return "OK";
        }
        catch(Exception e){
            return "KO";
        }
    }

    private Document modificaInfoBusConfigService3(Document documento){
        NodeList service = documento.getElementsByTagName("Service");
        Stream<Node> nodeStream = IntStream.range(0, service.getLength())
         .mapToObj(service::item);
        List<Node> nodeStreamModificato = nodeStream.map(x -> {
            x.getAttributes().item(0).setNodeValue("TEST_STEFANO_ROSATI");
            return x;
        }).collect(Collectors.toList());
        return documento;
        /**
         NodeList services = documento.getElementsByTagName("Services");
         Node ser;
         NamedNodeMap attributes;
         Node attribute;
         int dim = service.getLength();

         for(int i=0;i<dim;i++){
         ser = service.item(i);
         attributes = ser.getAttributes();
         attribute = attributes.item(0);
         attribute.setNodeValue("TEST_STEFANO");
         }**/
    }

    public String deleteElementInfoBusConfigService3() throws MaxException, XMLConfigException {
        try {
            DocumentRepository docrepo = DocumentRepository.instance();
            DocumentProxy fs = docrepo.getDocumentProxy("InfobusServicesConfig-R4J-3");
            Document documento = docrepo.getDocument("InfobusServicesConfig-R4J-3"); //pdfdoc
            //passando Services come argomento si cancella l'intera lista dei Services
            NodeList service = documento.getElementsByTagName("Service");
            Node e = service.item((0));
            e.getParentNode().removeChild(e);
            fs.save(documento);
            return "OK";
        }
        catch(Exception e){
            return "KO";
        }
/**
        Set<Element> targetElements = new HashSet<Element>();
        for (int i = 0; i < services.getLength(); i++) {
            Element e = (Element)services.item(i);
            if (certain criteria involving Element e) {
                targetElements.add(e);
            }
        }
        for (Element e: targetElements) {
            e.getParentNode().removeChild(e);
        }
 **/
    }
}