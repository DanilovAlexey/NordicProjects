package com.example.storage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tariff", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tariff {

	@Id
	@Column(name = "tariff_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tariffId;
	
	@Column(name = "tariff_name")
	private String tariffName;
	
	@Column(name = "tariff_limit_mb")
	private Integer tariffLimitMb;
	
}
