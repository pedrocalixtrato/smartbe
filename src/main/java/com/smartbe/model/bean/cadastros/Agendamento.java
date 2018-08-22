package com.smartbe.model.bean.cadastros;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Agendamento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
	private Cliente cliente;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_AGENDAMENTO_VALORES", referencedColumnName = "ID")
	private AgendamentoValores agendamentoValores;
	@Column(name="STATUS")
	private String status;	
	@Column(name = "DATA_INICIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	//esta será a data efetiva
	@Column(name = "DATA_FIM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;
	private Integer qtdAdiantamento = 0;	
	@Column(name = "OBSERVACAO")
	private String observacao;	
	@OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("servico ASC")   
    private Set<AgendamentoServico> listaAgendamentoServico;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}		
	
	
	public Set<AgendamentoServico> getListaAgendamentoServico() {
		return listaAgendamentoServico;
	}
	public void setListaAgendamentoServico(Set<AgendamentoServico> listaAgendamentoServico) {
		this.listaAgendamentoServico = listaAgendamentoServico;		
	}	
	
	
	
	
	public Integer getQtdAdiantamento() {
		return qtdAdiantamento;
	}
	public void setQtdAdiantamento(Integer qtdAdiantamento) {
		this.qtdAdiantamento = qtdAdiantamento;
	}	
	
	public AgendamentoValores getAgendamentoValores() {
		return agendamentoValores;
	}
	public void setAgendamentoValores(AgendamentoValores agendamentoValores) {
		this.agendamentoValores = agendamentoValores;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agendamento other = (Agendamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
