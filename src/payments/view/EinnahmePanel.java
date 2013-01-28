
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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import member.model.Donator;
import member.model.Member;
import moneybook.model.KassenBuch;
import moneybook.model.Payment;
import net.java.dev.designgridlayout.DesignGridLayout;
import payments.controller.EinnahmeVerbuchenListener;
import payments.controller.SearchPersonListener;
import payments.controller.VerbuchenFrameCloseListener;
import payments.model.KontoauszugTableModel;
import utils.dokuments.LengthDocument;
import association.model.AssociationDataTransfer;

/**
 * Repräsentiert GUI eines Einnahme-Panels
 * @author Artem Petrov
 *
 */
@SuppressWarnings("serial")
public class EinnahmePanel extends PaymentPanel {

	private String name = "Inhaber: ";
	private String kto = "Kontonummer: ";
	private String blz = "BLZ: ";
	private String betrag = "Betrag: ";
	private String date = "Datum: ";
	private String zweck = "Verwendungszweck: ";
	private String project = "Projekt: ";
	private String choice = "Bitte wählen: ";
	private String beitrag = "Beitrag";
	private String spende = "Spende";
	private String other = "Sonstige Einnahme";
	private String owner = "Mitglied/Spendernr.: ";
	private String member = "Mitglied";
	private String save = "Verbuchen";
	private String close = "Abbrechen";
	private String title = "Einnahme verbuchen";
	private String bookingArea = "Buchungsbereich: ";
	
	private JTextField nameLbl;
	private JTextField ktoLbl;
	private JTextField blzLbl;
	private JTextField betragLbl;
	private JTextField dateLbl;
	
	private JLabel zweckLbl;
	private JTextArea zweckTxt;
	private JLabel projectLbl;
	private JLabel bookingAreaLbl;
	private JLabel typLbl;
	private JLabel ownerLbl;
	
	private JTextField ownerFeld;
	
	private ButtonGroup typRadio;
	private JRadioButton beitragButton;
	private JRadioButton spendeButton;
	private JRadioButton otherButton;
	
	private JButton searchBtn;
	private JButton saveBtn;
	private JButton closeBtn;
	
	private JComboBox projectBox;
	private JComboBox bookingAreaBox;
	
	private JCheckBox donatorType;
	
	// Projekt "-"
	private Vector<String> projectBoxItems_1;
	private JComboBox projectBox_1;
	
	// Alle projekte
	private Vector<String> projectBoxItems_2;
	private JComboBox projectBox_2;
	
	// alle bereiche
	private Vector<String> bookingAreaBoxItems_1;
	private JComboBox bookingAreaBox_1;
	// alle, außer MB und Spenden
	private Vector<String> bookingAreaBoxItems_2;
	private JComboBox bookingAreaBox_2;
	
	public final static int MEMEBERSHIP_FEE = 0;
	public final static int DONATION = 1;
	public final static int OTHER = 2;
	
	public final static int MEMBER = 0;
	public final static int DONATOR = 1;
	
	private AssociationDataTransfer associationDataTransfer;
	private Payment payment;
	
	// TODO Das Panel soll die Person kennen, der eine Zahlung zugeordnet werden soll
	// Zurzeit kennt das Panel nur die Personnummer!!!
	// Funktioniert aber alles korrekt, bis auf die Anzeige von der Spendernummer,
	// die im folgenden Format angezeigt wird: 2XXXX, in der Mitgliederverwaltung wird aber folgendes Format
	// verwendet: 0XXXX
	
