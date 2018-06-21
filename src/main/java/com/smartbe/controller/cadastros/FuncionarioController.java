package com.smartbe.controller.cadastros;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.smartbe.controller.AbstractController;
import com.smartbe.model.bean.cadastros.Funcionario;

@ManagedBean
@ViewScoped
public class FuncionarioController extends AbstractController<Funcionario> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public Class<Funcionario> getClazz() {		
		return Funcionario.class;
	}
	
	@Override
    public String getFuncaoBase() {
        return "FUNCIONARIO";
        }
	
}
