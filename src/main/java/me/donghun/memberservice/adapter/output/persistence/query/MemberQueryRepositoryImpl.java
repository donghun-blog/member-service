package me.donghun.memberservice.adapter.output.persistence.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static me.donghun.memberservice.adapter.output.persistence.entity.QMemberEntity.memberEntity;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MemberEntity> findById(Long memberId) {
        return Optional.ofNullable(queryFactory
                .select(memberEntity)
                .from(memberEntity)
                .where(memberEntity.id.eq(memberId))
                .fetchOne());
    }
}
