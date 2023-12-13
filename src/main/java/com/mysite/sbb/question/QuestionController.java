package com.mysite.sbb.question;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class QuestionController {
    private final QuestionRepository questionRepository;

    @GetMapping("/question/list")
    public List<Question> list() {
        List<Question> questionList = this.questionRepository.findAll();
        return questionList;
    }

    @GetMapping("/question/details/{id}")
    public Question detail(@PathVariable Integer id, Model model) {
        Question question = this.questionRepository.findById(id).orElse(null);
        return question;
    }
}
