package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class EntityTest {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    @Test
    public void testHibernateSaveAllAtOnce() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        // Create entities
        Parent parent = new Parent();
        Child child1 = new Child();
        child1.setParent(parent);
        Child child2 = new Child();
        child2.setParent(parent);

        parent.addChild(child1);
        parent.addChild(child2);

        // Persist entities
        entityManager.persist(parent);

        entityManager.getTransaction().commit();
        entityManager.close();

        assertNotNull(parent.getId());
        assertNotNull(child1.getId());
        assertNotNull(child2.getId());
    }

    @Test
    public void testHibernateSaveStaged() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        // Create entities
        Parent parent = new Parent();
        Child child1 = new Child();
        Child child2 = new Child();

        // Persist entities
        entityManager.persist(parent);

        parent.addChild(child1);
        parent.addChild(child2);
        child1.setParent(parent);
        child2.setParent(parent);
        parent.setToggle(true);

        entityManager.merge(parent);

        entityManager.getTransaction().commit();
        entityManager.close();

        assertNotNull(parent.getId());
        assertNotNull(child1.getId());
        assertNotNull(child2.getId());
    }

}
