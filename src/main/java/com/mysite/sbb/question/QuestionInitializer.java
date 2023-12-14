package com.mysite.sbb.question;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuestionInitializer implements ApplicationRunner {
    private final QuestionService questionService;

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (this.profile.equals("dev")) {
            System.out.println("DB initialization start");
            this.initializeDB();
            System.out.println("DB initialization end");
        }
    }

    private void initializeDB() {
        this.questionService.deleteAll();
        for (int i = 0; i < 300; i++) {
            Question question = new Question(
                    String.format("Question subject %03d", i),
                    String.format("Question content %03d", i));
            this.questionService.create(question);
        }
    }
}
