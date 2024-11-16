package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Incendio;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class IncendioDaoJPA implements IncendioDao{
    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public Incendio recuperaPorCodigo(String codigo) {
        List<Incendio> incendios = new ArrayList<>();

        try{
            em = emf.createEntityManager();
            em.getTransaction().begin();

            incendios = em.createNamedQuery("Incendio.recuperaPorCodigo", Incendio.class).setParameter("codigo", codigo).getResultList();

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return (incendios.isEmpty()?null:incendios.get(0));
    }

    @Override
    public Incendio almacena(Incendio incendio) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(incendio);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return incendio;
    }

    @Override
    public void elimina(Incendio incendio) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Incendio incendioTmp = em.find(Incendio.class, incendio.getCodigo());
            em.remove(incendio);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
    }

    @Override
    public Incendio modifica(Incendio incendio) {
        try{
            em = emf.createEntityManager();
            em.getTransaction().begin();

            incendio = em.merge(incendio);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return incendio;
    }
}
