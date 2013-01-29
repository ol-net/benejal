
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
package utils.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import moneybook.model.KassenBuch;
import moneybook.model.Payment;


public class MT940parser implements KontoauszugParser {

	private List<Payment> zahlungen;
	private String filename;
	private KassenBuch kassenbuch;
	
	public MT940parser(String filename, KassenBuch kassenbuch) {
		this.zahlungen = new LinkedList<Payment>();
		this.filename = filename;
		this.kassenbuch = kassenbuch;
		parse();
	}
	
	public List<Payment> getZahlungen() {
		
		setRecorded();
		return this.zahlungen;
		
	}
	
	private void setRecorded() {
		
		Iterator<Payment> it = zahlungen.iterator();
		
		while(it.hasNext()) {
			Payment p = it.next();
			if (kassenbuch.isPaymentRecorded(p)) {
				p.setVerbucht();
			}
		}
		
	}
	
	private void parse() {
		
		BufferedReader reader = null; 
		try 
		{ 
		  reader = new BufferedReader(new FileReader(filename)); 
		 
		  // Felder 61 und 86 einlesen und parsen
		  String s = "";
		  String f61, f86;
		  f61 = f86 = null;
		  
		  while ((s = reader.readLine()) != null) {
	
			  if(s.startsWith(":61:")) {
				  if(f61 != null) {
					  parseSatz(f61, f86);
					  f86 = null;
				  }
				  f61 = s.substring(4);
			  }
			  
			  else if(s.startsWith(":86:") && f61 != null) {
				  f86 = s.substring(4);
			  }
			  
			  else if (!s.startsWith(":") && f86 != null && !s.equals("-")) {
				  f86 = f86.concat(s);
			  }
			  
		  }
		  // Letzten Satz parsen
		  parseSatz(f61, f86);
		  		    
		} 
		catch ( IOException e ) { 
			e.printStackTrace();
		  System.err.println( "Fehler beim Lesen der Datei!" ); 
		} 

		finally { 
		  try { reader.close(); } catch ( Exception e ) { } 
		}
		
	}
	
	
	@SuppressWarnings("deprecation")
	private void parseSatz(String f61, String f86) {

		String[] lines;
		
		// Umsatzzeile (Feld :61:)
		Date wertStellDatum = null;
		Date buchungsDatum = null;		
		double betrag = 0.0;
		boolean haben = false;
		// Mehrzweckfeld (Feld :86:)
//		int gvc;
		long kto = 0;
		long blz = 0;
		String zweck = "";
		String name = "";
		
		// Buchungsdatum
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat df2 = new SimpleDateFormat("MMdd");
		
		try {

			wertStellDatum = df.parse(f61.substring(0, 6));
			
			// TODO 1.1 dieses Feld ist optional
//			try {
	
				buchungsDatum = df2.parse(f61.substring(6, 10));
	
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
			
			buchungsDatum.setYear(wertStellDatum.getYear());
			
			if (buchungsDatum.before(wertStellDatum)) {
				buchungsDatum.setYear(wertStellDatum.getYear() + 1);
			}
			
//			System.out.println(datum);
			// TODO 1.2 in Abhängigkeit von df2 behandeln
			f61 = f61.substring(10);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Haben/soll
		// TODO RC/RD (storno Credit/Debit)
		if(f61.startsWith("C")) {
			haben = true;
		}
		
//		System.out.println("char at 1: " + f61.toUpperCase().charAt(1));
		// und hier war bug :), dises Feld ist auch optional
		if (f61.toUpperCase().charAt(1) == 'R') {
			f61 = f61.substring(2);
		}
		else {
			f61 = f61.substring(1);
		}
		
		// Betrag
		String b = f61.substring(0, f61.indexOf('N'));
		b = b.replaceAll(",", ".");
		betrag = Double.valueOf(b);
		
		if(!haben) {
			betrag *= -1.0;
		}
		
		
		lines = f86.split(Pattern.quote(f86.charAt(3) + ""));
		
		// Geschäftsvorfallcode
//		gvc = Integer.valueOf(lines[0]);
		
		for(int i = 1; i < lines.length; i++) {

			// Verwendungszweck (Feldschlüssel 20-29 und 60-63)
			if(lines[i].startsWith("2") || lines[i].startsWith("6")) {
				zweck = zweck.concat(" " + lines[i].substring(2));
			}
			// BLZ (Feldschlüssel 30)
			else if(lines[i].startsWith("30")) {
				blz = Long.valueOf(lines[i].substring(2));
			}
			// KTO (Feldschlüssel 31)
			else if(lines[i].startsWith("31")) {
				kto = Long.valueOf(lines[i].substring(2));
			}
			// Name (Feldschlüssel 32-33)
			else if(lines[i].startsWith("32") || lines[i].startsWith("33")) {
				name = name.concat(" " + lines[i].substring(2));
			}
		}
		
		// TODO GVC???
		zahlungen.add(new Payment(wertStellDatum, kto, blz, betrag, name.trim(), zweck.trim(), ""));
		
	}

}
