package com.lausdahl.ide.assistance;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

public class RevealInExplorerHandler extends AbstractHandler implements
		IHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		RevealInExplorerAction action = new RevealInExplorerAction();
		action.run(null);
		return null;
	}

}
