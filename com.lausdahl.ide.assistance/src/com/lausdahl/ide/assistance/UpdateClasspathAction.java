package com.lausdahl.ide.assistance;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.eclipse.pde.internal.core.ClasspathComputer;
import org.eclipse.pde.internal.ui.PDEPlugin;
import org.eclipse.pde.internal.ui.PDEUIMessages;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

@SuppressWarnings("restriction")
public class UpdateClasspathAction implements IObjectActionDelegate
{
	ISelection selection = null;

	public void setActivePart(IAction action, IWorkbenchPart targetPart)
	{

	}

	public void run(IAction action)
	{

		if (selection != null && selection instanceof TreeSelection)
		{
			TreeSelection ts = (TreeSelection) selection;

			for (Object item : ts.toList())
			{

				if (item instanceof IJavaProject)
				{
					IJavaProject project = (IJavaProject) item;
					try
					{
						project.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
						// getPage().getEditor().doSave(null);
						IPluginModelBase model = PluginRegistry.findModel(project.getProject());
						if (model != null)
						{
							ClasspathComputer.setClasspath(project.getProject(), model);
							if (PDEPlugin.getWorkspace().isAutoBuilding())
							{
								doFullBuild(project.getProject());
							}
						}
					} catch (CoreException e1)
					{
					}
				}
			}
		}
	}

	private void doFullBuild(final IProject project)
	{
		Job buildJob = new Job(PDEUIMessages.CompilersConfigurationBlock_building)
		{
			public boolean belongsTo(Object family)
			{
				return ResourcesPlugin.FAMILY_MANUAL_BUILD == family;
			}

			protected IStatus run(IProgressMonitor monitor)
			{
				try
				{
					project.build(IncrementalProjectBuilder.FULL_BUILD, JavaCore.BUILDER_ID, null, monitor);
				} catch (CoreException e)
				{
				}
				return Status.OK_STATUS;
			}
		};
		buildJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
		buildJob.schedule();
	}

	public void selectionChanged(IAction action, ISelection selection)
	{
		this.selection = selection;
	}
}
