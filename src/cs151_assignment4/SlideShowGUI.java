package cs151_assignment4;


import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;



/**
 * 
 * Main class that runs and loads the GUI.
 * 
 * @author Zayd
 *
 */

public class SlideShowGUI {

	
	private static JFrame mainGUI;
	private static SpringLayout mainGUILayout;
	private static SlideShowJMenuBar topMenu;
	
	private static JPanel leftGUIPanel;
	private static SlideShowCaptionPanel captionPanel;
	private static SlideShowFileBrowserPanel fileBrowserPanel;
	private static SlideShowContentsPanel fileContentsPanel;
	
	private static SlideShowImagePanel imagePanel;

	private static final int STANDARD_PADDING = 5;
	
	//---- Enumerate constants regarding GUI Height
	private static final int TOP_MENU_HEIGHT = 23;
	private static final int PANELS_HEIGHT = 700;
	private static final int GUI_HEIGHT = PANELS_HEIGHT;
	private static final int FILE_BROWSER_PANEL_HEIGHT = 37;
	private static final int CAPTION_BROWSER_PANEL_HEIGHT = 37; 
	private static final int LEFT_PANEL_SPACING_BETWEEN_MENU_BAR = 5 * STANDARD_PADDING;
	private static final int LEFT_PANEL_VERTICAL_SPACING = 3 * STANDARD_PADDING;
	private static final int IMAGE_CAPTION_HEIGHT = 40;
	private static final int FILE_CONTENTS_PANEL_HEIGHT = PANELS_HEIGHT - FILE_BROWSER_PANEL_HEIGHT - CAPTION_BROWSER_PANEL_HEIGHT 
														  - LEFT_PANEL_SPACING_BETWEEN_MENU_BAR - 3*LEFT_PANEL_VERTICAL_SPACING;

	
	
	//---- Enumerate constants regarding GUI Width
	private static final int LEFT_PANEL_WIDTH = 400;
	private static final int BROWSE_BUTTON_WIDTH = LEFT_PANEL_WIDTH/4;
	private static final int GUI_WIDTH = LEFT_PANEL_WIDTH + PANELS_HEIGHT;
	private static final int RIGHT_PANEL_WIDTH = GUI_WIDTH-LEFT_PANEL_WIDTH;
	private static final int LEFT_PANEL_LABEL_WIDTH = 70;
	private static final int IMAGE_CAPTION_WIDTH = RIGHT_PANEL_WIDTH - 200;
	
	

	/**
	 * Main input into the Slide Show Gui.
	 * 
	 * @param args Not used.
	 */
	public static void main(String args[]){
		
		mainGUI = new JFrame();
		
		//----- Make the menu bar. Needs to be done first to get its height.
		topMenu = new SlideShowJMenuBar();
		Dimension menuDimension = new Dimension(GUI_WIDTH, TOP_MENU_HEIGHT);
		topMenu.setSize(menuDimension);
		topMenu.setPreferredSize(menuDimension);
		topMenu.setMinimumSize(menuDimension);
		topMenu.setMaximumSize(menuDimension);
		mainGUI.setJMenuBar(topMenu);		
		
		//----- Initialize the main variables
		setupMainGUIWindow();
		
		//---- Create the left panel of the GUI
		createLeftPanel();
		
		//----- Create the right panel of the GUI
		createImagePanel();
		
		//---- Make the GUI visible
		mainGUI.pack();
		mainGUI.setVisible(true);//---- Make the GUI visible

	}
	
	
	
	/**
	 * Sets the parameters for the GUI JFrame including its size and layout.
	 */
	private static void setupMainGUIWindow(){
		
		mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //---- Ensure the execution ends when the GUI is closed.
		
		//---- Set GUI sizing information
		Dimension contentPaneDimension = new Dimension(GUI_WIDTH, GUI_HEIGHT);
		mainGUI.getContentPane().setSize( contentPaneDimension );
		mainGUI.getContentPane().setPreferredSize( contentPaneDimension );
		mainGUI.getContentPane().setMinimumSize( contentPaneDimension );
		mainGUI.getContentPane().setMaximumSize( contentPaneDimension );
		//mainGUI.setUndecorated(true);
		mainGUI.setResizable(false);
		
		//mainGUI.setLayout(new BorderLayout());
		mainGUILayout = new SpringLayout();
		mainGUI.setLayout(mainGUILayout);
	}

