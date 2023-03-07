package ru.chia2k.logist.tickets;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Component;
import ru.chia2k.logist.barcode.BarcodeGenerator;
import ru.chia2k.logist.dto.ParcelDto;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class PdfTicketProvider implements TicketProvider {
    public static final String TICKET_TYPE = "pdf";

    private static final int TITLE_FONT_SIZE = 14;
    private static final String TITLE_TEMPLATE = "Препроводительная ведомость %s";

    private final BarcodeGenerator barcodeGenerator;

    @Override
    public byte[] create(ParcelDto parcel) throws Exception {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            var classLoader = TicketProvider.class.getClassLoader();

            InputStream is;
            is = classLoader.getResourceAsStream("fonts/Roboto-Regular.ttf");
            var fontRobotoRegular = PDType0Font.load(doc, is);
            is = classLoader.getResourceAsStream("fonts/Roboto-Medium.ttf");
            var fontRobotoMedium = PDType0Font.load(doc, is);
            is = classLoader.getResourceAsStream("fonts/Roboto-Bold.ttf");
            var fontRobotoBold = PDType0Font.load(doc, is);

            float pageWidth = page.getTrimBox().getWidth();
            float pageHeight = page.getTrimBox().getHeight();

            try (PDPageContentStream c = new PDPageContentStream(doc, page)) {
                var titleText = String.format(TITLE_TEMPLATE, parcel.getNumber());
                var titleHorizontalOffset = (pageWidth - calculateTextWidth(titleText, fontRobotoMedium, TITLE_FONT_SIZE)) / 2;
                var titleVerticalOffset = pageHeight - 50f;
                c.beginText();
                c.setFont(fontRobotoMedium, TITLE_FONT_SIZE);
                c.newLineAtOffset(titleHorizontalOffset, titleVerticalOffset);
                c.showText(titleText);
                c.endText();

                drawBarcode(doc, page, c, parcel.getBarcode());

                c.setStrokingColor(Color.DARK_GRAY);
                c.setLineWidth(1);

                c.addRect(15f, pageHeight - 260f, pageWidth - 30f, 255f );

                float lineHeight = 25f;
                float valueOffset = 10f;
                float y = 90f;
                float leftLine = 140f;
                float rx, ry, rh;

                String text;
                text = "Пломба №:";

                float textWidth = calculateTextWidth(text, fontRobotoRegular, 12);

                c.beginText();
                c.setFont(fontRobotoRegular, 12);
                c.newLineAtOffset(leftLine - textWidth, pageHeight - y);
                c.showText(text);
                c.endText();

                c.beginText();
                c.setFont(fontRobotoBold, 12);
                c.newLineAtOffset(leftLine + valueOffset, pageHeight - y);
                c.showText(parcel.getSeal());
                c.endText();

                y += lineHeight;
                text = "Дата:";
                textWidth = calculateTextWidth(text, fontRobotoRegular, 12);
                c.beginText();
                c.setFont(fontRobotoRegular, 12);
                c.newLineAtOffset(leftLine - textWidth, pageHeight - y);
                c.showText(text);
                c.endText();

                c.beginText();
                c.setFont(fontRobotoBold, 12);
                c.newLineAtOffset(leftLine + valueOffset, pageHeight - y);
                text = parcel.getCreatedAt().atZone(ZoneId.of("UTC+3")).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString();
                c.showText(text);
                c.endText();

                y += lineHeight;
                text = "Получатель:";
                textWidth = calculateTextWidth(text, fontRobotoRegular, 12);
                c.beginText();
                c.setFont(fontRobotoRegular, 12);
                c.newLineAtOffset(leftLine - textWidth, pageHeight - y);
                c.showText(text);
                c.endText();

                ry = y - 12f;
                c.beginText();
                c.setFont(fontRobotoBold, 12);
                c.newLineAtOffset(leftLine + valueOffset, pageHeight - y);
                text = String.format( "%s (%s)",
                        parcel.getRecipient().getName(),
                        parcel.getRecipient().getId()
                );
                c.showText(text);
                c.endText();

                y += 17f;
                c.beginText();
                c.setFont(fontRobotoRegular, 10);
                c.newLineAtOffset(leftLine + valueOffset, pageHeight - y);
                text = parcel.getRecipient().getAddress();
                c.showText(text);
                c.endText();

                rh = y - ry + 12f;
                ry = y + 12f;


                c.addRect(leftLine + valueOffset - 5f, pageHeight - ry, pageWidth - leftLine - 30f, rh);

                y += (lineHeight + 10f);
                ry = y - 12f;
                text = "Описание:";
                textWidth = calculateTextWidth(text, fontRobotoRegular, 12);
                c.beginText();
                c.setFont(fontRobotoRegular, 12);
                c.newLineAtOffset(leftLine - textWidth, pageHeight - y);
                c.showText(text);
                c.endText();

                c.beginText();
                c.setFont(fontRobotoRegular, 10);
                c.newLineAtOffset(leftLine + valueOffset, pageHeight - y);
                c.showText(parcel.getDescription());
                c.endText();

                rh = y - ry + 20f;
                ry = y + 20f;

                c.addRect(leftLine + valueOffset - 5f, pageHeight - ry, pageWidth - leftLine - 30f, rh);

                y += (lineHeight + 15f);
                text = "Грузоотправитель:";
                textWidth = calculateTextWidth(text, fontRobotoRegular, 12);
                c.beginText();
                c.setFont(fontRobotoRegular, 12);
                c.newLineAtOffset(leftLine - textWidth, pageHeight - y);
                c.showText(text);
                c.endText();

                c.beginText();
                c.setFont(fontRobotoBold, 12);
                c.newLineAtOffset(leftLine + valueOffset, pageHeight - y);
                c.showText( String.format("%s (%s)", parcel.getSender().getName(), parcel.getSender().getId()));
                c.endText();
                c.stroke();
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            doc.save(bos);
            return bos.toByteArray();
        }
    }

    @Override
    public String getType() {
        return TICKET_TYPE;
    }

    private void drawBarcode(PDDocument doc, PDPage page, PDPageContentStream context, String barcode) throws Exception {
        var image = barcodeGenerator.generateQRCodeImage(barcode,80, 80, "png");
        PDImageXObject pdImage = PDImageXObject.createFromByteArray(doc, image, "barcode");

        float offsetX = page.getTrimBox().getWidth() - pdImage.getWidth() - 15f;
        float offsetY = page.getTrimBox().getUpperRightY() - pdImage.getHeight() - 10f;

        context.drawImage(pdImage, offsetX, offsetY, pdImage.getWidth(), pdImage.getHeight());
    }

    private float calculateTextWidth(String text, PDType0Font font, int fontSize) throws IOException {
        return font.getStringWidth(text) / 1000 * fontSize;
    }
}
