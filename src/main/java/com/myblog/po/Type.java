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
@Table(name = "t_type")
public class Type {

    /*  类型的主键  */
    @Id
    @GeneratedValue
    private Long id;

    /*  类型名称  */
    @NotBlank(message = "分类名称不能为空")
    private String name;

    /*  此类型对应的博客数量  */
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();
}
