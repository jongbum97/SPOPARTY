package com.spoparty.api.member.repository.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface FollowingTeamProjection {

	Long getId();

	@JsonProperty("memberId")
	Long getMember_id();

	@JsonProperty("teamId")
	Long getTeam_id();

}
