package me.donghun.memberservice.adapter.input.api.test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestMemberRepository extends JpaRepository<TestMemberEntity, Long> {
}
