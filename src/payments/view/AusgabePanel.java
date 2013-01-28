
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import moneybook.model.KassenBuch;
import moneybook.model.Payment;
import net.java.dev.designgridlayout.DesignGridLayout;
import payments.controller.AusgabeVerbuchenListener;
import payments.controller.VerbuchenFrameCloseListener;
import payments.model.KontoauszugTableModel;
import utils.dokuments.LengthDocument;
import association.model.AssociationDataTransfer;

/**
 * Repräsentiert GUI eines Ausgabe-Panels
 * @author Artem Petrov
 * TODO Lastschriftrückgabe - Person wählen
 *
 */
@SuppressWarnings("serial")
public class AusgabePanel extends PaymentPanel {
	
	public static final int FOR_PROJECT = 0;
	public static final int NOT_FOR_PROJECT = 1;
	public static final int RETURN = 2;
	
	private String name = "Inhaber: ";
	private String kto = "Kontonummer: ";
	private String blz = "BLZ: ";
	private String betrag = "Betrag: ";
	private String date = "Datum: ";
	private String zweck = "Verwendungszweck: ";
	private String project = "Projekt:";
	private String save = "Verbuchen";
	private String close = "Abbrechen";
	private String title = "Ausgabe verbuchen";
	private String forProject = "Projektbezogene Ausgabe";
	private String other = "Sonstige Ausgabe";
	private String feeReturn = "Lastschriftrückgabe";
	private String choice = "Bitte wählen: ";
	private String bookingArea = "Buchungsbereich: ";
	
	private JTextField nameLbl;
	private JTextField ktoLbl;
	private JTextField blzLbl;
	private JTextField betragLbl;
	private JTextField dateLbl;
	
	private JLabel zweckLbl;
	private JTextArea zweckTxt;
	private JLabel projectLbl;
	private JLabel typLbl;
	private JLabel bookingAreaLbl;
	
	private JButton saveBtn;
	private JButton closeBtn;
	
	private ButtonGroup typRadio;
	private JRadioButton forProjectButton;
	private JRadioButton otherButton;
	private JRadioButton feeReturnButton;
	
	private JComboBox projectBox;
	private JComboBox bookingAreaBox;
	
	// Projekt "-"
	private Vector<String> projectBoxItems_1;
	private JComboBox projectBox_1;
	
	// Alle projekte
	private Vector<String> projectBoxItems_2;
	private JComboBox projectBox_2;
	
	// alle bereiche
	private Vector<String> bookingAreaBoxItems_1;
	private JComboBox bookingAreaBox_1;
	// alle, außer projektbez. Ausgaben
	private Vector<String> bookingAreaBoxItems_2;
	private JComboBox bookingAreaBox_2;
	
	// MB/Spenden
	private Vector<String> bookingAreaBoxItems_3;
	private JComboBox bookingAreaBox_3;
	
