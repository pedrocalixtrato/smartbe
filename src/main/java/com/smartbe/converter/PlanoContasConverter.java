package com.smartbe.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.smartbe.model.bean.financeiro.FinPlanoContas;
import com.smartbe.model.dao.DaoGenerico;

@FacesConverter ("planoContasConverter")
public class PlanoContasConverter implements Converter{	
		
				

		@Override
		public Object getAsObject(FacesContext context, UIComponent component, String value) {
			
			try {
				Integer id = Integer.parseInt(value);
				
				DaoGenerico<FinPlanoContas> finPlanoContasDao = new DaoGenerico<>(FinPlanoContas.class);
				FinPlanoContas finPlanoContas = finPlanoContasDao.getBean(id);
				
				return finPlanoContas;				
			}catch (Exception e) {
				return null;
			}			
		}

		@Override
		public String getAsString(FacesContext context, UIComponent component, Object value) {
			
			try {
				FinPlanoContas finPlanoContas = (FinPlanoContas) value;
				Integer id = finPlanoContas.getId();
				
				return id.toString();
				
			}catch (Exception e) {
				return null;
			}

			
		}

}
