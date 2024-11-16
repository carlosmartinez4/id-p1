package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Rescate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class RescateDaoJPA implements RescateDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public Rescate recuperaPorCodigo(String codigo) {
        List<Rescate> rescates = new ArrayList<>();

        try{
            em = emf.createEntityManager();
            em.getTransaction().begin();

            rescates = em.createNamedQuery("Rescate.recuperaPorCodigo", Rescate.class).setParameter("codigo", codigo).getResultList();

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return (rescates.isEmpty()?null:rescates.get(0));
    }

    @Override
    public Rescate almacena(Rescate rescate) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(rescate);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return rescate;
    }

    @Override
    public void elimina(Rescate rescate) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Rescate rescateTmp = em.find(Rescate.class, rescate.getCodigo());
            em.remove(rescateTmp);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
    }

    @Override
    public Rescate modifica(Rescate rescate) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.merge(rescate);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return rescate;
    }
}
