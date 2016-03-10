package br.ifba.gsort.jgroup.frontend;

import org.jgroups.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ifba.gsort.jgroup.IFMensagem;
import br.ifba.gsort.jgroup.JCluster;
import br.ifba.gsort.jgroup.Mensagem;

@Controller
public class ClusterController {

	@Autowired
	JCluster jCluster;



	@RequestMapping("/instances/size")
	public @ResponseBody int getAllInstancesSize() {
		return jCluster.getChannel().getView().getMembers().size();
	}

	@RequestMapping("/execute")
	public @ResponseBody Boolean execute(@RequestParam(required = true) String sql) {
		IFMensagem mensagem = new Mensagem(sql);
		Message msg = new Message(null, null, mensagem);
		try {
			jCluster.getChannel().send(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
}
