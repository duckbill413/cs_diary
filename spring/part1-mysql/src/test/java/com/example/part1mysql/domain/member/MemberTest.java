package com.example.part1mysql.domain.member;

import com.example.part1mysql.domain.member.entity.Member;
import com.example.part1mysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * author        : duckbill413
 * date          : 2023-03-13
 * description   :
 **/
class MemberTest {
    @DisplayName("회원은 닉네임을 변경할 수 있다")
    @Test
    public void testChangeName() {
        Member member = MemberFixtureFactory.create();
        String expected = "pnu";

        member.changeNickname(expected);

        Assertions.assertEquals(expected, member.getNickname());
    }
    @DisplayName("회원의 닉네임은 10자를 초과할 수 없다")
    @Test
    public void testNicknameMaxLength() {
        Member member = MemberFixtureFactory.create();
        String overMaxLengthName = "pupupupupupupuupup";

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> member.changeNickname(overMaxLengthName));
    }
}