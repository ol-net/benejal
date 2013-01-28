
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

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import projects.model.Project;

import moneybook.model.KassenBuch;

import association.model.AssociationDataTransfer;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import finstatement.model.BookingAreasTableModel;
import finstatement.model.BookingSubArea;



/**
 * Speichert ein Jahresabschluss in Form von einer PDF-Datei
 * @author Artem Petrov
 *
 */
public class JahresAbschlussPrinter {
	
	public static final int SHOW_ROOT_PROJECTS = 1;
	public static final int SHOW_PROJECTS_AND_SUBPROJECTS = 2;
	
	private Document document;
	private KassenBuch moneybook;
	private AssociationDataTransfer associationDataTransfer;
	private int year;
	private int viewmode;
	
	/**
	 * Erzeugt einen Printer
	 * @param moneybook Kassenbuch
	 * @param associationDataTransfer AssociationDataTransfer
	 * @param year Jahr
	 * @param viewmode Anzeigemodus
	 */
	public JahresAbschlussPrinter(KassenBuch moneybook, AssociationDataTransfer associationDataTransfer, int year, int viewmode) {
		
		this.associationDataTransfer = associationDataTransfer;
		this.moneybook = moneybook;
		this.year = year;
		this.viewmode = viewmode;
		
	}


	/**
	 * Speichert JA als eine PDF-Datei
	 * @param path Pfad
	 * @throws Exception falls das Speichern fehlerhaft ist
	 */
	public void printPdf(String path) throws Exception {
	
		
		document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream(path));
		document.open();
		
		addBalance();
		
		addAreas();
		
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
	
	// erste Seite mit Summen 
	private void addBalance() throws DocumentException {
	
		PdfPTable table = new PdfPTable(2);
    	table.setWidthPercentage(80.0f);
    	table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    	
    	double endsaldo = moneybook.getSaldo(year - 1);
    	
    	float[] widths = {80.0f, 20.0f}; 
    	
		table.setWidths(widths);
	
		
    	String text = "Jahresabschluss des Vereins " + associationDataTransfer.getAssociation().getName();
    	// TODO font
    	Phrase phrase = new Phrase(new Chunk(text));

    	PdfPCell cell = new PdfPCell (new Paragraph(phrase));
    	cell.setColspan(2);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	table.addCell(cell);
    	
    	// Jahr
    	text = "für das Geschäftsjahr " + year;
    	// TODO font
    	phrase = new Phrase(new Chunk(text));

    	cell = new PdfPCell (new Paragraph(phrase));
    	cell.setColspan(2);
    	cell.setFixedHeight(50f);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	table.addCell(cell);
    	
    	// startsaldo
    	text = "Saldo per 01.01." + year;
    	// TODO font
    	phrase = new Phrase(new Chunk(text));

    	cell = new PdfPCell (new Paragraph(phrase));
    	cell.setFixedHeight(50f);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	cell = new PdfPCell (new Paragraph(String.format("%.2f €", endsaldo)));
    	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	// Bereich 0
    	text = "+ Gewinn (- Verlust) aus ideellem Bereich";
    	// TODO font
    	phrase = new Phrase(new Chunk(text));

    	cell = new PdfPCell (new Paragraph(phrase));
    	cell.setFixedHeight(50f);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	double balance = moneybook.getBookingAreaCredit(0, year) + moneybook.getBookingAreaDebit(0, year);
    	endsaldo += balance;
    	
    	cell = new PdfPCell (new Paragraph(String.format("%.2f €", balance)));
    	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	// Bereich 1
    	text = "+ Gewinn (- Verlust) aus Vermögensverwaltung";
    	// TODO font
    	phrase = new Phrase(new Chunk(text));

    	cell = new PdfPCell (new Paragraph(phrase));
    	cell.setFixedHeight(50f);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	balance = moneybook.getBookingAreaCredit(1, year) + moneybook.getBookingAreaDebit(1, year);
    	endsaldo += balance;
    	
    	cell = new PdfPCell (new Paragraph(String.format("%.2f €", balance)));
    	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	// Bereich 2
    	text = "+ Gewinn (- Verlust) aus Zweckbetrieb";
    	// TODO font
    	phrase = new Phrase(new Chunk(text));

    	cell = new PdfPCell (new Paragraph(phrase));
    	cell.setFixedHeight(50f);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	balance = moneybook.getBookingAreaCredit(2, year) + moneybook.getBookingAreaDebit(2, year);
    	endsaldo += balance;
    	
    	cell = new PdfPCell (new Paragraph(String.format("%.2f €", balance)));
    	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	// Bereich 3
    	text = "+ Gewinn (- Verlust) aus wirtschaftlichem Geschäftsbetrieb";
    	// TODO font
    	phrase = new Phrase(new Chunk(text));

    	cell = new PdfPCell (new Paragraph(phrase));
    	cell.setFixedHeight(50f);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	balance = moneybook.getBookingAreaCredit(3, year) + moneybook.getBookingAreaDebit(3, year);
    	endsaldo += balance;
    	
    	cell = new PdfPCell (new Paragraph(String.format("%.2f €", balance)));
    	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	// endsaldo
    	text = "Saldo per 31.12." + year;
    	// TODO font
    	phrase = new Phrase(new Chunk(text));

    	cell = new PdfPCell (new Paragraph(phrase));
    	cell.setFixedHeight(50f);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	cell = new PdfPCell (new Paragraph(String.format("%.2f €", endsaldo)));
    	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	cell = new PdfPCell();
    	cell.setFixedHeight(400f);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	cell.setColspan(2);
    	table.addCell(cell);
    	
    	document.add(table);
		
	}
	
