package com.myblog.service.impl;

import com.myblog.dao.CommentRepository;
import com.myblog.po.Comment;
import com.myblog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1) {
            comment.setParentComment(commentRepository.findOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    private List<Comment> eachComment(List<Comment> comments) {
        /*  转存list当中的数据 避免修改对数据库产生变化  */
        List<Comment> commentView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentView.add(c);
        }
        /*  合并评论各层子代到第一级子代集合中  */
        combineChildren(commentView);
        return commentView;
    }

    /*  格式化每个节点  */
    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            /*  获取子评论  */
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply1 : replys1) {
                recursively(reply1);
            }
            /*  修改顶节点的reply集合为迭代处理后的集合  */
            comment.setReplyComments(tempReply);
            tempReply = new ArrayList<>();
        }
    }

    /*  保存迭代找出的子代的集合  */
    private List<Comment> tempReply = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     * @param comment
     */
    private void recursively(Comment comment) {
        tempReply.add(comment);
        if(comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReply.add(reply);
                /*  递归遍历这个节点  */
                if(reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }
}
