package wh.duckbill.nplusone.batchsize.lazy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pencils")
public class Pencil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pencil_id")
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "pencilcase_id")
    private Pencilcase pencilcase;
}
