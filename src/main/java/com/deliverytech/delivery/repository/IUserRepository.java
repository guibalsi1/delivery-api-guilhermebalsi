package com.deliverytech.delivery.repository;


import com.deliverytech.delivery.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    public Optional<Users> findByEmail(String email);
    public boolean existsByEmail(String email);
    public Optional<Users> findByActiveAndEmail(Boolean active, String email);
}
