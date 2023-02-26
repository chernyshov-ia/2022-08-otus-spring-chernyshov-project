package ru.chia2k.logist.service;

import ru.chia2k.logist.dto.AddressDto;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    List<AddressDto> findAll();
    List<AddressDto> search(String search);
    Optional<AddressDto> findById(String id);
}
