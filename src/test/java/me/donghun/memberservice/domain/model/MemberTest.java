package me.donghun.memberservice.domain.model;

import me.donghun.memberservice.filters.DefaultTest;
import me.donghun.memberservice.filters.EmptyParameters;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.model.EmailAddressTest.EmailAddressFixture;
import org.junit.jupiter.api.DisplayName;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_NAME_EMPTY;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest extends DefaultTest {

    @DisplayName("회원 생성 시 회원 이름이 없는 경우 예외를 발생시킵니다.")
    @EmptyParameters
    void createMemberNameEmpty(String emptyName) {
        // given

        // when & then
        assertThatThrownBy(() -> MemberFixture.complete()
                                              .name(emptyName)
                                              .build())
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", MEMBER_NAME_EMPTY);
    }

    static class MemberFixture {
        public static Member.MemberBuilder complete() {
            return Member.builder()
                         .id(1L)
                         .type(MemberType.AUTHORS)
                         .name("name")
                         .avatar("path:avatar")
                         .occupationType(OccupationType.BACKEND_ENGINEER)
                         .company("company")
                         .emailAddress(EmailAddressFixture.complete()
                                                          .build())
                    ;
        }
    }
}