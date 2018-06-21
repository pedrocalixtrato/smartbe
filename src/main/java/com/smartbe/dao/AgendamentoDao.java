package com.smartbe.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.smartbe.filter.FilterData;
import com.smartbe.model.bean.cadastros.Agendamento;
import com.smartbe.model.dao.DaoGenerico;

public class AgendamentoDao extends DaoGenerico<Agendamento> {

	private static final long serialVersionUID = 1L;

	public AgendamentoDao() {
		super(Agendamento.class);
	}

	@SuppressWarnings("unchecked")
	public List<Agendamento> filtrar(FilterData filtro) throws Exception {
		try {

			abrirConexao();
			Session session = em.unwrap(Session.class);
			Criteria criteria = session.createCriteria(Agendamento.class);

			if (filtro.getDataInicial() != null && filtro.getDataFinal() != null) {

				criteria.add(Restrictions.ge("dataInicio", filtro.getDataInicial()))
						.add(Restrictions.le("dataInicio", filtro.getDataFinal()));
			}
			if (filtro.getStatusServico() != null &&  !filtro.getStatusServico().trim().equals("")){
				criteria.add(Restrictions.eq("status", filtro.getStatusServico()));
			}

			return criteria.setCacheable(true).list();
		} catch (Exception e) {
			throw e;
		} finally {
			if (isAutoCommit()) {
				fecharConexao();
			}
		}
	}
	public BigDecimal somarTotal(FilterData sfiltro) throws Exception {
		try {
			abrirConexao();
			Session session = em.unwrap(Session.class);
			Criteria criteria = session.createCriteria(Agendamento.class);

			if (sfiltro.getDataInicial() == null && sfiltro.getDataFinal() == null) {

				criteria.setProjection(Projections.sum("valorTotal"));
				return (BigDecimal) criteria.uniqueResult();

			}

			criteria.setProjection(Projections.sum("valorTotal"))
					.add(Restrictions.ge("dataInicio", sfiltro.getDataInicial()))
					.add(Restrictions.le("dataInicio", sfiltro.getDataFinal()));
			if (sfiltro.getStatusServico() != null) {
				criteria.add(Restrictions.eq("status", sfiltro.getStatusServico()));
			}

			return (BigDecimal) criteria.uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			if (isAutoCommit()) {
				fecharConexao();

			}

		}
	}
}
