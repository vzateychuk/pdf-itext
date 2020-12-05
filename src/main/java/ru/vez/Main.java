package ru.vez;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfStampAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;

public class Main {
    private static final String SRC_FILE = "/home/osboxes/tmp/page.pdf";
    private static final String DEST_FILE = "/home/osboxes/tmp/merged.pdf";
    private static final String STAMP_IMG = "/home/osboxes/tmp/stamp.png";

    public static void main(String[] args) throws Exception {

        manipulatePdf(SRC_FILE, DEST_FILE, STAMP_IMG);
    }

    public static void manipulatePdf(String src, String dest, String imgSrc) throws Exception {

        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));

        ImageData img = ImageDataFactory.create(imgSrc);
        float width = img.getWidth();
        float height = img.getHeight();
        PdfFormXObject xObj = new PdfFormXObject(new Rectangle(width, height));

        PdfCanvas canvas = new PdfCanvas(xObj, pdfDoc);
        canvas.addImageAt(img, 0, 0, false);

        Rectangle location = new Rectangle(36, 770 - height, width, height);
        PdfStampAnnotation stamp = new PdfStampAnnotation(location).setStampName(new PdfName("ITEXT"));
        stamp.setNormalAppearance(xObj.getPdfObject());

        // Set to print the annotation when the page is printed
        stamp.setFlags(PdfAnnotation.PRINT);
        pdfDoc.getFirstPage().addAnnotation(stamp);

        pdfDoc.close();
        canvas.release();
    }
}
