package cs151_assignment3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.JFrame;


public class SlideShowImagePanel extends JPanel implements DocumentListener {

	/**
	 * Autogeneral UID from the system
	 */
	private static final long serialVersionUID = 6847898081534233006L;
	private int panelBorder; 
	private String imagePath;
	

	public SlideShowImagePanel(int width, int height, int border){
		
		//---- Fix the size of the image panel
		Dimension panelDimension = new Dimension(width, height);
		this.setSize(panelDimension);
		this.setPreferredSize(panelDimension);
		this.setMinimumSize(panelDimension);
		this.setMaximumSize(panelDimension);
		
		//---- Store the panel border
		panelBorder = border;
		
		//---- No image by default.
		imagePath = "";
		
	}
	
	

	
	public void paint(Graphics g){

		//---- Redraw the entire panel.
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if(imagePath.equals("")) return;
		
		//---- Load an Image from file
		try{
			BufferedImage newImage = ImageIO.read(new File(imagePath));
			
			//---- Calculate the ratio of the width and height of the image versus the width and height of the panel.
			double imageWidthRatio = (double)newImage.getWidth()/(this.getWidth()-2*panelBorder);
			double imageHeightRatio = (double)newImage.getHeight()/(this.getHeight()-2*panelBorder);
			
			//----- Check if the image is taller or wider than the panel.  If so, resize
			if(imageWidthRatio > 1 || imageHeightRatio > 1){
				
				double maxRatio = (imageWidthRatio > imageHeightRatio)? imageWidthRatio: imageHeightRatio; //--- Get the maxRatio
				int newWidth = (int)(newImage.getWidth()/maxRatio);
				int newHeight = (int)(newImage.getHeight()/maxRatio);

				Image originalImage = Toolkit.getDefaultToolkit().getImage(imagePath);					
				
				//---- draw the scaled image
				g.drawImage(originalImage, (this.getWidth() - newWidth)/2, (this.getHeight() - newHeight)/2, newWidth, newHeight, this); 
			}
			else{
				//---- Center with respect to both width and height as the image is smaller than the panel.
				g.drawImage(newImage, (this.getWidth() - newImage.getWidth())/2, (this.getHeight() - newImage.getHeight())/2,  null);
			}
		}
		catch(FileNotFoundException ex){
			JOptionPane.showMessageDialog(null, "Error: The file \"" + imagePath + "\" does not exist.");
		}
		catch(IOException ex){
			JOptionPane.showMessageDialog(null, "Error: There was an unrecoverable error loading the image at location \"" + imagePath + "\".");
		}			

	}
	
		
	/**
	 * Function handles document updates (specifically insertions) from a TextField in the FileBrowserPanel.
	 */
	public void insertUpdate(DocumentEvent e){
		updatePathAndRepaint(e);	
	}

	/**
	 * Function handles document updates (specifically removals) from a TextField in the FileBrowserPanel.
	 */
	public void removeUpdate(DocumentEvent e){
		updatePathAndRepaint(e);
	}
	
	
	/**
	 *  changedUpdate does not Apply for Text Fields.  
	 *  This function is implemented due to the interface type's requirements. It does nothing.
	 */
	public void changedUpdate(DocumentEvent e){
	}		
	
	/**
	 * Helper method used to handle document update actions.
	 * 
	 * @param e DocumentEvent passed by removeUpdate or insertUpdate methods.
	 */
	private void updatePathAndRepaint(DocumentEvent e){
		Document doc = e.getDocument();
		try {
			imagePath = doc.getText(0, doc.getLength());
			revalidate();
			repaint();
			this.invalidate();
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}			
	}
	
	
//	public void addActionListener(ActionListener listener){
//		this.addActionListener(listener);
//	}
	
}
