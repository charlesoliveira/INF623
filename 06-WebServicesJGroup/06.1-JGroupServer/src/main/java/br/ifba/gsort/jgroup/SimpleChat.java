package br.ifba.gsort.jgroup;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.ViewId;
import org.jgroups.util.Util;

import br.ifba.gsort.dao.BancoDadosDAO;

public class SimpleChat extends ReceiverAdapter {
    JChannel channel;
    String user_name=System.getProperty("user.name", "n/a");
    final List<String> state=new LinkedList<String>();
    ArrayList<String> jdbcList = new ArrayList<String>();
    ViewId me; 
    BancoDadosDAO dao;

    public void viewAccepted(View new_view) {
    	me = new_view.getViewId();
    	dao = new BancoDadosDAO(jdbcList.get(new_view.getMembers().indexOf(new_view) != -1 ? new_view.getMembers().indexOf(new_view) : 0));
        System.out.println("** view: " + new_view);
    }
    
    public void receive(Message sql) {
    	dao.executarSQL(sql.getObject().toString());
    	
        String line=sql.getSrc() + ": " + sql.getObject();
        System.out.println(line);
        synchronized(state) {
        	state.add(line);
        }
        
    }

    public void getState(OutputStream output) throws Exception {
        synchronized(state) {
            Util.objectToStream(state, new DataOutputStream(output));
        }
    }

    @SuppressWarnings("unchecked")
    public void setState(InputStream input) throws Exception {
        List<String> list=(List<String>)Util.objectFromStream(new DataInputStream(input));
        synchronized(state) {
            state.clear();
            state.addAll(list);
            
            for(String sql:list){
            	dao.executarSQL(sql);
            }
        }
      //  System.out.println("received state (" + list.size() + " messages in chat history):");
        for(String str: list) {
            System.out.println(str);
        }
    }


    private void start() throws Exception {
    	
    	jdbcList.add("jdbc://localhost:5432/sa@12345");
    	jdbcList.add("jdbc://localhost:5433/sa@12345");
    	jdbcList.add("jdbc://localhost:5434/sa@12345");
    	jdbcList.add("jdbc://localhost:5435/sa@12345");
    	jdbcList.add("jdbc://localhost:5436/sa@12345");
        channel=new JChannel();
        channel.setReceiver(this);
        channel.connect("ChatCluster");
        channel.getState(null, 10000);
        eventLoop();
        channel.close();
    }

    private void eventLoop() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                System.out.print("> "); System.out.flush();
                String line=in.readLine().toLowerCase();
                if(line.startsWith("quit") || line.startsWith("exit")) {
                    break;
                }
                Message msg=new Message(null, null, line);
                channel.send(msg);
            }
            catch(Exception e) {
            }
        }
    }


    public static void main(String[] args) throws Exception {
        new SimpleChat().start();
    }
}
