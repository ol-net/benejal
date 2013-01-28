
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
package member.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import association.model.Account;
import association.model.Adress;
import association.model.AssociationDataTransfer;
import association.model.BookingDayDonator;
import association.model.Interval;
import association.model.SpecialContribution;
import association.model.Telephone;
import association.view.InfoDialog;

import mail.view.SingleDonationReceipt;
import mail.view.SingleMailView;
import member.model.Member;
import member.view.InfoPayedStatus;
import member.view.ManageMembers;
import member.view.MemberAndDonatorFrame;
import member.view.MemberContributionView;
import member.view.MemberInformation;
import moneybook.model.KassenBuch;

/**
 * class represents ActionListener for member
 * 
 * @author Leonid Oldenburger
 */
public class MemberActionListener implements ActionListener{
	
	private String title = "Beitragsübersicht";
	private String title_receipt = "Spendenbescheinigung";
	private String title_association = "Förderverein";
	private MemberInformation member_information;
	private Account association_member_account;
	private Adress association_member_adress;
	private Telephone association_member_phone;
	private MemberAndDonatorFrame mframe;
	private ManageMembers mmpanel;
	private SpecialContribution special_contribution;
	private AssociationDataTransfer association_data_transfer;
	private KassenBuch money_book;
	private Member association_member;
	private DateFormat df;
	private DecimalFormat decimalformat;
	private InfoPayedStatus payed_status;
	private MemberContributionView memberCView;
	
	/**
	 * constructor 
	 * 
	 * @param aMember
	 * @param mInfo
	 * @param mainframe
	 * @param status
	 */
	public MemberActionListener(Member aMember, MemberInformation mInfo, MemberAndDonatorFrame mainframe, InfoPayedStatus status){

		this.mframe = mainframe;
		this.association_member = aMember;
		this.member_information = mInfo;
		this.association_data_transfer =  AssociationDataTransfer.getInstance();
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");;
		this.decimalformat = new DecimalFormat("#0.00");
		this.payed_status = status;
		this.memberCView = null;
	}

	/**
	 * constructor 
	 * 
	 * @param mInfo
	 * @param mCView
	 */
	public MemberActionListener(Member aMember, MemberInformation mInfo, MemberContributionView mCView){

		this.association_member = aMember;
		this.member_information = mInfo;
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
		this.decimalformat = new DecimalFormat("#0.00");
		this.memberCView = mCView;
	}
	
