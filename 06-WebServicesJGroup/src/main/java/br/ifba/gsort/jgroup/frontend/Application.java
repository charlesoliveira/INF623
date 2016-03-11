package br.ifba.gsort.jgroup.frontend;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import br.ifba.gsort.jgroup.JCluster;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean(name = "cluster", destroyMethod = "stop")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public JCluster configureClusterl() throws Exception {
		JCluster cluster = new JCluster();
		cluster.start();
		return cluster;
	}
	
	@Bean(name = "counter")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public AtomicLong getCounter() throws Exception {
		return new AtomicLong();
	}
	


}
