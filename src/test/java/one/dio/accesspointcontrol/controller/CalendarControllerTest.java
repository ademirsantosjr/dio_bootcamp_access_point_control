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

import one.dio.accesspointcontrol.dto.CalendarDTO;
import one.dio.accesspointcontrol.dtofactory.CalendarDTOFactory;
import one.dio.accesspointcontrol.exception.CalendarNotFoundException;
import one.dio.accesspointcontrol.service.CalendarService;

@ExtendWith(MockitoExtension.class)
public class CalendarControllerTest {
    
    private static final String API_URL_PATH = "/api/v1/calendars";
    private static final long NOT_EXISTING__ID = 99l;

    private MockMvc mockMvc;

    @Mock
    private CalendarService calendarService;

    @InjectMocks
    private CalendarController calendarController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(calendarController)
                                 .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                 .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                                 .build();
    }

    @Test
    void whenPOSTrequest_withNotBlankName_calendarShouldBeCreated() throws Exception {
        // given
        CalendarDTO calendarDTO = CalendarDTOFactory.builder().build().dto();

        // when
        when(calendarService.create(calendarDTO)).thenReturn(calendarDTO);

        // then
        mockMvc.perform(post(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(calendarDTO)))
               .andExpect(jsonPath("$.id", is((int) calendarDTO.getId())))
               .andExpect(jsonPath("$.description", is(calendarDTO.getDescription())));
    }

    @Test 
    void whenPOSTrequest_withBlankName_returnBadRequest() throws Exception {
        // given
        CalendarDTO calendarDTO = CalendarDTOFactory.builder().build().dto();
        calendarDTO.setDescription(" ");

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(calendarDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETrequest_returnOk() throws Exception {
        // given
        CalendarDTO calendarDTO = CalendarDTOFactory.builder().build().dto();

        // when
        when(calendarService.findAll()).thenReturn(Collections.singletonList(calendarDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id", is((int) calendarDTO.getId())))
               .andExpect(jsonPath("$[0].description", is(calendarDTO.getDescription())));
    }

    @Test
    void whenGETrequest_withExistingId_returnOk() throws Exception {
        // given
        CalendarDTO calendarDTO = CalendarDTOFactory.builder().build().dto();        

        // when
        when(calendarService.findById(calendarDTO.getId())).thenReturn(calendarDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + calendarDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) calendarDTO.getId())))
                .andExpect(jsonPath("$.description", is(calendarDTO.getDescription())));
    }

    @Test
    void whenGETrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        CalendarDTO calendarDTO = CalendarDTOFactory.builder().build().dto();        

        // when
        when(calendarService.findById(calendarDTO.getId())).thenThrow(CalendarNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + calendarDTO.getId())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTrequest_withExistingId_calendarShouldBeUpdated() throws Exception {
        // given
        CalendarDTO toUpdateCalendarDTO = CalendarDTOFactory.builder().build().dto();
        CalendarDTO updatedCalendarDTO = toUpdateCalendarDTO;        
        updatedCalendarDTO.setDescription("It has changed");

        doReturn(updatedCalendarDTO)
            .when(calendarService).update(updatedCalendarDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(updatedCalendarDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is((int) toUpdateCalendarDTO.getId())))
               .andExpect(jsonPath("$.description", is(toUpdateCalendarDTO.getDescription())));
    }

    @Test
    void whenPUTrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        CalendarDTO toUpdateCalendarDTO = CalendarDTOFactory.builder().build().dto();
        toUpdateCalendarDTO.setId(NOT_EXISTING__ID);

        // when
        doThrow(CalendarNotFoundException.class)
            .when(calendarService).update(toUpdateCalendarDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(toUpdateCalendarDTO)))
               .andExpect(status().isNotFound());
    }

    @Test
    void whenDELETErequest_withExistingId_returnNoContent() throws Exception {
        // given
        CalendarDTO toDeleteCalendarDTO = CalendarDTOFactory.builder().build().dto();

        // when
        doNothing().when(calendarService).deleteById(toDeleteCalendarDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + toDeleteCalendarDTO.getId())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETErequest_withNotExistingId_returnNotFound() throws Exception {
        // when
        doThrow(CalendarNotFoundException.class)
            .when(calendarService).deleteById(NOT_EXISTING__ID);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + NOT_EXISTING__ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
