package ru.chia2k.logist.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class ZXingBarcodeGenerator implements BarcodeGenerator{
    @Override
    public byte[] generateQRCodeImage(String barcodeText, int width, int height, String format) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix,format, bos);
        var bytes = bos.toByteArray();
        bos.close();
        return bytes;
    }

    @Override
    public byte[] generateCode128Image(String barcodeText, int width, int height, String format) throws Exception {
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, width, height);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix,format, bos);
        var bytes = bos.toByteArray();
        bos.close();
        return bytes;
    }
}
