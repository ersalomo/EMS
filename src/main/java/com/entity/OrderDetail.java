package com.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.util.DefaultAttrEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_detail")
@Where(clause = "deleted_at IS NULL")
@AttributeOverride(name = "id", column = @Column(name = "order_id" ))
public class OrderDetail extends DefaultAttrEntity {

    @Id
    private Long id;


    /*
     * maka akan error karena terjadi pemanggilan terusng menerus
     *
     * */
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "order_id")
    private Order order;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    private int qty;

    @Column(name = "total_price")
    private double totalPrice;


}
