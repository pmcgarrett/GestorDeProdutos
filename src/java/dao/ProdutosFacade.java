package dao;

import entities.Produtos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.*;

@Stateless
public class ProdutosFacade extends AbstractFacade<Produtos> {

    @PersistenceContext(unitName = "Aplicacao_gestorDeProdutosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProdutosFacade() {
        super(Produtos.class);
    }

    public List<Produtos> getProdutosDeUmEmpregado(Integer idEmpregado) {

        String query = "select e from Produtos e join e.empregadosidEmpregado p where p.idEmpregado = :idEmpregado";
        TypedQuery<Produtos> typedQuery = em.createQuery(query, Produtos.class).setParameter("idEmpregado", idEmpregado);

        List<Produtos> list = typedQuery.getResultList();
        return list;
    }

    public List<Produtos> getNomeDoProdutoDeUmEmpregado(Integer idEmpregado) {

        String query = "SELECT e.nomeDoProduto FROM Produtos e JOIN e.empregadosidEmpregado p WHERE p.idEmpregado = :idEmpregado";
        TypedQuery<Produtos> typedQuery = em.createQuery(query, Produtos.class).setParameter("idEmpregado", idEmpregado);

        List<Produtos> list = typedQuery.getResultList();
        return list;
    }

    public Produtos getProdutoPeloNome(String nomeDoProduto) {

        String query = "SELECT p FROM Produtos p WHERE p.nomeDoProduto = :nomeDoProduto";
        TypedQuery<Produtos> typedQuery = em.createQuery(query, Produtos.class).setParameter("nomeDoProduto", nomeDoProduto);

        Produtos resultado = typedQuery.getSingleResult();
        return resultado;
    }

    public boolean verSeJaExisteUmProdutoComOMesmoNome(String nomeDoProduto) {

        String query = "SELECT p FROM Produtos p WHERE p.nomeDoProduto = :nomeDoProduto";
        TypedQuery<Produtos> typedQuery = em.createQuery(query, Produtos.class).setParameter("nomeDoProduto", nomeDoProduto);

        try {

            Produtos resultado = typedQuery.getSingleResult();
            return true;

        } catch (NoResultException e) {
            return false;
        }
    }
}
