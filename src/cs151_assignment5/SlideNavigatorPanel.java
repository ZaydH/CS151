package cs151_assignment5;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;



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
		
		class PlayPauseAbstractAction extends AbstractAction {
			
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

	
}
