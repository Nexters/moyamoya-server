package kr.co.funch.api.interfaces.dto

import kr.co.funch.api.domain.member.model.Club
import kr.co.funch.api.domain.member.model.JobGroup
import kr.co.funch.api.domain.member.model.Mbti
import kr.co.funch.api.domain.member.model.Member
import org.bson.types.ObjectId
import java.time.LocalDateTime

object MemberDto {
    data class MemberResponse(
        val id: String,
        val name: String,
        val jobGroup: String,
        val clubs: List<String>,
        val subwayStations: List<String>,
        val mbti: String,
        val memberCode: String,
    ) {
        companion object {
            fun of(member: Member): MemberResponse {
                return MemberResponse(
                    id = member.id.toString(),
                    name = member.name,
                    jobGroup = member.jobGroup.koreanName,
                    clubs = member.clubs.map { it.name },
                    subwayStations = member.subwayStations.map { it.name },
                    mbti = member.mbti.name,
                    memberCode = member.code.orEmpty(),
                )
            }
        }
    }

    data class MemberCreateRequest(
        val name: String,
        val jobGroup: String,
        val clubs: List<String>,
        val subwayStations: List<String>,
        val mbti: String,
        val deviceNumber: String,
    ) {
        fun toDomain(): Member {
            return Member(
                id = ObjectId(),
                name = name,
                jobGroup = JobGroup.valueOf(jobGroup.uppercase()),
                clubs = clubs.map { Club.valueOf(it.uppercase()) },
                subwayStations = emptyList(),
                mbti = Mbti.valueOf(mbti.uppercase()),
                deviceNumber = deviceNumber,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            )
        }
    }
}
