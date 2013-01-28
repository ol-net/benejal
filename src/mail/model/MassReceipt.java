
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import association.model.Association;
import association.model.AssociationDataTransfer;
import association.model.Interval;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.pdfjet.Cell;
import com.pdfjet.Font;
import com.pdfjet.Image;
import com.pdfjet.ImageType;
import com.pdfjet.Letter;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.Table;
import com.pdfjet.TextLine;

import member.model.DonatorWithAdress;
import member.model.Member;
import moneybook.model.Beitrag;
import moneybook.model.KassenBuch;
import moneybook.model.Spende;

/**
 * class represents a creation of receipt
 * 
 * @author Leonid Oldenburger
 */
public class MassReceipt{

	@SuppressWarnings("unused")
	private String association_receipt;
	
	private int association_group;
	
	private Association association;
	
	private Member association_member;
	
	private DonatorWithAdress association_donator;
	
	@SuppressWarnings("unused")
	private TextLine mailtext;
	
	private Font f1;
	private Font f2;
	private Font f3;
	private Font f4;
	private Font f5;
	private Font f6;
	private Font f7;
	private Font f8;
	private Font f9;
	private Page page;
	
	private Receipt new_receipt;
	
	private KassenBuch money_book;
	
    private double x_pos = 406;
    private double y_pos = 23.0;
    
    private String nowMMDDYYYY;
    
	private List<Beitrag> paylist;
	private List<Spende> donation_list;
	
	private AssociationDataTransfer association_data_transfer;	
	private LinkedHashMap<Integer, Member> member_map;
	private LinkedHashMap<Integer, DonatorWithAdress> donator_map;

	private DecimalFormat df;
	private String people_group;
	private PDF pdf;
		
	@SuppressWarnings("unused")
	private Date last_date_receip_for_this_group;
	@SuppressWarnings("unused")
	private Date today;
	
	private DateFormat datef;
	
	private int x = 85;
	private int y = 500;
	
	private Image image = null;
	private Image image_signature = null;
	private StringBuilder vMMDDYYYY;
	private StringBuilder aMMDDYYYY;
	
	private Interval interval;
	
	private Date beginnDate;
	private Date endDate;
	private String member_id;
	
