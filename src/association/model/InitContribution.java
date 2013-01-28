
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
package association.model;

import java.util.Date;
import java.util.List;

import member.model.Member;
import moneybook.model.Beitrag;
import moneybook.model.KassenBuch;
/**
 * class to init contribution
 * 
 * @author Leonid Oldenburger
 */
public class InitContribution{
	
	private KassenBuch money_book;
	private AssociationDataTransfer association_data_transfer;
	private Member member;
	private List<Beitrag> list;
	
	private Date start_date;
	private Date end_date;

	/**
	 * constuctor
	 * 
	 * @param mem member id
	 */
	public InitContribution(int mem){
		this.association_data_transfer = AssociationDataTransfer.getInstance();
		this.member = association_data_transfer.getMemberByID(mem);
		this.money_book = KassenBuch.getInstance("jdbc:derby:.//derbydbs;create=true");
	}
	
	/**
	 * init contribution
	 */
	public void initContribution(){
		
		double periodsum = 0;
		periodsum = getPeriodSum(member.getNumber());
		
		if(member.getSumContribution() - periodsum <= 0){

			association_data_transfer.updateContributionMember(0.0, member.getNumber());
			association_data_transfer.updateContributionDid(0, member.getNumber());
			association_data_transfer.updateStatusMember(Member.BEZAHLT, member.getNumber());
		}else{
			association_data_transfer.updateContributionMember(member.getSumContribution() - periodsum, member.getNumber());
			
			if (member.getPayStatus() == Member.BEZAHLT && member.getStatus() == Member.FEST){
				member.setPayStatus(Member.FAELLIG);
				association_data_transfer.updateMember(member);
			} else if (member.getPayStatus() == Member.EMPTY && member.getStatus() == Member.FEST){
				member.setPayStatus(Member.FAELLIG);
				association_data_transfer.updateMember(member);
			}
		}
		association_data_transfer.ok();
	}
	
	/**
	 * methode get periode sum from member
	 * 
	 * @param number
	 * @return
	 */
	public Double getPeriodSum(int number){

		start_date = new Interval(association_data_transfer).getStartDate();
		end_date = new Interval(association_data_transfer).getEndDate();

		double sum = 0;
		int i;
		int contribution_did = 0;
		list = money_book.getMemberPremiums(member.getNumber());
		
		for (i= 0; i < money_book.getMemberPremiums(number).size(); i++){

			if (start_date.getTime() <= money_book.getMemberPremiums(number).get(i).getBookingDate().getTime() && 
				end_date.getTime() >= money_book.getMemberPremiums(number).get(i).getBookingDate().getTime() && 
				member.getContributionDid() <= i){
				sum = list.get(0).getBetrag();
				contribution_did++;
			}
		}
		association_data_transfer.updateContributionDid(contribution_did + member.getContributionDid(), member.getNumber());
		return sum;
	}
}