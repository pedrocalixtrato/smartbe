package com.smartbe.model.bean.financeiro;

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

import com.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty.Type;
import com.smartbe.model.bean.cadastros.Agendamento;
import com.smartbe.model.bean.cadastros.Cliente;

@Entity
@Table(name = "fin_lancamento_caixa")
public class FinLancamentoCaixa {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
	private Integer id;
	private Integer idAdiantamento;
	@Column(name = "TIPO")
	private String tipo;
	@Column(name = "DATA")
	@Temporal(TemporalType.DATE)
	private Date data;
	@ManyToOne
	private Cliente cliente;
	@Column(name = "CLIENTE_FORNECEDOR")
	private String clienteFornecedor;	
	@Column(name = "FORMA_PAGAMENTO")
	private String formaPagamento;
	@ManyToOne
	private Agendamento agendamento;	
	@JoinColumn(name = "ID_PLANO_CONTAS")	
    @ManyToOne(optional = false)
	private FinPlanoContas finPlanoContas;
	@ManyToOne	
	private FinContas finContas;
	@Column(name = "VALOR")
    private BigDecimal valor = BigDecimal.ZERO;
	@Column(name = "DESCRICAO")
	private String descricao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getClienteFornecedor() {
		return clienteFornecedor;
	}
	public void setClienteFornecedor(String clienteFornecedor) {
		this.clienteFornecedor = clienteFornecedor;
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
	public String toString() {
		return "FinLancamentoCaixa [id=" + id + "]";
	}
	
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public Agendamento getAgendamento() {
		return agendamento;
	}
	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}
	
	
	public Integer getIdAdiantamento() {
		return idAdiantamento;
	}
	public void setIdAdiantamento(Integer idAdiantamento) {
		this.idAdiantamento = idAdiantamento;
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
		FinLancamentoCaixa other = (FinLancamentoCaixa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	

}
