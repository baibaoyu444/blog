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
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    /*  昵称  */
    private String nickname;

    /*  用户名  */
    private String username;

    /*  密码  */
    private String password;

    /*  邮箱  */
    private String email;

    /*  头像地址  */
    private String avatar;

    /*  类型  */
    private Integer type;

    /*  创建时间  */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /*  更新时间  */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /*  多个博客对应单个用户  */
    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();
}
