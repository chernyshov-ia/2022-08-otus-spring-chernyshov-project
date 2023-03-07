package ru.chia2k.logist.rest;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chia2k.logist.dto.ParcelDto;
import ru.chia2k.logist.dto.RequestParcelDto;
import ru.chia2k.logist.service.ParcelService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParcelRestController {
    private final ParcelService parcelService;

    @GetMapping("api/v1/parcel/{id}")
    public ResponseEntity<ParcelDto> getParcel(@NonNull @PathVariable("id") Integer id) {
        return parcelService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("api/v1/parcel")
    public ResponseEntity<List<ParcelDto>> getParcels(
            @RequestParam(value = "seal", required = false, defaultValue = "") String seal,
            @RequestParam(value = "barcode", required = false, defaultValue = "") String barcode
    )
    {
        if(seal != null && !"".equals(seal)) {
            return ResponseEntity.ok(parcelService.findBySeal(seal));
        }

        if(barcode != null && !"".equals(barcode)) {
            return ResponseEntity.ok(parcelService.findBySeal(barcode));
        }

        return ResponseEntity.ok(List.of());
    }

    @PostMapping("api/v1/parcel")
    public ResponseEntity<ParcelDto> postParcel(@Valid @RequestBody RequestParcelDto request) {
        return ResponseEntity.ok(parcelService.create(request));
    }
}
