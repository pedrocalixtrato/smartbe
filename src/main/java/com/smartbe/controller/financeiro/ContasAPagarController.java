package com.smartbe.controller.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.smartbe.controller.AbstractController;
import com.smartbe.model.bean.financeiro.ContasAPagar;
import com.smartbe.model.bean.financeiro.FinContas;
import com.smartbe.model.bean.financeiro.FinPlanoContas;
import com.smartbe.model.dao.DaoGenerico;

@ManagedBean
@ViewScoped
public class ContasAPagarController extends AbstractController<ContasAPagar> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<ContasAPagar> getClazz() {
		return ContasAPagar.class;
	}

	@Override
	public String getFuncaoBase() {
		return "CONTASAPAGAR";
	}

	@Override
	@PostConstruct
	public void init() {
		super.init();
		finPlanoContasDao = new DaoGenerico<>(FinPlanoContas.class);
		finContasDao = new DaoGenerico<>(FinContas.class);
	}

	@Override
	public void incluir() {
		carregarContas();
		super.incluir();
	}

	private DaoGenerico<FinPlanoContas> finPlanoContasDao;
	private DaoGenerico<FinContas> finContasDao;
	List<FinContas> listaContas = new ArrayList<>();

	public List<FinPlanoContas> getListaPlanoContas(String nome) {
		List<FinPlanoContas> listaPlanoContas = new ArrayList<>();
		try {
			listaPlanoContas = finPlanoContasDao.getBeansLike("descricao", nome);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaPlanoContas;
	}

	public List<FinContas> carregarContas() {

		try {
			listaContas = finContasDao.getBeans();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaContas;

	}

	public List<FinContas> getListaContas() {
		return listaContas;
	}

	public void setListaContas(List<FinContas> listaContas) {
		this.listaContas = listaContas;
	}

}