	/**
	 * Erzeugt ein Einnahme-Panel
	 * @param associationDataTransfer AssociationDataTransfer
	 * @param kassenbuch Kassenbuch
	 * @param z Zahlung (Einnahme)
	 * @param tableModel Tabellen-Modell des Kontoauszugsmoduls
	 */
	public EinnahmePanel(AssociationDataTransfer associationDataTransfer, KassenBuch kassenbuch, Payment z, KontoauszugTableModel tableModel) {
	
		super(associationDataTransfer, kassenbuch);
		
		this.associationDataTransfer = associationDataTransfer;
		this.payment = z;
		
		typLbl = new JLabel(choice);
		typRadio = new ButtonGroup();
		beitragButton = new JRadioButton(beitrag);
		spendeButton = new JRadioButton(spende);
		otherButton =  new JRadioButton(other);
		
		beitragButton.setOpaque(false);
		spendeButton.setOpaque(false);
		otherButton.setOpaque(false);
		
		projectLbl = new JLabel(project);
		
		projectBoxItems_1 = new Vector<String>();
		projectBoxItems_1.add("-");
		projectBox_1 = new JComboBox(projectBoxItems_1);
		
		projectBoxItems_2 = kassenbuch.getProjectNames(3, true);
		projectBoxItems_2.remove(0);
		projectBox_2 = new JComboBox(projectBoxItems_2);
		
		projectBox = new JComboBox(projectBoxItems_2);
		
		bookingAreaLbl = new JLabel(bookingArea);
		
		bookingAreaBoxItems_1 = kassenbuch.getBookingSubAreasNames(false);
		bookingAreaBoxItems_2 = kassenbuch.getBookingSubAreasNames(false);
		bookingAreaBoxItems_2.remove(0);
		bookingAreaBoxItems_2.remove(0);
		
		bookingAreaBox_1 = new JComboBox(bookingAreaBoxItems_1);
		bookingAreaBox_2 = new JComboBox(bookingAreaBoxItems_2);
		
		bookingAreaBox = new JComboBox(bookingAreaBoxItems_1);
		
		typRadio.add(beitragButton);
		typRadio.add(spendeButton);
		typRadio.add(otherButton);
		
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
		
		
		ownerLbl = new JLabel(owner);
		
		ownerFeld = new JTextField();
		ownerFeld.setEditable(false);
		
		searchBtn = new JButton("Suchen...");
		
		searchBtn.addActionListener (new SearchPersonListener());
		
		donatorType = new JCheckBox(member);
		donatorType.setOpaque(false);
		
		saveBtn = new JButton(save);
		saveBtn.addActionListener(new EinnahmeVerbuchenListener(kassenbuch, z, tableModel));
		closeBtn = new JButton(close);
		closeBtn.addActionListener(new VerbuchenFrameCloseListener());
		
		beitragButton.setSelected(true);
		setPremium();
		
		beitragButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				setPremium();

			}
			
		});
		
		spendeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				setDonation();
				
			}
			
		});
		
		otherButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				setOther();
				
			}
			
		});
		
		this.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), title));
		
		DesignGridLayout lay = new DesignGridLayout(this);
		
		
		lay.row().center().add(typLbl).add(beitragButton).add(spendeButton).add(otherButton);
		lay.row().center().add(new JLabel(" "));
		
		lay.row().grid(new JLabel(name)).add(nameLbl).grid(new JLabel(date)).add(dateLbl);
		lay.row().grid(new JLabel(kto)).add(ktoLbl).grid(new JLabel(blz)).add(blzLbl);
		lay.row().grid(new JLabel(betrag)).add(betragLbl);

		lay.row().grid(zweckLbl).add(zweckTxt);
		
		lay.row().center().add(new JLabel(" "));
		
		lay.row().grid().add(projectLbl).add(projectBox).add();
		lay.row().grid().add(bookingAreaLbl).add(bookingAreaBox).add();
		
		lay.row().center().add(ownerLbl).add(ownerFeld).add(donatorType).add(searchBtn);
		lay.row().center().add(saveBtn).add(closeBtn);
		
	}
	

	/**
	 * Liefert den Projektname zurück
	 * @return Projektname
	 */
	public String getProject() {

		return (String) projectBox.getSelectedItem();
		
	}
	
	/**
	 * Liefert die Mitglieds/Spendernummer zurück
	 * @return Mitglieds/Spendernummer
	 */
	public int getMemberNumber() {
		
		try {
		
			return Integer.parseInt(ownerFeld.getText());
		
		} catch (NumberFormatException e) {
		
			return -1;
			
		}
		
	}
	
	@Override
	public boolean setPersonNumber(int number) {
		
		ownerFeld.setText(String.format("%05d", number));
		
		return true;
	
	}
	
	
	/**
	 * Liefert den Typ der Einnahme zurück (Beitrag/Spende/sonstige Einnahme)
	 * @return Typ der Einnahme
	 */
	public int getMemberTyp() {
		
		if(beitragButton.isSelected()) {
			return EinnahmePanel.MEMEBERSHIP_FEE;
		}
		else if(spendeButton.isSelected()) {
			return EinnahmePanel.DONATION;
		}
		else {
			return EinnahmePanel.OTHER;
		}
		
	}
	
	private void setPremium() {
		
		// MemberNr
		Member m = associationDataTransfer.getMember(payment.getKTO(), payment.getBLZ());
		
		if(m == null) {
			ownerFeld.setText("unbekannt");
		}
		else {
			ownerFeld.setText(String.format("%05d", m.getNumber()));
		}
		
		
		donatorType.setSelected(true);
		donatorType.setEnabled(false);
		projectBox.setModel(projectBox_1.getModel());
		bookingAreaBox.setModel(bookingAreaBox_1.getModel());
		projectBox.setEnabled(false);
		bookingAreaBox.setSelectedIndex(0);
		bookingAreaBox.setEnabled(false);
		searchBtn.setEnabled(true);
	}
	
	private void setDonation() {
		// Spenden
		// MemberNr
		Donator d = associationDataTransfer.getDonator(payment.getKTO(), payment.getBLZ());
		Member m = null;
		
		if (d == null) {
			// MemberNr
			m = associationDataTransfer.getMember(payment.getKTO(), payment.getBLZ());
				
		}
		
		if(d == null && m == null) {
			ownerFeld.setText("unbekannt");
		}
		else if (d != null) {
			
			ownerFeld.setText(String.format("%05d", d.getNumber()));
		}
		else if (m != null) {
			
			ownerFeld.setText(String.format("%05d", m.getNumber()));
		}
		
		
		donatorType.setSelected(true);
		donatorType.setEnabled(true);
		projectBox.setModel(projectBox_2.getModel());
		bookingAreaBox.setModel(bookingAreaBox_1.getModel());
		projectBox.setEnabled(true);
		bookingAreaBox.setSelectedIndex(1);
		bookingAreaBox.setEnabled(false);
		searchBtn.setEnabled(true);
	}
	
	private void setOther() {
		// Sonstige
		donatorType.setEnabled(false);
		searchBtn.setEnabled(false);
		ownerFeld.setText("-");
		projectBox.setModel(projectBox_1.getModel());
		bookingAreaBox.setModel(bookingAreaBox_2.getModel());
		projectBox.setEnabled(false);
		bookingAreaBox.setEnabled(true);
	}

	/**
	 * Liefert den Name des Buchungsunterbereichs zurück
	 * @return Name des Buchungsunterbereichs
	 */
	public String getBookingArea() {
		return (String) bookingAreaBox.getSelectedItem();
	}


	// welche Liste soll angezeigt werden: Donators oder Members
	public int getDonatorType () {
		
		if (this.donatorType.isSelected()) {
			return PaymentPanel.TYPE_MEMBER;
		}
		else {
			return PaymentPanel.TYPE_DONATOR;
		}

	}

	
}
