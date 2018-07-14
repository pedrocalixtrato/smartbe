package com.smartbe.model.bean.cadastros;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class AgendamentoValores implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "VALOR_TOTAL")
	private BigDecimal valorTotal = BigDecimal.ZERO;
	@Column(name = "VALOR_PARCIAL")
	private BigDecimal valorParcial = BigDecimal.ZERO;	
	private Integer qtdAdiantamento = 0;
	private boolean valoresSalvo = false;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public BigDecimal getValorParcial() {
		return valorParcial;
	}
	public void setValorParcial(BigDecimal valorParcial) {
		this.valorParcial = valorParcial;
	}
	public Integer getQtdAdiantamento() {
		return qtdAdiantamento;
	}
	public void setQtdAdiantamento(Integer qtdAdiantamento) {
		this.qtdAdiantamento = qtdAdiantamento;
	}
	
	public boolean isValoresSalvo() {
		return valoresSalvo;
	}
	public void setValoresSalvo(boolean valoresSalvo) {
		this.valoresSalvo = valoresSalvo;
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
		AgendamentoValores other = (AgendamentoValores) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	

}
