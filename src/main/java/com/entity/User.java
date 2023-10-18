package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(name = "email_addr")
    private String email;

    @NotBlank(message = "required")
    private String password;

    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @Column(name = "created_at", nullable= true, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @Column(name = "updated_at")
    private Date updatedAt;

}
