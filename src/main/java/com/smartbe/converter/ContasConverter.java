package com.smartbe.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.smartbe.model.bean.financeiro.FinContas;
import com.smartbe.model.dao.DaoGenerico;

@FacesConverter("contasConverter")
public class ContasConverter implements Converter{			
					

			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				
				try {
					Integer id = Integer.parseInt(value);
					
					DaoGenerico<FinContas> finContasDao = new DaoGenerico<>(FinContas.class);
					FinContas finContas = finContasDao.getBean(id);
					
					return finContas;				
				}catch (Exception e) {
					return null;
				}			
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				
				try {
					FinContas finContas = (FinContas) value;
					Integer id = finContas.getId();
					
					return id.toString();
					
				}catch (Exception e) {
					return null;
				}

				
			}



}
