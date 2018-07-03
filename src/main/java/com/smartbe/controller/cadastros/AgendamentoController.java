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
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.smartbe.controller.AbstractController;
import com.smartbe.dao.AgendamentoDao;
import com.smartbe.dao.AgendamentoServicoDao;
import com.smartbe.filter.FilterData;
import com.smartbe.model.bean.cadastros.Agendamento;
import com.smartbe.model.bean.cadastros.AgendamentoServico;
import com.smartbe.model.bean.cadastros.Cliente;
import com.smartbe.model.bean.cadastros.Funcionario;
import com.smartbe.model.bean.cadastros.Servico;
import com.smartbe.model.bean.financeiro.FinContas;
import com.smartbe.model.bean.financeiro.FinLancamentoCaixa;
import com.smartbe.model.bean.financeiro.FinPlanoContas;
import com.smartbe.model.dao.DaoGenerico;
import com.smartbe.util.FacesContextUtil;

@ManagedBean
@ViewScoped
public class AgendamentoController extends AbstractController<Agendamento> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private DaoGenerico<Cliente> clienteDao;
	private DaoGenerico<Servico> servicoDao;
	private DaoGenerico<Funcionario> funcionarioDao;
	private DaoGenerico<FinLancamentoCaixa> finLancamentoCaixaDao;
	private DaoGenerico<FinPlanoContas> finPlanoContasDao;
	private DaoGenerico<FinContas> finContasDao;
	private AgendamentoDao agendamentoDao;
	private AgendamentoServicoDao agendamentoServicoDao;
	private BigDecimal somarTotal;
	private Agendamento agendamento;
	private AgendamentoServico agendamentoServico;
	private AgendamentoServico agendamentoServicoSelecionado;
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private FinLancamentoCaixa finLancamentoCaixa;
	private FinContas finContas ;
	private FinPlanoContas finPlanoContas;
	private boolean podeIncluirServico;
	
	private FilterData filtro;	
	private List<Agendamento> agendamentos;
	private List <AgendamentoServico> agendamentosServicos;
	
	@Override
	public Class<Agendamento> getClazz() {
		return Agendamento.class;
	}
	
	@Override
    public String getFuncaoBase() {
        return "AGENDAMENTO";
    }
	
	@PostConstruct
	@Override
	public void init() {		
		clienteDao = new DaoGenerico<>(Cliente.class);
		servicoDao = new DaoGenerico<>(Servico.class);
		funcionarioDao = new DaoGenerico<>(Funcionario.class);
		agendamentoDao = new AgendamentoDao();	
		agendamentoServicoDao = new AgendamentoServicoDao();
		filtro = new FilterData();		
		agendamentos = new ArrayList<Agendamento>();
		finLancamentoCaixa = new FinLancamentoCaixa();
		agendamentosServicos = new ArrayList<AgendamentoServico>();
		
		super.init();
	}
	
	@Override
	public void incluir() {
		super.incluir();
		agendamentoServico = new AgendamentoServico();
		getObjeto().setListaAgendamentoServico(new HashSet<AgendamentoServico>());		
	
				
	}
	
	@Override
	public void salvar() {	
		if(getObjeto().getId() == null) {
		
			getObjeto().setStatus("EM ABERTO");
			getObjeto().setDataInicio(new Date());
		
		}
		super.salvar();		
	}
	@Override
	public void alterar() {
		DaoGenerico<FinLancamentoCaixa> finLancamentoCaixaDao = new DaoGenerico<>(FinLancamentoCaixa.class);
		
		try {
			finLancamentoCaixa = new FinLancamentoCaixa();
			finLancamentoCaixa = finLancamentoCaixaDao.getBean("agendamento", getObjeto().getId());
		} catch (Exception e) {			
			e.printStackTrace();
		}		
		super.alterar();
	}
	public void adicionarAdiantamento() {
		try {			
			finContas = new FinContas();
			finPlanoContas = new FinPlanoContas();
			finLancamentoCaixaDao = new DaoGenerico<>(FinLancamentoCaixa.class);
			getObjeto().setQtdAdiantamento(getObjeto().getQtdAdiantamento()+ 1);
			finPlanoContas = getListaPlanoContas().get(0);
			finLancamentoCaixa.setFormaPagamento("DEPOSITO");
			finLancamentoCaixa.setClienteFornecedor(getObjeto().getCliente().getNome());
			finLancamentoCaixa.setAgendamento(getObjeto());
			finLancamentoCaixa.setData(new Date());
			finLancamentoCaixa.setFinPlanoContas(finPlanoContas);
			finLancamentoCaixa.setTipo("Credito");			
			finLancamentoCaixaDao.merge(finLancamentoCaixa);
			FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_INFO, "Adiantamento incluido com sucesso!", null);
			
		}catch (Exception e) {
			FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_ERROR, "Nao foi possivel adicionar o agendamento!", null);
			e.printStackTrace();
			
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
            FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_INFO, "Registro excluído!", null);
        }
    }
	
	
	 public void incluirServico() {
		 
		 if (getObjeto().getCliente() == null) {
	            podeIncluirServico = false;
	            FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_WARN, "Antes de incluir serviços selecione o cliente.", null);	      
	        } else {
	        	salvar();
	        	podeIncluirServico = true;
	        	agendamento = new Agendamento();
		        agendamentoServico = new AgendamentoServico();
		        agendamentoServico.setAgendamento(getObjeto());		            
	           
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
	 
	 public void salvarServico() {
	        try {	            
	            if (!getObjeto().getListaAgendamentoServico().contains(agendamentoServico)) {	            	
	            	somarHorario();
	            	filtrarAgendamentoServico();
	            	if(getObjeto().getDataFim() == null) {
	            		getObjeto().setDataFim(getAgendamentoServico().getDataInicio());
	            	}
	            	if(agendamentosServicos == null || agendamentosServicos.isEmpty() ) {	            		
		                getObjeto().getListaAgendamentoServico().add(agendamentoServico);
		                atualizarTotal();
		                salvar("Servico salvo com sucesso!");
		                
	            	}else {
	            	 FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_WARN, "Horarios indisponiveis, verifique a agenda!", "");
	            	}
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro ao salvar o registro", e.getMessage());
	        }
	    }
	 
	 
	 
	 public void filtrarAgendamentoServico() throws Exception{ 
		 
		 	filtro.setDataInicial(getAgendamentoServico().getDataInicio());		 	
		 	filtro.setDataFinal(agendamentoServico.getDataFinal());
		 	filtro.setStatusServico(agendamentoServico.getFuncionario().getNome());
			this.agendamentosServicos = agendamentoServicoDao.filtrar(filtro);			
			System.out.println("este é o retorno da pesquisa" + agendamentosServicos);
		}
	 
	public void atualizarTotal() {
		BigDecimal valorSelecionado = BigDecimal.ZERO;
		BigDecimal valorAtual = BigDecimal.ZERO;
		valorSelecionado = agendamentoServico.getValor();
		valorAtual = getObjeto().getValorTotal();
		valorTotal = valorSelecionado.add(valorAtual); 
		getObjeto().setValorTotal(valorTotal);
	
		
	}
	
	public void iniciarServico(ActionEvent evento) {		
		try {
			agendamento = new Agendamento();
			agendamento = (Agendamento) evento.getComponent().getAttributes().get("agendamentoSelecionado");		
			agendamento.setStatus("INICIADO");			
			agendamento = dao.merge(agendamento);
		} catch (Exception e) {			
			e.printStackTrace();
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
	
	public List<FinContas> getListaContas() {
		finContasDao = new DaoGenerico<>(FinContas.class);
        List<FinContas> listaContas = new ArrayList<>();
        try {
            listaContas = finContasDao.getBeansLike("nomeBanco", "EMPRESA");
            
        } catch (Exception e) {
             e.printStackTrace();
        }
        return listaContas;
    }
 
	
	public void finalizarPagamentoDinheiro() {				
		try {
			finContas = new FinContas();
			finPlanoContas = new FinPlanoContas();
			finLancamentoCaixa = new FinLancamentoCaixa();
			finLancamentoCaixaDao = new DaoGenerico<>(FinLancamentoCaixa.class);
			finPlanoContas = getListaPlanoContas().get(0);
			finContas = getListaContas().get(0);
			finLancamentoCaixa.setFormaPagamento("DINHEIRO");
			finLancamentoCaixa.setValor(agendamento.getValorTotal());
			finLancamentoCaixa.setClienteFornecedor(agendamento.getCliente().getNome());
			finLancamentoCaixa.setAgendamento(agendamento);
			finLancamentoCaixa.setData(new Date());
			finLancamentoCaixa.setDescricao("Recebimento serviço");
			finLancamentoCaixa.setFinPlanoContas(finPlanoContas);
			finLancamentoCaixa.setFinContas(finContas);
			finLancamentoCaixa.setTipo("Credito");			
			finLancamentoCaixaDao.merge(finLancamentoCaixa);
			FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_INFO, "Finalizado com sucesso!", null);			
			
		} catch (Exception e) {
			FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_ERROR, "Nao foi possivel finalizar!", null);
			e.printStackTrace();
		}
		
	}
	
	public void finalizarServico (ActionEvent evento) {
		
		try {
			agendamento = new Agendamento();
			agendamento = (Agendamento) evento.getComponent().getAttributes().get("agendamentoFinalizar");		
			agendamento.setStatus("FINALIZADO");			
			agendamento = dao.merge(agendamento);
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}
	
	public void filtrar() throws Exception{
		
		GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(filtro.getDataFinal()); 
        gc.add(Calendar.HOUR, 23 );
        gc.add(Calendar.MINUTE, 59);	                
        Date dataFinal = gc.getTime();       
		
		filtro.setDataFinal(dataFinal);
		this.agendamentos = agendamentoDao.filtrar(filtro);
		this.somarTotal = agendamentoDao.somarTotal(filtro);
		
		
	}
	
	public List<Cliente> getListaCliente(String nome) {
        List<Cliente> listaCliente = new ArrayList<>();
        try {
            listaCliente = clienteDao.getBeansLike("nome", nome);
            
        } catch (Exception e) {
             e.printStackTrace();
        }
        return listaCliente;
    }
	
	public List<Servico> getListaServico(String nome) {
        List<Servico> listaServico = new ArrayList<>();
        try {
            listaServico = servicoDao.getBeansLike("descricao", nome);
            
        } catch (Exception e) {
             e.printStackTrace();
        }
        return listaServico;
    }
	
	public List<Funcionario> getListaFuncionario(String nome) {
        List<Funcionario> listaFuncionario = new ArrayList<>();
        try {
            listaFuncionario = funcionarioDao.getBeansLike("nome", nome);
            
        } catch (Exception e) {
             e.printStackTrace();
        }
        return listaFuncionario;
    }

	public FilterData getFiltro() {if(filtro == null) {
		filtro = new FilterData();
	}
		return filtro;
	}

	public void setFiltro(FilterData filtro) {
		this.filtro = filtro;
	}

	public List<Agendamento> getAgendamentos() {if(agendamentos == null) {
		agendamentos = new ArrayList<Agendamento>();
	}
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

	public BigDecimal getSomarTotal() {
		return somarTotal;
	}

	public void setSomarTotal(BigDecimal somarTotal) {
		this.somarTotal = somarTotal;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public AgendamentoServico getAgendamentoServicoSelecionado() {
		return agendamentoServicoSelecionado;
	}

	public void setAgendamentoServicoSelecionado(AgendamentoServico agendamentoServicoSelecionado) {
		this.agendamentoServicoSelecionado = agendamentoServicoSelecionado;
	}

	public AgendamentoServico getAgendamentoServico() {
		return agendamentoServico;
	}

	public void setAgendamentoServico(AgendamentoServico agendamentoServico) {
		this.agendamentoServico = agendamentoServico;
	}

	public FinLancamentoCaixa getFinLancamentoCaixa() {
		return finLancamentoCaixa;
	}

	public void setFinLancamentoCaixa(FinLancamentoCaixa finLancamentoCaixa) {
		this.finLancamentoCaixa = finLancamentoCaixa;
	}

	public boolean isPodeIncluirServico() {
		return podeIncluirServico;
	}

	public void setPodeIncluirServico(boolean podeIncluirServico) {
		this.podeIncluirServico = podeIncluirServico;
	}
	
	
	
	
	
	
	
	
	
}
