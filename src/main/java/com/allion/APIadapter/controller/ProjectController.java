/**
 *
 */

package com.allion.APIadapter.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.allion.APIadapter.models.Issue;
import com.allion.APIadapter.models.Project;
import com.allion.APIadapter.models.ProjectDto;
import com.allion.APIadapter.models.ProjectSummary;
import com.allion.APIadapter.repository.ProjectRepository;
import com.allion.APIadapter.service.ProjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("project")
public class ProjectController {

    private final static Logger LOGGER = LogManager.getLogger(ProjectController.class);

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private ProjectService projectService;

    /**
     *
     * @param project
     * @return
     */
    @PostMapping(path = "/addIssues", consumes = "application/json", produces = "application/json")
    public Mono<ResponseEntity<Project>> addIssues(@RequestBody Project project) {
        LOGGER.info(project);
        List<Issue> issues = project.getIssues().stream().peek(issue -> {
            issue.setProject(project);
            issue.getChangeLogs().forEach(changelog -> changelog.setIssue(issue));
        }).collect(Collectors.toList());
        project.setIssues(issues);
        projectRepo.save(project);
        return Mono.just(ResponseEntity.ok(project));
    }

    @GetMapping(path = "/getIssues/{id}", produces = "application/json")
    public Mono<ResponseEntity<Optional<Project>>> getIssues(@PathVariable Integer id) {

        Optional<Project> project = projectRepo.findById(id);
        LOGGER.info(project);
        return Mono.just(ResponseEntity.ok(project));
    }

    @PostMapping(path = "/get-issue-by-date", consumes = "application/json", produces = "application/json")
    public Mono<ResponseEntity<ProjectSummary>> getIssuesByDate(@RequestBody
                                                                    ProjectDto projectDto) throws ParseException {
        return Mono.just(ResponseEntity.ok(projectService.getProjectSummary(projectDto)));
    }

}
