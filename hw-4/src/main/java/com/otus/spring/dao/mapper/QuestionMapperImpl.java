package com.otus.spring.dao.mapper;

import com.otus.spring.dao.validator.QuestionValidator;
import com.otus.spring.model.Answer;
import com.otus.spring.model.Question;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class QuestionMapperImpl implements QuestionMapper {

    private final QuestionValidator validator;

    public QuestionMapperImpl(QuestionValidator validator) {
        this.validator = validator;
    }

    @Override
    public Question map(List<String> entries) {
        validator.validate(entries);

        return new Question(
                entries.get(0),
                mapAnswers(entries.subList(1, entries.size()))
        );
    }

    private Map<Answer, Boolean> mapAnswers(List<String> entries) {
        List<Answer> answers = entries.subList(0, entries.size() - 1)
                .stream()
                .map(Answer::new)
                .collect(Collectors.toList());

        Set<Integer> correctAnswers = getCorrectAnswerIndexes( entries.get(entries.size() - 1) );
        Map<Answer, Boolean> answersToCorrect = new LinkedHashMap<>();
        for (int i = 0; i < answers.size(); i++) {
            answersToCorrect.put(answers.get(i), correctAnswers.contains(i));
        }

        return answersToCorrect;
    }

    private Set<Integer> getCorrectAnswerIndexes(String correctAnswer) {
        return Arrays.stream(correctAnswer.split(CORRECT_ANSWER_SEPARATOR))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

}
