package controller;

import dao.CategoriaFacade;
import entities.Categoria;
import entities.Produtos;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "categoriaController")
@SessionScoped
public class CategoriaController implements Serializable {

    @EJB
    private CategoriaFacade categoriaFacade;
    
    /*Objecto usado para editar ou adicionar uma nova categoria 
      à BD*/
    private Categoria categoriaEdit = new Categoria();
    private Integer idCategoria;
    private Integer produtos_idProdutos;
    private String nomeDaCategoria;

    public CategoriaController() {
    }

    public void esvaziarVariaveis(Categoria c) {
        c.setIdcategoria(0);
        c.setNomeDaCategoria("");
        c.setProdutosidProduto(null);
    }

    public boolean verSeONomeEstaVazio(Categoria c) {

        return c.getNomeDaCategoria().equals("");
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getProdutos_idProdutos() {
        return produtos_idProdutos;
    }

    public void setProdutos_idProdutos(Integer produtos_idProdutos) {
        this.produtos_idProdutos = produtos_idProdutos;
    }

    public String getNomeDaCategoria() {
        return nomeDaCategoria;
    }

    public void setNomeDaCategoria(String nomeDaCategoria) {
        this.nomeDaCategoria = nomeDaCategoria;
    }

    public Categoria getCategoriaEdit() {
        return categoriaEdit;
    }

    public void setCategoriaEdit(Categoria categoriaEdit) {
        this.categoriaEdit = categoriaEdit;
    }

    public List<Categoria> getTodasCategorias() {
        return this.categoriaFacade.findAll();
    }
    
    
    /*Retorna uma lista de Categoria de um empregado através do seu id*/
    public List<Categoria> getTodasCategoriasDesteEmpregado(Integer idEmpregado) {
        return this.categoriaFacade.todasAsCategoriasDesteEmpregado(idEmpregado);
    }

    //Retorna uma lista de Categoria associadas a um produto pelo nome de produto
    public List<Categoria> getTodasCategoriasDesteProduto(String nomeDoProduto) {

        return this.categoriaFacade.getCategoriasDeUmProduto(nomeDoProduto);
    }
    
    
    //Cria uma categoria, verifica se já existe uma com o mesmo nome na BD
    public String criarCategoria(Produtos p) {

        if (this.categoriaFacade.verSeJaExisteUmaCategoriaComOMesmoNome(this.categoriaEdit.getNomeDaCategoria())) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Esse produto já existe"));
            return "criarCategoria";
        } else {

            this.categoriaEdit.setProdutosidProduto(p);
            this.categoriaFacade.create(categoriaEdit);
            esvaziarVariaveis(categoriaEdit);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Nova categoria adicionada"));
            return "gerirCategoriasSegundaPagina.xhtml?faces-redirect=true";
        }
    }

    /*Edita uma categoria, verifica se o utlizador não está a tentar inserir 
      uma string vazia ou se a categoria já existe na BD*/
    public String editarCategoria() {

        if (verSeONomeEstaVazio(this.categoriaEdit)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Nenhum campo pode ficar em branco"));
            return null;

        } else if (this.categoriaFacade.verSeJaExisteUmaCategoriaComOMesmoNome(this.categoriaEdit.getNomeDaCategoria())) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Esse produto já existe"));
            return null;

        } else {
            this.categoriaFacade.edit(this.categoriaEdit);
            esvaziarVariaveis(categoriaEdit);
            return "gerirCategoriasSegundaPagina?faces-redirect=true";
        }

    }

    public String eliminarCategoria(Categoria categoria) {
        this.categoriaFacade.remove(categoria);
        return "gerirCategoriasSegundaPagina.xhtml?faces-redirect=true";
    }

}
