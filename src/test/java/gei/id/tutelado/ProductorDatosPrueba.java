package gei.id.tutelado;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.*;

import gei.id.tutelado.model.Prueba;
import gei.id.tutelado.model.Usuario;
import gei.id.tutelado.model.Paciente;
import gei.id.tutelado.model.Sanitario;

public class ProductorDatosPrueba {

	// Crea un conxunto de obxectos para utilizar nos casos de proba
	
	private EntityManagerFactory emf=null;

	public Paciente u0, u1;
	public Sanitario u2, u3;
	public Prueba p0, p1;
	public List<Paciente> l1;
	public List<Sanitario> l2;

	public void Setup (Configuracion config) {
		this.emf=(EntityManagerFactory) config.get("EMF");
	}
	
	public void creaPacientesSueltos() {
		// Crea dous usuarios EN MEMORIA: u0, u1
		// SEN entradas de log
		
		this.u0 = new Paciente();
        this.u0.setNombre("Juan");
		this.u0.setApellidos("Martinez Lopez");
		this.u0.setFechaNac(LocalDate.of(1999,10,12));
		this.u0.setDni("000000000");
		this.u0.setSexo('H');
		this.u0.setCalle("Rua da Concha");
		this.u0.setLocalidad("Vilagarcia de Arousa");
		this.u0.setProvincia("Pontevedra");
		this.u0.setTelefono("674456444");
		this.u0.setNss("1234567890");

		this.u1 = new Paciente();
		this.u1.setNombre("Marta");
		this.u1.setApellidos("Carregal Jimenez");
		this.u1.setFechaNac(LocalDate.of(1996,5,23));
		this.u1.setDni("000000001");
		this.u1.setSexo('M');
		this.u1.setCalle("Ronda de Nelle");
		this.u1.setLocalidad("A Coruña");
		this.u1.setProvincia("A Coruña");
		this.u1.setTelefono("654564333");
		this.u1.setNss("0987654321");
	}

	public void creaSanitariosSueltos() {
		// Crea dous usuarios EN MEMORIA: u0, u1
		// SEN entradas de log

		this.u2 = new Sanitario();
		this.u2.setNombre("Bruno");
		this.u2.setApellidos("Garcia Diaz");
		this.u2.setFechaNac(LocalDate.of(1960,10,30));
		this.u2.setDni("000000002");
		this.u2.setSexo('H');
		this.u2.setCalle("Calle Principe");
		this.u2.setLocalidad("Vigo");
		this.u2.setProvincia("Pontevedra");
		this.u2.setTelefono("666888999");
		this.u2.setCentro("Hospital Provincial de Pontevedra");

		this.u3 = new Sanitario();
		this.u3.setNombre("Maria");
		this.u3.setApellidos("Ramos Lopez");
		this.u3.setFechaNac(LocalDate.of(1978,1,5));
		this.u3.setDni("000000003");
		this.u3.setSexo('M');
		this.u3.setCalle("Calle Castelao");
		this.u3.setLocalidad("Vilagarcia de Arousa");
		this.u3.setProvincia("Pontevedra");
		this.u3.setTelefono("647589345");
		this.u3.setCentro("Hospital do Salnes");

	}
	
	public void creaPruebasSueltas () {
		// Crea pruebas EN MEMORIA: p0, p1

		this.p0.setCodPrueba("0001");
		this.p0.setTipo("PCR");
		this.p0.setFecha(LocalDateTime.of(2021, 12, 9, 17, 30));
		this.p0.setLugar("Hospital do Salnes");
		this.p0.setLocalidad("Vilagarcia de Arousa");
		this.p0.setProvincia("Pontevedra");

		this.p1.setCodPrueba("0002");
		this.p1.setTipo("PCR");
		this.p1.setFecha(LocalDateTime.of(2021, 12, 6, 12, 15));
		this.p1.setLugar("CHUAC");
		this.p1.setLocalidad("A Coruña");
		this.p1.setProvincia("A Coruña");
	}

	public void creaPacienteConPruebas(){

		this.creaPacientesSueltos();
		this.creaPruebasSueltas();
		this.creaSanitariosSueltos();

		this.u0.addPrueba(p0);
		this.u1.addPrueba(p1);

	}

	public void grabaUsuarios() {
		EntityManager em=null;

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Iterator<Paciente> itP = this.l1.iterator();

			while (itP.hasNext()) {
				Paciente p = itP.next();
				em.persist(p);
				Iterator<Prueba> itPR = p.getPruebas().iterator();
				while (itPR.hasNext()) {
					em.persist(itPR.next());
				}
			}

			Iterator<Sanitario> itS = this.l2.iterator();

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

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			em.createQuery("DELETE FROM Prueba").executeUpdate();
			em.createQuery("DELETE FROM Sanitario").executeUpdate();
			em.createQuery("DELETE FROM Paciente").executeUpdate();

			em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nombre_id='idUser'" ).executeUpdate();
			em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nombre_id='idPrueba'" ).executeUpdate();

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
