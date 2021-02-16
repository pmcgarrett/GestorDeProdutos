package entities;

import entities.Produtos;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-11T21:50:12")
@StaticMetamodel(Empregados.class)
public class Empregados_ { 

    public static volatile SingularAttribute<Empregados, String> nomeDoEmpregado;
    public static volatile SingularAttribute<Empregados, String> password;
    public static volatile SingularAttribute<Empregados, Integer> idEmpregado;
    public static volatile ListAttribute<Empregados, Produtos> produtosList;
    public static volatile SingularAttribute<Empregados, String> userName;

}