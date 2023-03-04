package ru.chia2k.logist.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.chia2k.logist.barcode.BarcodeGenerator;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BarcodeRestController {
    private final BarcodeGenerator barcodeGenerator;

    @GetMapping("barcode")
    public ResponseEntity<byte[]> generate(
            @RequestParam(value = "type") String type,
            @RequestParam(value = "text") String text,
            @RequestParam(value = "width") Integer width,
            @RequestParam(value = "height") Integer height)
    {
        byte[] image = null;
        try {
            if ("QR".equalsIgnoreCase(type)) {
                image = barcodeGenerator.generateQRCodeImage(text, width, height, "png");
            } else if ("CODE128".equalsIgnoreCase(type)) {
                image = barcodeGenerator.generateCode128Image(text, width, height, "png");
            }
        } catch (Exception e) {
            log.warn("{}", e);
        }

        if (image == null) {
            return ResponseEntity.badRequest().build();
        }

        var contentDisposition = ContentDisposition.builder("inline")
                .filename("image.png")
                .build();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(image);
    }

}
