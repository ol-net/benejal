
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
package moneybook.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import moneybook.controller.MoneyBookFrameDisposeListener;
import moneybook.controller.UebersichtPrintListener;
import moneybook.controller.UebersichtShowListener;
import moneybook.model.KassenBuch;
import moneybook.model.UebersichtTableModel;
import net.java.dev.designgridlayout.DesignGridLayout;
import association.model.AssociationDataTransfer;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

import com.toedter.calendar.JDateChooser;


@SuppressWarnings("serial")
/**
 * GUI des Kassenbuchübersichtsmoduls
 * 
 * @author Artem Petrov
 *
 */
public class KassenBuchPanel extends BackgroundPanel implements Observer {
	
	private String title = "Kassenbuchübersicht";
	private String saldo = "Aktueller Kontostand: ";
	private String show = "Aktualisieren";
	private String von = "Startdatum(TT.MM.JJJJ)";
	private String bis = "Enddatum(TT.MM.JJJJ)";
	
	private KassenBuch kassenbuch;
	
	private JTable uerbesichtTable;
	
	private JScrollPane tablePane;
	
	private JLabel saldoLabel;
	private JLabel vonLabel;
	private JLabel bisLabel;
	
	private JDateChooser startDate;
	private JDateChooser finishDate;
	
	private JButton printBtn;
	private JButton showBtn;
	private JButton mainMenuBtn;
	
	private AssociationDataTransfer associationDataTransfer;
	
	/**
	 * Erstellt ein KassenBuchPanel
	 * 
	 * @param associationDataTransfer Datenmodell  der Mitgliederverwaltung
	 * @param kassenbuch Datenmodell des Kassenbuchmoduls
	 */
	public KassenBuchPanel(AssociationDataTransfer associationDataTransfer, KassenBuch kassenbuch) {
		
		this.associationDataTransfer = associationDataTransfer;
		this.kassenbuch = kassenbuch;
		
		kassenbuch.addObserver(this);
		
		this.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), title));
		
		DesignGridLayout thisLayout = new DesignGridLayout(this);
		
		uerbesichtTable = new JTable(new UebersichtTableModel(kassenbuch.getZahlungen(15)));
		
		uerbesichtTable.getColumnModel().getColumn(0).setPreferredWidth(70);
		uerbesichtTable.getColumnModel().getColumn(1).setPreferredWidth(400);
		uerbesichtTable.getTableHeader().setReorderingAllowed(false);
		
		tablePane = new JScrollPane(uerbesichtTable);
		
		tablePane.setPreferredSize(new Dimension(600, 200));
		
		printBtn = new ButtonDesign("images/create_pdf_icon.png");
		printBtn.addActionListener(new UebersichtPrintListener());
		showBtn = new JButton(show); // new ButtonDesign("images/reload_icon.png");//
		showBtn.addActionListener(new UebersichtShowListener());
		
		mainMenuBtn = new ButtonDesign("images/mainmenu_icon.png");
		mainMenuBtn.addActionListener(new MoneyBookFrameDisposeListener());
		
		saldoLabel = new JLabel(String.format(this.saldo + "        %.2f€", kassenbuch.getSaldo()));
		
		vonLabel = new JLabel(von);
		bisLabel = new JLabel(bis);
		
		startDate = new JDateChooser();
		finishDate = new JDateChooser();
		
		thisLayout.row().grid().add(vonLabel).add(startDate).add(bisLabel).add(finishDate).add(showBtn);
		thisLayout.row().grid().add(tablePane);
		thisLayout.row().right().add(saldoLabel);
		thisLayout.row().center().add(mainMenuBtn).add(printBtn);
		
	}
	
	/**
	 * Liefert das Kassenbuch zurück
	 * 
	 * @return Kassenbuch
	 */
	public KassenBuch getKassenBuch() {
		
		return this.kassenbuch;
	
	}
	
	/**
	 * Liefert das AssociationDataTransfer zurück
	 */
	public AssociationDataTransfer getAssociationDataTransfer() {
		return this.associationDataTransfer;
	}
	
	/**
	 * Liefert Startdatum der Übersichtsperiode zurück
	 * @return Startdatum der Übersichtsperiode
	 * @throws NullPointerException falls kein Datum eingegeben wurde
	 */
	public Date getStartDate() throws NullPointerException {
		
		return startDate.getDate();
		
	}
	
	/**
	 * Liefert Enddatum der Übersichtsperiode zurück
	 * @return Enddatum der Übersichtsperiode
	 * @throws NullPointerException falls kein Datum eingegeben wurde
	 */
	public Date getEndDate() throws NullPointerException {

		return finishDate.getDate();
		
	}
	
	/**
	 * Liefert das Tabellen-Modell zurück
	 * @return Tabellen-Modell
	 */
	public UebersichtTableModel getUebersichtTableModel(){
		
		return (UebersichtTableModel) uerbesichtTable.getModel();
		
	}
	
	/**
	 * Setzt Tabellen-Modell
	 * @param m Tabellen-Modell
	 */
	public void setModel(UebersichtTableModel m) {
		
		uerbesichtTable.setModel(m);
		uerbesichtTable.getColumnModel().getColumn(0).setPreferredWidth(70);
		uerbesichtTable.getColumnModel().getColumn(1).setPreferredWidth(400);
		
	}
	
	/**
	 * Aktualisiert das Panel
	 */
	public void update(Observable o, Object arg) {
		
		try {
			
			setModel(new UebersichtTableModel(kassenbuch.getZahlungen(getStartDate(), getEndDate())));

		} catch (NullPointerException e1) {

			setModel(new UebersichtTableModel(kassenbuch.getZahlungen(15)));

		}

		saldoLabel.setText(String.format(this.saldo + "%.2f€", kassenbuch.getSaldo()));
		
	}

	/**
	 * Liefert den Saldo des Vereins zurück
	 * @return Saldo des Vereins
	 */
	public double getSaldo() {
		
		return kassenbuch.getSaldo();
	
	}
	
	/**
	 * Liefert den Namen des Vereins zurück
	 */
	public String getName() {
		
		return associationDataTransfer.getAssociation().getName();
	
	}

}
