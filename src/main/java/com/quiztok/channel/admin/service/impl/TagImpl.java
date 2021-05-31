package com.quiztok.channel.admin.service.impl;

import com.quiztok.channel.admin.model.Criteria;
import com.quiztok.channel.admin.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagImpl implements  TagService{

    private TagMapper mapper;

    public TagImpl (final  TagMapper mapper) {
        this.mapper = mapper;
    }

    /*
    @Override
    public List<Tag> list(Criteria cri) {
        return  mapper.list(cri);
    }
    */
    @Override
    public List<Tag> list() {
        return  mapper.list();
    }

    @Override
    public int listCount(Criteria cri) {
        return  mapper.listCount(cri);
    }
}
