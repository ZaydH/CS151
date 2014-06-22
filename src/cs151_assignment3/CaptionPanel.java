package cs151_assignment3;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;

public class CaptionPanel extends JPanel  {

		
	/**
	 * UID required to extended JPanel.  System generated.
	 */
	private static final long serialVersionUID = -8073474470479993782L;
	private static JTextField captionTextField;
	private static JLabel captionLabel;
	
	
	/**
	 * Primary constructor for the Panel Containing the Picture Caption and the Caption Label ("Caption:").
	 * 
	 * @param panelWidth	Width of this Panel
	 * @param panelHeight	Height of this Panel
	 * @param labelWidth	Width of the left label for this panel.
	 * @param padding		Spacing between items in the Panel.
	 */
	public CaptionPanel(int panelWidth, int panelHeight, int labelWidth, int padding){
				

		
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
		captionLabel.setText("Caption:");
		this.add(captionLabel);
		
		//----- Set the captions layout constraints
		SpringLayout.Constraints captionLabelConstraints = captionSpringLayout.getConstraints(captionLabel);
		captionLabelConstraints.setX(Spring.constant(labelWidth - captionLabelConstraints.getWidth().getValue()));
		
		
		//---- Create and Add the Caption Field
		captionTextField = new JTextField("Initializing", JTextField.TRAILING);
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
	
	
}
