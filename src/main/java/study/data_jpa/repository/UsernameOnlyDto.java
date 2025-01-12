package study.data_jpa.repository;

public class UsernameOnlyDto {

    private final String usernmae;

    public UsernameOnlyDto(String usernmae) {
        this.usernmae = usernmae;
    }

    public String getUsername() {
        return usernmae;
    }
}
