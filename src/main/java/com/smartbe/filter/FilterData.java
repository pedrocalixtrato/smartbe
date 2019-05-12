package com.smartbe.filter;

import java.util.Date;

public class FilterData {
	
	private Date dataInicial;
	private Date dataFinal;
	private int primeiroRegistro;
	private int quantidadeRegistro;
	private boolean ascendente;
	private String propriedadeOrdenacao;
	
	private String statusServico;
	private String nomeFuncionario;
	private Integer codFuncionario;
	
	
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	public String getStatusServico() {
		return statusServico;
	}
	public void setStatusServico(String statusServico) {
		this.statusServico = statusServico;
	}
	public String getNomeFuncionario() {
		return nomeFuncionario;
	}
	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}
	public int getPrimeiroRegistro() {
		return primeiroRegistro;
	}
	public void setPrimeiroRegistro(int primeiroRegistro) {
		this.primeiroRegistro = primeiroRegistro;
	}
	public int getQuantidadeRegistro() {
		return quantidadeRegistro;
	}
	public void setQuantidadeRegistro(int quantidadeRegistro) {
		this.quantidadeRegistro = quantidadeRegistro;
	}
	public boolean isAscendente() {
		return ascendente;
	}
	public void setAscendente(boolean ascendente) {
		this.ascendente = ascendente;
	}
	public String getPropriedadeOrdenacao() {
		return propriedadeOrdenacao;
	}
	public void setPropriedadeOrdenacao(String propriedadeOrdenacao) {
		this.propriedadeOrdenacao = propriedadeOrdenacao;
	}
	public Integer getCodFuncionario() {
		return codFuncionario;
	}
	public void setCodFuncionario(Integer codFuncionario) {
		this.codFuncionario = codFuncionario;
	}
	
	
	
	

}
