package com.kickshaw.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author kickshaw
 * @since 2021-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String roleName;

    private String description;

    private String resourceIds;

    private List<String> resourceNames;

    private String resourceNamesStr;

    private Boolean status;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 将resourceIds转换为list集合
     * @Param []
     * @Return java.util.List<java.lang.Integer>
     */
    public List<Integer> getResourceIdsList() {
        List<Integer> resourcesIdsList = new ArrayList<>();
        if(!getResourceIds().isEmpty()) {
            String[] ids = resourceIds.split(",");
            for (String id : ids) {
                resourcesIdsList.add(Integer.valueOf(id));
            }
        }
        return resourcesIdsList;
    }

    /*public String getReourceNamesStr() {
        if(!resourceNames.isEmpty()) {
            resourceNamesStr = String.join("," , resourceNames);
        }
        return resourceNamesStr;
    }*/
}
