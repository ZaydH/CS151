package cs151_assignment5;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * This class is a replacement for the JOptionPane and is threaded.
 * 
 * @author Zayd
 *
 */
public class JOptionPaneThreaded extends Thread {

	private String paneMessage;
	private String panelTitle;
	
	/**
	 * Constructor for the JOptionPaneThread.  It will automatically open the JOptionPane.
	 * 
	 * @param paneMessage  	Message that will be displayed by the pane.
	 * @param panelTitle	Title for the message JOptionPane.
	 */
	public JOptionPaneThreaded(String paneMessage, String panelTitle){
		this.paneMessage = paneMessage;
		this.panelTitle = panelTitle;
		
		this.start();
	}
	
	/**
	 * Constructor that creates and options a JOptionPane with the specified message and a blank title.
	 * 
	 * @param paneMessage	  	Message that will be displayed by the pane.
	 */
	public JOptionPaneThreaded(String paneMessage){
		this(paneMessage, "");
	}
		
	
	/**
	 * Displays a JOptionPane with the specified message.
	 */
	@Override
	public void run(){
		
		JFrame dialogFrame = new JFrame(); //----- create a dummy frame the JOptionPane will block.
		JOptionPane optionPane = new JOptionPane( paneMessage, JOptionPane.ERROR_MESSAGE );
		JDialog outputDialog = optionPane.createDialog( dialogFrame, panelTitle);
		outputDialog.setModal(false);
		outputDialog.setVisible(true);
		
	}

	
}
