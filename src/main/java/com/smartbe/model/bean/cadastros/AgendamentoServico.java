package com.smartbe.model.bean.cadastros;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "AGENDAMENTO_SERVICO")
public class AgendamentoServico implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "ID")
	    private Integer id;	    
		@ManyToOne
		private Servico servico;
		@Column(name = "DATA_INICIO")
		@Temporal(TemporalType.TIMESTAMP)
		private Date dataInicio;
		@Column(name = "DATA_FINAL")
		@Temporal(TemporalType.TIMESTAMP)
		private Date dataFinal;
		@ManyToOne
		private Funcionario funcionario;		
		@Column(name = "VALOR")
	    private BigDecimal valor;		
	    @JoinColumn(name = "ID_AGENDAMENTO", referencedColumnName = "ID")
	    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
	    private Agendamento agendamento;
	    @Column(name="STATUS_SERVICO")
	    private String statusServico;
	    
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Servico getServico() {
			return servico;
		}
		public void setServico(Servico servico) {
			this.servico = servico;
		}		
		
		public Date getDataInicio() {
			return dataInicio;
		}
		public void setDataInicio(Date dataInicio) {
			this.dataInicio = dataInicio;
		}
		public Funcionario getFuncionario() {
			return funcionario;
		}
		public void setFuncionario(Funcionario funcionario) {
			this.funcionario = funcionario;
		}
		public BigDecimal getValor() {
			return valor;
		}
		public void setValor(BigDecimal valor) {
			this.valor = valor;
		}
		public Agendamento getAgendamento() {
			return agendamento;
		}
		public void setAgendamento(Agendamento agendamento) {
			this.agendamento = agendamento;
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
			AgendamentoServico other = (AgendamentoServico) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
	    
	    
	    
	
}
