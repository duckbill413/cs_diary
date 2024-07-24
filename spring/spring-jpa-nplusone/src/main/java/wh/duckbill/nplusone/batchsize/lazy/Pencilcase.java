package wh.duckbill.nplusone.batchsize.lazy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pencilcases")
public class Pencilcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pencilcase_id")
    private Long id;
    private String name;

    @BatchSize(size = 4)
    @OneToMany(mappedBy = "pencilcase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pencil> pencils = new ArrayList<>();
}
