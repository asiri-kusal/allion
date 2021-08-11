package com.allion.APIadapter.models;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"issue_id",
		"type",
		"current_state",
		"changelogs"
})

@Entity
@Table(name = "issue", schema = "allion")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
public class Issue implements Serializable {
	private static final long serialVersionUID = 6915738322286987386L;
	@JsonIgnore
	private Integer id;

	@JsonProperty("issue_id")
	private String issueId;

	@JsonProperty("type")
	private String type;

	@JsonProperty("current_state")
	private String currentState;

	@JsonProperty("change_logs")
	@JsonManagedReference
	private List<Changelog> changeLogs;

	@JsonBackReference
	private Project project;

	public Issue(){}

	public Issue(Integer id, String issueId, String type, String currentState, Project project) {
		this.id = id;
		this.issueId = issueId;
		this.type = type;
		this.currentState = currentState;
		this.project = project;
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

	@Column(name = "issue_id", length = 50)
	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	@Column(name = "type", length = 50)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "current_state", length = 50)
	@JsonIgnore
	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "issue",cascade = CascadeType.ALL)
	@JsonIgnore
	public List<Changelog> getChangeLogs() {
		return changeLogs;
	}

	public void setChangeLogs(List<Changelog> changeLogs) {
		this.changeLogs = changeLogs;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
