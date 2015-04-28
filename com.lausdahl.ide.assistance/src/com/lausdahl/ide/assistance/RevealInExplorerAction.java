//package com.lausdahl.ide.assistance;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.eclipse.core.resources.IFile;
//import org.eclipse.core.resources.IFolder;
//import org.eclipse.core.resources.IProject;
//import org.eclipse.core.resources.IResource;
//import org.eclipse.jdt.core.IJavaElement;
//import org.eclipse.jface.action.IAction;
//import org.eclipse.jface.viewers.ISelection;
//import org.eclipse.jface.viewers.TreeSelection;
//import org.eclipse.ui.IObjectActionDelegate;
//import org.eclipse.ui.IWorkbenchPart;
//
//public class RevealInExplorerAction implements IObjectActionDelegate
//{
//	ISelection selection = null;
//
//	public void setActivePart(IAction action, IWorkbenchPart targetPart)
//	{
//		
//	}
//
//	public void run(IAction action)
//	{
//
//		if (selection != null && selection instanceof TreeSelection)
//		{
//			TreeSelection ts = (TreeSelection) selection;
//			Object item = ts.getFirstElement();
//			open(item);
//		}
//	}
//	
//
//
//	public void selectionChanged(IAction action, ISelection selection)
//	{
//		this.selection = selection;
//	}
//
//}
