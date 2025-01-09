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

    @Test
    public void findByUsernameAndAgeGreaterThen() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberJPARepsository.save(m1);
        memberJPARepsository.save(m2);


        List<Member> result = memberJPARepsository.findByUsernameAndAgeGreaterThen("AAA", 15);// m2

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    public void testNamedQuery() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);

        memberJPARepsository.save(m1);
        memberJPARepsository.save(m2);

        List<Member> result = memberJPARepsository.findByUsername("AAA");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);

    }

    @Test
    public void paging() {

        //given
        memberJPARepsository.save(new Member("member1", 10));
        memberJPARepsository.save(new Member("member2", 10));
        memberJPARepsository.save(new Member("member3", 10));
        memberJPARepsository.save(new Member("member4", 10));
        memberJPARepsository.save(new Member("member5", 10));

        int age = 10;
        int offset = 0;
        int limit = 3;

        //when
        List<Member> members = memberJPARepsository.findByPage(age, offset, limit);
        long totalCount = memberJPARepsository.totalCount(age);

        // then
        assertThat(members.size()).isEqualTo(3); //offset 0 ~ limit 3
        assertThat(totalCount).isEqualTo(5);

    }

    @Test
    public void bulkUpdate() {
        //given
        memberJPARepsository.save(new Member("member1", 10));
        memberJPARepsository.save(new Member("member2", 19));
        memberJPARepsository.save(new Member("member3", 20));
        memberJPARepsository.save(new Member("member4", 21));
        memberJPARepsository.save(new Member("member5", 40));

        //when
        int resultCount = memberJPARepsository.bulkAgePlus(20);

        //then 
        assertThat(resultCount).isEqualTo(3);

    }
}