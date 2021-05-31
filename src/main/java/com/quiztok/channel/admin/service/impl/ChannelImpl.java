package com.quiztok.channel.admin.service.impl;

import com.quiztok.channel.admin.model.Channel;
import com.quiztok.channel.admin.model.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelImpl implements ChannelService {

    private ChannelMapper mapper;

    public ChannelImpl(final ChannelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Channel> list(Criteria cri) {
        return mapper.list (cri);
    }


    @Override
    public int listCount(Criteria cri) {
        return mapper.listCount (cri);
    }

    @Override
    public void save(Channel channel) {
        mapper.save (channel);
    }

    @Override
    public Channel view(String  idx) {
       return mapper.view(idx);
    }


}
