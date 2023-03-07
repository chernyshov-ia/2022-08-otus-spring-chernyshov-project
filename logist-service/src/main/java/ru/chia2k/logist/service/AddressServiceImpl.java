package ru.chia2k.logist.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chia2k.logist.domain.Address;
import ru.chia2k.logist.dto.AddressDto;
import ru.chia2k.logist.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository repository;

    @Override
    public List<AddressDto> findAll() {
        return repository.findAll().stream()
                .map(AddressDto::fromDomainObject)
                .toList();
    }

    @Override
    public Optional<AddressDto> findById(String id) {
        return repository.findById(id)
                .map(AddressDto::fromDomainObject);
    }

    @Override
    public List<AddressDto> search(@NonNull String search) {
        return repository.search(search.toUpperCase()).stream()
                .map(AddressDto::fromDomainObject)
                .toList();
    }
}
