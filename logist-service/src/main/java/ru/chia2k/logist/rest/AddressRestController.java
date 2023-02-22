package ru.chia2k.logist.rest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.chia2k.logist.domain.Address;
import ru.chia2k.logist.service.AddressService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AddressRestController {
    private final AddressService addressService;

    @GetMapping("api/v1/address")
    public List<Address> addresses(@RequestParam(value = "search", defaultValue = "", required = false) String search){
        if(search == null || "".equals(search)) {
            return addressService.findAll();
        } else {
            return addressService.search(search);
        }
    }

    @GetMapping("api/v1/address/{id}")
    public ResponseEntity<Address> getAddresses(@NonNull @PathVariable("id") String id) {
        return addressService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
