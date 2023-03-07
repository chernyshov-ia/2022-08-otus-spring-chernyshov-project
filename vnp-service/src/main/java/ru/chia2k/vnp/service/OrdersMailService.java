package ru.chia2k.vnp.service;

import ru.chia2k.vnp.dto.OrderDto;


public interface OrdersMailService {
    void sendNotifyNewOrder(OrderDto order);
    void sendTicket(OrderDto order, byte[] resource, String ticketFilename);
}
