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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class P02_Brigadas_Bomberos_Intervenciones {
    private Logger log = LogManager.getLogger("gei.id.tutelado");
    private static ProductorDatos productorDatos = new ProductorDatos();

    private static Configuracion cfg;
    private static BrigadaDao brigadaDao;
    private static BomberoDao bomberoDao;
    private static IntervencionDao intervencionDao;

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.info("");
            log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            log.info("Iniciando test: " + description.getMethodName());
            log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        }

        protected void finished(Description description) {
            log.info("");
            log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
            log.info("Finalizado test: " + description.getMethodName());
            log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
        }
    };

    @BeforeClass
    public static void init() throws Exception {
        cfg = new ConfiguracionJPA();
        cfg.start();

        brigadaDao = new BrigadaDaoJPA();
        brigadaDao.setup(cfg);

        bomberoDao = new BomberoDaoJPA();
        bomberoDao.setup(cfg);

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
    public void test01_RecuperacionBrigadasBomberosIncendiosRescates() {
        Brigada b;
        Bombero bb;
        Rescate r;
        Incendio i;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        productorDatos.crearBrigadasConIncendioYRescatesYBomberos();

        productorDatos.guardaDatos();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de recuperación desde a BD de brigadas (sen bombeiros nin intervencions asociadas) por nombre\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Recuperación por nombre existente\n"
                + "\t\t\t\t b) Recuperacion por nombre inexistente\n");

        // Situación de partida:
        // u0 desligado

        log.info("Probando recuperacion por nome EXISTENTE --------------------------------------------------");

        b = brigadaDao.recuperaPorNombre(productorDatos.brigada1.getNombre());
        Assert.assertEquals(productorDatos.brigada1.getNombre(), b.getNombre());
        Assert.assertEquals(productorDatos.brigada1.getLocalidad(), b.getLocalidad());

        bb = bomberoDao.buscarPorNSS(productorDatos.bombero1.getNss());
        Assert.assertEquals(productorDatos.bombero1.getNss(), bb.getNss());
        Assert.assertEquals(productorDatos.bombero1.getNombre(), bb.getNombre());

        r = (Rescate) intervencionDao.buscarPorCodigo(productorDatos.rescate1.getCodigo());
        Assert.assertEquals(productorDatos.rescate1.getCodigo(), r.getCodigo());

        i = (Incendio) intervencionDao.buscarPorCodigo(productorDatos.incendio1.getCodigo());
        Assert.assertEquals(productorDatos.incendio1.getCodigo(), i.getCodigo());

        log.info("");
        log.info("Probando recuperacion por nome INEXISTENTE -----------------------------------------------");

        b = brigadaDao.recuperaPorNombre("Brigada #");
        Assert.assertNull(b);
    }


    @Test
    public void test02_GuardarBomberos() {
        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        productorDatos.crearBomberoConBrigada();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de novo bombero\n");

        // Situación de partida
        Assert.assertNull(productorDatos.bombero1.getId());
        brigadaDao.almacena(productorDatos.brigada1);
        Assert.assertNotNull(productorDatos.bombero1.getId());
    }

    @Test
    public void test02_GuardarRescate() {
        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        productorDatos.crearRescateConBrigada();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de novo rescate\n");

        // Situación de partida
        Assert.assertNull(productorDatos.rescate1.getId());
        intervencionDao.almacena(productorDatos.rescate1);
        Assert.assertNotNull(productorDatos.rescate1.getId());
    }

    @Test
    public void test02_GuardarBomberosConIncendiosYRescatesYBomberos() {
        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        productorDatos.crearBrigadasConIncendioYRescatesYBomberos();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de novo bombero con incendios asociados\n");

        // Situación de partida
        Assert.assertNull(productorDatos.brigada1.getId());
        Assert.assertNull(productorDatos.bombero1.getId());
        Assert.assertNull(productorDatos.incendio1.getId());
        Assert.assertNull(productorDatos.rescate1.getId());

        productorDatos.guardaDatos();

        Assert.assertNotNull(productorDatos.brigada1.getId());
        Assert.assertNotNull(productorDatos.bombero1.getId());
        Assert.assertNotNull(productorDatos.incendio1.getId());
        Assert.assertNotNull(productorDatos.rescate1.getId());
    }


    @Test
    public void test03_EliminarBrigadasConBomberos() {
        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        productorDatos.crearBomberoConBrigada();
        productorDatos.guardaBrigadas();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de eliminación da BD de brigadas sen bombeiros asociadas\n");

        // Situación de partida:
        // u0 desligado

        Assert.assertNotNull(brigadaDao.recuperaPorNombre(productorDatos.brigada1.getNombre()));
        brigadaDao.elimina(productorDatos.brigada1);
        Assert.assertNull(brigadaDao.recuperaPorNombre(productorDatos.brigada1.getNombre()));
        Assert.assertNull(bomberoDao.buscarPorNSS(productorDatos.bombero1.getNss()));
    }
}
