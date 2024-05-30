package com.green.securityweb_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.securityweb_1.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{

	public Member findByUsername(String username);
}
