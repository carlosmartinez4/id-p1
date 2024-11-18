package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;

import gei.id.tutelado.model.Intervencion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;


public class IntervencionDaoJPA implements IntervencionDao {

    private EntityManagerFactory emf;
    private EntityManager em;


    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public Intervencion buscarPorCodigo(String codigo) {
        List<Intervencion> intervenciones = new ArrayList<>();

        try{
            em = emf.createEntityManager();
            em.getTransaction().begin();

            intervenciones = em.createNamedQuery("Intervencion.recuperaPorCodigo", Intervencion.class).
                    setParameter("codigo", codigo).getResultList();

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return (intervenciones.isEmpty()?null:intervenciones.get(0));
    }

    @Override
    public Intervencion almacena(Intervencion intervencion) {
        try{
            em = emf.createEntityManager();
            em.getTransaction().begin();


            em.persist(intervencion);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return intervencion;
    }

    @Override
    public void elimina(Intervencion intervencion) {
        try{
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Intervencion i = em.find(Intervencion.class, intervencion.getId());
            em.remove(i);

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
    public Intervencion modifica(Intervencion intervencion) {
        try{
            em = emf.createEntityManager();
            em.getTransaction().begin();

            intervencion = em.merge(intervencion);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return intervencion;
    }

    @Override
    public int countRescatePorLocalidad(String localidad) {
        EntityManager em = null;
        int count = 0;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            // Usamos la consulta correcta
            Long result = em.createQuery(
                            "SELECT COUNT(i) FROM Rescate i WHERE i.localidad = :localidad", Long.class)
                    .setParameter("localidad", localidad)
                    .getSingleResult();

            count = result.intValue();

            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return count;
    }
}
