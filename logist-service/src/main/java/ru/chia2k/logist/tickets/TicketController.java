package ru.chia2k.logist.tickets;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TicketController {
    private final TicketManager ticketManager;

    @GetMapping("/api/v1/parcel/{id}/ticket")
    public ResponseEntity<byte[]> getTicket(@PathVariable("id") Integer id) throws Exception {
        var payload = ticketManager.getTicket(id, "pdf");

        var contentDisposition = ContentDisposition.builder("inline")
                .filename(String.format("Этикетка %s.pdf", id))
                .build();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(payload);
    }
}
