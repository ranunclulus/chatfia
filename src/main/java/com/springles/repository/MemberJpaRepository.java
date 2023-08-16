package com.springles.repository;

import com.springles.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberName(String memberName);

    boolean existsByMemberName(String memberName);
}
