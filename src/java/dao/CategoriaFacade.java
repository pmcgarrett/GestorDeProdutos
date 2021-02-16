package dao;

import entities.Categoria;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class CategoriaFacade extends AbstractFacade<Categoria> {

    @PersistenceContext(unitName = "Aplicacao_gestorDeProdutosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaFacade() {
        super(Categoria.class);
    }

    public List<Categoria> getCategoriasDeUmProduto(String nomeDoProduto) {

        String query = "SELECT c FROM Categoria c JOIN c.produtosidProduto p WHERE p.nomeDoProduto = :nomeDoProduto";
        TypedQuery<Categoria> typedQuery = em.createQuery(query, Categoria.class).setParameter("nomeDoProduto", nomeDoProduto);

        List<Categoria> list = typedQuery.getResultList();
        return list;
    }

    public boolean verSeJaExisteUmaCategoriaComOMesmoNome(String nomeDaCategoria) {
        
        String query = "SELECT c FROM Categoria c WHERE c.nomeDaCategoria = :nomeDaCategoria";
        TypedQuery<Categoria> typedQuery = em.createQuery(query, Categoria.class).setParameter("nomeDaCategoria", nomeDaCategoria);

        try {

            Categoria resultado = typedQuery.getSingleResult();
            return true;

        } catch (NoResultException e) {
            return false;
        }
    }

    public List<Categoria> todasAsCategoriasDesteEmpregado(Integer idEmpregado) {
        
       String query = "SELECT c FROM Categoria c JOIN c.produtosidProduto p JOIN p.empregadosidEmpregado e WHERE e.idEmpregado = :idEmpregado";
        TypedQuery<Categoria> typedQuery = em.createQuery(query, Categoria.class).setParameter("idEmpregado", idEmpregado);

        List<Categoria> list = typedQuery.getResultList();
        return list;
    }

}
