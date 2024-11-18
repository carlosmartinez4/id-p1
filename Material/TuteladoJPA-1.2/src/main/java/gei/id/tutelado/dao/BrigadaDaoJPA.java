package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Brigada;
import org.hibernate.LazyInitializationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class BrigadaDaoJPA implements BrigadaDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup (Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    /* MO4.1 */
    @Override
    public Brigada recuperaPorNombre(String nombre) {
        List<Brigada> brigadas = new ArrayList<>();

        try{
            em = emf.createEntityManager();
            em.getTransaction().begin();

            brigadas = em.createNamedQuery("Brigada.recuperaPorNombre", Brigada.class).setParameter("nombre", nombre).getResultList();

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return (brigadas.isEmpty()?null:brigadas.get(0));
    }

    /* MO4.2 */
    @Override
    public Brigada almacena(Brigada brigada) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(brigada);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return brigada;
    }

    /* MO4.3 */
    @Override
    public void elimina(Brigada brigada) {
        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            Brigada brigadaTmp = em.find (Brigada.class, brigada.getId());
            em.remove (brigadaTmp);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
    }

    /* MO4.4 */
    @Override
    public Brigada modifica(Brigada brigada) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            brigada = em.merge(brigada);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return brigada;
    }

    /* MO4.5 */
    @Override
    public Brigada restauraBomberos(Brigada brigada) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            try{
                brigada.getBomberos().size();
            } catch (Exception ex2 ){
                if (ex2 instanceof LazyInitializationException){
                    brigada = em.merge(brigada);
                    brigada.getBomberos().size();
                } else{
                    throw(ex2);
                }
            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return brigada;
    }

    /* MO4.6.c */
    @Override
    public List<Brigada> buscarBrigadasPorRescatesEnUnaLocalidad(String localidad) {
        List<Brigada> brigadas = new ArrayList<>();
        try{
            em = emf.createEntityManager();
            em.getTransaction().begin();

            brigadas = em.createQuery("SELECT b FROM Brigada b "+
                    "WHERE b.localidad = "+
                    "(SELECT r.localidad FROM Rescate r WHERE r.localidad = :loc)",
                    Brigada.class).setParameter("loc", localidad).getResultList();

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex){
            if (em!=null && em.isOpen()){
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return brigadas;
    }
}
