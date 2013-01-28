
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
package register.controller;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.ButtonModel;

import member.model.Member;
import moneybook.model.KassenBuch;

import association.model.Association;
import association.model.AssociationDataTransfer;
import association.model.Contribution;
import association.model.Group;

import association.view.ConfigFrame;
import association.view.InfoDialog;
import association.view.UpdateAssociationGroup;

import register.view.RegisterAssociationBudget;
import register.view.RegisterAssociationConfig;
import register.view.RegisterAssociationGroup;
import register.view.RegisterStartWindow;

/**
 * class represents ActionListener for association group
 * 
 * @author Leonid Oldenburger
 */
public class AssociationGroupActionListener implements ActionListener{
	
	private Association association_object;
	private RegisterAssociationGroup associationgroup;
	private RegisterStartWindow mainframe;
	private Font font_style;
	
	private Group association_group_0;
	private Contribution association_premium_0;
	private Group association_group_1;
	private Contribution association_premium_1;
	private Group association_group_2;
	private Contribution association_premium_2;
	private Group association_group_3;
	private Contribution association_premium_3;
	private Group association_group_4;
	private Contribution association_premium_4;
	private Group association_group_5;
	private Contribution association_premium_5;
	private LinkedList<Group> grouplist;
	private ConfigFrame menuFrame;
	private UpdateAssociationGroup associationGroup;
	private AssociationDataTransfer association_data_transfer;
	private LinkedHashMap<Integer, Member> member_map;
	private Member member;
	private KassenBuch money_book;
	protected DecimalFormat df;
	
	
	/**
	 * constructor
	 * 
	 * @param aGroup
	 * @param hMenue
	 * @param aObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AssociationGroupActionListener(UpdateAssociationGroup aGroup, ConfigFrame hMenue, Association aObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.menuFrame = hMenue;
		this.association_object = aObject;
		this.associationGroup = aGroup;
		this.associationgroup = aGroup;
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		this.df = new DecimalFormat("#0.00");
	}
	
	/**
	 * constructor
	 * 
	 * @param agroup
	 * @param frame
	 * @param associationObject
	 * @param moneyBook
	 * @param adatatrans
	 */
	public AssociationGroupActionListener(RegisterAssociationGroup agroup, RegisterStartWindow frame, Association associationObject, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.association_object = associationObject;
		this.associationgroup = agroup;
		this.mainframe = frame;
		this.font_style = associationgroup.getFontStyle();
		this.association_data_transfer = adatatrans;
		this.money_book = moneyBook;
		this.df = new DecimalFormat("#0.00");
	}
	
	/**
	 * methode handles events
	 */
	public void actionPerformed(ActionEvent event) {
		
		createAssociation();
		
		// next
		if (associationGroup == null){
			if (associationgroup.getNextButton() == event.getSource()) {
				try {
					mainframe.removePanel();
					mainframe.addPanel(new RegisterAssociationConfig(font_style, mainframe, association_object, money_book, association_data_transfer));
				} catch (Exception e) {
				}
			}
		}
		
		// back
		if (associationGroup == null){
			if (associationgroup.getBackButton() == event.getSource()) {
				try {
					mainframe.removePanel();
					mainframe.addPanel(new RegisterAssociationBudget(font_style, mainframe, association_object, money_book, association_data_transfer));
				} catch (Exception e) {
				}
			}
		}	
		
		if (associationGroup != null){
			if (this.associationGroup.getBackButton() == event.getSource()) {
				try {
					menuFrame.removePane();
				} catch (Exception e) {
				}
			}
		}
		
		// update
		if (associationGroup != null){
			if (associationGroup.getSaveButton() == event.getSource()) {
				try {
					
					association_data_transfer.updateAssociationGroup(association_object);
					
					initNewMemberContribution();

					new InfoDialog("Vereinsdaten gespeichert");

				} catch (Exception e) {
				}
			}
		}
		
	}
	
