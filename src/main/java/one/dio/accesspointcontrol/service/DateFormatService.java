package one.dio.accesspointcontrol.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.dio.accesspointcontrol.dto.DateFormatDTO;
import one.dio.accesspointcontrol.exception.DateFormatNotFoundException;
import one.dio.accesspointcontrol.mapper.DateFormatMapper;
import one.dio.accesspointcontrol.model.DateFormat;
import one.dio.accesspointcontrol.repository.DateFormatRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DateFormatService {
    
    DateFormatRepository dateFormatRepository;

    private final DateFormatMapper dateFormatMapper = DateFormatMapper.INSTANCE;

    public DateFormatDTO create(DateFormatDTO dateFormatDTO) {
        DateFormat dateFormatToCreate = dateFormatMapper.toModel(dateFormatDTO);
        DateFormat savedDateFormat = dateFormatRepository.save(dateFormatToCreate);
        return dateFormatMapper.toDTO(savedDateFormat);
    }

    public List<DateFormatDTO> findAll() {
        return dateFormatRepository.findAll()
                                    .stream()
                                    .map(dateFormatMapper::toDTO)
                                    .collect(Collectors.toList());        
    }

    public DateFormatDTO findById(long id) throws DateFormatNotFoundException {
        DateFormat foundDateFormat = dateFormatRepository.findById(id)
                                                         .orElseThrow(
                                                             () -> new DateFormatNotFoundException(id));

        return dateFormatMapper.toDTO(foundDateFormat);
    }

    public DateFormatDTO update(DateFormatDTO dateFormatDTO) throws DateFormatNotFoundException {
        verifyIfDateFormatExists(dateFormatDTO.getId());

        DateFormat dateFormatToUpdate = dateFormatMapper.toModel(dateFormatDTO);

        DateFormat updatedDateFormat = dateFormatRepository.save(dateFormatToUpdate);

        return dateFormatMapper.toDTO(updatedDateFormat);
    }

    public void deleteById(long id) throws DateFormatNotFoundException {
        verifyIfDateFormatExists(id);

        dateFormatRepository.deleteById(id);
    }

    private DateFormat verifyIfDateFormatExists(long id) throws DateFormatNotFoundException {
        return dateFormatRepository.findById(id)
                                   .orElseThrow(
                                    () -> new DateFormatNotFoundException(id));
    }
}