	/**
	 * constructor creates a pdf file
	 * 
	 * @param newReceipt
	 * @param date
	 * @param group
	 * @param memGroup
	 * @param adatatrans
	 * @param moneyBook
	 * @throws Exception
	 */
	public MassReceipt(Receipt newReceipt, String group, int memGroup, int mem_id) throws Exception {
		
		this.new_receipt = newReceipt;
		this.association_group = memGroup;
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
		this.people_group = group;
		this.member_id = String.valueOf(mem_id);

		this.today = new Date();

		if(association_group > 1)
			association_group+=1;

		datef = new SimpleDateFormat("dd.MM.yyyy"); 
		
        SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
        vMMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(association_data_transfer.getAssociation().getFinacneOffice().getV()));
        aMMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(association_data_transfer.getAssociation().getFinacneOffice().getA()));

		this.interval = new Interval(association_data_transfer);
		
		Calendar calendarNowDate = Calendar.getInstance();
		calendarNowDate.setTime(new Date());
		
		if(calendarNowDate.get(Calendar.MONTH) == 0 || calendarNowDate.get(Calendar.MONTH) == 1 || calendarNowDate.get(Calendar.MONTH) == 2){
			
			String start_string_date = "01.01." + new Integer(calendarNowDate.get(Calendar.YEAR)-1).toString();
			String end_string_date = "31.12." + new Integer(calendarNowDate.get(Calendar.YEAR)-1).toString();
			
			Date sdate = new Date();
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy"); 
			
			try{
				sdate = df.parse(start_string_date);             
			} catch (ParseException e) {}
			
			Date edate = new Date();
			
			try{
				edate = df.parse(end_string_date);             
			} catch (ParseException e) {}
			
			this.beginnDate = sdate;
			this.endDate = edate;
			
		}else{
			this.beginnDate = interval.getStartDate();
			this.endDate = interval.getEndDate();
		}

		if(association_data_transfer.getReceiptDate(people_group) != null){
			try{
				this.last_date_receip_for_this_group = datef.parse(association_data_transfer.getReceiptDate(people_group));             
			} catch (ParseException e) { 
			} 
		}else{
			this.last_date_receip_for_this_group = interval.getStartDate();
		}

		this.association = association_data_transfer.getAssociation();
		
		
		this.df = new DecimalFormat("#0.00");
				
        this.nowMMDDYYYY = newReceipt.getDate();
        String file = association_data_transfer.getAssociation().getDataDir()+"Spendenbescheinigung_an_"+people_group+"_vom_"+nowMMDDYYYY+".pdf";

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
        f6.setSize(6);
        f7 = new Font(pdf, "Helvetica-Bold");
        f7.setSize(6);
        f8 = new Font(pdf, "Helvetica-Bold");
        f8.setSize(10);
        f9 = new Font(pdf, "Helvetica");
        f9.setSize(8);
        
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
        		
        if(association_group == 1){
        	this.donator_map = association_data_transfer.getDonatorMap(1, null);
        	handleDonator();
        }else if(association_group == -1){
        	int id = Integer.parseInt(member_id);
        	if(id < 20000){
        		this.member_map = association_data_transfer.getMember(-10, member_id);
        		handleMember();
        	}else if(id >= 20000){
            	this.donator_map = association_data_transfer.getDonatorMap(4, member_id);
            	handleDonator();
        	}
        }else{
        	this.member_map = association_data_transfer.getMember(association_group, null);
        	handleMember();
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
	 * method handle member
	 * 
	 * @throws Exception
	 */
	public void handleMember() throws Exception{
		
		/**
		 * get Collection of values contained in HashMap using
		 * Collection values() method of HashMap class
		 */
		Iterator<Map.Entry<Integer,Member>> i = member_map.entrySet().iterator();
        
		while (i.hasNext()) {
			
			Map.Entry<Integer, Member> entry = i.next();
			
			association_member = entry.getValue();
			
			page = new Page(pdf, Letter.PORTRAIT);

			makeHeader();
	        
			makeContributionView();  
			
			makeMainPart();
			
			x = 85;
			y = 500;
	        makeFooter();
		}   
	}
	
	/**
	 * methode handle donator
	 * 
	 * @throws Exception
	 */
	public void handleDonator() throws Exception{
		
		/**
		 * get Collection of values contained in HashMap using
		 * Collection values() method of HashMap class
		 */
		Iterator<Map.Entry<Integer, DonatorWithAdress>> i = donator_map.entrySet().iterator();

		while (i.hasNext()) {
			
			Map.Entry<Integer, DonatorWithAdress> entry = i.next();
			
			association_donator = entry.getValue();
			
			if(association_donator.getNumber() > 20000){
			
				page = new Page(pdf, Letter.PORTRAIT);

				makeHeader();

				makeContributionView();  

				makeMainPart();
			
				x = 85;
				y = 500;
				makeFooter();
			}
		}   
	}
	
	/**
	 * method to make the header
	 * 
	 * @throws Exception
	 */
	public void makeHeader() throws Exception{
		
		TextLine topic = new TextLine(f5);
		
		if(association_data_transfer.getAssociation().getLogo() == 1 && image != null || association_data_transfer.getAssociation().getLogo() == 3 && image != null){
			image.setPosition(x_pos, y_pos += 10);
			image.drawOn(page);
		}else{
			topic.setText(association.getName());
			topic.setPosition(x_pos, y_pos += 60);
			topic.drawOn(page);  
		}
        
        int x = 85;
        int y = 117;
        
        TextLine association_adress = new TextLine(f4);
        String adress = association.getName()+", "+association.getAdress().getStreet()+
        " "+association.getAdress().getHouseNumber()+", "+association.getAdress().getZipCode()+
        " "+association.getAdress().getCity(); 
        association_adress.setText(adress);
        association_adress.setPosition(x, y);
        association_adress.drawOn(page);  
    
        TextLine mail_date = new TextLine(f2);
        mail_date.setText(association.getAdress().getCity()+", "+nowMMDDYYYY);
        mail_date.setPosition(406, y);
        mail_date.drawOn(page);
        
        if(association_group == 1){
        	y = donatorData(x, y);
        }else if(association_group == -1){
    		int id = Integer.parseInt(member_id);
    		if(id < 20000){
                y = memberData(x, y);
    		}else if(id >= 20000){
            	y = donatorData(x, y);
    		}
        }else{
            y = memberData(x, y);
        }
 
        
        TextLine mail_subject = new TextLine(f1);  
        mail_subject.setText("Bestätigung über Geldzuwendungen/Mitgliedsbeitrag");
        mail_subject.setPosition(x, y+=81);
        mail_subject.drawOn(page);
        
        TextLine text = new TextLine(f2);  
        String row1 = "im Sinne des § 10b des Einkommensteuergesetzes " +
        		"an eine der in § 5 Abs. 1 Nr. 9 des ";
        text.setText(row1);
        text.setPosition(x, y+=12);
        text.drawOn(page);
        
        TextLine text2 = new TextLine(f2);  
        String row2 = "Körperschaftssteuergesetzes bezeichneten Körperschaften, " +
        		"Personenvereinigungen oder";
        text2.setText(row2);
        text2.setPosition(x, y+=12);
        text2.drawOn(page);
        
        TextLine text3 = new TextLine(f2);  
        String row3 = "Vermögensmassen ";
        text3.setText(row3);
        text3.setPosition(x, y+=12);
        text3.drawOn(page);
        
        if(association_group == 1){
	        TextLine text4 = new TextLine(f2);  
	        String row4 = "Name und Anschrift des Zuwendenden: "+association_donator.getFirstName()+
	        			  " "+association_donator.getLastName()+", "+association_donator.getAdress().getStreet()+
	        			  " "+association_donator.getAdress().getHouseNumber()+", "+association_donator.getAdress().getZipCode()+
	        			  " "+association_donator.getAdress().getCity();
	        text4.setText(row4);
	        text4.setPosition(x, y+=20);
	        text4.drawOn(page);
        }else if(association_group == -1){
    		int id = Integer.parseInt(member_id);
    		if(id < 20000){
    	        TextLine text4 = new TextLine(f2);  
    	        String row4 = "Name und Anschrift des Zuwendenden: "+association_member.getFirstName()+
    	        			  " "+association_member.getLastName()+", "+association_member.getAdress().getStreet()+
    	        			  " "+association_member.getAdress().getHouseNumber()+", "+association_member.getAdress().getZipCode()+
    	        			  " "+association_member.getAdress().getCity();
    	        text4.setText(row4);
    	        text4.setPosition(x, y+=20);
    	        text4.drawOn(page);
    		}else if(id >= 20000){
    	        TextLine text4 = new TextLine(f2);  
    	        String row4 = "Name und Anschrift des Zuwendenden: "+association_donator.getFirstName()+
    	        			  " "+association_donator.getLastName()+", "+association_donator.getAdress().getStreet()+
    	        			  " "+association_donator.getAdress().getHouseNumber()+", "+association_donator.getAdress().getZipCode()+
    	        			  " "+association_donator.getAdress().getCity();
    	        text4.setText(row4);
    	        text4.setPosition(x, y+=20);
    	        text4.drawOn(page);
    		}
        }else{
            TextLine text4 = new TextLine(f2);  
            String row4 = "Name und Anschrift des Zuwendenden: "+association_member.getFirstName()+
            			  " "+association_member.getLastName()+", "+association_member.getAdress().getStreet()+
            			  " "+association_member.getAdress().getHouseNumber()+", "+association_member.getAdress().getZipCode()+
            			  " "+association_member.getAdress().getCity();
            text4.setText(row4);
            text4.setPosition(x, y+=20);
            text4.drawOn(page);
        }
        
        x_pos = 406;
        y_pos = 23.0;
	}
	
	/**
	 * method creates member address
	 * 
	 * @param x
	 * @param y
	 * @return
	 * @throws Exception
	 */
	public int memberData(int x, int y) throws Exception{
		
        TextLine member_title = new TextLine(f2);
        member_title.setText(association_member.getGender()+" "+association_member.getTitle());
        member_title.setPosition(x, y+=39);
        member_title.drawOn(page);
    
        TextLine member_name = new TextLine(f2);  
        member_name.setText(association_member.getFirstName()+" "+  association_member.getLastName());
        member_name.setPosition(x, y+=12);
        member_name.drawOn(page);
    
        TextLine member_street = new TextLine(f2);  
        member_street.setText(association_member.getAdress().getStreet()+" "+association_member.getAdress().getHouseNumber());
        member_street.setPosition(x, y+=12);
        member_street.drawOn(page);
        
        TextLine member_city = new TextLine(f2);  
        member_city.setText(association_member.getAdress().getZipCode()+" "+association_member.getAdress().getCity());
        member_city.setPosition(x, y+=12);
        member_city.drawOn(page);
        
        return y;
	}
	
	/**
	 * method creates donator address
	 * 
	 * @param x
	 * @param y
	 * @return
	 * @throws Exception
	 */
	public int donatorData(int x, int y) throws Exception{
        
		TextLine member_title = new TextLine(f2);
        member_title.setText(association_donator.getGender()+" "+association_donator.getTitle());
        member_title.setPosition(x, y+=39);
        member_title.drawOn(page);
    
        TextLine member_name = new TextLine(f2);  
        member_name.setText(association_donator.getFirstName()+" "+  association_donator.getLastName());
        member_name.setPosition(x, y+=12);
        member_name.drawOn(page);
    
        TextLine member_street = new TextLine(f2);  
        member_street.setText(association_donator.getAdress().getStreet()+" "+association_donator.getAdress().getHouseNumber());
        member_street.setPosition(x, y+=12);
        member_street.drawOn(page);
        
        TextLine member_city = new TextLine(f2);  
        member_city.setText(association_donator.getAdress().getZipCode()+" "+association_donator.getAdress().getCity());
        member_city.setPosition(x, y+=12);
        member_city.drawOn(page);
        
        return y;
	}
	
	/**
	 * method makes contribution view
	 * 
	 * @throws Exception
	 */
	public void makeContributionView() throws Exception{

		Table table = new Table(f1, f2);
		
		if(association_group == 1){
			this.donation_list = money_book.getDonations(association_donator.getNumber());
		}else if(association_group == -1){
        	int id = Integer.parseInt(member_id);
        	if(id < 20000){
    			this.donation_list = money_book.getDonations(association_member.getNumber());
    			this.paylist = money_book.getMemberPremiums(association_member.getNumber());
        	}else if(id >= 20000){
    			this.donation_list = money_book.getDonations(association_donator.getNumber());
        	}
		}else{
			this.donation_list = money_book.getDonations(association_member.getNumber());
			this.paylist = money_book.getMemberPremiums(association_member.getNumber());
		}
		
		List<List<Cell>> tableData = new ArrayList<List<Cell>>();		
		List<Cell> row = new ArrayList<Cell>();
		 
		row.add(new Cell(f2, "Betrag der Zuwendung - in Ziffern -"));
		row.add(new Cell(f2, "- in Buchstaben -"));
		row.add(new Cell(f2, "Tag der Zuwendung:"));
        
		tableData.add(row);
		
        double sum = 0;
        
        if(association_group == 1){
        	for (Spende item: donation_list) {
        		
				List<Cell> row2 = new ArrayList<Cell>();

				if(item.getDatum().getTime() >= beginnDate.getTime() && item.getDatum().getTime() <= endDate.getTime()){
					row2.add(new Cell(f2, df.format(item.getBetrag())+ "  " +association_data_transfer.getAssociation().getFinacneOffice().getCurrency()));
					row2.add(new Cell(f2, new RuleBasedNumberFormat(RuleBasedNumberFormat.SPELLOUT).format(item.getBetrag())));
				
					SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
					StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(item.getDatum()));
				 
					row2.add(new Cell(f2, MMDDYYYY.toString()));
			    
					tableData.add(row2);
			    
					sum = sum + item.getBetrag();
				}
        	}
        }else if(association_group == -1){
        	
        	int id = Integer.parseInt(member_id);
        	
        	if(id < 20000){
    			for (Beitrag item: paylist) {
    				List<Cell> row2 = new ArrayList<Cell>();
    				if(item.getDatum().getTime() >= beginnDate.getTime() && item.getDatum().getTime() <= endDate.getTime()){
    					row2.add(new Cell(f2, df.format(item.getBetrag())+ "  " +association_data_transfer.getAssociation().getFinacneOffice().getCurrency()));
    					row2.add(new Cell(f2, new RuleBasedNumberFormat(RuleBasedNumberFormat.SPELLOUT).format(item.getBetrag())));
    				
    					SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
    					StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(item.getDatum()));
    				 
    					row2.add(new Cell(f2, MMDDYYYY.toString()));
    			    
    					tableData.add(row2);
    			    
    					sum = sum + item.getBetrag();
    				}
    			}
            	for (Spende item: donation_list) {
            		
            			List<Cell> row2 = new ArrayList<Cell>();

        				if(item.getDatum().getTime() >= beginnDate.getTime() && item.getDatum().getTime() <= endDate.getTime()){
            				row2.add(new Cell(f2, df.format(item.getBetrag())+ "  " +association_data_transfer.getAssociation().getFinacneOffice().getCurrency()));
            				row2.add(new Cell(f2, new RuleBasedNumberFormat(RuleBasedNumberFormat.SPELLOUT).format(item.getBetrag())));
    				
            				SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
            				StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(item.getDatum()));
    				 
            				row2.add(new Cell(f2, MMDDYYYY.toString()));
    			    
            				tableData.add(row2);
    			    
            				sum = sum + item.getBetrag();
            			}
            	}
        	}else if(id >= 20000){
            	for (Spende item: donation_list) {
            		
    				List<Cell> row2 = new ArrayList<Cell>();

    				if(item.getDatum().getTime() >= beginnDate.getTime() && item.getDatum().getTime() <= endDate.getTime()){
    					row2.add(new Cell(f2, df.format(item.getBetrag())+ "  " +association_data_transfer.getAssociation().getFinacneOffice().getCurrency()));
    					row2.add(new Cell(f2, new RuleBasedNumberFormat(RuleBasedNumberFormat.SPELLOUT).format(item.getBetrag())));
    				
    					SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
    					StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(item.getDatum()));
    				 
    					row2.add(new Cell(f2, MMDDYYYY.toString()));
    			    
    					tableData.add(row2);
    			    
    					sum = sum + item.getBetrag();
    				}
            	}
        	}
		}else{
			for (Beitrag item: paylist) {
				List<Cell> row2 = new ArrayList<Cell>();
				if(item.getDatum().getTime() >= beginnDate.getTime() && item.getDatum().getTime() <= endDate.getTime()){
					row2.add(new Cell(f2, df.format(item.getBetrag())+ "  " +association_data_transfer.getAssociation().getFinacneOffice().getCurrency()));
					row2.add(new Cell(f2, new RuleBasedNumberFormat(RuleBasedNumberFormat.SPELLOUT).format(item.getBetrag())));
				
					SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
					StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(item.getDatum()));
				 
					row2.add(new Cell(f2, MMDDYYYY.toString()));
			    
					tableData.add(row2);
			    
					sum = sum + item.getBetrag();
				}
			}
        	for (Spende item: donation_list) {
        		
        			List<Cell> row2 = new ArrayList<Cell>();

    				if(item.getDatum().getTime() >= beginnDate.getTime() && item.getDatum().getTime() <= endDate.getTime()){
        				row2.add(new Cell(f2, df.format(item.getBetrag())+ "  " +association_data_transfer.getAssociation().getFinacneOffice().getCurrency()));
        				row2.add(new Cell(f2, new RuleBasedNumberFormat(RuleBasedNumberFormat.SPELLOUT).format(item.getBetrag())));
				
        				SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
        				StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(item.getDatum()));
				 
        				row2.add(new Cell(f2, MMDDYYYY.toString()));
			    
        				tableData.add(row2);
			    
        				sum = sum + item.getBetrag();
        			}
        	}
		}
		List<Cell> row2 = new ArrayList<Cell>();
		String currency = association_data_transfer.getAssociation().getFinacneOffice().getCurrency();

		if(currency.equals("€"))
			currency = "Euro";
		row2.add(new Cell(f2, "Summe: " + df.format(sum) + " " + currency));
		
		tableData.add(row2);
		
        table.setData(tableData, Table.DATA_HAS_1_HEADER_ROWS);
		table.setLineWidth(0.2);
        table.setPosition(85, 340);
        table.setColumnWidth(0, 170);
        table.setColumnWidth(1, 170);
        table.setColumnWidth(2, 100);
        table.setCellMargin(5.0);
		
        table.drawOn(page);		
	}
	
	/**
	 * method to handle main part of a mail
	 * 
	 * @throws Exception
	 */
	public void makeMainPart() throws Exception{
		
		String word;
		
        TextLine text1 = new TextLine(f2);
        if(new_receipt.getExpense() == 0){
        	word = "nicht ";
        }else{
        	word = "";
        }
        String row1 = "Es handelt sich "+word+" um den Verzicht auf die Erstattung von Aufwendungen ";
        text1.setText(row1);
        text1.setPosition(x, y);
        text1.drawOn(page);
        
        if(new_receipt.getBase1() == 1){
            optionA();
        }else if(new_receipt.getBase1() == 0){
            optionB();
        }
        
        OptionC();
	}
	
	/**
	 * method with the option a
	 * 
	 * @throws Exception
	 */
	public void optionA() throws Exception{
		
        TextLine text2 = new TextLine(f2);  
        String row2 = "Wir sind wegen Förderung " + new_receipt.getObject1();
        text2.setText(row2);
        text2.setPosition(x, y+=20);
        text2.drawOn(page);
        
        TextLine text3 = new TextLine(f2);  
        String row3 = "nach dem letzten uns zugegangenen Freistellungsbescheid" +
        		" bzw. nach der Anlage zum Körperschaftsbescheid";
        text3.setText(row3);
        text3.setPosition(x, y+=12);
        text3.drawOn(page);
        
        TextLine text4 = new TextLine(f2);  
        String row4 = "des Finanzamtes "+association_data_transfer.getAssociation().getFinacneOffice().getAdress().getCity()+ "" +
        		", StNr. "+ association_data_transfer.getAssociation().getFinacneOffice().getTaxNumber() +"" +
        				", vom "+vMMDDYYYY+" nach § 5 Abs. 1 Nr. 9 des Körperschaftsteuergesetzes";
        text4.setText(row4);
        text4.setPosition(x, y+=12);
        text4.drawOn(page);
        
        TextLine text5 = new TextLine(f2);  
        String row5 = "von der Körperschaftsteuer und nach § 3 Nr. 6 des Gewerbesteuergesetzes von der " +
        				"Gewerbesteuer befreit.";
        text5.setText(row5);
        text5.setPosition(x, y+=12);
        text5.drawOn(page);
	}
	
	/**
	 * method with the option b
	 * 
	 * @throws Exception
	 */
	public void optionB() throws Exception{
       
		TextLine text2 = new TextLine(f2);  
        String row2 = "Wir sind wegen Förderung " + new_receipt.getObject2();
        text2.setText(row2);
        text2.setPosition(x, y+=20);
        text2.drawOn(page);
        
        TextLine text3 = new TextLine(f2);  
        String row3 = "durch vorläufige Bescheinigung des Finanzamt "+association_data_transfer.getAssociation().getFinacneOffice().getAdress().getCity()+", " +
        		"StNr. "+association_data_transfer.getAssociation().getFinacneOffice().getTaxNumber()+ ",  vom "+vMMDDYYYY+" ab "+aMMDDYYYY+" ";
        text3.setText(row3);
        text3.setPosition(x, y+=12);
        text3.drawOn(page);
        
        TextLine text4 = new TextLine(f2);  
        String row4 = "als steuerbegünstigten Zwecken dienend anerkannt.";
        text4.setText(row4);
        text4.setPosition(x, y+=12);
        text4.drawOn(page);
	}
	
	/**
	 * method with the option c
	 * 
	 * @throws Exception
	 */
	public void OptionC() throws Exception{
		
		TextLine text2 = new TextLine(f2);  
        String row2 = "Es wird bestätigt, dass die Zuwendung nur zur Förderung " + new_receipt.getObject3();
        text2.setText(row2);
        text2.setPosition(x, y+=20);
        text2.drawOn(page);
        
		TextLine text7 = new TextLine(f2);  
        String row7 = "verwendet wird.";
        text7.setText(row7);
        text7.setPosition(x, y+=12);
        text7.drawOn(page);
        
        if(new_receipt.getBase2() == 1){
        	
    		TextLine text8 = new TextLine(f8);  
            String row8 = "Nur für steuerbegünstigte Einrichtungen, bei denen die Mitgliedsbeiträge steuerlich nicht ";
            text8.setText(row8);
            text8.setPosition(x, y+=20);
            text8.drawOn(page);
            
    		TextLine text9 = new TextLine(f8);  
            String row9 = "abziehbar sind:";
            text9.setText(row9);
            text9.setPosition(x, y+=12);
            text9.drawOn(page);
        	
    		TextLine text3 = new TextLine(f2);  
            String row3 = "Es wird bestätigt, dass es sich nicht um einen Mitgliedsbeitrag i.S.v § 10 b Abs. 1 Satz 2";
            text3.setText(row3);
            text3.setPosition(x, y+=20);
            text3.drawOn(page);
            
    		TextLine text4 = new TextLine(f2);  
            String row4 = "Einkommensteuergesetzes handelt.";
            text4.setText(row4);
            text4.setPosition(x, y+=12);
            text4.drawOn(page);
        }
        
		if(association_data_transfer.getAssociation().getLogo() == 2 && image_signature != null || association_data_transfer.getAssociation().getLogo() == 3 && image_signature != null){
			image_signature.setPosition(x, y+=5);
			image_signature.drawOn(page);
		}
        
		TextLine text5 = new TextLine(f9);  
        String row5 = "__________________________________________________";
        text5.setText(row5);
        text5.setPosition(x, y += 50);
        text5.drawOn(page);
        
		TextLine text6 = new TextLine(f9);  
        String row6 = "(Ort, Datum und Unterschrift des Zuwendungsempfängers)";
        text6.setText(row6);
        text6.setPosition(x, y+=12);
        text6.drawOn(page);
	}
	
	/**
	 * method to make the footer
	 * 
	 * @throws Exception
	 */
	public void makeFooter() throws Exception{
		
		int x = 85;
		int y = 730;
		
        TextLine association_name = new TextLine(f7);  
        association_name.setText("Hinweis:");
        association_name.setPosition(x, y);
        association_name.drawOn(page);
        
        TextLine row_1 = new TextLine(f6);
        String row1 = "Wer vorsätzlich oder grob fahrlässig " +
        		"eine unrichtige Zuwendungsbestätigung erstellt " +
        		"oder wer veranlasst, dass Zuwendungen nicht zu " +
        		"den in der Zuwendungsbestätigung";
        row_1.setText(row1);
        row_1.setPosition(x, y+=10);
        row_1.drawOn(page);
        
        TextLine row_2 = new TextLine(f6);
        String row2 = "angegebenen steuerbegünstigten Zwecken " +
        		"verwendet werden, haftet für die Steuer, die dem" +
        		" Fiskus durch einen etwaigen Abzug der Zuwendungen" +
        		" beim Zuwendenden";
        row_2.setText(row2);
        row_2.setPosition(x, y+=10);
        row_2.drawOn(page);
        
        TextLine row_3 = new TextLine(f6);
        String row3 = "entgeht (§ 10b Abs. 4 EStG, § 9 Abs. 3 KStG, " +
        		"§ 9 Nr. 5 GewStG).";
        row_3.setText(row3);
        row_3.setPosition(x, y+=10);
        row_3.drawOn(page);
        
        TextLine row_4 = new TextLine(f6);  
        String row4 = "Diese Bestätigung wird nicht als Nachweis für " +
        		"die steuerliche Berücksichtigung der Zuwendung " +
        		"anerkannt, wenn das Datum des Freistellungsbescheides " +
        		"länger als";
        row_4.setText(row4);
        row_4.setPosition(x, y+=10);
        row_4.drawOn(page);
        
        TextLine row_5 = new TextLine(f6);
        String row5 = "5 Jahre bzw. das Datum der vorläufigen " +
        		"Bescheinigung länger als 3 Jahre seit Ausstellung " +
        		"der Bestätigung zurückliegt (BMF vom 15.12.1994 - " +
        		"BStBI I S. 884).";
        row_5.setText(row5);
        row_5.setPosition(x, y+=10);
        row_5.drawOn(page);
	}
}