	/**
	 * Erzeugt ein Ausgabe-Panel
	 * @param kassenbuch Kassenbuch
	 * @param z Zahlung (Ausgabe)
	 * @param tableModel Tabellen-Modell des Kontoauszugsmoduls
	 */
	public AusgabePanel(AssociationDataTransfer associationDataTransfer, KassenBuch kassenbuch, Payment z, KontoauszugTableModel tableModel) {
		
		super(associationDataTransfer, kassenbuch);
		
		typLbl = new JLabel(choice);
		
//		nameLbl = new JLabel(name + z.getInhaber());
//		ktoLbl = new JLabel(kto + z.getKTO());
//		blzLbl = new JLabel(blz + z.getBLZ());
//		betragLbl = new JLabel(betrag + String.format("%.2f €", z.getBetrag()));
//		dateLbl = new JLabel(date + z.getDatum());
		
		nameLbl = new JTextField(z.getInhaber());
		ktoLbl = new JTextField(z.getKTO() + "");
		blzLbl = new JTextField(z.getBLZ() + "");
		betragLbl = new JTextField(String.format("%.2f €", z.getBetrag()));
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		dateLbl = new JTextField(df.format(z.getDatum()));
		
		zweckLbl = new JLabel(zweck);
		this.zweckTxt = new JTextArea(new LengthDocument(378));
		this.zweckTxt.setLineWrap(true);
		this.zweckTxt.setWrapStyleWord(true);
		this.zweckTxt.setRows(6);
		this.zweckTxt.setText(z.getZweck());
		this.zweckTxt.setEditable(false);
		
		projectLbl = new JLabel(project);
		
		projectBoxItems_1 = new Vector<String>();
		projectBoxItems_1.add("-");
		projectBox_1 = new JComboBox(projectBoxItems_1);
		
		projectBoxItems_2 = kassenbuch.getProjectNames(3, true);
		projectBoxItems_2.remove(0);
		projectBox_2 = new JComboBox(projectBoxItems_2);
		
		projectBox = new JComboBox(projectBoxItems_2);
		
		
		saveBtn = new JButton(save);
		saveBtn.addActionListener(new AusgabeVerbuchenListener(associationDataTransfer, kassenbuch, z, tableModel));
		closeBtn = new JButton(close);
		closeBtn.addActionListener(new VerbuchenFrameCloseListener());
		
		typRadio = new ButtonGroup();
		forProjectButton = new JRadioButton(forProject);
		otherButton = new JRadioButton(other);
		feeReturnButton = new JRadioButton(feeReturn);
		
		forProjectButton.setOpaque(false);
		otherButton.setOpaque(false);
		feeReturnButton.setOpaque(false);
		
		typRadio.add(forProjectButton);
		typRadio.add(otherButton);
		typRadio.add(feeReturnButton);
		
		bookingAreaLbl = new JLabel(bookingArea);
		
		bookingAreaBoxItems_1 = kassenbuch.getBookingSubAreasNames(true);
		bookingAreaBoxItems_2 = kassenbuch.getBookingSubAreasNames(true);
		bookingAreaBoxItems_2.remove(0);
		bookingAreaBoxItems_2.remove(0);
		bookingAreaBoxItems_3 = kassenbuch.getBookingSubAreasNames(false);
		
		bookingAreaBox_1 = new JComboBox(bookingAreaBoxItems_1);
		bookingAreaBox_2 = new JComboBox(bookingAreaBoxItems_2);
		bookingAreaBox_3 = new JComboBox(bookingAreaBoxItems_3);
		
		bookingAreaBox = new JComboBox(bookingAreaBoxItems_1);
		
		forProjectButton.setSelected(true);
		setForProject();
		
		forProjectButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				setForProject();
				
			}
			
		});
		
		otherButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				setOther();
				
			}
			
		});
		
		feeReturnButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				setFeeReturn();
				
			}
			
		});
		
		this.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), title));
		
		DesignGridLayout lay = new DesignGridLayout(this);
		
		lay.row().center().add(typLbl).add(forProjectButton).add(otherButton).add(feeReturnButton);
		lay.row().center().add(new JLabel(" "));
		
		lay.row().grid(new JLabel(name)).add(nameLbl).grid(new JLabel(date)).add(dateLbl);
		lay.row().grid(new JLabel(kto)).add(ktoLbl).grid(new JLabel(blz)).add(blzLbl);
		lay.row().grid(new JLabel(betrag)).add(betragLbl);

		lay.row().grid(zweckLbl).add(zweckTxt);
		lay.row().center().add(new JLabel(" "));
		
		lay.row().grid().add(projectLbl).add(projectBox);
		lay.row().grid().add(bookingAreaLbl).add(bookingAreaBox);
		lay.row().center().add(saveBtn).add(closeBtn);
		
	}
	
	/**
	 * Liefert den Projektname zurück
	 * @return Projektname
	 */
	public String getProject() {

		return (String) projectBox.getSelectedItem();
	}
	
	@Override
	public boolean setPersonNumber(int number) {
		return true;
	}
	
	@Override
	public int getDonatorType() {
		return -1;
	}


	/**
	 * Liefert den Name des Buchungsunterbereichs zurück
	 * @return Name des Buchungsunterbereichs
	 */
	public String getBookingArea() {
		
		return (String) bookingAreaBox.getSelectedItem();
	
	}

	/**
	 * Liefert den Ausgabetyp zurück
	 * @return Ausgabetyp
	 */
	public int getPaymentTyp() {

		if(forProjectButton.isSelected())
			return AusgabePanel.FOR_PROJECT;
		if(otherButton.isSelected())
			return AusgabePanel.NOT_FOR_PROJECT;
		else
			return AusgabePanel.RETURN;
	}
	
	// Combo-Box Logik
	private void setForProject() {
		
		projectBox.setModel(projectBox_2.getModel());
		bookingAreaBox.setModel(bookingAreaBox_1.getModel());
		projectBox.setEnabled(true);
		bookingAreaBox.setSelectedIndex(0);
		bookingAreaBox.setEnabled(false);

	}
	
	// Combo-Box Logik
	private void setOther() {
	
		projectBox.setModel(projectBox_1.getModel());
		bookingAreaBox.setModel(bookingAreaBox_2.getModel());
		projectBox.setEnabled(false);
		projectBox.setSelectedIndex(0);
		bookingAreaBox.setEnabled(true);
	
	}

	// Combo-Box Logik
	private void setFeeReturn() {

		projectBox.setModel(projectBox_1.getModel());
		bookingAreaBox.setModel(bookingAreaBox_3.getModel());
		bookingAreaBox.setSelectedIndex(0);
		projectBox.setEnabled(false);
		bookingAreaBox.setEnabled(false);
	
	}

	

}
