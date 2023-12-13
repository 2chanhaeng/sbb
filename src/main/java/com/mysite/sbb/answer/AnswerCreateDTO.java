package com.mysite.sbb.answer;

import java.time.LocalDateTime;

import com.mysite.sbb.question.Question;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerCreateDTO {
    private String content;
    private LocalDateTime createDate;

    public Answer toEntity(final Question question) {
        return new Answer(this.content, question);
    }
}
