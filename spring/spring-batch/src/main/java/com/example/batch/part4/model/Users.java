package com.example.batch.part4.model;

/**
 * author        : duckbill413
 * date          : 2023-02-07
 * description   :
 **/

import com.example.batch.part5.Orders;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Level level = Level.NORMAL;
    private LocalDate updatedDate;
    @OneToMany(mappedBy = "users", cascade = CascadeType.PERSIST)
    private List<Orders> orders = new ArrayList<>();

    @Builder
    public Users(String name, List<Orders> orders) {
        this.name = name;
        this.orders = orders;
        orders.forEach(o -> o.setUsers(this));

        updatedDate = LocalDate.now().minusDays(1);
    }
    private int getTotalPrice(){
        return this.orders.stream().mapToInt(Orders::getPrice).sum();
    }

    public boolean availableLevelUp() {
        return Level.availableLevelUp(this.getLevel(), this.getTotalPrice());
    }

    public Level levelUp() {
        Level nextLevel = Level.getNextLevel(this.getTotalPrice());

        this.level = nextLevel;
        this.updatedDate = LocalDate.now();

        return nextLevel;
    }

    public void addOrder(Orders order) {
        this.orders.add(order);
        if (order.getUsers() != this) {
            order.setUsers(this);
        }
    }
}
