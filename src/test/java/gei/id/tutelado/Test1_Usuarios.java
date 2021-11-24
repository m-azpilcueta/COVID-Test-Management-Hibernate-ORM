package gei.id.tutelado;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.UsuarioDao;
import gei.id.tutelado.dao.UsuarioDaoJPA;
import gei.id.tutelado.model.Paciente;
import gei.id.tutelado.model.Sanitario;

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

        //CREA p1, p2
        productorDatos.creaPacientesSueltos();
        //CREA s1, s2
        productorDatos.creaSanitariosSueltos();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de novos usuarios (pacientes e sanitarios) (sen probas asignadas ós pacientes)\n");

        // Situación de partida:
        // p0 transitorio
        Assert.assertNull(productorDatos.p0.getId());
        usuDao.almacena(productorDatos.p0);
        Assert.assertNotNull(productorDatos.p0.getId());

        // Situación de partida:
        // p1 transitorio
        Assert.assertNull(productorDatos.p1.getId());
        usuDao.almacena(productorDatos.p1);
        Assert.assertNotNull(productorDatos.p1.getId());
        
        // Situación de partida:
        // s0 transitorio
        Assert.assertNull(productorDatos.s0.getId());
        usuDao.almacena(productorDatos.s0);
        Assert.assertNotNull(productorDatos.s0.getId());

        // Situación de partida:
        // s1 transitorio
        Assert.assertNull(productorDatos.s1.getId());
        usuDao.almacena(productorDatos.s1);
        Assert.assertNotNull(productorDatos.s1.getId());
    }
    
    @Test
    public void t2_CRUD_TestRecupera() {
    	
    	Paciente p, inex;
    	Sanitario s;
    	
    	log.info("");
        log.info("Configurando situación de partida do test ----------------------------------------------------------------------");
        
        //CREA p1, p2
        productorDatos.creaPacientesSueltos();
        //CREA s1, s2
        productorDatos.creaSanitariosSueltos();
        
        productorDatos.registraUsuarios();
        
        log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de recuperación desde a BD de pacientes (sen entradas asociadas) e sanitarios por dni\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Recuperación por dni existente\n"
    			+ "\t\t\t\t b) Recuperacion por dni inexistente\n");
    	
    	
    	log.info("Probando Dni existente (Paciente) --------------------------------------------------------------------------------------------------");
    	
    	// Situación de partida:
    	// p0 desligado
    	p = (Paciente) usuDao.recuperaPorDni(productorDatos.p0.getDni());
    	Assert.assertEquals(productorDatos.p0.getDni(),      p.getDni());
    	Assert.assertEquals(productorDatos.p0.getNombre(),     p.getNombre());
    	Assert.assertEquals(productorDatos.p0.getFechaNac(), p.getFechaNac());
    	
    	log.info("Probando Dni existente (Sanitario) --------------------------------------------------------------------------------------------------");

    	// Situación de partida:
    	// s0 desligado
    	s = (Sanitario) usuDao.recuperaPorDni(productorDatos.s0.getDni());
    	Assert.assertEquals(productorDatos.s0.getDni(),      s.getDni());
    	Assert.assertEquals(productorDatos.s0.getNombre(),     s.getNombre());
    	Assert.assertEquals(productorDatos.s0.getFechaNac(), s.getFechaNac());

    	log.info("Probando Dni inexistente --------------------------------------------------------------------------------------------------");
    	inex = (Paciente) usuDao.recuperaPorDni("noexiste");
    	Assert.assertNull(inex);
    }
}
