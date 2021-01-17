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
@Table(name = "t_blog")
public class Blog {

    /*  用户唯一标识  */
    @Id
    @GeneratedValue
    private Long id;

    /*  博客标题  */
    private String title;

    /*  文章内容  */
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;

    /*  首页内容  */
    private String firstPicture;

    /*  标记  */
    private String flag;

    /*  浏览次数  */
    private Integer views;

    /*  是否支持赞赏  */
    private boolean appreciation;

    /*  是否支持分享  */
    private boolean shareStatement;

    /*  是否支持评论  */
    private boolean commentabled;

    /*  是否公开  */
    private boolean published;

    /*  是否支持推荐  */
    private boolean recommend;

    /*  博客描述-用于首页展示  */
    private String description;

    /*  创建时间  */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /*  更新时间  */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /*  博客对应的类型  */
    @ManyToOne
    private Type type;

    /*  多对多的标签信息  */
    /*  级联添加tag的时候 自动添加到数据库当中的tag当中  */
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Tag> tags = new ArrayList<>();

    /*  一个博客对应一个用户  */
    @ManyToOne
    private User user;

    /*  一个博客对应多个评论  */
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    /*  对应的所有标签的id  */
    @Transient
    private String tagIds;

    /*  初始化方法  */
    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags) {
        if(!tags.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    buffer.append(",");
                } else {
                    flag = true;
                }
                buffer.append(tag.getId());
            }
            return buffer.toString();
        } else {
            return tagIds;
        }
    }
}
