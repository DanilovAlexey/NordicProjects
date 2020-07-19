package com.example.storage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="user", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude="userPassword")
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(name = "user_email")
	private String userEmail;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "user_password", updatable=false)
	private String userPassword;

	@Column(name = "user_role")
	private String userRole;
	
	/*
	@Column(name = "user_tariff")
	private Integer userTariff;
	*/
	
	@ManyToOne
    @JoinColumn(name = "user_tariff")
    private Tariff tariff;
}
