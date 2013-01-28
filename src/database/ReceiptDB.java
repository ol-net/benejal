
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

import mail.model.Receipt;

/**
 * class represents receipt connection with the database
 * 
 * @author Leonid Oldenburger
 */
public class ReceiptDB extends DBAccess{

	private LinkedHashMap<Integer, Receipt> receipt_map;
	
	/**
	 * methode to get a receipt date
	 * 
	 * @param group
	 * @return
	 */
	public String getReceiptDate(String group){
		
    	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        String date = null;
    	
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
         	
            try{
            	resultSet = statement.executeQuery("Select * from Receipt WHERE MemberGroup = '"+group+"' ORDER BY id ASC");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	
         	 while(resultSet.next()) {
         		 date = resultSet.getString("Date");
         	 }
//         	System.out.println(date);
//            
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
		
		 return date;
	}
	
	/**
	 * methode to get a receipt
	 * 
	 * @return
	 */
    public LinkedHashMap<Integer, Receipt> getReceipt(){
    	
    	
    	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        receipt_map = new LinkedHashMap<Integer, Receipt>();
    	
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
         	
            try{
            	resultSet = statement.executeQuery("Select * from Receipt ORDER BY id DESC");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	Receipt receipt;
            while(resultSet.next()) {
            	receipt = new Receipt(resultSet.getInt("Expense"), resultSet.getInt("BaseOne"), resultSet.getInt("BaseTwo"), resultSet.getString("ObjectOne"), resultSet.getString("ObjectTwo"), resultSet.getString("ObjectThree"));
            	receipt.setDate(resultSet.getString("Date"));
            	receipt.setMemberGroup(resultSet.getString("MemberGroup"));
            	receipt_map.put(resultSet.getInt("id"), receipt);
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
    	return receipt_map;
    }
    
    public void updateReceipt(Receipt newreceipt){
        
    	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        
        String object_one = newreceipt.getObject1();

        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            try{
            	statement.executeUpdate("UPDATE Receipt SET ObjectOne = '"+object_one+"' WHERE id = 1");
         	} catch( SQLException ex ) {
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
    
    /**
     * methode to insert a new receipt
     * 
     * @param newreceipt
     * @throws SQLException
     */
    public void insertReceipt(Receipt newreceipt) throws SQLException{
    	
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        
        String date = newreceipt.getDate();
        String member = newreceipt.getMemberGroup();
        int expense = newreceipt.getExpense();
        int base1 = newreceipt.getBase1();
        int base2 = newreceipt.getBase2();
        String object_one = newreceipt.getObject1();
        String object_two = newreceipt.getObject2();
        String object_three = newreceipt.getObject3();
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            try{
            	statement.executeUpdate("INSERT INTO Receipt(Date, MemberGroup, Expense, BaseOne, BaseTwo, ObjectOne, ObjectTwo, ObjectThree) VALUES('"+date+"', '"+member+"', "+expense+", "+base1+", "+base2+", '"+object_one+"', '"+object_two+"', '"+object_three+"')");
         	} catch( SQLException ex ) {
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
