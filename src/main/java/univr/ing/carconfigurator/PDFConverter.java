package univr.ing.carconfigurator;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFConverter {
    
    private String outputPath;
    private Preventivo quotation;
    
    public PDFConverter(String outputPath, Preventivo quotation) {
        this.outputPath = outputPath;
        this.quotation = quotation;
    }
    
    public void printPdf() {
        try {
            // Crea il writer PDF
            PdfWriter writer = new PdfWriter(outputPath);
        
            // Crea il documento PDF e imposta i margini e font
            Document pdfDocument = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));
            pdfDocument.setMargins(30, 30, 30, 30); // margini standard Agenzia delle Entrate
            PdfFont titleFont = PdfFontFactory.createFont("Times-Bold"); // Grassetto per il titolo
            PdfFont normalFont = PdfFontFactory.createFont("Times-Roman"); // Font di default
            
            // Generazione del titolo
            String title = "Preventivo " + quotation.getConfiguredCar().getName();
            Paragraph header = new Paragraph(title);
            header.setFont(titleFont).setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.TOP)
                    .setMarginBottom(20);
            pdfDocument.add(header);
            
            // Generazione dati cliente
            String content = "Dati cliente";
            pdfDocument.add(new Paragraph(content)
                    .setFont(titleFont)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER)
            );
            
            Cliente customer = new Cliente(quotation.getUserID());
            // Creazione di una tabella per formattare il documento
            Table table = new Table(2);
            table.setWidth(UnitValue.createPercentValue(100));
            table.setFont(normalFont);
            
            // Dati a sinistra
            content = "Cliente: " + customer.getUserName() + " " + customer.getUserLastName();
            Cell leftCell = new Cell().add(new Paragraph(content).setFontSize(12))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(null);
            table.addCell(leftCell);
            // Dati a destra
            content = "Sede negozio: " + quotation.getShopLocation();
            Cell rightCell = new Cell().add(new Paragraph(content).setFontSize(12))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(null);
            table.addCell(rightCell);
            table.setMarginBottom(20);
            pdfDocument.add(table);
            
            // Generazione dati veicolo configurato
            content = "Veicolo configurato";
            pdfDocument.add(new Paragraph(content)
                    .setFont(titleFont)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(14)
            );
            
            content = "Riepilogo dimensioni ";
            pdfDocument.add(new Paragraph(content)
                    .setFont(titleFont)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12)
            );
            
            // Creo la tabella per i dati tecnici della vettura
            table = new Table(3);
            table.setWidth(UnitValue.createPercentValue(100));
            table.setFont(normalFont).setFontSize(12);
            
            content = "Larghezza: " + String.format("%.2f", quotation.getConfiguredCar().getWidth()) + "cm";
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.LEFT)
            );
            content = "Lunghezza: " + String.format("%.2f", quotation.getConfiguredCar().getLength()) + "cm";
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.CENTER)
            );
            content = "Altezza: " + String.format("%.2f", quotation.getConfiguredCar().getHeight()) + "cm";
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.RIGHT)
            );
            content = "Peso: " + String.format("%.2f", quotation.getConfiguredCar().getWeight()) + "kg";
            table.addCell(new Cell().add(new Paragraph(content))
                            .setBorder(null)
                    .setTextAlignment(TextAlignment.LEFT)
            );
            content = "Volume bagagliaio: " + String.format("%.2f",quotation.getConfiguredCar().getTrunkVol()) + "l";
            table.addCell((new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.CENTER))
            );
            pdfDocument.add(table);
    
            content = "Motore: " + quotation.getConfiguredCar().getEngine().getName();
            pdfDocument.add(new Paragraph(content)
                    .setFont(titleFont)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12)
            );
            
            table = new Table(2);
            table.setFontSize(12).setFont(normalFont).setWidth(UnitValue.createPercentValue(100));
            
            content = "Accelerazione: " + quotation.getConfiguredCar().getEngine().getAccelerationTime() + "km/h";
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.LEFT)
            );
            
            content = "Alimentazione: " + quotation.getConfiguredCar().getEngine().getFuelType();
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.LEFT)
            );
            
            content = "Cilindrata: " + quotation.getConfiguredCar().getEngine().getDisplacement() + "cc";
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.LEFT)
            );
            
            content = "Emissioni: " + quotation.getConfiguredCar().getEngine().getGramsCO2perKm();
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.LEFT)
            );
            
            content = "Potenza: " + quotation.getConfiguredCar().getEngine().getPower();
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.LEFT)
            );
            
            content = "Consumi: " + quotation.getConfiguredCar().getEngine().getConsumption();
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.LEFT)
            );
            table.setMarginBottom(20);
            pdfDocument.add(table);
            
            content = "Prezzo allestimento: " + String.format("%.2f", quotation.getConfiguredCar().getEngine().getPrice())
                + "€";
            pdfDocument.add(new Paragraph(content)
                    .setFont(titleFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontColor(new DeviceRgb(17,121,255))
                    
            );
            
            // Generazione riepilogo optional
            content = "Optional";
            pdfDocument.add(new Paragraph(content)
                    .setFont(titleFont)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12)
            );
            
            // Tabella degli optional
            table = new Table(2);
            table.setWidth(UnitValue.createPercentValue(100));
            table.setFont(normalFont).setFontSize(12);
            
            content = "Colore: " + quotation.getConfiguredCar().getColor();
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.LEFT)
            );
            content = String.format("%.2f", quotation.getConfiguredCar().getColorPrice()) + "€";
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontColor(new DeviceRgb(17,121,255))
            );
            content = "Cerchi: " + quotation.getConfiguredCar().getColor();
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.LEFT)
            );
            content = String.format("%.2f", quotation.getConfiguredCar().getCircle().getPrice());
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontColor(new DeviceRgb(17,121,255))
            );
            content = "Sensori: " + (quotation.getConfiguredCar().getSensor().equals("null") ?
                    quotation.getConfiguredCar().getSensor().toString() : "nessuno selezionato");
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.LEFT)
            );
            content = String.format("%.2f", quotation.getConfiguredCar().getSensor().getPrice());
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontColor(new DeviceRgb(17,121,255))
            );
            content = "Interni: " + (quotation.getConfiguredCar().getInterior().equals("null") ?
                    quotation.getConfiguredCar().getInterior().toString() : "di serie");
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.LEFT)
            );
            content = String.format("%.2f", quotation.getConfiguredCar().getInterior().getPrice());
            table.addCell(new Cell().add(new Paragraph(content))
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontColor(new DeviceRgb(17,121,255))
            );
            
            table.setMarginBottom(20);
            pdfDocument.add(table);
    
            PageSize pageSize = pdfDocument.getPdfDocument().getDefaultPageSize();
            // Prezzo finale
            content = "Prezzo finale";
            pdfDocument.add(new Paragraph(content)
                    .setFixedPosition(30, pageSize.getBottom() + 150, pageSize.getWidth() - 60)
                    .setFont(titleFont)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(14)
            );
            // Se e' presente una valutazione dell'usato la inseriamo nel preventivo in qualita' di sconto
            
            if (quotation.getOldCarValue() > 0) {
                
                table = new Table(2);
                table.setWidth(UnitValue.createPercentValue(100));
                table.setFont(normalFont).setFontSize(12).setFontColor(new DeviceRgb(17,121,255));
                table.setFixedPosition(30, pageSize.getBottom() + 100, pageSize.getWidth()-60);
                
                content = "Sconto: ";
                table.addCell(new Cell().add(new Paragraph(content))
                        .setBorder(null)
                        .setTextAlignment(TextAlignment.RIGHT)
                );
                content = String.format("%.2f", quotation.getOldCarValue());
                table.addCell(new Cell().add(new Paragraph(content))
                        .setBorder(null)
                        .setTextAlignment(TextAlignment.RIGHT)
                );
                content = "Prezzo finale: ";
                table.addCell(new Cell().add(new Paragraph(content))
                        .setBorder(null)
                        .setTextAlignment(TextAlignment.RIGHT)
                );
                content = String.format("%.2f", quotation.getConfiguredCar().getPrice());
                table.addCell(new Cell().add(new Paragraph(content))
                        .setBorder(null)
                        .setTextAlignment(TextAlignment.RIGHT)
                );
                pdfDocument.add(table);
            } else {
    
    
                table = new Table(2);
                table.setWidth(UnitValue.createPercentValue(100));
                table.setFont(normalFont).setFontSize(12).setFontColor(new DeviceRgb(17, 121, 255));
                table.setFixedPosition(0, pageSize.getBottom() + 100, pageSize.getWidth());
    
                content = "Prezzo finale: ";
                table.addCell(new Cell().add(new Paragraph(content))
                        .setBorder(null)
                        .setTextAlignment(TextAlignment.RIGHT)
                );
                content = String.format("%.2f", quotation.getConfiguredCar().getPrice());
                table.addCell(new Cell().add(new Paragraph(content))
                        .setBorder(null)
                        .setTextAlignment(TextAlignment.RIGHT)
                );
                pdfDocument.add(table);
            }
            
            // Chiusura documento
            pdfDocument.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
