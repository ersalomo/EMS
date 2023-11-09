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
@Table(name = "orders")
@Where(clause = "deleted_at IS NULL")
public class Order extends DefaultAttrEntity {
    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "destination_addr")
    private String destinationAddr;

    @Column(name = "completed")
    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    /*
    * jika tidak dikomen maka akan error karena terjadi pemanggilan terusng menerus
    * @JsonIgnore
    * */
    private OrderDetail orderDetail;
}
