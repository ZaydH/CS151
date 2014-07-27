package cs151_assignment5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * This class the set of images for the class and manages their import and export.
 * 
 * @author Zayd
 *
 */

public class SlideShowFileContents {

	private ArrayList<SlideShowImageInstance> allImages;
	public static final String FILE_EXTENSION = "slideshow";
	public static final String FILE_KEY_SEPARATOR = "\\^";
	
	
	/**
	 * Constructor for the SlideShowFile contents.  
	 */
	public SlideShowFileContents(){
		allImages = new ArrayList<SlideShowImageInstance>(); //---- Create an array list of images.
	}
	
	
	/*
	 * Adds a new image instance with no data.
	 */
	public void addNewImageInstance(){
		allImages.add(new SlideShowImageInstance(allImages.size()+1, "", "", 
												 SlideShowImageInstance.DEFAULT_IMAGE_LOCATION,
												 SlideShowImageInstance.DEFAULT_IMAGE_LOCATION ) );
	}
	
	
	/**
	 * Get the number of image instances in the active slide show.
	 * 
	 * @return	Number of Images in this Slide Show.
	 */
	public int getNumberOfImageInstances(){
		return allImages.size();
	}
	
	
	
	/**
	 * Gets a individual image instance.
	 * 
	 * @param index	Index of the image instance to retrieve
	 * @return		Image instance at the specified index "i".
	 */
	public SlideShowImageInstance getImageInstance(int index){
		if( index < 0 || index >= allImages.size()) return null;
		
		return allImages.get(index);
	}
	
	
	/**
	 * Gets the caption for the image at the specified index.
	 * 
	 * @param index		Index of the image instance
	 * @return			Caption for image at the "index"
	 */
	public String getImageInstanceCaption(int index){
		if( index < 0 || index >= allImages.size()) return null;
		
		return allImages.get(index).getImageCaption();
	}
	
	
	/**
	 * Update an image instance in the file contents data structure.
	 * 
	 * @param index			Index of the image to be updated
	 * @param imagePath		Path to the image file
	 * @param imageCaption	Caption for the image file.
	 */
	public void setImageInstance(int index, String imagePath, String imageCaption, int captionXLocation, int captionYLocation){
		
		allImages.set(index, new SlideShowImageInstance(index + 1, imagePath, imageCaption, captionXLocation, captionYLocation ) );
		
	}
	
	/**
	 * Sets an image instance based off a passed in image instance.
	 * 
	 * @param newInstance New Image instance to be used to override the previous instance
	 */
	public void setImageInstance(int index, SlideShowImageInstance newInstance){
		
		allImages.set(index, newInstance );
		
	}
	
	
	
	
	/**
	 * Empty the list of images.
	 */
	public void clear(){
		allImages.clear();
	}
	
	
	
	/**
	 * Imports Slide show information from a file.
	 * 
	 * @param filePath		Path of the SlideShow File to read.
	 * @return				If the file read was success, returns true.  Otherwise, it returns false.
	 */
	public boolean readSlideShowFile(File filePath){
		
		Scanner fileIn;
		ArrayList<SlideShowImageInstance> bufferImageList = new ArrayList<SlideShowImageInstance>();
		String[] imageFileParameters;
		String fileLine;
		int imageCnt=1;
		
		//---- Check to see if the file extension is correct.
		String lowercaseFilePath = filePath.toString().toLowerCase();
		if(!lowercaseFilePath.endsWith("." + FILE_EXTENSION)){
			@SuppressWarnings("unused")
			Thread t = new JOptionPaneThreaded("Incorrect File Extension.  Please specify a valid file and try again.");
			return false;
		}
		
		//----- Once the file extension has been verified, try opening the file.
		try{
			fileIn = new Scanner(new FileReader(filePath));

			if( !fileIn.hasNextLine() ){
				new JOptionPaneThreaded("A slideshow must have at least one image.", "INSUFFICIENT IMAGES ERROR");
				fileIn.close(); //---- Close the scanner.
				return false;
			}
			
			//---- Read until the end of the file.
			while( fileIn.hasNextLine() ){
				
				fileLine = fileIn.nextLine(); //--- Read the next line.
				imageFileParameters = fileLine.split( FILE_KEY_SEPARATOR ); //--- Split the string.
				
				//---- Ensure the string split properly.
				if(imageFileParameters.length != SlideShowImageInstance.PARAMETERS_PER_IMAGE_INSTANCE ){
					new JOptionPaneThreaded("Fatal Error: Row #" + imageCnt + " of the image file has the incorrect "
											+ "number of parameters.", "FILE FORMAT ERROR");
					fileIn.close(); //---- Close the scanner.
					return false;
				}
				
				//---- Ensure the index, X, and Y all resolve.
				try{
					bufferImageList.add( new SlideShowImageInstance(imageFileParameters) );
				}
				catch(NumberFormatException e){
					new JOptionPaneThreaded("The slideshow file appears to be corrupted.\n"
											+ "Some of the parameters (e.g. image ID, X Location, Y Location, etc.)\b"
											+ "could not be parsed.  Check the file, and try again.");
					fileIn.close(); //---- Close the scanner.
					return false;
				}
				
				
				//---- Increment the number of images.
				imageCnt++;
			}
			
			//---- Valid file so update the file information.
			fileIn.close(); //---- Close the scanner.
			allImages = bufferImageList; //---- Update the list of images.
			return true;
		}
		catch(FileNotFoundException e){
			new JOptionPaneThreaded("No file with the specified name exists.  Please specify a valid file and try again.");
			return false;
		}

	}
	
	
	

}
