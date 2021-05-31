package com.quiztok.channel.admin.service.impl;

import com.quiztok.channel.admin.model.Criteria;
import com.quiztok.channel.admin.model.Tag;
import java.util.List;

public interface TagService{
    int listCount(Criteria cri);
    //List<Tag> list(Criteria cri);
    List<Tag> list();
}
