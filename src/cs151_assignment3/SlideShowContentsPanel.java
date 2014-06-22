package cs151_assignment3;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class SlideShowContentsPanel extends JPanel {

	private static JButton saveButton;
	private static JList slideShowList;
	private static JButton addNewButton;
	
	
	private static final String saveButtonCommandName = "SAVE_CONTENT";
	private static final String addNewButtonCommandName = "ADD_NEW_CONTENT";
	
	
	public SlideShowContentsPanel(int width, int height, int padding){
		
		slideShowList = new JList();
		slideShowList.setLayoutOrientation(JList.VERTICAL); 				 //----- One item per row.	
		slideShowList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //---- Allow only one image to be selected at a time.
		
		
		
	}
	
	
}
