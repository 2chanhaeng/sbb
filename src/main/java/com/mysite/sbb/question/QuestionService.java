package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        return this.questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Question not found."));
    }

    public Question create(Question question) {
        return this.questionRepository.save(question);
    }

    public Question deleteAll() {
        this.questionRepository.deleteAll();
        return null;
    }

    public Question delete(Integer id) {
        Question question = this.getQuestion(id);
        this.questionRepository.delete(question);
        return question;
    }
}
