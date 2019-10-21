package Business;

import java.util.Vector;

import Data.RepositoryProviderFactory;

/**
 * Encapsulates any business logic to be executed on the app server; 
 * and uses the data layer for data queries/creates/updates/deletes
 * @author matthewsladescu
 *
 */
public class IssueProvider implements IIssueProvider{


	/**
	 * Update the details for a given issue
	 * @param issue : the issue for which to update details
	 */
	@Override
	public void updateIssue(Issue issue) {
		RepositoryProviderFactory.getInstance().getRepositoryProvider().updateIssue(issue);
	}

	/**
	 * Find the issues associated in some way with a user
	 * Issues which have the id parameter below in any one or more of the
	 * creator, resolver, or verifier fields should be included in the result
	 * @param id
	 * @return
	 */
	@Override
	public Vector<Issue> findUserIssues(int id) {		
		return RepositoryProviderFactory.getInstance().getRepositoryProvider().findUserIssues(id);
	}
	
	/**
	 * Add the details for a new issue to the database
	 * @param issue: the new issue to add
	 */
	@Override
	public void addIssue(Issue issue) {
		RepositoryProviderFactory.getInstance().getRepositoryProvider().addIssue(issue);
	}



	/**
	 * Given an expression searchString like myFirst words|my second words
	 * this method should return any issues associated with a user based on userId that either:
	 * contain 1 or more of the phrases separated by the '|' character in the issue title OR
	 * contain 1 or more of the phrases separated by the '|' character in the issue description OR
	 * @param searchString: the searchString to use for finding issues in the database based on the issue titles and
	 * descriptions. searchString may either be a single phrase, or a phrase separated by the '|' character. The searchString
	 * is used as described above to find matching issues in the database.
	 * @param userId: used to first find issues associated with userId on either one or more of the creator/resolver/verifier
	 * fields. Once a user's issues are identified, the search would then take place on the user's associated issues.
	 * @return
	 */
	@Override
	public Vector<Issue> findIssueBasedOnExpressionSearchedOnTitleAndDescription(
			String searchString, int userId) {
		return RepositoryProviderFactory.getInstance().getRepositoryProvider().
		findIssueBasedOnExpressionSearchedOnTitleAndDescription(searchString, userId);

	}

}
