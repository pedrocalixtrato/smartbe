package com.smartbe.controller.cadastros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.smartbe.controller.AbstractController;
import com.smartbe.model.bean.cadastros.Funcionario;
import com.smartbe.model.bean.cadastros.Usuario;
import com.smartbe.model.dao.DaoGenerico;

@ManagedBean
@ViewScoped
public class UsuarioController extends AbstractController<Usuario> implements Serializable{

	
	
	private static final long serialVersionUID = 1L;
	@Override
	public Class<Usuario> getClazz() {
		return Usuario.class;
	}
	@Override
	public String getFuncaoBase() {
		return "USUARIO";
	}
	
	List<Funcionario> listaFuncionario = new ArrayList<>();
	private DaoGenerico<Funcionario> funcionarioDao;
	
	@Override
	@PostConstruct
	public void init() {
		super.init();
		funcionarioDao = new DaoGenerico<>(Funcionario.class);
		carregarFuncionarios();
	}
	
	 public List<Funcionario> carregarFuncionarios(){		 
			 
			 try {
				listaFuncionario = funcionarioDao.getBeans();
			} catch (Exception e) {			
				e.printStackTrace();
			}
			 
			 return listaFuncionario;
			 
		 }
	public List<Funcionario> getListaFuncionario() {
		return listaFuncionario;
	}
	public void setListaFuncionario(List<Funcionario> listaFuncionario) {
		this.listaFuncionario = listaFuncionario;
	}
	 
	 

}
