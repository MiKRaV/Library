package by.htp.library.dao.impl;

import by.htp.library.dao.AuthorDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.helper.DAOMessages;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.Author;
import by.htp.library.entity.Book;
import by.htp.library.entity.helper.AuthorHelper;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class AuthorDAOImpl extends BaseDAOImpl<Author> implements AuthorDAO{

    @PersistenceContext
    @Getter
    private EntityManager em;
    private CriteriaBuilder cb;

    public AuthorDAOImpl() {
        super();
        clazz = Author.class;
    }

    @Override
    //CHECK IF THE AUTHOR EXISTS
    public boolean isAuthorExist(String name, String surname) throws DAOException {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Author> criteria = cb.createQuery(clazz);
        Root<Author> root = criteria.from(clazz);
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
        cb = em.getCriteriaBuilder();
        Author author = null;
        CriteriaQuery<Author> criteria = cb.createQuery(clazz);
        Root<Author> root = criteria.from(clazz);
        Predicate predicate = cb.and(
                cb.equal(root.get(AuthorHelper.NAME), name),
                cb.equal(root.get(AuthorHelper.SURNAME), surname)
        );
        criteria.select(root).where(predicate);
        try {
            author = em.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            throw new DAOException(DAOMessages.ERROR_AUTHOR_EXTRACTING);
        }
        return author;
    }
}
