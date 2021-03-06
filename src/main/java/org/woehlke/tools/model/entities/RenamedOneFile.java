package org.woehlke.tools.model.entities;

import org.woehlke.tools.model.config.JobEventSignal;
import org.woehlke.tools.model.config.JobEventType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.File;
import java.io.Serializable;

import static org.woehlke.tools.model.common.JobEventDiscriminatorValue.RENAMED_ONE_FILE;

@Entity
@DiscriminatorValue(RENAMED_ONE_FILE)
public class RenamedOneFile extends RenamedOneDirectory implements Serializable {

    public RenamedOneFile() {
        super();
    }

    public RenamedOneFile(
        File source,
        File target,
        Job myJob,
        JobEventType jobEventType,
        JobEventSignal jobEventSignal
       ) {
        super(source, target, myJob, jobEventType, jobEventSignal);
    }

    @Transient
    public String getCategory() {
        return "UDEFINED_CATEGORY";
    }

    @Transient
    public String getLine() {
        return "UNDEFINED_LINE";
    }

    @Override
    public String toString() {
        return "RenamedOneFile{} " + super.toString();
    }
}
