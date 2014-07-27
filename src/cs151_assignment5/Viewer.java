package cs151_assignment5;


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

public class Viewer {

	
	private static JFrame mainGUI;
	private static SpringLayout mainGUILayout;
	private static SlideShowJMenuBar topMenu;
	
	private static JPanel leftGUIPanel;
	private static SlideShowSliderPanel slidePanel;
	private static SlideNavigatorPanel slideNavigatorPanel;
	//private static SlideShowContentsPanel fileContentsPanel;
	
	private static SlideShowImagePanel imagePanel;

	private static final int STANDARD_PADDING = 5;
	
	//---- Enumerate constants regarding GUI Height
	private static final int TOP_MENU_HEIGHT = 23;
	private static final int PANELS_HEIGHT = 700;
	private static final int GUI_HEIGHT = PANELS_HEIGHT;
	
	private static final int SLIDE_NAVIGATION_PANEL_HEIGHT = 100;
	private static final int SLIDE_NAVIGATION_BUTTON_HEIGHT = SLIDE_NAVIGATION_PANEL_HEIGHT/3;
	private static final int SLIDER_PANEL_HEIGHT = SLIDE_NAVIGATION_PANEL_HEIGHT;
	
	private static final int LEFT_PANEL_SPACING_BETWEEN_MENU_BAR = 5 * STANDARD_PADDING;
	private static final int IMAGE_CAPTION_HEIGHT = 40;
	
	
	//---- Enumerate constants regarding GUI Width
	private static final int LEFT_PANEL_WIDTH = 400;
	private static final int GUI_WIDTH = LEFT_PANEL_WIDTH + PANELS_HEIGHT;
	private static final int RIGHT_PANEL_WIDTH = GUI_WIDTH-LEFT_PANEL_WIDTH;
	//private static final int LEFT_PANEL_LABEL_WIDTH = 70;
	private static final int SLIDE_NAVIGATION_MAIN_BUTTON_WIDTH = 3 * LEFT_PANEL_WIDTH  /4;

	private static final int SPACE_BETWEEN_SLIDE_NAVIGATION_BUTTONS = 20;
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
		
		//---- Add the slideNavigatorPanel
		slideNavigatorPanel = new SlideNavigatorPanel(LEFT_PANEL_WIDTH, SLIDE_NAVIGATION_PANEL_HEIGHT, SLIDE_NAVIGATION_MAIN_BUTTON_WIDTH,  
												   SLIDE_NAVIGATION_BUTTON_HEIGHT, STANDARD_PADDING, SPACE_BETWEEN_SLIDE_NAVIGATION_BUTTONS );
		leftGUIPanel.add(slideNavigatorPanel);	
		//----- Set the position of the file browser padding
		leftPanelLayout.putConstraint( SpringLayout.NORTH, slideNavigatorPanel, LEFT_PANEL_SPACING_BETWEEN_MENU_BAR, SpringLayout.NORTH, leftGUIPanel);
		leftPanelLayout.putConstraint( SpringLayout.HORIZONTAL_CENTER, slideNavigatorPanel, 0, SpringLayout.HORIZONTAL_CENTER, leftGUIPanel);
		
		
		//----- Make the Pane storing the caption information		
		slidePanel = new SlideShowslidePanel(LEFT_PANEL_WIDTH, SLIDER_PANEL_HEIGHT, LEFT_PANEL_LABEL_WIDTH, STANDARD_PADDING);
		leftGUIPanel.add(slidePanel);
		//----- Set the position of the file browser padding 
		leftPanelLayout.putConstraint( SpringLayout.NORTH, slidePanel, LEFT_PANEL_VERTICAL_SPACING, SpringLayout.SOUTH, slideNavigatorPanel);
		leftPanelLayout.putConstraint( SpringLayout.HORIZONTAL_CENTER, slidePanel, 0, SpringLayout.HORIZONTAL_CENTER, leftGUIPanel);

		public SlideShowSliderPanel(int panelWidth, int panelHeight, int labelWidth, int padding, 
				int sliderWidth, int sliderHeight)
		
//		//---- Create the file contents panel.
//		fileContentsPanel = new SlideShowContentsPanel(LEFT_PANEL_WIDTH, FILE_CONTENTS_PANEL_HEIGHT, STANDARD_PADDING, SLIDE_NAVIGATION_PANEL_HEIGHT - 2*STANDARD_PADDING );
//		leftGUIPanel.add(fileContentsPanel);
//		//----- Set the position of the file browser padding 
//		leftPanelLayout.putConstraint( SpringLayout.NORTH, fileContentsPanel, LEFT_PANEL_VERTICAL_SPACING, SpringLayout.SOUTH, slidePanel);
//		leftPanelLayout.putConstraint( SpringLayout.HORIZONTAL_CENTER, fileContentsPanel, 0, SpringLayout.HORIZONTAL_CENTER, leftGUIPanel);
//		//----- Setup the listeners
//		//fileContentsPanel.addActionListener(new SlideShowslidePanel.ResetCaptionListener(), SlideShowContentsPanel.ADD_NEW_IMAGE_LISTENER); 			//---- Listen for New Image Button
//		//fileContentsPanel.addActionListener(new SlideShowslideNavigatorPanel.ResetFileBrowserListener(), SlideShowContentsPanel.ADD_NEW_IMAGE_LISTENER);	//---- Listen for New Image Button
//		fileContentsPanel.addListSelectionListener(new SlideShowslideNavigatorPanel.FilePathListSelectionListener());											//---- Listen for an Image to Be Selected from the List
//		fileContentsPanel.addListSelectionListener(new SlideShowslidePanel.CaptionListSelectionListener());												//---- Listen for an Image to Be Selected from the List
//		slideNavigatorPanel.addDocumentListenerForFile(new SlideShowContentsPanel.FileBrowserListener());											//---- Listen for changes in the file browser.
//		slidePanel.addDocumentListenerForCaption(new SlideShowContentsPanel.CaptionListener()); 												//---- Listen for changes in the caption
		//topMenu.addActionListener(new SlideShowContentsPanel.OpenFileContentsPaneListener());		//---- Listen for New File Actions on MenuBar
		
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
		mainGUI.add(imagePanel);
		//---- Setup the location of the panel
		mainGUILayout.putConstraint(SpringLayout.NORTH, imagePanel, 0, SpringLayout.NORTH, mainGUI);
		mainGUILayout.putConstraint(SpringLayout.WEST, imagePanel, 0, SpringLayout.EAST, leftGUIPanel);
		topMenu.addOpenFileChooserActionListener(new SlideShowImagePanel.OpenFileContentsPaneListener() );
//		fileContentsPanel.addListSelectionListener(imagePanel.createListSelectionCaptionLocationListener());
		
	}
	
	

}
