package com.otus.spring.dao.impl;

import com.otus.spring.config.ContentConfiguration;
import com.otus.spring.dao.api.QuestionsDao;
import com.otus.spring.dao.mapper.QuestionMapper;
import com.otus.spring.exception.TechnicalException;
import com.otus.spring.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvQuestionsDao implements QuestionsDao {

    private final String questionsSourceBase;
    private final ContentConfiguration contentConfiguration;
    private final CsvRowParser parser;
    private final QuestionMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(CsvQuestionsDao.class);

    public CsvQuestionsDao(@Value("${questions.file}") String questionsSourceBase,
                           ContentConfiguration contentConfiguration,
                           CsvRowParser parser, QuestionMapper mapper) {
        this.questionsSourceBase = questionsSourceBase;
        this.contentConfiguration = contentConfiguration;
        this.parser = parser;
        this.mapper = mapper;
    }

    @Override
    public List<Question> readQuestions() {
        List<Question> questions = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream(getQuestionsSource())
            ));
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> questionEntries = parser.parse(line);
                questions.add( mapper.map(questionEntries) );
            }
        } catch (IOException | RuntimeException e) {
            logger.error("An error occurred while reading questions", e);
            throw new TechnicalException(e);
        }

        return questions;
    }

    private String getQuestionsSource() {
        return questionsSourceBase + "_" + contentConfiguration.getLanguage() +
                                     "_" + contentConfiguration.getCountry() + ".csv";
    }

}
