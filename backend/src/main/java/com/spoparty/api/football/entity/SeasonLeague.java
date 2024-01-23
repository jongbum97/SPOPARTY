package com.spoparty.api.football.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.spoparty.api.common.entity.FootballBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class SeasonLeague extends FootballBaseEntity {
	@Id
	@GeneratedValue
	@Column(name="season_league_id")
	private long id;

	@Column(nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime seasonStartDate;

	@Column(nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime seasonEndDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "season_id", nullable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Season season;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "league_id", nullable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private League league;

	@OneToMany(mappedBy = "seasonLeague")
	private List<SeasonLeagueTeam> seasonLeagueTeams = new ArrayList<>();

	@OneToMany(mappedBy = "seasonLeague")
	private List<Fixture> fixtures = new ArrayList<>();

}
