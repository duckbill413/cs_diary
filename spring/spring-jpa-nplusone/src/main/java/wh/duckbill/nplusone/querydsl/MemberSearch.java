package wh.duckbill.nplusone.querydsl;

import wh.duckbill.nplusone.lazy.Member;

import java.util.List;

public interface MemberSearch {
    List<Member> searchAllWithOrders();
}
