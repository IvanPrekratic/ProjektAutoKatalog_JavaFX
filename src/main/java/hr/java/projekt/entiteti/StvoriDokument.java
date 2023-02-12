package hr.java.projekt.entiteti;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import hr.java.projekt.glavna.AutoKatalog;
import hr.java.projekt.login.SessionMenager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class StvoriDokument {


    public static void stvoriDokument(List<CartItem> kosarica){

        String[] headers = new String[]{"Kataloski broj", "Model auta", "Naziv proizvoda", "Kolicina", "Cijena (EUR)"};
        String[][] rows = new String[kosarica.size()][5];

        for (int i = 0; i< kosarica.size(); i++) {
            rows[i][0] = kosarica.get(i).getProizvod().getPartNumber();
            rows[i][1] = kosarica.get(i).getProizvod().getCar().getMake() + " " + kosarica.get(i).getProizvod().getCar().getModel();
            rows[i][2] = kosarica.get(i).getProizvod().getName();
            rows[i][3] = kosarica.get(i).getKolicina().toString();
            rows[i][4] = kosarica.get(i).getProizvod().getPartPrice().toString();
        }


        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Dokument.pdf"));
            document.open();
            BaseFont courier = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.EMBEDDED);
            Font fontHeader = new Font(courier, 12, Font.BOLD);
            Font fontRow = new Font(courier, 10, Font.NORMAL);

            PdfPTable table = new PdfPTable(headers.length);
            for (String header : headers) {
                PdfPCell cell = new PdfPCell();
                cell.setGrayFill(0.9f);
                cell.setPhrase(new Phrase(header.toUpperCase(), fontHeader));
                table.addCell(cell);
            }
            table.completeRow();

            for (String[] row : rows) {
                for (String data : row) {
                    Phrase phrase = new Phrase(data, fontRow);
                    table.addCell(new PdfPCell(phrase));
                }
                table.completeRow();
            }
            document.addAuthor(SessionMenager.username);
            document.addTitle("Ispis košarice");
            document.add(table);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            document.close();
        }
    }


}
