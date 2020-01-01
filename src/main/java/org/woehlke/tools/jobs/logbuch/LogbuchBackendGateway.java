package org.woehlke.tools.jobs.logbuch;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.woehlke.tools.model.db.config.JobCase;

import static org.woehlke.tools.config.properties.QueueNames.LOGBUCH_QUEUE;
import static org.woehlke.tools.config.properties.QueueNames.LOGBUCH_QUEUE_REPLY;

@MessagingGateway(
    defaultRequestChannel = LOGBUCH_QUEUE,
    defaultReplyChannel = LOGBUCH_QUEUE_REPLY
)
public interface LogbuchBackendGateway {

    @Gateway(
        replyChannel = LOGBUCH_QUEUE_REPLY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);

    void sendMessage(String payload, String category, JobCase job);
}