package com.repository;

import com.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    @Modifying
    @Query("UPDATE Merchant m SET m.isOpen = :status WHERE m.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") boolean status);
}
