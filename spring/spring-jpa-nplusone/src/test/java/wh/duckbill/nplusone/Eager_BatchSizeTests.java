package wh.duckbill.nplusone;

import jakarta.persistence.EntityManager;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.nplusone.batchsize.eager.School;
import wh.duckbill.nplusone.batchsize.eager.Student;
import wh.duckbill.nplusone.batchsize.lazy.Pencil;
import wh.duckbill.nplusone.batchsize.lazy.Pencilcase;
import wh.duckbill.nplusone.jpa.SchoolRepository;
import wh.duckbill.nplusone.jpa.StudentRepository;

import static org.jeasy.random.FieldPredicates.*;

@SpringBootTest
public class Eager_BatchSizeTests {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private EntityManager entityManager;

    EasyRandomParameters parameters = new EasyRandomParameters()
            .excludeField(named("id").and(ofType(Long.class)).and(inClass(School.class)))
            .excludeField(named("id").and(ofType(Long.class)).and(inClass(Student.class)));

    EasyRandom easyRandom = new EasyRandom(parameters);

    /**
     * FetchType.EAGER - BatchSize (size: 4)
     * BatchSize 를 4로 설정하고 총 조회하는 데이터는 10
     * 처음에 10개의 Pencilcase 를 로딩
     * 연관관계 데이터 요청하지 않더라도 추가 쿼리 발생
     */
    @Transactional
    @DisplayName("Eager - BatchSize 하위 데이터 조회 X")
    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            School school = easyRandom.nextObject(School.class);
            schoolRepository.save(school);
            Student student = easyRandom.nextObject(Student.class);
            student.setSchool(school);
            school.getStudents().add(student);
            studentRepository.save(student);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        var schools = schoolRepository.findAll();
    }

    /**
     * FetchType.LAZY - BatchSize (size: 4)
     * BatchSize 를 4로 설정하고 총 조회하는 데이터는 10
     * 처음에 10개의 Pencilcase 를 로딩
     * BatchSize 만큼 추가 쿼리 발생
     * 연관관계 데이터 요청하더라도 추가 쿼리 발생 X
     */
    @Transactional
    @DisplayName("Eager- BatchSize 하위 데이터 조회 O")
    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            School school = easyRandom.nextObject(School.class);
            schoolRepository.save(school);
            Student student = easyRandom.nextObject(Student.class);
            student.setSchool(school);
            school.getStudents().add(student);
            studentRepository.save(student);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        var schools = schoolRepository.findAll();
        var names = schools.stream().flatMap(school -> school.getStudents().stream().map(Student::getName)).toList();
        System.out.println(names);
    }

}
