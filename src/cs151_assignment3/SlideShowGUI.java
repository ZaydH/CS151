package cs151_assignment3;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;



public class SlideShowGUI {

	
	private static JFrame mainGUI;
	private static SlideShowJMenuBar topMenu;
	
	private static JPanel leftGUIPanel;
	private static CaptionPanel captionPanel;
	private static FileBrowserPanel fileBrowserPanel;
	private static SlideShowContentsPanel fileContentsPanel;
	
	private static SlideShowImagePanel imagePanel;

	private static final int STANDARD_PADDING = 5;
	
	//---- Enumerate constants regarding GUI Height
	private static final int GUI_HEIGHT = 700;
	private static final int FILE_BROWSER_PANEL_HEIGHT = 37;
	private static final int CAPTION_BROWSER_PANEL_HEIGHT = 37; 
	private static final int LEFT_PANEL_SPACING_BETWEEN_MENU_BAR = 10 * STANDARD_PADDING;
	private static final int LEFT_PANEL_VERTICAL_SPACING = 3 * STANDARD_PADDING;
	private static final int FILE_CONTENTS_PANEL_HEIGHT = GUI_HEIGHT - FILE_BROWSER_PANEL_HEIGHT - CAPTION_BROWSER_PANEL_HEIGHT 
														  - LEFT_PANEL_SPACING_BETWEEN_MENU_BAR - 3*LEFT_PANEL_VERTICAL_SPACING;

	
	//---- Enumerate constants regarding GUI Width
	private static final int LEFT_PANEL_WIDTH = 400;
	private static final int GUI_WIDTH = LEFT_PANEL_WIDTH + GUI_HEIGHT;
	private static final int RIGHT_PANEL_WIDTH = GUI_WIDTH-LEFT_PANEL_WIDTH;
	private static final int LEFT_PANEL_LABEL_WIDTH = 70;
	
	

	
	public static void main(String args[]){
		
		//----- Initialize the main variables
		mainGUI = new JFrame();
		setupMainGUIWindow();
		
		//----- Make the menu bar;
		topMenu = new SlideShowJMenuBar();
		mainGUI.setJMenuBar(topMenu);
		
		//---- Create the left panel of the GUI
		createLeftPanel();
		
		//----- Create the right panel of the GUI
		createImagePanel();
		
		//---- Make the GUI visible
		mainGUI.setVisible(true);//---- Make the GUI visible
	}
	
	
	
	
	private static void setupMainGUIWindow(){
		
		mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //---- Ensure the execution ends when the GUI is closed.
		
		//---- Set GUI sizing information
		mainGUI.setSize(GUI_WIDTH, GUI_HEIGHT);
		mainGUI.setResizable(false);
		
		mainGUI.setLayout(new BorderLayout());

	}

	
	private static void createLeftPanel(){
		
		//----- Create the left panel
		leftGUIPanel = new JPanel();
		Dimension panelDimension = new Dimension (LEFT_PANEL_WIDTH, GUI_HEIGHT);
		leftGUIPanel.setSize(panelDimension);
		leftGUIPanel.setMinimumSize(panelDimension);
		leftGUIPanel.setPreferredSize(panelDimension);
		leftGUIPanel.setMaximumSize(panelDimension);		
		SpringLayout leftPanelLayout = new SpringLayout();
		leftGUIPanel.setLayout(leftPanelLayout);
		
		//---- Add the fileBrowserPanel
		fileBrowserPanel = new FileBrowserPanel(LEFT_PANEL_WIDTH, FILE_BROWSER_PANEL_HEIGHT, LEFT_PANEL_LABEL_WIDTH,  
												STANDARD_PADDING, 100);
		topMenu.addActionListener(new FileBrowserPanel.ResetFileBrowserListener(), SlideShowJMenuBar.ListenerObject.NEW_FILE);		//---- Listen for New File Actions on MenuBar
		topMenu.addActionListener(new FileBrowserPanel.ResetFileBrowserListener(), SlideShowJMenuBar.ListenerObject.OPEN_FILE);		//---- Listen for Open File Actions on MenuBar
		
		leftGUIPanel.add(fileBrowserPanel);	
		//----- Set the position of the file browser padding 
		leftPanelLayout.putConstraint( SpringLayout.NORTH, fileBrowserPanel, LEFT_PANEL_SPACING_BETWEEN_MENU_BAR, SpringLayout.NORTH, leftGUIPanel);
		leftPanelLayout.putConstraint( SpringLayout.HORIZONTAL_CENTER, fileBrowserPanel, 0, SpringLayout.HORIZONTAL_CENTER, leftGUIPanel);
		
		
		//----- Make the Pane storing the caption information		
		captionPanel = new CaptionPanel(LEFT_PANEL_WIDTH, CAPTION_BROWSER_PANEL_HEIGHT, LEFT_PANEL_LABEL_WIDTH, STANDARD_PADDING);
		topMenu.addActionListener(new CaptionPanel.ResetCaptionListener(), SlideShowJMenuBar.ListenerObject.NEW_FILE);		//---- Listen for New File Actions on MenuBar
		topMenu.addActionListener(new CaptionPanel.ResetCaptionListener(), SlideShowJMenuBar.ListenerObject.OPEN_FILE);		//---- Listen for Open File Actions on MenuBar		
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
		
		//---- Add the Left GUI Panel to the GUI.
		mainGUI.add(leftGUIPanel, BorderLayout.WEST);
		
	}
	


	
	//---- This creates the left panel where the image is.
	public static void createImagePanel(){
		
		imagePanel = new SlideShowImagePanel(LEFT_PANEL_WIDTH, GUI_HEIGHT, 2 * STANDARD_PADDING);
		fileBrowserPanel.addDocumentListenerForFile(imagePanel);
		mainGUI.add(imagePanel);
		
	}
	
	

}
