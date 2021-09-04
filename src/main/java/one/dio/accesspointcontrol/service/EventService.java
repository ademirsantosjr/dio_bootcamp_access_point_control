package one.dio.accesspointcontrol.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.dio.accesspointcontrol.dto.EventDTO;
import one.dio.accesspointcontrol.exception.EventNotFoundException;
import one.dio.accesspointcontrol.mapper.EventMapper;
import one.dio.accesspointcontrol.model.Event;
import one.dio.accesspointcontrol.repository.EventRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventService {
    
    EventRepository eventRepository;

    private final EventMapper eventMapper = EventMapper.INSTANCE;

    public EventDTO create(EventDTO eventDTO) {
        Event eventToCreate = eventMapper.toModel(eventDTO);
        Event savedEvent = eventRepository.save(eventToCreate);
        return eventMapper.toDTO(savedEvent);
    }

    public List<EventDTO> findAll() {
        return eventRepository.findAll()
                              .stream()
                              .map(eventMapper::toDTO)
                              .collect(Collectors.toList());        
    }

    public EventDTO findById(long id) throws EventNotFoundException {
        Event foundEvent = eventRepository.findById(id)
                                          .orElseThrow(
                                              () -> new EventNotFoundException(id));

        return eventMapper.toDTO(foundEvent);
    }

    public EventDTO update(EventDTO eventDTO) throws EventNotFoundException {
        verifyIfEventExists(eventDTO.getId());

        Event eventToUpdate = eventMapper.toModel(eventDTO);

        Event updatedEvent = eventRepository.save(eventToUpdate);

        return eventMapper.toDTO(updatedEvent);
    }

    public void deleteById(long id) throws EventNotFoundException {
        verifyIfEventExists(id);

        eventRepository.deleteById(id);
    }

    private Event verifyIfEventExists(long id) throws EventNotFoundException {
        return eventRepository.findById(id)
                              .orElseThrow(
                                  () -> new EventNotFoundException(id));
    }
}
