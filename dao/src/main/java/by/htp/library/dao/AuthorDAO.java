package by.htp.library.dao;

import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.Author;

public interface AuthorDAO {

    boolean isAuthorExist(String name, String surname) throws DAOException;
    Author getAuthor(String name, String surname) throws DAOException;
}
