package one.dio.accesspointcontrol.service;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.empty;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import one.dio.accesspointcontrol.dto.WorkScheduleDTO;
import one.dio.accesspointcontrol.dtofactory.WorkScheduleDTOFactory;
import one.dio.accesspointcontrol.mapper.WorkScheduleMapper;
import one.dio.accesspointcontrol.model.WorkSchedule;
import one.dio.accesspointcontrol.repository.WorkScheduleRepository;

@ExtendWith(MockitoExtension.class)
public class WorkScheduleServiceTest {
    
    @Mock
    private WorkScheduleRepository workScheduleRepository;

    private WorkScheduleMapper workScheduleMapper = WorkScheduleMapper.INSTANCE;

    @InjectMocks
    private WorkScheduleService workScheduleService;

    @Test
    void createWorkSchedule_WithDescriptionFieldNotEmpty_ShouldCreateWorkSchedule() {
        // given
        WorkScheduleDTO submittedWorkScheduleDTO = WorkScheduleDTOFactory.builder().build().dto();
        WorkSchedule workScheduleToSave = workScheduleMapper.toModel(submittedWorkScheduleDTO);

        // when
        when(workScheduleRepository.save(workScheduleToSave)).thenReturn(workScheduleToSave);

        // then
        WorkScheduleDTO createdWorkScheduleDTO = workScheduleService.create(submittedWorkScheduleDTO);

        assertThat(createdWorkScheduleDTO.getId(), is(equalTo(submittedWorkScheduleDTO.getId())));
        assertThat(createdWorkScheduleDTO.getDescription(), is(equalTo(submittedWorkScheduleDTO.getDescription())));   
    }

    @Test
    void findAll_ReturnAllWorkSchedulesInTheDatabase() {
        // given
        WorkScheduleDTO workScheduleDTO = WorkScheduleDTOFactory.builder().build().dto();
        WorkSchedule workSchedule = workScheduleMapper.toModel(workScheduleDTO);

        // when
        when(workScheduleRepository.findAll()).thenReturn(Collections.singletonList(workSchedule));

        // then
        List<WorkScheduleDTO> workSchedulesDTOsFound = workScheduleService.findAll();

        assertThat(workSchedulesDTOsFound, is(not(empty())));
        assertThat(workSchedulesDTOsFound.get(0), is(equalTo(workScheduleDTO)));
    }
    
}
