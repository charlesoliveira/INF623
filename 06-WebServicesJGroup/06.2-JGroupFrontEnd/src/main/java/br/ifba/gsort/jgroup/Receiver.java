package br.ifba.gsort.jgroup;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.ViewId;
import org.jgroups.util.Util;

import br.ifba.gsort.dao.BancoDadosDAO;

public class Receiver extends ReceiverAdapter {

	private JCluster cluster;
	private ViewId me;
	private BancoDadosDAO dao;
	String user_name = System.getProperty("user.name", "n/a");
	final List<String> state = new LinkedList<String>();
	
	public Receiver(JCluster cluster) {
		this.cluster = cluster;
		cluster.getChannel().setReceiver(this);
	}
	@Override
	public void receive(Message msg) {
		dao.executarSQL(msg.getObject().toString());

		String line = msg.getSrc() + ": " + msg.getObject();
		System.out.println(line);
		synchronized (state) {
			state.add(line);
		}
	}

	@Override
	public void getState(OutputStream output) throws Exception {
		 synchronized(state) {
	            Util.objectToStream(state, new DataOutputStream(output));
	        }
	}

	@Override
	public void setState(InputStream input) throws Exception {
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) Util.objectFromStream(new DataInputStream(input));
		synchronized (state) {
			state.clear();
			state.addAll(list);

			for (String sql : list) {
				dao.executarSQL(sql);
			}
		}
		for (String str : list) {
			System.out.println(str);
		}
	}

	@Override
	public void viewAccepted(View new_view) {
		me = new_view.getViewId();
		dao = new BancoDadosDAO(cluster.getJdbcList()
				.get(new_view.getMembers().indexOf(new_view) != -1 ? new_view.getMembers().indexOf(new_view) : 0));
		System.out.println("** view: " + new_view);

	}

	

}
