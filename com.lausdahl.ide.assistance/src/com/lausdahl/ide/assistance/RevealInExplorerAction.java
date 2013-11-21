package com.lausdahl.ide.assistance;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class RevealInExplorerAction implements IObjectActionDelegate
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
			Object item = ts.getFirstElement();
			open(item);
		}
	}
	
	private void open(Object item)
	{
		if (item instanceof IFolder)
		{
			open(((IFolder) item).getLocation().toFile(), false);
		} else if (item instanceof IFile)
		{
			open(((IFile) item).getLocation().toFile(), true);
		} else if (item instanceof IProject)
		{
			open(((IResource) item).getLocation().toFile(), false);
		}else if(item instanceof IJavaElement)
		{
			IJavaElement element = (IJavaElement) item;
			IResource res =(IResource) element.getAdapter(IResource.class);
			if(res!=null)
			{
				open(res);
			}
		}else{
		
//			System.out.println(item);
		}
	}

	private void open(File file, boolean select)
	{
		if (isWindows())
		{
			winOpen(file, select);
		} else if (isUnix())
		{
			linuxOpen(file, select);
		} else if (isMac())
		{
			macOpen(file, select);
		}

	}

	public static boolean isWindows()
	{

		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);

	}

	public static boolean isMac()
	{

		String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return (os.indexOf("mac") >= 0);

	}

	public static boolean isUnix()
	{

		String os = System.getProperty("os.name").toLowerCase();
		// linux or unix
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);

	}

	private void winOpen(File file, boolean select)
	{
		try
		{
			// http://support.microsoft.com/kb/152457
			String command = null;
			if (select)
			{
				command = "explorer /select," + file.getAbsolutePath();
			} else
			{
				command = "explorer " + file.getAbsolutePath();

			}
//			System.out.println(command);
			Runtime.getRuntime().exec(command);
		} catch (IOException e)
		{
			// ignore it
		}
	}

	private void macOpen(File file, boolean select)
	{
		try
		{
			String command = "open " + file.getAbsolutePath();
			Runtime.getRuntime().exec(command);
		} catch (IOException e)
		{
			// ignore it
		}
	}
	
	private void linuxOpen(File file, boolean select)
	{
		try
		{
			String command = "gnome-open " + file.getAbsolutePath();
			Runtime.getRuntime().exec(command);
		} catch (IOException e)
		{
			// ignore it
		}
	}

	public void selectionChanged(IAction action, ISelection selection)
	{
		this.selection = selection;
	}

}
