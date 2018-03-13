package by.htp.library.dao;

import by.htp.library.dao.exception.DAOException;

public interface DAO<T> {
    void add(T obj) throws DAOException;
    T find(Class<T> tClass, Object obj) throws DAOException;
    void change(T obj) throws DAOException;
    void remove(T obj) throws DAOException;
}
