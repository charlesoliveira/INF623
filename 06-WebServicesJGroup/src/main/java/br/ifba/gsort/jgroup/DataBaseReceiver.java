package br.ifba.gsort.jgroup;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.ViewId;
import org.jgroups.util.Util;

import br.ifba.gsort.dao.BancoDadosDAO;

public class DataBaseReceiver extends ReceiverAdapter {
	
	
	protected JChannel channel;
	protected ViewId viewId;
	protected BancoDadosDAO dao;
	protected String user_name = System.getProperty("user.name", "n/a");
	protected final List<IFMensagem> state = new LinkedList<IFMensagem>();

	public DataBaseReceiver(JChannel channel, BancoDadosDAO dao) {
		this.channel = channel;
		this.dao = dao;
		this.channel.setReceiver(this);
	}

	@Override
	public void receive(Message msg) {
		IFMensagem mensagem = (IFMensagem) msg.getObject();
		if (mensagem.getSql() !=null){
			String status = dao.executarSQL(mensagem.getSql());
			
			if (status != Status.OK.getValue()){
				Mensagem error = new Mensagem();
				error.setStatus(status);
				try {
					this.channel.send(new Message(null, null, error));
				} catch (Exception e) {				
					e.printStackTrace();
				}
			}
			
			System.out.println(msg.getSrc() + "> Recebido ID : " + mensagem.getId()+ ": " + mensagem.getSql());
			
			synchronized (state) {
				state.add(mensagem);
			}
		}else{
		System.out.println("> Respota ID : " + mensagem.getId() + ": " +  mensagem.getStatus());
		}
		
		
	}

	@Override
	public void getState(OutputStream output) throws Exception {
		synchronized (state) {
			Util.objectToStream(state, new DataOutputStream(output));
		}
	}

	@Override
	public void setState(InputStream input) throws Exception {
		@SuppressWarnings("unchecked")
		List<IFMensagem> list = (List<IFMensagem>) Util.objectFromStream(new DataInputStream(input));
		synchronized (state) {
			state.clear();
			state.addAll(list);

			for (IFMensagem mensagem : list) {
				if (mensagem.getSql() !=null){
					dao.executarSQL(mensagem.getSql());
					System.out.println("> Carregando ID : " + mensagem.getId()+ ": " + mensagem.getSql());
				}
				System.out.println("> Carregando ID : "  + ": " +  mensagem.getStatus());
			}
		}
	
	}

	@Override
	public void viewAccepted(View new_view) {
		viewId = new_view.getViewId();
		System.out.println("> Olá mundo! Sou o novo : " + new_view);

	}

}
