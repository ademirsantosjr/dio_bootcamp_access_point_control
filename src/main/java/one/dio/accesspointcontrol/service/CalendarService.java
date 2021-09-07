package one.dio.accesspointcontrol.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.dio.accesspointcontrol.dto.CalendarDTO;
import one.dio.accesspointcontrol.exception.CalendarNotFoundException;
import one.dio.accesspointcontrol.mapper.CalendarMapper;
import one.dio.accesspointcontrol.model.Calendar;
import one.dio.accesspointcontrol.repository.CalendarRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CalendarService {
    
    CalendarRepository calendarRepository;

    private final CalendarMapper calendarMapper = CalendarMapper.INSTANCE;

    public CalendarDTO create(CalendarDTO calendarDTO) {
        Calendar calendarToCreate = calendarMapper.toModel(calendarDTO);
        Calendar savedCalendar = calendarRepository.save(calendarToCreate);
        return calendarMapper.toDTO(savedCalendar);
    }

    public List<CalendarDTO> findAll() {
        return calendarRepository.findAll()
                                    .stream()
                                    .map(calendarMapper::toDTO)
                                    .collect(Collectors.toList());        
    }

    public CalendarDTO findById(long id) throws CalendarNotFoundException {
        Calendar foundCalendar = calendarRepository.findById(id)
                                                   .orElseThrow(
                                                    () -> new CalendarNotFoundException(id));

        return calendarMapper.toDTO(foundCalendar);
    }

    public CalendarDTO update(CalendarDTO calendarDTO) throws CalendarNotFoundException {
        verifyIfCalendarExists(calendarDTO.getId());

        Calendar calendarToUpdate = calendarMapper.toModel(calendarDTO);

        Calendar updatedCalendar = calendarRepository.save(calendarToUpdate);

        return calendarMapper.toDTO(updatedCalendar);
    }

    public void deleteById(long id) throws CalendarNotFoundException {
        verifyIfCalendarExists(id);

        calendarRepository.deleteById(id);
    }

    private Calendar verifyIfCalendarExists(long id) throws CalendarNotFoundException {
        return calendarRepository.findById(id)
                                 .orElseThrow(
                                    () -> new CalendarNotFoundException(id));
    }
}
