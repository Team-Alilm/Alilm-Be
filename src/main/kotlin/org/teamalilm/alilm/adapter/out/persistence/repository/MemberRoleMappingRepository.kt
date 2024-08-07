package org.teamalilm.alilm.adapter.out.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.teamalilm.alilm.adapter.out.persistence.entity.MemberRoleMappingJpaEntity

interface MemberRoleMappingRepository : JpaRepository<MemberRoleMappingJpaEntity, Long> {
}