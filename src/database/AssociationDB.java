
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
package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import member.model.Person;

import association.model.Account;
import association.model.Adress;
import association.model.Association;
import association.model.Contribution;
import association.model.Group;
import association.model.TaxOffice;
import association.model.Telephone;

/**
 * class handles the database connection
 * creates table, insert association object and update it
 * 
 * @author Leonid Oldenburger
 */
public class AssociationDB extends DBAccess{
    
    private Association association_object;
    private Adress association_adress;
    private Telephone association_phone;
    private Person association_leader;
    private Person association_treasurer;
    private TaxOffice association_office;
    private Adress association_o_adress;
    private Account association_account;
    
	private Group association_group_0;
	private Group association_group_1;
	private Group association_group_2;
	private Group association_group_3;
	private Group association_group_4;
	private Group association_group_5;
	
	private LinkedList<Group> association_member_group;
    
    /**
     * show all tables
     */
    public boolean showTables() {
        String query = "select * from Association";
        //String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        boolean state = false; 
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            // Select-Anweisung ausführen
            resultSet = statement.executeQuery(query);
            
            while(resultSet.next()) {
            	resultSet.getInt("id");
            }
            if (resultSet != null)
            	state  = true;
            
        } catch( SQLException ex ) {
            //System.out.println(message + ex.getMessage());
        } finally {
            
            // Alle Ressourcen wieder freigeben
            if( resultSet != null ) {
                try { 
                    resultSet.close();
                } catch( SQLException ex ) {
                   // System.out.println(message + ex.getMessage());
                }
            }
            if( statement != null ) {
                try { 
                    statement.close();
                } catch( SQLException ex ) {
                   // System.out.println(message + ex.getMessage());
                }
            }
        }
        disconnect();
        return state;
    }
    
    /**
     * get group
     * 
     * @return group
     */
    public LinkedList<Group> getGroup(){
    	
       	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            try{
            	resultSet = statement.executeQuery("Select * from Membergroup");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	
         	association_member_group = new LinkedList<Group>();
         	
         	// create member object sort by lastname
            while(resultSet.next()) {
            	if (!resultSet.getString("GroupName").equals("")){
            		association_member_group.add(new Group(resultSet.getString("GroupName"), new Contribution(resultSet.getDouble("Premium"), "", ""))); 
            	}
            }
        } catch( SQLException ex ) {
            System.out.println(message + ex.getMessage());
        } finally {
            
        	// Alle Ressourcen wieder freigeben
            if( resultSet != null ) {
                try { 
                    resultSet.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
            
            if( statement != null ) {
                try { 
                    statement.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
        }
        disconnect();
    	return association_member_group;
    }
    
    /**
     * methode get association object
     * 
     * @return
     */
    public Association getAssociation(){
    	
       	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
         	
            try{
            	resultSet = statement.executeQuery("Select * from Association LEFT JOIN Adress ON Association.id = Adress.ObjectID LEFT JOIN Account ON Association.id=Account.ObjectID LEFT JOIN Phone ON Association.id = Phone.ObjectID");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	association_object = Association.getInstance();
         	// create member object sort by lastname
            while(resultSet.next()) {
               association_object.setName(resultSet.getString("Name"));
               association_object.setPassword(resultSet.getInt("Password"));
               association_object.setDatadir(resultSet.getString("Datadir"));
               association_object.setPaymentRhythm(resultSet.getInt("Paymentrhythm"));
               association_object.setPaymentTime(resultSet.getString("Paymenttime"));
               association_object.setInitDate(resultSet.getString("InitDate"));
               association_object.setLogo(resultSet.getInt("Logo"));
               association_adress = new Adress("" ,resultSet.getString("Street"), resultSet.getString("Housenr"), resultSet.getString("PLZ"), resultSet.getString("City"), resultSet.getString("Country"), resultSet.getString("Email"));
               association_phone = new Telephone(resultSet.getString("Phone"), "", "", resultSet.getString("Fax"));
               association_account = new Account(resultSet.getString("Name"), resultSet.getString("Bankname"), resultSet.getString("accountnr"), resultSet.getString("blz"), resultSet.getString("bSwift"), resultSet.getString("iBan"));
            }
            
            try{
            	resultSet = statement.executeQuery("Select * from Person WHERE id = 1");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
            
         	association_leader = new Person();
       
            while(resultSet.next()) {
            	association_leader.setTitle(resultSet.getString("PersonTitle"));
            	association_leader.setGender(resultSet.getString("PersonGender"));
            	association_leader.setFirstName(resultSet.getString("PersonFirstname"));
            	association_leader.setLastName(resultSet.getString("PersonLastname"));
            }
            
            try{
            	resultSet = statement.executeQuery("Select * from Person WHERE id = 2");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
            
         	association_treasurer = new Person();
         	
            while(resultSet.next()) {
            	association_treasurer.setTitle(resultSet.getString("PersonTitle"));
            	association_treasurer.setGender(resultSet.getString("PersonGender"));
            	association_treasurer.setFirstName(resultSet.getString("PersonFirstname"));
            	association_treasurer.setLastName(resultSet.getString("PersonLastname"));
            }
            
            try{
            	resultSet = statement.executeQuery("Select * from Office");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
            
         	association_office = TaxOffice.getInstance();
         	
            while(resultSet.next()) {
            	association_office.setCurrency(resultSet.getString("Currency"));
            	association_office.setTaxNumber(resultSet.getString("TaxNr"));
            	association_office.setV(resultSet.getDate("VDate"));
            	association_office.setA(resultSet.getDate("ADate"));
            }
            
            try{
            	resultSet = statement.executeQuery("Select * from Adress WHERE id = 0");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	
            while(resultSet.next()) {
            	association_o_adress = new Adress("" ,resultSet.getString("Street"), resultSet.getString("Housenr"), resultSet.getString("PLZ"), resultSet.getString("City"), resultSet.getString("Country"), resultSet.getString("Email"));
            }
            
            
            try{
            	resultSet = statement.executeQuery("Select * from Membergroup");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	
         	association_member_group = new LinkedList<Group>();
         	
         	// create member object sort by lastname
            while(resultSet.next()) {
            	association_member_group.add(new Group(resultSet.getString("GroupName"), new Contribution(resultSet.getDouble("Premium"), "", ""))); 
            }
            
         	association_office.setAdress(association_o_adress);
         	
            association_object.setAdress(association_adress);
            association_object.setTelephone(association_phone);
            association_object.setAccount(association_account);
            association_object.setLeader(association_leader);
            association_object.setTreasurer(association_treasurer);
            association_object.setFinanceOffice(association_office);
            association_object.setGroupList(association_member_group);
            
         } catch( SQLException ex ) {
                System.out.println(message + ex.getMessage());
         } finally {
                
            	// Alle Ressourcen wieder freigeben
                if( resultSet != null ) {
                    try { 
                        resultSet.close();
                    } catch( SQLException ex ) {
                        System.out.println(message + ex.getMessage());
                    }
                }
                
                if( statement != null ) {
                    try { 
                        statement.close();
                    } catch( SQLException ex ) {
                        System.out.println(message + ex.getMessage());
                    }
                }
          }
          disconnect();
    	return association_object;
    }
    
    /**
     * methode update booking day
     * 
     * @param String date
     */
    public void updateAssociationBookingDay(String bookingdate){

        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            int object_id = -1;
            try { 
            	statement.executeUpdate("UPDATE Association SET Paymenttime = '"+bookingdate+"' WHERE id = "+object_id+"");            
            }catch( SQLException ex ) {
                 System.out.println(message + ex.getMessage());
            }
            
        } catch( SQLException ex ) {
            System.out.println(message + ex.getMessage());
        } finally {
            
        	// Alle Ressourcen wieder freigeben
            if( resultSet != null ) {
                try { 
                    resultSet.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
            
            if( statement != null ) {
                try { 
                    statement.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
        }
        disconnect();
    }
    
    /**
     * methode update association group
     * 
     * @param associationObject
     */
    public void updateAssociationGroup(Association associationObject){
    	this.association_object = associationObject;

        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            association_account = association_object.getAccount();
            try { 
             	 statement.executeUpdate("UPDATE Membergroup SET GroupName = '"+association_object.getGroupList().get(0).getGroup()+"', Premium = "+association_object.getGroupList().get(0).getPremium().getVlaue()+" WHERE id = 0");
            }catch( SQLException ex ) {
                 System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("UPDATE Membergroup SET GroupName = '"+association_object.getGroupList().get(1).getGroup()+"', Premium = "+association_object.getGroupList().get(1).getPremium().getVlaue()+" WHERE id = 1");
            }catch( SQLException ex ) {
                System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("UPDATE Membergroup SET GroupName = '"+association_object.getGroupList().get(2).getGroup()+"', Premium = "+association_object.getGroupList().get(2).getPremium().getVlaue()+" WHERE id = 2");
            }catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("UPDATE Membergroup SET GroupName = '"+association_object.getGroupList().get(3).getGroup()+"', Premium = "+association_object.getGroupList().get(3).getPremium().getVlaue()+" WHERE id = 3");
            }catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("UPDATE Membergroup SET GroupName = '"+association_object.getGroupList().get(4).getGroup()+"', Premium = "+association_object.getGroupList().get(4).getPremium().getVlaue()+" WHERE id = 4");
            }catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("UPDATE Membergroup SET GroupName = '"+association_object.getGroupList().get(5).getGroup()+"', Premium = "+association_object.getGroupList().get(5).getPremium().getVlaue()+" WHERE id = 5");
            }catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }    
            
        } catch( SQLException ex ) {
            System.out.println(message + ex.getMessage());
        } finally {
            
        	// Alle Ressourcen wieder freigeben
            if( resultSet != null ) {
                try { 
                    resultSet.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
            
            if( statement != null ) {
                try { 
                    statement.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
        }
        disconnect();
    }
    
    /**
     * methode update association budget
     * 
     * @param associationObject
     */
    public void updateAssociationBudget(Association associationObject){
    	
    	this.association_object = associationObject;

        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            association_account = association_object.getAccount();
            
            try { 
            	statement.executeUpdate("UPDATE Account SET Bankname = '"+association_account.getBankName()+"', " +
            			"accountnr = '"+association_account.getAccountNumber()+"', " +
            			"blz = '"+association_account.getBankCodeNumber()+"', " +
            			"bSwift = '"+association_account.getBicSwift()+"', " +
            			"iBan = '"+association_account.getiBan()+"' WHERE ObjectID = -1");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            int office_id = 0;
            
            association_o_adress = association_object.getFinacneOffice().getAdress();
            
            try { 
            	statement.executeUpdate("UPDATE Adress SET Adressextra ='"+association_o_adress.getAdressAdditional()+"', " +
            			"Street = '"+association_o_adress.getStreet()+"', Housenr = '"+association_o_adress.getHouseNumber()+"', " +
            			"City = '"+association_o_adress.getCity()+"', PLZ = '"+association_o_adress.getZipCode()+"', " +
            			"Country = '"+association_o_adress.getCountry()+"', Email = '"+association_o_adress.getEmail()+"' " +
            			"WHERE ObjectID = "+office_id+"");
            } catch( SQLException ex ) {
               System.out.println(message + ex.getMessage());
            }  
            
            try { 
            	statement.executeUpdate("UPDATE Office SET TaxNr = '"+association_object.getFinacneOffice().getTaxNumber()+"', Currency =  '"+association_object.getFinacneOffice().getCurrency()+"', VDate = '"+association_object.getFinacneOffice().getV()+"', ADate = '"+association_object.getFinacneOffice().getA()+"' WHERE id = 0");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            
        } catch( SQLException ex ) {
            System.out.println(message + ex.getMessage());
        } finally {
            
        	// Alle Ressourcen wieder freigeben
            if( resultSet != null ) {
                try { 
                    resultSet.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
            
            if( statement != null ) {
                try { 
                    statement.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
        }
        disconnect();
    }
    
    /**
     * methode update association admin
     * 
     * @param associationObject
     */
    public void updateAssociationAdmin(Association associationObject){
    	
    	this.association_object = associationObject;
    	this.association_leader = association_object.getLeader();
    	this.association_treasurer = association_object.getTreasurer();
    	
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            try{
            	statement.executeUpdate("UPDATE Person SET PersonTitle = '"+association_leader.getTitle()+"', " +
            			"PersonGender = '"+association_leader.getGender()+"', " +
            			"PersonFirstname = '"+association_leader.getFirstName()+"', " +
            			"PersonLastname = '"+association_leader.getLastName()+"' WHERE id = "+1+"");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
            
            try{
            	statement.executeUpdate("UPDATE Person SET PersonTitle = '"+association_treasurer.getTitle()+"', " +
            			"PersonGender = '"+association_treasurer.getGender()+"', " +
            			"PersonFirstname = '"+association_treasurer.getFirstName()+"', " +
            			"PersonLastname = '"+association_treasurer.getLastName()+"' WHERE id = "+2+""); 		
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
            
        } catch( SQLException ex ) {
            System.out.println(message + ex.getMessage());
        } finally {
            
        	// Alle Ressourcen wieder freigeben
            if( resultSet != null ) {
                try { 
                    resultSet.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
            
            if( statement != null ) {
                try { 
                    statement.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
        }
        disconnect();
    }
    
    /**
     * methode initStatusDate
     * 
     * @param initDate
     */
    public void initStatusDate(String initDate){
    	
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        //System.out.println(initDate);
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
         	int object_id = -1;
            
            try { 
            	statement.executeUpdate("UPDATE Association SET InitDate = '"+initDate+"' WHERE id = "+object_id+"");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
        } catch( SQLException ex ) {
            System.out.println(message + ex.getMessage());
        } finally {
            
        	// Alle Ressourcen wieder freigeben
            if( resultSet != null ) {
                try { 
                    resultSet.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
            if( statement != null ) {
                try { 
                    statement.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
        }
        disconnect();
    }
    
    /**
     * methode update association data
     * 
     * @param associationObject
     */
    public void updateAssociationData(Association associationObject){
    	
    	this.association_object = associationObject;
    	this.association_adress = association_object.getAdress();
    	this.association_phone = association_object.getTelephone();
    	
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
         	int object_id = -1;
            
            try { 
            	statement.executeUpdate("UPDATE Association SET  Name ='"+association_object.getName()+"', Password = "+association_object.getPassword()+", Datadir = '"+association_object.getDataDir()+"', Paymenttime = '"+association_object.getPamentTime()+"', Paymentrhythm = "+association_object.getPaymentRhythm()+", Logo = "+association_object.getLogo()+" WHERE id = "+object_id+"");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("UPDATE Adress SET Adressextra ='"+association_adress.getAdressAdditional()+"', " +
            			"Street = '"+association_adress.getStreet()+"', Housenr = '"+association_adress.getHouseNumber()+"', " +
            			"City = '"+association_adress.getCity()+"', PLZ = '"+association_adress.getZipCode()+"', " +
            			"Country = '"+association_adress.getCountry()+"', Email = '"+association_adress.getEmail()+"' " +
            			"WHERE ObjectID = "+object_id+"");
            } catch( SQLException ex ) {
               System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("UPDATE Phone SET Phone = '"+association_phone.getPrivatePhone()+"', Fax = '"+association_phone.getFaxNumber()+"' WHERE ObjectID = "+object_id+ "");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
        } catch( SQLException ex ) {
            System.out.println(message + ex.getMessage());
        } finally {
            
        	// Alle Ressourcen wieder freigeben
            if( resultSet != null ) {
                try { 
                    resultSet.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
            if( statement != null ) {
                try { 
                    statement.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
        }
        disconnect();
    }
    
    /**
     * methode insert association
     * 
     * @param associationObject
     */
    public void insertAssociation(Association associationObject){
    	
    	this.association_object = associationObject;
    	this.association_adress = association_object.getAdress();
    	this.association_phone = association_object.getTelephone();
    	this.association_leader = association_object.getLeader();
    	this.association_treasurer = association_object.getTreasurer();
    	this.association_office = association_object.getFinacneOffice();
    	this.association_o_adress = association_office.getAdress();
    	this.association_account = association_object.getAccount();
    	
    	this.association_group_0 = association_object.getGroupList().get(0);
    	this.association_group_1 = association_object.getGroupList().get(1);
    	this.association_group_2 = association_object.getGroupList().get(2);
    	this.association_group_3 = association_object.getGroupList().get(3);
    	this.association_group_4 = association_object.getGroupList().get(4);
    	this.association_group_5 = association_object.getGroupList().get(5);
    	
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            try { 
            	statement.executeUpdate("INSERT INTO Association(Name, Password, Datadir, Paymenttime, Paymentrhythm, Logo) VALUES( '"+association_object.getName()+"', "+association_object.getPassword()+", '"+association_object.getDataDir()+"', '"+association_object.getPamentTime()+"', "+association_object.getPaymentRhythm()+", "+association_object.getLogo()+")");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage()+"test");
            }    
            
         	int object_id = -1;

            try { 
            	statement.executeUpdate("INSERT INTO Adress(Adressextra, Street, Housenr, City, PLZ, Country, Email, ObjectID) VALUES('"+association_adress.getAdressAdditional()+"',"
            			+" '"+association_adress.getStreet()+"', '"+association_adress.getHouseNumber()+"', '"+association_adress.getCity()+"', '"+association_adress.getZipCode()+"', '"
            			+association_adress.getCountry()+"', '"+association_adress.getEmail()+"', "+object_id+")");
            } catch( SQLException ex ) {
               System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Account(Bankname, accountnr, blz, bSwift, iBan, ObjectID) VALUES( '"+association_account.getBankName()+"', " +
            			"'"+association_account.getAccountNumber()+"', '"+association_account.getBankCodeNumber()+"', '"+association_account.getBicSwift()+"', " +
            					"'"+association_account.getiBan()+"', "+object_id+")");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Phone(Phone, Fax, ObjectID) VALUES( '"+association_phone.getPrivatePhone()+"', '"+association_phone.getFaxNumber()+"', "+object_id+")");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
         	int office_id = 0;
            
            try { 
            	statement.executeUpdate("INSERT INTO Adress(Street, Housenr, City, PLZ, ObjectID) VALUES('"+association_o_adress.getStreet()+"', '"+association_o_adress.getHouseNumber()+"', '"+association_o_adress.getCity()+"', '"+association_o_adress.getZipCode()+"', "+office_id+")");
            } catch( SQLException ex ) {
               System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Phone(ObjectID) VALUES("+office_id+")");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Account(ObjectID) VALUES("+office_id+")");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try{
            	statement.executeUpdate("INSERT INTO Member(Title, Gender, Firstname, Lastname, Status) VALUES( '"+association_leader.getTitle()+"', '"+association_leader.getGender()+"', '"
            			+association_leader.getFirstName()+"', '"+association_leader.getLastName()+"', "+-1+")");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}      	
         	
            try{
            	statement.executeUpdate("INSERT INTO Person(PersonTitle, PersonGender, PersonFirstname, PersonLastname) " +
            			"VALUES('"+association_leader.getTitle()+"', '"+association_leader.getGender()+"', " +
            					"'"+association_leader.getFirstName()+"', '"+association_leader.getLastName()+"')");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}    
         	
            try { 
            	statement.executeUpdate("INSERT INTO Adress(ObjectID) VALUES(1)");
            } catch( SQLException ex ) {
               System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Phone(ObjectID) VALUES(1)");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Account(ObjectID) VALUES(1)");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try{
            	statement.executeUpdate("INSERT INTO Member(Title, Gender, Firstname, Lastname, Status) VALUES( '"+association_treasurer.getTitle()+"', '"+association_treasurer.getGender()+"', '"
            			+association_treasurer.getFirstName()+"', '"+association_treasurer.getLastName()+"', "+-1+")");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	
            try{
            	statement.executeUpdate("INSERT INTO Person(PersonTitle, PersonGender, PersonFirstname, PersonLastname) " +
            			"VALUES( '"+association_treasurer.getTitle()+"', '"+association_treasurer.getGender()+"', " +
            					"'"+association_treasurer.getFirstName()+"', '"+association_treasurer.getLastName()+"')");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	
            try { 
            	statement.executeUpdate("INSERT INTO Adress(ObjectID) VALUES(2)");
            } catch( SQLException ex ) {
               System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Phone(ObjectID) VALUES(2)");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Account(ObjectID) VALUES(2)");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
         	
            try { 
            	statement.executeUpdate("INSERT INTO Office(TaxNr, Currency, VDate, ADate) VALUES( '"+association_office.getTaxNumber()+"', '"+association_office.getCurrency()+"', '"+association_office.getV()+"', '"+association_office.getA()+"')");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try { 
              	 statement.executeUpdate("INSERT INTO Membergroup(GroupName, Premium) VALUES('"+association_group_0.getGroup()+"', "+association_group_0.getPremium().getVlaue()+")");
            }catch( SQLException ex ) {
                  System.out.println(message + ex.getMessage());
            }
            
            try { 
             	 statement.executeUpdate("INSERT INTO Membergroup(GroupName, Premium) VALUES('"+association_group_1.getGroup()+"', "+association_group_1.getPremium().getVlaue()+")");
            }catch( SQLException ex ) {
                 System.out.println(message + ex.getMessage());
            }
            
            try { 
             	 statement.executeUpdate("INSERT INTO Membergroup(GroupName, Premium) VALUES('"+association_group_2.getGroup()+"', "+association_group_2.getPremium().getVlaue()+")");
            }catch( SQLException ex ) {
                 System.out.println(message + ex.getMessage());
            }
           
            try { 
             	 statement.executeUpdate("INSERT INTO Membergroup(GroupName, Premium) VALUES('"+association_group_3.getGroup()+"', "+association_group_3.getPremium().getVlaue()+")");
           }catch( SQLException ex ) {
                 System.out.println(message + ex.getMessage());
           }
           
           try { 
            	 statement.executeUpdate("INSERT INTO Membergroup(GroupName, Premium) VALUES('"+association_group_4.getGroup()+"', "+association_group_4.getPremium().getVlaue()+")");
           }catch( SQLException ex ) {
                System.out.println(message + ex.getMessage());
           }
           
           try { 
            	 statement.executeUpdate("INSERT INTO Membergroup(GroupName, Premium) VALUES('"+association_group_5.getGroup()+"', "+association_group_5.getPremium().getVlaue()+")");
           }catch( SQLException ex ) {
                System.out.println(message + ex.getMessage());
           }
            
         } catch( SQLException ex ) {
                System.out.println(message + ex.getMessage());
         } finally {
                
            	// Alle Ressourcen wieder freigeben
                if( resultSet != null ) {
                    try { 
                        resultSet.close();
                    } catch( SQLException ex ) {
                        System.out.println(message + ex.getMessage());
                    }
                }
                if( statement != null ) {
                    try { 
                        statement.close();
                    } catch( SQLException ex ) {
                        System.out.println(message + ex.getMessage());
                    }
                }
          }
          disconnect();
    }
    
    /**
     * methode create table
     */
    public void createTable(){
        
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
    	
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            try { 
            	 statement.executeUpdate("CREATE TABLE Association (id INTEGER  NOT NULL  GENERATED ALWAYS AS IDENTITY (START WITH -1, INCREMENT BY 1), Name VARCHAR(50), Password INT, Datadir Varchar(200), Paymenttime Varchar(50), Paymentrhythm INT, InitDate Varchar(50), Logo INT)");
            } catch( SQLException ex ) {
                System.out.println(message + ex.getMessage());
            }
            
            try { 
           	 	statement.executeUpdate("CREATE TABLE Office (id INTEGER  NOT NULL  GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1), TaxNr VARCHAR(50), Currency VARCHAR(50), VDate DATE, ADate DATE)");
            }catch( SQLException ex ) {
               System.out.println(message + ex.getMessage());
            }
            
            try { 
            	 statement.executeUpdate("CREATE TABLE Adress (id INTEGER  NOT NULL  GENERATED ALWAYS AS IDENTITY (START WITH -1, INCREMENT BY 1), Adressextra VARCHAR(20), Street VARCHAR(50), Housenr VARCHAR(50), City VARCHAR(50), PLZ VARCHAR(50), Country VARCHAR(50), Email VARCHAR(50), ObjectID INT, DonatorID INT)");
            } catch( SQLException ex ) {
                System.out.println(message + ex.getMessage());
            }               
            
            try { 
              	 statement.executeUpdate("CREATE TABLE Account (id INTEGER  NOT NULL  GENERATED ALWAYS AS IDENTITY (START WITH -1, INCREMENT BY 1), Owner VARCHAR(50), Bankname VARCHAR(50), accountnr VARCHAR(50), blz VARCHAR(50), bSwift VARCHAR(50), iBan VARCHAR(50), ObjectID INT, DonatorID INT)");
             }catch( SQLException ex ) {
                  System.out.println(message + ex.getMessage());
             }
             
             try { 
               	 statement.executeUpdate("CREATE TABLE Phone (id INTEGER  NOT NULL  GENERATED ALWAYS AS IDENTITY (START WITH -1, INCREMENT BY 1), Phone VARCHAR(50), Fax VARCHAR(50), ObjectID INT, DonatorID INT)");
             }catch( SQLException ex ) {
                   System.out.println(message + ex.getMessage());                
             }
             
             try { 
               	 statement.executeUpdate("CREATE TABLE Membergroup (id INTEGER  NOT NULL  GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1), GroupName VARCHAR(50), Premium DOUBLE, ObjectID INT)");
             }catch( SQLException ex ) {
                   System.out.println(message + ex.getMessage());
             }
             
             try { 
               	 statement.executeUpdate("CREATE TABLE Member (id INTEGER  NOT NULL  GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), Title VARCHAR(50), Gender VARCHAR(50), Firstname VARCHAR(50), Lastname VARCHAR(50), Status INT, MemberStatus INT, GroupID INT, Comment VARCHAR(1000), ContributionOld DOUBLE, ContributionSum DOUBLE, Payway INT, ContributionDid INT)");
             }catch( SQLException ex ) {
                   System.out.println(message + ex.getMessage());
             }
             
             try { 
               	 statement.executeUpdate("CREATE TABLE Donator (id INTEGER  NOT NULL  GENERATED ALWAYS AS IDENTITY (START WITH 20001, INCREMENT BY 1), Title VARCHAR(20), Gender VARCHAR(50), Firstname VARCHAR(50), Lastname VARCHAR(50), DonatorStatus INT)");
             }catch( SQLException ex ) {
                   System.out.println(message + ex.getMessage());
             }
             
             try { 
               	 statement.executeUpdate("CREATE TABLE Contribution (id INTEGER  NOT NULL  GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1), SContribution DOUBLE,  PTime INT, PForm INT, SpecialBookingDate Date, MemberID INT)");
             }catch( SQLException ex ) {
                   System.out.println(message + ex.getMessage());
             }
             
             try { 
               	 statement.executeUpdate("CREATE TABLE Mail(id INTEGER  NOT NULL  GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), Subject VARCHAR(50), Text VARCHAR(5000), Date VARCHAR(50), MemberGroup VARCHAR(50))");
             }catch( SQLException ex ) {
                   System.out.println(message + ex.getMessage());
             }
             
             try { 
               	 statement.executeUpdate("CREATE TABLE Receipt(id INTEGER  NOT NULL  GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), Date VARCHAR(50), MemberGroup VARCHAR(50), Expense INT, BaseOne INT, BaseTwo INT, ObjectOne VARCHAR(200), ObjectTwo VARCHAR(200), ObjectThree VARCHAR(200))");
             }catch( SQLException ex ) {
                   System.out.println(message + ex.getMessage());
             }
             
             try { 
               	 statement.executeUpdate("CREATE TABLE Person(id INTEGER  NOT NULL  GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), PersonTitle VARCHAR(50), PersonGender VARCHAR(50), PersonFirstname VARCHAR(50), PersonLastname VARCHAR(50))");
             }catch( SQLException ex ) {
                   System.out.println(message + ex.getMessage());
             }
             
        } catch( SQLException ex ) {
            System.out.println(message + ex.getMessage());
        } finally {
            
            if( statement != null ) {
                try { 
                    statement.close();
                } catch( SQLException ex ) {
                    System.out.println(message + ex.getMessage());
                }
            }
        }
        disconnect();
    }
}