package wh.duckbill.nplusone.eager;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parties")
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "party", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Person> people = new ArrayList<>();
}