	private void addAreas() throws DocumentException {

		PdfPTable table = new PdfPTable(1);
    	table.setWidthPercentage(100.0f);
    	table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
	
    	table.addCell(getAreaTable(KassenBuch.IDEALISTIC_AREA));
    	table.addCell(new Paragraph (" "));
    	
    	table.addCell(getAreaTable(KassenBuch.PROPERTY_AREA));
    	table.addCell(new Paragraph (" "));
    	
    	table.addCell(getAreaTable(KassenBuch.PURPOSE_AREA));
    	table.addCell(new Paragraph (" "));
    	
    	table.addCell(getAreaTable(KassenBuch.ECONOMIC_AREA));

    	
    	document.add(table);

	}
	
	private PdfPTable getAreaTable(int areaNumber) throws DocumentException {
		
		PdfPTable table = new PdfPTable(2);
    	
		table.setWidthPercentage(100.0f);
    	
		float[] widths = {50.0f, 50.0f}; 
    	
		table.setWidths(widths);
    	
    	String title;
    	
    	switch (areaNumber) {
    	
    	case KassenBuch.IDEALISTIC_AREA:
    		title = BookingAreasTableModel.idealisticArea;
    		break;
    	case KassenBuch.PROPERTY_AREA:
    		title = BookingAreasTableModel.propertyArea;
    		break;
    	case KassenBuch.PURPOSE_AREA:
    		title = BookingAreasTableModel.purposeArea;
    		break;	
    	case KassenBuch.ECONOMIC_AREA: default:
    		title = BookingAreasTableModel.economicArea;
    		break;
    	
    	}
    	
    	
    	// table header
    	PdfPCell cell = new PdfPCell (new Paragraph (title));
    	cell.setColspan(2);
    	table.addCell(cell);
    	
    	cell = new PdfPCell(new Paragraph ("Einnahmen"));
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	table.addCell(cell);

    	cell = new PdfPCell(new Paragraph ("Ausgaben"));
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	table.addCell(cell);
    	
    	
    	List<BookingSubArea> subareasCredit = moneybook.getBookingSubAreas(areaNumber, false, year);
    	List<BookingSubArea> subareasDebit = moneybook.getBookingSubAreas(areaNumber, true, year);
    	
    	// bereiche
    	table.addCell(getSubAreasTable(subareasCredit));
    	table.addCell(getSubAreasTable(subareasDebit));
    	
    	// balance
    	double credit = moneybook.getBookingAreaCredit(areaNumber, year);
    	double debit = moneybook.getBookingAreaDebit(areaNumber, year);;
    	
    	// summen TODO aus KassenBuch
    	table.addCell(getSummeTable(credit, "Summe"));
    	table.addCell(getSummeTable(debit, "Summe"));
    	
    	// -Summe Ausgaben
    	table.addCell(getSummeTable(debit, "- Summe Ausgaben"));
    	cell = new PdfPCell(new Paragraph());
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	// Gewinn/Verlust
    	table.addCell(getSummeTable(credit + debit, "Gewinn/Verlust"));
    	cell = new PdfPCell(new Paragraph());
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	
    	return table;
		
	}
	
