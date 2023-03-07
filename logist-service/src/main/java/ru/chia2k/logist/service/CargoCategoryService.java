package ru.chia2k.logist.service;

import ru.chia2k.logist.dto.CargoCategoryDto;

import java.util.List;
import java.util.Optional;

public interface CargoCategoryService {
    Optional<CargoCategoryDto> findById(Integer id);
    List<CargoCategoryDto> findAll();
}
