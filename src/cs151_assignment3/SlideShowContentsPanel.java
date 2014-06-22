package cs151_assignment3;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

public class SlideShowContentsPanel extends JPanel {

	private static JButton saveButton;
	private static JList slideShowList;
	private static SlideShowFileContents slideShowFileContents;
	private static JButton addNewButton;
	
	
	private static final String saveButtonCommandName = "SAVE_CONTENT";
	private static final String addNewButtonCommandName = "ADD_NEW_CONTENT";
	
	
	public SlideShowContentsPanel(int width, int height, int padding, int buttonHeight){
		
		
		//----- Set the panel information.
		Dimension panelDimension = new Dimension(width, height);
		this.setSize(panelDimension);
		this.setPreferredSize(panelDimension);
		this.setMinimumSize(panelDimension);
		this.setMaximumSize(panelDimension);
		
		
		slideShowList = new JList();
		slideShowList.setLayoutOrientation(JList.VERTICAL); 				 //----- One item per row.	
		slideShowList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //---- Allow only one image to be selected at a time.
		
		
	}
	

	
	
}
