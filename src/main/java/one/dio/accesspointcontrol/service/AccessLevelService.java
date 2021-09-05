package one.dio.accesspointcontrol.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.dio.accesspointcontrol.dto.AccessLevelDTO;
import one.dio.accesspointcontrol.exception.AccessLevelNotFoundException;
import one.dio.accesspointcontrol.mapper.AccessLevelMapper;
import one.dio.accesspointcontrol.model.AccessLevel;
import one.dio.accesspointcontrol.repository.AccessLevelRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccessLevelService {
    
    AccessLevelRepository accessLevelRepository;

    private final AccessLevelMapper accessLevelMapper = AccessLevelMapper.INSTANCE;

    public AccessLevelDTO create(AccessLevelDTO accessLevelDTO) {
        AccessLevel accessLevelToCreate = accessLevelMapper.toModel(accessLevelDTO);
        AccessLevel savedAccessLevel = accessLevelRepository.save(accessLevelToCreate);
        return accessLevelMapper.toDTO(savedAccessLevel);
    }

    public List<AccessLevelDTO> findAll() {
        return accessLevelRepository.findAll()
                                    .stream()
                                    .map(accessLevelMapper::toDTO)
                                    .collect(Collectors.toList());        
    }

    public AccessLevelDTO findById(long id) throws AccessLevelNotFoundException {
        AccessLevel foundAccessLevel = accessLevelRepository.findById(id)
                                                            .orElseThrow(
                                                                () -> new AccessLevelNotFoundException(id));

        return accessLevelMapper.toDTO(foundAccessLevel);
    }

    public AccessLevelDTO update(AccessLevelDTO accessLevelDTO) throws AccessLevelNotFoundException {
        verifyIfAccessLevelExists(accessLevelDTO.getId());

        AccessLevel accessLevelToUpdate = accessLevelMapper.toModel(accessLevelDTO);

        AccessLevel updatedAccessLevel = accessLevelRepository.save(accessLevelToUpdate);

        return accessLevelMapper.toDTO(updatedAccessLevel);
    }

    public void deleteById(long id) throws AccessLevelNotFoundException {
        verifyIfAccessLevelExists(id);

        accessLevelRepository.deleteById(id);
    }

    private AccessLevel verifyIfAccessLevelExists(long id) throws AccessLevelNotFoundException {
        return accessLevelRepository.findById(id)
                                    .orElseThrow(
                                        () -> new AccessLevelNotFoundException(id));
    }
}
