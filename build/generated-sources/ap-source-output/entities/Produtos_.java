package entities;

import entities.Categoria;
import entities.Empregados;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-11T21:50:12")
@StaticMetamodel(Produtos.class)
public class Produtos_ { 

    public static volatile SingularAttribute<Produtos, Integer> idProduto;
    public static volatile SingularAttribute<Produtos, String> nomeDoProduto;
    public static volatile SingularAttribute<Produtos, Empregados> empregadosidEmpregado;
    public static volatile ListAttribute<Produtos, Categoria> categoriaList;

}