	/**
	 * methode handels events
	 */
	public void actionPerformed(ActionEvent event) {
		
		if(memberCView == null){
			if (member_information.getBackButton() == event.getSource()) {
				try {
					member_information.dispose();
				
					mframe.removePane();
				
					this.mmpanel = new ManageMembers(mframe, 0, new Member(), null, money_book, association_data_transfer);
				
					mframe.addTabbedPanel(mmpanel);
			
				} catch (Exception e) {
				}
			}
			
			if (member_information.getContributionButton() == event.getSource()) {
				try {
					member_information.removePanel();
					member_information.setTitle(title_association+" - "+association_data_transfer.getAssociation().getName()+" - "+title+" - "+association_member.getFirstName()+" "+association_member.getLastName());

					member_information.createPanel(new MemberContributionView(member_information, association_member));
				} catch (Exception e) {
				}
			}
			
			if (member_information.getReceiptButton() == event.getSource()) {
				try {
					member_information.removePanel();
					member_information.setTitle(title_association+" - "+association_data_transfer.getAssociation().getName()+" - "+title_receipt+" für "+association_member.getFirstName()+" "+association_member.getLastName());

					member_information.createPanel(new SingleDonationReceipt(member_information));
				} catch (Exception e) {
				}
			}
			
			if (member_information.getMailButton() == event.getSource()) {
				try {
					member_information.removePanel();
					member_information.setTitle(title_association+" - "+association_data_transfer.getAssociation().getName()+" - Brief an "+association_member.getFirstName()+" "+association_member.getLastName());

					member_information.createPanel(new SingleMailView(member_information));
				} catch (Exception e) {
				}
			}
			
			if (member_information.getRemoveButton() == event.getSource()) {
				try {

					df = new SimpleDateFormat("dd.MM.yyyy"); 
				
					Date date_1 = null;
					Date date_2 = null;

					try{
						if(association_data_transfer.getReceiptDate("Mitglieder") != null){
							date_1 = df.parse(association_data_transfer.getReceiptDate("Mitglieder"));             
						}
						//System.out.println("Today = " + df.format(date)); 
					} catch (ParseException e) {} 

				try{
					if(association_data_transfer.getReceiptDate(association_data_transfer.getAssociation().getGroupList().get(association_member.getGroupID()).getGroup()) != null){
						date_2 = df.parse(association_data_transfer.getReceiptDate(association_data_transfer.getAssociation().getGroupList().get(association_member.getGroupID()).getGroup()));             
					}//System.out.println("Today = " + df.format(date)); 
				} catch (ParseException e) {} 

				Date tmp = new Date();
				
				if(date_1 == null && date_2 == null){
					
					association_member.setStatus(2);
					association_data_transfer.updateMember(association_member);
					association_data_transfer.ok();
					new InfoDialog("Dises Mitglied wird später automatisch gelöscht!");
				}else{
					
					if(date_1 == null){
						tmp = date_2;
					}else if(date_2 == null){
						tmp = date_1;
					}else{
						if(date_1.getTime() > date_2.getTime()){
							tmp = date_1;
						}else{
							tmp = date_2;
						}
					}

					if(new Interval(association_data_transfer).getStartDate().getTime() 
							<= tmp.getTime() && 
							new Interval(association_data_transfer).getEndDate().getTime() 
							>= tmp.getTime() && association_member.getSumContribution() == 0){
						association_data_transfer.deleteMember(association_member.getNumber());
						new InfoDialog("Dises Mitglied wird unverzüglich gelöscht!");
					}else{
						association_member.setStatus(2);
						association_data_transfer.updateMember(association_member);
						association_data_transfer.ok();
						new InfoDialog("Dises Mitglied wird später automatisch gelöscht!");
					}
				}
				} catch (Exception e) {
				}
			}
			
			if (member_information.getSaveButton() == event.getSource()) {
				try {
				
					boolean ok = false;

					if (member_information.getAccountNumber().getText().length() >= 3 && member_information.getBankCode().getText().length() >= 3 || member_information.getPayWay() == 1){
						ok = true;
					}
				
					if (member_information.getFirstName().getText().length() >= 3 && 
						member_information.getLastName().getText().length() >= 3 && 
						member_information.getStreet().getText().length() >= 3 && 
						member_information.getHouseNumber().getText().length() >= 1 && 
						member_information.getCity().getText().length() >= 3 &&
						member_information.getZipCode().getText().length() >= 3 && ok){
				
						createContribution();
						createMembersAccount();
						createMember();
										
						association_data_transfer.updateMember(association_member);
						association_data_transfer.ok();
				
						new InfoDialog("Benutzerdaten gespeichert");
					}else{
						new InfoDialog("Bitte füllen Sie die Pflichtfelder aus (min. 3 Zeichen)!");
					}

				} catch (Exception e) {
				}
			}
			
			if (member_information.getPayedButton() == event.getSource()) {
				
				String info = "Der Status dieses Mitglieds wird auf bezahlt gesetz!";
				new InfoPayedStatus(info, association_member, member_information, mframe, money_book, association_data_transfer);
			}
			
			if(payed_status != null){
				if (payed_status.getPayedButton() == event.getSource()) {

					association_data_transfer.updateContributionMember(0.0, association_member.getNumber());
					association_data_transfer.updateContributionDid(0, association_member.getNumber());
					association_data_transfer.updateStatusMember(Member.BEZAHLT, association_member.getNumber());
				
					try {
						payed_status.dispose();
					} catch (Exception e) {
					}
				}
			
				if (payed_status.getButton()== event.getSource()) {
					try {
						payed_status.dispose();
					} catch (Exception e) {
					}
				}
			}
		}
		
		if(memberCView != null){
			if (memberCView.getReceiptButton() == event.getSource()) {
				try {
					member_information.removePanel();

					member_information.setTitle(title_association+" - "+association_data_transfer.getAssociation().getName()+" - "+title_receipt+" für "+association_member.getFirstName()+" "+association_member.getLastName());

					member_information.createPanel(new SingleDonationReceipt(member_information));

				} catch (Exception e) {
				}
			}
		}
	}
	
	/**
	 * methode creates a new contribution.
	 */
	public void createContribution(){
		special_contribution = new SpecialContribution();
		
		if(!member_information.getMemberValue().getText().isEmpty()){
			Double s = new Double(member_information.getMemberValue().getText().replace(",", "."));
			special_contribution.setValue(new Double(decimalformat.format(s).replace(",", ".")));
		}else{
			special_contribution.setValue(0);
		}
		
		special_contribution.setPaymentForm(member_information.getPaymentForm());
		special_contribution.setPaymentTime(member_information.getPaymentTime());

		BookingDayDonator pspdate = new BookingDayDonator(association_data_transfer, money_book);

		java.sql.Date sqlDate = new java.sql.Date(pspdate.initBookingDay(special_contribution, 0).getTime());

		special_contribution.setSpecialPremiumDate(sqlDate);
	}
	
