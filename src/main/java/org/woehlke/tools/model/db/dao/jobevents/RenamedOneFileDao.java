package org.woehlke.tools.model.db.dao.jobevents;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.model.db.entities.jobevents.RenamedOneFile;
import org.woehlke.tools.model.db.dao.JobEventDao;

@Component
@Repository
public interface RenamedOneFileDao extends JobEventDao<RenamedOneFile> {
}
