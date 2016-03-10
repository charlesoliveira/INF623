package br.ifba.gsort.jgroup.frontend;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import br.ifba.gsort.jgroup.JCluster;
import br.ifba.gsort.jgroup.Receiver;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean(name = "cluster", destroyMethod = "destroyMe")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public JCluster configureClusterl() throws Exception {
		JCluster cluster = new JCluster();
		cluster.addReceiver();
		cluster.addReceiver();
		cluster.addReceiver();
		cluster.addReceiver();
		cluster.addReceiver();
		return cluster;
	}

}
