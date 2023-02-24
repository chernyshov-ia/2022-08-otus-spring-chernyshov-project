package ru.chia2k.logist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chia2k.logist.dto.CargoCategoryDto;
import ru.chia2k.logist.repository.CargoCategoryRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CargoCategoryServiceImpl implements CargoCategoryService {
    private final CargoCategoryRepository repository;

    @Override
    public List<CargoCategoryDto> findAll() {
        return repository.findAll()
                .stream()
                .map(CargoCategoryDto::fromDomainObject)
                .toList();
    }
}
