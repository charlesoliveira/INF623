package br.ifba.gsort.jgroup;

import java.io.Serializable;

public interface IFMensagem extends Serializable {

	long getId();

	String getSql();

	String getStatus();

	void setStatus(String status);

}
