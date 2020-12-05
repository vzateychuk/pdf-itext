package ru.vez;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;

import java.io.IOException;

public class Main {
    private static final String PAGE_FILE = "/home/osboxes/tmp/page.pdf";
    private static final String SEAL_FILE = "/home/osboxes/tmp/seal.pdf";
    private static final String OUT_FILE = "/home/osboxes/tmp/merged.pdf";

    public static void main(String[] args) throws IOException {

        PdfWriter outWriter = new PdfWriter(OUT_FILE);

        PdfDocument pagePdfDoc = new PdfDocument( new PdfReader(PAGE_FILE), outWriter );
        PdfDocument sealPdfDoc = new PdfDocument( new PdfReader(SEAL_FILE) );

        PdfMerger merger = new PdfMerger(pagePdfDoc);

        merger.merge(sealPdfDoc, 1, sealPdfDoc.getNumberOfPages());

        sealPdfDoc.close();
        pagePdfDoc.close();
    }

}
