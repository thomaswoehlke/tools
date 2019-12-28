package org.woehlke.tools.jobs.mq;

import org.springframework.scheduling.annotation.Async;
import org.woehlke.tools.db.Renamed;

public interface JobRenameFilesAsyncService {

    void deleteAll();

    @Async
    Renamed add(Renamed p);
}