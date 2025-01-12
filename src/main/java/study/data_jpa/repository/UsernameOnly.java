package study.data_jpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

    @Value("#{target.username + ' ' + target.age}") // username + age를 함께 반환하도록
    String getUsername();
}
