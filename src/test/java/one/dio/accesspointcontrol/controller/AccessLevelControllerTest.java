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

import one.dio.accesspointcontrol.dto.AccessLevelDTO;
import one.dio.accesspointcontrol.dtofactory.AccessLevelDTOFactory;
import one.dio.accesspointcontrol.exception.AccessLevelNotFoundException;
import one.dio.accesspointcontrol.service.AccessLevelService;

@ExtendWith(MockitoExtension.class)
public class AccessLevelControllerTest {
    
    private static final String API_URL_PATH = "/api/v1/accesslevels";
    private static final long NOT_EXISTING__ID = 99l;

    private MockMvc mockMvc;

    @Mock
    private AccessLevelService accessLevelService;

    @InjectMocks
    private AccessLevelController accessLevelController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accessLevelController)
                                 .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                 .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                                 .build();
    }

    @Test
    void whenPOSTrequest_withNotBlankName_accessLevelShouldBeCreated() throws Exception {
        // given
        AccessLevelDTO accessLevelDTO = AccessLevelDTOFactory.builder().build().dto();

        // when
        when(accessLevelService.create(accessLevelDTO)).thenReturn(accessLevelDTO);

        // then
        mockMvc.perform(post(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(accessLevelDTO)))
               .andExpect(jsonPath("$.id", is((int) accessLevelDTO.getId())))
               .andExpect(jsonPath("$.description", is(accessLevelDTO.getDescription())));
    }

    @Test 
    void whenPOSTrequest_withBlankName_returnBadRequest() throws Exception {
        // given
        AccessLevelDTO accessLevelDTO = AccessLevelDTOFactory.builder().build().dto();
        accessLevelDTO.setDescription(" ");

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accessLevelDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETrequest_returnOk() throws Exception {
        // given
        AccessLevelDTO accessLevelDTO = AccessLevelDTOFactory.builder().build().dto();

        // when
        when(accessLevelService.findAll()).thenReturn(Collections.singletonList(accessLevelDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id", is((int) accessLevelDTO.getId())))
               .andExpect(jsonPath("$[0].description", is(accessLevelDTO.getDescription())));
    }

    @Test
    void whenGETrequest_withExistingId_returnOk() throws Exception {
        // given
        AccessLevelDTO accessLevelDTO = AccessLevelDTOFactory.builder().build().dto();        

        // when
        when(accessLevelService.findById(accessLevelDTO.getId())).thenReturn(accessLevelDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + accessLevelDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) accessLevelDTO.getId())))
                .andExpect(jsonPath("$.description", is(accessLevelDTO.getDescription())));
    }

    @Test
    void whenGETrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        AccessLevelDTO accessLevelDTO = AccessLevelDTOFactory.builder().build().dto();        

        // when
        when(accessLevelService.findById(accessLevelDTO.getId())).thenThrow(AccessLevelNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + accessLevelDTO.getId())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTrequest_withExistingId_accessLevelShouldBeUpdated() throws Exception {
        // given
        AccessLevelDTO toUpdateAccessLevelDTO = AccessLevelDTOFactory.builder().build().dto();
        AccessLevelDTO updatedAccessLevelDTO = toUpdateAccessLevelDTO;        
        updatedAccessLevelDTO.setDescription("It has changed");

        doReturn(updatedAccessLevelDTO)
            .when(accessLevelService).update(updatedAccessLevelDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(updatedAccessLevelDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is((int) toUpdateAccessLevelDTO.getId())))
               .andExpect(jsonPath("$.description", is(toUpdateAccessLevelDTO.getDescription())));
    }

    @Test
    void whenPUTrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        AccessLevelDTO toUpdateAccessLevelDTO = AccessLevelDTOFactory.builder().build().dto();
        toUpdateAccessLevelDTO.setId(NOT_EXISTING__ID);

        // when
        doThrow(AccessLevelNotFoundException.class)
            .when(accessLevelService).update(toUpdateAccessLevelDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(toUpdateAccessLevelDTO)))
               .andExpect(status().isNotFound());
    }

    @Test
    void whenDELETErequest_withExistingId_returnNoContent() throws Exception {
        // given
        AccessLevelDTO toDeleteAccessLevelDTO = AccessLevelDTOFactory.builder().build().dto();

        // when
        doNothing().when(accessLevelService).deleteById(toDeleteAccessLevelDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + toDeleteAccessLevelDTO.getId())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETErequest_withNotExistingId_returnNotFound() throws Exception {
        // when
        doThrow(AccessLevelNotFoundException.class)
            .when(accessLevelService).deleteById(NOT_EXISTING__ID);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + NOT_EXISTING__ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
