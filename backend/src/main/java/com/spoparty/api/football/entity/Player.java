package com.spoparty.api.football.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player extends FootballBaseEntity {

	@Id
	@Column(name = "player_id")
	private long id;

	@Size(min = 0, max = 100)
	@Column(length = 100, nullable = true)
	private String nameKr = "";

	@Size(min = 0, max = 200)
	@Column(length = 200, nullable = true)
	private String nameEng = "";

	@Column(nullable = false)
	private int age;

	@Size(min = 0, max = 100)
	@Column(length = 100, nullable = true)
	private String nationality = "";

	@Size(min = 0, max = 10)
	@Column(length = 3, nullable = true)
	private String height = "";

	@Size(min = 0, max = 10)
	@Column(length = 3, nullable = true)
	private String weight = "";

	@Size(min = 0, max = 200)
	@Column(length = 200, nullable = true)
	private String photo = "";

	@OneToMany(mappedBy = "player")
	List<SeasonLeagueTeamPlayer> seasonLeagueTeamPlayers = new ArrayList<>();

	@Builder
	public Player(long id, String nameKr, String nameEng, int age, String nationality, String height, String weight,
		String photo) {
		this.id = id;
		this.nameKr = nameKr;
		this.nameEng = nameEng;
		this.age = age;
		this.nationality = nationality;
		this.height = height;
		this.weight = weight;
		this.photo = photo;
	}

}
