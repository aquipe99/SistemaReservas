package com.sys.reservas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 20)
    private String name;
    @Column(length = 10)
    private String phone;
    @Column(nullable = false,unique = true,length = 100)
    private String email;
    @Column(length = 256)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private Integer createdBy;
    private LocalDateTime createdAt;
    private Integer modifiedBy;
    private LocalDateTime modifiedAt;

    private Boolean active;
}
