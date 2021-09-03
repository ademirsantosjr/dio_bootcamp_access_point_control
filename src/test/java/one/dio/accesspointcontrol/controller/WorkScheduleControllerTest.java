package one.dio.accesspointcontrol.controller;

import static org.mockito.Mockito.when;

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

import one.dio.accesspointcontrol.dto.WorkScheduleDTO;
import one.dio.accesspointcontrol.dtofactory.WorkScheduleDTOFactory;
import one.dio.accesspointcontrol.exception.WorkScheduleNotFoundException;
import one.dio.accesspointcontrol.service.WorkScheduleService;

@ExtendWith(MockitoExtension.class)
public class WorkScheduleControllerTest {
    
    private static final String WORK_SCHEDULE_API_URL_PATH = "/api/v1/workschedules";

    private MockMvc mockMvc;

    @Mock
    private WorkScheduleService workScheduleService;

    @InjectMocks
    private WorkScheduleController workScheduleController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(workScheduleController)
                                 .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                 .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                                 .build();
    }

    @Test
    void whenPOSTrequest_withNotBlankDescription_workScheduleShouldBeCreated() throws Exception {
        // given
        WorkScheduleDTO workScheduleDTO = WorkScheduleDTOFactory.builder().build().dto();

        // when
        when(workScheduleService.create(workScheduleDTO)).thenReturn(workScheduleDTO);

        // then
        mockMvc.perform(post(WORK_SCHEDULE_API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(workScheduleDTO)))
               .andExpect(jsonPath("$.id", is((int) workScheduleDTO.getId())))
               .andExpect(jsonPath("$.description", is(workScheduleDTO.getDescription())));
    }

    @Test 
    void whenPOSTrequest_withBlankDescription_returnBadRequest() throws Exception {
        // given
        WorkScheduleDTO workScheduleDTO = WorkScheduleDTOFactory.builder().build().dto();
        workScheduleDTO.setDescription(" ");

        // then
        mockMvc.perform(post(WORK_SCHEDULE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(workScheduleDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETrequest_returnOk() throws Exception {
        // given
        WorkScheduleDTO workScheduleDTO = WorkScheduleDTOFactory.builder().build().dto();

        // when
        when(workScheduleService.findAll()).thenReturn(Collections.singletonList(workScheduleDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(WORK_SCHEDULE_API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id", is((int) workScheduleDTO.getId())))
               .andExpect(jsonPath("$[0].description", is(workScheduleDTO.getDescription())));
    }

    @Test
    void whenGETrequest_withExistingId_returnOk() throws Exception {
        // given
        WorkScheduleDTO workScheduleDTO = WorkScheduleDTOFactory.builder().build().dto();        

        // when
        when(workScheduleService.findById(workScheduleDTO.getId())).thenReturn(workScheduleDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(WORK_SCHEDULE_API_URL_PATH + "/" + workScheduleDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) workScheduleDTO.getId())))
                .andExpect(jsonPath("$.description", is(workScheduleDTO.getDescription())));
    }

    @Test
    void whenGETrequest_withNotExistingId_returnNotFound() throws Exception {
        // given
        WorkScheduleDTO workScheduleDTO = WorkScheduleDTOFactory.builder().build().dto();        

        // when
        when(workScheduleService.findById(workScheduleDTO.getId())).thenThrow(WorkScheduleNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(WORK_SCHEDULE_API_URL_PATH + "/" + workScheduleDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
