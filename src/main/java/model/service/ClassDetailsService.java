package model.service;

import model.dao.ClassDetailsDao;
import model.dao.ClassModelDao;
import model.entity.ClassDetails;
import model.entity.ClassModel;
import model.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class ClassDetailsService {

    private final ClassDetailsDao classDetailsDao = new ClassDetailsDao();
    private final ClassModelDao classDao = new ClassModelDao();

    public List<ClassModel> getClassesOfUser(int userId) {
        return classDao.findClassesOfUser(userId);
    }


    public List<ClassDetails> getClassesByClassId(int classId) {
        return classDetailsDao.findByClassId(classId);
    }

    public ClassDetails addStudentToClass(User student, ClassModel c) {
        Transaction tx = null;
        ClassDetails cd = new ClassDetails(c, student);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.persist(cd);
            session.flush();

            tx.commit();
            return cd;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Failed to add student to class", e);
        }
    }


    public void update(ClassDetails cd) {
        classDetailsDao.update(cd);
    }

    public void removeStudentFromClass(ClassDetails cd) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            ClassDetails attached = session.get(ClassDetails.class, cd.getClassDetailsId());
            session.remove(attached);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Failed to remove student from class", e);
        }
    }

    public ClassModel reloadClass(int classId) {
        return classDao.findById(classId);
    }




}
