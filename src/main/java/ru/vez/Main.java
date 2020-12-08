package ru.vez;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static final String SRC_FILE = "/home/osboxes/tmp/page.pdf";
    private static final String DIRECTOR_FILE = "/home/osboxes/tmp/director.txt";
    private static final String MERGED_DIRECTOR_FILE = "/home/osboxes/tmp/merged_director.pdf";
    private static final String ENGINEER_FILE = "/home/osboxes/tmp/engineer.txt";
    private static final String MERGED_ENGINEER_FILE = "/home/osboxes/tmp/merged_engineer.pdf";

    public static void main(String[] args) throws Exception {

        Person director = readFromFile(DIRECTOR_FILE);
        mergeAndSavePdfToFile(SRC_FILE, MERGED_DIRECTOR_FILE, director);

        Person engineer = readFromFile(ENGINEER_FILE);
        mergeAndSavePdfToFile(SRC_FILE, MERGED_ENGINEER_FILE, engineer);
    }


    public static void mergeAndSavePdfToFile(String src, String dest, Person person) throws Exception {

        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        pdfDoc.getCatalog().put(PdfName.Lang, new PdfString("RU"));
        Document doc = new Document(pdfDoc);
        doc.setMargins(20,20,20,20);

        Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();

        String string = person.getPosition() + ": " + System.lineSeparator() + person.getName();
        Cell nameCell = getPreparedCell(string);

        table.addCell(nameCell);

        doc.add(table);
        doc.close();
    }

    // region Private

    private static Cell getPreparedCell(String string) throws IOException {

        PdfFont fontrus = getPdfFont();
        Text text = new Text(string);
        text.setFont(fontrus);

        return new Cell().add(
            new Paragraph(text).setFontSize(10).setFontColor(ColorConstants.BLUE)
        )
            .setBorder(new SolidBorder(ColorConstants.BLUE, 2))
            .setTextAlignment(TextAlignment.CENTER);
    }

    private static PdfFont getPdfFont() throws IOException {

        return PdfFontFactory.createFont(
            "/home/osboxes/tmp/freesans.otf",
            "CP1251",
            true
        );
    }

    private static Person readFromFile(String personFile) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(personFile));
        String name = br.readLine();
        String position = br.readLine();
        return new Person(name, position);
    }

    // endregion
}
