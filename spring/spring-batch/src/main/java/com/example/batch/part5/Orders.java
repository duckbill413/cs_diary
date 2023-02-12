package com.example.batch.part5;

/**
 * author        : duckbill413
 * date          : 2023-02-12
 * description   :
 **/
import com.example.batch.part4.model.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String itemName;
    private int price;
    private LocalDate createdDate;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    public void setUsers(Users users){
        this.users = users;

        if(!users.getOrders().contains(this)){
            users.getOrders().add(this);
        }
    }
}
