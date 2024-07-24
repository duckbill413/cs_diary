package wh.duckbill.nplusone;

import jakarta.persistence.EntityManager;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.nplusone.jpa.CompanyRepository;
import wh.duckbill.nplusone.jpa.EmployeeRepository;
import wh.duckbill.nplusone.subselect.eager.Company;
import wh.duckbill.nplusone.subselect.eager.Employee;

import static org.jeasy.random.FieldPredicates.*;

@SpringBootTest
public class Lazy_SubSelectTests {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EntityManager entityManager;

    EasyRandomParameters parameters = new EasyRandomParameters()
            .excludeField(named("id").and(ofType(Long.class)).and(inClass(Company.class)))
            .excludeField(named("id").and(ofType(Long.class)).and(inClass(Employee.class)));

    EasyRandom easyRandom = new EasyRandom(parameters);

    /**
     * FetchType.LAZY - FetchMode.SUBSELECT
     * IN 절을 사용하여 데이터를 조회
     * 하위 데이터를 조회하지 않는 경우 IN 절로 데이터 요청 발생 X
     */
    @Transactional
    @DisplayName("Lazy - FetchMode.SUBSELECT 하위 데이터 조회 X")
    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            Company company = easyRandom.nextObject(Company.class);
            companyRepository.save(company);
            Employee employee = easyRandom.nextObject(Employee.class);
            employee.setCompany(company);
            company.getEmployees().add(employee);
            employeeRepository.save(employee);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        var companies = companyRepository.findAll();
    }

    /**
     * FetchType.LAZY - FetchMode.SUBSELECT
     * IN 절을 사용하여 데이터를 조회
     * 하위 데이터를 조회하지 않는 경우 IN 절로 데이터 요청 발생 O
     */
    @Transactional
    @DisplayName("Lazy - FetchMode.SUBSELECT 하위 데이터 조회 O")
    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            Company company = easyRandom.nextObject(Company.class);
            companyRepository.save(company);
            Employee employee = easyRandom.nextObject(Employee.class);
            employee.setCompany(company);
            company.getEmployees().add(employee);
            employeeRepository.save(employee);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        var companies = companyRepository.findAll();
        var names = companies.stream().flatMap(company -> company.getEmployees().stream().map(Employee::getName)).toList();
        System.out.println(names);
    }
}
