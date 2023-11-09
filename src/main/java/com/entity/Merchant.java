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
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "merchants", uniqueConstraints = @UniqueConstraint(columnNames = "merchant_code", name = "unique_merchant_code"))
@Where(clause = "deleted_at IS NULL")
public class Merchant extends DefaultAttrEntity {


    @Column(name = "merchant_code")
    private String merchantCode;

    @Column(name = "merchant_name")
    private String merchantName;

    @Column(name = "merchant_location")
    private String merchantLocation;

    @Column(name = "is_open")
    private boolean isOpen;


    @JsonIgnore
    @OneToMany(mappedBy = "merchant",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products;
}
