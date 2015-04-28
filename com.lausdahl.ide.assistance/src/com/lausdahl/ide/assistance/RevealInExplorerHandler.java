package com.lausdahl.ide.assistance;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class RevealInExplorerHandler extends AbstractHandler implements
		IHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection != null) {

			if (selection instanceof TreeSelection) {
				TreeSelection ts = (TreeSelection) selection;
				Object item = ts.getFirstElement();
				open(item);
			} else {
				open(selection);
			}
		}
		return null;
	}

	private void open(Object item) {
		if (item instanceof IFolder) {
			open(((IFolder) item).getLocation().toFile(), false);
		} else if (item instanceof IFile) {
			open(((IFile) item).getLocation().toFile(), true);
		} else if (item instanceof IProject) {
			open(((IResource) item).getLocation().toFile(), false);
		} else if (item instanceof IJavaElement) {
			IJavaElement element = (IJavaElement) item;
			IResource res = (IResource) element.getAdapter(IResource.class);
			if (res != null) {
				open(res);
			}
		} else {

			// System.out.println(item);

			try {
				IResource res = (IResource) ((IResource) item)
						.getAdapter(IResource.class);
				if (res != null) {
					open(res);
				}
			} catch (Throwable t) {
			}
		}
	}

	private void open(File file, boolean select) {
		if (isWindows()) {
			winOpen(file, select);
		} else if (isUnix()) {
			linuxOpen(file, select);
		} else if (isMac()) {
			macOpen(file, select);
		}

	}

	public static boolean isWindows() {

		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return (os.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		String os = System.getProperty("os.name").toLowerCase();
		// linux or unix
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);

	}

	private void winOpen(File file, boolean select) {
		try {
			// http://support.microsoft.com/kb/152457
			String command = null;
			if (select) {
				command = "explorer /select," + file.getAbsolutePath();
			} else {
				command = "explorer " + file.getAbsolutePath();

			}
			// System.out.println(command);
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// ignore it
		}
	}

	private void macOpen(File file, boolean select) {
		try {
			String command = "open " + file.getAbsolutePath();
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// ignore it
		}
	}

	private void linuxOpen(File file, boolean select) {
		try {
			String command = "gnome-open " + file.getAbsolutePath();
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// ignore it
		}
	}

}
