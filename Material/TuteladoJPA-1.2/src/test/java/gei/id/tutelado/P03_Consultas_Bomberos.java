package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.*;
import gei.id.tutelado.model.Bombero;
import gei.id.tutelado.model.Brigada;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

import java.util.List;

public class P03_Consultas_Bomberos {
    private Logger log = LogManager.getLogger("gei.id.tutelado");
    private static ProductorDatos productorDatos = new ProductorDatos();

    private static Configuracion cfg;
    private static BrigadaDao brigadaDao;
    private static BomberoDao bomberoDao;
    private static IntervencionDao intervencionDao;

    @BeforeClass
    public static void init() throws Exception {
        cfg = new ConfiguracionJPA();
        cfg.start();

        brigadaDao = new BrigadaDaoJPA();
        brigadaDao.setup(cfg);

        bomberoDao = new BomberoDaoJPA();
        brigadaDao.setup(cfg);

        intervencionDao = new IntervencionDaoJPA();
        intervencionDao.setup(cfg);

        productorDatos.Setup(cfg);
    }

    @AfterClass
    public static void endclose() throws Exception {
        cfg.endUp();
    }

    @Before
    public void setUp() throws Exception {
        log.info("");
        log.info("Limpando BD --------------------------------------------------------------------------------------------");
        productorDatos.limpaBD();
    }

    @After
    public void tearDown() throws Exception {
    }

@Test
public void testBuscarBomberosPorBrigada() {
    List<Bombero> bomberos;

    log.info("Configurando situación de partida para testBuscarBomberosPorBrigada...");
    productorDatos.crearBrigadasConBomberos();
    productorDatos.guardaBrigadas();

    log.info("Inicio del test: buscarBomberosPorBrigada");

    bomberos = bomberoDao.buscarBomberosPorBrigada(productorDatos.brigada1);
    Assert.assertNotNull(bomberos);
    Assert.assertEquals(2, bomberos.size()); // Supongamos que brigada1 tiene 2 bomberos
    Assert.assertTrue(bomberos.contains(productorDatos.bombero1));
    Assert.assertTrue(bomberos.contains(productorDatos.bombero2));

    bomberos = bomberoDao.buscarBomberosPorBrigada(productorDatos.brigada2);
    Assert.assertNotNull(bomberos);
    Assert.assertEquals(0, bomberos.size());
}
    @Test
    public void testBuscarBomberosPorIntervencion() {
        List<Bombero> bomberos;

        log.info("Configurando situación de partida para testBuscarBomberosPorIntervencion...");
        productorDatos.crearBrigadasConIncendioYRescatesYBomberos();
        productorDatos.guardaBrigadas();

        log.info("Inicio del test: buscarBomberosPorIntervencion");

        bomberos = bomberoDao.buscarBomberosPorIntervencion(productorDatos.incendio1);
        Assert.assertNotNull(bomberos);
        Assert.assertEquals(1, bomberos.size());
        Assert.assertTrue(bomberos.contains(productorDatos.bombero1));

        bomberos = bomberoDao.buscarBomberosPorIntervencion(productorDatos.incendio2);
        Assert.assertNull(bomberos);
        Assert.assertEquals(0, bomberos.size());
    }

    @Test
    public void testCountRescatePorLocalidad() {
        int count;

        log.info("Configurando situación de partida para testCountRescatePorLocalidad...");
        productorDatos.crearBrigadasConIncendioYRescatesYBomberos();
        productorDatos.guardaBrigadas();

        log.info("Inicio del test: countRescatePorLocalidad");


        count = intervencionDao.countRescatePorLocalidad("Arteixo");
        Assert.assertEquals(1, count);


        count = intervencionDao.countRescatePorLocalidad("Pazos");
        Assert.assertEquals(0, count);
    }

    @Test
    public void testBuscarBrigadasPorRescatesEnUnaLocalidad() {
        List<Brigada> brigadas;

        log.info("Configurando situación de partida para testBuscarBrigadasPorRescatesEnUnaLocalidad...");
        productorDatos.crearBrigadasConIncendioYRescatesYBomberos();
        productorDatos.guardaBrigadas();

        log.info("Inicio del test: buscarBrigadasPorRescatesEnUnaLocalidad");


        brigadas = brigadaDao.buscarBrigadasPorRescatesEnUnaLocalidad("Arteixo");
        Assert.assertNotNull(brigadas);
        Assert.assertEquals(1, brigadas.size());
        Assert.assertTrue(brigadas.contains(productorDatos.brigada1));
        Assert.assertTrue(brigadas.contains(productorDatos.brigada2));


        brigadas = brigadaDao.buscarBrigadasPorRescatesEnUnaLocalidad("Carballo");
        Assert.assertNotNull(brigadas);
        Assert.assertEquals(0, brigadas.size());
    }
}
