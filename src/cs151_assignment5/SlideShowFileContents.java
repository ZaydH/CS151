package cs151_assignment5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;


/**
 * This class the set of images for the class and manages their import and export.
 * 
 * @author Zayd
 *
 */

public class SlideShowFileContents {

	private ArrayList<SlideShowImageInstance> allImages;
	public static final String FILE_EXTENSION = "slideshow";
	
	
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
	public void setImageInstance(SlideShowImageInstance newInstance){
		
		allImages.set(newInstance.getImageID()-1, newInstance );
		
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
		int numberImageFiles;
		ArrayList<SlideShowImageInstance> bufferImageList;
		String[] imageFileParamters = new String[SlideShowImageInstance.PARAMETERS_PER_IMAGE_INSTANCE];
		
		//---- Check to see if the file extension is correct.
		String lowercaseFilePath = filePath.toString().toLowerCase();
		if(!lowercaseFilePath.endsWith("." + FILE_EXTENSION)){
			Thread t = new JOptionPaneThreaded("Incorrect File Extension.  Please specify a valid file and try again.");
			return false;
		}
		
		//----- Once the file extension has been verified, try opening the file.
		try{
			fileIn = new Scanner(new FileReader(filePath));
			

			//----- Ensure the first element of the file is the number of elements.
			if(!fileIn.hasNextInt()){
				new JOptionPaneThreaded("Invalid file format.  Please specify a valid file and try again.");
				fileIn.close(); //---- Close the scanner.
			}

			//---- Get the number of files in the list.
			//numberImageFiles = Integer.parseInt(fileIn.nextLine());
			numberImageFiles = fileIn.nextInt();
			if(numberImageFiles > 0) fileIn.nextLine();//---- Read the newline.
			
			//---- Create the ArrayList.
			if(numberImageFiles > 0) bufferImageList = new ArrayList<SlideShowImageInstance>(numberImageFiles);
			else bufferImageList = new ArrayList<SlideShowImageInstance>();
			
			//----- Read the image information from the file.
			for(int i = 0; i < numberImageFiles; i++){
				
				//----- Iterate through the elements in a possible 
				for(int j = 0; j < SlideShowImageInstance.PARAMETERS_PER_IMAGE_INSTANCE; j++){
					//---- Verify the file is still valid.
					if(!fileIn.hasNextLine()){
						new JOptionPaneThreaded("The slideshow file appears to be missing data or is corrupted.\n"
												+ "Please specify a new file and try again.");
						fileIn.close(); //---- Close the scanner.
						return false;
					}
					else{
						//---- Load image parameters.
						imageFileParamters[j] = fileIn.nextLine();
						if(imageFileParamters[j].equals(" ")) imageFileParamters[j] = "";//---- Handle the null space case needed by the parser
					}
				}
				
				//---- Add the item to the list.
				bufferImageList.add(new SlideShowImageInstance(i+1, imageFileParamters));
			}
			
			
			//---- Ensure the file length is as expected.  If there is more text, thats a problem so ignore the file.
			if(fileIn.hasNextLine()){
				new JOptionPaneThreaded("The slideshow file appears to have too much data or is corrupted.\n"
										+ "Please specify a new file and try again.");				
				fileIn.close();
				return false;
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
