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

import one.dio.accesspointcontrol.dto.DateFormatDTO;
import one.dio.accesspointcontrol.dtofactory.DateFormatDTOFactory;
import one.dio.accesspointcontrol.exception.DateFormatNotFoundException;
import one.dio.accesspointcontrol.service.DateFormatService;

@ExtendWith(MockitoExtension.class)
public class DateFormatControllerTest {
    
    private static final String API_URL_PATH = "/api/v1/dateformats";
    private static final long NOT_EXISTING__ID = 99l;

    private MockMvc mockMvc;

    @Mock
    private DateFormatService dateFormatService;

    @InjectMocks
    private DateFormatController dateFormatController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dateFormatController)
                                 .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                 .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                                 .build();
    }

    @Test
    void whenPOSTrequest_withNotBlankName_dateFormatShouldBeCreated() throws Exception {
        // given
        DateFormatDTO dateFormatDTO = DateFormatDTOFactory.builder().build().dto();

        // when
        when(dateFormatService.create(dateFormatDTO)).thenReturn(dateFormatDTO);

        // then
        mockMvc.perform(post(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(dateFormatDTO)))
               .andExpect(jsonPath("$.id", is((int) dateFormatDTO.getId())))
               .andExpect(jsonPath("$.description", is(dateFormatDTO.getDescription())));
    }

    @Test 
    void whenPOSTrequest_withBlankName_returnBadRequest() throws Exception {
        // given
        DateFormatDTO dateFormatDTO = DateFormatDTOFactory.builder().build().dto();
        dateFormatDTO.setDescription(" ");

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dateFormatDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETrequest_returnOk() throws Exception {
        // given
        DateFormatDTO dateFormatDTO = DateFormatDTOFactory.builder().build().dto();

        // when
        when(dateFormatService.findAll()).thenReturn(Collections.singletonList(dateFormatDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id", is((int) dateFormatDTO.getId())))
               .andExpect(jsonPath("$[0].description", is(dateFormatDTO.getDescription())));
    }

    @Test
    void whenGETrequest_withExistingId_returnOk() throws Exception {
        // given
        DateFormatDTO dateFormatDTO = DateFormatDTOFactory.builder().build().dto();        

        // when
        when(dateFormatService.findById(dateFormatDTO.getId())).thenReturn(dateFormatDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + dateFormatDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) dateFormatDTO.getId())))
                .andExpect(jsonPath("$.description", is(dateFormatDTO.getDescription())));
    }

    @Test
    void whenGETrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        DateFormatDTO dateFormatDTO = DateFormatDTOFactory.builder().build().dto();        

        // when
        when(dateFormatService.findById(dateFormatDTO.getId())).thenThrow(DateFormatNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + dateFormatDTO.getId())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTrequest_withExistingId_dateFormatShouldBeUpdated() throws Exception {
        // given
        DateFormatDTO toUpdateDateFormatDTO = DateFormatDTOFactory.builder().build().dto();
        DateFormatDTO updatedDateFormatDTO = toUpdateDateFormatDTO;        
        updatedDateFormatDTO.setDescription("It has changed");

        doReturn(updatedDateFormatDTO)
            .when(dateFormatService).update(updatedDateFormatDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(updatedDateFormatDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is((int) toUpdateDateFormatDTO.getId())))
               .andExpect(jsonPath("$.description", is(toUpdateDateFormatDTO.getDescription())));
    }

    @Test
    void whenPUTrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        DateFormatDTO toUpdateDateFormatDTO = DateFormatDTOFactory.builder().build().dto();
        toUpdateDateFormatDTO.setId(NOT_EXISTING__ID);

        // when
        doThrow(DateFormatNotFoundException.class)
            .when(dateFormatService).update(toUpdateDateFormatDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(toUpdateDateFormatDTO)))
               .andExpect(status().isNotFound());
    }

    @Test
    void whenDELETErequest_withExistingId_returnNoContent() throws Exception {
        // given
        DateFormatDTO toDeleteDateFormatDTO = DateFormatDTOFactory.builder().build().dto();

        // when
        doNothing().when(dateFormatService).deleteById(toDeleteDateFormatDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + toDeleteDateFormatDTO.getId())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETErequest_withNotExistingId_returnNotFound() throws Exception {
        // when
        doThrow(DateFormatNotFoundException.class)
            .when(dateFormatService).deleteById(NOT_EXISTING__ID);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + NOT_EXISTING__ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
