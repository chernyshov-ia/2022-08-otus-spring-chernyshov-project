package ru.chia2k.vnp.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ru.chia2k.vnp.dto.PackVolumeDto;
import ru.chia2k.vnp.dto.PackVolumeRequestDto;

import java.util.List;
import java.util.Optional;

public interface PackVolumeService {
    List<PackVolumeDto> findAll();
    Optional<PackVolumeDto> findById(Integer id);
    PackVolumeDto update(@NotNull Integer id, @Valid PackVolumeRequestDto request);
    PackVolumeDto create(@Valid PackVolumeRequestDto request);
    void deleteById(@NotNull Integer id);
}