	private PdfPTable getSubAreasTable(List<BookingSubArea> subareas) throws DocumentException {
		
		PdfPTable table = new PdfPTable(2);
    	
		table.setWidthPercentage(50.0f);
    	
		float[] widths = {30.0f, 20.0f}; 
    	
		table.setWidths(widths);
    	
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    	
    	Iterator<BookingSubArea> it = subareas.iterator();
    	
    	PdfPCell cell;
    	BookingSubArea area;
    	
    	List<Project> projects = moneybook.getProjekteAsList(year);
    	
    	while (it.hasNext()) {
    		
    		area = it.next();
			
    		// spenden, vor den MB
    		if(area.getName().equals(KassenBuch.premiums)) {
    		
    			// zuerst Spenden nach projekten
    			cell = new PdfPCell(getProjectsTable(projects, true));
    			cell.setBorder(PdfPCell.NO_BORDER);
    			cell.setColspan(2);
    			table.addCell(cell);
    			
    			// MB danach
    			cell = new PdfPCell(new Paragraph (area.getName()));
    			cell.setBorder(PdfPCell.NO_BORDER);
    			table.addCell(cell);
    			
    			// balance
    			cell = new PdfPCell(new Paragraph (String.format("%.2f €", area.getBalance())));
    			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
    			cell.setBorder(PdfPCell.NO_BORDER);
    			table.addCell(cell);
    			
    		}
    		
    		else if(area.getName().equals(KassenBuch.donations)) {
    			// tue nix
    		}
    		
    		else if(area.getName().equals(KassenBuch.projectPayOut)) {
    			
    			// projektbezogene ausgaben nach projekten
    			cell = new PdfPCell(getProjectsTable(projects, false));
    			cell.setBorder(PdfPCell.NO_BORDER);
    			cell.setColspan(2);
    			table.addCell(cell);
    			
    		} 
    		else {
    			
    			// name
    			cell = new PdfPCell(new Paragraph (area.getName()));
    			cell.setBorder(PdfPCell.NO_BORDER);
    			table.addCell(cell);
    			
    			// balance
    			cell = new PdfPCell(new Paragraph (String.format("%.2f €", area.getBalance())));
    			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
    			cell.setBorder(PdfPCell.NO_BORDER);
    			table.addCell(cell);	
    		}
    		
    	}
		
		return table;
    	
	}
	
	private PdfPTable getProjectsTable(List<Project> projects, boolean credit) throws DocumentException {
		
		PdfPTable table = new PdfPTable(2);
    	table.setWidthPercentage(50.0f);
    	float[] widths = {30.0f, 20.0f}; 
    	table.setWidths(widths);
    	table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    	
    	Iterator<Project> it = projects.iterator();
    	
    	PdfPCell cell;
    	Project project;
    	
    	while (it.hasNext()) {
    	
    		project = it.next();
			
    		if (viewmode == SHOW_ROOT_PROJECTS) {
        		
    			if (project.getParent() == null) {
    				
    				// name
    				cell = new PdfPCell(new Paragraph (project.getName()));
    				cell.setBorder(PdfPCell.NO_BORDER);
    				table.addCell(cell);
    				
    				// balance
    				
    				double balance;
    				
    				if(credit) {
    					balance = project.getCreditTotal();
    				} else {
    					balance = project.getDebitTotal();
    				}
    				
    				cell = new PdfPCell(new Paragraph (String.format("%.2f €", balance)));
    				
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
    				
    				cell.setBorder(PdfPCell.NO_BORDER);
    				
    				table.addCell(cell);
    				
    			}
    		
    		}
    		
    		else if (viewmode == SHOW_PROJECTS_AND_SUBPROJECTS) { 
    			
    			// name
    			String name;
    			
    			if(project.getParent() == null) {
    				name = project.getName();
    			}
    			else if(project.getParent().getParent() == null) {
    				name = "  " + project.getName();
    			}
    			else {
    				name = "    " + project.getName();
    			}
    			
				cell = new PdfPCell(new Paragraph (name));
				cell.setBorder(PdfPCell.NO_BORDER);
				table.addCell(cell);
				
				// balance
				
				double balance;
				
				if(credit) {
					balance = project.getCredit();
				} else {
					balance = project.getDebit();
				}
				
				cell = new PdfPCell(new Paragraph (String.format("%.2f €", balance)));
				
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				
				cell.setBorder(PdfPCell.NO_BORDER);
				
				table.addCell(cell);
    		}	
			
    	}
    	
		return table;
    	
	}
	
	
	private PdfPTable getSummeTable(double balance, String name) throws DocumentException {
		
		PdfPTable table = new PdfPTable(2);
    	table.setWidthPercentage(50.0f);
    	float[] widths = {30.0f, 20.0f}; 
    	table.setWidths(widths);
    	
    	PdfPCell cell;
    	
    	// name
		cell = new PdfPCell(new Paragraph (name));
		cell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell);
		
		// balance
		cell = new PdfPCell(new Paragraph (String.format("%.2f €", balance)));
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);
		
    	return table;
    	
	}
		
}
