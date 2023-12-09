package com.bbs.project.service.iml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bbs.project.mapper.BoardMapper;
import com.bbs.project.model.Board;
import com.bbs.project.service.BoardService;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl extends ServiceImpl<BoardMapper, Board> implements BoardService {
    // 这里可以实现自定义的业务逻辑方法
}
