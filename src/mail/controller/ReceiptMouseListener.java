
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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;

import javax.swing.JTable;

import association.model.AssociationDataTransfer;

import mail.model.Receipt;

import mail.view.MassDonationReceipt;

import moneybook.model.KassenBuch;

/**
 * class represents MouseListener for the receipt table
 * 
 * @author Leonid Oldenburger
 */
public class ReceiptMouseListener extends MouseAdapter{
	
	private JTable table;
	private MassDonationReceipt mReceiptView;
	private AssociationDataTransfer association_data_transfer;
	private LinkedHashMap<Integer, Receipt> association_receip_map;
	private Receipt receipt;
	
	/**
	 * constructor creates a new mouselistener
	 * 
	 * @param mReceiptV
	 * @param gTable
	 * @param moneyBook
	 * @param adatatrans
	 */
	public ReceiptMouseListener(MassDonationReceipt mReceiptV, JTable gTable, KassenBuch moneyBook, AssociationDataTransfer adatatrans){
		this.table = gTable;
		this.mReceiptView = mReceiptV;
		this.association_data_transfer = adatatrans;
		this.association_receip_map = association_data_transfer.getReceipt();
	}
	
	/**
	 * methode handles mouse events
	 */
	public void mouseClicked(MouseEvent e) {
        if (e.getClickCount()==2) 
        {
    		this.association_receip_map = association_data_transfer.getReceipt();
    		
            int row = table.getSelectedRow();
           
            if(row < association_receip_map.size()-1){

            	int number = Integer.parseInt(((String)table.getValueAt(row, 0)));
            	
            	receipt = association_receip_map.get(number+1);
            	
            	try{
            		mReceiptView.setText1(receipt.getObject1());
            	}catch(NullPointerException ex){}

            	try{
            		mReceiptView.setText2(receipt.getObject2());
            	}catch(NullPointerException ex){}
            	
            	try{
            		mReceiptView.setText3(receipt.getObject3());
            	}catch(NullPointerException ex){}
            	
            	try{
                	if(receipt.getExpense() == 1){
                		mReceiptView.setNoButton(false);
                		mReceiptView.setYesButton(true);
                	}else{
                		mReceiptView.setNoButton(true);
                		mReceiptView.setYesButton(false);      		
                	}
            	}catch(NullPointerException ex){}
            	
            	try{
                	if(receipt.getBase1() == 1){
                		mReceiptView.set1(true);
                		mReceiptView.set2(false);
                	}else if (receipt.getBase1() == 0){
                		mReceiptView.set1(false);
                		mReceiptView.set2(true);
                	}else{
                		mReceiptView.set1(false);
                		mReceiptView.set2(false);
                	}
            	}catch(NullPointerException ex){}

            	try{
                  	if(receipt.getBase2() == 1){
                		mReceiptView.setCheckbox3(true);
                  	}else{
                  		mReceiptView.setCheckbox3(false);
                  	}
            	}catch(NullPointerException ex){}           	
            }
        }
    } 
}