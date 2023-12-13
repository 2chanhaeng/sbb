package com.mysite.sbb.question;

import com.mysite.sbb.DataNotFoundException;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@RestController
public class QuestionController {
    private final QuestionRepository questionRepository;

    @GetMapping("/list")
    public List<Question> list() {
        List<Question> questionList = this.questionRepository.findAll();
        return questionList;
    }

    @GetMapping("/details/{id}")
    public Question detail(@PathVariable Integer id, Model model) {
        Question question = this.questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Question not found with id " + id));
        return question;
    }
}
