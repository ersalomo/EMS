package com.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private Long id;

    @Column(name = "order_time")
    private String orderTime;

    @Column(name = "destination_addr")
    private String destinationAddr;

    @Column(name = "completed")
    private boolean isCompleted;

    @Column(name = "email_addr")
    private Long email;

    private String password;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
