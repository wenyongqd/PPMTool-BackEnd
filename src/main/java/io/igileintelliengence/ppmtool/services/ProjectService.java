package io.igileintelliengence.ppmtool.services;

import io.igileintelliengence.ppmtool.domain.Project;
import io.igileintelliengence.ppmtool.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {

        return projectRepository.save(project);
    }
}