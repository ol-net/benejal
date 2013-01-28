
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

import mail.model.Mail;

/**
 * class represents mail database connection
 * 
 * @author Leonid Oldenburger
 */
public class MailDB extends DBAccess{

	private LinkedHashMap<Integer, Mail> mail_map;
	
	
	/**
	 * methode returns mail map
	 * 
	 * @return
	 */
    public LinkedHashMap<Integer, Mail> getMail(){
    	
    	
    	String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        ResultSet resultSet = null;
        mail_map = new LinkedHashMap<Integer,Mail>();
    	
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
         	
            try{
            	resultSet = statement.executeQuery("Select * from Mail ORDER BY id DESC");
         	} catch( SQLException ex ) {
         		System.out.println(message + ex.getMessage());
         	}
         	
            while(resultSet.next()) {
            	mail_map.put(resultSet.getInt("id"), new Mail(resultSet.getString("Subject"), resultSet.getString("Text"), resultSet.getString("Date"), resultSet.getString("MemberGroup"), 0));
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
         
    	return mail_map;
    }
    
    /**
     * methode to insert a new mail object
     * 
     * @param membermail
     * @throws SQLException
     */
    public void insertMail(Mail membermail) throws SQLException{
    	
        String message = "Datenbankfehler. Die Fehlermeldung lautet: ";
        Statement statement = null;
        
        String subject = membermail.getSubject();
        String date = membermail.getDate();
        String member = membermail.getMemberGroup();
        String text = membermail.getText();
        
        connect();
        
        try {
            // Benötige Ressourcen für eine SQL-Anweisung bereitstellen 
            statement = connection.createStatement();
            
            try{
            	statement.executeUpdate("INSERT INTO Mail(Subject, Text, Date, MemberGroup) VALUES('"+subject+"', '"+text+"', '"+date+"', '"+member+"')");
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
