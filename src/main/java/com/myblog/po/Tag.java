package com.myblog.po;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "t_tag")
public class Tag {

    /*  标记的唯一标识  */
    @Id
    @GeneratedValue
    private Long id;

    /*  标记的名称  */
    @NotBlank(message = "标签名称不能为空")
    private String name;

    /*  一个标签可能对应很多博客  */
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();
}
