/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Garrett-PC
 */
@Entity
@Table(name = "empregados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empregados.findAll", query = "SELECT e FROM Empregados e")
    , @NamedQuery(name = "Empregados.findByIdEmpregado", query = "SELECT e FROM Empregados e WHERE e.idEmpregado = :idEmpregado")
    , @NamedQuery(name = "Empregados.findByUserName", query = "SELECT e FROM Empregados e WHERE e.userName = :userName")
    , @NamedQuery(name = "Empregados.findByPassword", query = "SELECT e FROM Empregados e WHERE e.password = :password")
    , @NamedQuery(name = "Empregados.findByNomeDoEmpregado", query = "SELECT e FROM Empregados e WHERE e.nomeDoEmpregado = :nomeDoEmpregado")})
public class Empregados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEmpregado")
    private Integer idEmpregado;
    @Size(max = 45)
    @Column(name = "userName")
    private String userName;
    @Size(max = 45)
    @Column(name = "password")
    private String password;
    @Size(max = 45)
    @Column(name = "nomeDoEmpregado")
    private String nomeDoEmpregado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empregadosidEmpregado")
    private List<Produtos> produtosList;

    public Empregados() {
    }

    public Empregados(Integer idEmpregado) {
        this.idEmpregado = idEmpregado;
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

    @XmlTransient
    public List<Produtos> getProdutosList() {
        return produtosList;
    }

    public void setProdutosList(List<Produtos> produtosList) {
        this.produtosList = produtosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpregado != null ? idEmpregado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empregados)) {
            return false;
        }
        Empregados other = (Empregados) object;
        if ((this.idEmpregado == null && other.idEmpregado != null) || (this.idEmpregado != null && !this.idEmpregado.equals(other.idEmpregado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Empregados[ idEmpregado=" + idEmpregado + " ]";
    }
    
}
