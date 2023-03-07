package ru.chia2k.logist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.chia2k.logist.domain.Parcel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Integer> {
    List<Parcel> findBySeal(String seal);
    Optional<Parcel> findByBarcode(String seal);
    Optional<Parcel> findByNumber(String number);
    @Query(value = "select nextval('sq_hu')", nativeQuery = true)
    Integer nextId();
}
