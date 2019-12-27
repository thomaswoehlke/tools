package org.woehlke.tools.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.woehlke.tools.db.Logbuch;

import static org.woehlke.tools.config.SpringIntegrationConfig.LOGBUCH_QUEUE;
import static org.woehlke.tools.config.SpringIntegrationConfig.LOGBUCH_REPLY_QUEUE;

@MessagingGateway(
    name = "myPanelRenameFilesGateway",
    defaultRequestChannel = LOGBUCH_QUEUE,
    defaultReplyChannel = LOGBUCH_REPLY_QUEUE
)
public interface PanelRenameFilesGateway {

    @Gateway(
        replyChannel = LOGBUCH_REPLY_QUEUE,
        replyTimeout = 2,
        requestTimeout = 200
    )
    Logbuch listen(Logbuch payload);
}
