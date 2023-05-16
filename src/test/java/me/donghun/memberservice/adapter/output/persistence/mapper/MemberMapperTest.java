package me.donghun.memberservice.adapter.output.persistence.mapper;

import me.donghun.memberservice.adapter.output.persistence.entity.EmailAddressValue;
import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import me.donghun.memberservice.domain.model.EmailAddress;
import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.domain.model.MemberType;
import me.donghun.memberservice.domain.model.OccupationType;
import me.donghun.memberservice.filters.DefaultTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberMapperTest extends DefaultTest {

    @Mock
    private EmailAddressMapper emailAddressMapper;

    @InjectMocks
    private MemberMapper memberMapper;

    @DisplayName("Entity Model을 Domain Model로 변경 후 객체를 확인한다.")
    @Test
    void toDomainModel() {
        // given
        final Long id = 1L;
        final String name = "name";
        final MemberType memberType = MemberType.AUTHORS;
        final String avatar = "avatar";
        final OccupationType occupationType = OccupationType.BACKEND_ENGINEER;
        final String company = "company";
        final EmailAddress emailAddress = EmailAddress.createEmailAddress("email", "twitter", "linkedin", "github");
        final String introduce = "introduce";

        MemberEntity memberEntity = MemberEntity.builder()
                                                .id(id)
                                                .name(name)
                                                .type(memberType)
                                                .avatar(avatar)
                                                .occupationType(occupationType)
                                                .company(company)
                                                .emailAddress(null)
                                                .introduce(introduce)
                                                .build();

        given(emailAddressMapper.toDomainModel(any()))
                .willReturn(emailAddress);

        // when
        Member domainModel = memberMapper.toDomainModel(memberEntity);

        // then
        assertThat(domainModel).extracting("id", "name", "type", "avatar", "occupationType", "company", "introduce")
                               .contains(id, name, memberType, avatar, occupationType, company, introduce);
        assertThat(domainModel.getEmailAddress()).extracting("email", "twitter", "linkedin", "github")
                                                 .contains("email", "twitter", "linkedin", "github");
    }

    @DisplayName("Domain Model값을 Entity Model로 변경 후 객체를 확인한다.")
    @ParameterizedTest(name = "#{index}  id값이 {0} 들어간 테스트 케이스")
    @ValueSource(longs = {1L})
    @NullSource
    void toEntity(Long id) {
        // given
        final String name = "name";
        final MemberType memberType = MemberType.AUTHORS;
        final String avatar = "avatar";
        final OccupationType occupationType = OccupationType.BACKEND_ENGINEER;
        final String company = "company";
        final EmailAddressValue emailAddressValue = EmailAddressValue.builder()
                                                                     .email("email")
                                                                     .twitter("twitter")
                                                                     .linkedin("linkedin")
                                                                     .github("github")
                                                                     .build();
        final String introduce = "introduce";

        Member memberDomainModel = Member.builder()
                                         .id(id)
                                         .name(name)
                                         .type(memberType)
                                         .avatar(avatar)
                                         .occupationType(occupationType)
                                         .company(company)
                                         .emailAddress(null)
                                         .introduce(introduce)
                                         .build();

        given(emailAddressMapper.toValueObject(any()))
                .willReturn(emailAddressValue);


        // when
        MemberEntity entity = memberMapper.toEntity(memberDomainModel);

        // then
        assertThat(entity).extracting("id", "name", "type", "avatar", "occupationType", "company", "introduce")
                               .contains(id, name, memberType, avatar, occupationType, company, introduce);
        assertThat(entity.getEmailAddress()).extracting("email", "twitter", "linkedin", "github")
                                                 .contains("email", "twitter", "linkedin", "github");
    }
}