
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

import association.view.ConfigFrame;
import association.view.UpdateAssociationAdmin;
import association.view.UpdateAssociationBudget;
import association.view.UpdateAssociationConfig;
import association.view.UpdateAssociationData;
import association.view.UpdateAssociationDatabase;
import association.view.UpdateAssociationGroup;

/**
 * class represents ActionListener
 * 
 * @author Leonid Oldenburger
 */
public class BackConfigActionListener implements ActionListener{
	
	private ConfigFrame mainframe;
	private UpdateAssociationDatabase association_security;
	private UpdateAssociationConfig association_config;
	private UpdateAssociationGroup association_group;
	private UpdateAssociationBudget association_budget;
	private UpdateAssociationAdmin association_admin;
	private UpdateAssociationData association_data;
	
	/**
	 * constructor
	 * 
	 * @param mainframe
	 * @param data
	 */
	public BackConfigActionListener(ConfigFrame mainframe, UpdateAssociationData data){
		this.association_group = null;
		this.mainframe = mainframe;
		this.association_config  = null;
		this.association_budget = null;
		this.association_admin = null;
		this.association_data = data;
		this.association_security = null;
	}
	
	/**
	 * constructor
	 * 
	 * @param mainframe
	 * @param admin
	 */
	public BackConfigActionListener(ConfigFrame mainframe, UpdateAssociationAdmin admin){
		this.association_group = null;
		this.mainframe = mainframe;
		this.association_config  = null;
		this.association_budget = null;
		this.association_admin = admin;
		this.association_data  = null;
		this.association_security = null;
	}
	
	/**
	 * 
	 * @param mainframe
	 * @param budget
	 */
	public BackConfigActionListener(ConfigFrame mainframe, UpdateAssociationBudget budget){
		this.association_group = null;
		this.mainframe = mainframe;
		this.association_config  = null;
		this.association_budget = budget;
		this.association_admin = null;
		this.association_data = null;
		this.association_security = null;
	}
	
	/**
	 * 
	 * @param mainframe
	 * @param group
	 */
	public BackConfigActionListener(ConfigFrame mainframe, UpdateAssociationGroup  group){
		this.association_group = group;
		this.mainframe = mainframe;
		this.association_config  = null;
		this.association_budget = null;
		this.association_admin = null;
		this.association_data  = null;
		this.association_security = null;
	}
	
	/**
	 * 
	 * @param mainframe
	 * @param config
	 */
	public BackConfigActionListener(ConfigFrame mainframe, UpdateAssociationConfig config){
		this.association_config = config;
		this.mainframe = mainframe;
		this.association_group = null;
		this.association_budget = null;
		this.association_admin = null;
		this.association_data = null;
		this.association_security = null;
	}
	
	/**
	 * 
	 * @param mainframe
	 * @param security
	 */
	public BackConfigActionListener(ConfigFrame mainframe, UpdateAssociationDatabase security){
		this.association_config = null;
		this.mainframe = mainframe;
		this.association_group = null;
		this.association_budget = null;
		this.association_admin = null;
		this.association_data = null;
		this.association_security = security;
	}
	
	/**
	 * methode handles events
	 */
	public void actionPerformed(ActionEvent event) {
		// next
		
		if (association_data != null){
			if (association_data.getBackButton() == event.getSource()) {
				try {
					mainframe.dispose();
				} catch (Exception e) {
				}
			}
		}
		
		if (association_group != null){
			if (association_group.getBackButton() == event.getSource()) {
				try {
					mainframe.dispose();
				} catch (Exception e) {
				}
			}
		}
		
		if (association_budget != null){
			if (association_budget.getBackButton() == event.getSource()) {
				try {
					mainframe.dispose();
				} catch (Exception e) {
				}
			}
		}
		
		if (association_admin != null){
			if (association_admin.getBackButton() == event.getSource()) {
				try {
					mainframe.dispose();
				} catch (Exception e) {
				}
			}
		}
		
		if(association_config  != null){
			if (association_config.getBackButton() == event.getSource()) {
				try {
					mainframe.dispose();
				} catch (Exception e) {
				}
			}
		}
		
		if (association_security != null){
			if (association_security.getBackButton() == event.getSource()) {
				try {
					mainframe.dispose();
				} catch (Exception e) {
				}
			}
		}
	}
}
