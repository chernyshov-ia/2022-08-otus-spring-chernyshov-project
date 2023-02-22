package ru.chia2k.logist.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chia2k.logist.domain.Address;
import ru.chia2k.logist.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository repository;

    @Override
    public List<Address> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Address> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Address> search(@NonNull String search) {
        return repository.search(search.toUpperCase());
    }
}
