package tim.infovus.newicon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import max.core.MaxException;
import tim.infobus.configuration.XMLConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tim.infovus.newicon.bean.response.DocumentResponse;
import tim.infovus.newicon.bean.response.ServiceSettingResponse;
import tim.infovus.newicon.service.DocumentService;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

@RestController
@RequestMapping(value = "XmlApi/")
public class ControllerXML {

    @Autowired
    private DocumentService ds;

    private static final Logger logger = LoggerFactory.getLogger(ControllerXML.class);

    @GetMapping(value="get/document/repository")
    public ResponseEntity<DocumentResponse> getDocumentRepositoryController() throws XMLConfigException, MaxException {
        String[] ris;
        logger.warn(String.format("metodo getDocumentRepository chiamato"));
        ris = ds.getDocumentRepository();
        HttpHeaders hh = new HttpHeaders();
        hh.setContentType(MediaType.APPLICATION_JSON);
        DocumentResponse res = new DocumentResponse();
        res.setDocumentNames(ris);
        return new ResponseEntity<DocumentResponse>(res,hh, HttpStatus.CREATED);
    }

    //sostituire con post
    @GetMapping(value="set/document/repository")
    public ResponseEntity<ServiceSettingResponse> setDocumentRepositoryController() throws XMLConfigException, MaxException, ParserConfigurationException, TransformerConfigurationException {
        logger.warn(String.format("metodo setDocumentRepository chiamato"));
        String response = ds.setDocumentRepository();
        HttpHeaders hh = new HttpHeaders();
        hh.setContentType(MediaType.APPLICATION_JSON);
        ServiceSettingResponse res = new ServiceSettingResponse();
        res.setServiceSettingResponse(response);
        return new ResponseEntity<ServiceSettingResponse>(res,hh, HttpStatus.CREATED);
    }

}