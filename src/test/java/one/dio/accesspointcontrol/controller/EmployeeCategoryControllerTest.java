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

import one.dio.accesspointcontrol.dto.EmployeeCategoryDTO;
import one.dio.accesspointcontrol.dtofactory.EmployeeCategoryDTOFactory;
import one.dio.accesspointcontrol.exception.EmployeeCategoryNotFoundException;
import one.dio.accesspointcontrol.service.EmployeeCategoryService;

@ExtendWith(MockitoExtension.class)
public class EmployeeCategoryControllerTest {
    
    private static final String API_URL_PATH = "/api/v1/employeecategories";
    private static final long NOT_EXISTING__ID = 99l;

    private MockMvc mockMvc;

    @Mock
    private EmployeeCategoryService employeeCategoryService;

    @InjectMocks
    private EmployeeCategoryController employeeCategoryController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeCategoryController)
                                 .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                 .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                                 .build();
    }

    @Test
    void whenPOSTrequest_withNotBlankDescription_employeeCategoryShouldBeCreated() throws Exception {
        // given
        EmployeeCategoryDTO employeeCategoryDTO = EmployeeCategoryDTOFactory.builder().build().dto();

        // when
        when(employeeCategoryService.create(employeeCategoryDTO)).thenReturn(employeeCategoryDTO);

        // then
        mockMvc.perform(post(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(employeeCategoryDTO)))
               .andExpect(jsonPath("$.id", is((int) employeeCategoryDTO.getId())))
               .andExpect(jsonPath("$.description", is(employeeCategoryDTO.getDescription())));
    }

    @Test 
    void whenPOSTrequest_withBlankDescription_returnBadRequest() throws Exception {
        // given
        EmployeeCategoryDTO employeeCategoryDTO = EmployeeCategoryDTOFactory.builder().build().dto();
        employeeCategoryDTO.setDescription(" ");

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employeeCategoryDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETrequest_returnOk() throws Exception {
        // given
        EmployeeCategoryDTO employeeCategoryDTO = EmployeeCategoryDTOFactory.builder().build().dto();

        // when
        when(employeeCategoryService.findAll()).thenReturn(Collections.singletonList(employeeCategoryDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id", is((int) employeeCategoryDTO.getId())))
               .andExpect(jsonPath("$[0].description", is(employeeCategoryDTO.getDescription())));
    }

    @Test
    void whenGETrequest_withExistingId_returnOk() throws Exception {
        // given
        EmployeeCategoryDTO employeeCategoryDTO = EmployeeCategoryDTOFactory.builder().build().dto();        

        // when
        when(employeeCategoryService.findById(employeeCategoryDTO.getId())).thenReturn(employeeCategoryDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + employeeCategoryDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) employeeCategoryDTO.getId())))
                .andExpect(jsonPath("$.description", is(employeeCategoryDTO.getDescription())));
    }

    @Test
    void whenGETrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        EmployeeCategoryDTO employeeCategoryDTO = EmployeeCategoryDTOFactory.builder().build().dto();        

        // when
        when(employeeCategoryService.findById(employeeCategoryDTO.getId())).thenThrow(EmployeeCategoryNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + employeeCategoryDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTrequest_withExistingId_employeeCategoryShouldBeUpdated() throws Exception {
        // given
        EmployeeCategoryDTO toUpdateEmployeeCategoryDTO = EmployeeCategoryDTOFactory.builder().build().dto();
        EmployeeCategoryDTO updatedEmployeeCategoryDTO = toUpdateEmployeeCategoryDTO;        
        updatedEmployeeCategoryDTO.setDescription("It has changed");

        doReturn(updatedEmployeeCategoryDTO)
            .when(employeeCategoryService).update(updatedEmployeeCategoryDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(updatedEmployeeCategoryDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is((int) toUpdateEmployeeCategoryDTO.getId())))
               .andExpect(jsonPath("$.description", is(toUpdateEmployeeCategoryDTO.getDescription())));
    }

    @Test
    void whenPUTrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        EmployeeCategoryDTO toUpdateEmployeeCategoryDTO = EmployeeCategoryDTOFactory.builder().build().dto();
        toUpdateEmployeeCategoryDTO.setId(NOT_EXISTING__ID);

        // when
        doThrow(EmployeeCategoryNotFoundException.class)
            .when(employeeCategoryService).update(toUpdateEmployeeCategoryDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(toUpdateEmployeeCategoryDTO)))
               .andExpect(status().isNotFound());
    }

    @Test
    void whenDELETErequest_withExistingId_returnNoContent() throws Exception {
        // given
        EmployeeCategoryDTO toDeleteEmployeeCategoryDTO = EmployeeCategoryDTOFactory.builder().build().dto();

        // when
        doNothing().when(employeeCategoryService).deleteById(toDeleteEmployeeCategoryDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + toDeleteEmployeeCategoryDTO.getId())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETErequest_withNotExistingId_returnNotFound() throws Exception {
        // when
        doThrow(EmployeeCategoryNotFoundException.class)
            .when(employeeCategoryService).deleteById(NOT_EXISTING__ID);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + NOT_EXISTING__ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
