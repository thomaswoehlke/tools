package org.woehlke.tools.jobs.rename.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.rename.JobGroupRename;
import org.woehlke.tools.jobs.rename.JobRenameDirectories;
import org.woehlke.tools.jobs.rename.JobRenameFiles;
import org.woehlke.tools.jobs.common.impl.AbstractJobGroupImpl;
import org.woehlke.tools.model.db.config.JobCase;
import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.model.db.entities.JobGroup;
import org.woehlke.tools.model.db.services.JobGroupService;
import org.woehlke.tools.model.db.services.JobService;

import java.io.File;

@Component
public class JobGroupRenameImpl extends AbstractJobGroupImpl implements JobGroupRename {

    private final JobRenameDirectories jobRenameDirectories;
    private final JobRenameFiles jobRenameFiles;
    private final ApplicationProperties properties;
    private final JobService jobService;
    private final JobGroupService jobGroupService;

    @Autowired
    public JobGroupRenameImpl(JobRenameDirectories jobRenameDirectories, JobRenameFiles jobRenameFiles, ApplicationProperties properties, JobService jobService, JobGroupService jobGroupService) {
        this.jobRenameDirectories = jobRenameDirectories;
        this.jobRenameFiles = jobRenameFiles;
        this.properties = properties;
        this.jobService = jobService;
        this.jobGroupService = jobGroupService;
    }

    @Override
    public void setRootDirectory(File rootDirectory) {
        JobGroup jobGroup = new JobGroup(properties.getDryRun(),properties.getDbActive());
        Job jobRenameDirectoriesObject = Job.create(
            JobCase.JOB_RENAME_DIRECTORIES,rootDirectory,properties.getDryRun(),properties.getDbActive()
        );
        jobRenameDirectoriesObject = jobService.add(jobRenameDirectoriesObject);
        Job jobRenameFilesObject = Job.create(
            JobCase.JOB_RENAME_FILES,rootDirectory,properties.getDryRun(),properties.getDbActive()
        );
        jobRenameFilesObject = jobService.add(jobRenameFilesObject);
        this.jobRenameDirectories.setRootDirectory(jobRenameDirectoriesObject);
        this.jobRenameFiles.setRootDirectory(jobRenameFilesObject);
        jobGroup.getJobSet().add(jobRenameFilesObject);
        jobGroup.getJobSet().add(jobRenameFilesObject);
        this.jobGroupService.add(jobGroup);
    }

    @Override
    public String getJobName() {
        return jobRenameDirectories.getJobName() + " and "+ jobRenameFiles.getJobName();
    }

    @Override
    public void run() {
        this.jobRenameDirectories.run();
        this.jobRenameFiles.run();
    }
}