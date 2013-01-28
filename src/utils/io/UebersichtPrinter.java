
// software for charitable associations 
// Copyright (C) 2010  Artem Petrov (artpetro@uos.de) & Leonid Oldenburger (loldenbu@uos.de)

// This program is free software; you can redistribute it and/or modify it under the terms 
// of the GNU General Public License as published by the Free Software Foundation; either 
// version 3 of the License, or (at your option) any later version.

// This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
// without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
// See the GNU General Public License for more details.

// You should have received a copy of the GNU General Public License along with this program; 
// if not, see <http://www.gnu.org/licenses/>.
package utils.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import moneybook.model.Payment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class UebersichtPrinter {

	private List<Payment> payments;
	private Document document;
	private String name;
	
	public UebersichtPrinter(List<Payment> payments, double saldo, String name){
		
		this.name = name;
		this.payments = payments;
		
	}
	 
	 
	public void printPdf(String path) throws FileNotFoundException, DocumentException {

		document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream(path));
		document.open();
		
		PdfPTable table = new PdfPTable(1);
    	table.setWidthPercentage(95.0f);
    	table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    	
		
    	// 1. zeile - name, datum
    	SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		// Name
    	PdfPCell cell = new PdfPCell (new Paragraph(name));
    	cell.setFixedHeight(50f);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	table.addCell(cell);
    	
    	// Datum
    	cell = new PdfPCell (new Paragraph("Kassenbuchübersicht von " + df.format(payments.get(0).getDatum()) + " bis " + df.format(payments.get(payments.size() - 1).getDatum())));
    	cell.setFixedHeight(50f);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	table.addCell(cell);
    	
		// 2.  - payments
		table.addCell(getPaymentsTable());
		
		document.add(table);
		document.close();
		
		Runtime rt = Runtime.getRuntime();
        try{
            @SuppressWarnings("unused")
			Process p = rt.exec( "rundll32" +" " + "url.dll,FileProtocolHandler"
                     + " " +path);
        }catch (Exception e1){
              e1.printStackTrace();
        }
		
	}
    
	
    
    private PdfPTable getPaymentsTable() throws DocumentException {
    	
    	PdfPTable table = new PdfPTable(4);
    	table.setWidthPercentage(100.0f);
    	float[] widths = {20.0f, 50.0f, 15.0f, 15.0f}; 
    	table.setWidths(widths);
    	
    	
    	PdfPCell cell = new PdfPCell (new Paragraph ("Datum"));
    	table.addCell(cell);

    	cell = new PdfPCell (new Paragraph ("Verwendungszweck"));
    	table.addCell(cell);

    	cell = new PdfPCell (new Paragraph ("Haben"));
    	table.addCell(cell);
    	
    	cell = new PdfPCell (new Paragraph ("Soll"));
    	table.addCell(cell);
    	
    	Iterator<Payment> it = payments.iterator();
		Payment p;
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		while(it.hasNext()) {
			p = it.next();
			table.addCell(df.format(p.getDatum()));
			table.addCell(p.getZweck());
			
			double amount = p.getBetrag();
			String amountStr = String.format("%.2f", amount);
			
			if (amount > 0.0) {
			
				table.addCell(amountStr);
				table.addCell("");
			
			}
			else {
			
				table.addCell("");
				table.addCell(amountStr);
				
			
			}
		}
    	
    	return table;
    	
    }
   
   
}
