package com.repository;

import com.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long>, JpaSpecificationExecutor<Merchant> {
    @Modifying
    @Query("UPDATE Merchant m SET m.isOpen = :status WHERE m.id = :id")
    Merchant updateStatus(@Param("id") Long id, @Param("status") boolean status);
//    @Query("select count(*) from Event k where k.status = :status and year(k.startDate) = year(current_date) and month(k.startDate) = month(current_date)")
//    public Integer countByStatusAndCurrentMonth(@Param("status") String status);

//    Specification<Event> spec = ((root, query, criteriaBuilder) -> {
//        List<Predicate> predicates = new ArrayList<>();
//        //                Expression<String> searchValueLower = cb.lower(cb.literal(searchValue));
//        if (!finalName.isEmpty()) {
//            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + finalName.toLowerCase() + "%"));
//        }
//        if (finalChekData != null) {
//            predicates.add(criteriaBuilder.equal(root.get("masterEvent"), finalChekData));
//        }
//        if (!Objects.equals(startDate, null) && !startDate.isEmpty() && !Objects.equals(endDate, null) && !endDate.isEmpty()) {
//            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), finalDate));
//            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), finalDateEndDate));
//        }
//
//        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//    });
}
