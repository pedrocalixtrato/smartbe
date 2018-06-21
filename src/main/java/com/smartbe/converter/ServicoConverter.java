package com.smartbe.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.smartbe.model.bean.cadastros.Servico;
import com.smartbe.model.dao.DaoGenerico;

@FacesConverter("servicoConverter")
public class ServicoConverter implements Converter{
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		try {
			Integer id = Integer.parseInt(value);
			
			DaoGenerico<Servico> servicoDao = new DaoGenerico<>(Servico.class);
			Servico servico = servicoDao.getBean(id);
			
			return servico;				
		}catch (Exception e) {
			return null;
		}			
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		try {
			Servico servico = (Servico) value;
			Integer id = servico.getId();
			
			return id.toString();
			
		}catch (Exception e) {
			return null;
		}

		
	}

}
