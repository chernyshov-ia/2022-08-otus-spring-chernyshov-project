package ru.chia2k.vnp.feign;

import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.chia2k.vnp.dto.AddressDto;
import ru.chia2k.vnp.dto.CargoCategoryDto;
import ru.chia2k.vnp.dto.ParcelDto;
import ru.chia2k.vnp.dto.RequestParcelDto;

import java.util.Optional;

@FeignClient(name = "logistics-service")
@Service
public interface LogisticsServiceProxy {
    @PostMapping("api/v1/parcel")
    ParcelDto postParcel(RequestParcelDto request);

    @GetMapping("api/v1/cargoCategory/{id}")
    Optional<CargoCategoryDto> getCargoCategory(@NonNull @PathVariable("id") Integer id);

    @GetMapping("api/v1/address/{id}")
    Optional<AddressDto> getAddresses(@NonNull @PathVariable("id") String id);
}

