package by.htp.library.dao;

import by.htp.library.dao.exception.DAOException;

import java.util.List;

public interface BaseDAO<T> {
    void add(T obj) throws DAOException;
    T find(Class<T> tClass, Object obj) throws DAOException;
    List<T> findAll(Class<T> tClass, int pageNumber, int pageSize) throws DAOException;
    T change(T obj) throws DAOException;
    void remove(T obj) throws DAOException;
    long getCount(Class<T> tClass) throws DAOException;
}
