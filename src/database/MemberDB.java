
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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.LinkedHashMap;

import member.model.Member;

import association.model.Account;
import association.model.Adress;
import association.model.Contribution;
import association.model.Group;
import association.model.SpecialContribution;
import association.model.Telephone;

/**
 * class represents member connection to the database
 * 
 * @author Leonid Oldenburger
 */
public class MemberDB extends DBAccess{
    
    private Member association_member;
    private Adress association_m_adress;
    private Telephone association_m_phone;
    private Account association_m_account;
    private SpecialContribution association_m_sc;
    
    private LinkedHashMap<Integer,Member> association_member_map;
    
    /**
     * methode to get member from db
     * 
     * @param select
     * @param lastname
     * @return
     */
    public LinkedHashMap<Integer,Member> getMemberFromDB(int select, String lastname){
    	
    	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        association_member_map = new LinkedHashMap<Integer,Member>();
        int member_id = 0;
       
        if(lastname != null)
        	member_id = Integer.parseInt(lastname);
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            if(select == -1){
            	try{
            		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
            				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
            				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id " +
            				"LEFT JOIN Contribution ON Member.id = Contribution.MemberID ORDER BY Member.LastName ASC");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}      	
            }else if(select == -2){
            	try{
            		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
            				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
            				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id LEFT JOIN Contribution ON Member.id = Contribution.MemberID " +
            				"WHERE Status = "+0+" AND MemberStatus = "+1+" " +
            				"ORDER BY Member.LastName ASC");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
            }else if(select == -3){
            	try{
            		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
            				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
            				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id LEFT JOIN Contribution ON Member.id = Contribution.MemberID " +
            				"WHERE MemberStatus = "+0+" " +
            				"ORDER BY Member.LastName ASC");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
            }else if(select == -4){
            	try{
            		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
            				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
            				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id WHERE MemberStatus = "+2+" " +
            				"ORDER BY Member.LastName ASC");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
            }else if(select == -5){
            	try{
            		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
            				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
            				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id LEFT JOIN Contribution ON Member.id = Contribution.MemberID " +
            				"WHERE Status = "+0+" " +
            				"AND MemberStatus = "+1+" AND Payway = "+0+" ORDER BY Member.LastName ASC");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
            }else if(select == -6){
            	try{
            		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
            				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
            				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id LEFT JOIN Contribution ON Member.id = Contribution.MemberID " +
            				"WHERE MemberStatus = "+1+" " +
            				"ORDER BY Member.LastName ASC");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
            }else if(select == -7){
            	try{
            		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
            				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
            				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id LEFT JOIN Contribution ON Member.id = Contribution.MemberID " +
            				"WHERE Status = "+0+" AND MemberStatus = "+1+" AND PTime > "+0+" AND PForm = "+0+" " +
            				"ORDER BY Member.LastName ASC");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
            }else if(select == -8){
            	try{
            		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
            				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
            				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id " +
            				"LEFT JOIN Contribution ON Member.id = Contribution.MemberID WHERE Lastname LIKE '%"+lastname+"%' ORDER BY Member.LastName ASC");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
            }else if(select == -9){
            	try{
            		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
            				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
            				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id " +
            				"LEFT JOIN Contribution ON Member.id = Contribution.MemberID WHERE MemberStatus = "+2+" ORDER BY Member.LastName ASC");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
            }else if(select == -10){
            	try{
            		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
            				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
            				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id " +
            				"LEFT JOIN Contribution ON Member.id = Contribution.MemberID WHERE Member.id = "+member_id+"");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
            }else{
               	try{
            		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
            				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
            				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id LEFT JOIN Contribution ON Member.id = Contribution.MemberID " +
            				"WHERE GroupID = "+select+" ORDER BY Member.LastName ASC");
            	} catch( SQLException ex ) {
            		System.out.println(message + ex.getMessage());
            	}
            }
            
         	// create member object sort by lastname
            while(resultSet.next()) {
               association_m_adress = new Adress(resultSet.getString("Adressextra") ,resultSet.getString("Street"), 
            		   resultSet.getString("Housenr"), resultSet.getString("PLZ"), resultSet.getString("City"), 
            		   resultSet.getString("Country"), resultSet.getString("Email"));
               association_m_phone = new Telephone(resultSet.getString("Phone"), "", "", "");
               association_m_account = new Account(resultSet.getString("Owner"), resultSet.getString("Bankname"),
            		   resultSet.getString("accountnr"), resultSet.getString("blz"), "", "");
               association_m_sc = new SpecialContribution();
               association_m_sc.setValue(resultSet.getDouble("SContribution"));
               association_m_sc.setPaymentForm(resultSet.getInt("PForm"));
               association_m_sc.setPaymentTime(resultSet.getInt("PTime"));
               association_m_sc.setSpecialPremiumDate(resultSet.getDate("SpecialBookingDate"));
               Member amember = new Member(resultSet.getInt("id"), resultSet.getInt("MemberStatus"),
            		   resultSet.getInt("Status"), null, null, association_m_adress, association_m_phone, 
            		   association_m_account, resultSet.getString("Title"), resultSet.getString("Gender"), 
            		   resultSet.getString("Firstname"), resultSet.getString("Lastname"), 
            		   new Group(resultSet.getString("GroupName"), new Contribution(resultSet.getInt("Premium"), "", "")),
            		   resultSet.getInt("GroupID"), resultSet.getInt("Payway"), resultSet.getDouble("ContributionSum"), 
            		   resultSet.getDouble("ContributionOld"), resultSet.getInt("ContributionDid"));
               amember.setComment(resultSet.getString("Comment"));
               amember.setSpecialContribution(association_m_sc);
               association_member_map.put(resultSet.getInt("id"), amember);
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
    	return association_member_map;
    }
    
    /**
     * methode to get member map for booking
     * 
     * @param start
     * @param end
     * @return
     */
    public LinkedHashMap<Integer,Member> getMemberListForBooking(Date start, Date end){
        
    	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        association_member_map = new LinkedHashMap<Integer,Member>();
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            try{
            	resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID" +
        				" LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
        				" LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id " +
        				" LEFT JOIN Contribution ON Member.id = Contribution.MemberID WHERE PTime > "+0+" AND SpecialBookingDate BETWEEN '"+start+"' AND '"+end+"' " +
        				" ORDER BY Member.LastName ASC");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
         	
            while(resultSet.next()) {
                association_m_adress = new Adress(resultSet.getString("Adressextra") ,resultSet.getString("Street"), 
             		   resultSet.getString("Housenr"), resultSet.getString("PLZ"), resultSet.getString("City"), 
             		   resultSet.getString("Country"), resultSet.getString("Email"));
                association_m_phone = new Telephone(resultSet.getString("Phone"), "", "", "");
                association_m_account = new Account(resultSet.getString("Owner"), resultSet.getString("Bankname"),
             		   resultSet.getString("accountnr"), resultSet.getString("blz"), "", "");
                association_m_sc = new SpecialContribution();
                association_m_sc.setValue(resultSet.getDouble("SContribution"));
                association_m_sc.setPaymentForm(resultSet.getInt("PForm"));
                association_m_sc.setPaymentTime(resultSet.getInt("PTime"));
                association_m_sc.setSpecialPremiumDate(resultSet.getDate("SpecialBookingDate"));
                Member amember = new Member(resultSet.getInt("id"), resultSet.getInt("MemberStatus"),
             		   resultSet.getInt("Status"), null, null, association_m_adress, association_m_phone, 
             		   association_m_account, resultSet.getString("Title"), resultSet.getString("Gender"), 
             		   resultSet.getString("Firstname"), resultSet.getString("Lastname"), 
             		   new Group(resultSet.getString("GroupName"), new Contribution(resultSet.getInt("Premium"), "", "")),
             		   resultSet.getInt("GroupID"), resultSet.getInt("Payway"), resultSet.getDouble("ContributionSum"), 
             		   resultSet.getDouble("ContributionOld"), resultSet.getInt("ContributionDid"));
                amember.setComment(resultSet.getString("Comment"));
                amember.setSpecialContribution(association_m_sc);
                association_member_map.put(resultSet.getInt("id"), amember);
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
    	return association_member_map;
    }
    
    /**
     * methode to update the special contribution
     * 
     * @param memberID
     * @param booking_date
     */
    public void updateSpecialContribution(int memberID, Date booking_date){
    	
     	int member_id = memberID;
     	
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();

            try { 
            	statement.executeUpdate("UPDATE Contribution SET SpecialBookingDate = '"+booking_date+"'" +
            			" WHERE MemberID= "+member_id+"");
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
     * methode to get the member by his account
     * 
     * @param kto
     * @param blz
     * @return
     */
    public Member getMember(long kto, long blz){
    	
    	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        String kto_nr = (new Long(kto)).toString();
        String blz_nr = (new Long(blz)).toString();
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            try{
           		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
           				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
           				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id LEFT JOIN Contribution ON Member.id = Contribution.MemberID " +
           				"WHERE accountnr = '"+kto_nr+"' AND blz = '"+blz_nr+"' ORDER BY Member.LastName ASC");
           	} catch( SQLException ex ) {
           		System.out.println(message + ex.getMessage());
           	}   
            
         	// create member object sort by lastname
            while(resultSet.next()) {
               association_m_adress = new Adress(resultSet.getString("Adressextra") ,
            		   resultSet.getString("Street"), resultSet.getString("Housenr"), 
            		   resultSet.getString("PLZ"), resultSet.getString("City"), 
            		   resultSet.getString("Country"), resultSet.getString("Email"));
               association_m_phone = new Telephone(resultSet.getString("Phone"), "", "", "");
               association_m_account = new Account(resultSet.getString("Owner"), 
            		   resultSet.getString("Bankname"), resultSet.getString("accountnr"),
            		   resultSet.getString("blz"), "", "");
               association_m_sc = new SpecialContribution();
               association_m_sc.setValue(resultSet.getDouble("SContribution"));
               association_m_sc.setPaymentForm(resultSet.getInt("PForm"));
               association_m_sc.setPaymentTime(resultSet.getInt("PTime"));
               association_m_sc.setSpecialPremiumDate(resultSet.getDate("SpecialBookingDate"));
               association_member = new Member(resultSet.getInt("id"), resultSet.getInt("MemberStatus"), 
            		   resultSet.getInt("Status"), null, null, association_m_adress, association_m_phone,
            		   association_m_account, resultSet.getString("Title"), resultSet.getString("Gender"), 
            		   resultSet.getString("Firstname"), resultSet.getString("Lastname"), 
            		   new Group(resultSet.getString("GroupName"), new Contribution(resultSet.getInt("Premium"), "", "")),
            		   resultSet.getInt("GroupID"), resultSet.getInt("Payway"),resultSet.getDouble("ContributionSum"), 
            		   resultSet.getDouble("ContributionOld"), resultSet.getInt("ContributionDid"));
               association_member.setComment(resultSet.getString("Comment"));
               association_member.setSpecialContribution(association_m_sc);
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
    	
          if(association_member != null){
        	  return association_member;
          }else{
        	  return null;
          }
    }
    
    /**
     * methode to get the member by his account
     * 
     * @param kto
     * @param blz
     * @return
     */
    public Member getMemberById(int id){
    	
    	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            try{
           		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
           				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
           				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id LEFT JOIN Contribution ON Member.id = Contribution.MemberID " +
           				"WHERE Member.id = "+id+"");
           	} catch( SQLException ex ) {
           		System.out.println(message + ex.getMessage());
           	}   
            
         	// create member object sort by lastname
            while(resultSet.next()) {
               association_m_adress = new Adress(resultSet.getString("Adressextra") ,
            		   resultSet.getString("Street"), resultSet.getString("Housenr"), 
            		   resultSet.getString("PLZ"), resultSet.getString("City"), 
            		   resultSet.getString("Country"), resultSet.getString("Email"));
               association_m_phone = new Telephone(resultSet.getString("Phone"), "", "", "");
               association_m_account = new Account(resultSet.getString("Owner"), 
            		   resultSet.getString("Bankname"), resultSet.getString("accountnr"),
            		   resultSet.getString("blz"), "", "");
               association_m_sc = new SpecialContribution();
               association_m_sc.setValue(resultSet.getDouble("SContribution"));
               association_m_sc.setPaymentForm(resultSet.getInt("PForm"));
               association_m_sc.setPaymentTime(resultSet.getInt("PTime"));
               association_m_sc.setSpecialPremiumDate(resultSet.getDate("SpecialBookingDate"));
               association_member = new Member(resultSet.getInt("id"), resultSet.getInt("MemberStatus"), 
            		   resultSet.getInt("Status"), null, null, association_m_adress, association_m_phone,
            		   association_m_account, resultSet.getString("Title"), resultSet.getString("Gender"), 
            		   resultSet.getString("Firstname"), resultSet.getString("Lastname"), 
            		   new Group(resultSet.getString("GroupName"), new Contribution(resultSet.getInt("Premium"), "", "")),
            		   resultSet.getInt("GroupID"), resultSet.getInt("Payway"),resultSet.getDouble("ContributionSum"), 
            		   resultSet.getDouble("ContributionOld"), resultSet.getInt("ContributionDid"));
               association_member.setComment(resultSet.getString("Comment"));
               association_member.setSpecialContribution(association_m_sc);
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
    	
          if(association_member != null){
        	  return association_member;
          }else{
        	  return null;
          }
    }
    
    /**
     * methode to get a member by name
     * 
     * @param firstname
     * @param lastname
     * @param kto
     * @param blz
     * @return
     */
    public Member getMemberByName(String firstname, String lastname, long kto, long blz){
    	
    	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        String kto_nr = (new Long(kto)).toString();
        String blz_nr = (new Long(blz)).toString();
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            try{
           		resultSet = statement.executeQuery("Select * from Member LEFT JOIN Adress ON Member.id = Adress.ObjectID " +
           				"LEFT JOIN Account ON Member.id=Account.ObjectID LEFT JOIN Phone ON Member.id = Phone.ObjectID " +
           				"LEFT JOIN Membergroup ON Member.GroupID = Membergroup.id LEFT JOIN Contribution ON Member.id = Contribution.MemberID " +
           				"WHERE accountnr = '"+kto_nr+"' AND blz = '"+blz_nr+"' AND Firstname = '"+firstname+"' AND Lastname = '"+lastname+"' ORDER BY Member.LastName ASC");
           	} catch( SQLException ex ) {
           		System.out.println(message + ex.getMessage());
           	}   
            
         	// create member object sort by lastname
            while(resultSet.next()) {
               association_m_adress = new Adress(resultSet.getString("Adressextra") ,
            		   resultSet.getString("Street"), resultSet.getString("Housenr"), 
            		   resultSet.getString("PLZ"), resultSet.getString("City"), 
            		   resultSet.getString("Country"), resultSet.getString("Email"));
               association_m_phone = new Telephone(resultSet.getString("Phone"), "", "", "");
               association_m_account = new Account(resultSet.getString("Owner"), 
            		   resultSet.getString("Bankname"), resultSet.getString("accountnr"),
            		   resultSet.getString("blz"), "", "");
               association_m_sc = new SpecialContribution();
               association_m_sc.setValue(resultSet.getDouble("SContribution"));
               association_m_sc.setPaymentForm(resultSet.getInt("PForm"));
               association_m_sc.setPaymentTime(resultSet.getInt("PTime"));
               association_m_sc.setSpecialPremiumDate(resultSet.getDate("SpecialBookingDate"));
               association_member = new Member(resultSet.getInt("id"), resultSet.getInt("MemberStatus"), 
            		   resultSet.getInt("Status"), null, null, association_m_adress, association_m_phone,
            		   association_m_account, resultSet.getString("Title"), resultSet.getString("Gender"), 
            		   resultSet.getString("Firstname"), resultSet.getString("Lastname"), 
            		   new Group(resultSet.getString("GroupName"), new Contribution(resultSet.getInt("Premium"), "", "")),
            		   resultSet.getInt("GroupID"), resultSet.getInt("Payway"),resultSet.getDouble("ContributionSum"), 
            		   resultSet.getDouble("ContributionOld"), resultSet.getInt("ContributionDid"));
               association_member.setComment(resultSet.getString("Comment"));
               association_member.setSpecialContribution(association_m_sc);
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
    	
          if(association_member != null){
        	  return association_member;
          }else{
        	  return null;
          }
    }
    
    /**
     * methode to delete a member
     * 
     * @param userID
     */
    public void deleteMember(int userID){
    	
         String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
         Statement statement = null;
         ResultSet resultSet = null;
            
            connect();
            
            try {
                // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
                statement = connection.createStatement();
               
                try{
                	statement.executeUpdate("DELETE FROM Member WHERE id = "+userID+"");
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
     * methode to update contribution did
     * 
     * @param cd
     * @param userID
     */
    public void updateContributionDid(int cd, int userID){
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
           
            try{
            	statement.executeUpdate("UPDATE Member SET ContributionDid= "+cd+" WHERE id = "+userID+"");
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
     * methode to update member contribution
     * 
     * @param contribution
     * @param userID
     */
    public void updateMemberContribution(double contribution, int userID){
       
    	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();

        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();

            try{
            	statement.executeUpdate("UPDATE Member SET ContributionSum = "+contribution+" WHERE id = "+userID+"");
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
     * methode to get the special contribution
     * 
     * @param userID
     * @return
     */
    public SpecialContribution getSpecialContribution(int userID){
	
    	association_m_sc = new SpecialContribution();
	
    	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
    	Statement statement = null;
    	ResultSet resultSet = null;
    
    	connect();
    
    	try {
    		// Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
    		statement = connection.createStatement();
    		try { 
    			resultSet = statement.executeQuery("Select * from Contribution WHERE MemberID = "+userID+"");
    		} catch( SQLException ex ) {
    			System.out.println(message + ex.getMessage());
    		}
        
    		while(resultSet.next()) {
                association_m_sc.setValue(resultSet.getDouble("SContribution"));
                association_m_sc.setPaymentForm(resultSet.getInt("PForm"));
                association_m_sc.setPaymentTime(resultSet.getInt("PTime"));
    		}       
        
    	}catch( SQLException ex ) {
    			System.out.println(message + ex.getMessage());
    	}finally {
            
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
    	return association_m_sc;
    }
    
    /**
     * methode to update the member status
     * 
     * @param state
     * @param userID
     */
    public void updateMemberStatus(int state, int userID){
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
           
            try{
            	statement.executeUpdate("UPDATE Member SET Status = "+state+" WHERE id = "+userID+"");
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
     * methode to update a member
     * 
     * @param aMember
     */
    public void updateMember(Member aMember){
    	
    	this.association_member = aMember;
    	this.association_m_adress = association_member.getAdress();
    	this.association_m_phone = association_member.getPhone();
    	this.association_m_account = association_member.getAccount();
    	this.association_m_sc = association_member.getSpecialContribution();
    	
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();

        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();

            try{
            	statement.executeUpdate("UPDATE Member SET Title = '"+association_member.getTitle()+"', " +
            			"Gender = '"+association_member.getGender()+"', " +
            			"Firstname = '"+association_member.getFirstName()+"', " +
            			"Lastname = '"+association_member.getLastName()+"',Status = "+association_member.getPayStatus()+" , " +
            			"MemberStatus = "+association_member.getStatus()+", GroupID = "+association_member.getGroupID()+", " +
            			"Comment = '"+association_member.getComment()+"', ContributionOld = "+association_member.getContribution()+", " +
            			"ContributionSum = "+association_member.getSumContribution()+", Payway = "+association_member.getPaymentWay()+" " +
            			"WHERE id = "+association_member.getNumber()+"");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	
            try { 
            	statement.executeUpdate("UPDATE Adress SET Adressextra = '"+association_m_adress.getAdressAdditional()+"', " +
            			"Street = '"+association_m_adress.getStreet()+"', Housenr = '"+association_m_adress.getHouseNumber()+"', " +
            			"City = '"+association_m_adress.getCity()+"', PLZ = '"+association_m_adress.getZipCode()+"', " +
            			"Country = '"+association_m_adress.getCountry()+"', " +
            			"Email = '"+association_m_adress.getEmail()+"' WHERE ObjectID = "+association_member.getNumber()+"");

            } catch( SQLException ex ) {
               System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("UPDATE Account SET Owner = '"+association_m_account.getOwner()+"', " +
            			"Bankname = '"+association_m_account.getBankName()+"', " +
            			"accountnr = '"+association_m_account.getAccountNumber()+"', " +
            			"blz = '"+association_m_account.getBankCodeNumber()+"' " +
            			"WHERE ObjectID = "+association_member.getNumber()+"");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("UPDATE Phone SET Phone = '"+association_m_phone.getPrivatePhone()+"' " +
            			"WHERE ObjectID = "+association_member.getNumber()+"");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
    		try { 
    			resultSet = statement.executeQuery("Select * from Contribution WHERE MemberID = "+association_member.getNumber()+"");
    		} catch( SQLException ex ) {
    			System.out.println(message + ex.getMessage());
    		}

    		boolean ok = false;
    		
    		while(resultSet.next()) {    				        
                ok = true;
    		}      
    		
    		if(association_member.getSpecialContribution().getPayForm() == 0 && association_member.getSpecialContribution().getSpecialPremiumDate() != null){	
    			
    			if (!ok){
    				try {
    					statement.executeUpdate("INSERT INTO Contribution (SContribution, PTime, PForm, SpecialBookingDate, MemberID) VALUES ("+association_member.getSpecialContribution().getVlaue()+", "+association_member.getSpecialContribution().getPayTime()+", "+association_member.getSpecialContribution().getPayForm()+", '"+association_member.getSpecialContribution().getSpecialPremiumDate()+"', "+association_member.getNumber()+")");
    				} catch( SQLException ex ) {
    					System.out.println(message + ex.getMessage());
    				}
    			}else{
    				try {
    					statement.executeUpdate("UPDATE Contribution SET SContribution = "+association_member.getSpecialContribution().getVlaue()+", " +
    							"PTime = "+association_member.getSpecialContribution().getPayTime()+", " +
                				"PForm = "+association_member.getSpecialContribution().getPayForm()+", " +
                				"SpecialBookingDate = '"+association_member.getSpecialContribution().getSpecialPremiumDate()+"' WHERE MemberID = "+association_member.getNumber()+"");
    				} catch( SQLException ex ) {
    					System.out.println(message + ex.getMessage());
    				} 
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
    }
    
    /**
     * methode to insert a member
     * 
     * @param aMember
     */
    public void insertMember(Member aMember){
    	
    	this.association_member = aMember;
    	this.association_m_adress = association_member.getAdress();
    	this.association_m_phone = association_member.getPhone();
    	this.association_m_account = association_member.getAccount();
 
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        
        connect();

        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            try{
            	statement.executeUpdate("INSERT INTO Member(" +
            			"Title, Gender, Firstname, Lastname, Status, MemberStatus, " +
            			"GroupID, Comment, ContributionOld, ContributionSum, Payway) " +
            			"VALUES( '"+association_member.getTitle()+"', '"+association_member.getGender()+"', '"
            			+association_member.getFirstName()+"', '"+association_member.getLastName()+"'," +
            			" "+association_member.getPayStatus()+", "+association_member.getStatus()+", " +
            			" "+association_member.getGroupID()+", '"+association_member.getComment()+"'," +
            			" "+association_member.getContribution()+", "+association_member.getSumContribution()+"," +
            			" "+association_member.getPaymentWay()+")");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	
            try{
            	resultSet = statement.executeQuery("select * from Member WHERE id = IDENTITY_VAL_LOCAL()");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
            
         	int member_id = 0;

            while(resultSet.next()) {
                member_id = resultSet.getInt("id");
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Adress(Adressextra, Street, Housenr, City," +
            			"PLZ, Country, Email, ObjectID) VALUES('"+association_m_adress.getAdressAdditional()+"',"
            			+" '"+association_m_adress.getStreet()+"', '"+association_m_adress.getHouseNumber()+"', " +
            			"'"+association_m_adress.getCity()+"', '"+association_m_adress.getZipCode()+"', '"
            			+association_m_adress.getCountry()+"', '"+association_m_adress.getEmail()+"', "+member_id+")");
            } catch( SQLException ex ) {
               System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Account(Owner, Bankname, accountnr, blz, ObjectID)" +
            			" VALUES('"+association_m_account.getOwner()+"', '"+association_m_account.getBankName()+"', " +
            			"'"+association_m_account.getAccountNumber()+"', '"+association_m_account.getBankCodeNumber()+"', " +
            			""+member_id+")");
            } catch( SQLException ex ) {
            	System.out.println(message + ex.getMessage());
            }
            
            try { 
            	statement.executeUpdate("INSERT INTO Phone(Phone, ObjectID) VALUES( '"+association_m_phone.getPrivatePhone()+"', " +
            			""+member_id+")");
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
}