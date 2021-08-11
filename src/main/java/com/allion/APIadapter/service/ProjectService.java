package com.allion.APIadapter.service;

import static com.allion.APIadapter.util.DateTimeUtil.getDatesByYearAndWeek;
import static com.allion.APIadapter.util.DateTimeUtil.getYearAndDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.allion.APIadapter.models.Changelog;
import com.allion.APIadapter.models.Project;
import com.allion.APIadapter.models.ProjectDto;
import com.allion.APIadapter.models.ProjectSummary;
import com.allion.APIadapter.models.StateSummary;
import com.allion.APIadapter.models.WeeklySummary;
import com.allion.APIadapter.repository.ProjectRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ProjectService {

    private final static Logger LOGGER = LogManager.getLogger(ProjectService.class);

    @Autowired
    private ProjectRepository projectRepo;

    public ProjectSummary getProjectSummary(ProjectDto projectDto) throws ParseException {

        String[] fromWeekArr = projectDto.getFromWeek().split("W");
        String[] toWeekArr = projectDto.getToWeek().split("W");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date sdate = df.parse(
            getDatesByYearAndWeek(Integer.parseInt(fromWeekArr[0]), Integer.parseInt(fromWeekArr[1])).get("startDate"));

        Date edate = df.parse(
            getDatesByYearAndWeek(Integer.parseInt(toWeekArr[0]), Integer.parseInt(toWeekArr[1])).get("startDate"));

        Project project = projectRepo
            .getProjectByChangedOnDate(projectDto.getProjectId(), sdate, edate, projectDto.getStates(),
                                       projectDto.getTypes());
        LOGGER.info(project);

        ProjectSummary projectSummary = new ProjectSummary();
        projectSummary.setProjectId(project.getProjectId());
        List<StateSummary> stateSummaries = new ArrayList<>();
        List<WeeklySummary> weeklySummaries = new ArrayList<>();
        if (!CollectionUtils.isEmpty(project.getIssues())) {

            AtomicReference<String> yearAndWeek = new AtomicReference<>();
            final List[] issueList = new List[] {new ArrayList<>()};
            project.getIssues().forEach(issue -> {
                if (!CollectionUtils.isEmpty(issue.getChangeLogs())) {
                    Collections.sort(issue.getChangeLogs());
                    Optional<Changelog> changelog = issue.getChangeLogs().stream().max(Changelog::compareTo);

                    changelog.ifPresent(log -> {
                        if (StringUtils.isBlank(yearAndWeek.get())) {
                            Map<String, Integer> yearDateMap = getYearAndDate(log.getChangedOn());
                            yearAndWeek.set(yearDateMap.get("year") + "W" + yearDateMap.get("week"));
                            issueList[0].add(issue);

                        } else {
                            Map<String, Integer> yearDateMap = getYearAndDate(log.getChangedOn());
                            String yearWeek = yearDateMap.get("year") + "W" + yearDateMap.get("week");
                            if (yearAndWeek.get().equalsIgnoreCase(yearWeek)) {
                                issueList[0].add(issue);
                            } else {
                                WeeklySummary weeklySummary = new WeeklySummary();
                                StateSummary stateSummary = new StateSummary();
                                stateSummary.setState(issue.getCurrentState());
                                stateSummary.setCount(issueList[0].size());
                                stateSummary.setIssues(issueList[0]);
                                stateSummaries.add(stateSummary);
                                weeklySummary.setStateSummaries(stateSummaries);
                                weeklySummary.setWeek(yearAndWeek.get());
                                weeklySummaries.add(weeklySummary);

                                issueList[0] = new ArrayList<>();
                                issueList[0].add(issue);

                                weeklySummary.setWeek(yearAndWeek.get());
                            }

                        }
                        if (project.getIssues().size() < 2) {
                            WeeklySummary weeklySummary = new WeeklySummary();
                            StateSummary stateSummary = new StateSummary();
                            stateSummary.setState(issue.getCurrentState());
                            stateSummary.setCount(issueList[0].size());
                            stateSummary.setIssues(issueList[0]);
                            stateSummaries.add(stateSummary);
                            weeklySummary.setStateSummaries(stateSummaries);
                            weeklySummary.setWeek(yearAndWeek.get());
                            weeklySummaries.add(weeklySummary);
                            issueList[0] = new ArrayList<>();
                            issueList[0].add(issue);
                        }

                    });
                }
            });

        }

        projectSummary.setWeeklySummaries(weeklySummaries);

        return projectSummary;
    }

}
