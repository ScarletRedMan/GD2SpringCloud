package ru.scarletredman.gateway.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "accounts")
public class Account {

    @Id
    private Long id;

    private String username;

    @Indexed(unique = true)
    private String lowerUsername;

    private String password;

    @Indexed(unique = true)
    private String email;

    private boolean banned = false;

    private String salt;

    public Account() {}

    public Account(String username, String password, String email) {
        this.username = username;
        this.lowerUsername = username.toLowerCase();
        this.password = password;
        this.email = email.toLowerCase();
    }
}
