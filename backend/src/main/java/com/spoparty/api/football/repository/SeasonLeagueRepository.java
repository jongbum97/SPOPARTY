package com.spoparty.api.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.api.football.entity.SeasonLeague;
import com.spoparty.api.football.repository.querydsl.SeasonLeagueRepositoryCustom;

@Repository
public interface SeasonLeagueRepository extends JpaRepository<SeasonLeague, Long>, SeasonLeagueRepositoryCustom {
}
