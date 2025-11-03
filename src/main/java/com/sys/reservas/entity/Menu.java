package com.sys.reservas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "menu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true,length = 100)
    private String description;
    @Column(nullable = false,length = 100)
    private String link;
    @Column(nullable = false,length = 100)
    private String icon;
    @Column(nullable = false)
    private Boolean active;
    private Integer parentMenu;
    @Column(nullable = false)
    private Integer menuOrder;
}
