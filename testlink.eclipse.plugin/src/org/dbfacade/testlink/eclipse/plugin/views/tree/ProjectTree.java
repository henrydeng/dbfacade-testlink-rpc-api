package org.dbfacade.testlink.eclipse.plugin.views.tree;


import java.util.Iterator;
import java.util.Map;

import org.dbfacade.testlink.api.client.TestLinkAPIClient;
import org.dbfacade.testlink.eclipse.plugin.UserMsg;
import org.dbfacade.testlink.eclipse.plugin.preferences.TestLinkPreferences;
import org.dbfacade.testlink.tc.autoexec.TestPlan;
import org.dbfacade.testlink.tc.autoexec.TestPlanLoader;
import org.dbfacade.testlink.tc.autoexec.TestProject;
import org.eclipse.jface.viewers.TreeViewer;


public class ProjectTree extends TreeParent
{
	private String projectName;
	private TestProject project = null;
	private TreeViewer viewer;
	
	/**
	 * Used for initial display
	 * 
	 * @param projectName
	 */
	public ProjectTree(
		TreeViewer viewer,
		String projectName)
	{
		super(projectName);
		this.projectName = projectName;
		this.viewer = viewer;
	}
	
	/**
	 * Use for full initializarion
	 * 
	 * @param project
	 */
	public ProjectTree(
		TreeViewer viewer,
		TestProject project)
	{
		super(project.getProjectName());
		this.project = project;
		this.projectName = project.getProjectName();
		this.viewer = viewer;
	}
	
	public boolean isOpen() {
		return (project != null);
	}
	
	/**
	 * Get the project information
	 * 
	 * @return
	 */
	public TestProject getProject()
	{
		return project;
	}
	
	public void setProject(
		TestProject project)
	{
		this.project = project;
		this.setName(project.getProjectName());
	}
	
	/**
	 * Always return true since if there are not children we will open a no test plans message.
	 */
	public boolean hasChildren()
	{
		return true;
	}
	
	public void findChildren()
	{
		try {
			TestLinkPreferences pref = new TestLinkPreferences();
			TestLinkAPIClient apiClient = pref.getTestLinkAPIClient();

			TestPlanLoader loader = new TestPlanLoader(projectName, apiClient);
			Map<Integer,
				TestPlan> plans = loader.getPlans();
		
			Iterator planIDs = plans.keySet().iterator();
			while ( planIDs.hasNext() ) {
				Object planID = planIDs.next();
				TestPlan plan = plans.get(planID);
				PlanTree planRoot = new PlanTree(viewer, plan);
				this.addChild(planRoot);
			}
		} catch ( Exception e ) {
			UserMsg.error(e, "Failed to find the children for the project.");
		}
		
		if ( children.size() == 0 ) {
			PlanTree planRoot = new PlanTree(viewer, "No test plans acquired");
			this.addChild(planRoot);
		}
	}
	
}
