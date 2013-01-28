
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
package member.model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import association.model.Association;
import association.model.AssociationDataTransfer;

import com.pdfjet.Cell;
import com.pdfjet.Font;
import com.pdfjet.Image;
import com.pdfjet.ImageType;
import com.pdfjet.Letter;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.Table;
import com.pdfjet.TextLine;

/**
 * class to creat a member list
 * 
 * @author Leonid Oldenburger
 */
public class MemberPDFList {

	private Member association_member;
	private LinkedHashMap<Integer, Member> association_member_map;
	private Association association;
	
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
	private Page page;
	
    private double x_pos = 210;
    private double y_pos = 80.0;
	
	private AssociationDataTransfer association_data_transfer;	

	private PDF pdf;
		
	private Date today;
	
	private Image image = null;
	
	private int group;
	
	private StringBuilder nowDDMMYYYY;
	
	public MemberPDFList(int member_group) throws Exception{
		
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.association = association_data_transfer.getAssociation();
		this.group = member_group;
		//System.out.println(group);
		this.association_member_map = association_data_transfer.getMember(group, null);
        this.today = new Date();
        this.image = null;
        
        SimpleDateFormat DDMMYYYY = new SimpleDateFormat("dd.MM.yyyy");
        nowDDMMYYYY = new StringBuilder(DDMMYYYY.format(today));
		
        String file = association_data_transfer.getAssociation().getDataDir()+"Mitgliedsliste_vom_"+nowDDMMYYYY+".pdf";
		
        FileOutputStream fos = null;
        
        try{
        	fos = new FileOutputStream(file);
        }catch(FileNotFoundException e){}
        
        try{
        	pdf = new PDF(fos);
        }catch(NullPointerException ex){}
        
        if (pdf != null){
        	
        	f1 = new Font(pdf, "Helvetica-Bold");
        	f1.setSize(14);
        	f2 = new Font(pdf, "Helvetica");
        	f2.setSize(10);
        	f3 = new Font(pdf, "Helvetica-Bold");
        	f3.setSize(30);
        	f4 = new Font(pdf, "Helvetica-Bold");
        	f4.setSize(8);
        	f5 = new Font(pdf, "Helvetica-Bold");
        	f5.setSize(20);
        	f6 = new Font(pdf, "Helvetica");
        	f6.setSize(6);
        	f7 = new Font(pdf, "Helvetica-Bold");
        	f7.setSize(6);
        	f8 = new Font(pdf, "Helvetica-Bold");
        	f8.setSize(10);
        
        	String fileName = "logo.jpg"; 
        
        	@SuppressWarnings("unused")
        	FileReader fr = null;
        	try {
        		fr = new FileReader(fileName);
        		BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(fileName));
        		this.image = new Image(pdf, bis2, ImageType.JPEG);
        	} catch (FileNotFoundException e) {
        		this.image = null;
        	}
		
        	this.association_member_map = association_data_transfer.getMember(group, null);
    	
        	//start paint
        	createMemberList();
    	
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
	}
	
	public void createMemberList() throws Exception{
		
		page = new Page(pdf, Letter.PORTRAIT);
		
		makeHeader();
		
		createList();
	}
	
	/**
	 * methode to make the header
	 * 
	 * @throws Exception
	 */
	public void makeHeader() throws Exception{
		
		TextLine maintopic = new TextLine(f3);
		maintopic.setText("Mitgliederliste");
		maintopic.setPosition(x_pos, y_pos += 40);
		maintopic.drawOn(page); 
		
		if(association_data_transfer.getAssociation().getLogo() == 1 && image != null || association_data_transfer.getAssociation().getLogo() == 3 && image != null){
			image.setPosition(x_pos+=27, y_pos += 20);
			image.scaleBy(0.7);
			image.drawOn(page);
			x_pos = 210;
			y_pos += 100;
		}else{
			TextLine topic = new TextLine(f3);
			topic.setText(association.getName());
			int length = association.getName().length() * 5;
			x_pos = 290;
			x_pos -= length;
			topic.setPosition(x_pos, y_pos += 70);
			topic.drawOn(page);
			x_pos = 210;
			y_pos += 70;
		}
		
        TextLine mail_date = new TextLine(f5);
        mail_date.setText("vom "+nowDDMMYYYY);
        mail_date.setPosition(x_pos += 27, y_pos);
        mail_date.drawOn(page);
        
//        TextLine shown_members = new TextLine(f5);
//        shown_members.setText("vom "+nowDDMMYYYY);
//        shown_members.setPosition(x_pos += 27, y_pos);
//        shown_members.drawOn(page);
	}
	
