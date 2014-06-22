package cs151_assignment3;

public class SlideShowImageInstance {

	private int id;
	private String imagePath;
	private String imageCaption;
	
	public static final int PARAMETERS_PER_IMAGE_INSTANCE = 2;
	
	/**
	 * Constructor to specify a slide show using an array.
	 * 
	 * @param imageParameters	Array of size PARAMETERS_PER_IMAGE_INSTANCE that contains the information on the Slide Show Image. Parameter 0  is the image path. Parameter 1 is the image caption.
	 */
	public SlideShowImageInstance(int id, String[] imageParameters){
		this.id = id;
		this.imagePath = imageParameters[0];
		this.imageCaption = imageParameters[1];
	}
	
	
	/**
	 * Creates a new slide show instance with the specified file path and caption information.
	 * 
	 * @param imagePath			File path for the new image.
	 * @param imageCaption		Caption for the new image.
	 */
	public SlideShowImageInstance(int id, String imagePath, String imageCaption){
		this.id = id;
		this.imagePath = imagePath;
		this.imageCaption = imageCaption;
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
		
		return imageParameters;
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
