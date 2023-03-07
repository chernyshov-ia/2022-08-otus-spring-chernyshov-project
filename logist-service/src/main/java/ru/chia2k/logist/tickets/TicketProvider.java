package ru.chia2k.logist.tickets;

import ru.chia2k.logist.dto.ParcelDto;

public interface TicketProvider {
    String getType();

    byte[] create(ParcelDto parcel) throws Exception;
}
