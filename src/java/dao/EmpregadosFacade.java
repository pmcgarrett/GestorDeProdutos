/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Empregados;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class EmpregadosFacade extends AbstractFacade<Empregados> {

    @PersistenceContext(unitName = "Aplicacao_gestorDeProdutosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpregadosFacade() {
        super(Empregados.class);
    }

    public Empregados autenticar(String userName, String password) {

        Query query = em.createQuery("SELECT e FROM Empregados e WHERE e.userName = :userName AND e.password = :password");
        query.setParameter("userName", userName).setParameter("password", password);
        try {
            Empregados a = (Empregados) query.getSingleResult();
            return a;

        } catch (NoResultException e) {
            return null;
        }

    }
    
    //Devolve uma lista de empregados sem o admin
    public List<Empregados> getTodosEmpregadosMenosAdmin() {

        String query = "SELECT e FROM Empregados e WHERE NOT e.idEmpregado = 1";
        TypedQuery<Empregados> typedQuery = em.createQuery(query, Empregados.class);

        List<Empregados> list = typedQuery.getResultList();
        return list;
    }
    
        public boolean verSeUserNameJaExiste(String userName) {

        String query = "SELECT e FROM Empregados e WHERE e.userName = :userName";
        TypedQuery<Empregados> typedQuery = em.createQuery(query, Empregados.class).setParameter("userName", userName);

         try {

            Empregados resultado = typedQuery.getSingleResult();
            return true;

        } catch (NoResultException e) {
            return false;
        }
    }

}
