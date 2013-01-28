
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

import mail.view.MView;
import mail.view.MailView;
import mail.view.MassDonationReceipt;
import mail.view.MassMailView;
import mail.view.SingleMailView;
import member.view.DonatorInformation;
import member.view.ManageMembers;
import member.view.MemberAndDonatorFrame;
import member.view.MemberInformation;

/**
 * class represents ActionListener for the mail
 * 
 * @author Leonid Oldenburger
 */
public class MailActionListener implements ActionListener{
	
	private MemberAndDonatorFrame mainframe;
	private ManageMembers mame;
	private MailView mailview; 
	private MView allmailview;
	private MassMailView massmail;
	private MassDonationReceipt massreceipt;
	private SingleMailView s_mail_view;
	private MemberInformation member_information;
	private DonatorInformation donator_information;
	
	/**
	 * creates an actionlistener for the mail
	 * 
	 * @param amailview
	 * @param mview
	 * @param moneyBook
	 * @param adatatrans
	 */
	public MailActionListener(MView amailview, MailView mview){
		this.allmailview = amailview;
		this.mainframe = allmailview.getFrame();
		this.mame = allmailview.getManagePanel();
		this.mailview = mview;
		this.s_mail_view = null;
	}

	/**
	 * creates an actionlistener for the mail
	 * 
	 * @param amailview
	 * @param mview
	 * @param moneyBook
	 * @param adatatrans
	 */
	public MailActionListener(SingleMailView smailview, MemberInformation m_info){
		this.allmailview = null;
		this.s_mail_view = smailview;
		this.mainframe = allmailview.getFrame();
		this.mame = allmailview.getManagePanel();
		this.member_information = m_info;
		this.donator_information = null;
	}

	/**
	 * creates an actionlistener for the mail
	 * 
	 * @param amailview
	 * @param mview
	 * @param moneyBook
	 * @param adatatrans
	 */
	public MailActionListener(SingleMailView smailview, DonatorInformation d_info){
		this.allmailview = null;
		this.s_mail_view = smailview;
		this.mainframe = allmailview.getFrame();
		this.mame = allmailview.getManagePanel();
		this.donator_information = d_info;
		this.member_information = null;
	}
	
	/**
	 * method handles events
	 */
	public void actionPerformed(ActionEvent event) {
		
		if(s_mail_view == null){
			if (mailview.getMailButton() == event.getSource()) {
				try {
					this.massmail = new MassMailView(allmailview);
					allmailview.removePanel();
					allmailview.setPanel(massmail);
				
					mainframe.removePane();
					mainframe.addTabbedPanel(mame);
				
				} catch (Exception e) {
				}
			}
		}
		
		if(s_mail_view != null){
			if (s_mail_view.getBButton() == event.getSource()) {
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
		}
		
		if(s_mail_view == null){
			if (mailview.getReceiptButton() == event.getSource()) {
				try {
					this.massreceipt = new MassDonationReceipt(allmailview);
					allmailview.removePanel();
					allmailview.setPanel(massreceipt);
				
					mainframe.removePane();
					mainframe.addTabbedPanel(mame);
				} catch (Exception e) {
				}
			}
		}
	}
}