package kr.co.funch.api.domain.matching

import kr.co.funch.api.domain.matching.model.BloodTypeChemistry
import kr.co.funch.api.domain.matching.model.MbtiChemistry
import kr.co.funch.api.domain.matching.model.MemberMatching
import kr.co.funch.api.domain.member.MemberService
import org.springframework.stereotype.Service

@Service
class MemberMatchingService(
    private val memberService: MemberService,
) {
    suspend fun getMatchingInfo(
        requestMemberId: String,
        targetMemberCode: String,
    ): MemberMatching {
        val requestMember = memberService.findMember(requestMemberId)
        val targetMember = memberService.findMemberByCode(targetMemberCode)

        require(!requestMember.isSameMember(targetMember)) {
            "request member is the same with target member - member key: ${requestMember.id}"
        }

        val mbtiChemistry =
            MbtiChemistry.of(
                requestMember.mbti,
                targetMember.mbti,
                targetMember.name,
            )
        val bloodTypeChemistry =
            BloodTypeChemistry.of(
                requestMember.bloodType,
                targetMember.bloodType,
            )
        val matchedClubs = requestMember.getMatchedClubs(targetMember)
        val matchedSubwayStations = requestMember.getMatchedSubwayStations(targetMember)

        return MemberMatching(
            targetMember,
            mbtiChemistry,
            bloodTypeChemistry,
            requestMember.hasSameJobGroup(targetMember),
            matchedClubs,
            matchedSubwayStations,
        )
    }
}
