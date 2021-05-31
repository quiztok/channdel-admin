package com.quiztok.channel.admin.service.impl;

import com.quiztok.channel.admin.model.Channel;
import com.quiztok.channel.admin.model.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChannelMapper {

    List<Channel> list(Criteria cri);

    Channel view(String idx);
    int listCount(Criteria cri);

    void save(Channel channel);


}
