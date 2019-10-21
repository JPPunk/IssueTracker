package Presentation;

import java.awt.BorderLayout;

import javax.swing.JDialog;

import Business.Issue;

/**
 * 
 * @author matthewsladescu
 *
 * AddIssueDialog - used to add a new issue
 * 
 */
public class AddIssueDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 173323780409671768L;
	
	/**
	 * detailPanel: reuse IssueDetailPanel to add issues
	 */
	private IssueDetailPanel detailPanel = new IssueDetailPanel(false);

	public AddIssueDialog()
	{
		detailPanel.initIssueDetails(getBlankIssue());
		add(detailPanel);
		updateLayout();
		setSize(400, 400);
	}

	private void updateLayout() {
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		layout.addLayoutComponent(detailPanel, BorderLayout.CENTER);
	}

	private Issue getBlankIssue() {
		Issue issue = new Issue();
		return issue;
	}
}
