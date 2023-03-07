package ru.chia2k.logist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chia2k.logist.domain.CargoCategory;

@Repository
public interface CargoCategoryRepository extends JpaRepository<CargoCategory, Integer> {

}
