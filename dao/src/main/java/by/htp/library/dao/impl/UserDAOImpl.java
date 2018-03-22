package by.htp.library.dao.impl;

import java.util.List;

import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.User;
import by.htp.library.dao.UserDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.helper.UserDAOHelper;
import by.htp.library.entity.UserData;
import by.htp.library.entity.helper.UserHelper;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.*;

public class UserDAOImpl implements UserDAO{
	
	private by.htp.library.dao.BaseDAO baseDAO = new BaseDAOImpl();
	private EntityManager em = EMUtil.getEntityManager();
	private CriteriaBuilder cb = em.getCriteriaBuilder();

	//LOGINATION
	@Override
	public User logination(String login, String password) throws DAOException {
		User user;

		if(!isUserExist(login)) {
			throw new DAOException(UserDAOHelper.MESSAGE_USER_DOES_NOT_EXIST);
		}

		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
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

		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		Join<User, UserData> userDataJoin = root.join(UserHelper.USER_DATA);
		criteria.where(cb.equal(userDataJoin.get(UserHelper.EMAIL) , user.getUserData().getEmail()));
		List<User> users = em.createQuery(criteria).getResultList();

		if (!users.isEmpty()) {
			throw new DAOException(UserDAOHelper.MESSAGE_EMAIL_REGISTERED);
		}

		baseDAO.add(user);
	}

	//CHEK IF THE USER EXISTS
	@Override
	public boolean isUserExist(String login) throws DAOException {
		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot)
				.where(cb.equal(userRoot.get(UserHelper.LOGIN), login));
		try {
			em.createQuery(criteria).getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
		return true;
	}

	//CHEK IF THE USER DELETED
	@Override
	public boolean isUserRemoved(String login) throws DAOException {
		Session session = em.unwrap(Session.class);
		Query query = session.createQuery(UserDAOHelper.SELECT_STATUS_BY_LOGIN);
		query.setParameter(UserHelper.LOGIN, login);
		String status = (String) query.getSingleResult();
		return status.equals(UserHelper.STATUS_DELETED);
	}

	//CHANGING USER DATA
	@Override
	public void changeUserData(User user) throws DAOException {
		baseDAO.change(user);
	}

	@Override
	public List<User> getAllUsersList() throws DAOException {
		return null;
	}

	//GETTING A LIST OF ALL USERS
	@Override
	public List<User> getAllUsersList(int pageNumber, int pageSize) throws DAOException {
		return baseDAO.findAll(User.class, pageNumber, pageSize);
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
	}

	//USER SEARCH BY LOGIN
	@Override
	public User findUserByLogin(String login) throws DAOException {
		if(!isUserExist(login)) {
			throw new DAOException(UserDAOHelper.MESSAGE_USER_DOES_NOT_EXIST);
		}

		User user = null;

		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot)
				.where(cb.equal(userRoot.get(UserHelper.LOGIN), login));
		try {
			user = em.createQuery(criteria).getSingleResult();
		} catch (NoResultException e) {
			throw new DAOException(UserDAOHelper.MESSAGE_FAIL_USER_SEARCHING);
		}
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
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery(UserDAOHelper.SELECT_STATUS_BY_LOGIN);
		query.setParameter(UserHelper.LOGIN, login);
		String status = (String) query.getSingleResult();
		query = session.createQuery(UserDAOHelper.UPDATE_USER_STATUS_BY_LOGIN);
		query.setParameter(UserHelper.LOGIN, login);

		switch (status) {
			case UserHelper.STATUS_ACTIVE :
				query.setParameter(UserHelper.STATUS, UserHelper.STATUS_BLOCKED);
				break;
			case UserHelper.STATUS_BLOCKED :
				query.setParameter(UserHelper.STATUS, UserHelper.STATUS_ACTIVE);
		}
		query.executeUpdate();
		transaction.commit();
	}

	@Override
	public long getUserCount() throws DAOException {
		return baseDAO.getCount(User.class);
	}
}
