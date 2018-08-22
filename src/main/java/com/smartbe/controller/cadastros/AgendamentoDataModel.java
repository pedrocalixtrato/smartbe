package com.smartbe.controller.cadastros;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.el.ELResolver;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.SortOrder;

import com.amazonaws.services.autoscaling.model.Filter;
import com.smartbe.controller.T2TiLazyDataModel;
import com.smartbe.dao.AgendamentoDao;
import com.smartbe.filter.FilterData;
import com.smartbe.model.bean.cadastros.Agendamento;
import com.smartbe.model.bean.cadastros.Usuario;

public class AgendamentoDataModel extends T2TiLazyDataModel<Agendamento> {
	
	private static final long serialVersionUID = 1L;

    @Override
    public List<Agendamento> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {        	
            String globalFilter = (String) filters.get("globalFilter");	           

            if (globalFilter != null) {
                filters.clear();

                filters.put("observacao", globalFilter);         

            
                try {
                                                        	
                    	FilterData filtro = new FilterData();
                    	AgendamentoDao agendamentoDao = new AgendamentoDao();
                    	FacesContext context = FacesContext.getCurrentInstance();    
                    	ELResolver resolver = context.getApplication().getELResolver();
                    	AgendamentoController agendamentoController = (AgendamentoController) resolver.getValue(context.getELContext(), null, "agendamentoController");
                    	
                    	filtro = agendamentoController.getFiltro();
                    	
                    	filtro.setDataInicial(agendamentoController.getFiltro().getDataInicial());
                    	filtro.setDataFinal(agendamentoController.getFiltro().getDataFinal());
                    	List<Agendamento> beans = agendamentoDao.filtrar(filtro);
                    	
                    	return beans;
                    
                    
                    
                } catch (Exception e) {
                	
                }
            }
            
            List<Agendamento> beans = getDao().getBeans(first, pageSize, sortField, sortOrder, filters, "OR");
            Long totalRegistros = getDao().getTotalRegistros(filters, "OR");
            this.setRowCount(totalRegistros.intValue());
            return beans;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    

}
