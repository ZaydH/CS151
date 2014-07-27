package cs151_assignment5;

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
import javax.swing.JFileChooser;
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
	private String captionText;
	private JLabel captionLabel;
	private static SlideShowFileContents slideShowFileContents;

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
		
		//---- Set up a blank label.
		captionLabel = new JLabel(captionText);
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
		
		//----- Initialize the slide show file contents
		slideShowFileContents = new SlideShowFileContents();
		
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
	 * 
	 * Listener used to Open a SlideShowFile.
	 * 
	 * @author Zayd
	 *
	 */
	public static class OpenFileContentsPaneListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){

			//---- Do not do anything on an cancelled command
			if(e.getSource() instanceof JFileChooser && e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION) ){
				return;
			}			
			
			final JFileChooser fc = (JFileChooser)e.getSource();//---- Get the file chooser.
			slideShowFileContents.readSlideShowFile(fc.getSelectedFile());
		}
		
	}	
	
}
