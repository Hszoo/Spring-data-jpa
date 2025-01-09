package study.data_jpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    // @Query(name="Member.findByUsername")
    List<Member> findByUsername(@Param("username")String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // Dto로 반환 할때는 new operation
    @Query("select new study.data_jpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    Collection<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String usernmae); // 컬렉션 반환
    Member findMemberByUsername(String usernmae); // 단건 반환
    Optional<Member> findOptionalMemberByUsername(String usernmae); // 단건 optional 반환

    Page<Member> findByAge(int age, Pageable pageable); // Pageable 넘겨주면 메서드 이름으로 페이징 메서드 생성됨

    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m")
    Page<Member> findByAge2(int age, Pageable pageable); // Count 쿼리 분리 가능

    @Modifying(clearAutomatically = true) // data 변경 쿼리 날릴 때 추가 / 데이터 변경 후 영속성 컨텍스트 clear
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int agePlus(@Param("age") int age);

    // JPQL로 fetch join
    @Query("select m from Member m left join fetch m.team") // member와 연관된 team을 한방 쿼리로 가져옴
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"}) // entity Graph가 내부적으로 fetch join 수행
    List<Member> findAll();

    @Query("select m from Member m")
    @EntityGraph(attributePaths = {"team"}) // entity Graph가 내부적으로 fetch join 수행
    List<Member> findMemberEntityGrapth();

    @EntityGraph(attributePaths = {"team"}) // entity Graph가 내부적으로 fetch join 수행
    List<Member> findEntityGrapthByUsername(@Param("username")String username);

    @QueryHints(value = @QueryHint(name="org.hibernate.readonly", value="true"))
    Member findReeadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // JPA 제공
    List<Member> findLockMemberByUsername(String username);

}
