package com.mysite.sbb.question;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class QuestionController {
    private final QuestionRepository questionRepository;

    @GetMapping("/question/list")
    public List<Question> list(Model model) {
        List<Question> questionList = this.questionRepository.findAll();
        return questionList;
    }
}
