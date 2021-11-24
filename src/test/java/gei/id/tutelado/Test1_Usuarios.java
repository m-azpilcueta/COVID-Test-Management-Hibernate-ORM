package gei.id.tutelado;

import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.dao.UsuarioDao;
import gei.id.tutelado.dao.UsuarioDaoDJPA;
import gei.id.tutelado.dao.UsuarioDaoJPA;
import gei.id.tutelado.model.Paciente;
import gei.id.tutelado.model.Usuario;

//import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test1_Usuarios {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static ProductorDatosPrueba productorDatos = new ProductorDatosPrueba();

    private static Configuracion cfg;
    private static UsuarioDao usuDao;

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

        usuDao = new UsuarioDaoJPA();
        usuDao.setup(cfg);

        productorDatos = new ProductorDatosPrueba();
        productorDatos.Setup(cfg);
    }

    @AfterClass
    public static void endclose() throws Exception {
        cfg.endUp();
    }

    @Before
    public void setUp() throws Exception {
        log.info("");
        log.info("Limpando a BD ----------------------------------------------------------------------------------------");
        productorDatos.limpiaBD();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void t1_CRUD_TestAlmacena() {

        log.info("");
        log.info("Configurando situación de partida do test ----------------------------------------------------------------------");

        //CREA u0, u1
        productorDatos.creaPacientesSueltos();
        //CREA u2, u3
        productorDatos.creaSanitariosSueltos();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de novo usuario (sen entradas de log asociadas)\n");

        // Situación de partida:
        // u0 transitorio
        Assert.assertNull(productorDatos.u0);
        usuDao.almacena(productorDatos.u0);
        Assert.assertNotNull(productorDatos.u0.getId());

        Assert.assertNull(productorDatos.u1);
        usuDao.almacena(productorDatos.u1);
        Assert.assertNotNull(productorDatos.u1.getId());

        Assert.assertNull(productorDatos.u2);
        usuDao.almacena(productorDatos.u2);
        Assert.assertNotNull(productorDatos.u2.getId());

        Assert.assertNull(productorDatos.u3);
        usuDao.almacena(productorDatos.u3);
        Assert.assertNotNull(productorDatos.u3.getId());
    }
}
