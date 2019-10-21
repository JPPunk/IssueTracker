package Presentation;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JPanel;

import Business.Issue;

/**
 * 
 * Represents issue list panel of issue tracker that includes
 * both the search box/button and text field; AND the issue list
 * @author matthewsladescu
 *
 */
public class IssueSidePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2693528613703066603L;

	private IssueListPanel mListPanel;
	
	/**
	 * Represents left panel of issue tracker that includes
	 * both the search box/button and text field; AND the issue list
	 * 
	 * @param searchEventListener : used to retrieve user search query in search box
	 * @param listPanel : issue list panel
	 */
	public IssueSidePanel(ISearchEventListener searchEventListener, IssueListPanel listPanel)
	{
		mListPanel = listPanel;
		IssueSearchComponents searchComponents = new IssueSearchComponents(searchEventListener);
	
		add(searchComponents);
		add(listPanel);
		
		BorderLayout layout = new BorderLayout();
		layout.addLayoutComponent(searchComponents, BorderLayout.NORTH);
		layout.addLayoutComponent(listPanel, BorderLayout.CENTER);
		setLayout(layout);
	}
	
	public void refresh(Vector<Issue> issues)
	{
		mListPanel.refresh(issues);
	}
	
	public void registerIssueSelectionNotifiableObject(IIssueSelectionNotifiable notifiable)
	{
		mListPanel.registerIssueSelectionNotifiableObject(notifiable);
	}
}
