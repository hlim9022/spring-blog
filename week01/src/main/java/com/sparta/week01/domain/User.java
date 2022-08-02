package com.sparta.week01.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.week01.dto.UserLoginDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "USERS")
@Entity
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Board> boardList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> commentList;

    public User(UserLoginDto userLoginDto) {
        this.username = userLoginDto.getUsername();
        this.password = userLoginDto.getPassword();
    }
}
