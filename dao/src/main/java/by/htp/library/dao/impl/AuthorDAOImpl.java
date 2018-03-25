package by.htp.library.dao.impl;

import by.htp.library.dao.AuthorDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.helper.DAOMessages;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.Author;
import by.htp.library.entity.helper.AuthorHelper;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AuthorDAOImpl implements AuthorDAO{
    private EntityManager em = EMUtil.getEntityManager();
    private CriteriaBuilder cb = em.getCriteriaBuilder();

    @Override
    //CHECK IF THE AUTHOR EXISTS
    public boolean isAuthorExist(String name, String surname) throws DAOException {
        CriteriaQuery<Author> criteria = cb.createQuery(Author.class);
        Root<Author> root = criteria.from(Author.class);
        Predicate predicate = cb.and(
                cb.equal(root.get(AuthorHelper.NAME), name),
                cb.equal(root.get(AuthorHelper.SURNAME), surname)
        );
        criteria.select(root).where(predicate);
        try {
            em.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            em.clear();
            return false;
        }
        em.clear();
        return true;
    }

    @Override
    public Author getAuthor(String name, String surname) throws DAOException {
        Author author = null;
        CriteriaQuery<Author> criteria = cb.createQuery(Author.class);
        Root<Author> root = criteria.from(Author.class);
        Predicate predicate = cb.and(
                cb.equal(root.get(AuthorHelper.NAME), name),
                cb.equal(root.get(AuthorHelper.SURNAME), surname)
        );
        criteria.select(root).where(predicate);
        try {
            author = em.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            em.clear();
            throw new DAOException(DAOMessages.ERROR_AUTHOR_EXTRACTING);
        }
        em.clear();
        return author;
    }
}
