package com.hihi.square.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "authenticate", discriminatorType = DiscriminatorType.STRING, columnDefinition = "VARCHAR(11) DEFAULT 'BUYER'")
public class User {
    //        extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Integer usrId;
    private String uid;
    private String password;
    private String nickname;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Column(name = "main_address")
    private String mainAddress;

//    @ManyToOne
//    @JoinTable(name = "user_authority",
//                joinColumns = {@JoinColumn(name = "usr_id", referencedColumnName = "usr_id")},
//                inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})

//    private Role role;

    @Transient
    public String getDecriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
}
