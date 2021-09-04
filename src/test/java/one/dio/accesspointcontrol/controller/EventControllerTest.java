package one.dio.accesspointcontrol.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;

import static org.hamcrest.core.Is.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import static one.dio.accesspointcontrol.utils.JsonConverterUtils.asJsonString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import one.dio.accesspointcontrol.dto.EventDTO;
import one.dio.accesspointcontrol.dtofactory.EventDTOFactory;
import one.dio.accesspointcontrol.exception.EventNotFoundException;
import one.dio.accesspointcontrol.service.EventService;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {
    
    private static final String API_URL_PATH = "/api/v1/events";
    private static final long NOT_EXISTING__ID = 99l;

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(eventController)
                                 .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                 .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                                 .build();
    }

    @Test
    void whenPOSTrequest_withNotBlankName_eventShouldBeCreated() throws Exception {
        // given
        EventDTO eventDTO = EventDTOFactory.builder().build().dto();

        // when
        when(eventService.create(eventDTO)).thenReturn(eventDTO);

        // then
        mockMvc.perform(post(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(eventDTO)))
               .andExpect(jsonPath("$.id", is((int) eventDTO.getId())))
               .andExpect(jsonPath("$.name", is(eventDTO.getName())));
    }

    @Test 
    void whenPOSTrequest_withBlankName_returnBadRequest() throws Exception {
        // given
        EventDTO eventDTO = EventDTOFactory.builder().build().dto();
        eventDTO.setName(" ");

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(eventDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETrequest_returnOk() throws Exception {
        // given
        EventDTO eventDTO = EventDTOFactory.builder().build().dto();

        // when
        when(eventService.findAll()).thenReturn(Collections.singletonList(eventDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id", is((int) eventDTO.getId())))
               .andExpect(jsonPath("$[0].name", is(eventDTO.getName())));
    }

    @Test
    void whenGETrequest_withExistingId_returnOk() throws Exception {
        // given
        EventDTO eventDTO = EventDTOFactory.builder().build().dto();        

        // when
        when(eventService.findById(eventDTO.getId())).thenReturn(eventDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + eventDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) eventDTO.getId())))
                .andExpect(jsonPath("$.name", is(eventDTO.getName())));
    }

    @Test
    void whenGETrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        EventDTO eventDTO = EventDTOFactory.builder().build().dto();        

        // when
        when(eventService.findById(eventDTO.getId())).thenThrow(EventNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + eventDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTrequest_withExistingId_eventShouldBeUpdated() throws Exception {
        // given
        EventDTO toUpdateEventDTO = EventDTOFactory.builder().build().dto();
        EventDTO updatedEventDTO = toUpdateEventDTO;        
        updatedEventDTO.setDescription("It has changed");

        doReturn(updatedEventDTO)
            .when(eventService).update(updatedEventDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(updatedEventDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is((int) toUpdateEventDTO.getId())))
               .andExpect(jsonPath("$.name", is(toUpdateEventDTO.getName())));
    }

    @Test
    void whenPUTrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        EventDTO toUpdateEventDTO = EventDTOFactory.builder().build().dto();
        toUpdateEventDTO.setId(NOT_EXISTING__ID);

        // when
        doThrow(EventNotFoundException.class)
            .when(eventService).update(toUpdateEventDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(toUpdateEventDTO)))
               .andExpect(status().isNotFound());
    }

    @Test
    void whenDELETErequest_withExistingId_returnNoContent() throws Exception {
        // given
        EventDTO toDeleteEventDTO = EventDTOFactory.builder().build().dto();

        // when
        doNothing().when(eventService).deleteById(toDeleteEventDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + toDeleteEventDTO.getId())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETErequest_withNotExistingId_returnNotFound() throws Exception {
        // when
        doThrow(EventNotFoundException.class)
            .when(eventService).deleteById(NOT_EXISTING__ID);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + NOT_EXISTING__ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
