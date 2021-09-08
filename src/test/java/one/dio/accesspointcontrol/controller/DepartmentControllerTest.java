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

import one.dio.accesspointcontrol.dto.DepartmentDTO;
import one.dio.accesspointcontrol.dtofactory.DepartmentDTOFactory;
import one.dio.accesspointcontrol.exception.DepartmentNotFoundException;
import one.dio.accesspointcontrol.service.DepartmentService;

@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {
    
    private static final String API_URL_PATH = "/api/v1/departments";
    private static final long NOT_EXISTING__ID = 99l;

    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                                 .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                 .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                                 .build();
    }

    @Test
    void whenPOSTrequest_withNotBlankName_departmentShouldBeCreated() throws Exception {
        // given
        DepartmentDTO departmentDTO = DepartmentDTOFactory.builder().build().dto();

        // when
        when(departmentService.create(departmentDTO)).thenReturn(departmentDTO);

        // then
        mockMvc.perform(post(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(departmentDTO)))
               .andExpect(jsonPath("$.id", is((int) departmentDTO.getId())))
               .andExpect(jsonPath("$.description", is(departmentDTO.getDescription())));
    }

    @Test 
    void whenPOSTrequest_withBlankName_returnBadRequest() throws Exception {
        // given
        DepartmentDTO departmentDTO = DepartmentDTOFactory.builder().build().dto();
        departmentDTO.setDescription(" ");

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(departmentDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETrequest_returnOk() throws Exception {
        // given
        DepartmentDTO departmentDTO = DepartmentDTOFactory.builder().build().dto();

        // when
        when(departmentService.findAll()).thenReturn(Collections.singletonList(departmentDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id", is((int) departmentDTO.getId())))
               .andExpect(jsonPath("$[0].description", is(departmentDTO.getDescription())));
    }

    @Test
    void whenGETrequest_withExistingId_returnOk() throws Exception {
        // given
        DepartmentDTO departmentDTO = DepartmentDTOFactory.builder().build().dto();        

        // when
        when(departmentService.findById(departmentDTO.getId())).thenReturn(departmentDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + departmentDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) departmentDTO.getId())))
                .andExpect(jsonPath("$.description", is(departmentDTO.getDescription())));
    }

    @Test
    void whenGETrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        DepartmentDTO departmentDTO = DepartmentDTOFactory.builder().build().dto();        

        // when
        when(departmentService.findById(departmentDTO.getId())).thenThrow(DepartmentNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + departmentDTO.getId())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTrequest_withExistingId_departmentShouldBeUpdated() throws Exception {
        // given
        DepartmentDTO toUpdateDepartmentDTO = DepartmentDTOFactory.builder().build().dto();
        DepartmentDTO updatedDepartmentDTO = toUpdateDepartmentDTO;        
        updatedDepartmentDTO.setDescription("It has changed");

        doReturn(updatedDepartmentDTO)
            .when(departmentService).update(updatedDepartmentDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(updatedDepartmentDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is((int) toUpdateDepartmentDTO.getId())))
               .andExpect(jsonPath("$.description", is(toUpdateDepartmentDTO.getDescription())));
    }

    @Test
    void whenPUTrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        DepartmentDTO toUpdateDepartmentDTO = DepartmentDTOFactory.builder().build().dto();
        toUpdateDepartmentDTO.setId(NOT_EXISTING__ID);

        // when
        doThrow(DepartmentNotFoundException.class)
            .when(departmentService).update(toUpdateDepartmentDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(toUpdateDepartmentDTO)))
               .andExpect(status().isNotFound());
    }

    @Test
    void whenDELETErequest_withExistingId_returnNoContent() throws Exception {
        // given
        DepartmentDTO toDeleteDepartmentDTO = DepartmentDTOFactory.builder().build().dto();

        // when
        doNothing().when(departmentService).deleteById(toDeleteDepartmentDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + toDeleteDepartmentDTO.getId())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETErequest_withNotExistingId_returnNotFound() throws Exception {
        // when
        doThrow(DepartmentNotFoundException.class)
            .when(departmentService).deleteById(NOT_EXISTING__ID);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + NOT_EXISTING__ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
