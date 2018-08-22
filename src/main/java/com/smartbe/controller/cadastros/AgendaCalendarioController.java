package com.smartbe.controller.cadastros;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.smartbe.controller.AbstractController;
import com.smartbe.dao.AgendamentoServicoDao;
import com.smartbe.filter.FilterData;
import com.smartbe.model.bean.cadastros.Agendamento;
import com.smartbe.model.bean.cadastros.AgendamentoServico;
import com.smartbe.model.bean.cadastros.AgendamentoValores;
import com.smartbe.model.bean.cadastros.Cliente;
import com.smartbe.model.bean.financeiro.FinContas;
import com.smartbe.model.bean.financeiro.FinLancamentoCaixa;
import com.smartbe.model.bean.financeiro.FinPlanoContas;
import com.smartbe.model.dao.DaoGenerico;
import com.smartbe.util.FacesContextUtil;

@ManagedBean
@javax.faces.bean.ViewScoped
public class AgendaCalendarioController extends AbstractController<Agendamento> implements Serializable {
	
	@Override
	public Class<Agendamento> getClazz() {
		return Agendamento.class;
	}
	
	@Override
    public String getFuncaoBase() {
        return "AGENDAMENTO";
    }

	private static final long serialVersionUID = 1L;

	private AgendamentoServico agendamentoServico;
	private ScheduleModel eventModel;
	private List<AgendamentoServico> listaServicos;
	private String nomeCliente;	
	private Cliente cliente;
	private boolean clienteSalvo = false;
	private FinPlanoContas finPlanoContas;
	private AgendamentoServico agendamentoServicoSelecionado;
	private FinLancamentoCaixa finLancamentoCaixa;
	private DaoGenerico<AgendamentoServico> agendamentoServicoDAO;
	private DaoGenerico<Cliente> clienteDao;
	private DaoGenerico<FinPlanoContas> finPlanoContasDao;
	private DaoGenerico<FinLancamentoCaixa> finLancamentoCaixaDao;	
	private List <AgendamentoServico> agendamentosServicos;
	private FinContas finContas ;
	private FilterData filtro;	
	private AgendamentoServicoDao agendamentoServicoDao;	
	
