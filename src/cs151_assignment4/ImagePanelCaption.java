package cs151_assignment4;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanelCaption extends JLabel {


	/**
	 * 
	 */
	private static final long serialVersionUID = -209353150182293374L;
	JPanel sourcePanel;
	int captionPanelBoundary;
	
	
	public ImagePanelCaption(String captionText, int horizontalAlignment, JPanel sourcePanel, int captionPanelBoundary){
		
		//---- Use the JLabel constructor
		super(captionText, horizontalAlignment);
		
		//---- Store the panel of the object
		this.sourcePanel = sourcePanel;
		
		this.captionPanelBoundary = captionPanelBoundary;
	}
	
	
	/**
	 * Calculates and returns default X location
	 * @return Default X location of the caption.
	 */
	public int getDefaultXLocation(){
		return (sourcePanel.getWidth() - this.getWidth())/2;
	}
	
	/**
	 * Calculates and returns default Y location
	 * @return Default Y location of the caption.
	 */
	public int getDefaultYLocation(){
		return sourcePanel.getHeight() - this.getHeight() - captionPanelBoundary;
	}
	
	/**
	 * This function resets the caption location 
	 */
	public void setToDefaultLocation(){
		this.setLocation(getDefaultXLocation(), getDefaultYLocation());
	}	
	
//	
//	/**
//	 * Process all calls to JLabel's set location.  Handle it manually for this object.
//	 */
//	@Override
//	public void setLocation(int newXLocation, int newYLocation){
//		
//		super.setLocation( newXLocation, newYLocation);
//		this.setBounds((sourcePanel.getWidth() - this.getWidth())/2, sourcePanel.getHeight() - this.getHeight() - 2*captionPanelBoundary, 
//					   this.getWidth(), this.getHeight());		
//		
//	}	
	
	/**
	 * Calculates and returns the minimum X location for the caption label in the image panel
	 * 
	 * @return Minimum X location for the caption label in the panel
	 */
	public int getMinimumXLocation(){ return captionPanelBoundary; }
	
	/**
	 * Calculates and returns the maximum X location for the caption label in the image panel.
	 * 
	 * @return Maximum X Location for the caption label in the panel.
	 */
	public int getMaximumXLocation(){
		int maximumX = sourcePanel.getWidth() - captionPanelBoundary - this.getWidth() ;
		return maximumX;		
	}
		
	
	/**
	 * Calculates and returns the minimum Y location for the caption label in the image panel
	 * 
	 * @return Minimum Y location for the caption label in the panel
	 */	
	public int getMinimumYLocation(){ return captionPanelBoundary; }

	
	/**
	 * Calculates and returns the maximum Y location for the caption label in the image panel.
	 * 
	 * @return Maximum Y Location for the caption label in the panel.
	 */
	public int getMaximumYLocation(){ 
		int maximumY = sourcePanel.getHeight() - captionPanelBoundary - this.getHeight() ;
		return maximumY;
	}
	
	
}
