package com.marcusfromsweden.plantdoctor;

import com.jayway.jsonpath.JsonPath;
import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GrowingLocationControllerIT {

    private final Logger log = Logger.getLogger(GrowingLocationControllerIT.class.getName());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GrowingLocationRepository growingLocationRepository;

    private GrowingLocationDTO growingLocationDTO;

    @BeforeEach
    public void setup() {
        growingLocationDTO = GrowingLocationDTO.builder()
                .name("Pot 111")
                .occupied(true)
                .build();
    }

    @Test
    public void testCreateGrowingLocation() throws Exception {
        String growingLocationJson = "{\"name\":\"%s\",\"occupied\":%b}".formatted(
                growingLocationDTO.name(), growingLocationDTO.occupied());

        mockMvc.perform(post("/api/growing-locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(growingLocationJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(growingLocationDTO.name())))
                .andExpect(jsonPath("$.occupied", is(growingLocationDTO.occupied())));
    }

    @Test
    public void testGetGrowingLocation() throws Exception {
        // First, create a growing location
        String growingLocationJson = "{\"name\":\"%s\",\"occupied\":%b}".formatted(
                growingLocationDTO.name(), growingLocationDTO.occupied());

        String response = mockMvc.perform(post("/api/growing-locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(growingLocationJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Number idNumber = JsonPath.read(response, "$.id");
        Long id = idNumber.longValue();

        // Then, retrieve the created growing location
        mockMvc.perform(get("/api/growing-locations/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is(growingLocationDTO.name())))
                .andExpect(jsonPath("$.occupied", is(growingLocationDTO.occupied())));
    }
}