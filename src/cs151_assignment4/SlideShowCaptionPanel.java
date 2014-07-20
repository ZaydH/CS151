package cs151_assignment4;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



/**
 * 
 * One of four helper panels.  This one contains the "Caption:" JLabel and the caption JTextField
 * 
 * @author Zayd
 *
 */

public class SlideShowCaptionPanel extends JPanel  {

		
	/**
	 * UID required to extended JPanel.  System generated.
	 */
	private static final long serialVersionUID = -8073474470479993782L;
	private static JTextField captionTextField;
	private static final String CAPTION_LABEL_STRING = "Caption:";
	private static JLabel captionLabel;
	
	
	/**
	 * Primary constructor for the Panel Containing the Picture Caption and the Caption Label ("Caption:").
	 * 
	 * @param panelWidth	Width of this Panel
	 * @param panelHeight	Height of this Panel
	 * @param labelWidth	Width of the left label for this panel.
	 * @param padding		Spacing between items in the Panel.
	 */
	public SlideShowCaptionPanel(int panelWidth, int panelHeight, int labelWidth, int padding){
				

		
		//---- Use the imports to set the panel size
		Dimension panelDimension = new Dimension(panelWidth, panelHeight);
		this.setSize(panelDimension);		
		this.setPreferredSize(panelDimension);
		this.setMinimumSize(panelDimension);
		this.setMaximumSize(panelDimension);
		this.setVisible(true);
		
		
		//------ Spring Layout Creation
		SpringLayout captionSpringLayout = new SpringLayout();
		this.setLayout(captionSpringLayout);
		
		//----- Create the captions
		captionLabel = new JLabel();
		captionLabel.setText(CAPTION_LABEL_STRING);
		this.add(captionLabel);
		
		//----- Set the captions layout constraints
		SpringLayout.Constraints captionLabelConstraints = captionSpringLayout.getConstraints(captionLabel);
		captionLabelConstraints.setX(Spring.constant(labelWidth - captionLabelConstraints.getWidth().getValue()));
		
		
		//---- Create and Add the Caption Field
		captionTextField = new JTextField("", JTextField.TRAILING);
		captionTextField.setEnabled(false); //---- By default on creation text field is disabled		
		this.add(captionTextField);
		

		//----- Place the components relative to each other.
		captionSpringLayout.putConstraint(SpringLayout.WEST, captionTextField, padding, SpringLayout.EAST, captionLabel);	//---- Space the caption padding pixels from the label
		captionSpringLayout.putConstraint(SpringLayout.NORTH, captionTextField, padding, SpringLayout.NORTH, this);		//---- Space "padding" pixels from the top of the panel
		captionSpringLayout.putConstraint(SpringLayout.VERTICAL_CENTER, captionLabel, 0, SpringLayout.VERTICAL_CENTER, captionTextField); //---- Make the label and caption field centered with respect to one another
		
		//----- Set the size of the caption text field.
		Dimension captionTextFieldDimension = new Dimension(panelWidth - 2*padding - labelWidth, panelHeight - 2*padding);
		captionTextField.setSize(captionTextFieldDimension);
		captionTextField.setPreferredSize(captionTextFieldDimension);
		SpringLayout.Constraints captionTextFieldConstraints = captionSpringLayout.getConstraints(captionTextField);
		captionTextFieldConstraints.setWidth( Spring.constant( captionTextFieldDimension.width ) ); 
		
	}
	
	
	
	/**
	 * Add a listener for changes in the Caption JTextField.  Since it is a DocumentListener, it can get the resulting text.
	 * 
	 * @param listener An object that implements the DocumentListener Interface that will listen for changes in the JTextField containing the Image Caption.
	 */
	public void addDocumentListenerForCaption(DocumentListener listener){
		captionTextField.getDocument().addDocumentListener(listener);
	}
	
	
	/**
	 * Listener class for CaptionPanel to receive actions (New File, Open File, etc.)
	 * 
	 * @author Zayd
	 *
	 */
	public static class ResetCaptionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			
			//---- Do not do anything on an cancelled command
			if(e.getSource() instanceof JFileChooser && e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION) ){
				return;
			}
			
			//---- If not reset, then reset.
			captionTextField.setText("");
		}
		
	}
	
	
	
	/**
	 * Helper class to monitor for changes in the list of images and then to update the caption box.
	 * 
	 * @author Zayd
	 *
	 */
	public static class CaptionListSelectionListener implements ListSelectionListener {
	    public void valueChanged(ListSelectionEvent e) {
	    	JList imageList = (JList)(e.getSource()); //---- Get the list of images
	    	
	    	//---- If nothing is selected, then do nothing
	    	if(imageList.getSelectedIndex() == -1){
	    		captionTextField.setEnabled(false);
	    		return;
	    	}
	    	
	    	captionTextField.setEnabled(true);
	    	SlideShowImageInstance imageInstance = (SlideShowImageInstance)imageList.getSelectedValue();
	    	captionTextField.setText(imageInstance.getImageCaption());
	    	
	    }
	}
	
	
}
