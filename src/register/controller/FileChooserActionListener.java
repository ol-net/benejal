
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
package register.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import association.model.Cropping;

import register.view.RegisterAssociationConfig;

/**
 * class to choose a file
 * 
 * @author Leonid Oldenburger
 */
public class FileChooserActionListener implements ActionListener {
	
	private JFileChooser chooser;
	private RegisterAssociationConfig associationconfig;
	
	/**
	 * constructor 
	 * 
	 * @param aConfig
	 */
	public FileChooserActionListener(RegisterAssociationConfig aConfig){
		this.associationconfig = aConfig;
	}
	
	/**
	 * methode handels events 
	 */
	public void actionPerformed(ActionEvent event) {
        
		if(associationconfig.getDirButton() == event.getSource()){
			
			chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
			try {
				int option = chooser.showOpenDialog(null);
            
				if(option == JFileChooser.APPROVE_OPTION){
					associationconfig.setDirText(chooser.getSelectedFile().getPath()+"\\");
					//System.out.println(chooser.getSelectedFile().getPath());
				}
				else{
					associationconfig.setDirText("");
				}
			} catch (NullPointerException e){}
		}
		
		if(associationconfig.getLogoButton() == event.getSource()){
			
			chooser = new JFileChooser();

			try {
				int option = chooser.showOpenDialog(null);
            
				if(option == JFileChooser.APPROVE_OPTION){
			        File file = new File(chooser.getSelectedFile().toString());
			        Cropping test = new Cropping(ImageIO.read(file), "logo.jpg");
			        test.load();
				}
			} catch (NullPointerException e){} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(associationconfig.getSignatureButton() == event.getSource()){
			
			chooser = new JFileChooser();

			try {
				int option = chooser.showOpenDialog(null);
            
				if(option == JFileChooser.APPROVE_OPTION){
			        File file = new File(chooser.getSelectedFile().toString());
			        Cropping test = new Cropping(ImageIO.read(file), "signature.jpg");
			        test.load();
				}
			} catch (NullPointerException e){} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}