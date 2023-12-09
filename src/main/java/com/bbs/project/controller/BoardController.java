package com.bbs.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbs.project.commen.BaseResponse;
import com.bbs.project.commen.ErrorCode;
import com.bbs.project.commen.ResultUtils;
import com.bbs.project.exception.BusinessException;
import com.bbs.project.model.Board;
import com.bbs.project.service.BoardService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("board")
public class BoardController {
    @Resource
    private BoardService boardService;

    /**
     * 根据板块 ID 获取板块信息
     *
     * @param boardId 板块 ID
     * @return 返回板块信息的 BaseResponse
     */
    @GetMapping("/{boardId}")
    public BaseResponse<Board> getBoardById(@PathVariable Integer boardId) {
        if (boardId == null || boardId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid boardId");
        }

        Board board = boardService.getById(boardId);
        if (board != null) {
            return ResultUtils.success(board);
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
    }

    /**
     * 创建板块
     *
     * @param board 板块信息
     * @return 返回创建结果的 BaseResponse
     */
    @PostMapping("/create")
    public BaseResponse<String> createBoard(@RequestBody Board board) {
        if (board == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Board name cannot be empty");
        }

        boolean created = boardService.save(board);
        if (created) {
            return ResultUtils.success("Board created successfully");
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    /**
     * 更新板块信息
     *
     * @param board 板块信息
     * @return 返回更新结果的 BaseResponse
     */
    @PutMapping("/update")
    public BaseResponse<String> updateBoard(@RequestBody Board board) {
        if (board == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Board information incomplete");
        }
        if (board.getBoardId() == null || board.getBoardId() == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Board information incomplete");
        }

        boolean updated = boardService.updateById(board);
        if (updated) {
            return ResultUtils.success("Board updated successfully");
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    /**
     * 删除板块
     *
     * @param boardId 板块 ID
     * @return 返回删除结果的 BaseResponse
     */
    @DeleteMapping("/delete/{boardId}")
    public BaseResponse<String> deleteBoard(@PathVariable Integer boardId) {
        if (boardId == null || boardId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid boardId");
        }

        boolean removed = boardService.removeById(boardId);
        if (removed) {
            return ResultUtils.success("Board deleted successfully");
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    /**
     * 分页查询板块列表
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 返回分页结果的 BaseResponse
     */
    @GetMapping("/list")
    public BaseResponse<IPage<Board>> listBoards(@RequestParam(defaultValue = "1") Integer current,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        if (current <= 0 || size <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid pagination parameters");
        }
        Page<Board> page = new Page<>(current, size);
        IPage<Board> boardPage = boardService.page(page);
        return ResultUtils.success(boardPage);
    }


    @GetMapping("tree")
    public BaseResponse<Map<String, List<Board>>> getBorderTree() {
//      查询所有的顶层板块
        LambdaQueryWrapper<Board> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Board::getParentId, 0);
        List<Board> list = boardService.list(queryWrapper);

        HashMap<String, List<Board>> map = new HashMap<>();
//     查询每个父板块下面的子版块
        for (Board board : list) {
            LambdaQueryWrapper<Board> boardLambdaQueryWrapper = new LambdaQueryWrapper<>();
            boardLambdaQueryWrapper.eq(Board::getParentId, board.getBoardId());
            List<Board> boardList = boardService.list(boardLambdaQueryWrapper);
            map.put(board.getBoardName(), boardList);
        }

        return ResultUtils.success(map);
    }
}
