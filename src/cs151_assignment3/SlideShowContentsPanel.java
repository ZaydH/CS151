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
	
	
	private static final String saveButtonCommandName = "SAVE_CONTENT";
	private static final String addNewButtonCommandName = "ADD_NEW_CONTENT";
	
	
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
		JButton[] panelButtons = {addNewButton, saveButton};
		String[]  buttonText = { "Add New Image", "Save Image" };
		String[]  buttonActionCommands = { addNewButtonCommandName, saveButtonCommandName };
		for(int i = 0; i < panelButtons.length; i++){
			panelButtons[i] = new JButton(buttonText[i]);
			panelButtons[i].setActionCommand(buttonActionCommands[i]);
			
			//---- Define the button sizes
			Dimension buttonDimension = new Dimension (width - 2*padding, height);
			panelButtons[i].setSize(buttonDimension);
			panelButtons[i].setPreferredSize(buttonDimension);
			panelButtons[i].setMinimumSize(buttonDimension);
			panelButtons[i].setMaximumSize(buttonDimension);
		}	
		
		
		//----- Create the storage list for the slide show images.
		slideShowListModel = new DefaultListModel<String>();
		slideShowList = new JList(slideShowListModel);
		slideShowList.setLayoutOrientation(JList.VERTICAL); 				 //----- One item per row.	
		slideShowList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //---- Allow only one image to be selected at a time.
		loadSlideShowListFromFileContents();
		
		//---- Create the pane containing the List of Images.
		slideShowFileContents = new SlideShowFileContents();
		slideShowListPane = new JScrollPane(slideShowList);
		int scrollPaneHeight = height - 2*buttonHeight - 4 * padding;//---- 2 * buttonHeight since two buttons.  4 * padding since padding on top and bottom of each button.
		Dimension listPaneDimension = new Dimension (width - 2*padding, scrollPaneHeight);
		slideShowListPane.setSize(listPaneDimension);
		slideShowListPane.setPreferredSize(listPaneDimension);
		slideShowListPane.setMinimumSize(listPaneDimension);
		slideShowListPane.setMaximumSize(listPaneDimension);
		
		
		//---- Setup the locations of the buttons and objects.
		contentsPanelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, saveButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		contentsPanelLayout.putConstraint(SpringLayout.NORTH, saveButton, padding, SpringLayout.NORTH, this);
		
		contentsPanelLayout.putConstraint(SpringLayout.NORTH, slideShowListPane, 0, SpringLayout.SOUTH, saveButton);
		contentsPanelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, slideShowListPane, 0, SpringLayout.HORIZONTAL_CENTER, this);
		
		contentsPanelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addNewButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		contentsPanelLayout.putConstraint(SpringLayout.SOUTH, addNewButton, padding, SpringLayout.SOUTH, this);
		
		
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
