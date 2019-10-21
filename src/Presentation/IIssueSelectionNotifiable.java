package Presentation;

import Business.Issue;

/**
 * 
 * @author matthewsladescu
 * 
 * Used to notify any interested object that implements this interface
 * and registers with IssueListPanel of an IssueSelection
 *
 */
public interface IIssueSelectionNotifiable {
	public void issueSelected(Issue issue);
}
