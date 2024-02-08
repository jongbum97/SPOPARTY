package com.spoparty.api.football.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeasonLeagueTeam extends FootballBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "season_league_team_id")
	private long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "season_league_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeague seasonLeague;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Team team;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "coach_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Coach coach;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "captain_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeamPlayer captain;

	@OneToMany(mappedBy = "seasonLeagueTeam")
	private List<SeasonLeagueTeamPlayer> seasonLeagueTeamPlayers = new ArrayList<>();

	@OneToMany(mappedBy = "seasonLeagueTeam")
	private List<Lineup> lineups = new ArrayList<>();

	@Builder

	public SeasonLeagueTeam(SeasonLeague seasonLeague, Team team, Coach coach) {
		this.seasonLeague = seasonLeague;
		this.team = team;
		this.coach = coach;
	}

}