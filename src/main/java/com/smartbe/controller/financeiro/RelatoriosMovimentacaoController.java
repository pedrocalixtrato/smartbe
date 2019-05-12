package com.smartbe.controller.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.hibernate.persister.entity.Loadable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import com.smartbe.controller.AbstractController;
import com.smartbe.controller.AgendamentoServicoDataModel;
import com.smartbe.controller.T2TiLazyDataModel;
import com.smartbe.controller.cadastros.AgendamentoDataModel;
import com.smartbe.dao.AgendamentoServicoDao;
import com.smartbe.filter.FilterData;
import com.smartbe.model.bean.cadastros.Agendamento;
import com.smartbe.model.bean.cadastros.AgendamentoServico;
import com.smartbe.model.bean.cadastros.Funcionario;
import com.smartbe.model.dao.DaoGenerico;

@ManagedBean
@ViewScoped
public class RelatoriosMovimentacaoController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	
	private AgendamentoServico agendamentoServico;
	private FilterData filtro;
	private List<Funcionario> funcionarios;
	private AgendamentoServicoDao agendamentoServicoDao;
	private DaoGenerico<Funcionario> funcionarioDao;
	private LazyDataModel<AgendamentoServico> model;
	
			
	 public RelatoriosMovimentacaoController() {
		model = new LazyDataModel<AgendamentoServico>() {
			private static final long serialVersionUID = 1L;
			
		@Override
		public List<AgendamentoServico> load(int first, int pageSize, String sortField, SortOrder sortOrder,
				Map<String, Object> filters) {
			
			filtro.setPrimeiroRegistro(first);
			filtro.setQuantidadeRegistro(pageSize);
			filtro.setAscendente(sortOrder.ASCENDING.equals(sortOrder));
			filtro.setPropriedadeOrdenacao(sortField);
			
			setRowCount(agendamentoServicoDao.quantidadFiltrados(filtro));
			
			return agendamentoServicoDao.filtrar(filtro);
		}
			
		};
	}		
	
	@PostConstruct		
	public void init() {
		filtro = new FilterData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		agendamentoServicoDao = new AgendamentoServicoDao();
		funcionarios = new ArrayList<Funcionario>();
		funcionarioDao = new DaoGenerico<>(Funcionario.class);		
		carregarFuncionario();
	}
	
	
	
	 public List<Funcionario> carregarFuncionario(){		 
			 
			 try {
				funcionarios = funcionarioDao.getBeans();
			} catch (Exception e) {			
				e.printStackTrace();
			}
			 
			 return funcionarios;
			 
		 }

	public AgendamentoServico getAgendamentoServico() {
		return agendamentoServico;
	}

	public void setAgendamentoServico(AgendamentoServico agendamentoServico) {
		this.agendamentoServico = agendamentoServico;
	}

	

	public FilterData getFiltro() {
		return filtro;
	}

	public void setFiltro(FilterData filtro) {
		this.filtro = filtro;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public LazyDataModel<AgendamentoServico> getModel() {
		return model;
	}

	
	
	
	
	

}
