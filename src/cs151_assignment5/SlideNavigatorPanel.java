package cs151_assignment5;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;



/**
 * 
 * One of four helper panels.  This panel contains the "Image:" JLabel, the FilePath text field, and the "Browse" button.
 * 
 * @author Zayd
 *
 */

public class SlideNavigatorPanel extends JPanel  {
	

	private static final long serialVersionUID = 5421452027701678411L;

	private static JButton playPauseButton;
	private static JButton previousSlideButton;
	private static JButton nextSlideButton;
	private SpringLayout slideNavigatorLayout;
	private SlideShowImagePanel imagePanel;
	
	public final static String PREVIOUS_BUTTON = "Previous";
	public final static String NEXT_BUTTON = "Next";
	public final static String PAUSE_BUTTON = "Pause";
	public final static String PLAY_BUTTON = "Play";
	
//	public final static String PLAY_SLIDESHOW_ACTION = "playSlideshow";
//	public final static String PAUSE_SLIDESHOW_ACTION = "pauseSlideshow";
	public final static String NEXT_SLIDE_ACTION = "nextSlide";
	public final static String PREVIOUS_SLIDE_ACTION = "previousSlide";
	
	
	/**
	 * 
	 * Sole constructor for the FileBrowserPanel we are using.  
	 * 
	 * @param panelWidth 			The width of this panel.
	 * @param panelHeight			The height of this panel
	 * @param labelWidth			Width of the File Path Label
	 * @param padding				Padding Between elements and the border
	 * @param browseButtonWidth		Width of the browse button.
	 */
	public SlideNavigatorPanel(int panelWidth, int panelHeight, int playPauseButtonWidth, int playPauseButtonHeight, 
							   int padding, int spaceBetweenButtons ){
		
		//---- Use Spring Layout
		Dimension panelDimension = new Dimension(panelWidth, panelHeight);
		this.setSize(panelDimension);
		this.setPreferredSize(panelDimension);
		this.setMinimumSize(panelDimension);
		this.setMaximumSize(panelDimension);
		slideNavigatorLayout = new SpringLayout();
		this.setLayout(slideNavigatorLayout);
		
		
		//---- Create the play/pause button.
		playPauseButton = new JButton( PLAY_BUTTON );
		playPauseButton.addActionListener( createPlayPauseButtonActionListener() );
		Dimension playPauseButtonDimension = new Dimension(playPauseButtonWidth, playPauseButtonHeight);
		playPauseButton.setSize(playPauseButtonDimension);
		playPauseButton.setPreferredSize(playPauseButtonDimension);
		playPauseButton.setMinimumSize(playPauseButtonDimension);
		playPauseButton.setMaximumSize(playPauseButtonDimension);
		playPauseButton.setEnabled(false);
		this.add(playPauseButton);
		slideNavigatorLayout.putConstraint(SpringLayout.NORTH, playPauseButton, padding, SpringLayout.NORTH, this);
		slideNavigatorLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, playPauseButton, 0, SpringLayout.HORIZONTAL_CENTER, this);

		
		//---- Create the previous button.
		previousSlideButton = new JButton( PREVIOUS_BUTTON );
		//previousSlideButton.setAction( createPlayPauseButtonAbstractAction() );
		Dimension previousNextButtonsDimension = new Dimension((playPauseButtonWidth - spaceBetweenButtons) /2, playPauseButtonHeight);
		previousSlideButton.setSize(previousNextButtonsDimension);
		previousSlideButton.setPreferredSize(previousNextButtonsDimension);
		previousSlideButton.setMinimumSize(previousNextButtonsDimension);
		previousSlideButton.setMaximumSize(previousNextButtonsDimension);
		previousSlideButton.setEnabled(false);
		previousSlideButton.setActionCommand(PREVIOUS_SLIDE_ACTION);
		this.add(previousSlideButton);
		slideNavigatorLayout.putConstraint(SpringLayout.NORTH, previousSlideButton, 3*padding, SpringLayout.SOUTH, playPauseButton);
		slideNavigatorLayout.putConstraint(SpringLayout.WEST, previousSlideButton, 0, SpringLayout.WEST, playPauseButton);

		
		//---- Create the previous button.
		nextSlideButton = new JButton( NEXT_BUTTON );
		//previousSlideButton.setAction( createPlayPauseButtonAbstractAction() );
		nextSlideButton.setSize(previousNextButtonsDimension);
		nextSlideButton.setPreferredSize(previousNextButtonsDimension);
		nextSlideButton.setMinimumSize(previousNextButtonsDimension);
		nextSlideButton.setMaximumSize(previousNextButtonsDimension);
		nextSlideButton.setEnabled(false);
		nextSlideButton.setActionCommand(NEXT_SLIDE_ACTION);
		this.add(nextSlideButton);
		slideNavigatorLayout.putConstraint(SpringLayout.NORTH, nextSlideButton, 3*padding, SpringLayout.SOUTH, playPauseButton);
		slideNavigatorLayout.putConstraint(SpringLayout.EAST, nextSlideButton, 0, SpringLayout.EAST, playPauseButton);


	}
	
	
	/**
	 * 
	 * Gets a reference of the image panel for this class to use to start and stop the slideshow.
	 * 
	 * @param imagePanel	Reference to the GUI's image panel.
	 */
	public void setImagePanelReference( SlideShowImagePanel imagePanel){
		this.imagePanel = imagePanel;
	}

	
	/**
	 * Factory Method to construct and return action elements for play/pause buttons.
	 * 
	 * @return  ActionListener for the Play Pause Button
	 */
	private ActionListener createPlayPauseButtonActionListener(){
		
		return new ActionListener(){
			
			/**
			 * Toggle the string for the pause button.
			 */
			public void actionPerformed(ActionEvent e){
				
				//----- Select the opposite of the current button state
				if(playPauseButton.getText().equals(PLAY_BUTTON)){
					playPauseButton.setText( PAUSE_BUTTON );
					imagePanel.startSlideshow();
				}
				else{
					playPauseButton.setText( PLAY_BUTTON );
					imagePanel.stopSlideshow();
				}

			}	
			
		};
		
	}
	
	
	/**
	 * Adds action listeners to the next or previous slide buttons.
	 * 
	 * @param slideButton Name of the button to have the listener attached to.
	 */
	public void addSlideTransitionButtonListener(String slideButton, ActionListener listener){
		
		if(slideButton.equals(NEXT_BUTTON))
			nextSlideButton.addActionListener(listener);
		else if(slideButton.equals(PREVIOUS_BUTTON))
			previousSlideButton.addActionListener(listener);
		else
			assert false : "Error: Invalid button name for slide button.";
		
	}
	
	/**
	 * Creates an ActionListener that once the GUI enters a valid state the GUI buttons are enabled.
	 * 
	 * @return	ActionListener that when an event occurs enables the buttons.
	 */
	public ActionListener createNavigationButtonEnableListener(){

		/**
		 * Class that creates a threaded ActionListener to enable the navigation buttons.
		 * 
		 * @author Zayd
		 */
		class EnableNavationButtonListener extends Thread implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e){
				this.run();
			}
			
			@Override
			public void run(){
				playPauseButton.setText( PLAY_BUTTON );
				playPauseButton.setEnabled(true);
				previousSlideButton.setEnabled(true);
				nextSlideButton.setEnabled(true);
			}
	
		}
		
		return new EnableNavationButtonListener();
	}
	
}
