
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import projects.model.Project;

import finstatement.model.BookingSubArea;

import moneybook.model.Beitrag;
import moneybook.model.PayingOut;
import moneybook.model.Payment;
import moneybook.model.Spende;

//JAVA-Doc s. KassenBuch.java
public class DerbyManager2 implements DBManager2 {

	
	private Connection connection;
	private Properties properties;
	private String url;
	private Statement statement;
	private ResultSet resultSet;
	private String query;
	
	public DerbyManager2(String url) {
		
		this.properties = new Properties();
		this.url = url;
		
		properties.put("user", "user1");
		properties.put("password", "passwd");
		
		
	}
	
	/**
	 * RUNONCE bei Programmkonfiguration!!!
	 * @throws SQLException
	 */
	public void createTables() throws SQLException {
	  
		connection = DriverManager.getConnection(url, properties);
		statement = connection.createStatement();
		
		// payments
		query = "CREATE TABLE payments " +
				"(id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1)," +
				"date DATE not null," +
				"booking_date DATE not null," +
				"kto BIGINT not null," +
				"blz INT not null," +
				"amount DOUBLE not null," +
				"owner VARCHAR(64) not null," +
				"purpose VARCHAR(512) not null," +
				"subarea_id INT not null)";		
		
		statement.execute(query);
		
		// premiums
		query = "CREATE TABLE premiums " +
		"(payment_id INT NOT NULL," +
		"member_id INT NOT NULL)";
		
		statement.execute(query);
		
		// donations
		query = "CREATE TABLE donations " +
		"(payment_id INT NOT NULL," +
		"donator_id INT NOT NULL)";
		
		statement.execute(query);
		
		// projects
		query = "CREATE TABLE projects " +
		"(id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1)," +
		"name VARCHAR(64) not null," +
		"parent_id INTEGER not null," +
		"start DATE not null," +
		"finish DATE)";

		statement.execute(query);
		
		// payments_to_projects
		query = "CREATE TABLE payments_to_projects " +
		"(payment_id INTEGER not null," +
		"project_id INTEGER not null)";
		
		statement.execute(query);
		
		// subareas
		query = "CREATE TABLE subareas " +
		"(id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1)," +
		"name VARCHAR(64) not null," +
		"area INTEGER not null," +
		"isdebit SMALLINT not null)";
		
		statement.execute(query);
		
		// startbalance
		query = "CREATE TABLE balance " +
		"(date INTEGER not null," +
		"balance DOUBLE not null)";
		
		statement.execute(query);
		
		statement.close();
		connection.close();

	}
	
	private void closeConnection() {
	
		try {
			
			connection.close();
		
		} catch (SQLException e) {
		
			e.printStackTrace();
		
		}
		
	}
	
	/*********PAYMENTS****************************************************************************************/
	
	public boolean isPaymentSaved(Payment payment) {
		
		try {
			
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
		
			//Prüfen, ob diese Zahlung in der DB vorhanden ist
			query = "SELECT COUNT(*) FROM payments WHERE " +
					"date ='" + new java.sql.Date(payment.getDatum().getTime())+ "'" +
					" AND kto = " 	+ 	 payment.getKTO() + 
					" AND blz = " 	+ 	 payment.getBLZ() + 
					" AND amount = " + 	 payment.getBetrag() + 
					" AND purpose = '" + payment.getZweck() + "'";
			
			resultSet = statement.executeQuery(query);
			
			resultSet.next();
			if(resultSet.getInt(1) != 0) {
				resultSet.close();
				statement.close();
				return true;
		  	}
			else {
			
				return false;
				
			}
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			return true;
		
		} finally {
			
			closeConnection();
			
		}
		
	}
	 
