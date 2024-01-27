package com.spoparty.api.football.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spoparty.api.common.entity.FootballBaseEntity;
import com.spoparty.api.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cheer extends FootballBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cheer_id")
	private long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cheer_fixture_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CheerFixture cheerFixture;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "season_league_team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeam seasonLeagueTeam;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member member;

	@Builder

	public Cheer(CheerFixture cheerFixture, SeasonLeagueTeam seasonLeagueTeam, Member member) {
		this.cheerFixture = cheerFixture;
		this.seasonLeagueTeam = seasonLeagueTeam;
		this.member = member;
	}
}
