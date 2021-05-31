package com.quiztok.channel.admin.service.impl;

import com.quiztok.channel.admin.model.Channel;
import com.quiztok.channel.admin.model.Criteria;

import java.util.List;

public interface ChannelService {
    List<Channel> list(Criteria cri);
    Channel view(String idx);

    int listCount(Criteria cri);

    void save(Channel channel);

}
