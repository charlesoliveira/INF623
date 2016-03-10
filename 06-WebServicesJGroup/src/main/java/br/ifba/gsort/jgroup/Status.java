package br.ifba.gsort.jgroup;

public enum Status {
	OK("OK"),ERRO("ERRO");
	String value;
	Status(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
