package com.smartbe.controller.cadastros;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.smartbe.controller.AbstractController;
import com.smartbe.model.bean.cadastros.Cliente;

@ManagedBean
@ViewScoped
public class ClienteController extends AbstractController<Cliente> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public Class<Cliente> getClazz() {
		return Cliente.class;
	}
	
	@Override
    public String getFuncaoBase() {
        return "CLIENTE";
    }

}
