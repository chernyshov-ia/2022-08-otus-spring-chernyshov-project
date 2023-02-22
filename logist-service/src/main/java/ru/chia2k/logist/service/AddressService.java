package ru.chia2k.logist.service;

import ru.chia2k.logist.domain.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    List<Address> findAll();
    List<Address> search(String search);
    Optional<Address> findById(String id);
}
