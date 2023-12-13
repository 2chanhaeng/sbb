package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;
	private String q1subject = "sbb가 무엇인가요?";
	private String q1content = "sbb에 대해서 알고 싶습니다.";
	private String q2subject = "스프링부트 모델 질문입니다.";
	private String q2content = "id는 자동으로 생성되나요?";

	private int getPrevLastId() {
		List<Question> all = this.questionRepository.findAll();
		return all.get(all.size() - 1).getId() - 2;
	}

	@BeforeEach
	void setup() {
		this.questionRepository.deleteAll();
		Question q1 = new Question();
		q1.setSubject(this.q1subject);
		q1.setContent(this.q1content);
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1); // 첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject(this.q2subject);
		q2.setContent(this.q2content);
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2); // 두번째 질문 저장
	}

	@AfterEach
	void cleanup() {
		this.questionRepository.deleteAll();
	}

	@Test
	void findAll() {
		List<Question> all = this.questionRepository.findAll();
		Question q = all.get(all.size() - 2);
		assertEquals(this.q1subject, q.getSubject());
	}

	@Test
	void findById() {
		Optional<Question> oq = this.questionRepository.findById(this.getPrevLastId() + 1);
		if (oq.isPresent()) {
			Question q = oq.get();
			assertEquals(this.q1subject, q.getSubject());
		} else {
			throw new RuntimeException("질문이 없습니다.");
		}
	}

	@Test
	void findBySubject() {
		Question q = this.questionRepository.findBySubject(this.q1subject);
		assertEquals(this.getPrevLastId() + 1, q.getId());
	}

	@Test
	void findBySubjectAndContent() {
		Question q = this.questionRepository.findBySubjectAndContent(
				this.q1subject, this.q1content);
		assertEquals(this.getPrevLastId() + 1, q.getId());
	}

	@Test
	void findBySubjectLike() {
		List<Question> all = this.questionRepository.findBySubjectLike("%" + this.q1subject + "%");
		assertEquals(1, all.size());
	}
}
