package controller;

import dao.EmpregadosFacade;
import entities.Empregados;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "empregadosController")
@SessionScoped
public class EmpregadosController implements Serializable {

    @EJB
    private EmpregadosFacade empregadosFacade;
    
    /*Objecto usado para atualizar o Managed bean*/
    private Empregados empregado = new Empregados();
    
    /*Este objecto é utilizado quando é preciso
      editar ou adicionar um novo empregado*/
    private Empregados empregadoEdit = new Empregados();
    
    private Integer idEmpregado;
    private String userName;
    private String password;
    private String nomeDoEmpregado;

    public EmpregadosController() {
    }

    public void updateMB(Empregados e) {
        this.idEmpregado = e.getIdEmpregado();
        this.nomeDoEmpregado = e.getNomeDoEmpregado();
        this.password = e.getPassword();
        this.userName = e.getUserName();

    }

    public void esvaziarVariaveis(Empregados e) {
        e.setIdEmpregado(0);
        e.setNomeDoEmpregado("");
        e.setPassword("");
        e.setUserName("");
    }

    public boolean verSeAlgumCampoEstaVazio(Empregados e) {

        return e.getUserName().equals("") || e.getPassword().equals("") || e.getNomeDoEmpregado().equals("");
    }

    public Empregados getEmpregado() {
        return empregado;
    }

    public void setEmpregado(Empregados empregado) {
        this.empregado = empregado;
    }

    public Empregados getEmpregadoEdit() {
        return empregadoEdit;
    }

    public void setEmpregadoEdit(Empregados empregadoEdit) {
        this.empregadoEdit = empregadoEdit;
    }

    public Integer getIdEmpregado() {
        return idEmpregado;
    }

    public void setIdEmpregado(Integer idEmpregado) {
        this.idEmpregado = idEmpregado;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNomeDoEmpregado() {
        return nomeDoEmpregado;
    }

    public void setNomeDoEmpregado(String nomeDoEmpregado) {
        this.nomeDoEmpregado = nomeDoEmpregado;
    }

    public List<Empregados> getTodosEmpregados() {
        return this.empregadosFacade.findAll();
    }

    public List<Empregados> getTodosEmpregadosExceptoAdmin() {
        return this.empregadosFacade.getTodosEmpregadosMenosAdmin();
    }

    /*modificar dados presentes na BD de outros empregados, exclusivo para o 
      admin verifca se o utlizador está a tentar introduzir uma string vazia,
      ou se o user name já existe*/
    public String editarEmpregado() {

        if (verSeAlgumCampoEstaVazio(this.empregadoEdit)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Nenhum campo pode ficar em branco"));
            return null;
        } else if (this.empregadosFacade.verSeUserNameJaExiste(this.empregadoEdit.getUserName())) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Esse utilizador já existe"));
            return null;

        } else {
            this.empregadosFacade.edit(this.empregadoEdit);
            esvaziarVariaveis(empregadoEdit);
            return "gerirEmpregados.xhtml?faces-redirect=true";
        }

    }

    //modificar dados do proprio empregado na BD
    public String updateEmpregado() {
        this.empregadosFacade.edit(this.empregado);
        updateMB(empregado);

        return "areaPessoal.xhtml?faces-redirect=true";
    }

    /*criar um novo empregado, exclusivo para o admin
      verifica se o userName já está em uso*/
    public String criarEmpregado() {

        if (this.empregadosFacade.verSeUserNameJaExiste(this.empregadoEdit.getUserName())) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Esse utilizador já existe"));
            return null;

        } else {
            this.empregadosFacade.create(empregadoEdit);
            esvaziarVariaveis(empregadoEdit);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Novo empregado adicionado"));
            return "criarEmpregados";
        }
    }
    
    //  elimina empregado, JSF trata do aviso
    public String eliminarEmpregado(Empregados empregado) {
        this.empregadosFacade.remove(empregado);
        return "gerirEmpregados.xhtml?faces-redirect=true";
    }

    //Para garantir que os inputText, durante a criação de novo empregado, aparecem vazios
    public String paginaCriarEmpregado() {

        esvaziarVariaveis(empregadoEdit);
        return "criarEmpregados.xhtml?faces-redirect=true";
    }
    
    /*usado no login, verifica se o user name e password estão correctas
      actualiza o Managed bean com o objecto que recebe da query*/
    public String validar() {

        empregado = (empregadosFacade.autenticar(this.userName, this.password));

        if (empregado == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Utilizador ou password incorrectas"));
            return "login";
        } else {
            updateMB(empregado);
            return reencaminhar();
        }
    }

    //reencaminha o admin para uma página diferente
    public String reencaminhar() {
        if (this.idEmpregado == 1) {
            return "areaAdmin.xhtml?faces-redirect=true";
        } else {
            return "areaTrabalho.xhtml?faces-redirect=true";
        }
    }

}