	public boolean savePayment(Payment payment) {
		
		try {
			
			// später weg!!!
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
			
			query = "ALTER TABLE payments " +
					"ALTER purpose SET DATA TYPE VARCHAR(512)";
			
			statement.executeUpdate(query);
			statement.close();
			//---
			
			if(isPaymentSaved(payment)) {
				return false;
			}
			
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
			
			query = "INSERT INTO payments (date, booking_date, kto, blz, amount, owner, purpose, subarea_id) VALUES (" +
					"'" + new java.sql.Date(payment.getDatum().getTime()) + "'," +
					"'" + new java.sql.Date(payment.getBookingDate().getTime()) + "'," +
					payment.getKTO() + "," +
					payment.getBLZ() + "," +
					payment.getBetrag() + "," +
					"'" + payment.getInhaber() +"'," +
					"'" + payment.getZweck() +"'," +
					"(SELECT id FROM subareas WHERE name = '" + payment.getBookingArea() + "'))"; 
					
			
			statement.executeUpdate(query);
			statement.close();
			
			return true;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			return false;
		
		} finally {
			
			closeConnection();
		
		}
		
	}
	
	public boolean savePremium(Beitrag premium) {
		
		try {
			
			if(!savePayment(premium)) {
				return false;
		  	}
			else {
				
				Connection connection = DriverManager.getConnection(url, properties);
				statement = connection.createStatement();
			
				query = "INSERT INTO premiums (payment_id, member_id) VALUES (" +
				"(SELECT MAX(id) FROM payments)," +
				premium.getMitglied() + ")";
				
				statement.executeUpdate(query);
				statement.close();
		
				return true;
	
			}
						
		} catch (SQLException e) {
		
			e.printStackTrace();
			return false;

		} finally {
			
			closeConnection();
			
		}

	}
	
	
	public boolean saveDonation(Spende donation) {
		
		try {
			
			if(!savePayment(donation)) {
				return false;
		  	}
			else {
				
				Connection connection = DriverManager.getConnection(url, properties);
				statement = connection.createStatement();
			
				query = "INSERT INTO donations (payment_id, donator_id) VALUES (" +
				"(SELECT MAX(id) FROM payments)," +
				donation.getSpender() + ")";
				
				statement.executeUpdate(query);
				
				query = "INSERT INTO payments_to_projects (payment_id, project_id) VALUES (" +
				"(SELECT MAX(id) FROM payments)," +
				"(SELECT id FROM projects WHERE name = '" + donation.getProjekt() + "'))";
				
				statement.executeUpdate(query);
				
				statement.close();
		
				return true;
	
			}
						
		} catch (SQLException e) {
		
			e.printStackTrace();
			return false;

		} finally {
			
			closeConnection();
			
		}

	}
	
	public boolean saveProjectPayOut(PayingOut payout) {
		
		try {
			
			if(!savePayment(payout)) {
				return false;
		  	}
			else {
				
				Connection connection = DriverManager.getConnection(url, properties);
				statement = connection.createStatement();
				
				query = "INSERT INTO payments_to_projects (payment_id, project_id) VALUES (" +
				"(SELECT MAX(id) FROM payments)," +
				"(SELECT id FROM projects WHERE name = '" + payout.getProjekt() + "'))";
				
				statement.executeUpdate(query);
				
				statement.close();
		
				return true;
	
			}
						
		} catch (SQLException e) {
		
			e.printStackTrace();
			return false;

		} finally {
			
			closeConnection();
			
		}

	}
	
	// TODO einzelheiten zu zahlungen, nach Typ
	// TODO Areaname
	// TODO bookDay - wenn nötig
	public List<Payment> getPayments(Date start, Date finish) {
		
		List<Payment> payments = new LinkedList<Payment>();
		
		try {
			
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
			
			query = "SELECT * FROM payments " +
					"WHERE date <=  '" + new java.sql.Date(finish.getTime()) +"' " +
					"AND date >= '" + new java.sql.Date(start.getTime()) +"' " +
					"ORDER BY payments.date DESC";
				
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				
				payments.add(new Payment(rs.getDate("date"), rs.getLong("kto"), 
						rs.getLong("blz"), rs.getDouble("amount"), rs.getString("owner"), 
						rs.getString("purpose"), "TODO AREANEME"));
			
			}
			
			statement.close();
	
		} catch (SQLException e) {
	
			e.printStackTrace();
		
		} finally {
			
			closeConnection();
			
		}
		
