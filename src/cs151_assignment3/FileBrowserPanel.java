package cs151_assignment3;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FileBrowserPanel extends JPanel  implements ActionListener {
	

	private static JFileChooser fileChooser;
	private static JButton browseButton;
	private static JTextField filePathTextField;
	private static JLabel filePathLabel;
	private SpringLayout browserLayout;
	
	private final static String BROWSE_COMMAND_NAME = "FileBrowse";
	private static final long serialVersionUID = 5421452027701678411L;
	
	
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
	public FileBrowserPanel(int panelWidth, int panelHeight, int labelWidth, int padding, int browseButtonWidth){
		
		//---- Use Spring Layout
		Dimension panelDimension = new Dimension(panelWidth, panelHeight);
		this.setSize(panelDimension);
		this.setPreferredSize(panelDimension);
		this.setMinimumSize(panelDimension);
		this.setMaximumSize(panelDimension);
		browserLayout = new SpringLayout();
		this.setLayout(browserLayout);
		
		
		//---- Setup the layout
		filePathLabel = new JLabel("Image:");
		this.add(filePathLabel);
		SpringLayout.Constraints fileLabelConstraints = browserLayout.getConstraints(filePathLabel);
		fileLabelConstraints.setX( Spring.constant(labelWidth - fileLabelConstraints.getWidth().getValue() ) ); //---- Set the position of the of the file path label.
				
		
		//---- Setup the File Path Text Field
		filePathTextField = new JTextField("Initializing", JTextField.TRAILING);
		filePathTextField.setEditable(false); //----- This field is either set by Browse or by the GUI on a reset
		this.add(filePathTextField);
		
		//---- Set the size information for the text field
		Dimension fileTextFieldDimension = new Dimension(panelWidth - browseButtonWidth - labelWidth - padding, panelHeight - 2 * padding); //---- Use only padding and not 2*padding for width since need a padding on left side
		filePathTextField.setSize(fileTextFieldDimension);
		filePathTextField.setPreferredSize(fileTextFieldDimension);
		SpringLayout.Constraints fileTextFieldConstraints = browserLayout.getConstraints(filePathTextField);
		fileTextFieldConstraints.setWidth( Spring.constant( (int)fileTextFieldDimension.getWidth() ) );
		
		
		//----- Create the Browse Button
		browseButton = new JButton("Browse");
		browseButton.setActionCommand(BROWSE_COMMAND_NAME);
		browseButton.addActionListener(this);
		this.add(browseButton);
		
		//---- Set the size information for the browse buttons.
		Dimension browseButtonDimension = new Dimension(browseButtonWidth - 2 * padding, panelHeight - 2 * padding); //---- Use 2 * padding for width since need a padding on both sides due to panel boundary
		browseButton.setPreferredSize(browseButtonDimension);
		SpringLayout.Constraints browseButtonConstraints = browserLayout.getConstraints(browseButton);
		browseButtonConstraints.setWidth( Spring.constant( (int)browseButtonDimension.getWidth() ) );		

		
		
		//----- Place the objects in the panel vertically
		browserLayout.putConstraint(SpringLayout.VERTICAL_CENTER, filePathLabel, 0, SpringLayout.VERTICAL_CENTER, this );
		browserLayout.putConstraint(SpringLayout.VERTICAL_CENTER, filePathTextField, 0, SpringLayout.VERTICAL_CENTER, this );
		browserLayout.putConstraint(SpringLayout.VERTICAL_CENTER, browseButton, 0, SpringLayout.VERTICAL_CENTER, this );
		
		//---- Place the objects in the panel horizontally
		browserLayout.putConstraint(SpringLayout.WEST, filePathTextField, padding, SpringLayout.EAST, filePathLabel);
		browserLayout.putConstraint(SpringLayout.WEST, browseButton, padding, SpringLayout.EAST, filePathTextField);
		
		
		//---- Create the file chooser.
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "gif",
																			 "png" ));
		
		
	}
	
	/**
	 * Add a listener for changes in the TextField change.  Since it is a DocumentListener, it can get the resulting text.
	 * 
	 * @param listener An object that implements the DocumentListener Interface that will listen for changes in the JTextField containing the image path.
	 */
	public void addDocumentListenerForFile(DocumentListener listener){
		filePathTextField.getDocument().addDocumentListener(listener);
	}
	
	
	
	/**
	 * Helper class used to update the the File Path Text Field Once the Browse Button is Pressed.
	 */
	public void actionPerformed(ActionEvent e){
		
		if(e.getActionCommand().equals(BROWSE_COMMAND_NAME)){
			
			int chooserSelection = fileChooser.showOpenDialog(this);
			
			//----- Select OK in the File Chooser
			if(chooserSelection == JFileChooser.APPROVE_OPTION){
				String selectedFile = fileChooser.getSelectedFile().toString();
				
				//---- Update the file path text field.
				filePathTextField.setText(selectedFile);
				return;
			}
			
		}
		
	}
	
	
	/**
	 * 
	 * Helper class that implements the "ActionListener" interface.  Simply resets the TextField for the FileBrowser.  
	 * This is used in case something else (e.g. the menu bar) is changed requiring this field to be reset.
	 * 
	 * @author Zayd
	 *
	 */
	public static class ResetFileBrowserListener implements ActionListener {
		
		public void actionPerformed( ActionEvent e ){
			//---- Do not do anything on an cancelled command
			if(e.getSource() instanceof JFileChooser && e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION) ){
				return;
			}
			
			//---- If not reset, then reset.
			filePathTextField.setText("");			
		}
		
	}

	
}
