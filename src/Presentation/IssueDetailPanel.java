package Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Business.BusinessComponentFactory;
import Business.Issue;


/**
 * 
 * @author matthewsladescu
 * Panel used for creating and updating issues
 */
public class IssueDetailPanel extends JPanel implements IIssueSelectionNotifiable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2031054367491790942L;
	
	private Issue currentIssue = null;
	private boolean isUpdatePanel = true;
	
	private JTextField idField; 
	private JTextField titleField; 
	private JTextField creatorField;
	private JTextField resolverField;
	private JTextField verifierField;
	private JTextArea desc;
	
	/**
	 * Panel used for creating and updating issues
	 * @param isUpdatePanel : describes whether panel will be used to either create or update issue
	 */
	public IssueDetailPanel(boolean isUpdatePanel)
	{
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.isUpdatePanel = isUpdatePanel;
	}

	/**
	 * Re-populates panel details with given issue object
	 * @param issue new issue object to populate panel details with
	 */
	public void initIssueDetails(Issue issue) {
		removeAll();	
		if(issue != null)
		{
			currentIssue = issue;
			addAll();
		}
	}

	private void addAll() {
		JPanel lTextFieldPanel = new JPanel();
		BoxLayout lTextFieldLayout = new BoxLayout(lTextFieldPanel, BoxLayout.Y_AXIS);
		lTextFieldPanel.setLayout(lTextFieldLayout);
		
		BorderLayout lPanelLayout = new BorderLayout();	
		lPanelLayout.addLayoutComponent(lTextFieldPanel, BorderLayout.NORTH);

		//create issue text fields
		//application convention is to map null to empty string (if db has null this will be shown as empty string)
		if(currentIssue.getId() > 0) {
			idField = createLabelTextFieldPair(StringResources.getIssueIDLabel(), ""+currentIssue.getId(), lTextFieldPanel);
			idField.setEditable(false);
		}
		titleField = createLabelTextFieldPair(StringResources.getIssueTitleLabel(), currentIssue.getTitle() == null ? "" : currentIssue.getTitle(), lTextFieldPanel);
		creatorField = createLabelTextFieldPair(StringResources.getIssueCreatorLabel(), currentIssue.getCreator() == null? "" : ""+currentIssue.getCreator(), lTextFieldPanel);
		resolverField = createLabelTextFieldPair(StringResources.getIssueResolverLabel(),currentIssue.getResolver() == null? "" : ""+ currentIssue.getResolver(), lTextFieldPanel);
		verifierField = createLabelTextFieldPair(StringResources.getIssueVerifierLabel(), currentIssue.getVerifier() == null? "" : ""+currentIssue.getVerifier(), lTextFieldPanel);
		add(lTextFieldPanel);
		
		//create issue description text area
		desc = new JTextArea(currentIssue.getDescription() == null ? "" : currentIssue.getDescription());
		desc.setAutoscrolls(true);
		desc.setLineWrap(true);
		lPanelLayout.addLayoutComponent(desc, BorderLayout.CENTER);
		add(desc);
		
		//create issue save (create or update button)
		JButton saveButton = createIssueSaveButton();
		lPanelLayout.addLayoutComponent(saveButton, BorderLayout.SOUTH);
		this.add(saveButton);

		setLayout(lPanelLayout);
		updateUI();
	}

	private JButton createIssueSaveButton() {
		JButton saveButton = new JButton(isUpdatePanel ? StringResources.getIssueUpdateButtonLabel() : 
			StringResources.getIssueAddButtonLabel());
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//application convention is to map empty string to null (ie: if app has empty string - this will be null in db)
				currentIssue.setTitle(titleField.getText().equals("") ? null : titleField.getText() ); 
				currentIssue.setCreator(creatorField.getText().equals("") ? null : Integer.parseInt(creatorField.getText()));
				currentIssue.setResolver(resolverField.getText().equals("") ? null : Integer.parseInt(resolverField.getText()));
				currentIssue.setVerifier(verifierField.getText().equals("")  ? null : Integer.parseInt(verifierField.getText()));
				currentIssue.setDescription(desc.getText().equals("")  ? null : desc.getText());
				if(isUpdatePanel)
				{
					BusinessComponentFactory.getInstance().getIssueProvider().updateIssue(currentIssue);
				}
				else {
					BusinessComponentFactory.getInstance().getIssueProvider().addIssue(currentIssue);
				}
			}
		});
		
		return saveButton;
	}

	private JTextField createLabelTextFieldPair(String label, String value, JPanel container) {
		
		JPanel pairPanel = new JPanel();
		JLabel jlabel = new JLabel(label);
		JTextField field = new JTextField(value);
		pairPanel.add(jlabel);
		pairPanel.add(field);

		container.add(pairPanel);

		BorderLayout lPairLayout = new BorderLayout();
		lPairLayout.addLayoutComponent(jlabel, BorderLayout.WEST);
		lPairLayout.addLayoutComponent(field, BorderLayout.CENTER);
		pairPanel.setLayout(lPairLayout);	
		
		return field;
	}

	/**
	 * Implementation of IIssueSelectionNotifiable::issueSelected used to switch issue
	 * dispayed on IssueDisplayPanel
	 */
	@Override
	public void issueSelected(Issue issue) {
		initIssueDetails(issue);
	}
	
	/**
	 * Clear issue details panel
	 */
	public void refresh()
	{
		initIssueDetails(null);
	}
}
