
package controller;


import dao.ProdutosFacade;
import entities.Empregados;
import entities.Produtos;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "produtosController")
@SessionScoped
public class ProdutosController implements Serializable {

    @EJB
    private ProdutosFacade produtosFacade;
    
    /*Objecto usado quando é preciso editar ou criar produto novo*/
    private Produtos produtoEdit = new Produtos();
    
    private Integer idProduto;
    private Integer empregados_idEmpregado;
    private String nomeDoProduto;

    public ProdutosController() {
        
    }
    
    public void esvaziarVariaveis(Produtos p){
        p.setIdProduto(0);
        p.setNomeDoProduto("");
        p.setEmpregadosidEmpregado(null);
        
    }
    
    public boolean verSeONomeEstaVazio(Produtos p){
        
        return p.getNomeDoProduto().equals("");
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getEmpregados_idEmpregado() {
        return empregados_idEmpregado;
    }

    public void setEmpregados_idEmpregado(Integer empregados_idEmpregado) {
        this.empregados_idEmpregado = empregados_idEmpregado;
    }

    public String getNomeDoProduto() {
        return nomeDoProduto;
    }

    public void setNomeDoProduto(String nomeDoProduto) {
        this.nomeDoProduto = nomeDoProduto;
    }

    public Produtos getProdutoEdit() {
        return produtoEdit;
    }

    public void setProdutoEdit(Produtos produtoEdit) {
        this.produtoEdit = produtoEdit;
    }

    public List<Produtos> getTodosProdutos() {
        return this.produtosFacade.findAll();
    }

    /*Retorna uma lista de Produtos de todos os produtos associados ao id deste
      empregado*/
    public List<Produtos> getTodosProdutosDesteEmpregado(Integer idEmpregado) {

        return this.produtosFacade.getProdutosDeUmEmpregado(idEmpregado);
    }
    
    /*Retorna uma lista só do NomeDoProduto, de todos os produtos associados
      ao id deste empregado */
    public List<Produtos> getListaDoNomeDoProdutoDesteEmpregado(Integer idEmpregado) {

        return this.produtosFacade.getNomeDoProdutoDeUmEmpregado(idEmpregado);
    }
    
    /*Cria um produto, verifica se já existe um produto com o mesmo nome na lista de
      todos os empregados*/
    public String criarProduto(Empregados e) {
        
        if(this.produtosFacade.verSeJaExisteUmProdutoComOMesmoNome(this.produtoEdit.getNomeDoProduto())){
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Esse produto já existe"));
            return "criarProduto";
            
        }else{
        
        this.produtoEdit.setEmpregadosidEmpregado(e);
        this.produtosFacade.create(produtoEdit);
        esvaziarVariaveis(produtoEdit);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Novo produto adicionado"));
        return "criarProduto";
        }
    }
    
    
    /*Edita produtos, verifica se o utlizador está a tentar inserir uma string
      vazia ou se já existe um produto com esse nome*/
    public String editarProduto() {
        
       
        if (verSeONomeEstaVazio(this.produtoEdit)) { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Nenhum campo pode ficar em branco"));
            return null;
            
        }else if(this.produtosFacade.verSeJaExisteUmProdutoComOMesmoNome(this.produtoEdit.getNomeDoProduto())){
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Esse produto já existe"));
            return null;
            
        } else {
            this.produtosFacade.edit(this.produtoEdit);
            esvaziarVariaveis(produtoEdit);
            return "gerirProdutos.xhtml?faces-redirect=true";
        }

    }
    

    public String eliminarProduto(Produtos produto) {
        this.produtosFacade.remove(produto);
        return "gerirProdutos.xhtml?faces-redirect=true";
    }
    
    
    //para os campos da pagina criarProduto aparecerem vazios
    public String paginaCriarProduto() {

        esvaziarVariaveis(produtoEdit);
        return "criarProduto.xhtml?faces-redirect=true";
    }
    
    /*faz uma query para actualizar o produtoEdit que vai ser necessario caso
      seja criado uma nova categoria*/
     public String paginaParaCriarCategoria(){
        
        this.produtoEdit = this.produtosFacade.getProdutoPeloNome(this.nomeDoProduto);
        
        
        return "criarCategoria.xhtml?faces-redirect=true";
    }
     
     

}
