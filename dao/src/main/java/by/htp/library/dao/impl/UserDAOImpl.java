package by.htp.library.dao.impl;

import java.util.List;

import by.htp.library.dao.BaseDAO;
import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.User;
import by.htp.library.dao.UserDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.helper.UserDAOHelper;
import by.htp.library.entity.UserData;
import by.htp.library.entity.helper.UserHelper;
import by.htp.library.entity.helper.UserStatus;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;

@Repository
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO{

	@PersistenceContext
	@Getter
	private EntityManager em ;
	private CriteriaBuilder cb;

	public UserDAOImpl() {
		super();
		clazz = User.class;
	}

	//LOGINATION
	@Override
	public User logination(String login, String password) throws DAOException {
		cb = em.getCriteriaBuilder();
		User user = null;

		if(!isUserExist(login)) {
			throw new DAOException(UserDAOHelper.MESSAGE_USER_DOES_NOT_EXIST);
		}

		CriteriaQuery<User> criteria = cb.createQuery(clazz);
		Root<User> userRoot = criteria.from(clazz);
		Predicate predicate = cb.and(
				cb.equal(userRoot.get(UserHelper.LOGIN), login),
				cb.equal(userRoot.get(UserHelper.PASSWORD), password)
		);
		criteria.select(userRoot).where(predicate);

		try {
			user = em.createQuery(criteria).getSingleResult();
		} catch (NoResultException e) {
			throw new DAOException(UserDAOHelper.MESSAGE_INVALID_PASSWORD);
		}
		return user;
	}

	//REGISTRATION
	@Override
	public void registration(User user) throws DAOException {
		if(isUserExist(user.getLogin())) {
			throw new DAOException(UserDAOHelper.MESSAGE_USER_EXIST);
		}

		CriteriaQuery<User> criteria = cb.createQuery(clazz);
		Root<User> root = criteria.from(clazz);
		Join<User, UserData> userDataJoin = root.join(UserHelper.USER_DATA);
		criteria.where(cb.equal(userDataJoin.get(UserHelper.EMAIL) , user.getUserData().getEmail()));
		List<User> users = em.createQuery(criteria).getResultList();

		if (!users.isEmpty()) {
			em.clear();
			throw new DAOException(UserDAOHelper.MESSAGE_EMAIL_REGISTERED);
		}
		System.out.println(user);
		add(user);
		em.clear();
	}

	//CHEK IF THE USER EXISTS
	@Override
	public boolean isUserExist(String login) throws DAOException {
		cb = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = cb.createQuery(clazz);
		Root<User> userRoot = criteria.from(clazz);
		criteria.select(userRoot)
				.where(cb.equal(userRoot.get(UserHelper.LOGIN), login));
		try {
			em.createQuery(criteria).getSingleResult();
		} catch (NoResultException e) {
			em.clear();
			return false;
		}
		em.clear();
		return true;
	}

	//CHEK IF THE USER DELETED
	@Override
	public boolean isUserRemoved(String login) throws DAOException {
		Session session = em.unwrap(Session.class);
		Query query = session.createQuery(UserDAOHelper.SELECT_STATUS_BY_LOGIN);
		query.setParameter(UserHelper.LOGIN, login);
		UserStatus status = (UserStatus) query.getSingleResult();
		em.clear();
		return status.equals(UserStatus.DELETED);
	}

	//CHANGING USER DATA
	@Override
	public User updateUser(User user) throws DAOException {
		return update(user);
	}

	//GETTING A LIST OF ALL USERS
	@Override
	public List<User> getAllUsersList(int pageNumber, int pageSize) throws DAOException {
		return findAll(pageNumber, pageSize);
	}

	//REMOVING USER
	@Override
	public void removeUser(String login) throws DAOException {
		if(!isUserExist(login)) {
			throw new DAOException(UserDAOHelper.MESSAGE_USER_DOES_NOT_EXIST);
		} else if (isUserRemoved(login)) {
			throw new DAOException(UserDAOHelper.MESSAGE_USER_DELETED);
		}

		Session session = em.unwrap(Session.class);
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery(UserDAOHelper.UPDATE_USER_STATUS_BY_LOGIN);
		query.setParameter(UserHelper.LOGIN, login);
		query.setParameter(UserHelper.STATUS, UserHelper.STATUS_DELETED);
		query.executeUpdate();
		transaction.commit();
		em.clear();
	}

	//USER SEARCH BY LOGIN
	@Override
	public User findUserByLogin(String login) throws DAOException {
		if(!isUserExist(login)) {
			throw new DAOException(UserDAOHelper.MESSAGE_USER_DOES_NOT_EXIST);
		}

		User user = null;

		CriteriaQuery<User> criteria = cb.createQuery(clazz);
		Root<User> userRoot = criteria.from(clazz);
		criteria.select(userRoot)
				.where(cb.equal(userRoot.get(UserHelper.LOGIN), login));
		try {
			user = em.createQuery(criteria).getSingleResult();
		} catch (NoResultException e) {
			em.clear();
			throw new DAOException(UserDAOHelper.MESSAGE_FAIL_USER_SEARCHING);
		}
		em.clear();
		return user;
	}

	//BLOCK/UNLOCK USER
	@Override
	public void blockUnlockUser(String login) throws DAOException {
		if(!isUserExist(login)) {
			throw new DAOException(UserDAOHelper.MESSAGE_USER_DOES_NOT_EXIST);
		} else if (isUserRemoved(login)) {
			throw new DAOException(UserDAOHelper.MESSAGE_USER_DELETED);
		}

		Session session = em.unwrap(Session.class);
		Query query = session.createQuery(UserDAOHelper.SELECT_STATUS_BY_LOGIN);
		query.setParameter(UserHelper.LOGIN, login);
		UserStatus userStatus = (UserStatus) query.getSingleResult();
		query = session.createQuery(UserDAOHelper.UPDATE_USER_STATUS_BY_LOGIN);
		query.setParameter(UserHelper.LOGIN, login);

		switch (userStatus) {
			case ACTIVE:
				query.setParameter(UserHelper.STATUS, UserStatus.BLOCKED);
				break;
			case BLOCKED:
				query.setParameter(UserHelper.STATUS, UserStatus.ACTIVE);
		}
		query.executeUpdate();
		em.clear();
	}

	//GETTING USER COUNT
	@Override
	public long getUserCount() throws DAOException {
		return getCount();
	}
}
