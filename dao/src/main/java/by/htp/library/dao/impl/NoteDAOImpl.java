package by.htp.library.dao.impl;

import by.htp.library.dao.NoteDAO;
import by.htp.library.entity.Note;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

@Repository
public class NoteDAOImpl extends BaseDAOImpl<Note> implements NoteDAO {

    @PersistenceContext
    @Getter
    private EntityManager em ;
    private CriteriaBuilder cb;

    public NoteDAOImpl() {
        clazz = Note.class;
    }
}
