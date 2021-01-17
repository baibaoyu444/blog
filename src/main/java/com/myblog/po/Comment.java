package com.myblog.po;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    /*  昵称  */
    private String nickname;

    /*  邮箱  */
    private String email;

    /*  评论内容  */
    private String content;

    /*  头像地址  */
    private String avatar;

    /*  管理员的评论标记  */
    private boolean adminComment;

    /*  评论时间  */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /*  一条平路对应一个博客  */
    @ManyToOne
    private Blog blog;

    /*  如果当前的类是父类的时候  */
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();

    /*  如果当前的评论是子评论的时候  */
    @ManyToOne
    private Comment parentComment;
}
