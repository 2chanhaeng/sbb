package com.mysite.sbb.question;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.AnswerCreateDTO;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@RestController
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public List<Question> list() {
        List<Question> questionList = this.questionService.getList();
        return questionList;
    }

    @GetMapping("/details/{id}")
    public Question detail(@PathVariable Integer id, Model model) {
        return this.questionService.getQuestion(id);
    }

    @PostMapping("/create")
    public QuestionCreatedDTO createQuestion(@RequestBody QuestionCreateDTO questionDto) {
        try {
            Question question = questionDto.toEntity();
            this.questionService.create(question);
            return question.toCreatedDTO();
        } catch (Exception e) {
            throw new DataNotFoundException("Question not created.");
        }
    }
}
