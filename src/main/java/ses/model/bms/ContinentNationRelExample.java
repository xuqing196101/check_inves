package ses.model.bms;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ContinentNationRelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ContinentNationRelExample() {
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

        public Criteria andContinentIdIsNull() {
            addCriterion("CONTINENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andContinentIdIsNotNull() {
            addCriterion("CONTINENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andContinentIdEqualTo(String value) {
            addCriterion("CONTINENT_ID =", value, "continentId");
            return (Criteria) this;
        }

        public Criteria andContinentIdNotEqualTo(String value) {
            addCriterion("CONTINENT_ID <>", value, "continentId");
            return (Criteria) this;
        }

        public Criteria andContinentIdGreaterThan(String value) {
            addCriterion("CONTINENT_ID >", value, "continentId");
            return (Criteria) this;
        }

        public Criteria andContinentIdGreaterThanOrEqualTo(String value) {
            addCriterion("CONTINENT_ID >=", value, "continentId");
            return (Criteria) this;
        }

        public Criteria andContinentIdLessThan(String value) {
            addCriterion("CONTINENT_ID <", value, "continentId");
            return (Criteria) this;
        }

        public Criteria andContinentIdLessThanOrEqualTo(String value) {
            addCriterion("CONTINENT_ID <=", value, "continentId");
            return (Criteria) this;
        }

        public Criteria andContinentIdLike(String value) {
            addCriterion("CONTINENT_ID like", value, "continentId");
            return (Criteria) this;
        }

        public Criteria andContinentIdNotLike(String value) {
            addCriterion("CONTINENT_ID not like", value, "continentId");
            return (Criteria) this;
        }

        public Criteria andContinentIdIn(List<String> values) {
            addCriterion("CONTINENT_ID in", values, "continentId");
            return (Criteria) this;
        }

        public Criteria andContinentIdNotIn(List<String> values) {
            addCriterion("CONTINENT_ID not in", values, "continentId");
            return (Criteria) this;
        }

        public Criteria andContinentIdBetween(String value1, String value2) {
            addCriterion("CONTINENT_ID between", value1, value2, "continentId");
            return (Criteria) this;
        }

        public Criteria andContinentIdNotBetween(String value1, String value2) {
            addCriterion("CONTINENT_ID not between", value1, value2, "continentId");
            return (Criteria) this;
        }

        public Criteria andContinentNameIsNull() {
            addCriterion("CONTINENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andContinentNameIsNotNull() {
            addCriterion("CONTINENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andContinentNameEqualTo(String value) {
            addCriterion("CONTINENT_NAME =", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameNotEqualTo(String value) {
            addCriterion("CONTINENT_NAME <>", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameGreaterThan(String value) {
            addCriterion("CONTINENT_NAME >", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameGreaterThanOrEqualTo(String value) {
            addCriterion("CONTINENT_NAME >=", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameLessThan(String value) {
            addCriterion("CONTINENT_NAME <", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameLessThanOrEqualTo(String value) {
            addCriterion("CONTINENT_NAME <=", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameLike(String value) {
            addCriterion("CONTINENT_NAME like", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameNotLike(String value) {
            addCriterion("CONTINENT_NAME not like", value, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameIn(List<String> values) {
            addCriterion("CONTINENT_NAME in", values, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameNotIn(List<String> values) {
            addCriterion("CONTINENT_NAME not in", values, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameBetween(String value1, String value2) {
            addCriterion("CONTINENT_NAME between", value1, value2, "continentName");
            return (Criteria) this;
        }

        public Criteria andContinentNameNotBetween(String value1, String value2) {
            addCriterion("CONTINENT_NAME not between", value1, value2, "continentName");
            return (Criteria) this;
        }

        public Criteria andNationIdIsNull() {
            addCriterion("NATION_ID is null");
            return (Criteria) this;
        }

        public Criteria andNationIdIsNotNull() {
            addCriterion("NATION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andNationIdEqualTo(String value) {
            addCriterion("NATION_ID =", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdNotEqualTo(String value) {
            addCriterion("NATION_ID <>", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdGreaterThan(String value) {
            addCriterion("NATION_ID >", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdGreaterThanOrEqualTo(String value) {
            addCriterion("NATION_ID >=", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdLessThan(String value) {
            addCriterion("NATION_ID <", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdLessThanOrEqualTo(String value) {
            addCriterion("NATION_ID <=", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdLike(String value) {
            addCriterion("NATION_ID like", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdNotLike(String value) {
            addCriterion("NATION_ID not like", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdIn(List<String> values) {
            addCriterion("NATION_ID in", values, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdNotIn(List<String> values) {
            addCriterion("NATION_ID not in", values, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdBetween(String value1, String value2) {
            addCriterion("NATION_ID between", value1, value2, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdNotBetween(String value1, String value2) {
            addCriterion("NATION_ID not between", value1, value2, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationNameIsNull() {
            addCriterion("NATION_NAME is null");
            return (Criteria) this;
        }

        public Criteria andNationNameIsNotNull() {
            addCriterion("NATION_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNationNameEqualTo(String value) {
            addCriterion("NATION_NAME =", value, "nationName");
            return (Criteria) this;
        }

        public Criteria andNationNameNotEqualTo(String value) {
            addCriterion("NATION_NAME <>", value, "nationName");
            return (Criteria) this;
        }

        public Criteria andNationNameGreaterThan(String value) {
            addCriterion("NATION_NAME >", value, "nationName");
            return (Criteria) this;
        }

        public Criteria andNationNameGreaterThanOrEqualTo(String value) {
            addCriterion("NATION_NAME >=", value, "nationName");
            return (Criteria) this;
        }

        public Criteria andNationNameLessThan(String value) {
            addCriterion("NATION_NAME <", value, "nationName");
            return (Criteria) this;
        }

        public Criteria andNationNameLessThanOrEqualTo(String value) {
            addCriterion("NATION_NAME <=", value, "nationName");
            return (Criteria) this;
        }

        public Criteria andNationNameLike(String value) {
            addCriterion("NATION_NAME like", value, "nationName");
            return (Criteria) this;
        }

        public Criteria andNationNameNotLike(String value) {
            addCriterion("NATION_NAME not like", value, "nationName");
            return (Criteria) this;
        }

        public Criteria andNationNameIn(List<String> values) {
            addCriterion("NATION_NAME in", values, "nationName");
            return (Criteria) this;
        }

        public Criteria andNationNameNotIn(List<String> values) {
            addCriterion("NATION_NAME not in", values, "nationName");
            return (Criteria) this;
        }

        public Criteria andNationNameBetween(String value1, String value2) {
            addCriterion("NATION_NAME between", value1, value2, "nationName");
            return (Criteria) this;
        }

        public Criteria andNationNameNotBetween(String value1, String value2) {
            addCriterion("NATION_NAME not between", value1, value2, "nationName");
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

        public Criteria andPositionIsNull() {
            addCriterion("POSITION is null");
            return (Criteria) this;
        }

        public Criteria andPositionIsNotNull() {
            addCriterion("POSITION is not null");
            return (Criteria) this;
        }

        public Criteria andPositionEqualTo(Short value) {
            addCriterion("POSITION =", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotEqualTo(Short value) {
            addCriterion("POSITION <>", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThan(Short value) {
            addCriterion("POSITION >", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThanOrEqualTo(Short value) {
            addCriterion("POSITION >=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThan(Short value) {
            addCriterion("POSITION <", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThanOrEqualTo(Short value) {
            addCriterion("POSITION <=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionIn(List<Short> values) {
            addCriterion("POSITION in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotIn(List<Short> values) {
            addCriterion("POSITION not in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionBetween(Short value1, Short value2) {
            addCriterion("POSITION between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotBetween(Short value1, Short value2) {
            addCriterion("POSITION not between", value1, value2, "position");
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