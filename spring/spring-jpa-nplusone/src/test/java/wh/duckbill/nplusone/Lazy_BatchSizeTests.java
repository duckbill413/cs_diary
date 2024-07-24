package wh.duckbill.nplusone;

import jakarta.persistence.EntityManager;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.nplusone.batchsize.lazy.Pencil;
import wh.duckbill.nplusone.batchsize.lazy.Pencilcase;
import wh.duckbill.nplusone.jpa.PencilRepository;
import wh.duckbill.nplusone.jpa.PencilcaseRepository;

import static org.jeasy.random.FieldPredicates.*;

@SpringBootTest
public class Lazy_BatchSizeTests {
    @Autowired
    private PencilcaseRepository pencilcaseRepository;
    @Autowired
    private PencilRepository pencilRepository;
    @Autowired
    private EntityManager entityManager;

    EasyRandomParameters parameters = new EasyRandomParameters()
            .excludeField(named("id").and(ofType(Long.class)).and(inClass(Pencilcase.class)))
            .excludeField(named("id").and(ofType(Long.class)).and(inClass(Pencil.class)));

    EasyRandom easyRandom = new EasyRandom(parameters);

    /**
     * FetchType.LAZY - BatchSize (size: 4)
     * BatchSize 를 4로 설정하고 총 조회하는 데이터는 10
     * 처음에 10개의 Pencilcase 를 로딩
     * 이후, 연관관계 데이터하지 않으면 추가 쿼리 X
     */
    @Transactional
    @DisplayName("LAZY - BatchSize 하위 데이터 조회 X")
    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            Pencilcase pencilcase = easyRandom.nextObject(Pencilcase.class);
            pencilcaseRepository.save(pencilcase);
            Pencil pencil = easyRandom.nextObject(Pencil.class);
            pencil.setPencilcase(pencilcase);
            pencilcase.getPencils().add(pencil);
            pencilRepository.save(pencil);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        var pencilcases = pencilcaseRepository.findAll();
    }

    /**
     * FetchType.LAZY - BatchSize (size: 4)
     * BatchSize 를 4로 설정하고 총 조회하는 데이터는 10
     * 처음에 10개의 Pencilcase 를 로딩
     * 이후, 연관관계 데이터 로딩시 BatchSize 만큼씩 요청하여 데이터 로딩
     */
    @Transactional
    @DisplayName("LAZY - BatchSize 하위 데이터 조회 O")
    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            Pencilcase pencilcase = easyRandom.nextObject(Pencilcase.class);
            pencilcaseRepository.save(pencilcase);
            Pencil pencil = easyRandom.nextObject(Pencil.class);
            pencil.setPencilcase(pencilcase);
            pencilcase.getPencils().add(pencil);
            pencilRepository.save(pencil);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        var pencilcases = pencilcaseRepository.findAll();
        var names = pencilcases.stream().flatMap(pencilcase -> pencilcase.getPencils().stream().map(Pencil::getName)).toList();
        System.out.println(names);
    }

}
