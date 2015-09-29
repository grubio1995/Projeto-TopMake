package br.com.topmake.Bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.topmake.dao.LinhaDAO;
import br.com.topmake.domain.Linha;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class LinhaBean implements Serializable {
	private Linha linha;
	private List<Linha> linhas;

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public List<Linha> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<Linha> linhas) {
		this.linhas = linhas;
	}

	public void Novo() {
		linha = new Linha();
	}
	
	@PostConstruct
	public void Listar() {
		try {
			LinhaDAO linhaDAO = new LinhaDAO();
			linhas = linhaDAO.Listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as linhas!!!");
			erro.printStackTrace();
		}
	}
	
	public void novo() {
		linha = new Linha();
	}

	public void Salvar() {
		try {
			LinhaDAO linhaDAO = new LinhaDAO();
			linhaDAO.Merge(linha);

			linha = new Linha();
			linhas = linhaDAO.Listar();

			Messages.addGlobalInfo("A linha " + linha.getDescricao() + " foi salva com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a linha!!!");
			erro.printStackTrace();
		}
	}
	
	
	public void Excluir(ActionEvent evento) {
		try {
			linha = (Linha) evento.getComponent().getAttributes().get("linhaSelecionada");
			Messages.addGlobalInfo("Descrição: " + linha.getDescricao());

			LinhaDAO linhaDAO = new LinhaDAO();
			linhaDAO.Excluir(linha);

			linhas = linhaDAO.Listar();

			Messages.addGlobalInfo("Linha removida com sucesso ");
		} catch (RuntimeException erro) {
			Messages.addGlobalInfo("Ocorreu um erro ao tentar remover a linha" + erro);
			erro.printStackTrace();
		}
	}

	public void Editar(ActionEvent evento) {
		linha = (Linha) evento.getComponent().getAttributes().get("linhaSelecionada");
		Messages.addGlobalInfo("" + linha.getDescricao());

	}
}
