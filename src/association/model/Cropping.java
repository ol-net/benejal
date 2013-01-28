
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
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import com.mortennobel.imagescaling.ResampleOp;

import association.view.BackgroundPanel;
import association.view.ButtonDesign;
import association.view.InfoDialog;

/**
 * class to crop an image
 * 
 * @author Leonid Oldenburger
 */
public class Cropping extends JPanel{

	private static final long serialVersionUID = 1L;
	BufferedImage image;
    Dimension size;
    Rectangle clip;
    private Cropping test;
    private ClipMover mover;
    private JFrame f;
    private int width;
    private int heigh;
    private JScrollPane pane;
    private int xnull;
    private int ynull;
    private String file_name;
    private int wNew;
    private int hNew;
    private int image_size_w;
    private int image_size_h;
    
    public Cropping(BufferedImage image, String filename){
        
    	this.file_name = filename;
    	this.image = image;
        this.size = new Dimension(image.getWidth(), image.getHeight());
        this.test = this;
        this.xnull = 0;
        this.ynull = 0;
        this.image_size_w = image.getWidth();
        this.image_size_h = image.getHeight();
        
        if(filename.endsWith("logo.jpg")){
            this.width = 160;
            this.heigh = 70;
        	this.wNew = 210;
        	this.hNew = 100;
        }else if(filename.endsWith("signature.jpg")){
            this.width = 420;
            this.heigh = 40;
        	this.wNew = 320;
        	this.hNew = 80;
        }
        
    }
 
    protected void paintComponent(Graphics g){
    	
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int x = (getWidth() - size.width)/2;
        int y = (getHeight() - size.height)/2;
        g2.drawImage(image, x, y, this);

            if(clip == null)
                createClip();
            g2.setPaint(Color.red);
            g2.draw(clip);
    }
 
    public void setClip(int x, int y){
    	
        // keep clip within raster
        int x0 = (getWidth() - size.width)/2;
        int y0 = (getHeight() - size.height)/2;
        
        this.xnull = clip.x;
        this.ynull = clip.y;
        
        if(x < x0 || x + clip.width  > x0 + size.width ||
           y < y0 || y + clip.height > y0 + size.height)
            return;
        clip.setLocation(x, y);
        repaint();
    }
 
    public Dimension getPreferredSize(){
    	
        return size;
    }
 
    private void createClip(){
    	
        clip = new Rectangle(width, heigh);

        if(xnull == 0 && ynull == 0){
        	clip.x = (getWidth() - clip.width)/2;
        	clip.y = (getHeight() - clip.height)/2;
        }else{
        	clip.x = xnull;
        	clip.y = ynull; 
        }
    }
 
    private void clipImage(){
    	
        BufferedImage clipped = null;
        
        try{
            int w = clip.width;
            int h = clip.height;
            int x0 = (getWidth()  - size.width)/2;
            int y0 = (getHeight() - size.height)/2;
            int x = clip.x - x0;
            int y = clip.y - y0;
            clipped = image.getSubimage(x, y, w, h);
        }
        catch(RasterFormatException rfe){
            new InfoDialog("Image-Datei liegt außerhalb des gültigen Bereichs!");
            return;
        }
        //Image scaledImage = scaleImage(clipped, wNew, hNew);

        ResampleOp  resampleOp = new ResampleOp (wNew, hNew);
        //resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
        BufferedImage outImg = resampleOp.filter(clipped, null);
        
//        Image scaledImage = clipped.getScaledInstance(wNew, hNew,
//                Image.SCALE_SMOOTH);
        
//        BufferedImage outImg = new BufferedImage(wNew, hNew,
//                BufferedImage.TYPE_INT_RGB);
//       
//        Graphics g = outImg.getGraphics();
//        g.drawImage(scaledImage, 0, 0, null);
//        g.dispose();

        try{
        	ImageIO.write(outImg, "jpg", new File(file_name));
        }catch(Exception e){
        	System.out.println(e);
        }
        
        f.dispose();
    }
 
    private JPanel getUIPanel(){
    	
        f.setTitle("Logo auswählen");

        JButton clip = new ButtonDesign("images/save_icon.png");
        
        clip.addActionListener(new ActionListener(){
        	
            public void actionPerformed(ActionEvent e){
            	clipImage();
            }
        });
        
        JButton minus = new JButton("-");
        
        minus.addActionListener(new ActionListener(){
        	
            public void actionPerformed(ActionEvent e){
            	
                if(file_name.endsWith("logo.jpg")){
                	if (width > 140)
                		width = width - 10;
                	if(heigh > 70)
                		heigh = heigh - 5;
                }else if(file_name.endsWith("signature.jpg")){
                	if (width > 400)
                		width = width - 10;
                	if(heigh > 25)
                		heigh = heigh - 5;
                }
            	
				remove();	
				
				test.createClip();
                	
		        pane = new JScrollPane(test);

                test.addMouseListener(mover);
                test.addMouseMotionListener(mover);
		        f.add(pane);
		        f.add(getUIPanel(), "South");
		        
		        f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                f.setVisible(true);   
            }
        });
        
        JButton plus = new JButton("+");
        
        plus.addActionListener(new ActionListener(){
        	
            public void actionPerformed(ActionEvent e){
            	
                if(file_name.endsWith("logo.jpg")){
                	if (width < image_size_w)
                		width = width + 10;
                	if(heigh < image_size_h)
                		heigh = heigh + 5;
                }else if(file_name.endsWith("signature.jpg")){
                	if (width < image_size_w)
                		width = width + 10;
                	if(heigh < image_size_h)
                		heigh = heigh + 5;
                }
               
				remove();
            	
				test.createClip();
				
		        pane = new JScrollPane(test);

                test.addMouseListener(mover);
                test.addMouseMotionListener(mover);
                
		        f.add(pane);
		        f.add(getUIPanel(), "South");
		        
		        f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                f.setVisible(true);   
            }
        });
        
        JPanel panel = new BackgroundPanel();
        //panel.add(clipBox);
        panel.setOpaque(false);
        panel.add(clip);
        panel.add(plus);
        panel.add(minus);
        return panel;   
    }
    
    
    public void load(){
        
        mover = new ClipMover(this);
        
        test.addMouseListener(mover);
        test.addMouseMotionListener(mover);
        
        f = new JFrame();
		f.setMinimumSize(new Dimension(800, 560)); 
        
        pane = new JScrollPane(this);
        f.add(pane);
        f.add(test.getUIPanel(), "South");
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    public static void main(String[] args) throws IOException{
    	
        File file = new File("rubin.jpg");
        Cropping test = new Cropping(ImageIO.read(file), "logo.jpg");
        test.load();
    }
    
    public void remove(){
    	f.remove(pane);
    	f.remove(getUIPanel());
    }
}
 
class ClipMover extends MouseInputAdapter{
	
    Cropping cropping;
    Point offset;
    boolean dragging;
 
    public ClipMover(Cropping c){
        cropping = c;
        offset = new Point();
        dragging = false;
    }
 
    public void mousePressed(MouseEvent e){
    	
        Point p = e.getPoint();
        if(cropping.clip.contains(p)){
        	
            offset.x = p.x - cropping.clip.x;
            offset.y = p.y - cropping.clip.y;
            dragging = true;
        }
    }
 
    public void mouseReleased(MouseEvent e){
        dragging = false;
    }
 
    public void mouseDragged(MouseEvent e){
    	
        if(dragging){
            int x = e.getX() - offset.x;
            int y = e.getY() - offset.y;
            cropping.setClip(x, y);
        }
    }
}