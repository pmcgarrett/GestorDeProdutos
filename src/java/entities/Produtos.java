
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "produtos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produtos.findAll", query = "SELECT p FROM Produtos p")
    , @NamedQuery(name = "Produtos.findByIdProduto", query = "SELECT p FROM Produtos p WHERE p.idProduto = :idProduto")
    , @NamedQuery(name = "Produtos.findByNomeDoProduto", query = "SELECT p FROM Produtos p WHERE p.nomeDoProduto = :nomeDoProduto")})
public class Produtos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProduto")
    private Integer idProduto;
    @Size(max = 45)
    @Column(name = "nomeDoProduto")
    private String nomeDoProduto;
    @JoinColumn(name = "empregados_idEmpregado", referencedColumnName = "idEmpregado")
    @ManyToOne(optional = false)
    private Empregados empregadosidEmpregado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produtosidProduto")
    private List<Categoria> categoriaList;

    public Produtos() {
    }

    public Produtos(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeDoProduto() {
        return nomeDoProduto;
    }

    public void setNomeDoProduto(String nomeDoProduto) {
        this.nomeDoProduto = nomeDoProduto;
    }

    public Empregados getEmpregadosidEmpregado() {
        return empregadosidEmpregado;
    }

    public void setEmpregadosidEmpregado(Empregados empregadosidEmpregado) {
        this.empregadosidEmpregado = empregadosidEmpregado;
    }

    @XmlTransient
    public List<Categoria> getCategoriaList() {
        return categoriaList;
    }

    public void setCategoriaList(List<Categoria> categoriaList) {
        this.categoriaList = categoriaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProduto != null ? idProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produtos)) {
            return false;
        }
        Produtos other = (Produtos) object;
        if ((this.idProduto == null && other.idProduto != null) || (this.idProduto != null && !this.idProduto.equals(other.idProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Produtos[ idProduto=" + idProduto + " ]";
    }
    
}
