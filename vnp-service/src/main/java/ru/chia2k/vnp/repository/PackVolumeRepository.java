package ru.chia2k.vnp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chia2k.vnp.domain.PackVolume;

@Repository
public interface PackVolumeRepository extends JpaRepository<PackVolume, Integer> {
}
