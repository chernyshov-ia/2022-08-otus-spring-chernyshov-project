package ru.chia2k.logist.service;

import ru.chia2k.logist.dto.ParcelDto;
import ru.chia2k.logist.dto.RequestParcelDto;

import java.util.List;
import java.util.Optional;

public interface ParcelService {
    Optional<ParcelDto> findById(Integer id);
    ParcelDto create(RequestParcelDto request);
    List<ParcelDto> findBySeal(String seal);
    Optional<ParcelDto> findByBarcode(String barcode);
}
