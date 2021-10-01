package com.jun.portalservice.domain.sockets;

import com.jun.portalservice.domain.sockets.messages.BaseMessage;

public interface IMessagePublisher {
  void publish(BaseMessage baseMessage);
}
