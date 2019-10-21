package Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * @author matthewsladescu
 * AddEntitiesPanel is shown at the top of the issue tracker window
 * - this is where all buttons used to add entities like Issue/Project/User should be added
 * 
 */
public class AddEntitiesPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2256207501462485047L;
	

	private JButton addIssueButton = new JButton("Add New Issue");


	public AddEntitiesPanel()
	{
		setBorder(BorderFactory.createLineBorder(Color.black));
		add(addIssueButton);
		updateLayout();
		addIssueButton.addActionListener(new ActionListener() {		
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				AddIssueDialog dialog = new AddIssueDialog();
				dialog.show();
			}
		});	
	}


	private void updateLayout() {
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		layout.addLayoutComponent(addIssueButton, BorderLayout.CENTER);
	}
}
