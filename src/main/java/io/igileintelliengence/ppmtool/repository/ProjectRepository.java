package io.igileintelliengence.ppmtool.repository;

import io.igileintelliengence.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByProjectIdentifier(String projected);

    @Override
    Iterable<Project> findAll();
}

