package ru.chia2k.vnp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chia2k.vnp.dto.PackVolumeDto;
import ru.chia2k.vnp.dto.PackVolumeRequestDto;
import ru.chia2k.vnp.service.PackVolumeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PackVolumeRestController {
    private final PackVolumeService service;

    @GetMapping("api/v1/packVolume")
    List<PackVolumeDto> getList() {
        return service.findAll();
    }

    @GetMapping("api/v1/packVolume/{id}")
    ResponseEntity<PackVolumeDto> getObject(@PathVariable(name = "id") Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PutMapping("api/v1/packVolume/{id}")
    ResponseEntity<PackVolumeDto> putObject(@PathVariable(name = "id") Integer id, @RequestBody PackVolumeRequestDto request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PostMapping("api/v1/packVolume")
    ResponseEntity<PackVolumeDto> postObject(@RequestBody PackVolumeRequestDto request) {
        return ResponseEntity.ok(service.create(request));
    }

    @DeleteMapping("api/v1/packVolume/{id}")
    ResponseEntity deleteObject(@PathVariable(name = "id") Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
