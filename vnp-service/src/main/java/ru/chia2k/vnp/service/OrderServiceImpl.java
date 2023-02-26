package ru.chia2k.vnp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.chia2k.vnp.domain.Order;
import ru.chia2k.vnp.domain.OrderAddress;
import ru.chia2k.vnp.dto.*;
import ru.chia2k.vnp.exception.ObjectNotFoundException;
import ru.chia2k.vnp.feign.LogisticsServiceProxy;
import ru.chia2k.vnp.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CompanyPrincipalProvider principalProvider;
    private final LogisticsServiceProxy logisticsProxy;

    @Override
    public Optional<OrderDto> findByIdForCurrentUser(Integer id) {
        var accountNumber = principalProvider.getPrincipal().getAccountNumber();
        return orderRepository.findByIdAndUserId(id, accountNumber).map(OrderDto::from);
    }

    @Override
    public List<OrderDto> findAllForCurrentUser(LocalDateTime from, LocalDateTime to) {
        var accountNumber = principalProvider.getPrincipal().getAccountNumber();
        return orderRepository.findAllByUserIdAndCreatedAtBetween(accountNumber, from, to).stream()
                .map(OrderDto::from).toList();
    }

    @Transactional
    @Override
    public OrderDto create(RequestOrderDto request) {
        Order order = createOrderEntity(request);
        order = orderRepository.saveAndFlush(order);
        createParcel(order);
        order = orderRepository.save(order);
        return OrderDto.from(order);
    }

    private void createParcel(Order order) {
        String description = combineDescription(order);

        RequestParcelDto request = RequestParcelDto.builder()
                .seal(order.getSeal())
                .recipientId(order.getRecipient().getCode())
                .senderId(order.getSender().getCode())
                .description(description)
                .cargoCategoryId(order.getCargoCategoryId())
                .volume(order.getVolume())
                .weight(order.getWeight())
                .value(order.getValue())
                .build();

        ParcelDto parcel = logisticsProxy.postParcel(request);

        order.setParcelId(parcel.getId());
        order.setParcelBarcode(parcel.getBarcode());
    }

    private String getRecipientPersonText(Order order) {
        var sb = new StringBuilder();

        if (order.getRecipientPersonName() != null && !"".equals(order.getRecipientPersonName().trim())) {
            sb.append("Получатель: ");
            sb.append(order.getRecipientPersonName());

            if (order.getRecipientPersonPhone() != null && !"".equals(order.getRecipientPersonPhone().trim())) {
                sb.append(", тел. ");
                sb.append(order.getRecipientPersonPhone());
            }
        } else if (order.getRecipientPersonPhone() != null && !"".equals(order.getRecipientPersonPhone().trim())) {
            sb.append("Телефон получателя: ");
            sb.append(order.getRecipientPersonPhone());
        }
        return sb.toString();
    }

    private String combineDescription(Order order) {
        var sb = new StringBuilder();
        var personText = getRecipientPersonText(order);
        if (!"".equals(personText)) {
            sb.append(personText);
            sb.append("; ");
        }
        sb.append(order.getDescription());
        return sb.toString();
    }

    private Order createOrderEntity(RequestOrderDto request) {
        var principal = principalProvider.getPrincipal();

        CargoCategoryDto cargoCategory = logisticsProxy.getCargoCategory(request.getCargoCategoryId())
                .orElseThrow(() -> new ObjectNotFoundException("cargoCategoryId", request.getCargoCategoryId().toString()));

        AddressDto sender = logisticsProxy.getAddresses(request.getSenderId())
                .orElseThrow(() -> new ObjectNotFoundException("senderId", request.getSenderId()));

        AddressDto recipient = logisticsProxy.getAddresses(request.getRecipientId())
                .orElseThrow(() -> new ObjectNotFoundException("recipientId", request.getRecipientId()));

        Order order = new Order();
        order.setSender(createOrderAddressEntity(sender));
        order.setRecipient(createOrderAddressEntity(recipient));
        order.setSeal(request.getSeal());
        order.setCargoCategoryId(cargoCategory.id());
        order.setCargoCategoryName(cargoCategory.name());
        order.setDescription(request.getDescription());
        order.setUserCommentText(request.getUserCommentText());
        order.setUserId(principal.getAccountNumber());
        order.setUserName(principal.getFullName());
        order.setUserEmail(principal.getEmail());
        order.setVolume(request.getVolume());
        order.setWeight(request.getWeight());
        order.setValue(request.getValue());

        if (request.getRecipientPerson() != null) {
            order.setRecipientPersonName(request.getRecipientPerson().name());
            order.setRecipientPersonPhone(request.getRecipientPerson().phone());
        }

        return order;
    }

    private OrderAddress createOrderAddressEntity(AddressDto dto) {
        OrderAddress address = new OrderAddress();
        address.setCode(dto.id());
        address.setName(dto.name());
        address.setAddress(dto.address());
        return address;
    }

}
