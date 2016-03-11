package br.ifba.gsort.jgroup.frontend;

import java.util.List;

import org.jgroups.Address;
import org.jgroups.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;

import br.ifba.gsort.jgroup.IFMensagem;
import br.ifba.gsort.jgroup.JCluster;
import br.ifba.gsort.jgroup.Mensagem;
import br.ifba.gsort.jgroup.Status;

@Controller
public class ClusterController {

	@Autowired
	JCluster jCluster;

	@RequestMapping("/instances")
	public @ResponseBody List<Address> getAllInstances() {
		return jCluster.getChannel().getView().getMembers();
	}
	@RequestMapping("/instances/new")
	public @ResponseBody String[] execute() {

		try {
			jCluster.addDataBaseReceiver();
		} catch (Exception e) {
			return new String[]{e.getMessage()};
		}
		return new String[]{Status.OK.getValue()};
		

	}
	@RequestMapping("/instances/stop/")
	public @ResponseBody String[] executeStop(@RequestParam(required = true) int id) {

		try {
			jCluster.stopDataBaseReceiver(id);
		} catch (Exception e) {
			return new String[]{e.getMessage()};
		}
		return new String[]{Status.OK.getValue()};
	}

	@RequestMapping("/execute")
	public @ResponseBody String[] execute(@RequestParam(required = true) String sql) {
		IFMensagem mensagem = new Mensagem(sql);
		Message msg = new Message(null, null, mensagem);
		try {
			jCluster.getChannel().send(msg);
		} catch (Exception e) {
			return new String[]{e.getMessage()};
		}
		return new String[]{Status.OK.getValue()};

	}
}
