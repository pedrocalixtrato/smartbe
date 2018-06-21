package com.smartbe.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.smartbe.model.bean.cadastros.Funcionario;
import com.smartbe.model.dao.DaoGenerico;
@FacesConverter("funcionarioConverter")
public class FuncionarioConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		try {
			Integer id = Integer.parseInt(value);
			
			DaoGenerico<Funcionario> funcionarioDao = new DaoGenerico<>(Funcionario.class);
			Funcionario funcionario = funcionarioDao.getBean(id);
			
			return funcionario;				
		}catch (Exception e) {
			return null;
		}			
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		try {
			Funcionario funcionario = (Funcionario) value;
			Integer id = funcionario.getId();
			
			return id.toString();
			
		}catch (Exception e) {
			return null;
		}

		
	}

}
