package org.woehlke.tools.jobs;

import java.io.File;

public interface JobRenameFiles extends Runnable {

    void setRootDirectory(File rootDirectory, boolean dryRun);

    void start();
}