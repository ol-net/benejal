
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
package mail.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import association.model.Association;
import association.model.AssociationDataTransfer;

import com.pdfjet.A4;
import com.pdfjet.Font;
import com.pdfjet.Image;
import com.pdfjet.ImageType;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.TextLine;

import member.model.DonatorWithAdress;
import member.model.Member;

/**
 * class represents pdf file 
 * 
 * @author Leonid Oldenburger
 */
public class MassMail {

	private String association_mail;
	private int association_group;
	private Association association;
	private Member association_member;
	private DonatorWithAdress association_donator;
	private TextLine mailtext;
	private Font f1;
	private Font f2;
	private Font f3;
	private Font f4;
	private Font f5;
	private Font f6;
	private Font f7;
	private Page page;
	private AssociationDataTransfer association_data_transfer;	
	private LinkedHashMap<Integer, Member> member_map;
	private LinkedHashMap<Integer, DonatorWithAdress> donator_map;
	private double x_pos;
	private double y_pos;
	private String nowMMDDYYYY;
	private Mail nMail;
	private PDF pdf;
	private Image image = null;
	private Image image_signature = null;
	private int user_id;
	
	/**
	 * constructor creates a new pdf file with mass mails
	 * 
	 * @param newMail
	 * @param date
	 * @param memGroup
	 * @param adatatrans
	 * @throws Exception
	 */
	public MassMail(Mail newMail, String date, int memGroup, int u_id) throws Exception {
		
		this.association_mail = newMail.getText();
		this.association_group = memGroup;
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.nMail = newMail;
		this.association = association_data_transfer.getAssociation();
		this.user_id = u_id;
		
        x_pos = 406;
        y_pos = 23.0;
		
        nowMMDDYYYY = newMail.getDate();

        String file = association_data_transfer.getAssociation().getDataDir()+newMail.getSubject()+"_an_"+newMail.getMemberGroup()+"_vom_"+nowMMDDYYYY+".pdf";
        
        FileOutputStream fos = new FileOutputStream(file);

        pdf = new PDF(fos);
        
        f1 = new Font(pdf, "Helvetica-Bold");
        f1.setSize(14);
        f2 = new Font(pdf, "Helvetica");
        f2.setSize(10);
        f3 = new Font(pdf, "Helvetica-BoldOblique");
        f3.setSize(18);
        f4 = new Font(pdf, "Helvetica-Bold");
        f4.setSize(8);
        f5 = new Font(pdf, "Helvetica-Bold");
        f5.setSize(18);
        f6 = new Font(pdf, "Helvetica");
        f6.setSize(8);
        f7 = new Font(pdf, "Helvetica");
        f7.setSize(8);
        
        String fileName = "logo.jpg"; 
        
		@SuppressWarnings("unused")
		FileReader fr = null;
		try {
			fr = new FileReader(fileName);
	        BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(fileName));
	        image = new Image(pdf, bis2, ImageType.JPEG);
			image.scaleBy(0.7);
		} catch (FileNotFoundException e) {}
        
        String fileNameSignature = "signature.jpg"; 
        
