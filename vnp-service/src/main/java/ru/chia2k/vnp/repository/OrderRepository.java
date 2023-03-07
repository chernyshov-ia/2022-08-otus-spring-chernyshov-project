package ru.chia2k.vnp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chia2k.vnp.domain.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserIdAndCreatedAtBetween(Integer userId, LocalDateTime from, LocalDateTime to);
    Optional<Order> findByIdAndUserId(Integer id, Integer userId);
    Optional<Order> findFirstByParcelId(Integer parcelId);

}
