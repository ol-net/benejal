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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * db connection
 * 
 * @author Leonid Oldenburger
 */
public class DBAccess {

    /**
     * Verbindung zur Datenbank
     */
    protected Connection connection;

    /**
     * JDBC-Treiber-Name. Muss im Klassenpfad sein.
     */
    protected String DRIVER 
        = "org.apache.derby.jdbc.EmbeddedDriver";
    
    /**
     * Verbindungs-URL. Erstellt beim ersten Aufruf eine neue Datenbank.
     */
    protected String URL 
        = "jdbc:derby:derbydbs;create=true;" +
        		"dataEncryption=true;bootPassword=password";
	
	public DBAccess(){
        // Treiber laden
        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex ) {
            System.out.println("Der JDBC-Treiber konnte nicht " +
                    "geladen werden.");
            System.exit(1);
        }
	}
	
    /**
     * Verbindung zur Datenbank herstellen. 
     */
    public void connect() {
        
        // Verbindung herstellen
        try {
            connection = DriverManager.getConnection(URL);
        } catch( SQLException ex ) {
            System.out.println("Die Verbindung zur Datenbank konnte " +
                    "nicht hergestellt werden. " +
                    "Die Fehlermeldung lautet: " + ex.getMessage() );
            System.exit(1);
        }
    }
    
    /**
     * Verbindung trennen
     */
    public void disconnect() {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch( SQLException ex ) {
            System.out.println("Die Verbindung zur Datenbank " +
                    "konnte nicht geschlossen werden. " +
                    "Die Fehlermeldung lautet: " + ex.getMessage() );
            System.exit(1);
        }
    }
	
}
