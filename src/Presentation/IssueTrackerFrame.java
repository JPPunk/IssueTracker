package Presentation;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Business.BusinessComponentFactory;
import Business.Issue;

public class IssueTrackerFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5532618722097754725L;
	
	private AddEntitiesPanel addEntitiesPanel = null;
	private IssueDetailPanel detailPanel = null;
	private IssueSidePanel sidePanel = null;
	
	
	private int loggedInUserId = -1;

	/**w
	 * Main issue tracker frame
	 * Logs on the user
	 * Initialises side panel + add entities panel + containing issue list + detail panel
	 */
	public IssueTrackerFrame()
	{
		setTitle(StringResources.getAppTitle());
	    setLocationRelativeTo(null);
	    
	    logOnUser();
	    initialise();
	    
	    setDefaultCloseOperation(EXIT_ON_CLOSE);  
	}
	
	/**
	 *  !!! 
	 *  Only used to simulate logon
	 *  This should really be implemented using proper salted hashing
	 *	and compare hash to that in DB
	 *	!!!
	 */
	private void logOnUser() {
		String user = (String)JOptionPane.showInputDialog(
		                    this,
		                    StringResources.getEnterUserIdString(),
		                    StringResources.getUserIdFieldName(),
		                     JOptionPane.QUESTION_MESSAGE);
		if(user == null) System.exit(0);
		else if(!user.isEmpty()) loggedInUserId = Integer.parseInt(user);
	}

	private void initialise()
	{
		addEntitiesPanel = new AddEntitiesPanel();	
	    detailPanel = new IssueDetailPanel(true);	    
	    sidePanel = getSidePanel(new IssueListPanel(getAllIssues()));
	    
	    BorderLayout borderLayout = new BorderLayout();
	    borderLayout.addLayoutComponent(addEntitiesPanel, BorderLayout.NORTH);
	    borderLayout.addLayoutComponent(sidePanel, BorderLayout.WEST);
	    borderLayout.addLayoutComponent(detailPanel, BorderLayout.CENTER);
	    
	    JButton  refreshButton = new JButton(StringResources.getRefreshButtonLabel());
	    final IssueTrackerFrame frame = this;
	    refreshButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.refresh(frame.getAllIssues());
			}
		});
	    
	    borderLayout.addLayoutComponent(refreshButton, BorderLayout.SOUTH);
	    
	    this.setLayout(borderLayout);
	    this.add(addEntitiesPanel);
	    this.add(refreshButton);
	    this.add(sidePanel);
	    this.add(detailPanel);
	    this.setSize(600, 300);
	}
	
	private IssueSidePanel getSidePanel(IssueListPanel listPanel)
	{
		final IssueTrackerFrame frame = this;
		listPanel.registerIssueSelectionNotifiableObject(detailPanel);
		return new IssueSidePanel(new ISearchEventListener() {
			@Override
			public void searchClicked(String searchString) {
				frame.refresh(frame.findIssuesByTitleDescription(searchString));
			}
		},listPanel);
	}
	
	private Vector<Issue> getAllIssues()
	{
		return BusinessComponentFactory.getInstance().getIssueProvider().findUserIssues(loggedInUserId);
	}
	
	private Vector<Issue> findIssuesByTitleDescription(String pSearchString)
	{
		return BusinessComponentFactory.getInstance().getIssueProvider().
				findIssueBasedOnExpressionSearchedOnTitleAndDescription
				(pSearchString, loggedInUserId);
	}
	
	private  void refresh(Vector<Issue> issues)
	{
		if(sidePanel != null && detailPanel!= null)
		{
			sidePanel.refresh(issues);
			detailPanel.refresh();
			sidePanel.registerIssueSelectionNotifiableObject(detailPanel);
		}
	}
	
	
	public static void main(String[] args)
	{
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	IssueTrackerFrame ex = new IssueTrackerFrame();
                ex.setVisible(true);
            }
        });		
	}
}
