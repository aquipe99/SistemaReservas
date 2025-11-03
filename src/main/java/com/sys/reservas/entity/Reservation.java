package com.sys.reservas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "client_name",length = 100,nullable = false)
    private String clientName;
    private LocalDateTime date;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(length = 10, nullable = false)
    private String paymentType;

    @ManyToOne
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    @Column(length = 10)
    private String phone;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(length = 10)
    private String clientDni;
    @Column(length = 20,nullable = false)
    private String status;

    private Integer createdBy;
    private LocalDateTime createdAt;
    private Integer modifiedBy;
    private LocalDateTime modifiedAt;
}
