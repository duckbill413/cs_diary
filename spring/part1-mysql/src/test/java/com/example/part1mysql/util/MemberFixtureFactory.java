package com.example.part1mysql.util;

import com.example.part1mysql.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

/**
 * author        : duckbill413
 * date          : 2023-03-13
 * description   :
 **/

public class MemberFixtureFactory {
    static public Member create(){
        var param = new EasyRandomParameters();
        return new EasyRandom(param).nextObject(Member.class);
    }
    static public Member create(Long seed){
        var param = new EasyRandomParameters().seed(seed);
        return new EasyRandom(param).nextObject(Member.class);
    }
}
