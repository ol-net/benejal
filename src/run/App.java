
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
package run;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileReader;

import moneybook.model.KassenBuch;

import association.model.AssociationDataTransfer;
import association.view.MainMenu;
import association.view.SecurityView;

import register.view.RegisterStartWindow;

/**
 * class to start the application
 */
public class App {
	
	/**
	 *  methode start the app
	 * 
	 * @param args
	 */
	public static void main(String args[]){
		
		int width = 550;
		int height = 520;
		Font font_style = new Font("mono", Font.BOLD, 12);
		
		int password = 0;
		
		// database transfer
		AssociationDataTransfer association_data_transfer = new AssociationDataTransfer();
		KassenBuch money_book = new KassenBuch("jdbc:derby:.//derbydbs;create=true");
		
		// check if table are created
		if (association_data_transfer.showAssociationTable()){
			password = association_data_transfer.getAssociation().getPassword();
		}
		
		//start registration
		if (!association_data_transfer.showAssociationTable()){
			new RegisterStartWindow(height, width, font_style, money_book, association_data_transfer);
		}else{
			if (password==1){
				@SuppressWarnings("unused")
				FileReader fr = null;
				try {
					fr = new FileReader("secure.dat");
					new SecurityView(money_book, association_data_transfer);
				} catch (FileNotFoundException e) {
					new MainMenu(height, width, font_style, money_book, association_data_transfer);
				}
			}
			else{
			new MainMenu(height, width, font_style, money_book, association_data_transfer);
			}
		}
	}
}