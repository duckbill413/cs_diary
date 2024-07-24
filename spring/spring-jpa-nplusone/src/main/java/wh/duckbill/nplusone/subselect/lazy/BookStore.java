package wh.duckbill.nplusone.subselect.lazy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookstores")
public class BookStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookstore_id")
    private Long id;
    private String name;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "bookStore", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookStore bookStore = (BookStore) o;
        return id != null && id.equals(bookStore.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
