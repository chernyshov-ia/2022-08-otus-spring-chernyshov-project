package ru.chia2k.logist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.chia2k.logist.domain.Address;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

    @Query("select a from Address a " +
            " where UPPER(a.name) like CONCAT('%',UPPER(:text),'%') " +
            "or UPPER(a.address) like CONCAT('%',UPPER(:text),'%') " +
            "or a.id like CONCAT('%',UPPER(:text),'%')")
    List<Address> search(@Param("text") String text);
}
