package com.mysite.sbb.question;

public class QuestionCreatedDTO {
    public Integer id;

    public QuestionCreatedDTO(Question question) {
        this.id = question.getId();
    }
}
