package anton.kaliadau.touristagency.place;

import anton.kaliadau.AbstractCommonIntegrationTest;
import anton.kaliadau.touristagency.config.LiquibaseConfig;
import anton.kaliadau.touristagency.config.SpringConfig;
import anton.kaliadau.touristagency.place.model.PlaceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class, LiquibaseConfig.class})
@WebAppConfiguration("")
class PlaceControllerTest extends AbstractCommonIntegrationTest {
    @Autowired
    private PlaceService placeService;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        placeRepository.deleteById(newPlaceDTO2.id());
    }

    @Test
    public void controllerExists() {
        //given: servlet context
        var servletContext = webApplicationContext.getServletContext();

        //expect: servlet and controller exist
        assertNotNull(servletContext);
        assertInstanceOf(MockServletContext.class, servletContext);
        assertNotNull(webApplicationContext.getBean("placeController"));
    }

    @Test
    public void updatePlace() throws Exception {
        //given: place to update
        var body = gson.toJson(newPlaceDTO);
        //when: request to update place is sent
        //expect: successful request
        this.mockMvc.perform(MockMvcRequestBuilders.put("/place/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void savePlace() throws Exception {
        //given: place to save
        var body = gson.toJson(newPlaceDTO2);
        //when: request to save place is sent
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/place/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //expect: successful request
        var result = gson.fromJson(response.getResponse().getContentAsString(), PlaceDTO.class);
        assertEquals(newPlaceDTO2, result);
    }
}