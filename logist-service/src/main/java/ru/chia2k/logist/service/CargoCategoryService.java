package ru.chia2k.logist.service;

import ru.chia2k.logist.dto.CargoCategoryDto;

import java.util.List;

public interface CargoCategoryService {
    List<CargoCategoryDto> findAll();
}