	public void createList() throws Exception{
		
		Table table = new Table(f1, f2);
		
		List<List<Cell>> tableData = new ArrayList<List<Cell>>();		
		List<Cell> row = new ArrayList<Cell>();
		
		row.add(new Cell(f8, "Mitgl. Nr.  "));
		row.add(new Cell(f8, "Anrede  "));
		row.add(new Cell(f8, "Name  "));
		row.add(new Cell(f8, "Straﬂe  "));
		row.add(new Cell(f8, "PLZ  "));
		row.add(new Cell(f8, "Wohnort  "));
		//row.add(new Cell(f8, "E-Mail  "));
		row.add(new Cell(f8, "Mitgliedsart  "));
		
		tableData.add(row);
		
		/**
		 * get Collection of values contained in HashMap using
		 * Collection values() method of HashMap class
		 */
		Iterator<Map.Entry<Integer,Member>> i = association_member_map.entrySet().iterator();
		
		while (i.hasNext() ) {
			
			List<Cell> row2 = new ArrayList<Cell>();
			
			Map.Entry<Integer, Member> entry = i.next();
			association_member = entry.getValue();
			
			row2.add(new Cell(f2, Integer.toString(10000 + association_member.getNumber()).replaceFirst("1", "0") +  "  "));
			row2.add(new Cell(f2, association_member.getGender() + "   "));
			row2.add(new Cell(f2, association_member.getFirstName() + " " + association_member.getLastName() + "   "));
			row2.add(new Cell(f2, association_member.getAdress().getStreet() + " " + association_member.getAdress().getHouseNumber() + "   "));
			row2.add(new Cell(f2, association_member.getAdress().getZipCode() + "   "));
			row2.add(new Cell(f2, association_member.getAdress().getCity() + "   "));
			//row2.add(new Cell(f2, association_member.getAdress().getEmail() + "   "));
			row2.add(new Cell(f2, association_member.getMemberGroup().getGroup() + "   "));
			
			tableData.add(row2);
		}
		
		x_pos = 305;
		y_pos = 40;
				
        table.setData(tableData, Table.DATA_HAS_1_HEADER_ROWS);
		table.setLineWidth(0.0);
        table.autoAdjustColumnWidths();
    
		double x = table.getWidth() / 2;
		x_pos -= x;
        
        table.setPosition(x_pos, y_pos);
        table.setCellMargin(2.0);
                
        setFontForRow(table, 0, f8);

        
        setBgColorForRow(table, 0, new double[] { 0.85, 0.85, 0.85 });
        
        page = new Page(pdf, Letter.PORTRAIT);
        
        int numOfPages = table.getNumberOfPages(page);
        int pageNumber = 1;
        while (true) {
            table.drawOn(page);

            TextLine text = new TextLine(f7);
            text.setText("Seite " + pageNumber++ + " von " + numOfPages);
            text.setPosition(280.0, 775.0);
            text.drawOn(page);

            if (!table.hasMoreData()) break;
            page = new Page(pdf, Letter.PORTRAIT);
        }

        pdf.flush();
	}
	
	public void setBgColorForRow(
		Table table, int index, double[] color) throws Exception {
		List<Cell> row = table.getRow(index);
		for (int i = 0; i < row.size(); i++) {
			Cell cell = row.get(i);
			cell.setBgColor(color);
		}
	}
	
    public void setFontForRow(
            Table table, int index, Font font) throws Exception {
        List<Cell> row = table.getRow(index);
        for (int i = 0; i < row.size(); i++) {
            row.get(i).setFont(font);
        }
    }
}