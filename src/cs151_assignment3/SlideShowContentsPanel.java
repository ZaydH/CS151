package cs151_assignment3;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

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
	
	
	private static final String SAVE_BUTTON_COMMAND_NAME = "SAVE_CONTENT";
	private static final String ADD_NEW_BUTTON_COMMAND_NAME = "ADD_NEW_CONTENT";

	//----- Add final variables to connect listeners.
	public static final int ADD_NEW_IMAGE_LISTENER = 0;
	public static final int SAVE_IMAGE_LISTENER = 1;

	
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
	 * Internal function to handle action listeners in this class.
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
		}
		else if(e.getActionCommand().equals(SAVE_BUTTON_COMMAND_NAME)){
			//TODO fix the lack of a save button command support.
			int selectedIndex = slideShowList.getSelectedIndex();
			//---- Check if any image was selected.
			if(selectedIndex  == -1){
				JOptionPane.showMessageDialog(null, "No image was selected to save.  Select an image and try again.");
				return;
			}
			
			//----- Update the image instance information
			slideShowFileContents.setImageInstance(selectedIndex, fileBrowserText, captionText);
			slideShowListModel.setElementAt(slideShowFileContents.getImageInstance(selectedIndex), selectedIndex);
			
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
	
}
