package io.igileintelliengence.ppmtool.services;

import io.igileintelliengence.ppmtool.domain.Backlog;
import io.igileintelliengence.ppmtool.domain.ProjectTask;
import io.igileintelliengence.ppmtool.exceptions.ProjectNotFoundException;
import io.igileintelliengence.ppmtool.repository.BacklogRepository;
import io.igileintelliengence.ppmtool.repository.ProjectRepository;
import io.igileintelliengence.ppmtool.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

            //PTs to be added to a specific project, project != null, BL exists
            Backlog backlog =  projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
            //set the bl to pt
            projectTask.setBacklog(backlog);
            //we want our project sequence to be like this: IDPRO-1 IDPRO-2
            Integer BacklogSequence = backlog.getPTSequence();
            // Update the BL SEQUENCE
            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);

            //Add Sequence to Project Task
            projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            // INITIAL priority when priority null
            if(projectTask.getPriority() == 0 || projectTask.getPriority() == null) { //In the future we need projectTask.getPriority() == 0 to handle the form
                projectTask.setPriority(3);
            }
            // INITIAL status when status is null
            if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);

    }

    public Iterable<ProjectTask> findBacklogById(String id, String username) {

        projectService.findProjectByIdentifier(id, username);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id,String sequence, String username) {

        //make sure we are searching on an exciting backlog
        projectService.findProjectByIdentifier(backlog_id, username);

        //make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(sequence);
        if(projectTask == null) {
            throw new ProjectNotFoundException("Project Task '" + sequence + "' not found");
        }

        //make sure we are searching on the right backlog
        if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task '"+ sequence +"' does not in project: '" + backlog_id);
        }
        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }
    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
        projectTaskRepository.delete(projectTask);
    }
}
