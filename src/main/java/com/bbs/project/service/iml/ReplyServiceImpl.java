package com.bbs.project.service.iml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bbs.project.mapper.ReplyMapper;
import com.bbs.project.model.Reply;
import com.bbs.project.service.ReplyService;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService {
}
