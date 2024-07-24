package wh.duckbill.nplusone.batchsize.eager;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import wh.duckbill.nplusone.batchsize.lazy.Pencil;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schools")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long id;
    private String name;

    @BatchSize(size = 4)
    @OneToMany(mappedBy = "school", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();
}
