
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
import java.util.LinkedHashMap;

import member.model.DonatorWithAdress;

import association.model.Account;
import association.model.Adress;
import association.model.Telephone;

/**
 * class represents Donator access to the database
 * 
 * @author Leonid Oldenburger
 */
public class DonatorDB extends DBAccess{
    
    private DonatorWithAdress association_donator_wa;
    private Adress association_d_adress;
    private Telephone association_d_phone;
    private Account association_d_account;
    private LinkedHashMap<Integer,DonatorWithAdress> association_donator_map;

	public static final int ALLE = 0;
	public static final int STATUSBEITRAGFAELLIG = 3;
    
	/**
	 * methode get donator from database
	 * 
	 * @param select
	 * @param lastname
	 * @return
	 */
    public LinkedHashMap<Integer,DonatorWithAdress> getDonatorFromDB(int select, String lastname){
        
    	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        association_donator_map = new LinkedHashMap<Integer,DonatorWithAdress>();
        
        int donator_id = 0;
        
        if(lastname != null)
        	donator_id = Integer.parseInt(lastname);
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
         	
            if(select == DonatorDB.ALLE){
            	try{
            		resultSet = statement.executeQuery("Select * from Donator LEFT JOIN Adress ON Donator.id = Adress.DonatorID " +
            				"LEFT JOIN Account ON Donator.id=Account.DonatorID LEFT JOIN Phone ON Donator.id = Phone.DonatorID " +
            				"ORDER BY Donator.Lastname ASC");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
        	}else if(select == 1){
        		try{
        			resultSet = statement.executeQuery("Select * from Donator LEFT JOIN Adress ON Donator.id = Adress.DonatorID " +
        					"LEFT JOIN Account ON Donator.id=Account.DonatorID LEFT JOIN Phone ON Donator.id = Phone.DonatorID " +
        					"WHERE DonatorStatus = "+1+" ORDER BY Donator.Lastname ASC");
        		} catch( SQLException ex ) {
        			System.out.println(message + ex.getMessage());
        		}
            }else if(select == 2){
            	try{
            		resultSet = statement.executeQuery("Select * from Donator LEFT JOIN Adress ON Donator.id = Adress.DonatorID" +
            				" LEFT JOIN Account ON Donator.id=Account.DonatorID LEFT JOIN Phone ON Donator.id = Phone.DonatorID " +
            				"WHERE DonatorStatus = "+0+" ORDER BY Donator.Lastname ASC");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
            }else if(select == 3){
            	try{
            		resultSet = statement.executeQuery("Select * from Donator LEFT JOIN Adress ON Donator.id = Adress.DonatorID " +
            				"LEFT JOIN Account ON Donator.id=Account.DonatorID LEFT JOIN Phone ON Donator.id = Phone.DonatorID WHERE Lastname LIKE '%"+lastname+"%'" +
            				"ORDER BY Donator.Lastname ASC");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
            }else if(select == 4){
            	try{
            		resultSet = statement.executeQuery("Select * from Donator LEFT JOIN Adress ON Donator.id = Adress.DonatorID " +
            				"LEFT JOIN Account ON Donator.id=Account.DonatorID LEFT JOIN Phone ON Donator.id = Phone.DonatorID WHERE Donator.id = "+donator_id+"");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
            }
            
            
            while(resultSet.next()) {
               association_donator_wa = new DonatorWithAdress();
               association_d_adress = new Adress(resultSet.getString("Adressextra") ,resultSet.getString("Street"),
            		   resultSet.getString("Housenr"), resultSet.getString("PLZ"), resultSet.getString("City"), 
            		   resultSet.getString("Country"), resultSet.getString("Email"));
               association_d_phone = new Telephone(resultSet.getString("Phone"), "", "", "");
               association_d_account = new Account(resultSet.getString("Owner"), resultSet.getString("Bankname"), 
            		   resultSet.getString("accountnr"), resultSet.getString("blz"), "", "");
               association_donator_wa.setTitle(resultSet.getString("Title"));
               association_donator_wa.setGender(resultSet.getString("Gender"));
               association_donator_wa.setFirstName(resultSet.getString("Firstname"));
               association_donator_wa.setLastName(resultSet.getString("Lastname"));
               association_donator_wa.setType(resultSet.getInt("DonatorStatus"));
               association_donator_wa.setAdress(association_d_adress);
               association_donator_wa.setPhone(association_d_phone);
               association_donator_wa.setAccount(association_d_account);
               association_donator_wa.setNumber(resultSet.getInt("id"));
               association_donator_map.put(resultSet.getInt("id"), association_donator_wa);
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
    	return association_donator_map;
    }
    
    /**
     * methode get donator by name
     * 
     * @param firstname
     * @param lastname
     * @param kto
     * @param blz
     * @return
     */
    public DonatorWithAdress getDonatorByName(String firstname, String lastname, long kto, long blz){
        
       	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        String account_nr = (new Long(kto)).toString();
        String blz_nr = (new Long(blz)).toString();
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
         	    
            try{
            	resultSet = statement.executeQuery("Select * from Donator LEFT JOIN Adress ON Donator.id = Adress.DonatorID " +
            			"LEFT JOIN Account ON Donator.id=Account.DonatorID LEFT JOIN Phone ON Donator.id = Phone.DonatorID " +
            			"WHERE accountnr = '"+account_nr+"' AND blz = '"+blz_nr+"' AND Firstname = '"+firstname+"' AND Lastname = '"+lastname+"'ORDER BY Donator.Lastname ASC");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	
            while(resultSet.next()) {
               association_donator_wa = new DonatorWithAdress();
               association_d_adress = new Adress(resultSet.getString("Adressextra") ,resultSet.getString("Street"), 
            		   resultSet.getString("Housenr"), resultSet.getString("PLZ"), resultSet.getString("City"), 
            		   resultSet.getString("Country"), resultSet.getString("Email"));
               association_d_phone = new Telephone(resultSet.getString("Phone"), "", "", "");
               association_d_account = new Account(resultSet.getString("Owner"), resultSet.getString("Bankname"),
            		   resultSet.getString("accountnr"), resultSet.getString("blz"), "", "");
               association_donator_wa.setTitle(resultSet.getString("Title"));
               association_donator_wa.setGender(resultSet.getString("Gender"));
               association_donator_wa.setFirstName(resultSet.getString("Firstname"));
               association_donator_wa.setLastName(resultSet.getString("Lastname"));
               association_donator_wa.setType(resultSet.getInt("DonatorStatus"));
               association_donator_wa.setAdress(association_d_adress);
               association_donator_wa.setPhone(association_d_phone);
               association_donator_wa.setAccount(association_d_account);
               association_donator_wa.setNumber(resultSet.getInt("id"));
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
          
          if(association_donator_wa != null){
        	  return association_donator_wa;
          }else {
        	  return null;
          }
    }
    
    /**
     * methode get donator by account number 
     * 
     * @param kto
     * @param blz
     * @return
     */
    public DonatorWithAdress getDonator(long kto, long blz){
    
       	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        String account_nr = (new Long(kto)).toString();
        String blz_nr = (new Long(blz)).toString();
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
         	    
            try{
            	resultSet = statement.executeQuery("Select * from Donator LEFT JOIN Adress ON Donator.id = Adress.DonatorID " +
            			"LEFT JOIN Account ON Donator.id=Account.DonatorID LEFT JOIN Phone ON Donator.id = Phone.DonatorID " +
            			"WHERE accountnr = '"+account_nr+"' AND blz = '"+blz_nr+"' ORDER BY Donator.Lastname ASC");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	
            while(resultSet.next()) {
               association_donator_wa = new DonatorWithAdress();
               association_d_adress = new Adress(resultSet.getString("Adressextra") ,resultSet.getString("Street"), 
            		   resultSet.getString("Housenr"), resultSet.getString("PLZ"), resultSet.getString("City"), 
            		   resultSet.getString("Country"), resultSet.getString("Email"));
               association_d_phone = new Telephone(resultSet.getString("Phone"), "", "", "");
               association_d_account = new Account(resultSet.getString("Owner"), resultSet.getString("Bankname"),
            		   resultSet.getString("accountnr"), resultSet.getString("blz"), "", "");
               association_donator_wa.setTitle(resultSet.getString("Title"));
               association_donator_wa.setGender(resultSet.getString("Gender"));
               association_donator_wa.setFirstName(resultSet.getString("Firstname"));
               association_donator_wa.setLastName(resultSet.getString("Lastname"));
               association_donator_wa.setType(resultSet.getInt("DonatorStatus"));
               association_donator_wa.setAdress(association_d_adress);
               association_donator_wa.setPhone(association_d_phone);
               association_donator_wa.setAccount(association_d_account);
               association_donator_wa.setNumber(resultSet.getInt("id"));
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
          
          if(association_donator_wa != null){
        	  return association_donator_wa;
          }else {
        	  return null;
          }
    }
    
//    public void updateSpecialContribution(int donatorID, Date booking_date){
//    	
//     	int donator_id = donatorID;
//     	
//        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
//        Statement statement = null;
//        ResultSet resultSet = null;
//        
//        connect();
//        
//        try {
//            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
//            statement = connection.createStatement();
//
//            try { 
//            	statement.executeUpdate("UPDATE Contribution SET SpecialBookingDate = '"+booking_date+"'" +
//            			" WHERE DonatorID= "+donator_id+"");
//            } catch( SQLException ex ) {
//            	System.out.println(message + ex.getMessage());
//            }
//
//         } catch( SQLException ex ) {
//                System.out.println(message + ex.getMessage());
//         } finally {
//                
//            	// Alle Ressourcen wieder freigeben
//                if( resultSet != null ) {
//                    try { 
//                        resultSet.close();
//                    } catch( SQLException ex ) {
//                        System.out.println(message + ex.getMessage());
//                    }
//                }
//                if( statement != null ) {
//                    try { 
//                        statement.close();
//                    } catch( SQLException ex ) {
//                        System.out.println(message + ex.getMessage());
//                    }
//                }
//          }
//          disconnect();
//    }
    
    
//    public void updateSpecialContribution(int donatorID, SpecialContribution scontribution){
//    	
//    	this.special_contribution = scontribution;
//    	
//     	int donator_id = donatorID;
//     	
//        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
//        Statement statement = null;
//        ResultSet resultSet = null;
//        
//        connect();
//        
//        try {
//            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
//            statement = connection.createStatement();
//
//            try { 
//            	statement.executeUpdate("UPDATE Contribution SET Premium = "+special_contribution.getVlaue()+"," +
//            			" PaymentTime = "+special_contribution.getPayTime()+", PaymentForm = "+special_contribution.getPayForm()+" " +
//            			" SpecialBookingDate = '"+special_contribution.getSpecialPremiumDate()+"' WHERE DonatorID= "+donator_id+"");
//            } catch( SQLException ex ) {
//            	System.out.println(message + ex.getMessage());
//            }
//
//         } catch( SQLException ex ) {
//                System.out.println(message + ex.getMessage());
//         } finally {
//                
//            	// Alle Ressourcen wieder freigeben
//                if( resultSet != null ) {
//                    try { 
//                        resultSet.close();
//                    } catch( SQLException ex ) {
//                        System.out.println(message + ex.getMessage());
//                    }
//                }
//                if( statement != null ) {
//                    try { 
//                        statement.close();
//                    } catch( SQLException ex ) {
//                        System.out.println(message + ex.getMessage());
//                    }
//                }
//          }
//          disconnect();
//    }
    
    /**
     * methode update donator
     */
    public void updateDonator(DonatorWithAdress donatorwa){
    	
    	this.association_donator_wa = donatorwa;
    	this.association_d_adress = association_donator_wa.getAdress();
    	this.association_d_phone = association_donator_wa.getPhone();
    	this.association_d_account = association_donator_wa.getAccount();
     	int donator_id = association_donator_wa.getNumber();
     	
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();

            try{
            	statement.executeUpdate("UPDATE Donator SET Title = '"+association_donator_wa.getTitle()+"', " +
            			"Gender = '"+association_donator_wa.getGender()+"', Firstname = '"+association_donator_wa.getFirstName()+"', " +
            			"Lastname = '"+association_donator_wa.getLastName()+"', DonatorStatus = "+association_donator_wa.getType()+" " +
            			"WHERE id = "+donator_id+"");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	
            try { 
            	statement.executeUpdate("UPDATE Adress SET Adressextra = '"+association_d_adress.getAdressAdditional()+"', " +
            			"Street = '"+association_d_adress.getStreet()+"', Housenr = '"+association_d_adress.getHouseNumber()+"', " +
            			"City = '"+association_d_adress.getCity()+"', PLZ = '"+association_d_adress.getZipCode()+"', " +
            			"Country = '"+association_d_adress.getCountry()+"', " +
            			"Email = '"+association_d_adress.getEmail()+"' WHERE DonatorID = "+donator_id+"");

            } catch( SQLException ex ) {
               System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("UPDATE Account SET Owner = '"+association_d_account.getOwner()+"', " +
            			"Bankname = '"+association_d_account.getBankName()+"', " +
            			"accountnr = '"+association_d_account.getAccountNumber()+"', " +
            			"blz = '"+association_d_account.getBankCodeNumber()+"' WHERE DonatorID = "+donator_id+"");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("UPDATE Phone SET Phone = '"+association_d_phone.getPrivatePhone()+"' " +
            			"WHERE DonatorID = "+donator_id+"");
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
     * methoder insert a new donator
     * 
     * @param adonator
     */
    public void insertNewDonator(DonatorWithAdress adonator){
    	
    	this.association_donator_wa = adonator;
    	this.association_d_adress = association_donator_wa.getAdress();
    	this.association_d_phone = association_donator_wa.getPhone();
    	this.association_d_account = association_donator_wa.getAccount();
 
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
                
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            try{
            	statement.executeUpdate("INSERT INTO Donator(Title, Gender, Firstname, Lastname, DonatorStatus) " +
            			"VALUES( '"+association_donator_wa.getTitle()+"', '"+association_donator_wa.getGender()+"', '"
            			+association_donator_wa.getFirstName()+"', '"+association_donator_wa.getLastName()+"', " +
            			""+association_donator_wa.getType()+")");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}

            try{
            	resultSet = statement.executeQuery("select * from Donator WHERE id = IDENTITY_VAL_LOCAL()");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
            
         	int donator_id = 0;
         	
            while(resultSet.next()) {
                donator_id = resultSet.getInt("id");
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Adress(Adressextra, Street, Housenr, City," +
            			" PLZ, Country, Email, DonatorID) VALUES('"+association_d_adress.getAdressAdditional()+"',"
            			+" '"+association_d_adress.getStreet()+"', '"+association_d_adress.getHouseNumber()+"', " +
            			"'"+association_d_adress.getCity()+"', '"+association_d_adress.getZipCode()+"', '"
            			+association_d_adress.getCountry()+"', '"+association_d_adress.getEmail()+"', "+donator_id+")");
            } catch( SQLException ex ) {
               System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Account(Owner, Bankname, accountnr, blz, DonatorID) VALUES('"+association_d_account.getOwner()+"'," +
            			" '"+association_d_account.getBankName()+"', " +
            			"'"+association_d_account.getAccountNumber()+"', " +
            			"'"+association_d_account.getBankCodeNumber()+"', " +
            			""+donator_id+")");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Phone(Phone, DonatorID) VALUES('"+association_d_phone.getPrivatePhone()+"', " +
            			""+donator_id+")");
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
     * methode to delete a member
     * 
     * @param userID
     */
    public void deleteDonator(int donatorID){
    	
         String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
         Statement statement = null;
         ResultSet resultSet = null;
            
            connect();
            
            try {
                // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
                statement = connection.createStatement();
               
                try{
                	statement.executeUpdate("DELETE FROM Donator WHERE id = "+donatorID+"");
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
//    public void insertDonator(Member aMember, int typ, int user_id){
//    	
//    	int donator_typ = typ;
//    	this.association_member = aMember;
//    	this.association_d_adress = association_member.getAdress();
//    	this.association_d_phone = association_member.getPhone();
//    	this.association_d_account = association_member.getAccount();
// 
//        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
//        Statement statement = null;
//        ResultSet resultSet = null;
//        
//        connect();
//        
//        try {
//            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
//            statement = connection.createStatement();
//            
//            try{
//            	statement.executeUpdate("INSERT INTO Donator(Title, Gender, Firstname, Lastname, " +
//            			"DonatorStatus) VALUES( '"+association_member.getTitle()+"', '"+association_member.getGender()+"', '"
//            			+association_member.getFirstName()+"', '"+association_member.getLastName()+"', "+donator_typ+")");
//         	} catch( SQLException ex ) {
//         		System.out.println(message + ex.getMessage());
//         	}
//         	
//            try{
//            	resultSet = statement.executeQuery("select * from Donator WHERE id = IDENTITY_VAL_LOCAL()");
//         	} catch( SQLException ex ) {
//         		System.out.println(message + ex.getMessage());
//         	}
//            
//         	int donator_id = 0;
//         	
//            while(resultSet.next()) {
//                donator_id = resultSet.getInt("id");
//            }
//            
//            try { 
//            	statement.executeUpdate("INSERT INTO Adress(Adressextra, Street, Housenr," +
//            			" City, PLZ, Country, Email, DonatorID) VALUES('"+association_d_adress.getAdressAdditional()+"',"
//            			+" '"+association_d_adress.getStreet()+"', '"+association_d_adress.getHouseNumber()+"'," +
//            			" '"+association_d_adress.getCity()+"', '"+association_d_adress.getZipCode()+"', '"
//            			+association_d_adress.getCountry()+"', '"+association_d_adress.getEmail()+"', "+donator_id+")");
//            } catch( SQLException ex ) {
//               System.out.println(message + ex.getMessage());
//            }
//            
//            try { 
//            	statement.executeUpdate("INSERT INTO Account(Owner, Bankname, accountnr, blz, DonatorID) VALUES('"+association_d_account.getOwner()+"', " +
//            			"'"+association_d_account.getBankName()+"', " +
//            			"'"+association_d_account.getAccountNumber()+"', '"+association_d_account.getBankCodeNumber()+"', "+donator_id+")");
//            } catch( SQLException ex ) {
//            	System.out.println(message + ex.getMessage());
//            }
//            
//            try { 
//            	statement.executeUpdate("INSERT INTO Phone(Phone, DonatorID) VALUES( '"+association_d_phone.getPrivatePhone()+"', "+donator_id+")");
//            } catch( SQLException ex ) {
//            	System.out.println(message + ex.getMessage());
//            } 
//            
//         } catch( SQLException ex ) {
//                System.out.println(message + ex.getMessage());
//         } finally {
//                
//            	// Alle Ressourcen wieder freigeben
//                if( resultSet != null ) {
//                    try { 
//                        resultSet.close();
//                    } catch( SQLException ex ) {
//                        System.out.println(message + ex.getMessage());
//                    }
//                }
//                if( statement != null ) {
//                    try { 
//                        statement.close();
//                    } catch( SQLException ex ) {
//                        System.out.println(message + ex.getMessage());
//                    }
//                }
//          }
//         disconnect();
//    }
}