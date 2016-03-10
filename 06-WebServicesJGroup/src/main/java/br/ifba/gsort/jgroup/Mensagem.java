package br.ifba.gsort.jgroup;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;

public class Mensagem implements IFMensagem{
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 4774523228484392338L;
	private final long id;
    private String sql;
    private String status = Status.OK.value;

    @Autowired
    AtomicLong counter;

    public Mensagem(String sql) {
    	if (counter == null) {
    		counter = new AtomicLong();
    		this.id = counter.getAndIncrement()  + 10000;
    	}else{
    		this.id = counter.getAndIncrement();
    	}
        this.sql = sql;
    }
    
    
    public Mensagem() {
    	if (counter == null) {
    		counter = new AtomicLong();
    		this.id = counter.getAndIncrement()  + 10000;
    	}else{
    		this.id = counter.getAndIncrement();
    	}
    }


    public long getId() {
        return id;
    }

	public String getSql() {
		return sql;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status  =status;
		
	}
}
