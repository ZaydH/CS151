package cs151_assignment3;

public class SlideShowImageInstance {

	private String imagePath;
	private String imageCaption;
	
	/**
	 * Creates a new slide show instance with the specified file path and caption information.
	 * 
	 * @param imagePath			File path for thie new image.
	 * @param imageCaption		Caption for the new image.
	 */
	public SlideShowImageInstance(String imagePath, String imageCaption){
		this.imagePath = imagePath;
		this.imageCaption = imageCaption;
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
	
	
}
