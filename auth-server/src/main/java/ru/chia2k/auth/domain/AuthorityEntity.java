package ru.chia2k.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "AUTHORITIES")
@RequiredArgsConstructor
@Setter
@Getter
public class AuthorityEntity implements GrantedAuthority {
    @Id
    @Column(name = "ID")
    private String id;

    @Override
    public String getAuthority() {
        return id;
    }
}
