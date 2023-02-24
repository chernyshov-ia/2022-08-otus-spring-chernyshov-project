package ru.chia2k.logist.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
}