		return payments;
		
	}
	
	// TODO einzelheiten zu zahlungen, nach Typ
	// TODO Areaname
	public List<Payment> getPayments(int length) {
		
		List<Payment> payments = new LinkedList<Payment>();
		
		try {
			
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			
			query = "SELECT * FROM payments ORDER BY payments.date DESC";
				
			ResultSet rs = statement.executeQuery(query);
			
			rs.last();
			rs.relative(-length);
			
			while(rs.next()) {
				
				payments.add(new Payment(rs.getDate("date"), rs.getLong("kto"), 
						rs.getLong("blz"), rs.getDouble("amount"), rs.getString("owner"), 
						rs.getString("purpose"), "TODO AREANEME"));
			
			}
			
			statement.close();
	
		} catch (SQLException e) {
	
			e.printStackTrace();
		
		} finally {
			
			closeConnection();
			
		}
		
		return payments;
		
	}
	
	// TODO wenn nötig - area
	// für M-Verwaltung
	public List<Beitrag> getPremiums(int number) {
		
		List<Beitrag> premiums = new LinkedList<Beitrag>();
		
		try {
			
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
			
			query = "SELECT * FROM payments JOIN premiums ON payments.id = premiums.payment_id WHERE " +
					"member_id = " + number + " ORDER BY payments.date DESC";
				
			ResultSet rs = statement.executeQuery(query);
			
			Beitrag premium;
			
			while(rs.next()) {
				
				premium = new Beitrag(rs.getDate("date"), rs.getLong("kto"), 
						rs.getLong("blz"), rs.getDouble("amount"), rs.getString("owner"), 
						rs.getString("purpose"), "TODO AREANEME", rs.getInt("member_id"));
				
				premium.setBookingDate(rs.getDate("booking_date"));
				
				premiums.add(premium);
			
			}
			
			statement.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			closeConnection();
			
		}
		
		return premiums;
		
	}
	
	// TODO wenn nötig - area
	// für M-Verwaltung
	public List<Spende> getDonations(int number) {
		
		List<Spende> donations = new LinkedList<Spende>();
		
		try {
			
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
			
			query = "SELECT * FROM payments JOIN donations ON payments.id = donations.payment_id " +
					"JOIN payments_to_projects ON payments.id = payments_to_projects.payment_id " +
					"JOIN projects ON payments_to_projects.project_id = projects.id " +
					"WHERE " +
					"donator_id = " + number +  " ORDER BY payments.date DESC";
				
			ResultSet rs = statement.executeQuery(query);
			
			Spende donation;
			
			while(rs.next()) {
				
				donation = new Spende(rs.getDate("date"), rs.getLong("kto"), 
						rs.getLong("blz"), rs.getDouble("amount"), rs.getString("owner"), 
						rs.getString("purpose"), "TODO AREANEME", rs.getInt("donator_id"), rs.getString("name"));
				
				donation.setBookingDate(rs.getDate("booking_date"));
				
				donations.add(donation);
			}
			
			statement.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			closeConnection();
			
		}
		
		return donations;
		
	}

	
	
	/*********PROJECTS****************************************************************************************/
	
	private boolean isProjectSaved(Project p) {
		
		try {
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
		
			query = "SELECT COUNT(*) FROM projects WHERE name ='"+p.getName()+"'";
			//Prüfen, ob ein Projekt mit diesem Namen vorhanden ist
			resultSet = statement.executeQuery(query);
			
			resultSet.next();
			if(resultSet.getInt(1) != 0) {
				resultSet.close();
				statement.close();
				return true;
		  	}
			
			resultSet.close();
			
			return false;
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			
			return true;
	
		} finally {
			
			closeConnection();
			
		}

	}
	
	
	public boolean saveProject(Project p) {
		
		try {
			
			String parentID = "-1";
			
			Project parent = p.getParent();
			
			//Prüfen, ob ein Projekt mit diesem Namen vorhanden ist
			if (isProjectSaved(p)) {
				return false;
			}
			
			if (parent != null){
				//Prüfen, ob ein Oberprojekt vorhanden ist
				if (!isProjectSaved(parent)) {
					return false;
				}
				
				parentID = "(SELECT id FROM projects WHERE name = '" + parent.getName() + "')";
			}
			
			connection = DriverManager.getConnection(url, properties);
			
			statement = connection.createStatement();
					
			query = "INSERT INTO projects (name, parent_id, start, finish) VALUES (" +
					"'" + p.getName() + "'," +
					parentID + "," +
					"'" + new java.sql.Date(p.getStart().getTime()) + "'," +
					null + ")";
			
			statement.executeUpdate(query);
			
			statement.close();
			
			return true;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			
			return false;
	
		} finally {
			
			closeConnection();
			
		}
		
	}
	

	public boolean renameProject(Project p, String name) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public boolean restartProject(Project p, Date newStartDate) {
		
		try {
			connection = DriverManager.getConnection(url, properties);
		
			statement = connection.createStatement();
			
			query = "UPDATE projects SET start = '" + new java.sql.Date(newStartDate.getTime()) + "'" +
					"WHERE name = '" + p.getName() + "'";
				
			
			statement.executeUpdate(query);
			
			statement.close();
			
			return true;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			
			return false;
	
		} finally {
			
			closeConnection();
			
		}
		
	}
	
	
	public boolean finishProject(Project p) {
		
		try {
			connection = DriverManager.getConnection(url, properties);
		
			statement = connection.createStatement();
		
			Date finish = new Date();
			
			query = "UPDATE projects SET finish = '" + new java.sql.Date(finish.getTime()) + "'" +
					"WHERE name = '" + p.getName() + "'";
				
			
			statement.executeUpdate(query);
			
			statement.close();
			
			return true;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			
			return true;
	
		} finally {
			
			closeConnection();
			
		}
	}
	
	public boolean deleteProject(Project p) {
		
		try {
			connection = DriverManager.getConnection(url, properties);
		
			statement = connection.createStatement();
			
			query = "DELETE FROM projects " +
					"WHERE name = '" + p.getName() + "'";
				
			
			statement.executeUpdate(query);
			
			statement.close();
			
			return true;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			
			return true;
	
		} finally {
			
			closeConnection();
			
		}
	}


	
	/**
	 * Liefrt eine Liste mit den Projekten der 1. Ebene. Unterprojekte sind in der Liste nicht enthalten,
	 * sind jedoch ihren Vaterprojekten zugeordnet
	 * Haben/Soll der Projekte ist auf 0.0 gesetzt!
	 * @return Liste mit den Projekten der 1. Ebene
	 */
	public List<Project> getProjects() {
		
		List<Project> projects = getProjects(null);
		
		for (Project p : projects) {
			
			p.getChild().addAll(getProjects(p));
		
			for (Project child : p.getChild()) {
			
				child.getChild().addAll(getProjects(child));
			
			}
		
		}
		
		return projects;

	}
	
	
	private List<Project> getProjects(Project parent) {
		
		List<Project> projects = new LinkedList<Project>();
		
		String parentID = "-1";
		
		if(parent != null) {
			parentID = "(SELECT id FROM projects WHERE name = '" + parent.getName() + "')";
		}
		
		try {
			connection = DriverManager.getConnection(url, properties);
		
			statement = connection.createStatement();
			
			Date finish;
					
			query = "SELECT * FROM projects " +
					"WHERE parent_id = " + parentID;
			
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				
				if(resultSet.getDate("finish") == null) {
					finish = null;
				}
				else {
					finish = new Date(resultSet.getDate("finish").getTime());	
				}
				
				projects.add(new Project(resultSet.getString("name"), parent, new Date(resultSet.getDate("start").getTime()), finish));
				
			}
			
			statement.close();
			
			return projects;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			
			return null;
	
		} finally {
			
			closeConnection();
			
		}
		
	}
	
	public double getProjectCredit(Project project) {
		
		try {
			connection = DriverManager.getConnection(url, properties);
		
			statement = connection.createStatement();
			
			query = "SELECT SUM(amount) FROM payments JOIN payments_to_projects " +
					"ON payments.id = payments_to_projects.payment_id " +
					"WHERE payments.amount > 0 " +
					"AND payments_to_projects.project_id = " +
					"(SELECT id FROM projects WHERE name = '" + project.getName() + "')";
				
			
			resultSet = statement.executeQuery(query);
			
			resultSet.next();
			
			double credit = resultSet.getDouble(1);
			
			statement.close();
			
			return credit;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			
			return -1.0;
	
		} finally {
			
			closeConnection();
			
		}		
		
	}
	

	public double getProjectCredit(Project project, int year) {

		Calendar cal = new GregorianCalendar(year - 1, Calendar.DECEMBER, 31);
		Date start = new java.sql.Date(cal.getTime().getTime());
		cal = new GregorianCalendar(year + 1, Calendar.JANUARY, 1);
		Date end =  new java.sql.Date(cal.getTime().getTime());
		
		try {
			connection = DriverManager.getConnection(url, properties);
		
			statement = connection.createStatement();
			
			query = "SELECT SUM(amount) FROM payments JOIN payments_to_projects " +
					"ON payments.id = payments_to_projects.payment_id " +
					"WHERE payments.amount > 0 " +
					"AND payments_to_projects.project_id = " +
					"(SELECT id FROM projects WHERE name = '" + project.getName() + "') " +
					"AND payments.date > '" + start + "' " +
					"AND payments.date < '" + end + "'";
				
			
			resultSet = statement.executeQuery(query);
			
			resultSet.next();
			
			double credit = resultSet.getDouble(1);
			
			statement.close();
			
			return credit;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			
			return -1.0;
	
		} finally {
			
			closeConnection();
			
		}		
		
	}
	

	public double getProjectDebit(Project project) {
		
		try {
			connection = DriverManager.getConnection(url, properties);
		
			statement = connection.createStatement();
			
			query = "SELECT SUM(amount) FROM payments JOIN payments_to_projects " +
					"ON payments.id = payments_to_projects.payment_id " +
					"WHERE payments.amount < 0 " +
					"AND payments_to_projects.project_id = " +
					"(SELECT id FROM projects WHERE name = '" + project.getName() + "')";
				
			resultSet = statement.executeQuery(query);
			
			resultSet.next();
			
			double debit = resultSet.getDouble(1);
			
			statement.close();
			
			return debit;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			
			return 1.0;
	
		} finally {
			
			closeConnection();
			
		}		
		
	}
	
	
	public double getProjectDebit(Project project, int year) {
		
		Calendar cal = new GregorianCalendar(year - 1, Calendar.DECEMBER, 31);
		Date start = new java.sql.Date(cal.getTime().getTime());
		cal = new GregorianCalendar(year + 1, Calendar.JANUARY, 1);
		Date end =  new java.sql.Date(cal.getTime().getTime());
		
		try {
			connection = DriverManager.getConnection(url, properties);
		
			statement = connection.createStatement();
			
			query = "SELECT SUM(amount) FROM payments JOIN payments_to_projects " +
					"ON payments.id = payments_to_projects.payment_id " +
					"WHERE payments.amount < 0 " +
					"AND payments_to_projects.project_id = " +
					"(SELECT id FROM projects WHERE name = '" + project.getName() + "') " +
					"AND payments.date > '" + start + "' " +
					"AND payments.date < '" + end + "'";
				
			
			resultSet = statement.executeQuery(query);
			
			resultSet.next();
			
			double debit = resultSet.getDouble(1);
			
			statement.close();
			
			return debit;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			
			return 1.0;
	
		} finally {
			
			closeConnection();
			
		}		
	}
	
	/*********BOOKING AREAS***********************************************************************************/
	
	public boolean addBookingSubArea(BookingSubArea subArea) {
		
		try {
			connection = DriverManager.getConnection(url, properties);
		
			statement = connection.createStatement();

			query = "SELECT COUNT(*) FROM subareas WHERE name ='" + subArea.getName() + "'";
			
			resultSet = statement.executeQuery(query);
			
			resultSet.next();

			if(resultSet.getInt(1) != 0) {
				resultSet.close();
				statement.close();
				connection.close();
				return false;
		  	}
			
			resultSet.close();
			
			query = "INSERT INTO subareas (name, area, isdebit) " +
					"VALUES ('" + subArea.getName() + "', " +
							 subArea.getArea() + ", " + (subArea.isDebit() ? 1 : 0) + ")";
			
			statement.executeUpdate(query);
			
			statement.close();
			
			return true;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			return false;
		
		} finally {
			
			closeConnection();
			
		}
		
	}
	
	public List<BookingSubArea> getBookingSubAreas(int area, boolean debit, int year) {
		
		try {
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
			
		    LinkedList<BookingSubArea> subAreas = new LinkedList<BookingSubArea>();
		    
		    query = "SELECT * FROM subareas WHERE area = " + area + " AND isdebit = " + (debit ? 1 : 0);
		    
		    resultSet = statement.executeQuery(query); 
		    
		    
		    while(resultSet.next()) {
		    
		    	BookingSubArea subArea = new BookingSubArea(resultSet.getString("name"), 
		    												resultSet.getInt("area"), 0.0, 
		    												(resultSet.getInt("isdebit") == 1 ? true : false));	
		    	
		    	subAreas.add(subArea);
		    
		    }
		    
			resultSet.close();
			statement.close();
			
			for(BookingSubArea subArea : subAreas) {
				subArea.setBalance(getBookingSubAreaBalance(subArea, year));
			}
			
			return subAreas;
	
		} catch (SQLException e) {
		
			e.printStackTrace();
			return null;
	
		} finally {
			
			closeConnection();
			
		}
	}
	
	private double getBookingSubAreaBalance(BookingSubArea area, int year) {
		
		try {
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
			
			double balance = 0.0;
			
			Calendar cal = new GregorianCalendar(year - 1, Calendar.DECEMBER, 31);
			Date start = new java.sql.Date(cal.getTime().getTime());
			cal = new GregorianCalendar(year + 1, Calendar.JANUARY, 1);
			Date end =  new java.sql.Date(cal.getTime().getTime());
			
			query = "SELECT SUM(amount) FROM payments " +
					"WHERE subarea_id = " +
						"(SELECT id FROM subareas " +
						"WHERE name = '" + area.getName() + "') " +
					"AND payments.date > '" + start + "' " +
					"AND payments.date < '" + end + "'";
			
			resultSet = statement.executeQuery(query);
			
			resultSet.next();
			
		    balance = resultSet.getDouble(1);
		    
			resultSet.close();
			statement.close();
			
			return balance;
	
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return 0.0;
			
		} finally {
			
			closeConnection();
			
		}
		
	}
	
	public int getBookingSubAreasCount() {
		
		try {
			
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
			
			int counter = 0;
			
			
			query = "SELECT COUNT(*) FROM subareas";
			
			resultSet = statement.executeQuery(query);
			
			resultSet.next();
			
		    counter = resultSet.getInt(1);
		    
			resultSet.close();
			statement.close();
			
			return counter;
	
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return -1;
			
		} finally {
			
			closeConnection();
			
		}
		
	}
	
	public double getBookingAreaCredit(int area, int year) {

		try {
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
			
			double balance = 0.0;
			
			Calendar cal = new GregorianCalendar(year - 1, Calendar.DECEMBER, 31);
			Date start = new java.sql.Date(cal.getTime().getTime());
			cal = new GregorianCalendar(year + 1, Calendar.JANUARY, 1);
			Date end =  new java.sql.Date(cal.getTime().getTime());
			
			query = "SELECT SUM(amount) FROM payments JOIN subareas ON payments.subarea_id = subareas.id " +
					"WHERE subareas.area = " + area + " " +
					"AND payments.date > '" + start + "' " +
					"AND payments.date < '" + end + "' " +
					"AND payments.amount > 0";
			
			resultSet = statement.executeQuery(query);
			
			resultSet.next();
			
		    balance = resultSet.getDouble(1);
		    
			resultSet.close();
			statement.close();
			
			return balance;
	
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return 0.0;
			
		} finally {
			
			closeConnection();
			
		}
		
	}
	
	public double getBookingAreaDebit(int area, int year) {

		try {
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
			
			double balance = 0.0;
			
			Calendar cal = new GregorianCalendar(year - 1, Calendar.DECEMBER, 31);
			Date start = new java.sql.Date(cal.getTime().getTime());
			cal = new GregorianCalendar(year + 1, Calendar.JANUARY, 1);
			Date end =  new java.sql.Date(cal.getTime().getTime());
			
			query = "SELECT SUM(amount) FROM payments JOIN subareas ON payments.subarea_id = subareas.id " +
					"WHERE subareas.area = " + area + " " +
					"AND payments.date > '" + start + "' " +
					"AND payments.date < '" + end + "' " +
					"AND payments.amount < 0";
			
			resultSet = statement.executeQuery(query);
			
			resultSet.next();
			
		    balance = resultSet.getDouble(1);
		    
			resultSet.close();
			statement.close();
			
			return balance;
	
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return 0.0;
			
		} finally {
			
			closeConnection();
			
		}
		
	}
	
	/*********BALANCE***********************************************************************************/
	// TODO trigger
	public boolean setStartBalance(double balance, int startyear) {
		
		try {
			
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
		
			//Prüfen, startsaldo gesetzt ist
			query = "SELECT COUNT(*) FROM balance WHERE " +
					"date = " + startyear;
			
			resultSet = statement.executeQuery(query);
			
			resultSet.next();
			if(resultSet.getInt(1) != 0) {
				resultSet.close();
				statement.close();
				return false;
		  	}
			
			query = "INSERT INTO balance " +
					"VALUES (" + startyear + ", " + balance + ")";
			
			statement.executeUpdate(query);
			
			statement.close();
			
			return true;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			return false;
		
		} finally {
			
			closeConnection();
			
		}
		
	}
	
	public double getStartBalance() {
		
		try {
			
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
		
			
			query = "SELECT balance FROM balance";
			
			resultSet = statement.executeQuery(query);
			
			resultSet.next();
			
			double balance = resultSet.getDouble(1);
			
			statement.close();		
	
			return balance;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			return 0;
		
		} finally {
			
			closeConnection();
			
		}
		
	}
	
	/**
	 *  summe aller payments
	 */
	public double getBalance() {

		double balance = 0.0;
		
		try {
			
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
			
			query = "SELECT SUM(amount) FROM payments";
				
			ResultSet rs = statement.executeQuery(query);
			
			rs.next();
			
			balance = rs.getDouble(1);
			
			statement.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			closeConnection();
			
		}
		
		return balance;
		
	}
	
	/**
	 *  summe aller payments bis zum Jahr year inkl
	 */
	public double getBalance(int year) {
		
		double balance = 0.0;
	
		Calendar cal = new GregorianCalendar(year + 1, Calendar.JANUARY, 1);
		Date end =  new java.sql.Date(cal.getTime().getTime());
			
		try {
			
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
			
			query = "SELECT SUM(amount) FROM payments " +
					"WHERE date < '" + end + "'";
				
			ResultSet rs = statement.executeQuery(query);
			
			rs.next();
			
			balance = rs.getDouble(1);
			
			statement.close();
	
		} catch (SQLException e) {
		
			e.printStackTrace();
	
		} finally {
			
			closeConnection();
			
		}
		
		return balance;
		
	}
	
	public int getStartYear() {
		
		try {
			
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
		
			
			query = "SELECT date FROM balance";
			
			resultSet = statement.executeQuery(query);
			
			resultSet.next();
			
			int year = resultSet.getInt(1);
			
			statement.close();		
	
			return year;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			return -1;
		
		} finally {
			
			closeConnection();
			
		}
	}
	
	public boolean updateMemberNr(int oldnr, int newnr) {
		
		try {
			
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
		
			
			query = "UPDATE donations" +
					" SET donator_id = " + newnr +
					" WHERE donator_id = " + oldnr;
			
			@SuppressWarnings("unused")
			int tmp = statement.executeUpdate(query);
			
			statement.close();		
	
//			System.out.printf("%d Datensätze betroffen", tmp);
			return true;
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			return false;
		
		} finally {
			
			closeConnection();
			
		}
	}
	
