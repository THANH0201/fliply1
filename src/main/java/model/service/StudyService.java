package model.service;

import model.dao.StudyDao;
import model.entity.FlashcardSet;
import model.entity.Study;
import model.entity.User;

public class StudyService {

    private final StudyDao dao = new StudyDao();

    public double getProgressPercent(User student, FlashcardSet set) {
        Study study = dao.findByStudentAndSet(student.getUserId(), set.getFlashcardSetId());

        if (study == null) return 0.0;

        int learned = study.getStatistic();
        int total = set.getCards().size();

        if (total == 0) return 0.0;

        return (double) learned / total;
    }
}
