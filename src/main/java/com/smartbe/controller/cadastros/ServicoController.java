package com.smartbe.controller.cadastros;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.smartbe.controller.AbstractController;
import com.smartbe.model.bean.cadastros.Servico;


@ManagedBean
@ViewScoped
public class ServicoController extends AbstractController<Servico> implements Serializable {
	
	 private static final long serialVersionUID = 1L;

	    @Override
	    public Class<Servico> getClazz() {
	        return Servico.class;
	    }

	    @Override
	    public String getFuncaoBase() {
	        return "SERVICO";
	    }

	    @Override
	    public void incluir() {
	        super.incluir();	        
	        
	    }

}
