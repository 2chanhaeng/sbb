package com.mysite.sbb.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionCreateDTO {
    private String subject;
    private String content;

    public Question toEntity() {
        return new Question(this.subject, this.content);
    }
}
