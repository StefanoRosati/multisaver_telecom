package tim.infovus.newicon.tests;

//import javafx.application.Application;
//import javafx.application.Application;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tim.infovus.newicon.NewiconApplication;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = NewiconApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestScrittura {

    private MockMvc mockMvc;
    String ApiBaseUrl = "http://localhost:8008/XmlApi/";
    @Autowired
    WebApplicationContext wac;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void updateService_NameAttribute() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(ApiBaseUrl + "set/document/repository")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                //.andExpect(jsonPath("$.status").value("200 OK"))
                .andExpect(jsonPath("$.serviceSettingResponse").value("OK"));
    }
    /**
    @Test
    public void updateService_NameAttribute_verifica_not_ko() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(ApiBaseUrl + "set/document/repository")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                //.andExpect(jsonPath("$.status").value("200 OK"))
                .andExpect(jsonPath("$.serviceSettingResponse").value("KO"));
    }
    **/

}
