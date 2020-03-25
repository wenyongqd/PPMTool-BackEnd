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
}
