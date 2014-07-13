package cs151_assignment4;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import cs151_assignment4.SlideShowImagePanel.CaptionLabelMouseInputAdapter;


/**
 * Helper panel that contains the slide show list and buttons to add and save items in the list.
 * 
 * @author Zayd
 *
 */

public class SlideShowContentsPanel extends JPanel implements ActionListener {

	/**
	 * Auto generate UID for this panel.
	 */
	private static final long serialVersionUID = -7959030969928938285L;
	
	private static JButton saveButton;
	private static DefaultListModel<SlideShowImageInstance> slideShowListModel;
	private static JList slideShowList;
	private static JScrollPane slideShowListPane;
	private static SlideShowFileContents slideShowFileContents;
	private static JButton addNewButton;
	private static String captionText;
	private static String fileBrowserText;
	private Point captionLocation;
	Object undoBuffer;
	
	
	private static final String SAVE_BUTTON_COMMAND_NAME = "SAVE_CONTENT";
	private static final String ADD_NEW_BUTTON_COMMAND_NAME = "ADD_NEW_CONTENT";

	//----- Add final variables to connect listeners.
	public static final int ADD_NEW_IMAGE_LISTENER = 0;
	public static final int SAVE_IMAGE_LISTENER = 1;

	/**
	 * 
	 * Sole constructor for the Slide Show Contents Panel.
	 * 
	 * @param width			Width of the Slide Show Contents Panel
	 * @param height		Height of the Slide Show Contents Panel.
	 * @param padding		Padding (i.e. spacing) between items in the panel.
	 * @param buttonHeight	Height of any JButtons in this Panel
	 */
	public SlideShowContentsPanel(int width, int height, int padding, int buttonHeight){
		
		//---- Use the SpringLayout for this Panel
		SpringLayout contentsPanelLayout = new SpringLayout();
		this.setLayout(contentsPanelLayout);
		
		//----- Set the panel information.
		Dimension panelDimension = new Dimension(width, height);
		this.setSize(panelDimension);
		this.setPreferredSize(panelDimension);
		this.setMinimumSize(panelDimension);
		this.setMaximumSize(panelDimension);
		
		
		//---- Caption and path are blank by default.
		captionText = "";
		fileBrowserText = "";
		
		//----- Setup the save button 
		saveButton = new JButton("Save Image");
		saveButton.setActionCommand(SAVE_BUTTON_COMMAND_NAME);
		saveButton.addActionListener(this);
		//---- Define the button sizes
		Dimension buttonDimension = new Dimension (width - 2*padding, buttonHeight);
		saveButton.setSize(buttonDimension);
		saveButton.setPreferredSize(buttonDimension);
		saveButton.setMinimumSize(buttonDimension);
		saveButton.setMaximumSize(buttonDimension);
		//--- Add button to this panel.
		this.add(saveButton);
		
		
		//---- Setup the Add New Button
		addNewButton = new JButton("Add New Image");
		addNewButton.setActionCommand(ADD_NEW_BUTTON_COMMAND_NAME);
		addNewButton.addActionListener(this);
		//---- Define the button sizes
		buttonDimension = new Dimension (width - 2*padding, buttonHeight);
		addNewButton.setSize(buttonDimension);
		addNewButton.setPreferredSize(buttonDimension);
		addNewButton.setMinimumSize(buttonDimension);
		addNewButton.setMaximumSize(buttonDimension);
		//--- Add button to this panel.
		this.add(addNewButton);	
		
		
		
		//----- Create the storage list for the slide show images.
		slideShowListModel = new DefaultListModel<SlideShowImageInstance>();
		slideShowFileContents = new SlideShowFileContents();
		slideShowList = new JList(slideShowListModel);
		slideShowList.setLayoutOrientation(JList.VERTICAL); 				 //----- One item per row.	
		slideShowList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //---- Allow only one image to be selected at a time.
		loadSlideShowListFromFileContents();
		addListSelectionCaptionLocationListener();
		slideShowList.addListSelectionListener(new RepaintListSelectionListener());
		
		//---- Create the pane containing the List of Images.
		slideShowListPane = new JScrollPane(slideShowList);
		int scrollPaneHeight = height - 2*buttonHeight - 4 * padding;//---- 2 * buttonHeight since two buttons.  4 * padding since padding on top and bottom of each button.
		Dimension listPaneDimension = new Dimension (width - 2*padding, scrollPaneHeight);
		slideShowListPane.setSize(listPaneDimension);
		slideShowListPane.setPreferredSize(listPaneDimension);
		slideShowListPane.setMinimumSize(listPaneDimension);
		slideShowListPane.setMaximumSize(listPaneDimension);
		this.add(slideShowListPane);
		
		
		//---- Setup the locations of the buttons and objects.
		contentsPanelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, saveButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		contentsPanelLayout.putConstraint(SpringLayout.NORTH, saveButton, padding, SpringLayout.NORTH, this);
		
		contentsPanelLayout.putConstraint(SpringLayout.NORTH, slideShowListPane, padding, SpringLayout.SOUTH, saveButton);
		contentsPanelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, slideShowListPane, 0, SpringLayout.HORIZONTAL_CENTER, this);
		
		contentsPanelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addNewButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		contentsPanelLayout.putConstraint(SpringLayout.NORTH, addNewButton, padding, SpringLayout.SOUTH, slideShowListPane);
		
		
	}
	

	/**
	 * Helper method to load the Slide Show List from the object "slideShowFileContents".  This code is needed a couple of times so made a function.
	 */
	private static void loadSlideShowListFromFileContents(){
		
		slideShowListModel.clear(); //--- Empty the slideShowList.
		
		//---- Load the SlideShow from the Image Instances in the Container class.
		for(int i = 0; i < slideShowFileContents.getNumberOfImageInstances(); i++){
			//addElementToListModel(slideShowFileContents.getImageInstanceCaption(i), false); //---- Load the ListModel with image instance "i", but do not select it.
			slideShowListModel.addElement(slideShowFileContents.getImageInstance(i));
		}
		
	}
	
	
	/**
	 * Allows for outside objects to listen on the addNewImage and saveImageButtons.
	 * 
	 * @param listener		Listener to be added to the selected button.
	 * @param listenerType	Button Type to be Listened On.
	 */
	public void addActionListener(ActionListener listener, int listenerType){
		
		if(listenerType == ADD_NEW_IMAGE_LISTENER ){
			addNewButton.addActionListener(listener);
			return;
		}
		else if(listenerType == SAVE_IMAGE_LISTENER ){
			saveButton.addActionListener(listener);
			return;
		}
		
	}
	
	/**
	 * Adds a list selection listener to the list contain the set of images.
	 * 
	 * @param listener Listener to be tied to the list in this panel.
	 */
	public void addListSelectionListener(ListSelectionListener listener){
		slideShowList.addListSelectionListener(listener);
	}
	
	
	/**
	 * Internal function to handle all action listeners (e.g. Add New button, Save Image button, etc.) for this panel.
	 */
	public void actionPerformed(ActionEvent e){
		
		if(e.getActionCommand().equals(ADD_NEW_BUTTON_COMMAND_NAME)){
			
			
			//---- Create a new image
			slideShowFileContents.addNewImageInstance();		
			int newImageIndex = slideShowFileContents.getNumberOfImageInstances()-1;
			
			//--- Add the element and select it and make sure it is visible.
			slideShowListModel.addElement(slideShowFileContents.getImageInstance(newImageIndex));
			slideShowList.setSelectedIndex(newImageIndex); //---- Uses base 0 so subtract one
			slideShowList.ensureIndexIsVisible(newImageIndex); 
			
			//---- Set a default caption location.
    		captionLocation = new Point(SlideShowImageInstance.DEFAULT_IMAGE_LOCATION,
										SlideShowImageInstance.DEFAULT_IMAGE_LOCATION);
		}
		else if(e.getActionCommand().equals(SAVE_BUTTON_COMMAND_NAME)){
			int selectedIndex = slideShowList.getSelectedIndex();
			//---- Check if any image was selected.
			if(selectedIndex  == -1){
				JOptionPane.showMessageDialog(null, "Before a changes can be saved to a slideshow, a specific image must be selected \n"
													+ "from the list of images.  Please select an image from the list, and try again.");
				return;
			}
			
			//----- Store the previous image instance
			SlideShowImageInstance previousImageInstance = slideShowFileContents.getImageInstance(selectedIndex);
			SlideShowImageInstance newImageInstance = new SlideShowImageInstance( selectedIndex+1, fileBrowserText, captionText,
																				  captionLocation);
			
			//---- If no change in the image instance, do nothing.  Do not add to undo queue.
			if(previousImageInstance.equals(newImageInstance)) return;
			
			//----- Create a command for executing and undoing the save command.
			GUICommand newSaveImageCommand = new SaveGUICommand(newImageInstance, previousImageInstance);
			newSaveImageCommand.execute();
			((SlideShowJMenuBar)undoBuffer).addCommandToUndoBuffer(newSaveImageCommand);
			
//			//----- Update the image instance information
//			slideShowFileContents.setImageInstance(selectedIndex, fileBrowserText, captionText, 
//												   (int)captionLocation.getX(), (int)captionLocation.getY());
//			slideShowListModel.setElementAt(slideShowFileContents.getImageInstance(selectedIndex), selectedIndex);
			
		}
		
	}

	
	/**
	 * 
	 * Listener used to reset the ContentPane.
	 * 
	 * @author Zayd
	 *
	 */
	public static class ResetContentsPaneListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			slideShowFileContents.clear();		//---- Clear the file contents
			slideShowListModel.clear(); 		//--- Empty the slideShowList.
		}
		
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
			if(slideShowFileContents.readSlideShowFile(fc.getSelectedFile())){
				loadSlideShowListFromFileContents();
				slideShowList.clearSelection();
				//---- Per the use case select the first image in the list.
				if(slideShowList.getModel().getSize() > 0){
					slideShowList.setSelectedIndex(0);
				}
			}
		}
		
	}	
	
	
	/**
	 * 
	 * Listener used to Save a SlideShow to a File.
	 * 
	 * @author Zayd
	 *
	 */
	public static class SaveFileContentsPaneListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){

			//---- Do not do anything on an cancelled command
			if(e.getSource() instanceof JFileChooser && e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION) ){
				return;
			}			
			
			final JFileChooser fc = (JFileChooser)e.getSource();//---- Get the file chooser.
			slideShowFileContents.writeSlideShowFile(fc.getSelectedFile());
		}
		
	}		
	
	
	
	/**
	 * 
	 * DocumentListener class to get changes in the Caption Text Field
	 * 
	 * @author Zayd
	 *
	 */
	public static class CaptionListener implements DocumentListener { 
		/**
		 * Function handles document updates (specifically insertions) from a JTextField in the Caption Panel.
		 */
		public void insertUpdate(DocumentEvent e){
			extractDocumentText(e);	
		}
	
		/**
		 * Function handles document updates (specifically removals) from a TextField in the Caption Panel.
		 */
		public void removeUpdate(DocumentEvent e){
			extractDocumentText(e);
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
		private void extractDocumentText(DocumentEvent e){
			Document doc = e.getDocument();
			try {
				captionText = doc.getText(0, doc.getLength());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}			
		}
	}
	
	
	/**
	 * 
	 * DocumentListener class to get changes in the FileBrowser Text Field
	 * 
	 * @author Zayd
	 *
	 */
	public static class FileBrowserListener implements DocumentListener { 
		/**
		 * Function handles document updates (specifically insertions) from a JTextField in the File Browser  Panel.
		 */
		public void insertUpdate(DocumentEvent e){
			extractDocumentText(e);	
		}
	
		/**
		 * Function handles document updates (specifically removals) from a TextField in the File Browser Panel.
		 */
		public void removeUpdate(DocumentEvent e){
			extractDocumentText(e);
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
		private void extractDocumentText(DocumentEvent e){
			Document doc = e.getDocument();
			try {
				fileBrowserText = doc.getText(0, doc.getLength());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}			
		}
	}	
	
	
	/**
	 * 
	 * Allows for the panel to be repainted on a list selection.
	 * 
	 * @author Zayd
	 *
	 */
	final public class RepaintListSelectionListener implements ListSelectionListener {
	    public void valueChanged(ListSelectionEvent e) {
	    	slideShowListPane.revalidate();
	    	slideShowListPane.repaint();
	    	revalidate();
	    	repaint();
	    	slideShowListPane.paintAll(slideShowListPane.getGraphics());
	    }		
	}
	
	
	
	/**
	 * Creates a mouse motion listener to extract the caption location for saving to the file.
	 */
	public void addMouseInputListenerToCaption(SlideShowImagePanel imagePanel){
	
		//-- Create an anonymous object to listen for mouse motions.
		MouseInputAdapter captionListener = imagePanel.new CaptionLabelMouseInputAdapter(){

				@Override
				public void mouseReleased(MouseEvent e){
					super.mouseReleased(e);
					
					if(this.getDidCaptionMove()){
						captionLocation = this.getFinalCaptionLocation();
					}
					
				}

		};
		imagePanel.addCaptionMouseInputListener(captionListener);
		
	}	
	

	
	
	
	
	/**
	 * Helper class to monitor for changes in the list of images and then to update the caption box.
	 * 
	 * @author Zayd
	 *
	 */
	public void addListSelectionCaptionLocationListener(){
		
		
		ListSelectionListener contentsPanelListSelectionListener =  new ListSelectionListener(){
		
										public void valueChanged(ListSelectionEvent e) {
									    	JList imageList = (JList)(e.getSource()); //---- Get the list of images
									    	
									    	//---- If nothing is selected, then do nothing
									    	if(imageList.getSelectedIndex() == -1){
									    		captionLocation = new Point(SlideShowImageInstance.DEFAULT_IMAGE_LOCATION,
									    									SlideShowImageInstance.DEFAULT_IMAGE_LOCATION);	
									    		return;
									    	}
									    	
									    	//---- Gets the selected image from the list.
									    	SlideShowImageInstance selectedImage = (SlideShowImageInstance)imageList.getSelectedValue();
									    	
									    	//----- Updates the caption X and Y location
									    	captionLocation = selectedImage.getImageCaptionLocation();
										}
									};
		addListSelectionListener(contentsPanelListSelectionListener);
	}
	
	
	/**
	 * Sets the internal reference to the Undo Buffer.
	 * 
	 * @param undoBuffer  Reference to the Undo Buffer.
	 */
	public void setUndoBufferReference(Object undoBuffer){
		this.undoBuffer = undoBuffer;
	}
	
	
	
	/**
	 * 
	 * This class is used to Save and Unsave Image Instance Saves.
	 * 
	 * @author Zayd
	 *
	 */
	private class SaveGUICommand implements GUICommand{
		
		private SlideShowImageInstance previousImageInstance;
		private SlideShowImageInstance newImageInstance;
		
		/**
		 * 
		 * @param newImageInstance		New Image Image Instance to be set.
		 * @param previousImageInstance	Previous Image Image Instance to be set.
		 */
		public SaveGUICommand(SlideShowImageInstance newImageInstance, 
							  SlideShowImageInstance previousImageInstance){
			//---- Copy make clones of the input parameters.
			this.newImageInstance = (SlideShowImageInstance)(newImageInstance.clone());
			this.previousImageInstance = (SlideShowImageInstance)(previousImageInstance.clone());
		}

		@Override
		public void execute() {
			slideShowFileContents.setImageInstance(newImageInstance);
			slideShowListModel.setElementAt(slideShowFileContents.getImageInstance(newImageInstance.getImageID()), 
											newImageInstance.getImageID());
		}

		@Override
		public void undo() {			
			slideShowFileContents.setImageInstance(previousImageInstance);
			slideShowListModel.setElementAt(slideShowFileContents.getImageInstance(previousImageInstance.getImageID()), 
											previousImageInstance.getImageID());			
		}
		
	}
	
}