	/**
	 * Creates the JPanels on the left side of the GUI including the Caption Panel, File Browser Panel, and the File Contents/Image List Panel.
	 */
	private static void createLeftPanel(){
		
		//----- Create the left panel
		leftGUIPanel = new JPanel();
		Dimension panelDimension = new Dimension (LEFT_PANEL_WIDTH, PANELS_HEIGHT);
		leftGUIPanel.setSize(panelDimension);
		leftGUIPanel.setMinimumSize(panelDimension);
		leftGUIPanel.setPreferredSize(panelDimension);
		leftGUIPanel.setMaximumSize(panelDimension);		
		SpringLayout leftPanelLayout = new SpringLayout();
		leftGUIPanel.setLayout(leftPanelLayout);
		
		//---- Add the fileBrowserPanel
		fileBrowserPanel = new SlideShowFileBrowserPanel(LEFT_PANEL_WIDTH, FILE_BROWSER_PANEL_HEIGHT, LEFT_PANEL_LABEL_WIDTH,  
												STANDARD_PADDING, BROWSE_BUTTON_WIDTH);
		topMenu.addActionListener(new SlideShowFileBrowserPanel.ResetFileBrowserListener(), SlideShowJMenuBar.NEW_FILE_LISTENER);		//---- Listen for New File Actions on MenuBar
		//topMenu.addActionListener(new FileBrowserPanel.ResetFileBrowserListener(), SlideShowJMenuBar.OPEN_FILE_LISTENER);		//---- Listen for Open File Actions on MenuBar
		leftGUIPanel.add(fileBrowserPanel);	
		//----- Set the position of the file browser padding
		leftPanelLayout.putConstraint( SpringLayout.NORTH, fileBrowserPanel, LEFT_PANEL_SPACING_BETWEEN_MENU_BAR, SpringLayout.NORTH, leftGUIPanel);
		leftPanelLayout.putConstraint( SpringLayout.HORIZONTAL_CENTER, fileBrowserPanel, 0, SpringLayout.HORIZONTAL_CENTER, leftGUIPanel);
		
		
		//----- Make the Pane storing the caption information		
		captionPanel = new SlideShowCaptionPanel(LEFT_PANEL_WIDTH, CAPTION_BROWSER_PANEL_HEIGHT, LEFT_PANEL_LABEL_WIDTH, STANDARD_PADDING);
		topMenu.addActionListener(new SlideShowCaptionPanel.ResetCaptionListener(), SlideShowJMenuBar.NEW_FILE_LISTENER);		//---- Listen for New File Actions on MenuBar
		//topMenu.addActionListener(new CaptionPanel.ResetCaptionListener(), SlideShowJMenuBar.OPEN_FILE_LISTENER);		//---- Listen for Open File Actions on MenuBar	
		//fileBrowserPanel.addBrowseFileChooserListener(new CaptionPanel.ResetCaptionListener()); 							//---- Reset the Caption when browsing for a new file.
		leftGUIPanel.add(captionPanel);
		//----- Set the position of the file browser padding 
		leftPanelLayout.putConstraint( SpringLayout.NORTH, captionPanel, LEFT_PANEL_VERTICAL_SPACING, SpringLayout.SOUTH, fileBrowserPanel);
		leftPanelLayout.putConstraint( SpringLayout.HORIZONTAL_CENTER, captionPanel, 0, SpringLayout.HORIZONTAL_CENTER, leftGUIPanel);

		
		//---- Create the file contents panel.
		fileContentsPanel = new SlideShowContentsPanel(LEFT_PANEL_WIDTH, FILE_CONTENTS_PANEL_HEIGHT, STANDARD_PADDING, FILE_BROWSER_PANEL_HEIGHT - 2*STANDARD_PADDING );
		leftGUIPanel.add(fileContentsPanel);
		//----- Set the position of the file browser padding 
		leftPanelLayout.putConstraint( SpringLayout.NORTH, fileContentsPanel, LEFT_PANEL_VERTICAL_SPACING, SpringLayout.SOUTH, captionPanel);
		leftPanelLayout.putConstraint( SpringLayout.HORIZONTAL_CENTER, fileContentsPanel, 0, SpringLayout.HORIZONTAL_CENTER, leftGUIPanel);
		//----- Setup the listeners
		fileContentsPanel.addActionListener(new SlideShowCaptionPanel.ResetCaptionListener(), SlideShowContentsPanel.ADD_NEW_IMAGE_LISTENER); 			//---- Listen for New Image Button
		fileContentsPanel.addActionListener(new SlideShowFileBrowserPanel.ResetFileBrowserListener(), SlideShowContentsPanel.ADD_NEW_IMAGE_LISTENER);	//---- Listen for New Image Button
		fileContentsPanel.addListSelectionListener(new SlideShowFileBrowserPanel.FilePathListSelectionListener());											//---- Listen for an Image to Be Selected from the List
		fileContentsPanel.addListSelectionListener(new SlideShowCaptionPanel.CaptionListSelectionListener());												//---- Listen for an Image to Be Selected from the List
		fileBrowserPanel.addDocumentListenerForFile(new SlideShowContentsPanel.FileBrowserListener());											//---- Listen for changes in the file browser.
		captionPanel.addDocumentListenerForCaption(new SlideShowContentsPanel.CaptionListener()); 												//---- Listen for changes in the caption
		topMenu.addActionListener(new SlideShowContentsPanel.ResetContentsPaneListener(), SlideShowJMenuBar.NEW_FILE_LISTENER);			//---- Listen for New File Actions on MenuBar
		topMenu.addActionListener(new SlideShowContentsPanel.OpenFileContentsPaneListener(), SlideShowJMenuBar.OPEN_FILE_LISTENER);		//---- Listen for New File Actions on MenuBar
		topMenu.addActionListener(new SlideShowContentsPanel.SaveFileContentsPaneListener(), SlideShowJMenuBar.SAVE_FILE_LISTENER);		//---- Listen for New File Actions on MenuBar	
		
		//---- Add the Left GUI Panel to the GUI.
		mainGUI.add(leftGUIPanel);
		mainGUILayout.putConstraint(SpringLayout.NORTH, leftGUIPanel, 0, SpringLayout.NORTH, mainGUI);
		mainGUILayout.putConstraint(SpringLayout.WEST, leftGUIPanel, 0, SpringLayout.WEST, mainGUI);
		
	}
	


	
	/**
	 *  Creates the left panel where the image is.
	 */
	private static void createImagePanel(){
		
		//---- Add the Image Panel on the right
		imagePanel = new SlideShowImagePanel(RIGHT_PANEL_WIDTH, PANELS_HEIGHT, 2 * STANDARD_PADDING, IMAGE_CAPTION_WIDTH, IMAGE_CAPTION_HEIGHT);
		fileBrowserPanel.addDocumentListenerForFile(imagePanel.createImagePathDocumentListener());
		captionPanel.addDocumentListenerForCaption(imagePanel.createCaptionDocumentListener());
		mainGUI.add(imagePanel);
		//---- Setup the location of the panel
		mainGUILayout.putConstraint(SpringLayout.NORTH, imagePanel, 0, SpringLayout.NORTH, mainGUI);
		mainGUILayout.putConstraint(SpringLayout.WEST, imagePanel, 0, SpringLayout.EAST, leftGUIPanel);
		//---- Add a listener to reset the caption location when new panel is selected.
		topMenu.addActionListener(imagePanel.createResetCaptionLocationListener(), SlideShowJMenuBar.NEW_FILE_LISTENER);
		fileContentsPanel.addListSelectionListener(imagePanel.createListSelectionCaptionLocationListener());
		fileContentsPanel.addMouseInputListenerToCaption(imagePanel);
		
	}
	
	

}
