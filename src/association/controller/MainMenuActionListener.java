
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
package association.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import association.model.AssociationDataTransfer;
import association.model.OpenFile;
import association.view.AssociationUpdatePanel;
import association.view.ConfigFrame;
import association.view.MainMenu;
import association.view.MainMenuPanel;
import association.view.SoftwareInfo;
import association.view.VereinsMenueBar;

import member.model.Member;
import member.model.DonatorWithAdress;
import member.view.ManageMembers;
import member.view.MemberAndDonatorFrame;
import moneybook.model.KassenBuch;
import moneybook.view.MoneyBookFrame;

/**
 * ActionListener for the main menu
 * 
 * @author Leonid Oldenburger
 */
public class MainMenuActionListener implements ActionListener{

	private MainMenu menu;
	private MainMenuPanel mainmenupanel;
	private ManageMembers memberstabbedpane;
	private AssociationUpdatePanel association_data_update;
	
	private AssociationDataTransfer association_data_transfer;
	
	private KassenBuch money_book;
	private MoneyBookFrame moneyBookFrame;
	
	private MemberAndDonatorFrame memberframe;
	private ConfigFrame configframe;
	
	@SuppressWarnings("unused")
	private VereinsMenueBar menuBar;
	
	/**
	 * constructor creates actionlistener for the main menu
	 * 
	 * @param hMenue
	 * @param hMenuePanel
	 * @param adatatrans
	 * @param money_book
	 * @param mBar
	 */
	public MainMenuActionListener(MainMenu hMenue, MainMenuPanel hMenuePanel, AssociationDataTransfer adatatrans, KassenBuch money_book, VereinsMenueBar mBar) {
		
		this.association_data_transfer = adatatrans;
		this.menu = hMenue;
		this.mainmenupanel = hMenuePanel;
		this.money_book = money_book;
		this.menuBar = mBar;
	}
	/**
	 * methode to handle events
	 */
	public void actionPerformed(ActionEvent event) {
//		
//        System.out.println("Menu item [" + event.getActionCommand() +
//        "] was pressed.");
		
        boolean mem = false;
        boolean config = false;
        boolean mbook = false;
        boolean end = false;
        int tab = 0;
        
        if ("Benutzerhandbuch" == event.getActionCommand()){
			try {
		        Runtime rt = Runtime.getRuntime();
		        try{
		            @SuppressWarnings("unused")
					Process p = rt.exec( "rundll32" +" " + "url.dll,FileProtocolHandler"
		                     + " Benutzerhandbuch.pdf");
		        }catch (Exception e1){
		              e1.printStackTrace();
		        }
			} catch (Exception e) {
			}
        }  
        
        if ("Über" == event.getActionCommand()){
			try {
				new SoftwareInfo();
			} catch (Exception e) {
			}
        }   
        
        if ("Datei öffnen" == event.getActionCommand()){
			try {
				new OpenFile(association_data_transfer);
			} catch (Exception e) {
			}
        }   
        
        if ("Programm beenden" == event.getActionCommand()){
        	end = true;
        }
        
        // member shortcut
        if ("Mitglieder anzeigen" == event.getActionCommand()){
        	mem = true;
        	tab = 0;
        }else if("Mitglied erfassen" == event.getActionCommand()){
        	mem = true;
        	tab = 1;
        }else if("Spender anzeigen" == event.getActionCommand()){
        	mem = true;
        	tab = 2;
        }else if("Spender erfassen" == event.getActionCommand()){
        	mem = true;
        	tab = 3;
        }else if("Schriftverkehr" == event.getActionCommand()){
        	mem = true;
        	tab = 4;
        }

        // moneybook shortcut
        if("Übersicht" == event.getActionCommand()){
        	mbook = true;
        	tab = 0;
        }else if("Beiträge einziehen" == event.getActionCommand()){
        	mbook = true;
        	tab = 1;
        }else if("Kontoauszug" == event.getActionCommand()){
        	mbook = true;
        	tab = 2;
        }else if("Zahlung" == event.getActionCommand()){
        	mbook = true;
        	tab = 3;
        }else if("Projekte" == event.getActionCommand()){
        	mbook = true;
        	tab = 4;
        }else if("Jahresabschluss" == event.getActionCommand()){
        	mbook = true;
        	tab = 5;
        }
        
        // config shortcut
        if("Basisdaten" == event.getActionCommand()){
        	config = true;
        	tab = 0;
        }else if("Administration" == event.getActionCommand()){
        	config = true;
        	tab = 1;
        }else if("Finanzen" == event.getActionCommand()){
        	config = true;
        	tab = 2;
        }else if("Mitgliedergruppen" == event.getActionCommand()){
        	config = true;
        	tab = 3;
        }else if("Konfiguration" == event.getActionCommand()){
        	config = true;
        	tab = 4;
        }else if("Sicherheit" == event.getActionCommand()){
        	config = true;
        	tab = 5;
        }
        
		// event member button
		if (this.mainmenupanel.getMemberButton() == event.getSource() || mem) {
				
			try {
				if (this.memberframe == null) {
					memberframe = new MemberAndDonatorFrame(800, 560);
					
					this.memberstabbedpane = new ManageMembers(memberframe, tab, new Member(), new DonatorWithAdress(), money_book, association_data_transfer);
					
//					UIManager.put("TabbedPane.contentAreaColor", Color.black);
//					UIManager.put("TabbedPane.contentAreaColor", Color.black);
					
					memberframe.addTabbedPanel(memberstabbedpane);
				}
				else {
					this.memberframe.setVisible(true);
				}
			} catch (Exception e) {
			}
			mem = false;
			tab = 0;
		}
		
		// event moneybook button
		if (this.mainmenupanel.getMoneyBookButton() == event.getSource() || mbook) {
			
			if (this.moneyBookFrame == null) {

				this.moneyBookFrame = new MoneyBookFrame(800, 600, this.money_book, this.association_data_transfer);

			}
			else {
				this.moneyBookFrame.setVisible(true);
				this.moneyBookFrame.toFront();
			}
			
			
			try {
				//menu.removePanel();
			} catch (Exception e) {
			}
		}
		
		// event config button
		if (this.mainmenupanel.getConfigButton() == event.getSource() || config) {
		
			if(association_data_update == null){
				
				configframe = new ConfigFrame();
				
				this.association_data_update = new AssociationUpdatePanel(configframe, money_book, association_data_transfer);
				
				association_data_update.setSelectedIndex(tab);
				
				configframe.addTabbedPanel(association_data_update);
				
			}else{
				configframe.setVisibleTrue();
			}
			
			config = false;
			tab = 0;
		}
		
		// event end button
		if (this.mainmenupanel.getEndButton() == event.getSource() || end) {
			
			try {
				this.menu.schliessen();
			} catch (Exception e) {
			}
			end = false;
		}
	}
}
