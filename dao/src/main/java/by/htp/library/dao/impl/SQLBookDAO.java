package by.htp.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import by.htp.library.entity.Author;
import by.htp.library.entity.Book;
import by.htp.library.dao.BookDAO;
import by.htp.library.dao.db.ConnectionPool;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.helper.DAOBookHelper;

public class SQLBookDAO implements BookDAO {

	private ConnectionPool conPool;
			//conPool = ConnectionPool.getConnectionPool();

	//ДОБАВЛЕНИЕ КНИГИ
	public void addBook(Book book) throws DAOException {
		/*
		Connection con = null;
		
		String title = book.getTitle();
		ArrayList<Author> authors = book.getAuthors();
		
		if(isBookExist(title, authors)) {
			throw new DAOException("This book already exist");
		}
		
		PreparedStatement pstmt;
		ResultSet rs;
		
		try {
			con = conPool.retrieve();
			pstmt = con.prepareStatement("INSERT INTO books (status_book, genre, title)"
        			+ "VALUES (?,?,?)");
        	pstmt.setString(1, book.getStatus());
        	pstmt.setString(2, book.getGenre());
        	pstmt.setString(3, book.getTitle());
        	pstmt.executeUpdate();
        	
        	pstmt = con.prepareStatement("SELECT * FROM books ORDER BY id_book DESC LIMIT 1");
        	rs = pstmt.executeQuery();
        	String idBook = rs.getString("id_book");
        	
        	pstmt = con.prepareStatement("INSERT INTO books_authors (id_book, id_author)"
        			+ "VALUES (?,?)");
        	for(Author author : book.getAuthors()) {
        		pstmt.setString(1, idBook);
        		PreparedStatement pstmtAuthor;
        		ResultSet rsAuthor;
        		String name = author.getName();
        		String surname = author.getSurname();
        		String idAuthor = "";
        		if(!isAuthorExist(name, surname)) {
        			pstmtAuthor = con.prepareStatement("INSERT INTO authors (name, surname)"
                			+ "VALUES (?,?)");
        			pstmtAuthor.setString(1, name);
        			pstmtAuthor.setString(2, surname);
        			pstmtAuthor.executeUpdate();
        		} 
        		pstmtAuthor = con.prepareStatement("SELECT * FROM authors where name = ? AND surname = ?");
    			pstmtAuthor.setString(1, name);
    			pstmtAuthor.setString(2, surname);
    			rsAuthor = pstmt.executeQuery();
    			idAuthor = rsAuthor.getString("id_author");
            	pstmt.setString(2, idAuthor);
        	}
        	
		} catch (SQLException e) {
			throw new DAOException("Error with adding a book to the database", e);
		} finally {
			conPool.putback(con);
		}
		*/
	}

	public List<Book> searchBookByTitle(String title) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	//ПОЛУЧЕНИЕ СПИСКА ВСЕХ КНИГ
	public List<Book> getAllBooks() throws DAOException {
		/*
		Connection con = null;
		ArrayList<Book> bookList = null;

		try {
			con = conPool.retrieve();
			bookList = new ArrayList<Book>();
			
			PreparedStatement pstmtBooks = con.prepareStatement(DAOBookHelper.SELECT_ALL_BOOKS);
			ResultSet rsBook = pstmtBooks.executeQuery();
			
			PreparedStatement pstmtAuthors = con.prepareStatement(DAOBookHelper.SELECT_AUTHORS_BY_ID_BOOK);
			ResultSet rsAuthors = null;
			
			while (rsBook.next()) {
				String idBook = rsBook.getString(DAOBookHelper.ID_BOOK);
				String bookStatus = rsBook.getString(DAOBookHelper.STATUS_BOOK);
				String genre = rsBook.getString(DAOBookHelper.GENRE);
				String title = rsBook.getString(DAOBookHelper.TITLE);

				ArrayList<Author> authors = new ArrayList<Author>();

				pstmtAuthors.setString(1, idBook);
				rsAuthors = pstmtAuthors.executeQuery();

				while (rsAuthors.next()) {
					String surname = rsAuthors.getString(DAOBookHelper.AUTHOR_SURNAME);
					String name = rsAuthors.getString(DAOBookHelper.AUTHOR_NAME);
					authors.add(new Author(surname, name));
				}

				bookList.add(new Book(authors, title, bookStatus, genre));
			}

		} catch (SQLException e) {
			throw new DAOException("Error when retrieving user list", e);
		} finally {
			conPool.putback(con);
		}

		return bookList;
		*/
		//stub
		return null;
	}

	//ПРОВЕРКА, СУЩЕСТВУЕТ ЛИ АВТОР
	public boolean isAuthorExist(String name, String surname) throws DAOException {
		Connection con = null;
		
		try {
			con = conPool.retrieve();
			
			PreparedStatement pstmt = con.prepareStatement(DAOBookHelper.SELECT_ALL_AUTHORS);
	    	ResultSet rs = pstmt.executeQuery();
	    	
	    	while(rs.next()) {
	    		if (rs.getString(DAOBookHelper.AUTHOR_NAME).equals(name) &&
	    				rs.getString(DAOBookHelper.AUTHOR_SURNAME).equals(surname)) {
        			return true;
        		}
	    	}
		} catch (SQLException e) {
			throw new DAOException("Sorry", e);
		} finally {
			conPool.putback(con);
        }
		
		return false;
	}

	//УДАЛЕНИЕ КНИГИ
	public void removeBook(String title, List<Author> authors) throws DAOException {
		if(!isBookExist(title, authors)) {
			throw new DAOException("Such a book does not exist");
		}
		
		
	}

	//ПРОВЕРКА, СУЩЕСТВУЕТ ЛИ КНИГА
	public boolean isBookExist(String title, List<Author> authors) throws DAOException {
		/*
		Connection con = null;
		
		try {
			con = conPool.retrieve();
			
			PreparedStatement pstmtBook = con.prepareStatement(DAOBookHelper.SELECT_BOOKS_BY_TITLE);
			pstmtBook.setString(1, title);
			ResultSet rsBook = pstmtBook.executeQuery();
			
			while(rsBook.next()) {
				String idBook = rsBook.getString(DAOBookHelper.ID_BOOK);
				PreparedStatement pstmtAuthors = con.prepareStatement(DAOBookHelper.SELECT_AUTHORS_BY_ID_BOOK);
				pstmtAuthors.setString(1, idBook);
				ResultSet rsAuthors = pstmtAuthors.executeQuery();
				int countAuthors = 0;
				
				while(rsAuthors.next()) {
					String surname = rsAuthors.getString(DAOBookHelper.AUTHOR_SURNAME);
    				String name = rsAuthors.getString(DAOBookHelper.AUTHOR_NAME);
    				Author author = new Author(surname, name);
    				if(authors.contains(author)) {
    					countAuthors++;
    					break;
    				}
				}
				
				if(countAuthors == authors.size()) {
    				return true;
    			}
			}
			

	    	
		} catch (SQLException e) {
			throw new DAOException("Sorry", e);
		} finally {
			conPool.putback(con);
        }
        */
		
		return false;
	}
	
	

}
