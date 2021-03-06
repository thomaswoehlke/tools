package org.woehlke.tools.view.mq;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.properties.ToolsQueueNames.*;


@MessagingGateway(
    defaultRequestChannel = JOB_IMAGES_RESIZE_QUEUE,
    defaultReplyChannel = JOB_IMAGES_INFO_QUEUE+REPLY
)
public interface JobImagesResizePanelGateway {

    @Gateway(
        replyChannel = JOB_IMAGES_INFO_QUEUE+REPLY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);
}
