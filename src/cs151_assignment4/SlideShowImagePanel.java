package cs151_assignment4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
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
	private boolean imagePathIsValid;
	private String captionText;
	private ImagePanelCaption captionLabel;
	private final int CAPTION_OUTER_WIDTH;

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
		captionLabel = new ImagePanelCaption(captionText, JLabel.CENTER, this, panelBorder/2);
		captionLabel.setOpaque(true);
		captionLabel.setForeground(Color.BLACK);
		captionLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		captionLabel.setVisible(true);
		this.add(captionLabel);
		
		//----- Define the caption's size
		Dimension captionLabelDimension = new Dimension( captionWidth, captionHeight );
		captionLabel.setSize(captionLabelDimension);
		captionLabel.setPreferredSize(captionLabelDimension);
		captionLabel.setMinimumSize(captionLabelDimension);
		captionLabel.setMaximumSize(captionLabelDimension);
		
		//---- Set the caption label position
		CAPTION_OUTER_WIDTH = panelBorder;
		captionLabel.setToDefaultLocation();
		this.addMouseInputListenerToCaption();
		
		
		//---- By default the image is invalid.
		imagePathIsValid = false;
		
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
		
		if(!imagePathIsValid) return;
		
		//--- Get the file MIME type
		MimetypesFileTypeMap mtftp = new MimetypesFileTypeMap();
		mtftp.addMimeTypes("image png gif jpg jpeg");
		String mimeType = mtftp.getContentType(new File(imagePath));
		String[] types = mimeType.split("/");
		if(!types[0].equals("image")) return;
		
		
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
				g.drawImage(newImage, (this.getWidth() - newImage.getWidth())/2, (this.getHeight() - newImage.getHeight())/2,  this);
			}
			
			captionLabel.revalidate();
			captionLabel.repaint();
			//captionLabel.paint(g);
			super.paintComponents(g);
			return;
		}
		catch(IOException ex){
			drawImagePanelBackground(g);
			captionLabel.revalidate();
			captionLabel.repaint();
			//captionLabel.paint(g);
			super.paintComponents(g);
			return;
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
									//---- Verify you are able to read the document text.
									imagePath = doc.getText(0, doc.getLength());
									
									//---- Once you have read back the image information, verify if the image is a valid one.
									if(imagePath.equals("")){
										imagePathIsValid = false;
									}
									else if(!imagePath.equals(previousImagePath)){
										try{
											ImageIO.read(new File(imagePath)); //---- Try to read the image.
											imagePathIsValid = true;
										}
										catch(IOException imageBufferingError){
											JOptionPane.showMessageDialog(null, "Error: There was an unrecoverable error loading the image at location \"" + imagePath + "\".");
											imagePathIsValid = false;
										}
									}
									previousImagePath = imagePath;

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
	
	

	
	
	
	
	
	
	
	
	
	
	/**
	 * Creates a MouseListener and MouseMotionListener and adds it to CaptionLabel.
	 */
	private void addMouseInputListenerToCaption(){
	
		//-- Create an anonymous object to listen for mouse motions.
		MouseInputAdapter captionListener = new CaptionLabelMouseInputAdapter(){

				@Override
				public void mouseDragged(MouseEvent e){
					//---- Calculate the new caption locations
					super.mouseDragged(e);
					//----- If the caption moved, update its location
					if(getDidCaptionMove()){
						captionLabel.setLocation(this.getLastX(), this.getLastY());
					}
				}

		};
	
		//----- Create the mouse listener
		captionLabel.addMouseListener(captionListener);
		captionLabel.addMouseMotionListener(captionListener);
		
	}	
	
	
	/**
	 * Adapter class used for tracking mouse motion.
	 * 
	 * @author Zayd
	 */
	public abstract class CaptionLabelMouseInputAdapter extends MouseInputAdapter{

		
		//---- Store initial information about the caption
		private int initialCaptionX;
		private int initialCaptionY;
		
		//---- Store the last mouse position
		private int lastMouseX;
		private int lastMouseY;
		
		//---- Store Caption information
		private int latestCaptionX;
		private int latestCaptionY;
		private boolean captionMoved;
		
		//----
		boolean mouseOutsideValidArea;
		
		/**
		 * Stores the initial X and Y location when the mouse is pressed.
		 */
		@Override
		public void mousePressed(MouseEvent e){
			
			//---- Initially latest and initial position are the same as the the caption's current position
			latestCaptionX = initialCaptionX = captionLabel.getX();
			latestCaptionY = initialCaptionY = captionLabel.getY();
			
			//----- Get the mouse location information
			lastMouseX = e.getXOnScreen();
			lastMouseY = e.getYOnScreen();
			
			//---- By default caption not moved 
			captionMoved = false; 
			
			//---- Mouse still in valid area
			mouseOutsideValidArea = false;
		}
		
		@Override
		public void mouseDragged(MouseEvent e){
			
			//---- Check if the mouse position is invalid
			if(mouseOutsideValidArea && getMousePosition(true) == null) return;
			
			//---- Get the caption location
			Point captionLocation = captionLabel.getLocation();
			int captionXLoc = (int)captionLocation.getX();
			int captionYLoc = (int)captionLocation.getY();
			
			
			//---- Get the newX and newY locations for the caption label
			int newX = captionXLoc + (e.getXOnScreen() - lastMouseX);
			int newY = captionYLoc + (e.getYOnScreen() - lastMouseY);
			//---- Handle the case where the cursor just reentered the valid space
			if(mouseOutsideValidArea){
				
				//--- Handle default case where the mouse did not re-enter the valid space.
				if(newX < captionLabel.getMinimumXLocation() || newX > captionLabel.getMaximumXLocation()) newX =  captionXLoc + (e.getX());
				if(newY < captionLabel.getMinimumYLocation() || newY > captionLabel.getMaximumYLocation()) newY =  captionYLoc + (e.getY());
			}
			
			
			//----- Ensure the mouse is not too far away from the caption
			Point mousePositionInPanel = getMousePosition(true);
			if(mousePositionInPanel != null){
				//----- Ensure the mouse and CaptionLabel are not separated too much in the X direction.
				int mouseComponentXDistance = (int)mousePositionInPanel.getX() - newX;
				if( mouseComponentXDistance < 0 || mouseComponentXDistance > captionLabel.getWidth()){
					newX += mouseComponentXDistance;
				}
				
				//----- Ensure the mouse and CaptionLabel are not separated too much in the Y direction.
				int mouseComponentYDistance = (int)mousePositionInPanel.getY() - newY;
				if( mouseComponentYDistance < 0 || mouseComponentYDistance > captionLabel.getHeight()){
					newY += mouseComponentYDistance;
				}
			}
			//---- Update X location
			if(newX < captionLabel.getMinimumXLocation()){
				newX = captionLabel.getMinimumXLocation();
			}
			else if(newX > captionLabel.getMaximumXLocation()){
				newX = captionLabel.getMaximumXLocation();
			}			
			lastMouseX = e.getXOnScreen();
			latestCaptionX = newX;
			
			
			//---- Update Y location
			if(newY < captionLabel.getMinimumYLocation()){
				newY = captionLabel.getMinimumYLocation();
			}
			else if(newY > captionLabel.getMaximumYLocation()){
				newY = captionLabel.getMaximumYLocation();
			}
			lastMouseY = e.getYOnScreen();
			latestCaptionY = newY;			
		
			
			//----- Check if the caption moved.  May not move if you are at the boundary.
			if(captionXLoc != newX || captionYLoc != newY) captionMoved = true; //---- Mark caption moved.
			
			//---- Check if the mouse left the valid area
			if(getMousePosition(true) == null) 
				mouseOutsideValidArea = true;
			else{
				mouseOutsideValidArea = false;
			}
		}
		
		/**
		 * This function is used to determine the initial X position of the caption before any mouse movement.
		 * 
		 * @return Initial X location when the mouse was clicked
		 */
		public int getInitalX(){ return initialCaptionX; };
		
		/**
		 * This function is used to determine the initial Y position of the caption before any mouse movement.
		 * 
		 * @return Initial Y location when the mouse was clicked
		 */		
		public int getInitalY(){ return initialCaptionY; };
		
		/**
		 * This function is used to determine the final X position of the caption after any mouse movement.
		 * 
		 * @return Final X location when the mouse was released
		 */	
		public int getLastX(){ return latestCaptionX; };
		
		/**
		 * This function is used to determine the final Y position of the caption after any mouse movement.
		 * 
		 * @return Final Y location when the mouse was released
		 */		
		public int getLastY(){ return latestCaptionY; };	
		
		/**
		 * After the mouse has been released, this function is used to determine whether the caption moved. 
		 * @return Boolean value of whether the caption moved during the mouse dragging
		 */
		public boolean getDidCaptionMove(){ return captionMoved; }
		
	}


	/**
	 * Creates and returns an ActionListener object that allows for the user to reset the caption locaiton on certain events.
	 * An example event would be a new slide show.
	 * 
	 * @return ActionListener that can reset the caption location on certain events
	 */
	public ActionListener createResetCaptionLocationListener(){
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				captionLabel.setToDefaultLocation();
			}
		};
		
	}
	
	
	
	
	/**
	 * Helper class to monitor for changes in the list of images and then to update the caption box.
	 * 
	 * @author Zayd
	 *
	 */
	public ListSelectionListener createListSelectionCaptionLocationListener(){
		
		
		return new ListSelectionListener(){
		
			public void valueChanged(ListSelectionEvent e) {
		    	JList imageList = (JList)(e.getSource()); //---- Get the list of images
		    	
		    	//---- If nothing is selected, then do nothing
		    	if(imageList.getSelectedIndex() == -1) return;
		    	
		    	//---- Gets the selected image from the list.
		    	SlideShowImageInstance selectedImage = (SlideShowImageInstance)imageList.getSelectedValue();
		    	
		    	//----- Updates the caption X and Y location
		    	captionLabel.setLocation(selectedImage.getImageCaptionXLocation(), selectedImage.getImageCaptionYLocation());
			}
		};
	}
	
}
