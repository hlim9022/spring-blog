package com.sparta.week01.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.week01.dto.UserLoginDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @JsonIgnore
    @Transient
    private String passwordConfirm;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @OneToMany
    @JsonIgnore
    private List<Board> boardList;

    @OneToMany
    @JsonIgnore
    private List<Comment> commentList;

    public User(UserLoginDto userLoginDto) {
        this.username = userLoginDto.getUsername();
        this.password = userLoginDto.getPassword();
    }
}
