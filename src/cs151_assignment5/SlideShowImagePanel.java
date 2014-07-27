package cs151_assignment5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



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
	private static String imagePath;
	private static JButton fileOpenSuccessful; //---- This is used to signal other components to enable on a successful file open.
	private static JLabel captionLabel;
	private static int currentSlideIndex = 0;
	private static SlideShowFileContents slideShowFileContents;
	private static int slideDelay = -1; //--- Delay in milliseconds

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
		captionLabel = new JLabel( "", JLabel.CENTER);
		captionLabel.setOpaque(true);
		captionLabel.setForeground(Color.BLACK);
		captionLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		captionLabel.setVisible(false);
		this.add(captionLabel);
		
		//---- This is used to signal other components to enable on a successful file open.
		fileOpenSuccessful = new JButton("false");
		
		
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
			super.paintComponents(g);
			return;
		}
		catch(IOException ex){
			drawImagePanelBackground(g);
			captionLabel.revalidate();
			captionLabel.repaint();
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
	 * Sets the location of the caption. 
	 * If a location would cause the caption to appear off screen, the
	 * function adjusts the location to ensure the caption remains in the panel boundaries.
	 * 
	 * @param xLocation		Specified X-Location
	 * @param yLocation		Specified Y-Location
	 */
	private void setCaptionLocation(int xLocation, int yLocation ){
		
		//---- Ensure the X location is valid.
		if( xLocation < panelBorder)
			xLocation = panelBorder;
		else if(xLocation > this.getWidth() - captionLabel.getWidth() - panelBorder)
			xLocation = this.getWidth() - captionLabel.getWidth() - panelBorder;
		
		//---- Ensure the Y location is valid.
		if( yLocation < panelBorder)
			yLocation = panelBorder;
		else if(yLocation > this.getHeight() - captionLabel.getHeight() - panelBorder)
			yLocation = this.getHeight() - captionLabel.getHeight() - panelBorder;
		
		//--- Update the caption location.
		captionLabel.setLocation(xLocation, yLocation);
		
	}
	
	
	/**
	 * Calculates and sets the font size to ensure it fits in the JLabel.
	 */
	private void calculateFontSize(){
		
		//---- Calculate the font parameters
		Font captionLabelFont = captionLabel.getFont();
		
		//----- Determine if any changes are needed to font due to the width
		int captionLabelTextWidth = captionLabel.getFontMetrics(captionLabelFont).stringWidth( captionLabel.getText() );
		int captionFixedWidth = captionLabel.getWidth();
		double widthFontRatio = (double)captionFixedWidth/captionLabelTextWidth;
		int widthFontSize = (int)Math.floor(widthFontRatio * captionLabelFont.getSize()); //---- Calculate the new font size if only width is considered
		
		//----- Use the smaller of component height or font size
		int newFontSize = (widthFontSize < captionLabel.getHeight())? widthFontSize : (int)(0.8*captionLabel.getHeight()) ;
		newFontSize *= 0.9;//---- Give extra padding on the sides.
		captionLabel.setFont( new Font(captionLabelFont.getFontName(), Font.PLAIN, newFontSize) ); //--- Update the font with the new size.
		
	}
	
	
	
	/**
	 * Creates an ActionListener for the open JFileChooser.
	 * 
	 * @return	ActionListener for the open file JFileChooser.
	 */
	public ActionListener createOpenFileChooserListener(){
		
		return new OpenFileContentsPaneListener();
		
	}
	
	
	/**
	 * Adds listeners to determine when a show file was successfully opened.
	 * 
	 * @param listener  Listener that will use this field to determine when to be enabled.
	 */
	public void addSuccessfulFileOpenActionListener(ActionListener listener){
		fileOpenSuccessful.addActionListener( listener );
	}
	
	
	
	/**
	 * 
	 * Listener used to Open a SlideShowFile.
	 * 
	 * @author Zayd
	 *
	 */
	public class OpenFileContentsPaneListener implements ActionListener {
		
		private ActionEvent actionEvent;
		
		/**
		 * Action listener stores the action event and th
		 */
		public void actionPerformed(ActionEvent e){
			this.actionEvent = e;
			
			/**
			 * Class checks the JFileChooser results and if appropriate opens and parses the input file.
			 * 
			 * @author Zayd
			 *
			 */
			class OpenFileContentsThread extends Thread{
			
				//---- Constructor automatically starts the thread.
				public OpenFileContentsThread(){
					super();
					this.start();
				}
				
				//----- Parse the file as a thread.
				public void run(){
		
					//---- Do not do anything on an cancelled command
					if(actionEvent.getSource() instanceof JFileChooser && actionEvent.getActionCommand().equals(JFileChooser.CANCEL_SELECTION) ){
						return;
					}			
					
					final JFileChooser fc = (JFileChooser)actionEvent.getSource();//---- Get the file chooser.
					
					//----- Read the specified image file.
					if(slideShowFileContents.readSlideShowFile(fc.getSelectedFile())){
						
						//---- This is used to signal other components to enable on a successful file open.
						fileOpenSuccessful.doClick();
						
						//---- Display the first slide.
						currentSlideIndex = 0;
						SlideShowImageInstance imageInstance = slideShowFileContents.getImageInstance( currentSlideIndex );
						
						//----- Set the image path
						imagePath = imageInstance.getImagePath();
						
						//---- Setup the caption label.
						captionLabel.setText( imageInstance.getImageCaption() );
						calculateFontSize();
						setCaptionLocation(imageInstance.getImageCaptionXLocation(), imageInstance.getImageCaptionYLocation());
						captionLabel.setVisible(true);
						
					}//---- if(slideShowFileContents.readSlideShowFile(fc.getSelectedFile()))
				}//---public void run(){
			}//----class OpenFileContentsThread extends Thread{
		
			//---- Create the thread and it auto starts
			OpenFileContentsThread fileContentsThread = new OpenFileContentsThread();
		}//---public void actionPerformed(ActionEvent e){
	}	
	
	
	/**
	 * Creates and returns a listener to update the measure delay based off the slider state.
	 * 
	 * @return Listener that updates the slide measure delay.
	 */
	public ChangeListener createSliderChangeListener(){
		
		return new ChangeListener(){
			
			@Override
			public void stateChanged(ChangeEvent e){
				//--- Get the slider object
				JSlider slider = (JSlider)e.getSource();
				//----- Only update the slide delay when the slider is released.
				if (!slider.getValueIsAdjusting())
					slideDelay = slider.getValue();
			}
			
		};
		
	}
	

}
