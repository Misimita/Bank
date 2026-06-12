package org.example.project.repository;

import org.example.project.dto.UserResponseDto;
import org.example.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Page<User> findAll(Pageable pageable);

    @Query("""
    SELECT new org.example.project.dto.UserResponseDto(
        u.id, u.username, u.fullName, u.email, u.isKyc, 
        a.accountNumber, a.balance
    )
    FROM User u 
    LEFT JOIN u.account a
    """)
    Page<UserResponseDto> findAllUsers(Pageable pageable);
}