package com.allion.APIadapter.models;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"changed_on",
		"from_state",
		"to_state"
})

@Entity
@Table(name = "change_log", schema = "allion")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
public class Changelog implements Comparable<Changelog> {

	@JsonIgnore
	private Integer id;

	@JsonProperty("changed_on")
	private Date changedOn;

	@JsonProperty("from_state")
	private String fromState;

	@JsonProperty("to_state")
	private String toState;

	@JsonBackReference
	private Issue issue;

	public Changelog(){}

	public Changelog(Integer id, Date changedOn, String fromState, String toState,
			Issue issue) {
		this.id = id;
		this.changedOn = changedOn;
		this.fromState = fromState;
		this.toState = toState;
		this.issue = issue;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "changed_on", length = 50)
	public Date getChangedOn() {
		return changedOn;
	}

	public void setChangedOn(Date changedOn) {
		this.changedOn = changedOn;
	}

	@Column(name = "from_state", length = 50)
	public String getFromState() {
		return fromState;
	}

	public void setFromState(String fromState) {
		this.fromState = fromState;
	}

	@Column(name = "to_state", length = 50)
	public String getToState() {
		return toState;
	}

	public void setToState(String toState) {
		this.toState = toState;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issue_id")
	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	@Override
	public int compareTo(Changelog changelog) {
		if (changedOn.equals(changelog.changedOn)) {
			return 0;
		} else if (changedOn.after(changelog.changedOn)) {
			return 1;
		}
		return -1;
	}
}
