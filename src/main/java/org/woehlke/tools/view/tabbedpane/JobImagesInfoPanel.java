package org.woehlke.tools.view.tabbedpane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.view.mq.JobImagesInfoPanelGateway;
import org.woehlke.tools.config.properties.ToolsApplicationProperties;
import org.woehlke.tools.config.properties.ToolsMmiProperties;
import org.woehlke.tools.jobs.images.info.JobImagesInfoGroupServiceService;
import org.woehlke.tools.view.common.AbstractJobPanel;
import org.woehlke.tools.view.widgets.MyDirectoryChooser;

import java.awt.event.ActionEvent;
import java.io.File;

import static org.woehlke.tools.config.properties.ToolsPipelineNames.JOB_IMAGES_INFO_PANEL;

@Component(JOB_IMAGES_INFO_PANEL)
public class JobImagesInfoPanel extends AbstractJobPanel implements JobImagesInfoPanelGateway {

    private final JobImagesInfoGroupServiceService jobImagesInfoGroupService;

    @Autowired
    public JobImagesInfoPanel(
        JobImagesInfoGroupServiceService jobImagesInfoGroupService,
        ToolsApplicationProperties cfg,
        ToolsMmiProperties properties,
        MyDirectoryChooser chooser
    ) {
        super(jobImagesInfoGroupService.getJobName(), cfg, properties, chooser);
        this.jobImagesInfoGroupService = jobImagesInfoGroupService;
        initGUI();
    }

    @Override
    public void initGUI() {
        super.initUI();
        buttonChooseRootDirAndStartJob.addActionListener(this);
    }

    @Override
    public void start(File rootDirectory) {
        super.started(rootDirectory);
        jobImagesInfoGroupService.setRootDirectory(rootDirectory);
        jobImagesInfoGroupService.start();
    }

    @Override
    public String listen(String payload) {
        super.updatePanel(payload);
        return payload;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.myActionPerformed(e);
    }
}
