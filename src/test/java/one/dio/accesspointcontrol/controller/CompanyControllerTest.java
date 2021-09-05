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

import one.dio.accesspointcontrol.dto.CompanyDTO;
import one.dio.accesspointcontrol.dtofactory.CompanyDTOFactory;
import one.dio.accesspointcontrol.exception.CompanyNotFoundException;
import one.dio.accesspointcontrol.service.CompanyService;

@ExtendWith(MockitoExtension.class)
public class CompanyControllerTest {
    
    private static final String API_URL_PATH = "/api/v1/companies";
    private static final long NOT_EXISTING__ID = 99l;

    private MockMvc mockMvc;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(companyController)
                                 .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                 .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                                 .build();
    }

    @Test
    void whenPOSTrequest_withNotBlankName_companyShouldBeCreated() throws Exception {
        // given
        CompanyDTO companyDTO = CompanyDTOFactory.builder().build().dto();

        // when
        when(companyService.create(companyDTO)).thenReturn(companyDTO);

        // then
        mockMvc.perform(post(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(companyDTO)))
               .andExpect(jsonPath("$.id", is((int) companyDTO.getId())))
               .andExpect(jsonPath("$.name", is(companyDTO.getName())));
    }

    @Test 
    void whenPOSTrequest_withBlankName_returnBadRequest() throws Exception {
        // given
        CompanyDTO companyDTO = CompanyDTOFactory.builder().build().dto();
        companyDTO.setName(" ");

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(companyDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETrequest_returnOk() throws Exception {
        // given
        CompanyDTO companyDTO = CompanyDTOFactory.builder().build().dto();

        // when
        when(companyService.findAll()).thenReturn(Collections.singletonList(companyDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id", is((int) companyDTO.getId())))
               .andExpect(jsonPath("$[0].name", is(companyDTO.getName())));
    }

    @Test
    void whenGETrequest_withExistingId_returnOk() throws Exception {
        // given
        CompanyDTO companyDTO = CompanyDTOFactory.builder().build().dto();        

        // when
        when(companyService.findById(companyDTO.getId())).thenReturn(companyDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + companyDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) companyDTO.getId())))
                .andExpect(jsonPath("$.name", is(companyDTO.getName())));
    }

    @Test
    void whenGETrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        CompanyDTO companyDTO = CompanyDTOFactory.builder().build().dto();        

        // when
        when(companyService.findById(companyDTO.getId())).thenThrow(CompanyNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + companyDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTrequest_withExistingId_eventShouldBeUpdated() throws Exception {
        // given
        CompanyDTO toUpdateCompanyDTO = CompanyDTOFactory.builder().build().dto();
        CompanyDTO updatedCompanyDTO = toUpdateCompanyDTO;        
        updatedCompanyDTO.setName("It has changed");

        doReturn(updatedCompanyDTO)
            .when(companyService).update(updatedCompanyDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(updatedCompanyDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is((int) toUpdateCompanyDTO.getId())))
               .andExpect(jsonPath("$.name", is(toUpdateCompanyDTO.getName())));
    }

    @Test
    void whenPUTrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        CompanyDTO toUpdateCompanyDTO = CompanyDTOFactory.builder().build().dto();
        toUpdateCompanyDTO.setId(NOT_EXISTING__ID);

        // when
        doThrow(CompanyNotFoundException.class)
            .when(companyService).update(toUpdateCompanyDTO);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(toUpdateCompanyDTO)))
               .andExpect(status().isNotFound());
    }

    @Test
    void whenDELETErequest_withExistingId_returnNoContent() throws Exception {
        // given
        CompanyDTO toDeleteCompanyDTO = CompanyDTOFactory.builder().build().dto();

        // when
        doNothing().when(companyService).deleteById(toDeleteCompanyDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + toDeleteCompanyDTO.getId())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETErequest_withNotExistingId_returnNotFound() throws Exception {
        // when
        doThrow(CompanyNotFoundException.class)
            .when(companyService).deleteById(NOT_EXISTING__ID);
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + NOT_EXISTING__ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
