
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
package finstatement.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import moneybook.controller.MoneyBookFrameDisposeListener;
import moneybook.model.KassenBuch;
import net.java.dev.designgridlayout.DesignGridLayout;
import association.model.AssociationDataTransfer;
import association.view.BackgroundPanel;
import association.view.ButtonDesign;

import com.toedter.calendar.JYearChooser;

import finstatement.controller.BalancePrintListener;
import finstatement.controller.CreateBookingSubAreaListener;
import finstatement.model.BookingAreasTableModel;

/**
 * GUI des Jahresabschlussmoduls
 * 
 * @author Artem Petrov
 *
 */
@SuppressWarnings("serial")
public class BookingAreasPanel extends BackgroundPanel implements Observer {

	private String idealisticArea = "Ideeller Bereich (Steuerfrei)";
	private String propertyArea = "Vermögensverwaltung";
	private String purposeArea = "Zweckbetrieb";
	private String economicArea = "Wirtschaftlicher Geschäftsbetrieb";
	
	private String title = "Jahresabschluss";
	private String newTitle =  "Buchungsbereich erstellen";
	private String area = "Unterbereich von";
	private String name = "Name";
	private String credit = "Einnahmen";
	private String debit = "Ausgaben";
	private String year = "Jahr";
	private String update = "Aktualisieren";
	private String mainProjects = "Nur Hauptprojekte";
	private String allProjects = "Alle";
	
	private JTable areaTable;
	
	private JScrollPane tablePane;
	private JPanel newSubAreaPanel;
	
	private ButtonGroup viewRadio;
	private JRadioButton mainProjectsButton;
	private JRadioButton allProjectsButton;
	
	private ButtonGroup typRadio;
	private JRadioButton debitButton;
	private JRadioButton creditButton;
	
	private JButton createBtn;
	private JButton updateBtn;
	private JButton printBtn;
	private JButton mainMenuBtn;
	
	private JTextField nameField;
	private JLabel nameLbl;
	private JLabel yearLbl;
	private JLabel areaLbl;
	private JComboBox areaBox;
	
	private JYearChooser yearChooser;
	
	private KassenBuch kassenbuch;
	private AssociationDataTransfer associationDataTransfer;
	
	/**
	 * Erstellt ein BookingAreasPanel
	 * 
	 * @param associationDataTransfer Datenmodell  der Mitgliederverwaltung
	 * @param kassenbuch Datenmodell des Kassenbuchmoduls
	 */
	public BookingAreasPanel(AssociationDataTransfer associationDataTransfer, KassenBuch kassenbuch) {
		
		this.kassenbuch = kassenbuch;
		
		this.associationDataTransfer = associationDataTransfer;
		
		kassenbuch.addObserver(this);
		
		this.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), title));
		
		DesignGridLayout thisLayout = new DesignGridLayout(this);
		
		yearChooser = new JYearChooser();
		yearChooser.setMaximum(new GregorianCalendar().get(Calendar.YEAR));
		yearChooser.setMinimum(kassenbuch.getStartYear());
		
		viewRadio = new ButtonGroup();
		mainProjectsButton = new JRadioButton(mainProjects);
		allProjectsButton = new JRadioButton(allProjects);
		viewRadio.add(mainProjectsButton);
		viewRadio.add(allProjectsButton);
		mainProjectsButton.setSelected(true);
		
		mainProjectsButton.setOpaque(false);
		allProjectsButton.setOpaque(false);
		
		areaTable = new JTable(new BookingAreasTableModel(kassenbuch, this.getViewType(), this.getYear()));
		
		tablePane = new JScrollPane(areaTable);
		
		tablePane.setPreferredSize(new Dimension(600, 200));
		
		nameLbl = new JLabel(name);
		
		nameField = new JTextField();
		
		yearLbl = new JLabel(year);
		
		updateBtn = new JButton(update);
		
		updateBtn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				areaTable.setModel(new BookingAreasTableModel(getKassenBuch(), getViewType(), getYear()));
				
			}
			
		});
		
		areaLbl = new JLabel(area);
		
		areaBox = new JComboBox(new Object[] {idealisticArea, propertyArea, purposeArea, economicArea});
		
		typRadio = new ButtonGroup();
		debitButton = new JRadioButton(debit);
		creditButton = new JRadioButton(credit);
		typRadio.add(debitButton);
		typRadio.add(creditButton);
		creditButton.setSelected(true);
		
		debitButton.setOpaque(false);
		creditButton.setOpaque(false);
		
		createBtn = new ButtonDesign("images/save_icon.png");
		createBtn.addActionListener(new CreateBookingSubAreaListener(kassenbuch, this));
		
		newSubAreaPanel = new JPanel();
		newSubAreaPanel.setBorder(new TitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY ), newTitle));
		newSubAreaPanel.setOpaque(false);
		
		printBtn = new ButtonDesign("images/create_pdf_icon.png");
		printBtn.addActionListener(new BalancePrintListener());
		
		mainMenuBtn = new ButtonDesign("images/mainmenu_icon.png");
		mainMenuBtn.addActionListener(new MoneyBookFrameDisposeListener());
		
		DesignGridLayout bottLayout = new DesignGridLayout(newSubAreaPanel);
		bottLayout.row().grid(nameLbl).add(nameField);
		bottLayout.row().grid(areaLbl).add(areaBox).grid().add(creditButton).add(debitButton);
		bottLayout.row().center().add(createBtn);
		
		thisLayout.row().grid().add(yearLbl).add(yearChooser).add(mainProjectsButton).add(allProjectsButton).add(updateBtn);
		thisLayout.row().grid().add(tablePane);
		thisLayout.row().center().add(printBtn);
		thisLayout.row().grid().add(newSubAreaPanel);
		
		thisLayout.row().center().add(mainMenuBtn);

	}

	@Override
	/**
	 * Aktualisiert das Panel
	 */
	public void update(Observable arg0, Object arg1) {
		
		areaTable.setModel(new BookingAreasTableModel(kassenbuch, getViewType(), getYear()));
		
	}

	/**
	 * Liefert den Name des neuen Unterbereichs zurück
	 * 
	 * @return Name des neuen Unterbereichs
	 */
	public String getSubAreaName() {
		
		return nameField.getText().trim();
		
	}
	
	/**
	 * Liefert die Nummer des übergeordneten Bereichs zurück
	 * 
	 * @return Nummer des übergeordneten Bereichs zurück
	 */
	public int getArea() {
		
		return areaBox.getSelectedIndex();
		
	}
	
	/**
	 * Liefert den Typ des neuen Unterbereichs zurück
	 * 
	 * @return Typ des neuen Unterbereichs
	 */
	public boolean isDebit() {
		
		return debitButton.isSelected();
	
	}
	
	/**
	 * Liefert das Jahr des Jahresabschlusses zurück
	 * 
	 * @return Jahr des Jahresabschlusses
	 */
	public int getYear() {

		return yearChooser.getYear();
	
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
	 * 
	 * @return AssociationDataTransfer
	 */
	public AssociationDataTransfer getAssociationDataTransfer() {
		
		return this.associationDataTransfer;

	}

	/**
	 * Liefert das Anzeigemodus zurück
	 * 
	 * @return Anzeigemodus
	 */
	public int getViewType() {
		
		if(this.allProjectsButton.isSelected()) {
		
			return BookingAreasTableModel.SHOW_PROJECTS_AND_SUBPROJECTS;
		
		}
		else {
		
			return BookingAreasTableModel.SHOW_ROOT_PROJECTS;
		
		}
		
		
	}
		
}
