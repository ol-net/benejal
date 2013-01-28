
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

import java.io.File;

import javax.swing.JFileChooser;

import javax.swing.filechooser.FileFilter;

public class OpenFile {
	
	private AssociationDataTransfer associationDataTransfer;
	
	public OpenFile(AssociationDataTransfer aDataTransfer){
	
		associationDataTransfer = aDataTransfer;
		
		JFileChooser  fileChooser = new JFileChooser(associationDataTransfer.getAssociation().getDataDir());
		
		//deaktiviere die "Zeige Alle" Option
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		//setze den Filter
		fileChooser.setFileFilter(new FileFilter(){
			
		//Dieser Text wird unter "Dateityp" angezeigt
		String description = "PDF Dateien (*.pdf)";
		
		//Dateiendung
		String extension = "pdf";
		
		public boolean accept(File file){
			
			if(file == null) return false;
			//zeigt alle Ordner an
			if(file.isDirectory()) return true;
			//gibt nur Dateien mit .pdf Erweiterung aus
			return file.getName().toLowerCase(). endsWith(extension);
		}
		//hier wird unsere Beschreibung gesetzt
		public String getDescription(){
			return description;
		}
		});
		
		//zeige Dialog und mache etwas, wenn"Öffnen" geklickt wurde
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			
			//Pfad + Datei
			String filePath = fileChooser.getSelectedFile().toString();

			Runtime rt = Runtime.getRuntime();
			try{
				@SuppressWarnings("unused")
				Process p = rt.exec( "rundll32" +" " + "url.dll,FileProtocolHandler"
                     + " " + filePath);
			}catch (Exception e1){
				e1.printStackTrace();
			}
		}
	}
}
