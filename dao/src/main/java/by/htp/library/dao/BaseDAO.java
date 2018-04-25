package by.htp.library.dao;

import by.htp.library.dao.exception.DAOException;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<T> {
    T add(T obj) throws DAOException;
    T find(Serializable obj) throws DAOException;
    List<T> findAll(int pageNumber, int pageSize) throws DAOException;
    T update(T obj) throws DAOException;
    void remove(T obj) throws DAOException;
    long getCount() throws DAOException;
}
