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
	
	public final static String PREVIOUS_BUTTON_TEXT = "Previous";
	public final static String NEXT_BUTTON_TEXT = "Next";
	public final static String PAUSE_BUTTON_TEXT = "Pause";
	public final static String PLAY_BUTTON_TEXT = "Play";
	
	
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
		playPauseButton = new JButton( PLAY_BUTTON_TEXT );
		playPauseButton.setAction( createPlayPauseButtonAbstractAction() );
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
		previousSlideButton = new JButton( PREVIOUS_BUTTON_TEXT );
		//previousSlideButton.setAction( createPlayPauseButtonAbstractAction() );
		Dimension previousNextButtonsDimension = new Dimension((playPauseButtonWidth - spaceBetweenButtons) /2, playPauseButtonHeight);
		previousSlideButton.setSize(previousNextButtonsDimension);
		previousSlideButton.setPreferredSize(previousNextButtonsDimension);
		previousSlideButton.setMinimumSize(previousNextButtonsDimension);
		previousSlideButton.setMaximumSize(previousNextButtonsDimension);
		previousSlideButton.setEnabled(false);
		this.add(previousSlideButton);
		slideNavigatorLayout.putConstraint(SpringLayout.NORTH, previousSlideButton, 3*padding, SpringLayout.SOUTH, playPauseButton);
		slideNavigatorLayout.putConstraint(SpringLayout.WEST, previousSlideButton, 0, SpringLayout.WEST, playPauseButton);

		
		//---- Create the previous button.
		nextSlideButton = new JButton( NEXT_BUTTON_TEXT );
		//previousSlideButton.setAction( createPlayPauseButtonAbstractAction() );
		nextSlideButton.setSize(previousNextButtonsDimension);
		nextSlideButton.setPreferredSize(previousNextButtonsDimension);
		nextSlideButton.setMinimumSize(previousNextButtonsDimension);
		nextSlideButton.setMaximumSize(previousNextButtonsDimension);
		nextSlideButton.setEnabled(false);
		this.add(nextSlideButton);
		slideNavigatorLayout.putConstraint(SpringLayout.NORTH, nextSlideButton, 3*padding, SpringLayout.SOUTH, playPauseButton);
		slideNavigatorLayout.putConstraint(SpringLayout.EAST, nextSlideButton, 0, SpringLayout.EAST, playPauseButton);


	}

	
	/**
	 * Factory Method to construct and return action elements for play/pause buttons.
	 * 
	 * @return  Abstract Action for the Play Pause Button
	 */
	private AbstractAction createPlayPauseButtonAbstractAction(){
		
		/**
		 * Action class for the pause/play buttons. 
		 * 
		 * @author Zayd
		 *
		 */
		class PlayPauseAbstractAction extends AbstractAction {
			
			private static final long serialVersionUID = -3419452042957390414L;
			private String currentButtonState;
			
			/**
			 * Store the local button state to ensure it is not reset out of sync.
			 */
			public PlayPauseAbstractAction(){
				super(PLAY_BUTTON_TEXT);
				this.currentButtonState = PLAY_BUTTON_TEXT;
			}
			
			/**
			 * Toggle the string for the pause button.
			 */
			public void actionPerformed(ActionEvent e){
				
				//----- Select the opposite of the current button state
				if(currentButtonState.equals(PLAY_BUTTON_TEXT))
					currentButtonState = PAUSE_BUTTON_TEXT;
				else
					currentButtonState = PLAY_BUTTON_TEXT;
				
				//---- Update the button.
				playPauseButton.setText(currentButtonState);
			}	
			
		};
		
		//---- Construct the class defined above then return it.S
		return new PlayPauseAbstractAction();
		
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
				playPauseButton.setEnabled(true);
				previousSlideButton.setEnabled(true);
				nextSlideButton.setEnabled(true);
			}
	
		}
		
		return new EnableNavationButtonListener();
	}
	
}
