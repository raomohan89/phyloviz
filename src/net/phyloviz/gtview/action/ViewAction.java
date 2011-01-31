package net.phyloviz.gtview.action;

import java.beans.PropertyChangeEvent;
import javax.swing.SwingUtilities;
import net.phyloviz.core.data.DataSet;
import net.phyloviz.goeburst.GOeBurstResult;
import net.phyloviz.gtview.ui.GTPanel;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.Node;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;
import org.openide.windows.TopComponent;

public class ViewAction extends NodeAction {

	@Override
	protected void performAction(Node[] nodes) {
		GOeBurstResult gr = nodes[0].getLookup().lookup(GOeBurstResult.class);
		
		//If a viewer is already open, just give it focus
		for (TopComponent tc : TopComponent.getRegistry().getOpened()) {
			if (tc instanceof GTPanel && tc.getLookup().lookup(GOeBurstResult.class) == gr) {
				tc.requestActive();
				return;
			}
		}

		DataSet ds = nodes[0].getParentNode().getLookup().lookup(DataSet.class);

		//Nope, need a new viewer
        	StatusDisplayer.getDefault().setStatusText("Starting display...");
		GTPanel tvp = new GTPanel(nodes[0].getParentNode().getDisplayName() + ": " + nodes[0].getDisplayName(), gr, ds);
		tvp.open();
		tvp.requestActive();

		nodes[0].addNodeListener(new LocalNodeListener(tvp));
	
	}

	@Override
	protected boolean enable(Node[] nodes) {
		return nodes.length == 1;
	}

	@Override
	protected boolean asynchronous() {
		return false;
	}

	@Override
	public String getName() {
		return "View";
	}

	@Override
	public HelpCtx getHelpCtx() {
		return null;
	}

	private class LocalNodeListener implements NodeListener {

		private GTPanel tvp;

		LocalNodeListener(GTPanel tvp) {
			this.tvp = tvp;
		}

		@Override
		public void childrenAdded(NodeMemberEvent nme) {
		}

		@Override
		public void childrenRemoved(NodeMemberEvent nme) {
		}

		@Override
		public void childrenReordered(NodeReorderEvent nre) {
		}

		@Override
		public void nodeDestroyed(NodeEvent ne) {

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					tvp.close();
				}
			});
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
		}
	}

}
