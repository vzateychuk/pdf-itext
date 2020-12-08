package ru.vez;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import java.io.IOException;
import java.util.List;

public class Main {
    private static final String SRC_FILE = "/home/osboxes/tmp/page.pdf";
    private static final String DEST_FILE = "/home/osboxes/tmp/merged.pdf";
    private static final String STAMP_IMG = "/home/osboxes/tmp/stamp.png";

    public static void main(String[] args) throws Exception {

        manipulatePdf(SRC_FILE, DEST_FILE, STAMP_IMG);
    }

    public static void manipulatePdf(String src, String dest, String imgSrc) throws Exception {

        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfPage page = pdfDoc.getFirstPage();

        PdfCanvas canvas = drawCanvas(page, List.of("Большой директор:", "Владимир Затейчук"));

        pdfDoc.close();
        canvas.release();
    }

    private static PdfCanvas drawCanvas(PdfPage page, List<String> strings) throws IOException {

        PdfCanvas canvas = new PdfCanvas(page);
        Color blueColor = ColorConstants.BLUE;
        canvas.setLineWidth(5f).setStrokeColor(blueColor).setColor(blueColor, true);
        final float leftX = 20f;
        final float leftY = 20f;
        canvas.moveTo(leftX,leftY)
            .lineTo(150f, leftY)
            .lineTo(150f, 80f)
            .lineTo(leftX, 80f)
            .lineTo(leftX, leftY).stroke();

        canvas.beginText()
            .setFontAndSize(getPdfFont(), 12)
            .setLeading(12*1.2f)
            .moveText(leftX+5f, leftY+50f);

        for (String string : strings) {
            canvas.newlineShowText(string);
        }
        return canvas.endText();
    }

    private static PdfFont getPdfFont() throws IOException {

        return PdfFontFactory.createFont(
            "/home/osboxes/tmp/freesans.otf",
            "CP1251",
            true
        );
    }
}
