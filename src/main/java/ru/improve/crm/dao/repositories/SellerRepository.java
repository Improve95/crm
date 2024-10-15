package ru.improve.crm.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.improve.crm.dto.models.Seller;

public interface SellerRepository extends JpaRepository<Seller, Integer> {

}