		try {
			fr = new FileReader(fileNameSignature);
	        BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(fileNameSignature));
	        image_signature = new Image(pdf, bis2, ImageType.JPEG);
			image_signature.scaleBy(0.7);

		} catch (FileNotFoundException e) {}
		
        if(association_group == 2){
        	donator_map = association_data_transfer.getDonatorMap(1, null);
        	mailToDonator();
        }else if(association_group == -11){
        	donator_map = association_data_transfer.getDonatorMap(4, String.valueOf(user_id));
        	mailToDonator();
        }else if(association_group == -10){
        	this.member_map = association_data_transfer.getMember(-10, String.valueOf(user_id));
        	mailToMember();
        }else{
        	this.member_map = association_data_transfer.getMember(association_group, null);
        	mailToMember();
        }
	        
        pdf.flush();
        fos.close();
        
        Runtime rt = Runtime.getRuntime();
        try{
            @SuppressWarnings("unused")
			Process p = rt.exec( "rundll32" +" " + "url.dll,FileProtocolHandler"
                     + " " + file);
        }catch (Exception e1){
              e1.printStackTrace();
        }
	}
	
	/**
	 * methode for mails to donator
	 * 
	 * @throws Exception
	 */
	public void mailToDonator() throws Exception{
		
		/**
		 * get Collection of values contained in HashMap using
		 * Collection values() method of HashMap class
		 */
		Iterator<Map.Entry<Integer, DonatorWithAdress>> i = donator_map.entrySet().iterator();
        
		while (i.hasNext()) {
			
			Map.Entry<Integer, DonatorWithAdress> entry = i.next();
			
			association_donator = entry.getValue();
			
			if(association_donator.getNumber() > 20000){
				
				page = new Page(pdf, A4.PORTRAIT);
				
				makeHeader();
		        
		        makeMail();
		        
		        makeFooter();
			}
		}
	}
	
	/**
	 * methode for mails to member
	 * 
	 * @throws Exception
	 */
	public void mailToMember() throws Exception{
		
		/**
		 * get Collection of values contained in HashMap using
		 * Collection values() method of HashMap class
		 */
		Iterator<Map.Entry<Integer,Member>> i = member_map.entrySet().iterator();
        
		while (i.hasNext()) {
			Map.Entry<Integer, Member> entry = i.next();
			
			association_member = entry.getValue();
			
			page = new Page(pdf, A4.PORTRAIT);
	        
			makeHeader();
	        
	        makeMail();
	        
	        makeFooter();
		}    
	}	
	
	/**
	 * methode to build the header
	 * 
	 * @throws Exception
	 */
	public void makeHeader() throws Exception{
		
		if(association_data_transfer.getAssociation().getLogo() == 1 && image != null || association_data_transfer.getAssociation().getLogo() == 3 && image != null){
			image.setPosition(x_pos, y_pos += 10);
			image.drawOn(page);
		}else{
			TextLine topic = new TextLine(f5);
			topic.setText(association.getName());
			topic.setPosition(x_pos, y_pos += 60);
			topic.drawOn(page);  
		}
		
        TextLine association_adress = new TextLine(f4);
        String adress = association.getName()+", "+association.getAdress().getStreet()+
        " "+association.getAdress().getHouseNumber()+", "+association.getAdress().getZipCode()+
        " "+association.getAdress().getCity(); 
        association_adress.setText(adress);
        association_adress.setPosition(85, 117);
        association_adress.drawOn(page);  
        
		TextLine mail_date = new TextLine(f2);
        mail_date.setText(association.getAdress().getCity()+", "+nowMMDDYYYY);
        mail_date.setPosition(406, 117);
        mail_date.drawOn(page);
    
        if(association_group == 2 || association_group == -11){
        	donatorAdress();
        }else{
        	memberAdress();
        }
        
        TextLine mail_subject = new TextLine(f1);  
        mail_subject.setText(nMail.getSubject());
        mail_subject.setPosition(85, 273);
        mail_subject.drawOn(page);
        
        x_pos = 406;
        y_pos = 23.0;
	}
	
	/**
	 * methode to build the footer
	 * 
	 * @throws Exception
	 */
	public void makeFooter() throws Exception{
		
		int x = 70;
		int y = 790;
		
        TextLine association_name = new TextLine(f6);  
        association_name.setText(association.getName());
        association_name.setPosition(x, y);
        association_name.drawOn(page);
        
        TextLine association_street = new TextLine(f6);  
        association_street.setText(association.getAdress().getStreet()+" "+association.getAdress().getHouseNumber());
        association_street.setPosition(x, y+=12);
        association_street.drawOn(page);
        
        TextLine association_city = new TextLine(f6);  
        association_city.setText(association.getAdress().getZipCode()+" "+association.getAdress().getCity());
        association_city.setPosition(x, y+=12);
        association_city.drawOn(page);
        
        TextLine association_leader = new TextLine(f6);  
        association_leader.setText("Vorsitzender");
        association_leader.setPosition(x+=95, y-=24);
        association_leader.drawOn(page);
        
        TextLine leader_name = new TextLine(f6);  
        leader_name.setText(association.getLeader().getFirstName()+
        		" "+association.getLeader().getLastName());
        leader_name.setPosition(x, y+=12);
        leader_name.drawOn(page);
        
        TextLine association_treasurer = new TextLine(f6);  
        association_treasurer.setText("Kasenwart");
        association_treasurer.setPosition(x+=95, y-=12);
        association_treasurer.drawOn(page);
        
        TextLine treasurer_name = new TextLine(f6);  
        treasurer_name.setText(association.getTreasurer().getFirstName()+
        		" "+association.getTreasurer().getLastName());
        treasurer_name.setPosition(x, y+=12);
        treasurer_name.drawOn(page);
        
        TextLine bank_name = new TextLine(f6);  
        bank_name.setText("Bankverbindung");
        bank_name.setPosition(x+=95, y-=12);
        bank_name.drawOn(page);
        
        TextLine blz = new TextLine(f6);  
        blz.setText("BLZ "+association.getAccount().getBankCodeNumber());
        blz.setPosition(x, y+=12);
        blz.drawOn(page);
        
        TextLine account = new TextLine(f6);  
        account.setText("Kto.-Nr "+association.getAccount().getAccountNumber());
        account.setPosition(x, y+=12);
        account.drawOn(page);
        
        TextLine telephone = new TextLine(f6);  
        telephone.setText("Tel.: "+association.getTelephone().getPrivatePhone());
        telephone.setPosition(x+=95, y-=24);
        telephone.drawOn(page);
        
        TextLine fax = new TextLine(f6);  
        fax.setText("Fax.: "+association.getTelephone().getFaxNumber());
        fax.setPosition(x, y+=12);
        fax.drawOn(page);
        
        TextLine email = new TextLine(f6);  
        email.setText("Email.: "+association.getAdress().getEmail());
        email.setPosition(x, y+=12);
        email.drawOn(page);
	}
	
	/**
	 * methode creates donator adress
	 * 
	 * @throws Exception
	 */
	public void donatorAdress() throws Exception{
        
        TextLine member_title = new TextLine(f2);
        member_title.setText(association_donator.getGender()+" "+association_donator.getTitle());
        member_title.setPosition(85, 156);
        member_title.drawOn(page);
    
        TextLine member_name = new TextLine(f2);  
        member_name.setText(association_donator.getFirstName()+" "+  association_donator.getLastName());
        member_name.setPosition(85, 168);
        member_name.drawOn(page);
    
        TextLine member_street = new TextLine(f2);  
        member_street.setText(association_donator.getAdress().getStreet()+" "+association_donator.getAdress().getHouseNumber());
        member_street.setPosition(85, 180);
        member_street.drawOn(page);
        
        TextLine member_city = new TextLine(f2);  
        member_city.setText(association_donator.getAdress().getZipCode()+" "+association_donator.getAdress().getCity());
        member_city.setPosition(85, 192);
        member_city.drawOn(page);
	}
	
	/**
	 * methode creates member adress
	 * 
	 * @throws Exception
	 */
	public void memberAdress() throws Exception{
        
        TextLine member_title = new TextLine(f2);
        member_title.setText(association_member.getGender()+" "+association_member.getTitle());
        member_title.setPosition(85, 156);
        member_title.drawOn(page);
    
        TextLine member_name = new TextLine(f2);  
        member_name.setText(association_member.getFirstName()+" "+  association_member.getLastName());
        member_name.setPosition(85, 168);
        member_name.drawOn(page);
    
        TextLine member_street = new TextLine(f2);  
        member_street.setText(association_member.getAdress().getStreet()+" "+association_member.getAdress().getHouseNumber());
        member_street.setPosition(85, 180);
        member_street.drawOn(page);
        
        TextLine member_city = new TextLine(f2);  
        member_city.setText(association_member.getAdress().getZipCode()+" "+association_member.getAdress().getCity());
        member_city.setPosition(85, 192);
        member_city.drawOn(page);
	}
	
	/**
	 * methode to create a mail
	 * 
	 * @throws Exception
	 */
	public void makeMail() throws Exception{
		
        double x_pos = 85;//610.0;
        double y_pos = 300;
		String line;
        
		BufferedReader reader = new BufferedReader(
		  new StringReader(association_mail));
		        
		try {
		  while ((line = reader.readLine()) != null) {
		          //System.out.println(line); 
		          mailtext = new TextLine(f2);  
		          mailtext.setText(line);
		          mailtext.setPosition(x_pos, y_pos += 12);
		          mailtext.drawOn(page); 
		  		 
		          //mail_list.add(mailtext);
		  }
		} catch(IOException e){
		  e.printStackTrace();
		}
		
		String author = "";
		
		if(nMail.getAuthor() == 1){
			author = association_data_transfer.getAssociation().getLeader().getFirstName() +
			" " + association_data_transfer.getAssociation().getLeader().getLastName();
	        mailtext = new TextLine(f2);  
	        mailtext.setText(author);
	        mailtext.setPosition(x_pos, y_pos += 12);
	        mailtext.drawOn(page); 
		} else if(nMail.getAuthor() == 2){
			author = association_data_transfer.getAssociation().getTreasurer().getFirstName() + 
			" " + association_data_transfer.getAssociation().getTreasurer().getLastName();
	        mailtext = new TextLine(f2);  
	        mailtext.setText(author);
	        mailtext.setPosition(x_pos, y_pos += 12);
	        mailtext.drawOn(page); 
		}	
		
		if(association_data_transfer.getAssociation().getLogo() == 2 && image_signature != null || association_data_transfer.getAssociation().getLogo() == 3 && image_signature != null){
			image_signature.setPosition(x_pos, y_pos += 20);
			image_signature.drawOn(page);
		}
        
		TextLine text5 = new TextLine(f7);  
        String row5 = "__________________________________________________";
        text5.setText(row5);
        text5.setPosition(x_pos, y_pos += 50);
        text5.drawOn(page);
        
		TextLine text6 = new TextLine(f7);  
        String row6 = "(Ort, Datum und Unterschrift)";
        text6.setText(row6);
        text6.setPosition(x_pos, y_pos+=12);
        text6.drawOn(page);
	}
}
