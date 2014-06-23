package cs151_assignment3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;



/**
 * 
 * Displays the selected image and caption on the screen as specified by the user.
 * 
 * @author Zayd
 *
 */

public class SlideShowImagePanel extends JPanel {

	/**
	 * Auto generated UID from the system
	 */
	//SpringLayout imagePanelLayout;
	private static final long serialVersionUID = 6847898081534233006L;
	private int panelBorder; 
	private String imagePath;
	private String previousImagePath;
	private String captionText;
	private JLabel captionLabel;

	

	/**
	 * 
	 * Sole constructor for the Image Panel containing the image and the panel.
	 * 
	 * @param panelWidth		Width of the panel
	 * @param panelHeight		Height of the panel
	 * @param panelBorder		Width of the black border around the image panel
	 * @param captionWidth		Width of the caption below the image
	 * @param captionHeight		Height of the caption below the image.
	 */
	public SlideShowImagePanel(int panelWidth, int panelHeight, int panelBorder, int captionWidth, int captionHeight){
		
		//---- Setup the panel's layout.
		super();
		this.setLayout(null);
		
		//---- Fix the size of the image panel
		Dimension panelDimension = new Dimension(panelWidth, panelHeight);
		this.setSize(panelDimension);
		this.setPreferredSize(panelDimension);
		this.setMinimumSize(panelDimension);
		this.setMaximumSize(panelDimension);
		
		//---- Store the panel border
		this.panelBorder = panelBorder;
		
		//---- No image by default.
		imagePath = "";
		previousImagePath="";
		
		//---- Set up a blank label.
		captionText = "";
		captionLabel = new JLabel(captionText, JLabel.CENTER);
		captionLabel.setOpaque(false);
		captionLabel.setForeground(Color.BLACK);
		captionLabel.setVisible(true);
		this.add(captionLabel);
		
		//----- Define the caption's size
		Dimension captionLabelDimension = new Dimension( captionWidth, captionHeight );
		captionLabel.setSize(captionLabelDimension);
		captionLabel.setPreferredSize(captionLabelDimension);
		captionLabel.setMinimumSize(captionLabelDimension);
		captionLabel.setMaximumSize(captionLabelDimension);
		//imagePanelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, captionLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
		

		this.setLayout(null);
		
	}
	
	

	/**
	 * Method to redraw the SlideShowImagePanel.  If an image is specified, it resizes it (if necessary) then draws it to the panel.
	 * If no image is specified, it draws a blank panel.
	 */
	@Override
	public void paint(Graphics g){

		//----- Always draw the background when repainting.
		drawImagePanelBackground(g);
		
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
			
			captionLabel.revalidate();
			captionLabel.repaint();
			//captionLabel.paint(g);
			super.paintComponents(g);
			return;
		}
		catch(FileNotFoundException ex){
			if(!previousImagePath.equals(imagePath)){
				previousImagePath = imagePath;
				JOptionPane.showMessageDialog(null, "Error: The file \"" + imagePath + "\" does not exist.");				
			}
			//----- Need to redraw the background due to the JOptionPane.
			drawImagePanelBackground(g);			
		}
		catch(IOException ex){
			if(!previousImagePath.equals(imagePath)){
				previousImagePath = imagePath;
				JOptionPane.showMessageDialog(null, "Error: There was an unrecoverable error loading the image at location \"" + imagePath + "\".");				
			}			
			//----- Need to redraw the background due to the JOptionPane.
			drawImagePanelBackground(g);
		}			

	}
	
	
	
	/**
	 * Help function to redraw the background for the image panel with a black border and white center.
	 * 
	 * @param g		Graphics that comes from the panel's "paint" method.
	 */
	private void drawImagePanelBackground(Graphics g){
		
		//---- Create a border on this Panel.
		g.setColor(Color.BLACK);		
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//---- Redraw the entire panel.
		g.setColor(Color.WHITE);
		g.fillRect((int)Math.ceil(panelBorder/2), (int)Math.ceil(panelBorder/2), this.getWidth()-panelBorder, this.getHeight()-panelBorder);
		
		captionLabel.setLocation((this.getWidth() - captionLabel.getWidth())/2, this.getHeight() - captionLabel.getHeight() - 3 * panelBorder);
		captionLabel.setBounds((this.getWidth() - captionLabel.getWidth())/2, this.getHeight() - captionLabel.getHeight() - 3 * panelBorder, 
							   captionLabel.getWidth(), captionLabel.getHeight());
		
		captionLabel.revalidate();
		captionLabel.repaint();
		//captionLabel.paint(g);
		super.paintComponents(g);
	}

	/**
	 * Function that creates and returns a DocumentListener for monitoring changes in the the ImagePath.
	 * 
	 * @return	DocumentListener that updates the Image Path and In Turn the Image in the Panel
	 */
	public DocumentListener createImagePathDocumentListener(){
		
		
		return new DocumentListener(){
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
									previousImagePath = "";
									imagePath = doc.getText(0, doc.getLength());
									revalidate();
									repaint();
									invalidate();
								} catch (BadLocationException e1) {
									e1.printStackTrace();
								}			
							}
						};
	}
	
	
	
	
	
	/**
	 * Creates and Returns an Anonymous DocumentListener that monitors for Changes in the Caption and then Updates the Panel.
	 * 
	 * @return	Anonymous DocumentListener for
	 */
	public DocumentListener createCaptionDocumentListener(){
		
		return new DocumentListener(){
			/**
			 * Function handles document updates (specifically insertions) from a TextField in the CaptionPanel.
			 */
			public void insertUpdate(DocumentEvent e){
				updateCaptionAndRepaint(e);	
			}

			/**
			 * Function handles document updates (specifically removals) from a TextField in the CaptionPanel.
			 */
			public void removeUpdate(DocumentEvent e){
				updateCaptionAndRepaint(e);
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
			private void updateCaptionAndRepaint(DocumentEvent e){
				Document doc = e.getDocument();
				try {
					//--- Get the caption text.
					captionText = doc.getText(0, doc.getLength());
					captionLabel.setText(captionText);
					
					//---- Calculate the font parameters
					Font captionLabelFont = captionLabel.getFont();
					
					//----- Determine if any changes are needed to font due to the width
					int captionLabelTextWidth = captionLabel.getFontMetrics(captionLabelFont).stringWidth(captionText);
					int captionFixedWidth = captionLabel.getWidth();
					double widthFontRatio = (double)captionFixedWidth/captionLabelTextWidth;
					int widthFontSize = (int)Math.floor(widthFontRatio * captionLabelFont.getSize()); //---- Calculate the new font size if only width is considered
					
					//----- Use the smaller of component height or font size
					int newFontSize = (widthFontSize < captionLabel.getHeight())? widthFontSize : (int)(0.8*captionLabel.getHeight()) ;
					newFontSize *= 0.9;//---- Give extra padding on the sides.
					captionLabel.setFont( new Font(captionLabelFont.getFontName(), Font.PLAIN, newFontSize) ); //--- Update the font with the new size.
					
					revalidate();
					repaint();
					invalidate();
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}			
			}			
			
		};
		
		
	}
	

	
	
}
