package kr.co.funch.api.interfaces.dto

import kr.co.funch.api.domain.matching.model.ChemistryInfo
import kr.co.funch.api.domain.matching.model.RecommendInfo
import kr.co.funch.api.domain.matching.model.SubwayInfo
import kr.co.funch.api.domain.member.model.Club
import kr.co.funch.api.domain.member.model.Member

object MemberMatchingDto {
    data class MatchingRequestDto(
        val requestMemberId: String,
        val targetMemberCode: String,
    )

    data class MatchingResponseDto(
        val profile: TargetProfile,
        val similarity: Int,
        val chemistryInfos: Set<ChemistryInfo>,
        val recommendInfos: Set<RecommendInfo>,
        val subwayInfos: Set<SubwayInfo>,
    ) {
        data class TargetProfile(
            val name: String,
            val jobGroup: String,
            val clubs: List<Club>,
            val mbti: String,
            val constellation: String,
            val subwayName: List<String>,
        ) {
            companion object {
                fun from(member: Member): TargetProfile {
                    return TargetProfile(
                        name = member.name,
                        jobGroup = member.jobGroup.koreanName,
                        clubs = member.clubs,
                        mbti = member.mbti.name,
                        constellation = member.constellation.koreanName,
                        subwayName = member.subwayStations.map { it.name }.toList(),
                    )
                }
            }
        }
    }
}
