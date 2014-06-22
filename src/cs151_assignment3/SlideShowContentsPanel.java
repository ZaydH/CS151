package cs151_assignment3;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;

public class SlideShowContentsPanel extends JPanel {

	/**
	 * Auto generate UID for this panel.
	 */
	private static final long serialVersionUID = -7959030969928938285L;
	
	private static JButton saveButton;
	private static DefaultListModel<String> slideShowListModel;
	private static JList slideShowList;
	private static JScrollPane slideShowListPane;
	private static SlideShowFileContents slideShowFileContents;
	private static JButton addNewButton;
	
	
	private static final String SAVE_BUTTON_COMMAND_NAME = "SAVE_CONTENT";
	private static final String ADD_NEW_BUTTON_COMMAND_NAME = "ADD_NEW_CONTENT";
	
	
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
		//---- Define the button sizes
		buttonDimension = new Dimension (width - 2*padding, buttonHeight);
		addNewButton.setSize(buttonDimension);
		addNewButton.setPreferredSize(buttonDimension);
		addNewButton.setMinimumSize(buttonDimension);
		addNewButton.setMaximumSize(buttonDimension);
		//--- Add button to this panel.
		this.add(addNewButton);		
		
		
		//----- Create the storage list for the slide show images.
		slideShowListModel = new DefaultListModel<String>();
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
	private void loadSlideShowListFromFileContents(){
		
		slideShowListModel.clear(); //--- Empty the slideShowList.
		
		//---- Load the SlideShow from the Image Instances in the Container class.
		for(int i = 0; i < slideShowFileContents.getNumberOfImageInstances(); i++){
			slideShowListModel.addElement(slideShowFileContents.getImageInstanceCaption(i)); //---- Load the ListModel with image instance "i";
		}
		
	}
	
	
}
