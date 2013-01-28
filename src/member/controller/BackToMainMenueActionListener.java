
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

import mail.view.MailView;
import member.view.AddDonatorData;
import member.view.AddMemberData;
import member.view.EditDonators;
import member.view.EditMembers;
import member.view.MemberAndDonatorFrame;

/**
 * class represents ActionListener to close the panel
 * 
 * @author Leonid Oldenburger
 */
public class BackToMainMenueActionListener implements ActionListener{
	
	private EditMembers edit_member;
	private EditDonators edit_donator;
	private MemberAndDonatorFrame mainframe;
	private AddMemberData add_member;
	private AddDonatorData add_donator;
	private MailView mailview;
	
	/**
	 * constructor creates an actionlistener 
	 * 
	 * @param mainframe
	 * @param mview
	 */
	public BackToMainMenueActionListener(MemberAndDonatorFrame mainframe, MailView mview){
		this.edit_member = null;
		this.mainframe = mainframe;
		this.edit_donator = null;
		this.add_member = null;
		this.add_donator = null;
		this.mailview = mview;
	}
	
	/**
	 * constructor creates an actionlistener 
	 * 
	 * @param mainframe
	 * @param addDonator
	 */
	public BackToMainMenueActionListener(MemberAndDonatorFrame mainframe, AddDonatorData addDonator){
		this.edit_member = null;
		this.mainframe = mainframe;
		this.edit_donator = null;
		this.add_member = null;
		this.add_donator = addDonator;
		this.mailview = null;
	}
	
	/**
	 * constructor creates an actionlistener 
	 * 
	 * @param mainframe
	 * @param addMember
	 */
	public BackToMainMenueActionListener(MemberAndDonatorFrame mainframe, AddMemberData addMember){
		this.edit_member = null;
		this.mainframe = mainframe;
		this.edit_donator = null;
		this.add_member = addMember;
		this.add_donator = null;
		this.mailview = null;
	}
	
	/**
	 * constructor creates an actionlistener 
	 * 
	 * @param mainframe
	 * @param eMember
	 */
	public BackToMainMenueActionListener(MemberAndDonatorFrame mainframe, EditMembers eMember){
		this.edit_member = eMember;
		this.mainframe = mainframe;
		this.edit_donator = null;
		this.add_member = null;
		this.add_donator = null;
		this.mailview = null;
	}
	
	/**
	 * constructor creates an actionlistener 
	 * 
	 * @param mainframe
	 * @param eDonator
	 */
	public BackToMainMenueActionListener(MemberAndDonatorFrame mainframe, EditDonators eDonator){
		this.edit_donator = eDonator;
		this.mainframe = mainframe;
		this.edit_member = null;
		this.add_member = null;
		this.mailview = null;
	}

	/**
	 * methode handles events
	 */
	public void actionPerformed(ActionEvent event) {
		// next
		
		if (mailview != null){
			if (mailview.getBackButton() == event.getSource()) {
				try {
					mainframe.dispose();
				} catch (Exception e) {
				}
			}
		}
		
		if (edit_member != null){
			if (edit_member.getBackButton() == event.getSource()) {
				try {
					mainframe.dispose();
				} catch (Exception e) {
				}
			}
		}
		
		if (add_member != null){
			if (add_member.getBackButton() == event.getSource()) {
				try {
					mainframe.dispose();
				} catch (Exception e) {
				}
			}
		}
		
		if (add_donator != null){
			if (add_donator.getBackButton() == event.getSource()) {
				try {
					mainframe.dispose();
				} catch (Exception e) {
				}
			}
		}
		
		if(edit_donator != null){
			if (edit_donator.getBackButton() == event.getSource()) {
				try {
					mainframe.dispose();
				} catch (Exception e) {
				}
			}
		}
	}
}
