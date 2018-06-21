package com.smartbe.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.smartbe.model.bean.cadastros.Cliente;
import com.smartbe.model.dao.DaoGenerico;
@FacesConverter("clienteConverter")
public class ClienteConverter implements Converter {
					

				@Override
				public Object getAsObject(FacesContext context, UIComponent component, String value) {
					
					try {
						Integer id = Integer.parseInt(value);
						
						DaoGenerico<Cliente> clienteDao = new DaoGenerico<>(Cliente.class);
						Cliente cliente = clienteDao.getBean(id);
						
						return cliente;				
					}catch (Exception e) {
						return null;
					}			
				}

				@Override
				public String getAsString(FacesContext context, UIComponent component, Object value) {
					
					try {
						Cliente cliente = (Cliente) value;
						Integer id = cliente.getId();
						
						return id.toString();
						
					}catch (Exception e) {
						return null;
					}

					
				}

	}

