package com.smartbe.controller;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.smartbe.dao.AgendamentoServicoDao;
import com.smartbe.filter.FilterData;
import com.smartbe.model.bean.cadastros.AgendamentoServico;

public class AgendamentoServicoDataModel extends T2TiLazyDataModel<AgendamentoServico> {

	private static final long serialVersionUID = 1L;
	private FilterData filtro = new FilterData();
	private AgendamentoServicoDao agendamentoServicoDao = new AgendamentoServicoDao();

//	@Override
//    public List<AgendamentoServico> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
//    	try {
//    	filtro.setPrimeiroRegistro(first);
//    	filtro.setQuantidadeRegistro(pageSize);
//    	filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
//    	filtro.setPropriedadeOrdenacao(sortField);
//    	
//    
//    	
//    	return agendamentoServicoDao.filtrar(filtro);
//    	}catch(Exception e) {
//    		e.printStackTrace();
//    		return null;
//    	}
//    }

}
