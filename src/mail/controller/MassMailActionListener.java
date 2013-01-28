
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
package mail.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Date;
import java.util.LinkedList;

import mail.model.Mail;
import mail.model.MassMail;

import mail.view.MView;
import mail.view.MailView;
import mail.view.MassMailView;
import mail.view.SingleMailView;

import member.view.DonatorInformation;
import member.view.ManageMembers;
import member.view.MemberAndDonatorFrame;
import member.view.MemberInformation;
import moneybook.model.KassenBuch;

import association.model.AssociationDataTransfer;
import association.model.Group;

import java.text.SimpleDateFormat;

/**
 * class represents ActionListener
 * 
 * @author Leonid Oldenburger
 */
public class MassMailActionListener implements ActionListener{
	
	private MemberAndDonatorFrame mainframe;
	private ManageMembers mame;
	private MassMailView massmailview; 
	private MView allmailview;
	private MailView mailview;
	private Date dateNow;
	private Mail newmail;
	private LinkedList<Group> group_list;
	private AssociationDataTransfer association_data_transfer;
	@SuppressWarnings("unused")
	private KassenBuch money_book;
	private MemberInformation member_information;
	private DonatorInformation donator_information;
	private SingleMailView single_mview;
	
	/**
	 * constructor creates actionlistener for mass mail
	 * 
	 * @param amailview
	 * @param mmview
	 * @param moneyBook
	 * @param adatatrans
	 */
	public MassMailActionListener(MView amailview, MassMailView mmview){
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
		this.allmailview = amailview;
		this.mainframe = allmailview.getFrame();
		this.mame = allmailview.getManagePanel();
		this.massmailview = mmview;
		this.single_mview = null;
	}
	
	/**
	 * constructor creates actionlistener for single mail
	 * 
	 * @param m_view
	 * @param s_mview
	 */
	public MassMailActionListener(MemberInformation m_view, SingleMailView s_view){
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
		this.member_information = m_view;
		this.single_mview = s_view;

	}
	
	/**
	 * constructor creates actionlistener for single mail
	 * 
	 * @param d_view
	 * @param s_mview
	 */
	public MassMailActionListener(DonatorInformation d_view, SingleMailView s_view){
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
		this.massmailview = null;
		this.member_information = null;
		this.donator_information = d_view;
		this.single_mview = s_view;
	}

	/**
	 * method handles events
	 */
	public void actionPerformed(ActionEvent event) {

		if(single_mview == null){
			if (massmailview.getBackButton() == event.getSource()) {
				try {
					this.mailview = new MailView(allmailview);
					allmailview.removePanel();
					allmailview.setPanel(mailview);
				
					mainframe.removePane();
					mainframe.addTabbedPanel(mame);
				
				} catch (Exception e) {
				}
			}
		
			if (massmailview.getSaveButton() == event.getSource()) {
				try {
				
					dateNow = new Date();
					SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
					StringBuilder nowMMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(dateNow));
		        
					group_list = association_data_transfer.getGroup();
				
					String[] mgroup = new String[group_list.size()+3];
				
					mgroup[0] = "alle Mitglieder";
					mgroup[1] = "offene Beiträge";
					mgroup[2] = "Spender";
				
					for(int j = 0; j < group_list.size(); j++){
						mgroup[j+3] = group_list.get(j).getGroup();
					}
		        
					String group = mgroup[massmailview.getMemberGroup()];
				
					newmail = new Mail(massmailview.getSubject().getText(), massmailview.getMail().getText(), group, nowMMDDYYYY.toString(), massmailview.getAuthor());

					association_data_transfer.setMail(newmail);
				
					new MassMail(newmail, nowMMDDYYYY.toString(), massmailview.getMemberGroup(), -1);
				
				} catch (Exception e) {
				}
			}
		}
		
		if(single_mview != null){
			if (single_mview.getBButton() == event.getSource()) {
				try {
					if(donator_information == null){
						member_information.removePanel();
						member_information.makePanel();
					}else if(member_information == null){
						donator_information.removePanel();
						donator_information.makePanel();
					}
				} catch (Exception e) {
				}
			}
			
			if (single_mview.getNButton() == event.getSource()) {
				try {
					
					dateNow = new Date();
					SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd.MM.yyyy");
					StringBuilder nowMMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(dateNow));
					
					String name = "";
					
					if(donator_information == null){
						name = member_information.getFirstName().getText()+" "+member_information.getLastName().getText();
					}else if(member_information == null){
						name = donator_information.getFirstName().getText()+" "+donator_information.getLastName().getText();
					}
				
					newmail = new Mail(single_mview.getSubject().getText(), single_mview.getMail().getText(), name, nowMMDDYYYY.toString(), single_mview.getAuthor());

					association_data_transfer.setMail(newmail);
				
					if(donator_information == null){
						new MassMail(newmail, nowMMDDYYYY.toString(), -10, member_information.getMemberID());
					}else if(member_information == null){
						new MassMail(newmail, nowMMDDYYYY.toString(), -11, donator_information.getDonatorID());
					}
				
				} catch (Exception e) {
				}
			}
		}
	}
}