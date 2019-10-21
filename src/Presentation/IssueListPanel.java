package Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Business.Issue;

/**
 * Panel encapsulating issue list
 * @author matthewsladescu
 *
 */
public class IssueListPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1013855025757989473L;
	
	private List<IIssueSelectionNotifiable> notifiables = new ArrayList<IIssueSelectionNotifiable>();
	private Vector<Issue> issues;
	
	/**
	 * 
	 * @param issues vector of issues to display in the issue list panel
	 */
	public IssueListPanel(Vector<Issue> issues)
	{
		this.issues = issues;
		this.setBorder(BorderFactory.createLineBorder(Color.black));	
		initList(this.issues);
	}

	private void initList(Vector<Issue> issues) {
		
		final JList<Issue> list = new JList<Issue>(issues);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScroller = new JScrollPane(list);
		this.add(listScroller);
		BorderLayout listLayout = new BorderLayout();
		listLayout.addLayoutComponent(listScroller, BorderLayout.CENTER);
		this.setLayout(listLayout);		
		list.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) {
				for(IIssueSelectionNotifiable notifiable : notifiables)
				{
					Issue selectedIssue = list.getSelectedValue();
					if(selectedIssue != null)
					{
						notifiable.issueSelected(selectedIssue);
					}
				}
			}		
		});
	}
	
	/**
	 * Refresh issue list to display vector of issue objects
	 * @param issues - vector of issue objects to display
	 */
	public void refresh(Vector<Issue> issues)
	{
		this.removeAll();
		this.initList(issues);
		this.updateUI();
		this.notifiables.clear();
	}
	
	/**
	 * Register an object to be notified of an issue selection change
	 * @param notifiable object to invoke when a new issue is selected
	 */
	public void registerIssueSelectionNotifiableObject(IIssueSelectionNotifiable notifiable)
	{
		notifiables.add(notifiable);
	}

}