	/**
	 * methode creates a new member account.
	 */
	public void createMembersAccount(){
				
		if(association_member_account == null)
			association_member_account = new Account(); 
		
		if(!member_information.getAccountOwner().getText().isEmpty())
			association_member_account.setOwner(member_information.getAccountOwner().getText());
		if(!member_information.getAccountNumber().getText().isEmpty())
			association_member_account.setAccountNumber(member_information.getAccountNumber().getText());
		if(!member_information.getBankCode().getText().isEmpty())
			association_member_account.setBankCodeNumber(member_information.getBankCode().getText());
		if(!member_information.getBankname().getText().isEmpty())
			association_member_account.setBankName(member_information.getBankname().getText());
		association_member.setAccount(association_member_account);
		
		association_member.setGroupID(member_information.getGroup());
		
		// check contribution if it can be set
		double b = association_data_transfer.getMember(-1, null).get(association_member.getNumber()).getSumContribution();
		double ob = association_data_transfer.getMember(-1, null).get(association_member.getNumber()).getContribution();
		double nb = association_data_transfer.getAssociation().getGroupList().get(member_information.getGroup()).getPremium().getVlaue();
		
		if(ob == b){		
			b -= ob; 
			ob = nb;
			b = ob;
			association_member.setContribution(ob);
			association_member.setSumContribution(b);
		}else if(ob == 0 && b == 0){
			association_member.setContribution(nb);
			association_member.setSumContribution(nb);
		}else{
			association_member.setContribution(nb);
			association_member.setSumContribution(b);
		}
		association_member.setStatus(member_information.getStatus());
		association_member.setSpecialContribution(special_contribution);
		if(association_member.getSumContribution() != 0 && association_member.getStatus() == Member.FEST){
			association_member.setPayStatus(Member.FAELLIG);
		}else if(association_member.getSumContribution() == 0 && association_member.getStatus() == Member.FEST){
			association_member.setPayStatus(Member.BEZAHLT);
		}else{
			association_member.setPayStatus(Member.EMPTY);
		}
	}	
	
	/**
	 * methode creates new member.
	 */
	public void createMember(){
		// set person
		if(member_information .getMemberTitle().getText() != null)
			association_member.setTitle(member_information.getMemberTitle().getText());
		if(member_information .getGender().getText() != null)
			association_member.setGender(member_information.getGender().getText());
		if(member_information .getFirstName().getText() != null)
			association_member.setFirstName(member_information.getFirstName().getText());
		if(member_information.getLastName().getText() != null)
			association_member.setLastName(member_information.getLastName().getText());
		if(member_information.getTextArea() != null)
			association_member.setComment(member_information.getTextArea());
		
		association_member.setPaymentWay(member_information.getPayWay());
		
		// set adress
		if(association_member_adress == null)
			association_member_adress = new Adress();
		
		if(!member_information.getExtraAdress().getText().isEmpty())
			association_member_adress.setAdressAdditional(member_information.getExtraAdress().getText()); 
		
		if(!member_information.getStreet().getText().isEmpty())
			association_member_adress.setStreet(member_information.getStreet().getText()); 
		if(!member_information.getHouseNumber().getText().isEmpty())
			association_member_adress.setHouseNumber(member_information.getHouseNumber().getText());
		if(!member_information.getCity().getText().isEmpty())
			association_member_adress.setCity(member_information.getCity().getText());
		if(!member_information.getZipCode().getText().isEmpty())
			association_member_adress.setZipCode(member_information.getZipCode().getText());
		if(!member_information.getCountry().getText().isEmpty())
			association_member_adress.setCountry(member_information.getCountry().getText());
		if(!member_information.getEmail().getText().isEmpty())
			association_member_adress.setEMail(member_information.getEmail().getText());
		
		association_member.setAdress(association_member_adress);
		
		// set phone
		if(association_member_phone == null)
			association_member_phone = new Telephone();
		
		if(!member_information.getPhone().getText().isEmpty())
			association_member_phone.setPrivatePhone(member_information.getPhone().getText());
		association_member.setPhone(association_member_phone);
	}
}
