package com.smartbe.controller.financeiro;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.smartbe.controller.AbstractController;
import com.smartbe.model.bean.financeiro.FinContas;

@ManagedBean
@ViewScoped
public class ContasController extends AbstractController<FinContas> implements Serializable{
	
	private static final long serialVersionUID = 1L;

    @Override
    public Class<FinContas> getClazz() {
        return FinContas.class;
    }

    @Override
    public String getFuncaoBase() {
        return "FINCONTAS";
    }
    
    

}
