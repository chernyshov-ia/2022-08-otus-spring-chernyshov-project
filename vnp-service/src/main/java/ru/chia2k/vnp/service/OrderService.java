package ru.chia2k.vnp.service;

import jakarta.validation.Valid;
import ru.chia2k.vnp.dto.OrderDto;
import ru.chia2k.vnp.dto.RequestOrderDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<OrderDto> findByIdForCurrentUser(Integer id);
    Optional<OrderDto> findById(Integer id);
    Optional<OrderDto> findByParcelId(Integer id);
    List<OrderDto> findAllForCurrentUser(LocalDateTime from, LocalDateTime to);
    OrderDto create(@Valid RequestOrderDto request);
    OrderDto createAndNotifyByEmail(@Valid RequestOrderDto request);
}
