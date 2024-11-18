package gei.id.tutelado.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Bombero;
import gei.id.tutelado.model.Brigada;
import gei.id.tutelado.model.Intervencion;

public class BomberoDaoJPA implements BomberoDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    /* MO4.1 */
    @Override
    public Bombero buscarPorNSS(String nss) {
        List<Bombero> bomberos = new ArrayList<>();

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            bomberos = em.createNamedQuery("Bombero.buscarPorNSS", Bombero.class)
                    .setParameter("nss", nss).getResultList();

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return (bomberos.isEmpty() ? null : bomberos.get(0));
    }

    /* MO4.2 */
    @Override
    public Bombero almacena(Bombero bombero) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(bombero);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return bombero;
    }

    /* MO4.3 */
    @Override
    public void elimina(Bombero bombero) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Bombero b = em.find(Bombero.class, bombero.getId());
            em.remove(b);

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

    /* MO4.4 */
    @Override
    public Bombero modifica(Bombero bombero) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            bombero = em.merge(bombero);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return bombero;
    }

    /* MO4.6.a */
    @Override
    public List<Bombero> buscarBomberosPorBrigada(Brigada b) {
        List <Bombero> bomberos=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            bomberos = em.createQuery("SELECT e FROM Brigada u JOIN u.bomberos e WHERE u=:b",
                    Bombero.class).setParameter("b", b).getResultList();

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }

        return bomberos;

    }

    /* MO4.6.b */
    @Override
    public List<Bombero> buscarBomberosPorIntervencion(Intervencion i) {
        List <Bombero> bomberos=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            bomberos =  em.createQuery(
                    "SELECT DISTINCT b FROM Intervencion i " +
                    "LEFT JOIN i.brigadas br " +
                    "LEFT JOIN br.bomberos b " +
                    "WHERE i = :i", Bombero.class).setParameter("i", i).getResultList();

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return bomberos;
    }

}

