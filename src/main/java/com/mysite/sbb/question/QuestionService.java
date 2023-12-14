package com.mysite.sbb.question;

import com.mysite.sbb.DataNotFoundException;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.questionRepository.findAll(pageable);
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
