
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
package moneybook.model;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import association.model.InitContribution;

import projects.model.Project;

import finstatement.model.BookingSubArea;

import utils.database.DBManager2;
import utils.database.DerbyManager2;

/**
 * Bildet eine Schnittstelle zwischen der Datenhaltungsschicht und der 
 * Anwendungsschicht der Software.
 * 
 * @author Artem Petrov
 *
 */
public class KassenBuch extends Observable {
	
	private static KassenBuch moneybook_instance; 
	
	// Unterbereiche
	public static final String premiums = "Mitgliedsbeiträge";
	public static final String donations = "Spenden";
	public static final String projectPayOut = "Projektbezogene Ausgaben";
	public static final String grantsFunding = "Zuschüsse/Fördermittel";
	public static final String gifts = "Schenkungen/Erbschaften";
	public static final String replacements = "Ersatzleistungen (z.B. Arbeitsstunden)";
	public static final String otherPayIn = "Sonstige Einnahmen";
	public static final String administrativeCosts = "Verwaltungskosten";
	public static final String insurance = "Versicherungen";
	public static final String combinedContributions = "Verbandsbeiträge";
	public static final String expenses = "Auslagen/Aufwandsenschädigungen";
	public static final String dividends = "Zinsen/Dividende";
	public static final String accountManagementFees = "Kontoführungsgebühren";
	
	public static final int IDEALISTIC_AREA = 0;
	public static final int PROPERTY_AREA = 1;
	public static final int PURPOSE_AREA = 2;
	public static final int ECONOMIC_AREA = 3;
	private String url;
	
	public KassenBuch(String url) {
		this.url = url;
		createTables();
	}
	
	public synchronized static KassenBuch getInstance(String url){
		if(moneybook_instance == null){
			moneybook_instance = new KassenBuch(url);
		}
		return moneybook_instance;
	}
	
	// projektbezogene Ausgabe
	/**
	 * Speichert eine projektbezogene Ausgabe
	 * @return true, falls erfolgreich gespeichert
	 */
	public boolean saveProjectPayOut(PayingOut p) {
		
		DBManager2 dbm = new DerbyManager2(url);
		
		if(dbm.saveProjectPayOut(p)) {
			setChanged();  
			notifyObservers();
			return true;
		}
		else {
			return false;
		}
	}
	
	// sonst. ausgabe und sonstige einnahme
	/**
	 * Speichert eine nicht projektbezogene Ausgabe oder Einnahme
	 * @return true, falls erfolgreich gespeichert
	 */
	public boolean savePayment(Payment p) {
		
		DBManager2 dbm = new DerbyManager2(url);
		
		if(dbm.savePayment(p)) {
			setChanged();  
			notifyObservers();
			return true;
		}
		else {
			return false;
		}
	}
	
	

