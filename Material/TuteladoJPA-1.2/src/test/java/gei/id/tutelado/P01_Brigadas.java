package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.*;
import gei.id.tutelado.model.Bombero;
import gei.id.tutelado.model.Brigada;
import gei.id.tutelado.model.Incendio;
import gei.id.tutelado.model.Rescate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

public class P01_Brigadas {
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
    public void test01_RecuperarBrigadas() {

        Brigada b = null;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        productorDatos.crearBrigadasSueltas();
        productorDatos.guardaBrigadas();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de creación de nova brigada (sen bombeiros nin intervencions asociados)\n");

        // Situación de partida

        log.info("Probando recuperacion por nif EXISTENTE --------------------------------------------------");

        b = brigadaDao.recuperaPorNombre(productorDatos.brigada1.getNombre());

        Assert.assertEquals(productorDatos.brigada1.getNombre(), b.getNombre());

    }

    @Test
    public void test02_GuardarBrigadas(){
        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        productorDatos.crearBrigadasSueltas();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de nova brigada (sen bombeiros nin intervencions asociados)\n");

        // Situación de partida:
        // u0 transitorio

        Assert.assertNull(productorDatos.brigada1.getId());
        brigadaDao.almacena(productorDatos.brigada1);
        Assert.assertNotNull(productorDatos.brigada1.getId());
    }

    @Test
    public void test03_EliminarBrigadas() {
        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        productorDatos.crearBrigadasSueltas();
        productorDatos.guardaBrigadas();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de eliminación da BD de brigadas sen bombeiros asociadas\n");

        // Situación de partida:
        // u0 desligado

        Assert.assertNotNull(brigadaDao.recuperaPorNombre(productorDatos.brigada1.getNombre()));
        brigadaDao.elimina(productorDatos.brigada1);
        Assert.assertNull(brigadaDao.recuperaPorNombre(productorDatos.brigada1.getNombre()));
    }


    @Test
    public void test04_ModificacionBrigadas() {

        Brigada b1, b2;
        String nuevaLocalidad;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        productorDatos.crearBrigadasSueltas();
        productorDatos.guardaBrigadas();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de modificación da información básica dunha brigada sen bombeiros nin intervencions\n");

        // Situación de partida:
        // u0 desligado

        nuevaLocalidad = "A Coruna";

        b1 = brigadaDao.recuperaPorNombre(productorDatos.brigada1.getNombre());
        Assert.assertNotEquals(nuevaLocalidad, b1.getLocalidad());
        b1.setLocalidad(nuevaLocalidad);

        brigadaDao.modifica(b1);

        b2 = brigadaDao.recuperaPorNombre(productorDatos.brigada1.getNombre());
        Assert.assertEquals (nuevaLocalidad, b2.getLocalidad());
    }
}
