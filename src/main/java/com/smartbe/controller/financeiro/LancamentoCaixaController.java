package com.smartbe.controller.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.smartbe.controller.AbstractController;
import com.smartbe.model.bean.financeiro.FinContas;
import com.smartbe.model.bean.financeiro.FinLancamentoCaixa;
import com.smartbe.model.bean.financeiro.FinPlanoContas;
import com.smartbe.model.dao.DaoGenerico;

@ManagedBean
@ViewScoped
public class LancamentoCaixaController extends AbstractController<FinLancamentoCaixa> implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private DaoGenerico<FinPlanoContas> finPlanoContasDao;
	private DaoGenerico<FinContas> finContasDao;
	private FinLancamentoCaixa finLancamentoCaixa;
	List<FinContas> listaContas = new ArrayList<>();
	
	
	
	@Override
	public Class<FinLancamentoCaixa> getClazz() {		
		return FinLancamentoCaixa.class;
	}
	
	@Override
	public String getFuncaoBase() {		
		return "FIN_LANCAMENTO_CAIXA";
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
	@Override
	public void alterar() {
	   carregarContas();
	   super.alterar();
	}
	
	@Override
	public void salvar() {						
		super.salvar();		
	}
	
	
	 public List<FinPlanoContas> getListaPlanoContas(String nome) {
	        List<FinPlanoContas> listaPlanoContas = new ArrayList<>();
	        try {
	            listaPlanoContas = finPlanoContasDao.getBeansLike("descricao", nome);
	            
	        } catch (Exception e) {
	             e.printStackTrace();
	        }
	        return listaPlanoContas;
	    }
	 
	 public List<FinContas> carregarContas(){		 
		 
		 try {
			listaContas = finContasDao.getBeans();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		 
		 return listaContas;
		 
	 }
	 	

	public FinLancamentoCaixa getFinLancamentoCaixa() {
		return finLancamentoCaixa;
	}

	public void setFinLancamentoCaixa(FinLancamentoCaixa finLancamentoCaixa) {
		this.finLancamentoCaixa = finLancamentoCaixa;
	}

	public List<FinContas> getListaContas() {
		return listaContas;
	}

	public void setListaContas(List<FinContas> listaContas) {
		this.listaContas = listaContas;
	}

	
	
	

	
	 
	 

}
