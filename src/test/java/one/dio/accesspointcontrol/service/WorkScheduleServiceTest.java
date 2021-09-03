package one.dio.accesspointcontrol.service;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.empty;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import one.dio.accesspointcontrol.dto.WorkScheduleDTO;
import one.dio.accesspointcontrol.dtofactory.WorkScheduleDTOFactory;
import one.dio.accesspointcontrol.exception.WorkScheduleNotFoundException;
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
    void whenCreate_WithNotEmptyDescription_workScheduleShouldBeCreated() {
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
    void whenFindAll_ReturnAllWorkSchedulesInTheDatabase() {
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

    @Test
    void whenFindById_withExistingId_returnWorkSchedule() throws WorkScheduleNotFoundException {
        // given
        WorkScheduleDTO workScheduleDTO = WorkScheduleDTOFactory.builder().build().dto();
        WorkSchedule workSchedule = workScheduleMapper.toModel(workScheduleDTO);

        // when
        when(workScheduleRepository.findById(workSchedule.getId())).thenReturn(Optional.of(workSchedule));            
        
        // then
        WorkScheduleDTO foundWorkScheduleDTO = workScheduleService.findById(workScheduleDTO.getId());

        assertThat(foundWorkScheduleDTO, is(equalTo(workScheduleDTO)));
    }

    @Test
    void whenFindById_withNotExistingId_throwAnException() {
        // given
        WorkScheduleDTO workScheduleDTO = WorkScheduleDTOFactory.builder().build().dto();
        
        // when
        when(workScheduleRepository.findById(workScheduleDTO.getId())).thenReturn(Optional.empty());            
        
        // then
        assertThrows(WorkScheduleNotFoundException.class,
                     () -> workScheduleService.findById(workScheduleDTO.getId()));
    }
    
}
