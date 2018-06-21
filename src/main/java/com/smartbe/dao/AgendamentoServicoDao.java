package com.smartbe.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
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
	public List<AgendamentoServico> filtrar(FilterData filtro) throws Exception {
		try {

			abrirConexao();
			Session session = em.unwrap(Session.class);
			Criteria criteria = session.createCriteria(AgendamentoServico.class);

			if (filtro.getDataInicial() != null && filtro.getDataFinal() != null) {

				criteria.add(Restrictions.ge("dataInicio", filtro.getDataInicial()))
						.add(Restrictions.le("dataInicio", filtro.getDataFinal()));
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
	

}
