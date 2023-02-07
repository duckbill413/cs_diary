package com.example.batch.part4.model;

/**
 * author        : duckbill413
 * date          : 2023-02-07
 * description   :
 **/
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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Level level;
    private Integer orderedPrice;
    private LocalDate updateDate;

    @Builder
    public Users(String name, Integer orderedPrice){
        this.name = name;
        this.level = Level.NORMAL;
        this.orderedPrice = orderedPrice;
        updateDate = LocalDate.now().minusDays(1);
    }

    public boolean availableLevelUp(){
        return Level.availableLevelUp(this.getLevel(), this.getOrderedPrice());
    }

    public Level levelUp(){
        Level nextLevel = Level.getNextLevel(this.getOrderedPrice());

        this.level = nextLevel;
        this.updateDate = LocalDate.now();

        return nextLevel;
    }
}