//	public double getBalance(int startyear) {
//		
//		try {
//			
//			connection = DriverManager.getConnection(url, properties);
//			statement = connection.createStatement();
//		
//			
//			query = "SELECT balance FROM balance WHERE " +
//					"year = " + startyear;
//			
//			resultSet = statement.executeQuery(query);
//			
//			resultSet.next();
//			
//			double balance = resultSet.getDouble(1);
//			
//			statement.close();		
	
//			return balance;
//			
//			
//			
//		} catch (SQLException e) {
//		
//			e.printStackTrace();
//			return false;
//		
//		} finally {
//			
//			closeConnection();
//			
//		}
//		
//	}
	
	
	
	public static void main(String arg[]) throws SQLException {
	
		
		DerbyManager2 dbm = new DerbyManager2("jdbc:derby:.//vereinDB;create=true");
		dbm.createTables();
		System.out.println(dbm.setStartBalance(200, 2010));
	
		System.out.println(dbm.getStartBalance());
		
//		Payment p = new Payment(new Date(), 12345, 2222, 100.0, "bla", "bla", "testarea");
//		System.out.println(dbm.savePayment(p));
//		p = new Payment(new Date(), 12345, 2222, 1200.0, "bla", "bla", "testarea");
//		System.out.println(dbm.savePayment(p));
		
		
//		Beitrag prem = new Beitrag(new Date(), 12345, 2222, 3100.0, "Owner", "bla", "testarea", 1211);
//		System.out.println(dbm.savePremium(prem));
//		
//		Beitrag prem2 = new Beitrag(new Date(), 12345, 2222, 121300.0, "Owner", "bla", "testarea", 1212);
//		System.out.println(dbm.savePremium(prem2));
//		
//		for(Beitrag b : dbm.getPremiums(1212)) {
//			System.out.println(b.getBetrag());
//		}
//		
//		
//		System.out.println(dbm.getBalance());
//		System.out.println(dbm.getBalance(2010));
//		System.out.println(dbm.getBalance(2000));
//		
//		Projekt p = new Projekt("Projekt 1", null, new Date(), null);
//		System.out.println(dbm.saveProject(p));
//		Projekt p2  = new Projekt("Projekt 1-1", p, new Date(), null);
//		System.out.println(dbm.saveProject(p2));
//		Projekt pr  = new Projekt("Projekt 1-2", p, new Date(), null);
//		System.out.println(dbm.saveProject(pr, pr.getParent()));
//		
//		for(Projekt p3 : dbm.getProjects()) {
//			System.out.println(p3.getName());
//			for(Projekt p4 : p3.getChild()) {
//				System.out.println(p4.getName());
//			}
//		}
		
//		PayingOut po = new PayingOut(new Date(), 0, 0, -100, " ", " ", "testarea", p.getName());
//		PayingOut po2 = new PayingOut(new Date(), 0, 0, -200, " ", " ", "testarea", p.getName());
//		PayingOut po3 = new PayingOut(new Date(), 0, 0, -150, " ", " ", "testarea", p2.getName());
//		PayingOut po4 = new PayingOut(new Date(), 0, 0, 150, " ", " ", "testarea", p2.getName());
//		
//		Spende don = new Spende(new Date(), 0, 0, 800, "", "", "testarea", 111, p.getName());
//		
//		System.out.println(dbm.saveProjectPayOut(po));
//		System.out.println(dbm.saveProjectPayOut(po2));
//		System.out.println(dbm.saveProjectPayOut(po3));
//		System.out.println(dbm.saveProjectPayOut(po4));
//		System.out.println(dbm.saveDonation(don));
//		
//		System.out.println(dbm.getProjectCredit(p));
//		
//		BookingSubArea subArea = new BookingSubArea("area 1", 0, 0, false);
//		
//		System.out.println(dbm.addBookingSubArea(subArea));
//		
//		for (BookingSubArea sa : dbm.getBookingSubAreas(1, false, 2010))
//				System.out.println(sa.getBalance());	
//		

		
	}

	
	
}
