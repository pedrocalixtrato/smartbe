
package com.smartbe.controller;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import com.smartbe.model.dao.DaoGenerico;
import com.smartbe.util.FacesContextUtil;

public abstract class AbstractController<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	protected T2TiLazyDataModel<T> dataModel;
	private T objetoSelecionado;
	private T objeto;
	private boolean telaGrid = true;

	protected DaoGenerico<T> dao;
	// protected DaoFactory daoFactory;
	private Map<String, String> debitoCredito;
	private Map<String, String> simNao;
	private Map<String, String> tipoPessoa;
	private Map<String, String> sexo;
	private Map<String, String> tipoSangue;
	private Map<String, String> racaCor;
	private Map<String, String> crt;
	private Map<String, String> tipoRegimeEmpresa;
	private Map<String, String> tipoTelefone;
	private Map<String, String> tipoSindicato;
	private Map<String, String> produtoClasse;
	private Map<String, String> tipoItemSped;
	private Map<String, String> produtoTipo;
	private Map<String, String> produtoIat;
	private Map<String, String> produtoIppt;
	private Map<String, String> clienteIndicadorPreco;
	private Map<String, String> clienteTipoFrete;
	private Map<String, String> clienteFormaDesconto;
	private Map<String, String> fornecedorLocalizacao;
	private Map<String, String> colaboradorFormaPagamento;
	private Map<String, String> talonarioChequeStatus;
	private Map<String, String> chequeStatus;
	private Map<String, String> tipoContaCaixa;
	private Map<String, String> tipoFeriado;
	private Map<String, String> feriadoAbrangencia;
	private Map<String, String> tipoNaturazaFinanceira;
	private Map<String, String> layoutRemessa;
	private Map<String, String> especieCobranca;
	private Map<String, String> tipoBaixa;
	private Map<String, String> compraSituacaoCotacao;
	private Map<String, String> compraFormaPagamento;
	private Map<String, String> compraTipoFrete;
	private Map<String, String> vendaOrcamentoTipo;
	private Map<String, String> vendaOrcamentoSituacao;
	private Map<String, String> formaPagamento;
	private Map<String, String> vendaResponsavelFrete;
	private Map<String, String> vendaRomaneioSituacao;
	private Map<String, String> requisicaoInternaSituacao;
	private Map<String, String> tipoReajuste;
	private Map<String, Integer> origemMercadoriaNfe;
	private Map<String, String> codigoModeloNfe;
	private Map<String, Integer> localDestinoNfe;
	private Map<String, Integer> consumidorOperacaoNfe;
	private Map<String, Integer> consumidorPresencaNfe;
	private Map<String, Integer> tipoOperacaoNfe;
	private Map<String, Integer> tipoEmissaoNfe;
	private Map<String, Integer> finalidadeEmissaoNfe;
	private Map<String, Integer> formatoImpressaoDanfeNfe;
	private Map<String, Integer> modalidadeFreteNfe;
	private Map<String, Integer> statusNfe;
	private Map<String, String> origemMercadoria;
	private Map<String, String> tributIcmsBaseCalculo;
	private Map<String, String> tributIcmsStBaseCalculo;
	private Map<String, String> pisModalidadeBaseCalculo;
	private Map<String, String> issModalidadeBaseCalculo;
	private Map<String, String> issCodigoTributacao;
	private Map<String, String> funcao;
	private Map<String, Integer> tempoDuracao;
	private Map<String, String> statusServico;
	private Map<String, String> statusPagar;
	private String somenteNFE;
	

	@PostConstruct
	public void init() {
		// daoFactory = new DaoFactory();
		// dao = daoFactory.createDao(getClazz());
		dao = new DaoGenerico<>(getClazz());
		dataModel = new T2TiLazyDataModel<>();
		dataModel.setClazz(getClazz());
		dataModel.setDao(dao);
		if (FacesContextUtil.getUsuarioSessao() != null) {
			somenteNFE = FacesContextUtil.getUsuarioSessao().getPapel().getNome();
		}

		simNao = new LinkedHashMap<>();
		simNao.put("Sim", "S");
		simNao.put("Não", "N");

		tipoPessoa = new LinkedHashMap<>();
		tipoPessoa.put("Fisica", "F");
		tipoPessoa.put("Jurídica", "J");

		sexo = new LinkedHashMap<>();
		sexo.put("Masculino", "M");
		sexo.put("Feminino", "F");

		tipoSangue = new LinkedHashMap<>();
		tipoSangue.put("A+", "A+");
		tipoSangue.put("B+", "B+");
		tipoSangue.put("O+", "O+");
		tipoSangue.put("AB+", "AB+");
		tipoSangue.put("A-", "A-");
		tipoSangue.put("B-", "B-");
		tipoSangue.put("AB-", "AB-");
		tipoSangue.put("O-", "O-");

		racaCor = new LinkedHashMap<>();
		racaCor.put("Branco", "B");
		racaCor.put("Negro", "N");
		racaCor.put("Pardo", "P");
		racaCor.put("Indio", "I");

		tipoSindicato = new LinkedHashMap<>();
		tipoSindicato.put("Patronal", "P");
		tipoSindicato.put("Empregados", "E");

		produtoClasse = new LinkedHashMap<>();
		produtoClasse.put("A", "A");
		produtoClasse.put("B", "B");
		produtoClasse.put("C", "C");

		produtoTipo = new LinkedHashMap<>();
		produtoTipo.put("Venda", "V");
		produtoTipo.put("Composição", "C");
		produtoTipo.put("Produção", "P");
		produtoTipo.put("Insumo", "I");
		produtoTipo.put("Uso Proprio", "U");

		produtoIat = new LinkedHashMap<>();
		produtoIat.put("Arredondamento", "A");
		produtoIat.put("Truncamento", "T");

		produtoIppt = new LinkedHashMap<>();
		produtoIppt.put("Próprio", "P");
		produtoIppt.put("Terceiro", "T");

		tipoItemSped = new LinkedHashMap<>();
		tipoItemSped.put("Mercadoria para Revenda", "00");
		tipoItemSped.put("Matéria-Prima", "01");
		tipoItemSped.put("Embalagem", "02");
		tipoItemSped.put("Produto em Processo", "03");
		tipoItemSped.put("Produto Acabado", "04");
		tipoItemSped.put("Subproduto", "05");
		tipoItemSped.put("Produto Intermediário", "06");
		tipoItemSped.put("Material de Uso e Consumo", "07");
		tipoItemSped.put("Ativo Imobilizado", "08");
		tipoItemSped.put("Serviços", "09");
		tipoItemSped.put("Outros Insumos", "10");
		tipoItemSped.put("Outras", "99");

		debitoCredito = new LinkedHashMap<>();
		debitoCredito.put("Debito", "Debito");
		debitoCredito.put("Credito", "Credito");

		clienteIndicadorPreco = new LinkedHashMap<>();
		clienteIndicadorPreco.put("Tabela", "T");
		clienteIndicadorPreco.put("Último Pedido", "P");

		clienteTipoFrete = new LinkedHashMap<>();
		clienteTipoFrete.put("Emitente", "E");
		clienteTipoFrete.put("Destinatario", "D");
		clienteTipoFrete.put("Sem frete", "S");
		clienteTipoFrete.put("Terceiros", "T");

		clienteFormaDesconto = new LinkedHashMap<>();
		clienteFormaDesconto.put("Produto", "P");
		clienteFormaDesconto.put("Fim do Pedido", "F");

		crt = new LinkedHashMap<>();
		crt.put("1 - Simples Nacional", "1");
		crt.put("2 - Simples Nac - Excesso", "2");
		crt.put("3 - Regime Normal", "3");

		tipoRegimeEmpresa = new LinkedHashMap<>();
		tipoRegimeEmpresa.put("Lucro Real", "1");
		tipoRegimeEmpresa.put("Lucro Presumido", "2");
		tipoRegimeEmpresa.put("Simples Nacional", "3");

		tipoTelefone = new LinkedHashMap<>();
		tipoTelefone.put("Residencial", "0");
		tipoTelefone.put("Comercial", "1");
		tipoTelefone.put("Celular", "2");
		tipoTelefone.put("Outro", "3");

		fornecedorLocalizacao = new LinkedHashMap<>();
		fornecedorLocalizacao.put("Nacional", "N");
		fornecedorLocalizacao.put("Exterior", "E");

		colaboradorFormaPagamento = new LinkedHashMap<>();
		colaboradorFormaPagamento.put("Dinheiro", "1");
		colaboradorFormaPagamento.put("Cheque", "2");
		colaboradorFormaPagamento.put("Conta", "3");

		talonarioChequeStatus = new LinkedHashMap<>();
		talonarioChequeStatus.put("Normal", "N");
		talonarioChequeStatus.put("Cancelado", "C");
		talonarioChequeStatus.put("Extraviado", "E");
		talonarioChequeStatus.put("Utilizado", "U");

		chequeStatus = new LinkedHashMap<>();
		chequeStatus.put("Em Ser", "E");
		chequeStatus.put("Baixado", "B");
		chequeStatus.put("Utilizado", "U");
		chequeStatus.put("Compensado", "C");
		chequeStatus.put("Cancelado", "N");

		tipoContaCaixa = new LinkedHashMap<>();
		tipoContaCaixa.put("Corrente", "C");
		tipoContaCaixa.put("Poupança", "P");
		tipoContaCaixa.put("Investimento", "I");
		tipoContaCaixa.put("Caixa Interno", "X");

		tipoFeriado = new LinkedHashMap<>();
		tipoFeriado.put("Fixo", "F");
		tipoFeriado.put("Móvel", "M");

		feriadoAbrangencia = new LinkedHashMap<>();
		feriadoAbrangencia.put("Federal", "F");
		feriadoAbrangencia.put("Estadual", "E");
		feriadoAbrangencia.put("Municipal", "M");

		tipoNaturazaFinanceira = new LinkedHashMap<>();
		tipoNaturazaFinanceira.put("Receita", "R");
		tipoNaturazaFinanceira.put("Despesa", "D");

		layoutRemessa = new LinkedHashMap<>();
		layoutRemessa.put("240", "240");
		layoutRemessa.put("400", "400");

		especieCobranca = new LinkedHashMap<>();
		especieCobranca.put("Duplicata Mercantil", "DM");
		especieCobranca.put("Duplicata de Serviços", "DS");
		especieCobranca.put("Recibo", "RC");
		especieCobranca.put("Nota Promissória", "NP");

		tipoBaixa = new LinkedHashMap<>();
		tipoBaixa.put("Total", "T");
		tipoBaixa.put("Parcial", "P");

		compraSituacaoCotacao = new LinkedHashMap<>();
		compraSituacaoCotacao.put("Aberta", "A");
		compraSituacaoCotacao.put("Confirmada", "C");
		compraSituacaoCotacao.put("Fechada", "F");

		compraFormaPagamento = new LinkedHashMap<>();
		compraFormaPagamento.put("A Vista", "0");
		compraFormaPagamento.put("A Prazo", "1");
		compraFormaPagamento.put("Outros", "2");

		compraTipoFrete = new LinkedHashMap<>();
		compraTipoFrete.put("CIF", "C");
		compraTipoFrete.put("FOB", "F");

		vendaOrcamentoTipo = new LinkedHashMap<>();
		vendaOrcamentoTipo.put("Orçamento", "O");
		vendaOrcamentoTipo.put("Pedido", "P");

		vendaOrcamentoSituacao = new LinkedHashMap<>();
		vendaOrcamentoSituacao.put("Digitacao", "D");
		vendaOrcamentoSituacao.put("Producao", "P");
		vendaOrcamentoSituacao.put("Expedicao", "X");
		vendaOrcamentoSituacao.put("Faturado", "F");
		vendaOrcamentoSituacao.put("Entregue", "E");

		formaPagamento = new LinkedHashMap<>();
		formaPagamento.put("Dinheiro", "DINHEIRO");
		formaPagamento.put("Credito", "CREDITO");
		formaPagamento.put("Debito", "DEBITO");
		formaPagamento.put("Cheque", "CHEQUE");
		formaPagamento.put("a Prazo", "PRAZO");

		vendaResponsavelFrete = new LinkedHashMap<>();
		vendaResponsavelFrete.put("Emitente", "1");
		vendaResponsavelFrete.put("Destinatário", "2");

		vendaRomaneioSituacao = new LinkedHashMap<>();
		vendaRomaneioSituacao.put("Aberto", "A");
		vendaRomaneioSituacao.put("Trânsito", "T");
		vendaRomaneioSituacao.put("Encerrado", "E");

		requisicaoInternaSituacao = new LinkedHashMap<>();
		requisicaoInternaSituacao.put("Aberta", "A");
		requisicaoInternaSituacao.put("Deferida", "D");
		requisicaoInternaSituacao.put("Indeferida", "I");

		tipoReajuste = new LinkedHashMap<>();
		tipoReajuste.put("Aumentar", "A");
		tipoReajuste.put("Diminuir", "D");

		codigoModeloNfe = new LinkedHashMap<>();
		codigoModeloNfe.put("Nota Fiscal Eletrônica - NFe", "55");

		localDestinoNfe = new LinkedHashMap<>();
		localDestinoNfe.put("Operação Interna", 1);
		localDestinoNfe.put("Operação Interestadual", 2);
		localDestinoNfe.put("Operação com Exterior", 3);

		consumidorOperacaoNfe = new LinkedHashMap<>();
		consumidorOperacaoNfe.put("Normal", 0);
		consumidorOperacaoNfe.put("Consumidor Final", 1);

		consumidorPresencaNfe = new LinkedHashMap<>();
		consumidorPresencaNfe.put("Operação Presencial", 1);
		consumidorPresencaNfe.put("Operação não Presencial - Internet", 2);
		consumidorPresencaNfe.put("Operação não Presencial - Teleatendimento", 3);
		consumidorPresencaNfe.put("Operação não Presencial - Outros", 9);
		consumidorPresencaNfe.put("Não se aplica", 0);

		tipoOperacaoNfe = new LinkedHashMap<>();
		tipoOperacaoNfe.put("Entrada", 0);
		tipoOperacaoNfe.put("Saída", 1);

		tipoEmissaoNfe = new LinkedHashMap<>();
		tipoEmissaoNfe.put("Normal", 1);
		tipoEmissaoNfe.put("Contigência", 2);
		tipoEmissaoNfe.put("Contingência SCAN", 3);
		tipoEmissaoNfe.put("Contingência DPEC", 4);
		tipoEmissaoNfe.put("Contingência FS-DA", 5);

		finalidadeEmissaoNfe = new LinkedHashMap<>();
		finalidadeEmissaoNfe.put("Normal", 1);
		finalidadeEmissaoNfe.put("Complementar", 2);
		finalidadeEmissaoNfe.put("Ajuste", 3);

		formatoImpressaoDanfeNfe = new LinkedHashMap<>();
		formatoImpressaoDanfeNfe.put("Retrato", 1);
		formatoImpressaoDanfeNfe.put("Paisagem", 1);

		modalidadeFreteNfe = new LinkedHashMap<>();
		modalidadeFreteNfe.put("Conta Emitente", 0);
		modalidadeFreteNfe.put("Conta Destinatário", 1);
		modalidadeFreteNfe.put("Conta Terceiros", 2);
		modalidadeFreteNfe.put("Sem Frete", 9);

		statusNfe = new LinkedHashMap<>();
		statusNfe.put("Em Edição", 0);
		statusNfe.put("Salva", 1);
		statusNfe.put("Validada", 2);
		statusNfe.put("Assinada", 3);
		statusNfe.put("Enviada", 4);
		statusNfe.put("Autorizada", 5);
		statusNfe.put("Cancelada", 6);

		origemMercadoria = new LinkedHashMap<>();
		origemMercadoria.put("Nacional", "0");
		origemMercadoria.put("Estrangeira - Importação direta", "1");
		origemMercadoria.put("Estrangeira - Adquirida no mercado interno", "2");

		tributIcmsBaseCalculo = new LinkedHashMap<>();
		tributIcmsBaseCalculo.put("Margem Valor Agregado (%)", "0");
		tributIcmsBaseCalculo.put("Pauta (Valor)", "1");
		tributIcmsBaseCalculo.put("Preço Tabelado Máx. (valor)", "2");
		tributIcmsBaseCalculo.put("Valor da Operação", "3");

		tributIcmsStBaseCalculo = new LinkedHashMap<>();
		tributIcmsStBaseCalculo.put("Preço tabelado ou máximo sugerido", "0");
		tributIcmsStBaseCalculo.put("Lista Negativa (valor)", "1");
		tributIcmsStBaseCalculo.put("Lista Positiva (valor)", "2");
		tributIcmsStBaseCalculo.put("Lista Neutra (valor)", "3");
		tributIcmsStBaseCalculo.put("Margem Valor Agregado(%)", "4");
		tributIcmsStBaseCalculo.put("Pauta (valor)", "5");

		pisModalidadeBaseCalculo = new LinkedHashMap<>();
		pisModalidadeBaseCalculo.put("Percentual", "0");
		pisModalidadeBaseCalculo.put("Unidade", "0");

		issModalidadeBaseCalculo = new LinkedHashMap<>();
		issModalidadeBaseCalculo.put("Valor Operação", "0");
		issModalidadeBaseCalculo.put("Outros", "9");

		issCodigoTributacao = new LinkedHashMap<>();
		issCodigoTributacao.put("Normal", "N");
		issCodigoTributacao.put("Retida", "R");
		issCodigoTributacao.put("Substituta", "S");
		issCodigoTributacao.put("Isenta", "I");
		
		funcao = new LinkedHashMap<>();
		funcao.put("Penteados", "PENTEADOS");
		funcao.put("Assistente", "ASSISTENTE");
		funcao.put("Maquiador(a)", "MAQUIADOR");
		
		tempoDuracao = new LinkedHashMap<>();
		tempoDuracao.put("30 minutos", 30);
		tempoDuracao.put("45 minutos", 45);
		tempoDuracao.put("1 hora", 60);
		tempoDuracao.put("1 hora e 30 minutos", 90);
		tempoDuracao.put("2 horas", 120);
		
		statusServico = new LinkedHashMap<>();
		statusServico.put("EM ABERTO","EM ABERTO");
		statusServico.put("INICIADO","INICIADO");
		statusServico.put("FINALIZADO","FINALIZADO");
		
		statusPagar = new LinkedHashMap<>();
		statusPagar.put("Em aberto", "EM ABERTO");
		statusPagar.put("Baixado", "BAIXADO");
		

	}

	/* https://java.net/jira/browse/JAVASERVERFACES-1808 */
	@SuppressWarnings("rawtypes")
	public String keyFromValue(LinkedHashMap map, Object value) {
		for (Object o : map.keySet()) {
			if (map.get(o).equals(value)) {
				return String.valueOf(o);
			}
		}
		return null;
	}

	public abstract Class<T> getClazz();

	public abstract String getFuncaoBase();

	public T2TiLazyDataModel<T> getDataModel() {
		return dataModel;
	}

	public void incluir() {
		try {
			objeto = null;
			objeto = (T) getClazz().newInstance();
			telaGrid = false;
		} catch (InstantiationException | IllegalAccessException e) {
			FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um erro ao iniciar a inclusão do registro!", e.getMessage());
		}
	}

	public void alterar() {
		objeto = objetoSelecionado;
		telaGrid = false;
	}

	public void salvar() {
		salvar(null);
	}
	
	public void salvarSemMsg() {
		try {
			objeto = dao.merge(objeto);			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro ao salvar o registro!",
					e.getMessage());
		}
	}
	

	public void salvar(String mensagem) {
		try {
			objeto = dao.merge(objeto);
			FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_INFO,
					mensagem != null ? mensagem : "Registro salvo com sucesso!", null);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro ao salvar o registro!",
					e.getMessage());
		}
	}

	public void excluir() {
		try {
			Integer idObjeto = null;
			if (objetoSelecionado != null) {
				idObjeto = (Integer) getClazz().getMethod("getId").invoke(objetoSelecionado);
			}
			if (objetoSelecionado == null || idObjeto == null) {
				FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_INFO, "Nenhum registro selecionado!", null);
			} else {
				dao.excluir(objetoSelecionado, idObjeto);
				FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro ao excluir o registro!",
					e.getMessage());
		}
	}

	public void voltar() {
		telaGrid = true;
	}

	public boolean isTelaGrid() {
		return telaGrid;
	}

	public boolean podeConsultar() {
		return FacesContextUtil.isUserInRole(somenteNFE + "_CONSULTA") || FacesContextUtil.isUserInRole("ADMIN");
	}

	public boolean podeInserir() {
		return FacesContextUtil.isUserInRole(somenteNFE + "_INSERE") || FacesContextUtil.isUserInRole("ADMIN");
	}

	public boolean podeAlterar() {
		return FacesContextUtil.isUserInRole(somenteNFE + "_ALTERA") || FacesContextUtil.isUserInRole("ADMIN");
	}

	public boolean podeExcluir() {
		return FacesContextUtil.isUserInRole(somenteNFE + "_EXCLUI") || FacesContextUtil.isUserInRole("ADMIN");
	}

	public T getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(T objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	public T getObjeto() {
		return objeto;
	}

	public void setObjeto(T objeto) {
		this.objeto = objeto;
	}

	public Map<String, String> getSimNao() {
		return simNao;
	}

	public Map<String, String> getTipoSindicato() {
		return tipoSindicato;
	}

	public Map<String, String> getProdutoClasse() {
		return produtoClasse;
	}

	public Map<String, String> getTipoItemSped() {
		return tipoItemSped;
	}

	public Map<String, String> getProdutoTipo() {
		return produtoTipo;
	}

	public Map<String, String> getProdutoIat() {
		return produtoIat;
	}

	public Map<String, String> getProdutoIppt() {
		return produtoIppt;
	}

	public Map<String, String> getClienteIndicadorPreco() {
		return clienteIndicadorPreco;
	}

	public Map<String, String> getClienteTipoFrete() {
		return clienteTipoFrete;
	}

	public Map<String, String> getClienteFormaDesconto() {
		return clienteFormaDesconto;
	}

	public Map<String, String> getSexo() {
		return sexo;
	}

	public Map<String, String> getTipoSangue() {
		return tipoSangue;
	}

	public Map<String, String> getRacaCor() {
		return racaCor;
	}

	public Map<String, String> getCrt() {
		return crt;
	}

	public Map<String, String> getTipoRegimeEmpresa() {
		return tipoRegimeEmpresa;
	}

	public Map<String, String> getTipoTelefone() {
		return tipoTelefone;
	}

	public Map<String, String> getTipoPessoa() {
		return tipoPessoa;
	}

	public Map<String, String> getFornecedorLocalizacao() {
		return fornecedorLocalizacao;
	}

	public Map<String, String> getColaboradorFormaPagamento() {
		return colaboradorFormaPagamento;
	}

	public void setColaboradorFormaPagamento(Map<String, String> colaboradorFormaPagamento) {
		this.colaboradorFormaPagamento = colaboradorFormaPagamento;
	}

	public Map<String, String> getTalonarioChequeStatus() {
		return talonarioChequeStatus;
	}

	public Map<String, String> getChequeStatus() {
		return chequeStatus;
	}

	public Map<String, String> getTipoContaCaixa() {
		return tipoContaCaixa;
	}

	public Map<String, String> getTipoFeriado() {
		return tipoFeriado;
	}

	public Map<String, String> getFeriadoAbrangencia() {
		return feriadoAbrangencia;
	}

	public Map<String, String> getTipoNaturazaFinanceira() {
		return tipoNaturazaFinanceira;
	}

	public Map<String, String> getLayoutRemessa() {
		return layoutRemessa;
	}

	public Map<String, String> getEspecieCobranca() {
		return especieCobranca;
	}

	public Map<String, String> getTipoBaixa() {
		return tipoBaixa;
	}

	public Map<String, String> getCompraSituacaoCotacao() {
		return compraSituacaoCotacao;
	}

	public Map<String, String> getCompraFormaPagamento() {
		return compraFormaPagamento;
	}

	public Map<String, String> getCompraTipoFrete() {
		return compraTipoFrete;
	}

	public Map<String, String> getVendaOrcamentoTipo() {
		return vendaOrcamentoTipo;
	}

	public Map<String, String> getVendaOrcamentoSituacao() {
		return vendaOrcamentoSituacao;
	}

	public Map<String, String> getFormaPagamento() {
		return formaPagamento;
	}

	public Map<String, String> getVendaResponsavelFrete() {
		return vendaResponsavelFrete;
	}

	public Map<String, String> getVendaRomaneioSituacao() {
		return vendaRomaneioSituacao;
	}

	public Map<String, String> getRequisicaoInternaSituacao() {
		return requisicaoInternaSituacao;
	}

	public Map<String, String> getTipoReajuste() {
		return tipoReajuste;
	}

	public Map<String, Integer> getOrigemMercadoriaNfe() {
		return origemMercadoriaNfe;
	}

	public Map<String, String> getCodigoModeloNfe() {
		return codigoModeloNfe;
	}

	public Map<String, Integer> getLocalDestinoNfe() {
		return localDestinoNfe;
	}

	public Map<String, Integer> getConsumidorOperacaoNfe() {
		return consumidorOperacaoNfe;
	}

	public Map<String, Integer> getConsumidorPresencaNfe() {
		return consumidorPresencaNfe;
	}

	public Map<String, Integer> getTipoOperacaoNfe() {
		return tipoOperacaoNfe;
	}

	public Map<String, Integer> getTipoEmissaoNfe() {
		return tipoEmissaoNfe;
	}

	public Map<String, Integer> getFinalidadeEmissaoNfe() {
		return finalidadeEmissaoNfe;
	}

	public Map<String, Integer> getFormatoImpressaoDanfeNfe() {
		return formatoImpressaoDanfeNfe;
	}

	public Map<String, Integer> getModalidadeFreteNfe() {
		return modalidadeFreteNfe;
	}

	public Map<String, Integer> getStatusNfe() {
		return statusNfe;
	}

	public Map<String, String> getOrigemMercadoria() {
		return origemMercadoria;
	}

	public Map<String, String> getTributIcmsBaseCalculo() {
		return tributIcmsBaseCalculo;
	}

	public Map<String, String> getTributIcmsStBaseCalculo() {
		return tributIcmsStBaseCalculo;
	}

	public Map<String, String> getPisModalidadeBaseCalculo() {
		return pisModalidadeBaseCalculo;
	}

	public Map<String, String> getIssModalidadeBaseCalculo() {
		return issModalidadeBaseCalculo;
	}

	public Map<String, String> getIssCodigoTributacao() {
		return issCodigoTributacao;
	}

	public String getSomenteNFE() {
		return somenteNFE;
	}

	public void setSomenteNFE(String somenteNFE) {
		this.somenteNFE = somenteNFE;
	}

	public Map<String, String> getDebitoCredito() {
		return debitoCredito;
	}

	public void setDebitoCredito(Map<String, String> debitoCredito) {
		this.debitoCredito = debitoCredito;
	}

	public Map<String, String> getFuncao() {
		return funcao;
	}

	public void setFuncao(Map<String, String> funcao) {
		this.funcao = funcao;
	}

	public Map<String, Integer> getTempoDuracao() {
		return tempoDuracao;
	}

	public void setTempoDuracao(Map<String, Integer> tempoDuracao) {
		this.tempoDuracao = tempoDuracao;
	}

	public Map<String, String> getStatusServico() {
		return statusServico;
	}

	public void setStatusServico(Map<String, String> statusServico) {
		this.statusServico = statusServico;
	}

	public Map<String, String> getStatusPagar() {
		return statusPagar;
	}

	public void setStatusPagar(Map<String, String> statusPagar) {
		this.statusPagar = statusPagar;
	}
	
	
	

}
