package gei.id.tutelado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.LazyInitializationException;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.UsuarioD;


public class UsuarioDaoDJPA implements UsuarioDaoD {

	private EntityManagerFactory emf; 
	private EntityManager em;

	@Override
	public void setup (Configuracion config) {
		this.emf = (EntityManagerFactory) config.get("EMF");
	}

	@Override
	public UsuarioD almacena(UsuarioD user) {

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			em.persist(user);

			em.getTransaction().commit();
			em.close();

		} catch (Exception ex ) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return user;
	}

	@Override
	public UsuarioD modifica(UsuarioD user) {

		try {
			
			em = emf.createEntityManager();
			em.getTransaction().begin();

			user= em.merge (user);

			em.getTransaction().commit();
			em.close();		
			
		} catch (Exception ex ) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		return (user);
	}

	@Override
	public void elimina(UsuarioD user) {
		try {
			
			em = emf.createEntityManager();
			em.getTransaction().begin();

			UsuarioD userTmp = em.find (UsuarioD.class, user.getId());
			em.remove (userTmp);

			em.getTransaction().commit();
			em.close();
			
		} catch (Exception ex ) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
	}


	@Override
	public UsuarioD recuperaPorNif(String nif) {
		List <UsuarioD> usuarios=null;

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			usuarios = em.createNamedQuery("Usuario.recuperaPorNif", UsuarioD.class).setParameter("nif", nif).getResultList(); 

			em.getTransaction().commit();
			em.close();	

		}
		catch (Exception ex ) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}

		return (usuarios.size()!=0?usuarios.get(0):null);
	}


	@Override
	public UsuarioD restauraEntradasLog(UsuarioD user) {
		// Devolve o obxecto user coa coleccion de entradas cargada (se non o estaba xa)

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			try {
				user.getEntradasLog().size();
			} catch (Exception ex2) {
				if (ex2 instanceof LazyInitializationException)

				{
					/* OPCION DE IMPLEMENTACION 1 (comentada): Cargar a propiedade "manualmente" cunha consulta, 
					 *  e actualizar tamen "manualmente" o valor da propiedade  */  
					//List<EntradaLog> entradas = (List<EntradaLog>) entityManager.createQuery("From EntradaLog l where l.usuario=:usuario order by dataHora").setParameter("usuario",user).getResultList();
					//user.setEntradasLog (entradas);

					/* OPCION DE IMPLEMENTACIÓN 2: Volver a ligar o obxecto usuario a un novo CP, 
					 * e acceder á propiedade nese momento, para que Hibernate a cargue.*/
					user = em.merge(user);
					user.getEntradasLog().size();

				} else {
					throw ex2;
				}
			}
			em.getTransaction().commit();
			em.close();
		}
		catch (Exception ex ) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}
		
		return (user);

	}

	@Override
	public List<UsuarioD> recuperaTodos() {
		List <UsuarioD> usuarios=null;

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			usuarios = em.createNamedQuery("Usuario.recuperaTodos", UsuarioD.class).getResultList(); 

			em.getTransaction().commit();
			em.close();	

		}
		catch (Exception ex ) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}

		return usuarios;
	}







}
