package com.quiztok.channel.admin.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiztok.channel.admin.model.Channel;
import com.quiztok.util.file.FileUtil;
import com.quiztok.util.file.model.file.FileInfo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import com.quiztok.channel.admin.model.Criteria;
import com.quiztok.channel.admin.model.PageMaker;
import com.quiztok.channel.admin.service.impl.ChannelService;
import com.quiztok.channel.admin.service.impl.TagService;



@Slf4j
@Controller
public class ChannelController {

    private static final String S3_DIR = "channel";

    private final FileUtil fileUtil;

    public ChannelController(final FileUtil fileUtil) {

        this.fileUtil = fileUtil;
    }


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private TagService tagService;




    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(){
        return "list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String adminList(@ModelAttribute("cri") Criteria cri , Model model){

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(channelService.listCount(cri));
        model.addAttribute("pageMaker", pageMaker);
        System.out.println("ch log :"+channelService.list(cri));
        model.addAttribute("channel", channelService.list(cri));

        return "/list";
    }


    @RequestMapping(value = "/detail/{groupIdx}", method = RequestMethod.GET)
    public String modify(@PathVariable("groupIdx") String groupIdx, @ModelAttribute("cri") Criteria cri , Model model ) {
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        pageMaker.setTotalCount(tagService.listCount(cri));
        //System.out.println("count: "+tagService.list(cri));
        model.addAttribute("tagCount", tagService.listCount(cri));
        model.addAttribute ("pageMaker", pageMaker);
        System.out.println("ch:"+channelService.view(groupIdx));
        model.addAttribute("channel",  channelService.view(groupIdx) );
        String[] groupName = channelService.view(groupIdx).getGroup_name ().replace (" ", "").split (",");

        for(String w : groupName){
            System.out.println ("tag:"+w);
        }
        model.addAttribute ("groupNameArr",  groupName);
        model.addAttribute("groupId", groupIdx);
        model.addAttribute("tag", tagService.list());
        System.out.println ( "검색: 상태" +Arrays.binarySearch(groupName , "하하") );
        return "detail";
    }

    @GetMapping("/detail") // 입력
    public String detail( @ModelAttribute("cri") Criteria cri , Model model , Channel  channel ) {
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        String[] groupName = null;
        pageMaker.setTotalCount(tagService.listCount(cri));
        //System.out.println("count: "+tagService.list(cri));
        model.addAttribute ("groupNameArr",  groupName);
        model.addAttribute("tagCount", tagService.listCount(cri));
        model.addAttribute ("pageMaker", pageMaker);
        model.addAttribute("tag", tagService.list());
        model.addAttribute("channel",  channel );
        return "detail";
    }

    @PostMapping("/save")
    public String save(@Valid Channel channel , Model model) {

        System.out.println ("ch log : "+channel);
        channelService.save (channel);

        return "redirect:list";
    }

    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    public ResponseEntity<String> fileUpload(@RequestParam("questionTitleImg") MultipartFile uploadfile) {
        String imgKey;
        try {
            FileInfo file = fileUtil.uploadImg(S3_DIR, uploadfile, "temp");
            imgKey = file.getPath() + file.getRealName();
        } catch (Exception e) {
            imgKey = null;
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(imgKey, HttpStatus.OK);
    }


}

