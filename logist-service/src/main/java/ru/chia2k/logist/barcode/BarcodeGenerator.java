package ru.chia2k.logist.barcode;

public interface BarcodeGenerator {
    byte[] generateQRCodeImage(String barcodeText, int width, int height, String format) throws Exception;
    byte[] generateCode128Image(String barcodeText, int width, int height, String format) throws Exception;
}
