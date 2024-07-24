package wh.duckbill.nplusone.subselect.eager;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wh.duckbill.nplusone.subselect.lazy.BookStore;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
