package ru.chia2k.logist.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.chia2k.logist.dto.CargoCategoryDto;
import ru.chia2k.logist.service.CargoCategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CargoCategoryRestController {
    private final CargoCategoryService service;

    @GetMapping("api/v1/cargoCategory")
    public List<CargoCategoryDto> list(){
        return service.findAll();
    }

    @GetMapping("api/v1/cargoCategory/{id}")
    public ResponseEntity<CargoCategoryDto> object(@PathVariable("id") Integer id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
