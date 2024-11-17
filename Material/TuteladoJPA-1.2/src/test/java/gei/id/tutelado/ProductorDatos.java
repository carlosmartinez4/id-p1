package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ProductorDatos {

    private EntityManagerFactory emf=null;

    public Brigada brigada1, brigada2;
    public List<Brigada> listaBrigadas;

    public Bombero bombero1, bombero2;
    public List<Bombero> listaBomberos;

    public Incendio incendio1, incendio2;
    public List<Incendio> listaIncendios;

    public Rescate rescate1, rescate2;
    public List<Rescate> listaRescates;

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

    public void crearIncendiosSueltos(){
        this.incendio1 = new Incendio();
        this.incendio1.setCodigo("I001");
        this.incendio1.setNivelGravedad("Alto");
        this.incendio1.setFechaInicio(LocalDate.now().minusDays(2));
        this.incendio1.setFechaFin(LocalDate.now());
        this.incendio1.setSupQuemada(1000);
        this.incendio1.setLocalidadesAfectadas(new HashSet<>(List.of("Arteixo", "A Coruna")));

        this.incendio2 = new Incendio();
        this.incendio2.setCodigo("I002");
        this.incendio2.setNivelGravedad("Medio");
        this.incendio2.setFechaInicio(LocalDate.now().minusDays(1));
        this.incendio2.setFechaFin(LocalDate.now());
        this.incendio2.setSupQuemada(500);
        this.incendio2.setLocalidadesAfectadas(new HashSet<>(List.of("Carballo", "A Laracha")));


        this.listaIncendios = new ArrayList<>();
        this.listaIncendios.add(0, incendio1);
        this.listaIncendios.add(1, incendio2);
    }

    public void crearRescatesSueltos(){
        this.rescate1 = new Rescate();
        this.rescate1.setCodigo("R001");
        this.rescate1.setNivelGravedad("Bajo");
        this.rescate1.setFechaInicio(LocalDate.now().minusDays(2));
        this.rescate1.setFechaFin(LocalDate.now());
        this.rescate1.setObjetivo("Gato en un arbol");
        this.rescate1.setEstado("Rescatado Vivo");
        this.rescate1.setLocalidad("Arteixo");

        this.rescate2 = new Rescate();
        this.rescate2.setCodigo("R002");
        this.rescate2.setNivelGravedad("Medio");
        this.rescate2.setFechaInicio(LocalDate.now().minusDays(1));
        this.rescate2.setFechaFin(LocalDate.now());
        this.rescate2.setObjetivo("Persona atrapada en un coche");
        this.rescate2.setEstado("Rescatado Herido Grave");
        this.rescate2.setLocalidad("Carballo");

        this.listaRescates = new ArrayList<>();
        this.listaRescates.add(0, rescate1);
        this.listaRescates.add(1, rescate2);
    }

    public void crearBrigadasConBomberos(){
        this.crearBrigadasSueltas();
        this.crearBomberosSueltos();

        this.brigada1.addBombero(this.bombero1);
        this.brigada2.addBombero(this.bombero2);

        this.bombero1.setBrigada(brigada1);
        this.bombero2.setBrigada(brigada2);

    }

    public void crearBrigadasConIncendioYBomberos(){
        this.crearBomberosSueltos();
        this.crearBrigadasConBomberos();
        this.crearIncendiosSueltos();
        //this.crearRescatesSueltos();

        this.brigada1.addBombero(this.bombero1);
        this.brigada2.addBombero(this.bombero2);

        this.bombero1.setBrigada(brigada1);
        this.bombero2.setBrigada(brigada2);

        this.brigada1.addIncendio(this.incendio1);
        this.incendio1.addBrigada(this.brigada1);

    }

    public void guardaBomberosConBrigadas() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Iterator<Bombero> itB = this.listaBomberos.iterator();
            while (itB.hasNext()) {
                Bombero b = itB.next();
                em.persist(b);
                // DESCOMENTAR SE A PROPAGACION DO PERSIST NON ESTA ACTIVADA

				Iterator<Bombero> itBb = brigada1.getBomberos().iterator();

				while (itBb.hasNext()) {
					em.persist(itBb.next());
				}

                Iterator<Bombero> itBb2 = brigada2.getBomberos().iterator();

                while (itBb2.hasNext()) {
                    em.persist(itBb2.next());
                }

            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (e);
            }
        }
    }

    public void guardaDatos(){
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            if(listaBrigadas != null){
                for (Brigada b : listaBrigadas) {
                    em.persist(b);
                }
            }

            if (listaBomberos != null) {
                for (Bombero b : listaBomberos) {
                    em.persist(b);
                }
            }

            if (listaIncendios != null) {
                for (Incendio i : listaIncendios) {
                    em.persist(i);
                }
            }

            if (listaRescates != null) {
                for (Rescate r : listaRescates) {
                    em.persist(r);
                }
            }

            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (e);
            }
        }
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

    public void guardaIncendios() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Iterator<Incendio> itI = this.listaIncendios.iterator();
            while (itI.hasNext()) {
                Incendio i = itI.next();
                em.persist(i);
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
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (e);
            }
        }
    }

    public void guardaRescates() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Iterator<Rescate> itR = this.listaRescates.iterator();
            while (itR.hasNext()) {
                Rescate r = itR.next();
                em.persist(r);
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
            if (em != null && em.isOpen()) {
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

            Iterator<Brigada> itB = em.createQuery("SELECT b from Brigada b", Brigada.class).getResultList().iterator();
            while (itB.hasNext())
                em.remove(itB.next());

            Iterator<Bombero> itBb = em.createQuery("SELECT b from Bombero b", Bombero.class).getResultList().iterator();
            while (itBb.hasNext())
                em.remove(itBb.next());

            Iterator<Intervencion> itI = em.createQuery("SELECT i from Intervencion i", Intervencion.class).getResultList().iterator();
            while (itI.hasNext())
                em.remove(itI.next());

            Iterator<Incendio> itId = em.createQuery("SELECT i from Incendio i", Incendio.class).getResultList().iterator();
            while (itId.hasNext())
                em.remove(itId.next());

            Iterator<Rescate> itR = em.createQuery("SELECT r from Rescate r", Rescate.class).getResultList().iterator();
            while (itR.hasNext())
                em.remove(itR.next());

			/*
			// Non é necesario porque establecemos  propagacion do remove
			// Se desactivamos propagación, descomentar
			Iterator <EntradaLog> itL = em.createQuery("SELECT e from EntradaLog e", EntradaLog.class).getResultList().iterator();
			while (itL.hasNext()) em.remove(itL.next());
			*/

            em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nome_id='idBombero'" ).executeUpdate();
            em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nome_id='idBrigada'" ).executeUpdate();
            em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nome_id='idIntervencion'" ).executeUpdate();
            em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nome_id='idLocalidad'" ).executeUpdate();


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
