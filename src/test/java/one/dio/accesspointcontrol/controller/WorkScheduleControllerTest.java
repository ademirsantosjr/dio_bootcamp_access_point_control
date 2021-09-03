package one.dio.accesspointcontrol.controller;

import static org.mockito.Mockito.when;

import static org.hamcrest.core.Is.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import one.dio.accesspointcontrol.dto.WorkScheduleDTO;
import one.dio.accesspointcontrol.dtofactory.WorkScheduleDTOFactory;
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
    void whenPostRequest_withNotBlankDescription_workScheduleShouldBeCreated() throws Exception {
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
    void whenPostRequest_withBlankDescription_returnBadRequest() throws Exception {
        // given
        WorkScheduleDTO workScheduleDTO = WorkScheduleDTOFactory.builder().build().dto();
        workScheduleDTO.setDescription(" ");

        // then
        mockMvc.perform(post(WORK_SCHEDULE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(workScheduleDTO)))
                .andExpect(status().isBadRequest());
    }
}
