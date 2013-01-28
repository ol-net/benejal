
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
package utils.database;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import projects.model.Project;

import finstatement.model.BookingSubArea;

import moneybook.model.Beitrag;
import moneybook.model.PayingOut;
import moneybook.model.Payment;
import moneybook.model.Spende;

// JAVA-Doc s. KassenBuch.java
public interface DBManager2 {

	/**
	 *  
	 */
	static final int IDEALISTIC_AREA = 0;
	static final int PROPERTY_AREA = 1;
	static final int PURPOSE_AREA = 2;
	static final int ECONOMIC_AREA = 3;
	
	
	public void createTables() throws SQLException;
	
	/*********PAYMENTS****************************************************************************************/

	
	public boolean isPaymentSaved(Payment payment);
	 
	public boolean savePayment(Payment payment);
	
	public boolean savePremium(Beitrag premium);
	
	public boolean saveDonation(Spende donation);
	
	public boolean saveProjectPayOut(PayingOut payout);
	
	public List<Payment> getPayments(Date start, Date finish);
	
	public List<Payment> getPayments(int length);
	
	public List<Beitrag> getPremiums(int number);
	
	public List<Spende> getDonations(int number);

	public double getBalance();
	
	public double getBalance(int year);
	
	public boolean setStartBalance(double balance, int startyear);
	
	public double getStartBalance();
	
	/*********PROJECTS****************************************************************************************/
	
	public boolean saveProject(Project p);
	
	public boolean renameProject(Project p, String name);
	
	public boolean restartProject(Project p, Date newStartDate);
	
	public boolean finishProject(Project p);
	
	public boolean deleteProject(Project p);

	/**
	 * Liefert eine Liste mit den Projekten der 1. Ebene (Projekte, die kein Vaterprojekt haben) zurück. 
	 * Unterprojekte sind in der Liste nicht enthalten,
	 * sind jedoch ihren Oberprojekten zugeordnet
	 * Haben/Soll der Projekte ist auf 0.0 gesetzt!
	 * 
	 * @return Liste mit den Projekten der 1. Ebene
	 */
	public List<Project> getProjects();
	
	public double getProjectCredit(Project project);

	public double getProjectCredit(Project project, int year);
	
	public double getProjectDebit(Project project);
	
	public double getProjectDebit(Project project, int year);
	
	/*********BOOKING AREAS***********************************************************************************/
	
	public boolean addBookingSubArea(BookingSubArea subArea);
	
	public List<BookingSubArea> getBookingSubAreas(int area, boolean debit, int year);
	
	public int getBookingSubAreasCount();
	
	public double getBookingAreaCredit(int area, int year);
	
	public double getBookingAreaDebit(int area, int year);
	
	public int  getStartYear();
	
	public boolean updateMemberNr(int oldnr, int newnr);
	
}

