package com.smartbe.controller.financeiro;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.smartbe.controller.AbstractController;
import com.smartbe.model.bean.financeiro.FinPlanoContas;

@ManagedBean
@ViewScoped
public class PlanoContasController extends AbstractController<FinPlanoContas> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<FinPlanoContas> getClazz() {
		return FinPlanoContas.class;
	}

	@Override
	public String getFuncaoBase() {
		return "FINPLANOCONTAS";
	}

}
