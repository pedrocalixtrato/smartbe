package com.smartbe.dao;

import javax.persistence.Query;

import com.smartbe.model.bean.financeiro.FinLancamentoCaixa;
import com.smartbe.model.dao.DaoGenerico;

public class FinLancamentoCaixaDao extends DaoGenerico<FinLancamentoCaixa>{
	private static final long serialVersionUID = 1L;
	
	public FinLancamentoCaixaDao() {
		super(FinLancamentoCaixa.class);
		
	}
		
	
	
	 public FinLancamentoCaixa getBean(String atributo, Integer valor) throws Exception {
	        try {
	            abrirConexao();
	            String jpql = "SELECT o FROM " + FinLancamentoCaixa.class + " o WHERE o." + atributo + " = :valor";
	            Query query = em.createQuery(jpql);
	            query.setParameter("valor", valor);
	            return (FinLancamentoCaixa) query.getSingleResult();
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            if (isAutoCommit()) {
	                fecharConexao();
	            }
	        }
	    }
	

}
