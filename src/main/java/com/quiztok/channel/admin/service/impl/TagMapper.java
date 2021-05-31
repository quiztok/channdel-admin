package com.quiztok.channel.admin.service.impl;


import com.quiztok.channel.admin.model.Criteria;
import com.quiztok.channel.admin.model.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    int listCount(Criteria cri);
    //List<Tag> list(Criteria cri);
    List<Tag> list();
}
