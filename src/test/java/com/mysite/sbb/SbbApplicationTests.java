package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	private String q1subject = "sbb가 무엇인가요?";
	private String q1content = "sbb에 대해서 알고 싶습니다.";
	private String q2subject = "스프링부트 모델 질문입니다.";
	private String q2content = "id는 자동으로 생성되나요?";
	private String q1updatedSubject = "수정된 제목";
	private String a2content = "네 자동으로 생성됩니다.";

	private int getPrevLastId() {
		List<Question> all = this.questionRepository.findAll();
		return all.get(all.size() - 1).getId() - 2;
	}

	@BeforeEach
	void setup() {
		this.questionRepository.deleteAll();
		Question q1 = new Question(this.q1subject, this.q1content);
		this.questionRepository.save(q1); // 첫번째 질문 저장

		Question q2 = new Question(this.q2subject, this.q2content);
		this.questionRepository.save(q2); // 두번째 질문 저장

		Answer a2 = new Answer(this.a2content, q2);
		this.answerRepository.save(a2); // 두번째 질문에 답변 저장
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

	@Test
	void update() {
		int q1Id = this.getPrevLastId() + 1;
		Optional<Question> op = this.questionRepository.findById(q1Id);
		assertTrue(op.isPresent());
		Question q = op.get();
		q.setSubject(this.q1updatedSubject);
		this.questionRepository.save(q);
		op = this.questionRepository.findById(q1Id);
		assertTrue(op.isPresent());
		Question updated = op.get();
		assertNotEquals(this.q1subject, updated.getSubject());
		assertEquals(this.q1updatedSubject, updated.getSubject());
	}

	@Test
	void delete() {
		assertEquals(2, this.questionRepository.count());
		int q1Id = this.getPrevLastId() + 1;
		Optional<Question> op = this.questionRepository.findById(q1Id);
		assertTrue(op.isPresent());
		Question q = op.get();
		this.questionRepository.delete(q);
		op = this.questionRepository.findById(q1Id);
		assertTrue(op.isEmpty());
		assertEquals(1, this.questionRepository.count());
	}

	@Test
	void findAnswerById() {
		List<Answer> all = this.answerRepository.findAll();
		assertEquals(1, all.size());
		Answer a2 = all.get(0);
		assertEquals(this.a2content, a2.getContent());
	}

	@Transactional
	@Test
	void findAnswerByQuestion() {
		int q2Id = this.getPrevLastId() + 2;
		Question q = this.questionRepository.getReferenceById(q2Id);
		List<Answer> answers = q.getAnswerList();
		assertEquals(1, answers.size());
		Answer a2 = answers.get(0);
		assertEquals(this.a2content, a2.getContent());
	}
}
