package gei.id.tutelado;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Paciente;
import gei.id.tutelado.model.Prueba;
import gei.id.tutelado.model.Sanitario;

public class ProductorDatosPrueba {

	// Crea un conxunto de obxectos para utilizar nos casos de proba
	
	private EntityManagerFactory emf=null;

	public Paciente p0, p1;
	public Sanitario s0, s1;
	public Prueba pru0, pru1;
	
	public List<Paciente> lP;
	public List<Sanitario> lS;
	public List<Prueba> lPru;

	public void Setup (Configuracion config) {
		this.emf=(EntityManagerFactory) config.get("EMF");
	}
	
	public void creaPacientesSueltos() {
		
		// Crea dous pacientes EN MEMORIA: p0, p1
		// SEN entradas de log
		
		this.p0 = new Paciente();
        this.p0.setNombre("Juan");
		this.p0.setApellidos("Martinez Lopez");
		this.p0.setFechaNac(LocalDate.of(1999,10,12));
		this.p0.setDni("000000000");
		this.p0.setSexo('H');
		this.p0.setCalle("Rua da Concha");
		this.p0.setLocalidad("Vilagarcia de Arousa");
		this.p0.setProvincia("Pontevedra");
		this.p0.setTelefono("674456444");
		this.p0.setNss("1234567890");

		this.p1 = new Paciente();
		this.p1.setNombre("Marta");
		this.p1.setApellidos("Carregal Jimenez");
		this.p1.setFechaNac(LocalDate.of(1996,5,23));
		this.p1.setDni("000000001");
		this.p1.setSexo('M');
		this.p1.setCalle("Ronda de Nelle");
		this.p1.setLocalidad("A Coruña");
		this.p1.setProvincia("A Coruña");
		this.p1.setTelefono("654564333");
		this.p1.setNss("0987654321");
		
		this.lP = new ArrayList<Paciente>();
		this.lP.add(p0);
		this.lP.add(p1);
	}

	public void creaSanitariosSueltos() {
		// Crea dous sanitarios EN MEMORIA: s0, s1
		// SEN entradas de log

		this.s0 = new Sanitario();
		this.s0.setNombre("Bruno");
		this.s0.setApellidos("Garcia Diaz");
		this.s0.setFechaNac(LocalDate.of(1960,10,30));
		this.s0.setDni("000000002");
		this.s0.setSexo('H');
		this.s0.setCalle("Calle Principe");
		this.s0.setLocalidad("Vigo");
		this.s0.setProvincia("Pontevedra");
		this.s0.setTelefono("666888999");
		this.s0.setCodSanitario("00000001");
		this.s0.setCentro("Hospital Provincial de Pontevedra");

		this.s1 = new Sanitario();
		this.s1.setNombre("Maria");
		this.s1.setApellidos("Ramos Lopez");
		this.s1.setFechaNac(LocalDate.of(1978,1,5));
		this.s1.setDni("000000003");
		this.s1.setSexo('M');
		this.s1.setCalle("Calle Castelao");
		this.s1.setLocalidad("Vilagarcia de Arousa");
		this.s1.setProvincia("Pontevedra");
		this.s1.setTelefono("647589345");
		this.s1.setCodSanitario("00000002");
		this.s1.setCentro("Hospital do Salnes");
		
		this.lS = new ArrayList<Sanitario>();
		this.lS.add(s0);
		this.lS.add(s1);
	}
	
	public void creaPruebasSueltas () {
		// Crea pruebas EN MEMORIA: pru0, pru1

		this.pru0.setCodPrueba("0001");
		this.pru0.setTipo("PCR");
		this.pru0.setFecha(LocalDateTime.of(2021, 12, 9, 17, 30));
		this.pru0.setLugar("Hospital do Salnes");
		this.pru0.setLocalidad("Vilagarcia de Arousa");
		this.pru0.setProvincia("Pontevedra");

		this.pru1.setCodPrueba("0002");
		this.pru1.setTipo("PCR");
		this.pru1.setFecha(LocalDateTime.of(2021, 12, 6, 12, 15));
		this.pru1.setLugar("CHUAC");
		this.pru1.setLocalidad("A Coruña");
		this.pru1.setProvincia("A Coruña");
		
		this.lPru = new ArrayList<Prueba>();
		this.lPru.add(pru0);
		this.lPru.add(pru1);
	}

	public void creaPacienteConPruebas(){

		this.creaPacientesSueltos();
		this.creaPruebasSueltas();
		this.creaSanitariosSueltos();

		this.p0.addPrueba(this.pru0);
		this.p1.addPrueba(this.pru1);

	}

	public void registraUsuarios() {
		EntityManager em=null;

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Iterator<Paciente> itP = this.lP.iterator();

			while (itP.hasNext()) {
				Paciente p = itP.next();
				em.persist(p);
				Iterator<Prueba> itPR = p.getPruebas().iterator();
				while (itPR.hasNext()) {
					em.persist(itPR.next());
				}
			}

			Iterator<Sanitario> itS = this.lS.iterator();

			while (itS.hasNext()) {
				Sanitario s = itS.next();
				em.persist(s);
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
	public void limpiaBD () {
		EntityManager em=null;
		List<Prueba> pruebas = null;

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			pruebas = em.createQuery("from Prueba", Prueba.class).getResultList();
			if (pruebas.size() > 0) {
				Iterator<Prueba> itP = pruebas.iterator();
				Prueba p = itP.next();
				p.getSintomas().clear();
				em.remove(p);
			}
			else em.createQuery("DELETE FROM Prueba").executeUpdate();
			em.createQuery("DELETE FROM Sanitario").executeUpdate();
			em.createQuery("DELETE FROM Paciente").executeUpdate();

			em.createNativeQuery("UPDATE tabla_ids SET ultimo_valor_id=0 WHERE nombre_id='idUser'" ).executeUpdate();
			em.createNativeQuery("UPDATE tabla_ids SET ultimo_valor_id=0 WHERE nombre_id='idPrueba'" ).executeUpdate();

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
