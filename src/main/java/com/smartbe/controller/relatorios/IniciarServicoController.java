package com.smartbe.controller.relatorios;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;

import com.smartbe.controller.AbstractController;
import com.smartbe.dao.AgendamentoServicoDao;
import com.smartbe.filter.FilterData;
import com.smartbe.model.bean.cadastros.Agendamento;
import com.smartbe.model.bean.cadastros.AgendamentoServico;

@ManagedBean
@ViewScoped
public class IniciarServicoController extends AbstractController<AgendamentoServico> implements Serializable {

	
	private static final long serialVersionUID = 1L;	
	
	private static AgendamentoServico agendamentoServico;
	private List<AgendamentoServico> agendamentosServicos;
	private AgendamentoServicoDao agendamentoServicoDao;
	private FilterData filtro;
	private Date dataZerada ;
	private Date dataMaxima ;

	@Override
	public Class<AgendamentoServico> getClazz() {
		return AgendamentoServico.class;
	}

	@Override
	public String getFuncaoBase() {
		return "AGENDAMENTOSERVICO";
	}
	
	@PostConstruct
	public void inicializar() {
		try {			
			organizaDatas();			
			agendamentoServicoDao = new AgendamentoServicoDao();
			agendamentosServicos = new ArrayList<AgendamentoServico>();
			filtro = new FilterData();
			filtro.setDataInicial(dataZerada);
			filtro.setDataFinal(dataMaxima);
			filtro.setStatusServico("Em aberto");
			agendamentosServicos = agendamentoServicoDao.filtrar(filtro);
			formatarData();
			super.init();
	
		}catch(Exception e) {
			e.printStackTrace();
		}
	 }
	
	public void organizaDatas() {
		 Date gc = new Date();
         gc.setHours(0);
         gc.setMinutes(0);
         gc.setSeconds(0);
         dataZerada = gc;
         
         Date gc2 = new Date();
         gc2.setHours(23);
         gc2.setMinutes(59);
         gc2.setSeconds(59);
         dataMaxima = gc2;
	}
	
	public void iniciarServico(ActionEvent evento) {
		
		try {				
			agendamentoServico.setStatusServico("Em andamento");			
			agendamentoServico = dao.merge(agendamentoServico);
			inicializar();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	public void carregarServico(ActionEvent evento) {
		agendamentoServico = new AgendamentoServico();
		agendamentoServico = (AgendamentoServico) evento.getComponent().getAttributes().get("servicoSelecionado");
	}
	
	public void formatarData() {		 
		 for (AgendamentoServico as : agendamentosServicos) {
		 SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		 fmt.format(as.getDataInicio());
		 }
	 }
	

	public List<AgendamentoServico> getAgendamentosServicos() {
		return agendamentosServicos;
	}

	public void setAgendamentosServicos(List<AgendamentoServico> agendamentosServicos) {
		this.agendamentosServicos = agendamentosServicos;
	}

	public FilterData getFiltro() {
		return filtro;
	}

	public void setFiltro(FilterData filtro) {
		this.filtro = filtro;
	}
	
	

}
