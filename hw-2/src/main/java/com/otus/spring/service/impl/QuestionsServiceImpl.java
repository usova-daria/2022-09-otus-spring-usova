package com.otus.spring.service.impl;

import com.otus.spring.dao.api.QuestionsDao;
import com.otus.spring.model.Question;
import com.otus.spring.service.api.QuestionsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionsDao questionsDao;

    public QuestionsServiceImpl(QuestionsDao questionsDao) {
        this.questionsDao = questionsDao;
    }

    @Override
    public List<Question> getQuestions() {
        return questionsDao.readQuestions();
    }

}