	private int tabIndex = 0;
	private boolean abaGeral = false;
	private boolean abaServico = false;
	private boolean abaAdiantamento = false;
	private boolean abaTotais = false;
	private boolean podeIncluirServico;	
	

	
	@PostConstruct
	@Override
	public void init(){
		super.init();
		cliente = new Cliente();
		agendamentosServicos = new ArrayList<AgendamentoServico>();	
		clienteDao = new DaoGenerico<>(Cliente.class);
		filtro = new FilterData();	
		agendamentoServicoDao = new AgendamentoServicoDao();
		finLancamentoCaixaDao = new DaoGenerico<>(FinLancamentoCaixa.class);
		agendamentoServico = new AgendamentoServico();
		agendamentoServicoDAO = new DaoGenerico<>(AgendamentoServico.class);
		if (eventModel==null) {
	        eventModel = new DefaultScheduleModel();
	    }		      
		
			
		try {
			listaServicos = agendamentoServicoDAO.getBeans();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		for(AgendamentoServico ls : listaServicos ){
			DefaultScheduleEvent event = new DefaultScheduleEvent();
			event.setEndDate(ls.getDataFinal());
			event.setStartDate(ls.getDataInicio());
			event.setTitle(ls.getAgendamento().getCliente().getNome());
			event.setData(ls.getId());
			event.setDescription(String.valueOf(ls.getValor()));
			event.setAllDay(false);
			event.setEditable(true);
			
			eventModel.addEvent(event);
		}
		
		
		
	}
	
	@Override
	public void incluir() {
		super.incluir();
		agendamentoServico = new AgendamentoServico();
		finLancamentoCaixa = new FinLancamentoCaixa();
		finLancamentoCaixa.setData(new Date());
		getObjeto().setListaAgendamentoServico(new HashSet<AgendamentoServico>());
				
	}
	
	@Override
	public void salvar() {
		boolean jaSalvo = false ;
		if (getObjeto().getDataInicio() == null) {
			getObjeto().setStatus("EM ABERTO");
			getObjeto().setDataInicio(new Date());
		}
		if(finLancamentoCaixa.getValor().compareTo(BigDecimal.ZERO) != 0 ) {
			adicionarAdiantamento();
			try {
				finLancamentoCaixa = finLancamentoCaixaDao.getBean("idAdiantamento", getObjeto().getId());
			} catch (Exception e) {
				e.printStackTrace();
			}			
		
		}
		if (getObjeto().getCliente() == null && nomeCliente != null && clienteSalvo == false) {
			try {
				cliente.setNome(nomeCliente);
				getObjeto().setCliente(cliente);
				clienteSalvo = true;
				super.salvar();
				jaSalvo = true;
				cliente = clienteDao.getBean("id", getObjeto().getCliente().getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (clienteSalvo == true && getObjeto().getCliente() == null) {
			try {				
				getObjeto().setCliente(cliente);
				super.salvar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			if(jaSalvo == false ) {
			super.salvar();
			}
		}
		
		
	}	
	
	public void alterarServico() {
        agendamentoServico = agendamentoServicoSelecionado;
    }
	
	public void excluirServico() {
        if (agendamentoServicoSelecionado == null || agendamentoServicoSelecionado.getId() == null) {
            FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_INFO, "Nenhum registro selecionado!", null);
        } else {
            getObjeto().getListaAgendamentoServico().remove(agendamentoServicoSelecionado);
            subtrairTotal();
            salvar();
            FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_INFO, "Registro excluído!", null);
        }
    }
	 public void somarHorario() { 
		 
	     	GregorianCalendar gc = new GregorianCalendar();
	         gc.setTime(agendamentoServico.getDataInicio()); 
	         gc.add(Calendar.MINUTE, agendamentoServico.getServico().getTempoDuracao());	                
	         Date dataFinal = gc.getTime();
	         agendamentoServico.setDataFinal(dataFinal);
	         
	         System.out.println("este e o resultado" +gc.getTime());
			 
		 }
	 
	 public void filtrarAgendamentoServico() throws Exception{ 
		 
		 	filtro.setDataInicial(agendamentoServico.getDataInicio());		 	
		 	filtro.setDataFinal(agendamentoServico.getDataFinal());
		 	filtro.setStatusServico(agendamentoServico.getFuncionario().getNome());
			this.agendamentosServicos = agendamentoServicoDao.filtrar(filtro);			
			System.out.println("este é o retorno da pesquisa" + agendamentosServicos);
		}	

	public void incluirServico() {

		if (getObjeto().getCliente() == null && nomeCliente == null) {
			podeIncluirServico = false;
			FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_WARN,
					"Antes de incluir serviços selecione o cliente.", null);
		} else {

			podeIncluirServico = true;
			agendamentoServico = new AgendamentoServico();
			agendamentoServico.setAgendamento(getObjeto());
		}
	}
	public void salvarServico() {
        try {	            
            if (!getObjeto().getListaAgendamentoServico().contains(agendamentoServico)) {	            	
            	somarHorario();
                //filtrarAgendamentoServico();
            	if(getObjeto().getDataFim() == null) {
            		getObjeto().setDataFim(getAgendamentoServico().getDataInicio());
            	}
            	if(agendamentosServicos == null || agendamentosServicos.isEmpty() ) {	            		
	                getObjeto().getListaAgendamentoServico().add(agendamentoServico);
	                calculaTotal();
	                salvar();
	                
            	}else {
            	 FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_WARN, "Horarios indisponiveis, verifique a agenda!", "");
            	}
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro ao salvar o registro", e.getMessage());
        }
    }
	
	public void adicionarAdiantamento() {
		try {
			if(finLancamentoCaixa.getId() == null) {
			finContas = new FinContas();
			finPlanoContas = new FinPlanoContas();
			finLancamentoCaixaDao = new DaoGenerico<>(FinLancamentoCaixa.class);
			getObjeto().getAgendamentoValores().setQtdAdiantamento(getObjeto().getAgendamentoValores().getQtdAdiantamento()+ 1);
			finPlanoContas = getListaPlanoContas().get(0);
			finLancamentoCaixa.setFormaPagamento("DEPOSITO");
			if(getObjeto().getCliente()!= null) {
			finLancamentoCaixa.setClienteFornecedor(getObjeto().getCliente().getNome());
			}else {
			finLancamentoCaixa.setClienteFornecedor(nomeCliente);
			}
			finLancamentoCaixa.setAgendamento(getObjeto());
			finLancamentoCaixa.setIdAdiantamento(getObjeto().getId());			
			finLancamentoCaixa.setFinPlanoContas(finPlanoContas);
			if(finLancamentoCaixa.getDescricao() == null){
				finLancamentoCaixa.setDescricao("Adiantamento");
			}
			finLancamentoCaixa.setTipo("Credito");
			finLancamentoCaixaDao.merge(finLancamentoCaixa);
			BigDecimal valorTotal = getObjeto().getAgendamentoValores().getValorParcial().subtract(finLancamentoCaixa.getValor());
			getObjeto().getAgendamentoValores().setValorTotal(valorTotal);
			salvar("Adiantamento incluido com sucesso!");
			}else {
				finLancamentoCaixaDao = new DaoGenerico<>(FinLancamentoCaixa.class);
				finPlanoContas = new FinPlanoContas();
				getObjeto().getAgendamentoValores().setQtdAdiantamento(getObjeto().getAgendamentoValores().getQtdAdiantamento()+ 1);
				finPlanoContas = getListaPlanoContas().get(0);
				if(getObjeto().getCliente()!= null) {
					finLancamentoCaixa.setClienteFornecedor(getObjeto().getCliente().getNome());
					}else {
					finLancamentoCaixa.setClienteFornecedor(nomeCliente);
					}				
				finLancamentoCaixa.setFinPlanoContas(finPlanoContas);
				BigDecimal valorTotal = getObjeto().getAgendamentoValores().getValorParcial().subtract(finLancamentoCaixa.getValor());
				getObjeto().getAgendamentoValores().setValorTotal(valorTotal);
				finLancamentoCaixaDao.merge(finLancamentoCaixa);
				salvar("Adiantamento alterado com sucesso!");
			}
			
		}catch (Exception e) {
			FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_ERROR, "Nao foi possivel adicionar o agendamento!", null);
			e.printStackTrace();			
		}	
	}
	
	public void subtrairTotal() {
		BigDecimal novoValor = agendamentoServicoSelecionado.getAgendamento().getAgendamentoValores().getValorParcial().subtract(agendamentoServicoSelecionado.getValor());
		agendamentoServicoSelecionado.getAgendamento().getAgendamentoValores().setValorParcial(novoValor);
		calculaTotal();
		 
	 }
	 
	public void calculaTotal() {
		BigDecimal desconto = BigDecimal.ZERO;
		BigDecimal valorTotal = BigDecimal.ZERO;
		BigDecimal valorSelecionado = BigDecimal.ZERO;
		BigDecimal valorParcial = BigDecimal.ZERO;
		
		if(agendamentoServico != null) {
		valorSelecionado = agendamentoServico.getValor();
		}
		
		if(getObjeto().getAgendamentoValores() != null) {
		valorParcial = 	getObjeto().getAgendamentoValores().getValorParcial().add(valorSelecionado);	
		}else {
			valorParcial = 	valorSelecionado.add(valorParcial);
		}
		desconto = finLancamentoCaixa.getValor();		
		valorTotal = valorParcial.subtract(desconto);		
		if(getObjeto().getAgendamentoValores() == null) {
			AgendamentoValores agendamentoValores = new AgendamentoValores();
			getObjeto().setAgendamentoValores(agendamentoValores);
			getObjeto().getAgendamentoValores().setValorParcial(valorParcial);
			getObjeto().getAgendamentoValores().setValorTotal(valorTotal);
		}else {
			getObjeto().getAgendamentoValores().setValorParcial(valorParcial);
			getObjeto().getAgendamentoValores().setValorTotal(valorTotal);
		}
		
	}	
	
	public List<FinPlanoContas> getListaPlanoContas() {
		finPlanoContasDao = new DaoGenerico<>(FinPlanoContas.class);
        List<FinPlanoContas> listaPlanoContas = new ArrayList<>();
        try {
            listaPlanoContas = finPlanoContasDao.getBeansLike("descricao", "Receita");
            
        } catch (Exception e) {
             e.printStackTrace();
        }
        return listaPlanoContas;
    }
	
	public void eventoSelecionado(SelectEvent eventSelect){
		
		ScheduleEvent evento1 =  (ScheduleEvent) eventSelect.getObject();
		
		for(AgendamentoServico as : listaServicos){
			if(as.getId() ==  evento1.getData()){
			
				agendamentoServico = as;
				break;				
			}			
		}		
	}
	
	public void quandoNovo(SelectEvent selectEvent){
		
		ScheduleEvent event = new DefaultScheduleEvent("",(Date)selectEvent.getObject(), (Date)selectEvent.getObject());
		agendamentoServico = new AgendamentoServico();
		agendamentoServico.setDataInicio(new java.sql.Date(event.getStartDate().getTime()));
		agendamentoServico.setDataFinal(new java.sql.Date(event.getEndDate().getTime()));
		tabIndex = 0;
		abaGeral = false;
		abaServico = true;
		abaAdiantamento = true;
		abaTotais = true;
		incluir();
		
		
	}
	
	public void anteriorTab() {
		if (tabIndex == 1) {
			tabIndex = 0;
			abaGeral = false;
		} else {
			if (tabIndex == 2) {
				tabIndex = 1;
				abaServico = false;
			} else {
			
			if (tabIndex == 3) {
				tabIndex = 2;
				abaAdiantamento = false;
				
			}
			}
		}

	}
	
	public void proximoTab() {
		if (tabIndex == 0) {
			tabIndex = 1;
			abaGeral = true;
			abaServico = false;
		} else {
			if (tabIndex == 1) {
				tabIndex = 2;
				abaServico = true;
				abaAdiantamento = false;
			} else {
			
			if (tabIndex == 2) {
				tabIndex = 3;
				abaAdiantamento = true;
				
			}
			}
		}

	}
	
	 public void onEventMove(ScheduleEntryMoveEvent event) throws Exception {
		 for(AgendamentoServico as : listaServicos){
			 if(as.getId() == event.getScheduleEvent().getData()){
				 agendamentoServico = as;
				 try{
					 agendamentoServicoDAO.merge(agendamentoServico);
					 init();
				 }catch(RuntimeException e){
					 e.printStackTrace();
					 FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao salvar serviço", null);	
					
				 }
				 break;
			 }
		  }	       
	    }
	
	 public void onEventResize(ScheduleEntryResizeEvent event) throws Exception {
		 for(AgendamentoServico as : listaServicos){
			 if(as.getId() == event.getScheduleEvent().getData()){
				 agendamentoServico = as;
				 try{
					 agendamentoServicoDAO.merge(agendamentoServico);
					 init();
				 }catch(RuntimeException e){
					 e.printStackTrace();
					
				 }
				 break;
			 }
		  }	      
	    }
	 
	 public List<Cliente> getListaCliente(String nome) {
	        List<Cliente> listaCliente = new ArrayList<>();
	        DaoGenerico<Cliente> clienteDao = new DaoGenerico<>(Cliente.class);
	        try {
	            listaCliente = clienteDao.getBeansLike("nome", nome);
	            if(listaCliente.isEmpty()) {
	            	nomeCliente = nome;
	            }
	        } catch (Exception e) {
	             e.printStackTrace();
	        } 
	        	
	        return listaCliente;
	    }	 
	 


	public ScheduleModel getEventModel() {if (eventModel==null) {
        eventModel = new DefaultScheduleModel();
    }
		return eventModel;
	}


	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public AgendamentoServico getAgendamentoServico() {
		return agendamentoServico;
	}

	public void setAgendamentoServico(AgendamentoServico agendamentoServico) {
		this.agendamentoServico = agendamentoServico;
	}

	public List<AgendamentoServico> getListaServicos() {
		return listaServicos;
	}

	public void setListaServicos(List<AgendamentoServico> listaServicos) {
		this.listaServicos = listaServicos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public boolean isAbaGeral() {
		return abaGeral;
	}

	public void setAbaGeral(boolean abaGeral) {
		this.abaGeral = abaGeral;
	}

	public boolean isAbaServico() {
		return abaServico;
	}

	public void setAbaServico(boolean abaServico) {
		this.abaServico = abaServico;
	}

	public boolean isAbaAdiantamento() {
		return abaAdiantamento;
	}

	public void setAbaAdiantamento(boolean abaAdiantamento) {
		this.abaAdiantamento = abaAdiantamento;
	}

	public boolean isAbaTotais() {
		return abaTotais;
	}

	public void setAbaTotais(boolean abaTotais) {
		this.abaTotais = abaTotais;
	}

	public AgendamentoServico getAgendamentoServicoSelecionado() {
		return agendamentoServicoSelecionado;
	}

	public void setAgendamentoServicoSelecionado(AgendamentoServico agendamentoServicoSelecionado) {
		this.agendamentoServicoSelecionado = agendamentoServicoSelecionado;
	}

	public boolean isPodeIncluirServico() {
		return podeIncluirServico;
	}

	public void setPodeIncluirServico(boolean podeIncluirServico) {
		this.podeIncluirServico = podeIncluirServico;
	}

	
 
	
	

}
