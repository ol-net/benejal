
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
package association.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JPasswordField;

import association.view.UpdateAssociationDatabase;

/**
 * class represents a security class
 * 
 * @author Leonid Oldenburger
 */
public class Security {
	
	private UpdateAssociationDatabase association_config;
	private JPasswordField password;
	
	/**
	 * constructor 
	 * 
	 * @param pass
	 */
	public Security(JPasswordField pass){
		this.password = pass;
	}
	
	/**
	 * 
	 * @param aConfig
	 */
	public Security(UpdateAssociationDatabase aConfig){
		this.association_config = aConfig;
		this.password = association_config.getPassword();
	}
	
	/**
	 * methode generete a MD5-Hashcode and return
	 * 
	 * @return 
	 */
	public String generateCode(){
		
		/* Berechnung */
        MessageDigest md5 = null;
        
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        md5.reset();
        md5.update(new String(password.getPassword()).getBytes());
        byte[] result = md5.digest();

        /* Ausgabe */
        StringBuffer hexString = new StringBuffer();
        for (int i=0; i<result.length; i++) {
            hexString.append(Integer.toHexString(0xFF & result[i]));
        }
        
        return hexString.toString();
	}
	
	/**
	 * set code
	 */
	public void setCode(){

		String hashcode = generateCode();
		
        //if (!association_config.getDirText().getText().isEmpty()){
            File datei = new File("secure.dat");

            FileWriter schreiber = null;
			try {
				schreiber = new FileWriter(datei);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            try {
				schreiber.write(hashcode);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            try {
				schreiber.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//        }
	}
	
	/**
	 * get code
	 * 
	 * @return
	 */
	public String getCode(){
		
		String code = "";
		
		FileReader fr = null;
		
		try {
			fr = new FileReader("secure.dat");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		
		String c = "";
		
		try {
			while((c = br.readLine()) != null) {
				code = c;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return code;
	}
	
	/**
	 * compare the password
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean comparePassword(){
		
		boolean ok = false;
		
		if (new String(association_config.getPassword().getText()).equals(new String (association_config.getPasswordW().getText()))){
			ok = true;
		}
		
		return ok; 
	}
}
