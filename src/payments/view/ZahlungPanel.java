
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
package payments.view;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import moneybook.controller.MoneyBookFrameDisposeListener;
import net.java.dev.designgridlayout.DesignGridLayout;
import payments.controller.SaveCreditOrderListener;
import utils.dokuments.BLZDocument;
import utils.dokuments.LengthDocument;
import association.model.AssociationDataTransfer;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

import com.toedter.calendar.JDateChooser;

/**
 * Repräsentiert die GUI des Überweisungsmoduls
 * @author Artem Petrov
 *
 */
@SuppressWarnings("serial")
public class ZahlungPanel extends BackgroundPanel {
	
	private String name = "Empfänger (Name, Vorname / Firma)";
	private String kto = "Kontonummer";
	private String blz = "Bankleitzahl";
	private String betrag = "Betrag (EUR,Ct.)";
	private String date = "Datum (TT.MM.JJJJ, optional)";
	private String zweck = "Verwendungszweck";
	private String title = "Überweisung tätigen";
	
	private JLabel nameLabel;
	private JLabel ktoLabel;
	private JLabel blzLabel;
	private JLabel betragLabel;
	private JLabel dateLabel;
	private JLabel zweckLabel;
	
	private JTextField nameFeld;
	private JTextField ktoFeld;
	private JTextField blzFeld;
	private JTextField betragFeld;
	private JDateChooser dateChooser;
	private JTextArea zweckText;
	
	private JButton saveBtn;
	private JButton mainMenuBtn;
	
	private AssociationDataTransfer associationDataTransfer;
	
	/**
	 * Erzeugt das Panel des Überweisungsmoduls
	 * @param associationDataTransfer AssociationDataTransfer
	 */
	public ZahlungPanel(AssociationDataTransfer associationDataTransfer) {
		
		this.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), title));
		
		this.associationDataTransfer = associationDataTransfer;
		this.nameLabel = new JLabel(name);
		this.ktoLabel = new JLabel(kto);
		this.blzLabel = new JLabel(blz);
		this.betragLabel = new JLabel(betrag);
		this.dateLabel = new JLabel(date);
		this.zweckLabel = new JLabel(zweck);
		
		this.nameFeld = new JTextField(new LengthDocument(27), "", 10);
		this.ktoFeld = new JTextField(new BLZDocument(10), "", 10);

		this.blzFeld = new JTextField(new BLZDocument(8), "", 10);
		NumberFormat f = NumberFormat.getNumberInstance();
		f.setGroupingUsed(false);
		this.betragFeld = new JFormattedTextField(f);
		this.dateChooser = new JDateChooser();
		// Min +1 Max +365
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
		this.dateChooser.setMinSelectableDate(cal.getTime());
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
		this.dateChooser.setMaxSelectableDate(cal.getTime());
		
		
		this.zweckText = new JTextArea(new LengthDocument(378));
		this.zweckText.setLineWrap(true);
		this.zweckText.setWrapStyleWord(true);
		this.zweckText.setRows(6);
		
		this.saveBtn = new ButtonDesign("images/DTAUS_file_icon.png");
		this.saveBtn.addActionListener(new SaveCreditOrderListener());
		
		mainMenuBtn = new ButtonDesign("images/mainmenu_icon.png");
		mainMenuBtn.addActionListener(new MoneyBookFrameDisposeListener());
		
		DesignGridLayout thisLayout = new DesignGridLayout(this);
		
		thisLayout.row().grid(nameLabel).add(nameFeld);
		thisLayout.row().grid(ktoLabel).add(ktoFeld).add();
		thisLayout.row().grid(blzLabel).add(blzFeld);
		thisLayout.row().grid(betragLabel).add(betragFeld);
		thisLayout.row().grid(dateLabel).add(dateChooser);
		thisLayout.row().grid(zweckLabel).add(zweckText);
		thisLayout.emptyRow();
		thisLayout.emptyRow();
		thisLayout.row().center().add(mainMenuBtn).add(saveBtn);
		
	}
	
	/**
	 * Liefert den Empfängername zurück
	 */
	public String getName() {
		return nameFeld.getText();
	}
	
	/**
	 * Liefert die BLZ zurück
	 */
	public String getBLZ() {
		return blzFeld.getText();
	}
	
	/**
	 * Liefert die Kontonummer zurück
	 */
	public String getKTO() {
		return ktoFeld.getText();
	}
	
	/**
	 * Liefert den Betrag zurück
	 */
	public String getAmount() {
		return betragFeld.getText();
	}
	
	/**
	 * Liefert das Ausführungsdatum zurück
	 */
	public Date getDate() {
		return dateChooser.getDate();
	}
	
	/**
	 * Liefert den Verwendungszweck zurück
	 */
	public String getPurpose() {
		return zweckText.getText();
	}

	/**
	 * Liefert das AssociationDataTransfer zurück
	 */
	public AssociationDataTransfer getAssociationDataTransfer() {
		return this.associationDataTransfer;
	}
	
	/**
	 * Leert die Eingabefelder
	 */
	public void clear() {
		
		String s = "";
		
		nameFeld.setText(s);
		ktoFeld.setText(s);
		blzFeld.setText(s);
		betragFeld.setText(s);
		zweckText.setText(s);
		dateChooser.cleanup();
		
	}

}
