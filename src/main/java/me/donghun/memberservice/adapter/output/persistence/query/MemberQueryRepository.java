package me.donghun.memberservice.adapter.output.persistence.query;

import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;

import java.util.Optional;

public interface MemberQueryRepository {

    Optional<MemberEntity> findById(Long memberId);
}
