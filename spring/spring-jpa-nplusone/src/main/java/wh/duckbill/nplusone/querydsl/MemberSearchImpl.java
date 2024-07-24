package wh.duckbill.nplusone.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.nplusone.lazy.Member;
import wh.duckbill.nplusone.lazy.QMember;
import wh.duckbill.nplusone.lazy.QOrder;

import java.util.List;

@Service
public class MemberSearchImpl extends QuerydslRepositorySupport implements MemberSearch {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public MemberSearchImpl(JPAQueryFactory queryFactory) {
        super(Member.class);
        this.queryFactory = queryFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> searchAllWithOrders() {
        QMember member = QMember.member;
        QOrder order = QOrder.order;

        return queryFactory.selectFrom(member)
                .leftJoin(member.orders, order)
                .fetchJoin()
                .distinct()
                .fetch();
    }
}
