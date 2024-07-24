package wh.duckbill.nplusone;

import jakarta.persistence.EntityManager;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.nplusone.eager.Party;
import wh.duckbill.nplusone.eager.Person;
import wh.duckbill.nplusone.jpa.PartyRepository;
import wh.duckbill.nplusone.jpa.PersonRepository;

import java.util.List;

import static org.jeasy.random.FieldPredicates.*;

@SpringBootTest
public class EagerTests {
    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EntityManager entityManager;

    EasyRandomParameters parameters = new EasyRandomParameters()
            .excludeField(named("id").and(ofType(Long.class)).and(inClass(Person.class)))
            .excludeField(named("id").and(ofType(Long.class)).and(inClass(Party.class)));

    EasyRandom easyRandom = new EasyRandom(parameters);

    /**
     * FetchType.EAGER
     * 단순히 Party 만 조회하는 경우에도
     * N+1 문제가 발생
     */
    @Transactional
    @DisplayName("파티-사람 생성 및 파티 조회 테스트 (EAGER) 하위 조회 X")
    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            Party party = easyRandom.nextObject(Party.class);
            partyRepository.save(party);
            Person person = easyRandom.nextObject(Person.class);
            person.setParty(party);
            party.getPeople().add(person);
            personRepository.save(person);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        List<Party> everyParties = partyRepository.findAll();
    }

    /**
     * FetchType.EAGER
     * 단순히 Party 만 조회하는 경우에도
     * N+1 문제가 발생
     * 하위 데이터를 요청한다고 해서 추가 쿼리 X
     */
    @Transactional
    @DisplayName("파티-사람 생성 및 파티 조회 테스트 (EAGER) 하위 조회 O")
    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            Party party = easyRandom.nextObject(Party.class);
            partyRepository.save(party);
            Person person = easyRandom.nextObject(Person.class);
            person.setParty(party);
            party.getPeople().add(person);
            personRepository.save(person);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        List<Party> everyParties = partyRepository.findAll();
        List<String> names = everyParties.stream().flatMap(party -> party.getPeople().stream().map(Person::getName)).toList();
        System.out.println(names);
    }
}
