package com.spoparty.api.football.entity;

import java.util.ArrayList;
import java.util.List;

import com.spoparty.api.common.entity.FootballBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
public class League extends FootballBaseEntity {

	@Id
	@GeneratedValue
	@Column(name="league_id")
	private long id;

	@Size(min=0, max=100)
	@Column(length=100, nullable=false)
	private String nameKr;

	@Size(min=0, max=200)
	@Column(length=200, nullable=false)
	private String nameEng;

	@Size(min=0, max=200)
	@Column(length=200, nullable=false)
	private String logo;

	@Size(min=0, max=100)
	@Column(length=100, nullable=false)
	private String country;

	@Size(min=0, max=200)
	@Column(length=200, nullable=false)
	private String countryLogo;

	@Enumerated(EnumType.STRING)
	@Size(min=0, max=10)
	@Column(length=10, nullable=false)
	private LeagueType type;

	@OneToMany(mappedBy="league")
	List<SeasonLeague> seasonLeagues = new ArrayList<>();

}