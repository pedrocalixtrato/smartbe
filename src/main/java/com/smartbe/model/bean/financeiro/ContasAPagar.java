package com.smartbe.model.bean.financeiro;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
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
@Table(name = "fin_contas_pagar")
public class ContasAPagar {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
	private Integer id;
	@Column(name = "STATUS_PAGAR")
	private String statusPagar;
	@Column(name = "DATA_LANCAMENTO")
	@Temporal(TemporalType.DATE)
	private Date dataLancamento;
	@Column(name = "DATA_VENCIMENTO")
	@Temporal(TemporalType.DATE)
	private Date dataVencimeto;
	@Column(name = "DATA_BAIXA")
	@Temporal(TemporalType.DATE)
	private Date dataBaixa;
	@Column(name = "FORNECEDOR")
	private String fornecedor;	
	@JoinColumn(name = "ID_PLANO_CONTAS")	
    @ManyToOne(optional = false)
	private FinPlanoContas finPlanoContas;
	@ManyToOne	
	private FinContas finContas;
	@Column(name = "VALOR")
    private BigDecimal valor;
	@Column(name = "DESCRICAO")
	private String descricao;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatusPagar() {
		return statusPagar;
	}
	public void setStatusPagar(String statusPagar) {
		this.statusPagar = statusPagar;
	}
	public Date getDataLancamento() {
		return dataLancamento;
	}
	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	public Date getDataVencimeto() {
		return dataVencimeto;
	}
	public void setDataVencimeto(Date dataVencimeto) {
		this.dataVencimeto = dataVencimeto;
	}
	public Date getDataBaixa() {
		return dataBaixa;
	}
	public void setDataBaixa(Date dataBaixa) {
		this.dataBaixa = dataBaixa;
	}
	public String getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	public FinPlanoContas getFinPlanoContas() {
		return finPlanoContas;
	}
	public void setFinPlanoContas(FinPlanoContas finPlanoContas) {
		this.finPlanoContas = finPlanoContas;
	}
	public FinContas getFinContas() {
		return finContas;
	}
	public void setFinContas(FinContas finContas) {
		this.finContas = finContas;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		ContasAPagar other = (ContasAPagar) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
