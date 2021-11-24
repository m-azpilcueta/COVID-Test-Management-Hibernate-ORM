package gei.id.tutelado.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.LazyInitializationException;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Prueba;

public class PruebaDaoJPA implements PruebaDao {
	private EntityManagerFactory emf; 
	private EntityManager em;

	@Override
	public void setup (Configuracion config) {
		this.emf = (EntityManagerFactory) config.get("EMF");
	}
	
	@Override
	public Prueba almacena(Prueba prueba) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			em.persist(prueba);
			
			em.getTransaction().commit();
			em.close();
		} catch (Exception ex) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return prueba;
	}
	
	@Override
	public Prueba modifica(Prueba prueba) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			em.merge(prueba);
			
			em.getTransaction().commit();
			em.close();
		} catch (Exception ex) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return (prueba);
	}
	
	@Override
	public void elimina(Prueba prueba) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Prueba pruebaTmp = em.find(Prueba.class, prueba.getId());
			if (LocalDateTime.now().isBefore(pruebaTmp.getFecha())) {
				em.remove(pruebaTmp);
			} else throw new RuntimeException("¡La prueba ya ha tenido lugar!");

			em.getTransaction().commit();
			em.close();		
		} catch (Exception ex) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		} 
	}
	
	@Override
	public Prueba recuperaPorCod(String cod) {
		List<Prueba> pruebas = null;
		
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			pruebas = em.createNamedQuery("Prueba.findByCodigo", Prueba.class).setParameter("codPrueba", cod).getResultList();
			
			em.getTransaction().commit();
			em.close();
		} catch (Exception ex) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return (pruebas.size() != 0 ? pruebas.get(0) : null);
	}
	
	@Override
	public Prueba restauraSintomas(Prueba prueba) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			try {
				prueba.getSintomas().size();
			} catch (Exception ex2) {
				if (ex2 instanceof LazyInitializationException) {
					prueba = em.merge(prueba);
					prueba.getSintomas().size();
				} else throw ex2;
			}
			em.getTransaction().commit();
			em.close();
		} catch (Exception ex) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return (prueba);
	}
	
	@Override
	public Prueba restauraPaciente(Prueba prueba) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			try {
				prueba.getPaciente().getDni();
			} catch (Exception ex2) {
				if (ex2 instanceof LazyInitializationException) {
					prueba = em.merge(prueba);
					prueba.getPaciente().getDni();
				} else throw ex2;
			}
			em.getTransaction().commit();
			em.close();
		} catch (Exception ex) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return (prueba);
	}
	
}
