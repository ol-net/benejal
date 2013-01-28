
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
package projects.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Repräsentiert ein Projekt
 * @author Artem Petrov
 *
 */
public class Project {
	
	private String name;
	private double debit;
	private double credit;
	private Project parent;
	private List<Project> child;
	private Date start;
	private Date finish;	
	
	/**
	 * Erzeugt ein Projekt
	 * @param name Projektname
	 * @param parent Vaterprojekt
	 */
	public Project(String name, Project parent) {
		
		this.name = name.trim();
		this.parent = parent;
		this.child = new LinkedList<Project>();
		this.start = new Date();
		this.finish = null;
		
	}
	
	/**
	 * Erzeugt ein Projekt
	 * @param name Projektname
	 * @param parent Vaterprojekt
	 * @param start Projektstart
	 * @param finish Projektende
	 */
	public Project(String name, Project parent, Date start, Date finish) {
		
		this.name = name;
		this.parent = parent;
		this.child = new LinkedList<Project>();
		this.start = start;
		this.finish = finish;
		
	}
	
	/**
	 * Liefert den Projektname zurück
	 * @return Projektname
	 */
	public String getName() {
		
		return this.name;
		
	}
	
	/**
	 * Liefert den Name des Vaterprojekts zurück
	 * @return Name des Vaterprojekts
	 */
	public String getParentName() {
		
		if(this.parent != null)
			return this.parent.getName();
		else return "";
		
	}
	
	/**
	 * Liefert eine Liste mit den untegeordneten Projekten zurück
	 * @return Liste mit den untegeordneten Projekten
	 */
	public List<Project> getChild() {
		
		return this.child;
		
	}
	
	/**
	 * Liefert das Vaterprojekt zurück
	 * @return Vaterprojekt
	 */
	public Project getParent() {
		
		return this.parent;
		
	}
	
	/**
	 * Liefert das Startdatum zurück
	 * @return Startdatum
	 */
	public Date getStart() {
		
		return start;
	
	}
	
	/**
	 * Liefert das Enddatum zurück
	 * @return Enddatum
	 */
	public Date getFinish() {
	
		return finish;
	
	}

	/**
	 * Setzt den Projektname
	 * @param name  Projektname
	 */
	public void setName(String name) {
	
		this.name = name.trim();
	
	}

	/**
	 * Beendet das Projekt 
	 * @param finish Enddatum
	 */
	public void setFinish(Date finish) {
	
		this.finish = finish;
	
	}
	
	/**
	 * Liefert die Summe aller Projektausgaben zurück
	 * @return Summe aller Projektausgaben
	 */
	public double getDebit() {
	
		return this.debit; 
		
	}
	
	/**
	 * Liefert die Summe aller Projekteinnahmen zurück
	 * @return Summe aller Projekteinnahmen
	 */
	public double getCredit() {
		
		return this.credit; 
		
	}

	/**
	 * Liefert die Summe aller Projektausgaben, inkl. Unterprojekte zurück
	 * @return Summe aller Projektausgaben, inkl. Unterprojekte
	 */
	public double getDebitTotal() {
		
		double debitTotal = 0.0;
		
		for (Project p : child) {
			debitTotal += p.getDebit();
			for (Project p1 : p.getChild()) {
				debitTotal += p1.getDebit();
			}
		}
		
		return debitTotal + debit;
	}


	/**
	 * Liefert die Summe aller Projekteinnahmen, inkl. Unterprojekte zurück
	 * @return Summe aller Projekteinnahmen, inkl. Unterprojekte
	 */
	public double getCreditTotal() {

		double creditTotal = 0.0;
		
		for (Project p : child) {
			
			creditTotal += p.getCredit();
			
			for (Project p1 : p.getChild()) {
				
				creditTotal += p1.getCredit();
		
			}
		
		}
		
		return creditTotal + credit;
		
	}
	
	/**
	 * Setzt die Summe aller Projektausgaben
	 * @param debit Summe aller Projektausgaben
	 */
	public void setDebit(double debit) {
	
		this.debit = debit;
		
	}
	
	/**
	 * Setzt die Summe aller Projekteinnahmen
	 * @param debit Summe aller Projekteinnahmen
	 */
	public void setCredit(double credit) {
	
		this.credit = credit;
		
	}
	
}
