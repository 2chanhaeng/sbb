package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, AnswerCreateDTO answerDto) {
        Answer answer = answerDto.toEntity(question);
        this.answerRepository.save(answer);
    }
}
