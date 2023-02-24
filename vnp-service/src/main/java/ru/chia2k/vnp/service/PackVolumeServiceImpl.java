package ru.chia2k.vnp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.chia2k.vnp.domain.PackVolume;
import ru.chia2k.vnp.dto.PackVolumeDto;
import ru.chia2k.vnp.dto.PackVolumeRequestDto;
import ru.chia2k.vnp.repository.PackVolumeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class PackVolumeServiceImpl implements PackVolumeService{
    private final PackVolumeRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<PackVolumeDto> findAll() {
        return repository.findAll().stream()
                .map(PackVolumeDto::fromDomainObject)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<PackVolumeDto> findById(Integer id) {
        return repository.findById(id)
                .map(PackVolumeDto::fromDomainObject);
    }

    @Transactional
    @Override
    public PackVolumeDto update(Integer id, PackVolumeRequestDto request) {
        PackVolume obj = request.toDomainObject();
        obj.setId(id);
        obj = repository.save(obj);
        return PackVolumeDto.fromDomainObject(obj);
    }

    @Transactional
    @Override
    public PackVolumeDto create(PackVolumeRequestDto request) {
        PackVolume obj = request.toDomainObject();
        return PackVolumeDto.fromDomainObject(repository.save(obj));
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
