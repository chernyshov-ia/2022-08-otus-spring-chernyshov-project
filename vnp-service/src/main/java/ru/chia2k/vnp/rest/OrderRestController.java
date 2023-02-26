package ru.chia2k.vnp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chia2k.vnp.dto.OrderDto;
import ru.chia2k.vnp.dto.RequestOrderDto;
import ru.chia2k.vnp.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;

    @PostMapping("/api/v1/order")
    OrderDto createOrder(@RequestBody RequestOrderDto request) {
        return orderService.create(request);
    }

    @GetMapping("/api/v1/order/{id}")
    ResponseEntity<OrderDto> getOrder(@PathVariable(name = "id") Integer id) {
        return orderService.findByIdForCurrentUser(id).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }
}
