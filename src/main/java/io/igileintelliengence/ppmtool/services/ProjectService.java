package io.igileintelliengence.ppmtool.services;

import io.igileintelliengence.ppmtool.domain.Project;
import io.igileintelliengence.ppmtool.exceptions.ProjectIdException;
import io.igileintelliengence.ppmtool.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {

        try {
            project.setProjectIdentifier((project.getProjectIdentifier().toUpperCase()));
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already existed");
        }
    }

    public Project findProjectByIndentifer(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId);
        if(project == null) {
            throw new ProjectIdException("Project Id '"+projectId+"' does not exist.");
        }
        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectid) {
        Project project = projectRepository.findByProjectIdentifier(projectid.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Cannot Project with ID" +projectid +"'. This Project does not exist.");
        }
        projectRepository.delete(project);
    }
}
