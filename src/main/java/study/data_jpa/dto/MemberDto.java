package study.data_jpa.dto;

import lombok.Data;
import study.data_jpa.entity.Member;

@Data
public class MemberDto {
    private Long id;
    private String username;
    private String teamname;

    public MemberDto(Long id, String username, String teamname) {
        this.id = id;
        this.username = username;
        this.teamname = teamname;
    }

    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.teamname = member.getTeam().getName();
    }
}
