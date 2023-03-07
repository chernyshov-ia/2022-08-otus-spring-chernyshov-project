package ru.chia2k.logist.tickets;

import org.springframework.stereotype.Component;

@Component
public interface TicketManager {
    byte[] getTicket(Integer parcelId, String type) throws Exception;
}
