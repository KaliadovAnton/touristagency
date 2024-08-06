package anton.kaliadau.touristagency.sight;

import anton.kaliadau.AbstractCommonIntegrationTest;
import anton.kaliadau.touristagency.config.LiquibaseConfig;
import anton.kaliadau.touristagency.config.SpringConfig;
import anton.kaliadau.touristagency.place.PlaceService;
import anton.kaliadau.touristagency.sight.model.Sight;
import anton.kaliadau.touristagency.sight.model.SightDTO;
import com.google.gson.reflect.TypeToken;
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

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class, LiquibaseConfig.class})
@WebAppConfiguration("")
class SightControllerTest extends AbstractCommonIntegrationTest {
    @Autowired
    private PlaceService placeService;
    @Autowired
    private SightRepository sightRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void controllerExists() {
        //given: servlet context
        var servletContext = webApplicationContext.getServletContext();

        //expect: servlet and controller exist
        assertNotNull(servletContext);
        assertInstanceOf(MockServletContext.class, servletContext);
        assertNotNull(webApplicationContext.getBean("sightController"));
    }

    @Test
    void save() throws Exception {
        //given: new sight
        var body = gson.toJson(newSightDTO);
        //when: request to save sight is sent
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/sight/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //then: sight is saved
        var result = gson.fromJson(response.getResponse().getContentAsString(), SightDTO.class);
        assertEquals(newSightDTO.id(), result.id());
    }

    @Test
    void getAll() throws Exception {
        //given: there are sights
        var sights = sightRepository.findAll();
        var existingIndexes = sights.stream().map(Sight::getId).toList();
        //when: request to get all
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/sight/get"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //then: expect they are the same
        var result = (List<SightDTO>) gson.fromJson(response.getResponse().getContentAsString(),
                new TypeToken<List<SightDTO>>() {
                }.getType());
        var resultIndexes = result.stream().map(SightDTO::id).toList();
        assertEquals(sights.size(), result.size());
        assertEquals(existingIndexes, resultIndexes);
    }

    @Test
    void getSightsFromPlace() throws Exception {
        //given: existing place name
        var place = placeService.findById(2L);
        var expected = 1;
        //when: request sent to get all from place
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/sight/get/place/" + place.getPlaceName()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //then: expect they are the same
        var result = (List<SightDTO>) gson.fromJson(response.getResponse().getContentAsString(),
                new TypeToken<List<SightDTO>>() {
                }.getType());
        assertEquals(expected, result.size());
    }

    @Test
    void updateSightsDescription() throws Exception {
        //given: existing new sight
        var body = gson.toJson(newSightDTO);
        //when: request sent to update sight description
        mockMvc.perform(MockMvcRequestBuilders.put("/sight/update/description")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        var result = sightRepository.findAllBySightName(newSightDTO.sightName()).get(0);
        //then: expect they are the same
        assertEquals(newSightDTO.description(), result.getDescription());
    }

    @Test
    void deleteSight() throws Exception {
        //given: existing sight
        var sightName = sightRepository.findAll().get(1).getSightName();

        //when: deleting existing sight
        mockMvc.perform(MockMvcRequestBuilders.delete("/sight/remove/" + sightName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        //then: sight is deleted
        assertTrue(sightRepository.findAll().stream().noneMatch(sight1 -> Objects.equals(sight1.getSightName(), sightName)));
    }
}