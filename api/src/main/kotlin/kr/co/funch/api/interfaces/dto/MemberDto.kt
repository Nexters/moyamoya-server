package kr.co.funch.api.interfaces.dto

import kr.co.funch.api.domain.member.model.BloodType
import kr.co.funch.api.domain.member.model.Club
import kr.co.funch.api.domain.member.model.JobGroup
import kr.co.funch.api.domain.member.model.Mbti
import kr.co.funch.api.domain.member.model.Member
import kr.co.funch.api.domain.member.model.SubwayStation
import org.bson.types.ObjectId
import java.time.LocalDateTime

object MemberDto {
    data class MemberResponse(
        val id: String,
        val name: String,
        val bloodType: String,
        val jobGroup: String,
        val clubs: List<String>,
        val subwayInfos: List<SubwayInfo>,
        val mbti: String,
        val memberCode: String,
        val viewCount: Int,
    ) {
        data class SubwayInfo(
            val name: String,
            val lines: List<String>,
        )

        companion object {
            fun of(member: Member): MemberResponse {
                return MemberResponse(
                    id = member.id.toString(),
                    name = member.name,
                    bloodType = member.bloodType.name,
                    jobGroup = member.jobGroup.koreanName,
                    clubs = member.clubs.map { it.name },
                    subwayInfos =
                        member.subwayStations.map { station ->
                            SubwayInfo(station.name, station.lines.map { SubwayStation.SubwayLine.valueOf(it).name })
                        },
                    mbti = member.mbti.name,
                    memberCode = member.code.orEmpty(),
                    viewCount = member.viewCount,
                )
            }
        }
    }

    data class MemberCreateRequest(
        val name: String,
        val jobGroup: String,
        val clubs: List<String>,
        val bloodType: String,
        val subwayStations: List<String>,
        val mbti: String,
        val deviceNumber: String,
    ) {
        fun toDomain(): Member {
            return Member(
                id = ObjectId(),
                name = name,
                bloodType = BloodType.valueOf(bloodType.uppercase()),
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
