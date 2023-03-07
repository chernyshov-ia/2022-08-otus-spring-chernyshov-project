package ru.chia2k.logist.tickets;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.chia2k.logist.tickets.exception.CreateTicketException;
import ru.chia2k.logist.service.ParcelService;
import ru.chia2k.logist.tickets.exception.TicketManagerException;

@Component
@Slf4j
@RequiredArgsConstructor
public class TicketManagerImpl implements TicketManager {
    private final PdfTicketProvider pdfTicketProvider;
    private final ParcelService parcelService;

    @Override
    public byte[] getTicket(Integer parcelId, String type) throws Exception {
        var provider = getProvider(type);

        var parcel = parcelService.findById(parcelId)
                .orElseThrow(() -> new CreateTicketException("Parcel id=" + String.valueOf(parcelId) + " not found"));

        log.debug("Parcel data found: id = {}", parcelId);

        var bytes = provider.create(parcel);

        log.debug("Ticket created. Size = {} bytes.", bytes.length);

        return bytes;
    }

    private TicketProvider getProvider(String type) {
        if (pdfTicketProvider.getType().equalsIgnoreCase(type)) {
            return pdfTicketProvider;
        }
        throw new TicketManagerException("Ticket provider not found: " + type);
    }
}
