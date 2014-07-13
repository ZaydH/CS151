package cs151_assignment4;

import java.awt.Point;


/**
 * 
 * This file represents a single image instance.  It is how image list files are stored.
 * 
 * @author Zayd
 *
 */

public class SlideShowImageInstance {

	private int id;
	private String imagePath;
	private String imageCaption;
	private int captionXLocation;
	private int captionYLocation;
	
	public static final int DEFAULT_IMAGE_LOCATION = -1;
	public static final int PARAMETERS_PER_IMAGE_INSTANCE = 4;
	
	/**
	 * Constructor to specify a slide show using an array.
	 * 
	 * @param imageParameters	Array of size PARAMETERS_PER_IMAGE_INSTANCE that contains the information on the Slide Show Image. 
	 * 							Parameter 0 is the image path. Parameter 1 is the image caption. 
	 * 							Parameter 2 is the caption's X location. Parameter 3 is the caption's Y location
	 */
	public SlideShowImageInstance(int id, String[] imageParameters){
		this.id = id;
		this.imagePath = imageParameters[0];
		this.imageCaption = imageParameters[1];
		this.captionXLocation = Integer.valueOf(imageParameters[2]);
		this.captionYLocation = Integer.valueOf(imageParameters[3]);
	}
	
	
	/**
	 * Creates a new slide show instance with the specified file path and caption information.
	 * 
	 * @param id				Image ID Number
	 * @param imagePath			File path for the new image.
	 * @param imageCaption		Caption for the new image.
	 * @param captionXLocation	X location of the image caption.
	 * @param captionYLocation	Y location of the image caption.
	 * 
	 */
	public SlideShowImageInstance(int id, String imagePath, String imageCaption, int captionXLocation, int captionYLocation ){
		this.id = id;
		this.imagePath = imagePath;
		this.imageCaption = imageCaption;
		this.captionXLocation = captionXLocation;
		this.captionYLocation = captionYLocation;
	}
	
	/**
	 * Alternate constructor for SlideShowImageInstance that takes the caption location as a point instead of as two integers.
	 * 
	 * @param id				Image ID Number
	 * @param imagePath			File path for the new image.
	 * @param imageCaption		Caption for the new image.
	 * @param captionLocation	Point containing the top left corner of the caption,
	 */
	public SlideShowImageInstance(int id, String imagePath, String imageCaption, Point captionLocation ){
		this.id = id;
		this.imagePath = imagePath;
		this.imageCaption = imageCaption;
		this.captionXLocation = (int)captionLocation.getX();
		this.captionYLocation = (int)captionLocation.getY();
	}
	
	
	/** 
	 * Method to make the class object a string.
	 */
	@Override
	public String toString(){
		String outputString = "Image: " + id + ": " + imageCaption;
		return outputString;
	}
	
	/**
	 * Returns all parameters regarding an image in an array.  Used in the exporter for the images.
	 * 
	 * @return Array of Image Instance Parameters. Parameter 0 is the file path. Parameter 1 is the image caption.
	 */
	public String[] getAllImageInstanceParameters(){
		
		String[] imageParameters = new String[PARAMETERS_PER_IMAGE_INSTANCE];
		imageParameters[0] = imagePath;
		imageParameters[1] = imageCaption;
		imageParameters[2] = Integer.toString(captionXLocation);
		imageParameters[3] = Integer.toString(captionYLocation);
		
		return imageParameters;
	}
	
	
	/**
	 * Gets the ID for the SlideShowImageInstance.
	 * 
	 * @return ID number of the SlideShowImageInstance.
	 */
	public int getImageID(){
		return id;
	}
	
	/**
	 * Change the image path for a particular image.
	 * 
	 * @param imagePath  New file path for this image.
	 */
	public void setImagePath(String imagePath){
		this.imagePath = imagePath;
	}
	
	/**
	 * Get the file path for this image.
	 * 
	 * @return  File path for this slide show instance
	 */
	public String getImagePath(){
		return this.imagePath;
	}
	
	/**
	 * Change the caption for a particular image.
	 * 
	 * @param imagePath  New captions for this image.
	 */
	public void setImageCaption(String imageCaption){
		this.imageCaption = imageCaption;
	}
	
	/**
	 * Get the caption for this image.
	 * 
	 * @return  Caption for this slide show instance
	 */
	public String getImageCaption(){
		return this.imageCaption;
	}
	
	
	/**
	 * Gets the X location for this image caption on the image panel.
	 * 
	 * @return Integer X location of the caption.
	 */
	public int getImageCaptionXLocation(){
		return this.captionXLocation;
	}
	
	
	/**
	 * Sets the new X location for the image caption.
	 * 
	 * @param captionXLocation New X location for the Image
	 */
	public void setImageCaptionXLocation(int captionXLocation){
		this.captionXLocation = captionXLocation;
	}
	
	/**
	 * Gets the Y location for this image caption on the image panel.
	 * 
	 * @return Integer Y location of the caption.
	 */
	public int getImageCaptionYLocation(){
		return this.captionYLocation;
	}
	
	
	/**
	 * 
	 * @return Point containing X and Y location of the image caption
	 */
	public Point getImageCaptionLocation(){
		return new Point(captionXLocation, captionYLocation);
	}
	
	/**
	 * Set the caption location using a Point object.
	 * 
	 * @return Point containing X and Y location of the image caption
	 */
	public void setCaptionLocation(Point captionLocation){
		captionXLocation = (int)captionLocation.getX();
		captionYLocation = (int)captionLocation.getY();		
	}	
	
	
	/**
	 * Sets the new Y location for the image caption.
	 * 
	 * @param captionXLocation New Y location for the Image
	 */
	public void setImageCaptionYLocation(int captionYLocation){
		this.captionYLocation = captionYLocation;
	}
	
	
	/**
	 * Overrides the equals method for the SlideShowImageInstance.
	 * @return True if the two objects are equal false otherwide.
	 */
	@Override
	public boolean equals(Object other){
		
		//--- Verify the other object is not null and has the correct class.
		if(other == null || !(other instanceof SlideShowImageInstance)){
			return false;
		}
		
		//---- Cast the input to the SlideShowImageInstance class the function expects.
		SlideShowImageInstance castedOther = (SlideShowImageInstance)other;
		
		//--- Check whether the image IDs match
		if( this.getImageID() != castedOther.getImageID() ) return false;
		
		//---- Check if the image paths match
		if( !this.getImagePath().equals( castedOther.getImagePath() ) ) return false;
		
		//---- Check if the image captions match
		if( !this.getImageCaption().equals( castedOther.getImageCaption() ) ) return false;
		
		//---- Check if the caption locations match.
		if( !this.getImageCaptionLocation().equals( castedOther.getImageCaptionLocation() ) ) return false;
		
		return true;
	}
	

	/**
	 * Override the clone default clone method of the object. Performs a deep copy.
	 * @return Deep copy of the SlideShowImageInstance	
	 */
	@Override
	public Object clone(){
		
		SlideShowImageInstance newImageInstance = new SlideShowImageInstance(id, imagePath, imageCaption, 
																			 captionXLocation, captionYLocation );
		return (Object)newImageInstance;
	}
	
	
}
