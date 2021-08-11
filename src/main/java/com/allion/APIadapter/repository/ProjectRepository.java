package com.allion.APIadapter.repository;

import java.util.Date;
import java.util.List;

import com.allion.APIadapter.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query(value = "SELECT * FROM Project p\n" +
                   "inner join Issue as i\n" +
                   "on p.id=i.project_id\n" +
                   "inner join change_log as cd\n" +
                   "on i.id=cd.issue_id and cd.changed_on > DATE(:startDate)  and changed_on < DATE (:endDate)\n" +
                   "where p.project_id= :id and i.current_state IN (:status) and i.type IN (:types)",
           nativeQuery = true)
    Project getProjectByChangedOnDate(@Param("id") String id, @Param("startDate") @Temporal
        Date startDate,
                                      @Param("endDate") @Temporal Date endDate, @Param("status") List<String> status,
                                      @Param("types") List<String> types);
}
