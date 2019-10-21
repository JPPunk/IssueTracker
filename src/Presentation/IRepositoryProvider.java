package Presentation;

import java.util.Vector;

import Business.Issue;

/**
 * Encapsulates create/read/update/delete operations to database
 * @author matthewsladescu
 *
 */
public interface IRepositoryProvider {
	/**
	 * Update the details for a given issue
	 * @param issue : the issue for which to update details
	 */
	public void updateIssue(Issue issue);
	
	/**
	 * Find the user associated issues as per the assignment description
	 * @param id the user id
	 * @return
	 */
	public Vector<Issue>  findUserIssues(int id);
	
	/**
	 * Find the associated issues for the user with the given userId based 
	 * on the searchString provided as the parameter, and based on the assignment description
	 * @param searchString: see assignment description search specification
	 * @param userId: the userId for the user associated issues to look through
	 * @return
	 */
	public Vector<Issue> findIssueBasedOnExpressionSearchedOnTitleAndDescription
																(String searchString, int userId);	
	
	/**
	 * Add the details for a new issue to the database
	 * @param issue: the new issue to add
	 */
	public void addIssue(Issue issue);
}
