package org.dallae.gallery.backend.repository;

import org.dallae.gallery.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByMemberId(Long memberId);

    Cart findByMemberIdAndItemId(Long memberId, Long itemId);
}
