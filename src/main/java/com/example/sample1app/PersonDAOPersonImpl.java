package com.example.sample1app;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;


@Repository
public class PersonDAOPersonImpl implements PersonDAO<Person> {
    private static final long serialVersionUID = 1L;
    
    @PersistenceContext
    private EntityManager entityManager;

    // Constructor

    public PersonDAOPersonImpl() {
        super();
    }

    @Override
    public List<Person> getAll() {
        // getAll from Person ( select * from Person )
        Query query = entityManager.createQuery("from Person");
        List<Person> list = query.getResultList();
        entityManager.close();
        return list;
    } 

    @Override
    public Person findById(long id) {
        return (Person)entityManager.createQuery("from Person where id = " + id).getSingleResult();
        
    }

    @Override
    public List<Person> findByName(String name) {
        
        return entityManager
        .createQuery("from Person p where p.name = :name", Person.class)
        .setParameter("name", name)  
        .getResultList();
    }

    @Override 
    public List<Person> find(String fstr) {
        List<Person> list = null;
        String qstr = "from Person where id = :fstr";
        Query query = entityManager.createQuery(qstr)
            // queryを作成 + パラメータをセット
            .setParameter("fstr", Long.parseLong(fstr));
        list = query.getResultList();
        return list;
    }


    
}
