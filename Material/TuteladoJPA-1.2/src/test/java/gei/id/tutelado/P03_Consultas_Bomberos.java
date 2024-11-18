package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.BomberoDao;
import gei.id.tutelado.dao.BrigadaDao;
import gei.id.tutelado.dao.BrigadaDaoJPA;
import gei.id.tutelado.dao.IntervencionDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

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


}
