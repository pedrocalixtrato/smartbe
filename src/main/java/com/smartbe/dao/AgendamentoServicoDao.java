package com.smartbe.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.smartbe.filter.FilterData;
import com.smartbe.model.bean.cadastros.AgendamentoServico;
import com.smartbe.model.dao.DaoGenerico;

public class AgendamentoServicoDao extends DaoGenerico<AgendamentoServico> {
	


	private static final long serialVersionUID = 1L;

	public AgendamentoServicoDao() {
		super(AgendamentoServico.class);
	}

	@SuppressWarnings("unchecked")
	public List<AgendamentoServico> filtrar(FilterData filtro) {
		try {

			abrirConexao();
			
			Criteria criteria = criarCriteriaParaFiltro(filtro); 
			
			criteria.setFirstResult(filtro.getPrimeiroRegistro());
			criteria.setMaxResults(filtro.getQuantidadeRegistro());
			
			if(filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
				criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
			}else if(filtro.getPropriedadeOrdenacao() != null) {
				criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
			}

			

			return criteria.setCacheable(true).list();
		} catch (Exception e) {			
			e.getMessage();
			return null;
			
		} 		
	}
	
	public int quantidadFiltrados (FilterData filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(FilterData filtro) {
		try {
			abrirConexao();
		
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(AgendamentoServico.class);
		
		if (filtro.getDataInicial() != null && filtro.getDataFinal() != null) {

			criteria.add(Restrictions.ge("dataInicio", filtro.getDataInicial()))
					.add(Restrictions.le("dataInicio", filtro.getDataFinal()));
		}
		if (filtro.getCodFuncionario() != null ){
			criteria.createAlias("funcionario", "f").	
			
			add(Restrictions.eq("f.id", filtro.getCodFuncionario()));
			
		}
		
		return criteria;
		
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		}
		
	}
}
