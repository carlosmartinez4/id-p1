package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.*;
import gei.id.tutelado.model.Bombero;
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

        // Test con una brigada que tiene bomberos
        bomberos = bomberoDao.buscarBomberosPorBrigada(productorDatos.brigada1);
        Assert.assertNotNull(bomberos);
        Assert.assertEquals(2, bomberos.size()); // Supongamos que brigada1 tiene 2 bomberos
        Assert.assertTrue(bomberos.contains(productorDatos.bombero1));
        Assert.assertTrue(bomberos.contains(productorDatos.bombero2));

        // Test con una brigada que no tiene bomberos
        bomberos = bomberoDao.buscarBomberosPorBrigada(productorDatos.brigada2);
        Assert.assertNotNull(bomberos);
        Assert.assertEquals(0, bomberos.size());
    }
}