	/**
	 * Speichert einen Mitgliedsbeitrag
	 * @return true, falls erfolgreich gespeichert
	 */
	public boolean savePremium(Beitrag premium) {
		
		DBManager2 dbm = new DerbyManager2(url);

		if(dbm.savePremium(premium)) {
			InitContribution init = new InitContribution(premium.getMitglied());
			init.initContribution();
			setChanged();  
			notifyObservers();
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Speichert eine Spende
	 * @return true, falls erfolgreich gespeichert
	 */
	public boolean saveDonation(Spende donation) {
		
		DBManager2 dbm = new DerbyManager2(url);
		
		if(dbm.saveDonation(donation)) {
			setChanged();  
			notifyObservers();
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * Prüft, ob eine Zahlung in der DB vorhanden ist
	 * @param p Zahlung
	 * @return true, falls die zahlund in der DB vorhanden ist
	 */
	public boolean isPaymentRecorded(Payment p) {
		
		DBManager2 dbm = new DerbyManager2(url);
		
		return dbm.isPaymentSaved(p);

	}
	
	/**
	 * Liefert eine Liste von Mitgliedsbeiträgen und Spenden
	 * @param number Mitgliedsnummer
	 * @return Liste von Mitgliedsbeiträgen und Spenden
	 */
	public List<Beitrag> getMemberPremiums(int number) {
		
		DBManager2 dbm = new DerbyManager2(url);
		
		return dbm.getPremiums(number);
	
	}
	
	/**
	 * Liefert eine Liste von Spenden
	 * @param number Spendernummer
	 * @return Liste von Spenden
	 */
	public List<Spende> getDonations(int number) {
	
		DBManager2 dbm = new DerbyManager2(url);
	
		return dbm.getDonations(number);
	
	}

	
	/**
	 * Liefert eine Liste mit Projektnamen zurück, die projekte sind hierarchisch geordnet
	 * 
	 * @param level Projekttiefe
	 * @param active true, falls in der Liste nur aktive Projekte enthalten sein sollen 
	 * @return Liste mit Projektnamen
	 */
	public Vector<String> getProjectNames(int level, boolean active) {
	
		Vector<String> v = new Vector<String>();
		
		v.add("-");
		
		List<Project> l = getProjekteAsList();
		
		Iterator<Project> it = l.listIterator(0);
		while(it.hasNext()) {
			Project p = it.next();
			if(p.getParent() == null) {
				if(active) {
					if(p.getFinish() == null) {
						v.add(p.getName());
					}
				}
				else {
					v.add(p.getName());
				}
			}
			else if(p.getParent().getParent() == null) {
				if(active) {
					if(p.getFinish() == null) {
						v.add("  " + p.getName());
					}
				}
				else {
					v.add("  " + p.getName());
				}
				
			}
			else {
				if (level == 3) {
					if(active) {
						if(p.getFinish() == null) {
							v.add("    " + p.getName());
						}
					}
					else {
						v.add("    " + p.getName());
					}
				}
					
			}
		}
		
		return v;
		
	}
	
	/**
	 * Liefert eine Liste mit allen Projekten, die hiearhisch geordnet sind, zurück.
	 * 
	 * @return Liste mit allen Projekten
	 */
	public List<Project> getProjekteAsList() {
		
		DBManager2 dbm = new DerbyManager2(url);
		
		List<Project> l = dbm.getProjects();
		LinkedList<Project> projekte = new LinkedList<Project>();
		
		Iterator<Project> it = l.listIterator(0);
		while(it.hasNext()) {
			Project p = it.next();
			p.setCredit(dbm.getProjectCredit(p));
			p.setDebit(dbm.getProjectDebit(p));
			projekte.add(p);
			// 2.Level
			Iterator<Project> it2 = p.getChild().listIterator(0);
			while(it2.hasNext()) {
				Project p2 = it2.next();
				p2.setCredit(dbm.getProjectCredit(p2));
				p2.setDebit(dbm.getProjectDebit(p2));
				projekte.add(p2);
				
				// 3.Level
				Iterator<Project> it3 = p2.getChild().listIterator(0);
				while(it3.hasNext()) {
				
					Project p3 = it3.next();
					p3.setCredit(dbm.getProjectCredit(p3));
					p3.setDebit(dbm.getProjectDebit(p3));
					projekte.add(p3);
				
				}
			}
		}
		
		return projekte;
		
	}
	
	/**
	 * Liefert eine Liste mit allen Projekten, die hiearhisch geordnet sind, zurück.
	 * @param year Dem Projekt werden nur Einnahmen und Ausgaben des Jahres zugeordnet.
	 * @return Liste mit allen Projekten
	 */
	public List<Project> getProjekteAsList(int year) {
		
		Calendar cal = new GregorianCalendar(year - 1, Calendar.DECEMBER, 31);
		Date finish = cal.getTime();
		cal = new GregorianCalendar(year + 1, Calendar.JANUARY, 1);
//		Date start = cal.getTime();
		
		DBManager2 dbm = new DerbyManager2(url);
		
		List<Project> l = dbm.getProjects();
		LinkedList<Project> projekte = new LinkedList<Project>();
		
		Iterator<Project> it = l.listIterator(0);
		while(it.hasNext()) {
			
			Project p = it.next();
			// TODO 
			if (true) {//p.getStart().before(start)) {
				
				// Projekt läuft
				if(p.getFinish() == null) {
					
					p.setCredit(dbm.getProjectCredit(p, year));
					p.setDebit(dbm.getProjectDebit(p, year));
//					if (p.getCredit() != 0.0 || p.getDebit() != 0.0) {
						projekte.add(p);
//					}
					
				// Projekt wurde erst in der aktuellen Periode beendet	
				} else if (p.getFinish().after(finish)){
					
					p.setCredit(dbm.getProjectCredit(p, year));
					p.setDebit(dbm.getProjectDebit(p, year));
					
					
//					if (p.getCredit() != 0.0 || p.getDebit() != 0.0) {
						projekte.add(p);
//					}
				
				}
			
			}
			// 2.Level
			Iterator<Project> it2 = p.getChild().listIterator(0);
		
			while(it2.hasNext()) {
				
				Project p2 = it2.next();
				
				if (true) {//p.getStart().before(start)) {
				
					if(p2.getFinish() == null) {
						
						p2.setCredit(dbm.getProjectCredit(p2, year));
						p2.setDebit(dbm.getProjectDebit(p2, year));
						projekte.add(p2);
						  
					} else if (p2.getFinish().after(finish)){
						
						p2.setCredit(dbm.getProjectCredit(p2, year));
						p2.setDebit(dbm.getProjectDebit(p2, year));
						projekte.add(p2);
					
					}
				
				}
				// 3.Level
				Iterator<Project> it3 = p2.getChild().listIterator(0);
				while(it3.hasNext()) {
				
					Project p3 = it3.next();
					
					if (true) {//p.getStart().before(start)) {
					
						if(p3.getFinish() == null) {
							
							p3.setCredit(dbm.getProjectCredit(p3, year));
							p3.setDebit(dbm.getProjectDebit(p3, year));
							projekte.add(p3);
							  
						} else if (p3.getFinish().after(finish)){
							
							p3.setCredit(dbm.getProjectCredit(p3, year));
							p3.setDebit(dbm.getProjectDebit(p3, year));
							projekte.add(p3);
						
						}
						
					}
				
				}
			}
		}
		
		return projekte;
		
	}
	
	/**
	 * Liefert eine Liste mit den Namen der Buchungsunterbereiche zurück.
	 * @param debit Typ (Haben/Soll)
	 * @return Liste mit den Namen der Buchungsunterbereiche
	 */
	public Vector<String> getBookingSubAreasNames(boolean debit) {
		
		Vector<String> names = new Vector<String>();
		
		for(int i = 0; i < 4; i++) {
			List<BookingSubArea> subAreas = getBookingSubAreas(i, debit, 0);
			Iterator<BookingSubArea> it = subAreas.iterator();
			
			while(it.hasNext()) {
				names.add(it.next().getName());
			}
		}
		
		return names;
		
	}

	
	/**
	 * Beendet ein Projekt
	 * @param p Projekt
	 * @return true, falls erfolgreich beendet
	 */
	public boolean finishProjekt(Project p) {
		DBManager2 dbm = new DerbyManager2(url);
		
		if(p.getName().equals("Allgemein"))
			return false;
		
		if(dbm.finishProject(p)) {
			setChanged();  
			notifyObservers();
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * Löscht ein Projekt
	 * @param p Projekt
	 * @return true, falls erfolgreich beendet
	 */
	public boolean deleteProjekt(Project p) {
		
		DBManager2 dbm = new DerbyManager2(url);
		
		if(p.getName().equals("Allgemein"))
			return false;
		
		if(dbm.deleteProject(p)) {
			setChanged();  
			notifyObservers();
			return true;
		}
		else {
			return false;
		}
		
	}

	/**
	 * Liefert die Anzahl von Buchungsunterbereichen zurück
	 * @return Anzahl von Buchungsunterbereichen
	 */
	public int getBookingSubAreasCount() {
		DBManager2 dbm = new DerbyManager2(url);
		return dbm.getBookingSubAreasCount();
	}

	/**
	 * Liefert eine Liste mit den Buchungsunterbereichen zurück
	 * @param area Übergeordneter Bereich
	 * @param debit Typ
	 * @param year Jahr
	 * @return Liste mit den Buchungsunterbereichen
	 */
	public List<BookingSubArea> getBookingSubAreas(int area, boolean debit, int year) {
		DBManager2 dbm = new DerbyManager2(url);
		return dbm.getBookingSubAreas(area, debit, year);
	}

	/**
	 * Speichert ein Unterbereich
	 * @param bookingSubArea Unterbereich
	 * @return true, falls erfolgreich gespeichert
	 */
	public boolean addBookingSubArea(BookingSubArea bookingSubArea) {
		DBManager2 dbm = new DerbyManager2(url);
		if(dbm.addBookingSubArea(bookingSubArea)) {
			setChanged();  
			notifyObservers();
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * Liefert die Summe aller Einnahmen eines Buchungsbereichs zurück
	 * @param area Buchungsbereich
	 * @param year Jahr
	 * @return Summe aller Einnahmen
	 */
	public double getBookingAreaCredit(int area, int year) {
		
		DBManager2 dbm = new DerbyManager2(url);
		
		return dbm.getBookingAreaCredit(area, year);
	
	}
	
	/**
	 * Liefert die Summe aller Ausgaben eines Buchungsbereichs zurück
	 * @param area Buchungsbereich
	 * @param year Jahr
	 * @return Summe aller Ausgaben
	 */
	public double getBookingAreaDebit(int area, int year) {
		
		DBManager2 dbm = new DerbyManager2(url);
		
		return dbm.getBookingAreaDebit(area, year);
	
	}

	/**
	 * Speichert ein Projekt
	 * @param project Projekt
	 * @return true, falls erfolgreich gespeichert
	 */
	public boolean saveProjekt(Project project) {

		DBManager2 dbm = new DerbyManager2(url);
		
		if(dbm.saveProject(project)) {
			setChanged();  
			notifyObservers();
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Liefert eine Liste der Länge i, in der i letzten Zahlungen enthalten sind, zurück
	 * @param i Länge der Liste
	 * @return liste mit den Zahlungen
	 */
	public List<Payment> getZahlungen(int i) {
		
		DBManager2 dbm = new DerbyManager2(url);
	
		return dbm.getPayments(i);
	
	}
	
	/**
	 * Liefert eine Liste, in der Zahlungen enthalten sind, die in der 
	 * angegebenen Periode verbucht wurden, zurück
	 * @param startDate Startdatum der Periode
	 * @param endDate Enddatum der Periode
	 * @return liste mit den Zahlungen
	 */
	public List<Payment> getZahlungen(Date startDate, Date endDate) {
	
		DBManager2 dbm = new DerbyManager2(url);
		
		return dbm.getPayments(startDate, endDate);
	
	}

	

	/**
	 * Endsaldo des aktuellen Jahres
	 * 
	 * @return Endsaldo des aktuellen Jahres
	 */
	public double getSaldo() {
	
		DBManager2 dbm = new DerbyManager2(url);
		
		return dbm.getStartBalance() + dbm.getBalance();
	
	}
	
	/**
	 * Endsaldo des Jahres year
	 * 
	 * @param year
	 * @return Endsaldo des Jahres year
	 */
	public double getSaldo(int year) {
		
		DBManager2 dbm = new DerbyManager2(url);
		
		return dbm.getStartBalance() + dbm.getBalance(year);
	
	}
	
	/**
	 * Setzt Startsaldo des Vereins
	 * @param balance Startsaldo 
	 */
	public void setStartBalance(double balance) {
		
		DBManager2 dbm = new DerbyManager2(url);
		
		Calendar cal = Calendar.getInstance();
		
		dbm.setStartBalance(balance, cal.get(Calendar.YEAR) - 1);
		
	
	}
	
	/**
	 * Liefert das Jahr, in dem die Software in Betrieb genommen wurde
	 * @return Jahr der Inbetriebsnahme der Software
	 */
	public int  getStartYear() {
		
		DBManager2 dbm = new DerbyManager2(url);
		
		return dbm.getStartYear();
	
	}
	
	/**
	 * Aktualisiert die Nummer eines Mitglieds, wird bei Spendertransformation aufgerufen
	 * 
	 * @param oldnr Spendernummer
	 * @param newnr Mitgliedsnummer
	 * @return true, falls alles ok
	 */
	public boolean updateMemberNr(int oldnr, int newnr) {
		
		DBManager2 dbm = new DerbyManager2(url);
		
		return dbm.updateMemberNr(oldnr, newnr);
		
	}
	
	/**
	 * Erstellt DB-Tabellen
	 */
	private void createTables() {
		
		try {
			
			(new DerbyManager2(url)).createTables();
			fillBookingAreas();
			fillProjects();
		    
			
		} catch (SQLException e) {
			
			//System.out.println("DB vorhanden!");
//			e.printStackTrace();
		
		}
		
	}
	
	
	/**
	 * Füllt Buchungsunterbereichstabelle der DB
	 */
	private void fillBookingAreas() {
		
		BookingSubArea subarea;
		DBManager2 dbm = new DerbyManager2(url);
		
		// Einnahmen Ideeller Bereich:
		subarea = new BookingSubArea(premiums, 0, 0.0, false);
		dbm.addBookingSubArea(subarea);
		
		subarea = new BookingSubArea(donations, 0, 0.0, false);
		dbm.addBookingSubArea(subarea);
		
		subarea = new BookingSubArea(grantsFunding, 0, 0.0, false);
		dbm.addBookingSubArea(subarea);
		
		subarea = new BookingSubArea(gifts, 0, 0.0, false);
		dbm.addBookingSubArea(subarea);
		
		subarea = new BookingSubArea(replacements, 0, 0.0, false);
		dbm.addBookingSubArea(subarea);
		
		subarea = new BookingSubArea(otherPayIn, 0, 0.0, false);
		dbm.addBookingSubArea(subarea);
		
		// Ausgaben Ideeller Bereich:
		subarea = new BookingSubArea(projectPayOut, 0, 0.0, true);
		dbm.addBookingSubArea(subarea);
		
		subarea = new BookingSubArea(administrativeCosts, 0, 0.0, true);
		dbm.addBookingSubArea(subarea);
		
		subarea = new BookingSubArea(insurance, 0, 0.0, true);
		dbm.addBookingSubArea(subarea);
		
		subarea = new BookingSubArea(combinedContributions, 0, 0.0, true);
		dbm.addBookingSubArea(subarea);
		
		subarea = new BookingSubArea(expenses, 0, 0.0, true);
		dbm.addBookingSubArea(subarea);
		
		// Einnahmen Vermögensverwaltung
		subarea = new BookingSubArea(dividends, 1, 0.0, false);
		dbm.addBookingSubArea(subarea);
		
		// Ausgaben Vermögensverwaltung
		subarea = new BookingSubArea(accountManagementFees, 1, 0.0, true);
		dbm.addBookingSubArea(subarea);
		
	}
	
	/**
	 * Füllt Projekte-Tabelle der DB
	 */
	private void fillProjects() {
		
		DBManager2 dbm = new DerbyManager2(url);
		Project project = new Project("Allgemein", null);
		dbm.saveProject(project);
		
	}
	
}
