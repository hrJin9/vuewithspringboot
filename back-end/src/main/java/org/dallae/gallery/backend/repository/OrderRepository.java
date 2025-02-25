package org.dallae.gallery.backend.repository;

import org.dallae.gallery.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
