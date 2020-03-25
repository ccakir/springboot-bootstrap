package com.cakir.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
public class Ort {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty
	private String ortsname;
	@NotEmpty
	private String adresse;
	@NotEmpty
	private String stadt;
	@NotEmpty
	private String plz;
	private String land;
	
	
	public Ort() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Ort(Long id, @NotEmpty String ortsname, @NotEmpty String adresse, @NotEmpty String stadt,
			@NotEmpty String plz, String land) {
		super();
		this.id = id;
		this.ortsname = ortsname;
		this.adresse = adresse;
		this.stadt = stadt;
		this.plz = plz;
		this.land = land;
	}

	public String getOrtsname() {
		return ortsname;
	}

	public void setOrtsname(String ortsname) {
		this.ortsname = ortsname;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getStadt() {
		return stadt;
	}
	public void setStadt(String stadt) {
		this.stadt = stadt;
	}
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	public String getLand() {
		return land;
	}
	public void setLand(String land) {
		this.land = land;
	}

	@Override
	public String toString() {
		return "Ort [id=" + id + ", ortsname=" + ortsname + ", adresse=" + adresse + ", stadt=" + stadt + ", plz=" + plz
				+ ", land=" + land + "]";
	}
	

	
	
}
