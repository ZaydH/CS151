package cs151_assignment5;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeListener;



/**
 * 
 * One of four helper panels.  This one contains the "Caption:" JLabel and the caption JTextField
 * 
 * @author Zayd
 *
 */

public class SlideShowSliderPanel extends JPanel  {

		
	/**
	 * UID required to extended JPanel.  System generated.
	 */
	private static final long serialVersionUID = -8073474470479993782L;
	private static final String SLIDER_DELAY_LABEL_STRING = "Delay";
	private static JLabel delayLabel;
	private static JSlider delaySlider;
	
	
	/**
	 * Primary constructor for the Panel Containing the Picture Caption and the Caption Label ("Caption:").
	 * 
	 * @param panelWidth	Width of this Panel
	 * @param panelHeight	Height of this Panel
	 * @param labelWidth	Width of the left label for this panel.
	 * @param padding		Spacing between items in the Panel.
	 */
	public SlideShowSliderPanel(int panelWidth, int panelHeight, int sliderWidth, 
								int sliderHeight, int padding ){
				
		
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
		delayLabel = new JLabel();
		delayLabel.setText(SLIDER_DELAY_LABEL_STRING);
		this.add(delayLabel);
		
		
		//---- Create and Add the Caption Field
		delaySlider = new JSlider(10 /* ms */, 10000 /* ms */);
		Dimension sliderDimension = new Dimension( sliderWidth, sliderHeight );
		delaySlider.setSize(sliderDimension);
		delaySlider.setPreferredSize(sliderDimension);
		delaySlider.setMinimumSize(sliderDimension);
		delaySlider.setMaximumSize(sliderDimension);
		delaySlider.setEnabled(false);
		this.add(delaySlider);
		

		//----- Place the components horizontally
		captionSpringLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, delaySlider, 0, SpringLayout.HORIZONTAL_CENTER, this);	//---- Space the caption padding pixels from the label
		captionSpringLayout.putConstraint(SpringLayout.WEST, delayLabel, 0, SpringLayout.WEST, delaySlider);		//---- Space "padding" pixels from the top of the panel
		
		//--- Place the components vertically
		captionSpringLayout.putConstraint(SpringLayout.NORTH, delayLabel, padding, SpringLayout.NORTH, this);		//---- Space "padding" pixels from the top of the panel
		captionSpringLayout.putConstraint(SpringLayout.NORTH, delaySlider, padding, SpringLayout.SOUTH, delayLabel);		//---- Space "padding" pixels from the top of the panel
		
	}
	

	/**
	 * Creates an ActionListener that once the GUI enters a valid state the GUI buttons are enabled.
	 * 
	 * @return	ActionListener that when an event occurs enables the buttons.
	 */
	public ActionListener createSliderEnableListener(){

		/**
		 * Class that creates a threaded ActionListener to enable the navigation buttons.
		 * 
		 * @author Zayd
		 */
		class EnableSliderActionListener extends Thread implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e){
				this.run();
			}
			
			@Override
			public void run(){
				//----- Enable the slider and force an update on its state for all other objects.
				delaySlider.setEnabled(true);
				int sliderCurrentValue = delaySlider.getValue();
				delaySlider.setValue( delaySlider.getMinimum() );
				delaySlider.setValue( sliderCurrentValue );
			}
		}
		
		return new EnableSliderActionListener();
	}	
	
	
	/**
	 * Adds the input ChangeListener to the JSlider.
	 * 
	 * @param listener Listener that will be added to the slider.
	 */
	public void addSliderListener(ChangeListener listener){
		delaySlider.addChangeListener(listener);
	}
	
}
