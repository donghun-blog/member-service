package me.donghun.memberservice.adapter.output.persistence.command;

import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
}
