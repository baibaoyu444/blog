package com.myblog.dao;

import com.myblog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /*  获取到父评论不为空的评论  */
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
