package model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BBDDConnect {
    protected EntityManagerFactory emf;
    protected EntityManager em;

    public BBDDConnect(){
        emf= Persistence.createEntityManagerFactory("damPersistence");
        em = emf.createEntityManager();
    }

}
