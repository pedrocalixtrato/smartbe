package com.smartbe.model.bean.cadastros;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SERVICO_FPM")
public class Servico implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "DESCRICAO")	
	private String descricao;
	@Column(name = "VALOR")
	private BigDecimal valor;
	@Column(name = "VALOR_CUSTO")
	private BigDecimal valor_custo;	
	@Column(name = "TEMPO_DURACAO")
	private Integer tempoDuracao;
	private Boolean servicoUnico;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Integer getTempoDuracao() {
		return tempoDuracao;
	}
	public void setTempoDuracao(Integer tempoDuracao) {
		this.tempoDuracao = tempoDuracao;
		
	}					
	
	public BigDecimal getValor_custo() {
		return valor_custo;
	}
	public void setValor_custo(BigDecimal valor_custo) {
		this.valor_custo = valor_custo;
	}	
	
	public Boolean getServicoUnico() {
		return servicoUnico;
	}
	public void setServicoUnico(Boolean servicoUnico) {
		this.servicoUnico = servicoUnico;
	}
	@Override
	public String toString() {
		return "Servico [id=" + id + "]";
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
		Servico other = (Servico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
