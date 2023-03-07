package ru.chia2k.logist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chia2k.logist.domain.Address;
import ru.chia2k.logist.domain.CargoCategory;
import ru.chia2k.logist.domain.Parcel;
import ru.chia2k.logist.dto.ParcelDto;
import ru.chia2k.logist.dto.RequestParcelDto;
import ru.chia2k.logist.exception.ObjectNotFoundException;
import ru.chia2k.logist.repository.AddressRepository;
import ru.chia2k.logist.repository.CargoCategoryRepository;
import ru.chia2k.logist.repository.ParcelRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParcelServiceImpl implements ParcelService{
    private final ParcelRepository parcelRepository;
    private final AddressRepository addressRepository;
    private final CargoCategoryRepository cargoCategoryRepository;

    @Override
    public Optional<ParcelDto> findById(Integer id) {
        return parcelRepository.findById(id).map(ParcelDto::fromDomainObject);
    }

    @Transactional
    @Override
    public ParcelDto create(RequestParcelDto dto) {
        Parcel parcel = parcelFromDto(dto);
        parcel = parcelRepository.save(parcel);
        return ParcelDto.fromDomainObject(parcel);
    }

    private Parcel parcelFromDto( RequestParcelDto dto ) {
        Address sender = addressRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new ObjectNotFoundException("senderId", dto.getSenderId()));

        Address recipient = addressRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new ObjectNotFoundException("recipientId", dto.getRecipientId()));

        CargoCategory cargoCategory = cargoCategoryRepository.findById(dto.getCargoCategoryId())
                .orElseThrow(() -> new ObjectNotFoundException("cargoCategoryId", dto.getCargoCategoryId().toString()));

        Parcel parcel = new Parcel();
        parcel.setSeal(dto.getSeal());
        parcel.setVolume(dto.getVolume());
        parcel.setWeight(dto.getWeight());
        parcel.setValue(dto.getValue());
        parcel.setDescription(dto.getDescription());
        parcel.setSender(sender);
        parcel.setRecipient(recipient);
        parcel.setCargoCategory(cargoCategory);

        Integer nextId = parcelRepository.nextId();

        parcel.setId(nextId);
        parcel.setNumber(generateParcelNumber(nextId));
        parcel.setBarcode(generateParcelBarcode(nextId));
        parcel.setCreatedAt(LocalDateTime.now());

        return parcel;
    }

    private String generateParcelBarcode(Integer id) {
        return "HU-" + String.valueOf(id);
    }

    private String generateParcelNumber(Integer id) {
        return "HU-" + String.valueOf(id);
    }

    @Override
    public List<ParcelDto> findBySeal(String seal) {
        return parcelRepository.findBySeal(seal).stream()
                .map(ParcelDto::fromDomainObject)
                .toList();
    }

    @Override
    public Optional<ParcelDto> findByBarcode(String barcode) {
        return parcelRepository.findByBarcode(barcode)
                .map(ParcelDto::fromDomainObject);
    }
}
