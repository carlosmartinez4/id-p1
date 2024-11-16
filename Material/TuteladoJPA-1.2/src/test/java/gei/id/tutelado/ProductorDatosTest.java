package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Brigada;
import gei.id.tutelado.model.Bombero;
import gei.id.tutelado.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductorDatosTest {
    // Crea un conxunto de obxectos para utilizar nos casos de proba

    private EntityManagerFactory emf=null;

    public Brigada brigada1, brigada2;
    public List<Brigada> listaBrigadas;

    public Bombero bombero1, bombero2;
    public List<Bombero> listaBomberos;

    public void Setup (Configuracion config) {
        this.emf=(EntityManagerFactory) config.get("EMF");
    }

    public void crearBrigadasSueltas() {
        this.brigada1 = new Brigada();
        this.brigada1.setNombre("Brigada 1");
        this.brigada1.setLocalidad("Arteixo");

        this.brigada2 = new Brigada();
        this.brigada2.setNombre("Brigada 2");
        this.brigada2.setLocalidad("Carballo");

        this.listaBrigadas = new ArrayList<>();
        this.listaBrigadas.add(0, brigada1);
        this.listaBrigadas.add(1, brigada2);
    }

    public void crearBomberosSueltos(){
        this.bombero1 = new Bombero();
        this.bombero1.setNombre("Carlos");
        this.bombero1.setNss("111111111111");
        this.bombero1.setFechaHoraAlta(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        this.bombero1.setFechaNacimiento(LocalDate.of(2003, 8, 23));

        this.bombero2 = new Bombero();
        this.bombero2.setNombre("Roberto");
        this.bombero2.setNss("222222222222");
        this.bombero2.setFechaHoraAlta(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        this.bombero2.setFechaNacimiento(LocalDate.of(2003, 10, 1));

        this.listaBomberos = new ArrayList<>();
        this.listaBomberos.add(0, bombero1);
        this.listaBomberos.add(1, bombero2);
    }

    public void crearBrigadasConBomberos(){
        this.crearBrigadasSueltas();
        this.crearBomberosSueltos();

        this.brigada1.addBombero(this.bombero1);
        this.brigada2.addBombero(this.bombero2);
    }

    public void guardaBrigadas() {
        EntityManager em=null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Iterator<Brigada> itB = this.listaBrigadas.iterator();
            while (itB.hasNext()) {
                Brigada b = itB.next();
                em.persist(b);
                // DESCOMENTAR SE A PROPAGACION DO PERSIST NON ESTA ACTIVADA
				/*
				Iterator<EntradaLog> itEL = u.getEntradasLog().iterator();
				while (itEL.hasNext()) {
					em.persist(itEL.next());
				}
				*/
            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (e);
            }
        }
    }

    public void limpaBD () {
        EntityManager em=null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Iterator <Brigada> itB = em.createQuery("SELECT b from Brigada b", Brigada.class).getResultList().iterator();
            while (itB.hasNext()) em.remove(itB.next());
			/*
			// Non é necesario porque establecemos  propagacion do remove
			// Se desactivamos propagación, descomentar
			Iterator <EntradaLog> itL = em.createQuery("SELECT e from EntradaLog e", EntradaLog.class).getResultList().iterator();
			while (itL.hasNext()) em.remove(itL.next());
			*/

            em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nome_id='idBombero'" ).executeUpdate();
            em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nome_id='idBrigada'" ).executeUpdate();
            em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nome_id='idIntervencion'" ).executeUpdate();

            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (e);
            }
        }
    }
}
