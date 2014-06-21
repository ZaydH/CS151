package cs151_assignment3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class SlideShowJMenuBar extends JMenuBar implements ActionListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1686924456234878847L;
	private static JMenu fileMenu;
	
	
	public SlideShowJMenuBar(){
		
		
		//----- Create the JMenu
		fileMenu = new JMenu("File");
		
		//----- Create the "File" menu items.
		String[] menuLabels = { "New", "Open", "Save", "Exit" };
		
		//----- Add the menu items to the JMenu
		for(int i = 0; i < menuLabels.length; i++){
			
			JMenuItem menuItem = new JMenuItem(menuLabels[i]);
			menuItem.setActionCommand("fileMenu" + menuLabels[i]);
			menuItem.addActionListener(this); //---- The class is its own action listener
			
			fileMenu.add(menuItem); //---- Add the menu item to the list
		}
		
		//---- Create the menu bar.
		this.add(fileMenu);
		
		
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("fileMenuNew")){
			menuNewAction();
		}
		else if(e.getActionCommand().equals("fileMenuOpen")){
			menuOpenAction();
		}
		else if(e.getActionCommand().equals("fileMenuSave")){
			menuSaveAction();
		}
		else if(e.getActionCommand().equals("fileMenuExit")){
			menuExitAction();
		}		
	}
	
	
	private void menuNewAction(){
		//TODO: Implement the "New" Menu
		JOptionPane.showMessageDialog(this,"New Menu Remains to Be Implemented" );
		
	}

	private void menuOpenAction(){
		//TODO: Implement the "Open" Menu
		JOptionPane.showMessageDialog(this,"Open Menu Remains to Be Implemented");
	}
	
	private void menuSaveAction(){
		//TODO: Implement the "Save" Menu
		JOptionPane.showMessageDialog(this,"Save Menu Remains to Be Implemented");
	}
	
	private void menuExitAction(){
		System.exit(0); //---- Kill the program
	}	
	
	
}
