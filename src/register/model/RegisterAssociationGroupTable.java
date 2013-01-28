
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
package register.model;

import association.model.Association;
import association.model.AssociationDataTransfer;
import association.model.Contribution;
import association.model.Group;

import java.text.DecimalFormat;
import java.util.LinkedList;

import javax.swing.table.DefaultTableModel;

/**
 * class represents grouptable
 * 
 * @author Leonid Oldenburger
 */
public class RegisterAssociationGroupTable extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	protected String group = "Gruppe";
	protected String premium = "Beitrag";
	protected String currency = "W‰hrung";
	protected String membergroup = "Mitgliedergruppe";
	
	protected Group association_group_0;
	protected Contribution association_premium_0;
	protected Group association_group_1;
	protected Contribution association_premium_1;
	protected Group association_group_2;
	protected Contribution association_premium_2;
	protected Group association_group_3;
	protected Contribution association_premium_3;
	protected Group association_group_4;
	protected Contribution association_premium_4;
	protected Group association_group_5;
	protected Contribution association_premium_5;
	
	protected Association association;
	protected Group association_group;
	protected Contribution association_premium;
	protected LinkedList<Group> association_group_list;
	
	protected DecimalFormat df;
	
	protected String column[] = {group, premium, currency};
	protected String row[][]= {{"Regul‰res Mitglied ","0,00","Euro"}, {"Erm‰ﬂigtes Mitglied","0,00","Euro"}, {"Ehrenmitglied","0,00","Euro"}, {"","0,00","Euro"}, {"","0,00","Euro"}, {"","0,00","Euro"}};
	
	protected AssociationDataTransfer association_data_transfer;
	
	/**
	 * constructor for group table
	 * 
	 * @param adatatrans
	 * @param association_object
	 */
	public RegisterAssociationGroupTable(AssociationDataTransfer adatatrans, Association association_object){

		this.association_data_transfer = adatatrans;
		
		if(association_data_transfer.showAssociationTable()){
			association = association_data_transfer.getAssociation();
		}else{
			association = association_object;
		}
		
		if (association != null)
	    	association_group_list = association.getGroupList();
	    	
	    if (association_group_list != null)
	    	setData();
		
	    this.setDataVector(row, column);
		
	}
	
	/**
	 * methode set data
	 */
	public void setData(){
		
		association_group_0 = association_group_list.get(0);
		association_group_1 = association_group_list.get(1);
		association_group_2 = association_group_list.get(2);
		association_group_3 = association_group_list.get(3);
		association_group_4 = association_group_list.get(4);
		association_group_5 = association_group_list.get(5);
		
		df = new DecimalFormat("#0.00");
		
		if (association_group_0 != null){
			
		    row[0][0] = association_group_0.getGroup();
		    
		    association_premium_0 = association_group_0.getPremium();
		    
		    if(association_premium_0 != null){
			    row[0][1] = df.format(association_premium_0.getVlaue());
		    }
		    row[0][2] = association.getFinacneOffice().getCurrency();
		}
		
		if (association_group_1 != null){
			
		    row[1][0] = association_group_1.getGroup();
		    
		    association_premium_1 = association_group_1.getPremium();
		    
		    if(association_premium_1 != null){
			    row[1][1] = df.format(association_premium_1.getVlaue());
		    }
		    row[1][2] = association.getFinacneOffice().getCurrency();
		}
		
		if (association_group_2 != null){
			
		    row[2][0] = association_group_2.getGroup();
		    
		    association_premium_2 = association_group_2.getPremium();
		    
		    if(association_premium_2 != null){
			    row[2][1] = df.format(association_premium_2.getVlaue());
		    }
		    row[2][2] = association.getFinacneOffice().getCurrency();
		}
		
		if (association_group_3 != null){
			
		    row[3][0] = association_group_3.getGroup();
		    
		    association_premium_3 = association_group_3.getPremium();
		    
		    if(association_premium_3 != null){
			    row[3][1] = df.format(association_premium_3.getVlaue());;
		    }
		    row[3][2] = association.getFinacneOffice().getCurrency();
		}
		
		if (association_group_4 != null){
			
		    row[4][0] = association_group_4.getGroup();
		    
		    association_premium_4 = association_group_4.getPremium();
		    
		    if(association_premium_4 != null){
			    row[4][1] = df.format(association_premium_4.getVlaue());
		    }
		    row[4][2] = association.getFinacneOffice().getCurrency();
		}
		
		if (association_group_5 != null){
			
		    row[5][0] = association_group_5.getGroup();
		    
		    association_premium_5 = association_group_5.getPremium();
		    
		    if(association_premium_5 != null){
			    row[5][1] = df.format(association_premium_5.getVlaue());
		    }
		    row[5][2] = association.getFinacneOffice().getCurrency();
		}
	}
}
