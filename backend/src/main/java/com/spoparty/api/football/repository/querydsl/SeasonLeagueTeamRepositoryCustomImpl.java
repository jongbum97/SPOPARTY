package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.QCoach;
import com.spoparty.api.football.entity.QPlayer;
import com.spoparty.api.football.entity.QSeasonLeague;
import com.spoparty.api.football.entity.QSeasonLeagueTeam;
import com.spoparty.api.football.entity.QSeasonLeagueTeamPlayer;
import com.spoparty.api.football.entity.QStandings;
import com.spoparty.api.football.entity.QTeam;
import com.spoparty.api.football.entity.SeasonLeagueTeam;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SeasonLeagueTeamRepositoryCustomImpl implements SeasonLeagueTeamRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	private static final QSeasonLeague seasonLeague = QSeasonLeague.seasonLeague;
	private static final QSeasonLeagueTeam seasonLeagueTeam = QSeasonLeagueTeam.seasonLeagueTeam;
	private static final QTeam team = QTeam.team;
	private static final QStandings standings = QStandings.standings;

	private static final QSeasonLeagueTeamPlayer seasonLeagueTeamPlayer = QSeasonLeagueTeamPlayer.seasonLeagueTeamPlayer;

	private static final QPlayer player = QPlayer.player;

	private static final QCoach coach = QCoach.coach;

	public List<SeasonLeagueTeam> findTeamByKeyword(String keyword) {

		return jpaQueryFactory.select(seasonLeagueTeam)
			.from(seasonLeagueTeam)
			.join(seasonLeagueTeam.team, team)
			.fetchJoin()
			.where(team.nameKr.contains(keyword))
			.fetch();
	}

	public List<SeasonLeagueTeam> findTeamRank(int leagueId) {
		return jpaQueryFactory.select(seasonLeagueTeam)
			.from(seasonLeagueTeam)
			.join(seasonLeagueTeam.seasonLeague, seasonLeague)
			.fetchJoin()
			.join(seasonLeagueTeam.team, team)
			.fetchJoin()
			.join(seasonLeagueTeam.standing, standings)
			.fetchJoin()
			.where(seasonLeagueTeam.seasonLeague.id.eq((long)leagueId))
			.orderBy(standings.rank.asc())
			.fetch();
	}


	@Override
	public List<SeasonLeagueTeam> findTeamAllInfo(int teamId) {
		return jpaQueryFactory.select(seasonLeagueTeam)
			.from(seasonLeagueTeam)
			.join(seasonLeagueTeam.seasonLeagueTeamPlayers, seasonLeagueTeamPlayer)
			.fetchJoin()
			.join(seasonLeagueTeamPlayer.player, player)
			.fetchJoin()
			.join(seasonLeagueTeam.team, team)
			.fetchJoin()
			.join(seasonLeagueTeam.standing, standings)
			.fetchJoin()
			.join(seasonLeagueTeam.coach, coach)
			.fetchJoin()
			.join(seasonLeagueTeamPlayer.player)
			.fetchJoin()
			.where(seasonLeagueTeam.id.eq((long)teamId))
			.fetch();
	}
}