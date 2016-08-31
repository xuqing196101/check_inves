package yggc.model;

import java.util.Date;

public class SupplierTypes {
    private Long id;

    private String name;

    private String ancestry;

    private Long ancestryDepth;

    private Long orderNum;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAncestry() {
        return ancestry;
    }

    public void setAncestry(String ancestry) {
        this.ancestry = ancestry == null ? null : ancestry.trim();
    }

    public Long getAncestryDepth() {
        return ancestryDepth;
    }

    public void setAncestryDepth(Long ancestryDepth) {
        this.ancestryDepth = ancestryDepth;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}