	/**
	 * methode creates a new association object 
	 */
	public void createAssociation(){
		
		association_group_0 = new Group();
		association_premium_0 = new Contribution();
		association_group_1 = new Group();
		association_premium_1 = new Contribution();
		association_group_2 = new Group();
		association_premium_2 = new Contribution();
		association_group_3 = new Group();
		association_premium_3 = new Contribution();
		association_group_4 = new Group();
		association_premium_4 = new Contribution();
		association_group_5 = new Group();
		association_premium_5 = new Contribution();
		
		
		if(associationgroup.getData(0, 0) == null || ((associationgroup.getData(0, 0) instanceof String)&&
				((String)associationgroup.getData(0, 0)).length() == 0)){
		}
		else{
			association_group_0.setGroup(associationgroup.getData(0, 0).toString());
			
			if(associationgroup.getData(0, 1) == null || ((associationgroup.getData(0, 1) instanceof String)&&
					((String)associationgroup.getData(0, 1)).length() == 0)){
				association_premium_0.setValue(0);
				association_group_0.setPremium(association_premium_0);
			}
			else{
				Double s = new Double(associationgroup.getData(0, 1).toString().replace(",", "."));
				association_premium_0.setValue(new Double(df.format(s).replace(",", ".")));
				association_group_0.setPremium(association_premium_0);
			}
		}
		
		if(associationgroup.getData(1, 0) == null || ((associationgroup.getData(1, 0) instanceof String)&&
				((String)associationgroup.getData(1, 0)).length() == 0)){
		}
		else{
			association_group_1.setGroup(associationgroup.getData(1, 0).toString());
			
			if(associationgroup.getData(1, 1) == null || ((associationgroup.getData(1, 1) instanceof String)&&
					((String)associationgroup.getData(1, 1)).length() == 0)){
				association_premium_1.setValue(0);
				association_group_1.setPremium(association_premium_1);
			}
			else{
				Double s1 = new Double(associationgroup.getData(1, 1).toString().replace(",", "."));
				association_premium_1.setValue(new Double(df.format(s1).replace(",", ".")));
				association_group_1.setPremium(association_premium_1);
			}
		}
		
		if(associationgroup.getData(2, 0) == null || ((associationgroup.getData(2, 0) instanceof String)&&
				((String)associationgroup.getData(2, 0)).length() == 0)){
		}
		else{
			association_group_2.setGroup(associationgroup.getData(2, 0).toString());
			
			if(associationgroup.getData(2, 1) == null || ((associationgroup.getData(2, 1) instanceof String)&&
					((String)associationgroup.getData(2, 1)).length() == 0)){
				association_premium_2.setValue(0);
				association_group_2.setPremium(association_premium_2);
			}
			else{
				Double s2 = new Double(associationgroup.getData(2, 1).toString().replace(",", "."));
				association_premium_2.setValue(new Double(df.format(s2).replace(",", ".")));
				association_group_2.setPremium(association_premium_2);
			}
		}
		
		if(associationgroup.getData(3, 0) == null || ((associationgroup.getData(3, 0) instanceof String)&&
				((String)associationgroup.getData(3, 0)).length() == 0)){
		}
		else{
			association_group_3.setGroup(associationgroup.getData(3, 0).toString());
			
			if(associationgroup.getData(3, 1) == null || ((associationgroup.getData(3, 1) instanceof String)&&
					((String)associationgroup.getData(3, 1)).length() == 0)){
				association_premium_3.setValue(0);
				association_group_3.setPremium(association_premium_3);
			}
			else{
				Double s3 = new Double(associationgroup.getData(3, 1).toString().replace(",", "."));
				association_premium_3.setValue(new Double(df.format(s3).replace(",", ".")));
				association_group_3.setPremium(association_premium_3);
			}
		}
		
		if(associationgroup.getData(4, 0) == null || ((associationgroup.getData(4, 0) instanceof String)&&
				((String)associationgroup.getData(4, 0)).length() == 0)){
		}
		else{
			association_group_4.setGroup(associationgroup.getData(4, 0).toString());
			
			if(associationgroup.getData(4, 1) == null || ((associationgroup.getData(4, 1) instanceof String)&&
					((String)associationgroup.getData(4, 1)).length() == 0)){
				association_premium_4.setValue(0);
				association_group_4.setPremium(association_premium_4);
			}
			else{
				Double s4 = new Double(associationgroup.getData(4, 1).toString().replace(",", "."));
				association_premium_4.setValue(new Double(df.format(s4).replace(",", ".")));
				association_group_4.setPremium(association_premium_4);
			}
		}
		
		if(associationgroup.getData(5, 0) == null || ((associationgroup.getData(5, 0) instanceof String)&&
				((String)associationgroup.getData(5, 0)).length() == 0)){
		}
		else{
			association_group_5.setGroup(associationgroup.getData(5, 0).toString());
			
			if(associationgroup.getData(5, 1) == null || ((associationgroup.getData(5, 1) instanceof String)&&
					((String)associationgroup.getData(5, 1)).length() == 0)){
				association_premium_5.setValue(0);
				association_group_5.setPremium(association_premium_5);
			}
			else{
				Double s5 = new Double(associationgroup.getData(5, 1).toString().replace(",", "."));
				association_premium_5.setValue(new Double(df.format(s5).replace(",", ".")));
				association_group_5.setPremium(association_premium_5);
			}
		}
		
		grouplist = new LinkedList<Group>();
		
		grouplist.add(association_group_0);
		grouplist.add(association_group_1);
		grouplist.add(association_group_2);
		grouplist.add(association_group_3);
		grouplist.add(association_group_4);
		grouplist.add(association_group_5);
		
		association_object.setGroupList(grouplist);
	}
	
	/**
	 * methode init new member contribution
	 */
	public void initNewMemberContribution(){
		
		ButtonModel selectedModel = associationGroup.getButtonGroup().getSelection();
		boolean ok = associationGroup.getRadioButtonYes().getModel() == selectedModel;
		member_map = association_data_transfer.getMember(-1, null);
		
		/**
		 * get Collection of values contained in HashMap using
		 * Collection values() method of HashMap class
		 */
		Iterator<Map.Entry<Integer,Member>> i = member_map.entrySet().iterator();
		
		while (i.hasNext() ) {
			Map.Entry<Integer, Member> entry = i.next();
			member = entry.getValue();
						
			if(ok){
				member.setContribution(association_object.getGroupList().get(member.getGroupID()).getPremium().getVlaue());
				member.setSumContribution(association_object.getGroupList().get(member.getGroupID()).getPremium().getVlaue());
				member.setPayStatus(Member.FAELLIG);
			}else{
				member.setContribution(association_object.getGroupList().get(member.getGroupID()).getPremium().getVlaue());
			}
			association_data_transfer.updateMember(member);
		}
		association_data_transfer.ok();
	}
	
	public String getBookingDate(){
		
        SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder MMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(new Date()));
        
		return MMDDYYYY.toString();
	}
}
