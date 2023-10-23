package com.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", columnDefinition = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "id")
    private User user;

    private Long qty;

    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @Column(name = "updated_at")
    private Date updatedAt;
}
