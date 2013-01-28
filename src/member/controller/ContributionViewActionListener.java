
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

import association.model.AssociationDataTransfer;

import member.view.DonatorContributionView;
import member.view.DonatorInformation;
import member.view.MemberContributionView;
import member.view.MemberInformation;

/**
 * class represents ActionListener for the contribution view
 * 
 * @author Leonid Oldenburger
 */
public class ContributionViewActionListener implements ActionListener{
	
	private MemberInformation member_information;
	private DonatorInformation donator_information;
	private MemberContributionView memberView;
	private DonatorContributionView donatorView;
	@SuppressWarnings("unused")
	private AssociationDataTransfer association_data_transfer;
	
	/**
	 * constuctor creates a action listerner for constribution view
	 * 
	 * @param mInfo
	 * @param mView
	 * @param adatatrans
	 */
	public ContributionViewActionListener(MemberInformation mInfo, MemberContributionView mView, AssociationDataTransfer adatatrans){
		this.member_information = mInfo;
		this.memberView = mView;
		this.donatorView = null;
		this.association_data_transfer = adatatrans;
	}

	/**
	 * constuctor creates a action listerner for constribution view
	 * 
	 * @param dInfo
	 * @param dView
	 * @param adatatrans
	 */
	public ContributionViewActionListener(DonatorInformation dInfo, DonatorContributionView dView, AssociationDataTransfer adatatrans){
		this.donator_information = dInfo;
		this.memberView = null;
		this.donatorView = dView;
		this.association_data_transfer = adatatrans;
	}
	
	/**
	 * methode handles events
	 */
	public void actionPerformed(ActionEvent event) {
		
		if (memberView  != null){
			if (memberView.getBackButton() == event.getSource()) {
				try {
					member_information.removePanel();
					member_information.makePanel();
				} catch (Exception e) {
				}
			}
		}
		
		if (donatorView != null){
			if (donatorView.getBackButton() == event.getSource()) {
				try {
					donator_information.removePanel();
					donator_information.makePanel();
				} catch (Exception e) {
				}
			}
		}
	}
}
