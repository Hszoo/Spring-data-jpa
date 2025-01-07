package study.data_jpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test; // jupiter = Junit5
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJPARepsositoryTest {

    @Autowired
    MemberJPARepsository memberJPARepsository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member saveMember = memberJPARepsository.save(member);

        Member findMember = memberJPARepsository.find(saveMember.getId());

        assertThat(findMember.getId()).isEqualTo(saveMember.getId());
        assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());
        assertThat(findMember).isEqualTo(saveMember);


    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberJPARepsository.save(member1);
        memberJPARepsository.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberJPARepsository.findById(member1.getId()).get();
        Member findMember2 = memberJPARepsository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        List<Member> all = memberJPARepsository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        long count = memberJPARepsository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberJPARepsository.delete(member1);
        memberJPARepsository.delete(member2);

        long deletedCount = memberJPARepsository.count();
        assertThat(deletedCount).isEqualTo(0);

    }
}