package cs151_assignment5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Containing class for the JMenuBar.
 * 
 * @author Zayd
 *
 */

public class SlideShowJMenuBar extends JMenuBar implements ActionListener {

	
	/**
	 * Auto-generated UID for this JPanel.
	 */
	private static final long serialVersionUID = 1686924456234878847L;
	private static JMenu fileMenu;
	
	private static JFileChooser openFileChooser;
	
	
	/**
	 * Constructor for the Slide Show GUI Menu Bar.
	 */
	public SlideShowJMenuBar(){
	
		//----- Create the File JMenu and define its menu items
		fileMenu = new JMenu("File");
		String[] fileMenuLabels = { "Open", "Exit" };
		//----- Add the menu items to the File Menu
		for(int i = 0; i < fileMenuLabels.length; i++){
			
			JMenuItem menuItem = new JMenuItem(fileMenuLabels[i]);
			menuItem.setActionCommand("fileMenu" + fileMenuLabels[i]);
			menuItem.addActionListener(this); //---- The class is its own action listener
			
			fileMenu.add(menuItem); //---- Add the menu item to the list
		}
		this.add(fileMenu); //---- Add "File" to the menu bar
		//----- Create the open file chooser
		FileFilter fileNameExtensionFiter = new FileNameExtensionFilter("Slide Show Files (*." + SlideShowFileContents.FILE_EXTENSION + ")", 
																		SlideShowFileContents.FILE_EXTENSION );//---- File extension is "show". Use for all file tools
		openFileChooser = new JFileChooser();
		openFileChooser.setFileFilter( fileNameExtensionFiter );		
		
	}
	
	
	/**
	 * 
	 * Allows for ActionListeners to be added to the Menu Bar.  Menu Items that support listeners are "New", "Open", and "Save".
	 * 
	 * @param listener		ActionListener to be added.
	 * @param objectType	Type of Listener to be added.  
	 */
	public void addOpenFileChooserActionListener( ActionListener listener ){
		openFileChooser.addActionListener(listener);
	}
		
	/**
	 * Action Listener for the Menu bar.  Menu items that have effects are Open, Save, and Exit.
	 */
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("fileMenuOpen")){
			
			//----- Run the file chooser thread
			class FileChooserThread extends Thread{
				public void run(){
					openFileChooser.showOpenDialog(null);
					this.start();
				}
			}
			
			//---- Create the thread to display the JFileChooser.
			@SuppressWarnings("unused")
			Thread fileChooserThread = new FileChooserThread();
		}
		else if(e.getActionCommand().equals("fileMenuExit")){
			menuExitAction();
		}		
	}
	
	
	/**
	 * Closes the entire GUI.
	 */
	private void menuExitAction(){
		System.exit(0); //---- Kill the program
	}	
	
	
	
}
