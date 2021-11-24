package gei.id.tutelado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Paciente;
import gei.id.tutelado.model.Usuario;

public class UsuarioDaoJPA implements UsuarioDao {
	
	private EntityManagerFactory emf; 
	private EntityManager em;

	@Override
	public void setup (Configuracion config) {
		this.emf = (EntityManagerFactory) config.get("EMF");
	}
	
	@Override
	public Usuario almacena(Usuario usuario) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			em.persist(usuario);
			
			em.getTransaction().commit();
			em.close();
		} catch (Exception ex) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return usuario;
	}
	
	@Override
	public Usuario modifica(Usuario usuario) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			em.merge(usuario);
			
			em.getTransaction().commit();
			em.close();
		} catch (Exception ex) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return (usuario);
	}
	
	@Override
	public void elimina(Usuario usuario) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Long numPruebas;
			Usuario usuarioTmp = em.find(Usuario.class, usuario.getId());
			if (usuarioTmp.esSanitario()) {
				numPruebas = em.createNamedQuery("Sanitario.recuperarNumPruebas", Long.class).setParameter("dni", usuario.getDni()).getSingleResult();
				if (numPruebas > 0) {
					throw new RuntimeException("¡El sanitario ha realizado pruebas, no se puede eliminar!");
				}
			}
			em.remove(usuarioTmp);

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
	public Usuario recuperaPorDni(String dni) {
		List<Usuario> usuarios = null;
		
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			usuarios = em.createNamedQuery("Usuario.findByDni", Usuario.class).setParameter("dni", dni).getResultList();
			
			em.getTransaction().commit();
			em.close();
		} catch (Exception ex) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return (usuarios.size() != 0 ? usuarios.get(0) : null);
	}
	
	@Override
	public List<Paciente> recuperaPacientesSinPruebas() {
		List<Paciente> pacientes = null;
		
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			pacientes = em.createNamedQuery("Paciente.recuperarPacienteSinPruebas", Paciente.class).getResultList();
			
			em.getTransaction().commit();
			em.close();
		} catch (Exception ex) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return pacientes;
	}
	
	@Override
	public int recuperaNumPositivosNss(String nss) {
		int positivos = 0;
		
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			positivos = em.createNamedQuery("Paciente.recuperarNumPositivosPorNss", Integer.class).setParameter("nss", nss).getSingleResult();
			
			em.getTransaction().commit();
			em.close();
		} catch(Exception ex) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		
		return positivos;
	}
	
}
