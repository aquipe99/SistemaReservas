package com.sys.reservas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;


@Entity
@Table(name = "court")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true,length = 20)
    private String name;
    @Column(nullable = false,length = 100)
    private String description;
    private Integer createdBy;
    private OffsetDateTime createdAt;
    private Integer modifiedBy;
    private OffsetDateTime modifiedAt;
    @Column(nullable = false)
    private Boolean status;
}
