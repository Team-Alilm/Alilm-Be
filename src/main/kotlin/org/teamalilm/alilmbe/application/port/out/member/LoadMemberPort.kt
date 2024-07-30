package org.teamalilm.alilmbe.application.port.out.member

import org.teamalilm.alilmbe.domain.member.Member

interface LoadMemberPort {

    fun loadMember(phoneNumber: String): Member?

    fun loadMember(id: Long): Member?
}