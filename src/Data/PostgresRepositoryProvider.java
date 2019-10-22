package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.postgresql.ds.PGSimpleDataSource;
import Business.Issue;
import Presentation.IRepositoryProvider;

/**
 * Encapsulates create/read/update/delete operations to PostgreSQL database
 * @author Lijun
 */
public class PostgresRepositoryProvider implements IRepositoryProvider {
	   // connection parameters - ENTER YOUR LOGIN AND PASSWORD HERE
    private final String userid = "postgres";
    private final String passwd = "ljp";
    private final String myHost = "localhost:5432";
    private final String database = "IssueTracker";

	private Connection openConnection() throws SQLException
	{
		PGSimpleDataSource source = new PGSimpleDataSource();
		source.setServerName(myHost);
		source.setDatabaseName(database);
		source.setUser(userid);
		source.setPassword(passwd);
		Connection conn = source.getConnection();
	    
	    return conn;
	}

	/**
	 * Find the user associated issues as per the assignment description
	 * @param userId the user id
	 * @return
	 */
	@Override
	public Vector<Issue> findUserIssues(int userId) {		
		Vector<Issue> results = new Vector<Issue>();
		// TODO - list all user associated issues from db using sql
		try {
			Connection conn = openConnection();
			String sqlFUI = "SELECT * FROM A3_ISSUE " 
					+ "WHERE creator=? OR resolver=? OR verifier=? ORDER BY title";
			PreparedStatement stmt = conn.prepareStatement(sqlFUI);
			stmt.setInt(1, userId);
			stmt.setInt(2, userId);
			stmt.setInt(3, userId);
			ResultSet rset = stmt.executeQuery(); 
            int nr = 0;
            while (rset.next()) {
            	nr++;
            	Issue issue = new Issue();
            	/*Create Issue object*/
            	int creator = rset.getInt("creator");
            	issue.setCreator(creator);
            	int resolver = rset.getInt("resolver");
            	issue.setResolver(resolver);            	
            	int verifier = rset.getInt("verifier");
            	issue.setVerifier(verifier);
            	String desc = rset.getString("description");
            	issue.setDescription(desc);
            	String title = rset.getString("title");
            	issue.setTitle(title);
            	int issueId = rset.getInt("issue_id");
            	issue.setId(issueId);
            	//Add object to results.
            	results.addElement(issue);
            }
            
            if ( nr == 0 )
            	 System.out.println("No entries found.");
        	stmt.close();
        	conn.close();
            	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results;
	}

	/**
	 * Find the associated issues for the user with the given userId based 
	 * on the searchString provided as the parameter, and based on the assignment description
	 * @param searchString: see assignment description search specification
	 * @param userId: the userId for the user associated issues to look through
	 * @return
	 */
	@Override
	public Vector<Issue> findIssueBasedOnExpressionSearchedOnTitleAndDescription(
			String searchString, int userId) {
		
		Vector<Issue> results = new Vector<Issue>();
		// TODO - find necessary issues using sql database based on search input
		try {
			Connection conn = openConnection();
			String sql = "SELECT * FROM A3_ISSUE " 
					+ "WHERE (creator=? OR resolver=? OR verifier=?) "
					+ "AND title like ? ORDER BY title";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, userId);
			stmt.setInt(3, userId);
			stmt.setString(4, "%" + searchString + "%");
			ResultSet rset = stmt.executeQuery(); 
            int nr = 0;
            while (rset.next()) {
            	nr++;
            	Issue issue = new Issue();
            	/*Create Issue object*/
            	int creator = rset.getInt("creator");
            	issue.setCreator(creator);
            	int resolver = rset.getInt("resolver");
            	issue.setResolver(resolver);            	
            	int verifier = rset.getInt("verifier");
            	issue.setResolver(verifier);
            	String desc = rset.getString("description");
            	issue.setDescription(desc);
            	String title = rset.getString("title");
            	issue.setTitle(title);
            	int issueId = rset.getInt("issue_id");
            	issue.setId(issueId);
            	//Add object to results.
            	results.addElement(issue);
            }

            if ( nr == 0 )
            	 System.out.println("No entries found.");
            stmt.close();
            conn.close();
            	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
		return results;
		}

	/**
	 * Add the details for a new issue to the database
	 * @param issue: the new issue to add
	 */
	@Override
	public void addIssue(Issue issue) {
	    //TODO - add an issue
	    //Insert a new issue to database
		try {
			int creator = issue.getCreator();
			int resolver = issue.getResolver();
			int verifier = issue.getVerifier();
			String desc = issue.getDescription();
			String title = issue.getTitle();
			Connection conn = openConnection();
			String sql_user = "INSERT INTO a3_issue(title, creator, resolver, verifier, description) "
					+ "VALUES(?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql_user);
			stmt.setString(1, title);
			stmt.setInt(2, creator);
			stmt.setInt(3, resolver);
			stmt.setInt(4, verifier);
			stmt.setString(5, desc);;
			stmt.executeUpdate();
        	stmt.close();
        	conn.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * Update the details for a given issue
	 * @param issue : the issue for which to update details
	 */
	@Override
	public void updateIssue(Issue issue) {
		//TODO - update the issue using db
		try {
			int creator = issue.getCreator();
			int resolver = issue.getResolver();
			int verifier = issue.getVerifier();
			String desc = issue.getDescription();
			String title = issue.getTitle();
			int issueId = issue.getId();
			Connection conn = openConnection();
			String sql_user = "UPDATE a3_issue SET title=?, creator=?, resolver=?, verifier=?, description=? "
					+ "WHERE issue_id=?";
			PreparedStatement stmt = conn.prepareStatement(sql_user);
			stmt.setString(1, title);
			stmt.setInt(2, creator);
			stmt.setInt(3, resolver);
			stmt.setInt(4, verifier);
			stmt.setString(5, desc);;
			stmt.setInt(6, issueId);
			stmt.executeUpdate();
        	stmt.close();
        	conn.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
