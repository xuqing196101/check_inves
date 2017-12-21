package ses.model.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SupplierItemRecyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SupplierItemRecyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNull() {
            addCriterion("SUPPLIER_ID is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNotNull() {
            addCriterion("SUPPLIER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdEqualTo(String value) {
            addCriterion("SUPPLIER_ID =", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotEqualTo(String value) {
            addCriterion("SUPPLIER_ID <>", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThan(String value) {
            addCriterion("SUPPLIER_ID >", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLIER_ID >=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThan(String value) {
            addCriterion("SUPPLIER_ID <", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(String value) {
            addCriterion("SUPPLIER_ID <=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLike(String value) {
            addCriterion("SUPPLIER_ID like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotLike(String value) {
            addCriterion("SUPPLIER_ID not like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIn(List<String> values) {
            addCriterion("SUPPLIER_ID in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotIn(List<String> values) {
            addCriterion("SUPPLIER_ID not in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdBetween(String value1, String value2) {
            addCriterion("SUPPLIER_ID between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotBetween(String value1, String value2) {
            addCriterion("SUPPLIER_ID not between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNull() {
            addCriterion("CATEGORY_ID is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNotNull() {
            addCriterion("CATEGORY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdEqualTo(String value) {
            addCriterion("CATEGORY_ID =", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotEqualTo(String value) {
            addCriterion("CATEGORY_ID <>", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThan(String value) {
            addCriterion("CATEGORY_ID >", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThanOrEqualTo(String value) {
            addCriterion("CATEGORY_ID >=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThan(String value) {
            addCriterion("CATEGORY_ID <", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThanOrEqualTo(String value) {
            addCriterion("CATEGORY_ID <=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLike(String value) {
            addCriterion("CATEGORY_ID like", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotLike(String value) {
            addCriterion("CATEGORY_ID not like", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIn(List<String> values) {
            addCriterion("CATEGORY_ID in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotIn(List<String> values) {
            addCriterion("CATEGORY_ID not in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdBetween(String value1, String value2) {
            addCriterion("CATEGORY_ID between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotBetween(String value1, String value2) {
            addCriterion("CATEGORY_ID not between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdIsNull() {
            addCriterion("SUPPLIER_TYPE_RELATE_ID is null");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdIsNotNull() {
            addCriterion("SUPPLIER_TYPE_RELATE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdEqualTo(String value) {
            addCriterion("SUPPLIER_TYPE_RELATE_ID =", value, "supplierTypeRelateId");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdNotEqualTo(String value) {
            addCriterion("SUPPLIER_TYPE_RELATE_ID <>", value, "supplierTypeRelateId");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdGreaterThan(String value) {
            addCriterion("SUPPLIER_TYPE_RELATE_ID >", value, "supplierTypeRelateId");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLIER_TYPE_RELATE_ID >=", value, "supplierTypeRelateId");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdLessThan(String value) {
            addCriterion("SUPPLIER_TYPE_RELATE_ID <", value, "supplierTypeRelateId");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdLessThanOrEqualTo(String value) {
            addCriterion("SUPPLIER_TYPE_RELATE_ID <=", value, "supplierTypeRelateId");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdLike(String value) {
            addCriterion("SUPPLIER_TYPE_RELATE_ID like", value, "supplierTypeRelateId");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdNotLike(String value) {
            addCriterion("SUPPLIER_TYPE_RELATE_ID not like", value, "supplierTypeRelateId");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdIn(List<String> values) {
            addCriterion("SUPPLIER_TYPE_RELATE_ID in", values, "supplierTypeRelateId");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdNotIn(List<String> values) {
            addCriterion("SUPPLIER_TYPE_RELATE_ID not in", values, "supplierTypeRelateId");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdBetween(String value1, String value2) {
            addCriterion("SUPPLIER_TYPE_RELATE_ID between", value1, value2, "supplierTypeRelateId");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeRelateIdNotBetween(String value1, String value2) {
            addCriterion("SUPPLIER_TYPE_RELATE_ID not between", value1, value2, "supplierTypeRelateId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNull() {
            addCriterion("CREATED_AT is null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNotNull() {
            addCriterion("CREATED_AT is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtEqualTo(Date value) {
            addCriterionForJDBCDate("CREATED_AT =", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotEqualTo(Date value) {
            addCriterionForJDBCDate("CREATED_AT <>", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThan(Date value) {
            addCriterionForJDBCDate("CREATED_AT >", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CREATED_AT >=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThan(Date value) {
            addCriterionForJDBCDate("CREATED_AT <", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CREATED_AT <=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIn(List<Date> values) {
            addCriterionForJDBCDate("CREATED_AT in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotIn(List<Date> values) {
            addCriterionForJDBCDate("CREATED_AT not in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CREATED_AT between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CREATED_AT not between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNull() {
            addCriterion("UPDATED_AT is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNotNull() {
            addCriterion("UPDATED_AT is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtEqualTo(Date value) {
            addCriterionForJDBCDate("UPDATED_AT =", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotEqualTo(Date value) {
            addCriterionForJDBCDate("UPDATED_AT <>", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThan(Date value) {
            addCriterionForJDBCDate("UPDATED_AT >", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("UPDATED_AT >=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThan(Date value) {
            addCriterionForJDBCDate("UPDATED_AT <", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("UPDATED_AT <=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIn(List<Date> values) {
            addCriterionForJDBCDate("UPDATED_AT in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotIn(List<Date> values) {
            addCriterionForJDBCDate("UPDATED_AT not in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("UPDATED_AT between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("UPDATED_AT not between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andCateLevelIsNull() {
            addCriterion("CATE_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andCateLevelIsNotNull() {
            addCriterion("CATE_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andCateLevelEqualTo(String value) {
            addCriterion("CATE_LEVEL =", value, "cateLevel");
            return (Criteria) this;
        }

        public Criteria andCateLevelNotEqualTo(String value) {
            addCriterion("CATE_LEVEL <>", value, "cateLevel");
            return (Criteria) this;
        }

        public Criteria andCateLevelGreaterThan(String value) {
            addCriterion("CATE_LEVEL >", value, "cateLevel");
            return (Criteria) this;
        }

        public Criteria andCateLevelGreaterThanOrEqualTo(String value) {
            addCriterion("CATE_LEVEL >=", value, "cateLevel");
            return (Criteria) this;
        }

        public Criteria andCateLevelLessThan(String value) {
            addCriterion("CATE_LEVEL <", value, "cateLevel");
            return (Criteria) this;
        }

        public Criteria andCateLevelLessThanOrEqualTo(String value) {
            addCriterion("CATE_LEVEL <=", value, "cateLevel");
            return (Criteria) this;
        }

        public Criteria andCateLevelLike(String value) {
            addCriterion("CATE_LEVEL like", value, "cateLevel");
            return (Criteria) this;
        }

        public Criteria andCateLevelNotLike(String value) {
            addCriterion("CATE_LEVEL not like", value, "cateLevel");
            return (Criteria) this;
        }

        public Criteria andCateLevelIn(List<String> values) {
            addCriterion("CATE_LEVEL in", values, "cateLevel");
            return (Criteria) this;
        }

        public Criteria andCateLevelNotIn(List<String> values) {
            addCriterion("CATE_LEVEL not in", values, "cateLevel");
            return (Criteria) this;
        }

        public Criteria andCateLevelBetween(String value1, String value2) {
            addCriterion("CATE_LEVEL between", value1, value2, "cateLevel");
            return (Criteria) this;
        }

        public Criteria andCateLevelNotBetween(String value1, String value2) {
            addCriterion("CATE_LEVEL not between", value1, value2, "cateLevel");
            return (Criteria) this;
        }

        public Criteria andCertCodeIsNull() {
            addCriterion("CERT_CODE is null");
            return (Criteria) this;
        }

        public Criteria andCertCodeIsNotNull() {
            addCriterion("CERT_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCertCodeEqualTo(String value) {
            addCriterion("CERT_CODE =", value, "certCode");
            return (Criteria) this;
        }

        public Criteria andCertCodeNotEqualTo(String value) {
            addCriterion("CERT_CODE <>", value, "certCode");
            return (Criteria) this;
        }

        public Criteria andCertCodeGreaterThan(String value) {
            addCriterion("CERT_CODE >", value, "certCode");
            return (Criteria) this;
        }

        public Criteria andCertCodeGreaterThanOrEqualTo(String value) {
            addCriterion("CERT_CODE >=", value, "certCode");
            return (Criteria) this;
        }

        public Criteria andCertCodeLessThan(String value) {
            addCriterion("CERT_CODE <", value, "certCode");
            return (Criteria) this;
        }

        public Criteria andCertCodeLessThanOrEqualTo(String value) {
            addCriterion("CERT_CODE <=", value, "certCode");
            return (Criteria) this;
        }

        public Criteria andCertCodeLike(String value) {
            addCriterion("CERT_CODE like", value, "certCode");
            return (Criteria) this;
        }

        public Criteria andCertCodeNotLike(String value) {
            addCriterion("CERT_CODE not like", value, "certCode");
            return (Criteria) this;
        }

        public Criteria andCertCodeIn(List<String> values) {
            addCriterion("CERT_CODE in", values, "certCode");
            return (Criteria) this;
        }

        public Criteria andCertCodeNotIn(List<String> values) {
            addCriterion("CERT_CODE not in", values, "certCode");
            return (Criteria) this;
        }

        public Criteria andCertCodeBetween(String value1, String value2) {
            addCriterion("CERT_CODE between", value1, value2, "certCode");
            return (Criteria) this;
        }

        public Criteria andCertCodeNotBetween(String value1, String value2) {
            addCriterion("CERT_CODE not between", value1, value2, "certCode");
            return (Criteria) this;
        }

        public Criteria andDiyLevelIsNull() {
            addCriterion("DIY_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andDiyLevelIsNotNull() {
            addCriterion("DIY_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andDiyLevelEqualTo(String value) {
            addCriterion("DIY_LEVEL =", value, "diyLevel");
            return (Criteria) this;
        }

        public Criteria andDiyLevelNotEqualTo(String value) {
            addCriterion("DIY_LEVEL <>", value, "diyLevel");
            return (Criteria) this;
        }

        public Criteria andDiyLevelGreaterThan(String value) {
            addCriterion("DIY_LEVEL >", value, "diyLevel");
            return (Criteria) this;
        }

        public Criteria andDiyLevelGreaterThanOrEqualTo(String value) {
            addCriterion("DIY_LEVEL >=", value, "diyLevel");
            return (Criteria) this;
        }

        public Criteria andDiyLevelLessThan(String value) {
            addCriterion("DIY_LEVEL <", value, "diyLevel");
            return (Criteria) this;
        }

        public Criteria andDiyLevelLessThanOrEqualTo(String value) {
            addCriterion("DIY_LEVEL <=", value, "diyLevel");
            return (Criteria) this;
        }

        public Criteria andDiyLevelLike(String value) {
            addCriterion("DIY_LEVEL like", value, "diyLevel");
            return (Criteria) this;
        }

        public Criteria andDiyLevelNotLike(String value) {
            addCriterion("DIY_LEVEL not like", value, "diyLevel");
            return (Criteria) this;
        }

        public Criteria andDiyLevelIn(List<String> values) {
            addCriterion("DIY_LEVEL in", values, "diyLevel");
            return (Criteria) this;
        }

        public Criteria andDiyLevelNotIn(List<String> values) {
            addCriterion("DIY_LEVEL not in", values, "diyLevel");
            return (Criteria) this;
        }

        public Criteria andDiyLevelBetween(String value1, String value2) {
            addCriterion("DIY_LEVEL between", value1, value2, "diyLevel");
            return (Criteria) this;
        }

        public Criteria andDiyLevelNotBetween(String value1, String value2) {
            addCriterion("DIY_LEVEL not between", value1, value2, "diyLevel");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeIsNull() {
            addCriterion("QUALIFICATION_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeIsNotNull() {
            addCriterion("QUALIFICATION_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeEqualTo(String value) {
            addCriterion("QUALIFICATION_TYPE =", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeNotEqualTo(String value) {
            addCriterion("QUALIFICATION_TYPE <>", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeGreaterThan(String value) {
            addCriterion("QUALIFICATION_TYPE >", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeGreaterThanOrEqualTo(String value) {
            addCriterion("QUALIFICATION_TYPE >=", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeLessThan(String value) {
            addCriterion("QUALIFICATION_TYPE <", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeLessThanOrEqualTo(String value) {
            addCriterion("QUALIFICATION_TYPE <=", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeLike(String value) {
            addCriterion("QUALIFICATION_TYPE like", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeNotLike(String value) {
            addCriterion("QUALIFICATION_TYPE not like", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeIn(List<String> values) {
            addCriterion("QUALIFICATION_TYPE in", values, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeNotIn(List<String> values) {
            addCriterion("QUALIFICATION_TYPE not in", values, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeBetween(String value1, String value2) {
            addCriterion("QUALIFICATION_TYPE between", value1, value2, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeNotBetween(String value1, String value2) {
            addCriterion("QUALIFICATION_TYPE not between", value1, value2, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andProfessTypeIsNull() {
            addCriterion("PROFESS_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andProfessTypeIsNotNull() {
            addCriterion("PROFESS_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andProfessTypeEqualTo(String value) {
            addCriterion("PROFESS_TYPE =", value, "professType");
            return (Criteria) this;
        }

        public Criteria andProfessTypeNotEqualTo(String value) {
            addCriterion("PROFESS_TYPE <>", value, "professType");
            return (Criteria) this;
        }

        public Criteria andProfessTypeGreaterThan(String value) {
            addCriterion("PROFESS_TYPE >", value, "professType");
            return (Criteria) this;
        }

        public Criteria andProfessTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PROFESS_TYPE >=", value, "professType");
            return (Criteria) this;
        }

        public Criteria andProfessTypeLessThan(String value) {
            addCriterion("PROFESS_TYPE <", value, "professType");
            return (Criteria) this;
        }

        public Criteria andProfessTypeLessThanOrEqualTo(String value) {
            addCriterion("PROFESS_TYPE <=", value, "professType");
            return (Criteria) this;
        }

        public Criteria andProfessTypeLike(String value) {
            addCriterion("PROFESS_TYPE like", value, "professType");
            return (Criteria) this;
        }

        public Criteria andProfessTypeNotLike(String value) {
            addCriterion("PROFESS_TYPE not like", value, "professType");
            return (Criteria) this;
        }

        public Criteria andProfessTypeIn(List<String> values) {
            addCriterion("PROFESS_TYPE in", values, "professType");
            return (Criteria) this;
        }

        public Criteria andProfessTypeNotIn(List<String> values) {
            addCriterion("PROFESS_TYPE not in", values, "professType");
            return (Criteria) this;
        }

        public Criteria andProfessTypeBetween(String value1, String value2) {
            addCriterion("PROFESS_TYPE between", value1, value2, "professType");
            return (Criteria) this;
        }

        public Criteria andProfessTypeNotBetween(String value1, String value2) {
            addCriterion("PROFESS_TYPE not between", value1, value2, "professType");
            return (Criteria) this;
        }

        public Criteria andNodeLevelIsNull() {
            addCriterion("NODE_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andNodeLevelIsNotNull() {
            addCriterion("NODE_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andNodeLevelEqualTo(Short value) {
            addCriterion("NODE_LEVEL =", value, "nodeLevel");
            return (Criteria) this;
        }

        public Criteria andNodeLevelNotEqualTo(Short value) {
            addCriterion("NODE_LEVEL <>", value, "nodeLevel");
            return (Criteria) this;
        }

        public Criteria andNodeLevelGreaterThan(Short value) {
            addCriterion("NODE_LEVEL >", value, "nodeLevel");
            return (Criteria) this;
        }

        public Criteria andNodeLevelGreaterThanOrEqualTo(Short value) {
            addCriterion("NODE_LEVEL >=", value, "nodeLevel");
            return (Criteria) this;
        }

        public Criteria andNodeLevelLessThan(Short value) {
            addCriterion("NODE_LEVEL <", value, "nodeLevel");
            return (Criteria) this;
        }

        public Criteria andNodeLevelLessThanOrEqualTo(Short value) {
            addCriterion("NODE_LEVEL <=", value, "nodeLevel");
            return (Criteria) this;
        }

        public Criteria andNodeLevelIn(List<Short> values) {
            addCriterion("NODE_LEVEL in", values, "nodeLevel");
            return (Criteria) this;
        }

        public Criteria andNodeLevelNotIn(List<Short> values) {
            addCriterion("NODE_LEVEL not in", values, "nodeLevel");
            return (Criteria) this;
        }

        public Criteria andNodeLevelBetween(Short value1, Short value2) {
            addCriterion("NODE_LEVEL between", value1, value2, "nodeLevel");
            return (Criteria) this;
        }

        public Criteria andNodeLevelNotBetween(Short value1, Short value2) {
            addCriterion("NODE_LEVEL not between", value1, value2, "nodeLevel");
            return (Criteria) this;
        }

        public Criteria andIsReturnedIsNull() {
            addCriterion("IS_RETURNED is null");
            return (Criteria) this;
        }

        public Criteria andIsReturnedIsNotNull() {
            addCriterion("IS_RETURNED is not null");
            return (Criteria) this;
        }

        public Criteria andIsReturnedEqualTo(Short value) {
            addCriterion("IS_RETURNED =", value, "isReturned");
            return (Criteria) this;
        }

        public Criteria andIsReturnedNotEqualTo(Short value) {
            addCriterion("IS_RETURNED <>", value, "isReturned");
            return (Criteria) this;
        }

        public Criteria andIsReturnedGreaterThan(Short value) {
            addCriterion("IS_RETURNED >", value, "isReturned");
            return (Criteria) this;
        }

        public Criteria andIsReturnedGreaterThanOrEqualTo(Short value) {
            addCriterion("IS_RETURNED >=", value, "isReturned");
            return (Criteria) this;
        }

        public Criteria andIsReturnedLessThan(Short value) {
            addCriterion("IS_RETURNED <", value, "isReturned");
            return (Criteria) this;
        }

        public Criteria andIsReturnedLessThanOrEqualTo(Short value) {
            addCriterion("IS_RETURNED <=", value, "isReturned");
            return (Criteria) this;
        }

        public Criteria andIsReturnedIn(List<Short> values) {
            addCriterion("IS_RETURNED in", values, "isReturned");
            return (Criteria) this;
        }

        public Criteria andIsReturnedNotIn(List<Short> values) {
            addCriterion("IS_RETURNED not in", values, "isReturned");
            return (Criteria) this;
        }

        public Criteria andIsReturnedBetween(Short value1, Short value2) {
            addCriterion("IS_RETURNED between", value1, value2, "isReturned");
            return (Criteria) this;
        }

        public Criteria andIsReturnedNotBetween(Short value1, Short value2) {
            addCriterion("IS_RETURNED not between", value1, value2, "isReturned");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("IS_DELETED is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("IS_DELETED is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Short value) {
            addCriterion("IS_DELETED =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Short value) {
            addCriterion("IS_DELETED <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Short value) {
            addCriterion("IS_DELETED >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Short value) {
            addCriterion("IS_DELETED >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Short value) {
            addCriterion("IS_DELETED <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Short value) {
            addCriterion("IS_DELETED <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Short> values) {
            addCriterion("IS_DELETED in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Short> values) {
            addCriterion("IS_DELETED not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Short value1, Short value2) {
            addCriterion("IS_DELETED between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Short value1, Short value2) {
            addCriterion("IS_DELETED not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andRecyTimeIsNull() {
            addCriterion("RECY_TIME is null");
            return (Criteria) this;
        }

        public Criteria andRecyTimeIsNotNull() {
            addCriterion("RECY_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andRecyTimeEqualTo(Date value) {
            addCriterionForJDBCDate("RECY_TIME =", value, "recyTime");
            return (Criteria) this;
        }

        public Criteria andRecyTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("RECY_TIME <>", value, "recyTime");
            return (Criteria) this;
        }

        public Criteria andRecyTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("RECY_TIME >", value, "recyTime");
            return (Criteria) this;
        }

        public Criteria andRecyTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("RECY_TIME >=", value, "recyTime");
            return (Criteria) this;
        }

        public Criteria andRecyTimeLessThan(Date value) {
            addCriterionForJDBCDate("RECY_TIME <", value, "recyTime");
            return (Criteria) this;
        }

        public Criteria andRecyTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("RECY_TIME <=", value, "recyTime");
            return (Criteria) this;
        }

        public Criteria andRecyTimeIn(List<Date> values) {
            addCriterionForJDBCDate("RECY_TIME in", values, "recyTime");
            return (Criteria) this;
        }

        public Criteria andRecyTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("RECY_TIME not in", values, "recyTime");
            return (Criteria) this;
        }

        public Criteria andRecyTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("RECY_TIME between", value1, value2, "recyTime");
            return (Criteria) this;
        }

        public Criteria andRecyTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("RECY_TIME not between", value1, value2, "recyTime");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdIsNull() {
            addCriterion("RECY_APT_ID is null");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdIsNotNull() {
            addCriterion("RECY_APT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdEqualTo(String value) {
            addCriterion("RECY_APT_ID =", value, "recyAptId");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdNotEqualTo(String value) {
            addCriterion("RECY_APT_ID <>", value, "recyAptId");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdGreaterThan(String value) {
            addCriterion("RECY_APT_ID >", value, "recyAptId");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdGreaterThanOrEqualTo(String value) {
            addCriterion("RECY_APT_ID >=", value, "recyAptId");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdLessThan(String value) {
            addCriterion("RECY_APT_ID <", value, "recyAptId");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdLessThanOrEqualTo(String value) {
            addCriterion("RECY_APT_ID <=", value, "recyAptId");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdLike(String value) {
            addCriterion("RECY_APT_ID like", value, "recyAptId");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdNotLike(String value) {
            addCriterion("RECY_APT_ID not like", value, "recyAptId");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdIn(List<String> values) {
            addCriterion("RECY_APT_ID in", values, "recyAptId");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdNotIn(List<String> values) {
            addCriterion("RECY_APT_ID not in", values, "recyAptId");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdBetween(String value1, String value2) {
            addCriterion("RECY_APT_ID between", value1, value2, "recyAptId");
            return (Criteria) this;
        }

        public Criteria andRecyAptIdNotBetween(String value1, String value2) {
            addCriterion("RECY_APT_ID not between", value1, value2, "recyAptId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}