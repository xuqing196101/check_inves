package bss.model.ob;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OBRuleExample implements Serializable {
    /** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;

	protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OBRuleExample() {
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

        public Criteria andIntervalWorkdayIsNull() {
            addCriterion("INTERVAL_WORKDAY is null");
            return (Criteria) this;
        }

        public Criteria andIntervalWorkdayIsNotNull() {
            addCriterion("INTERVAL_WORKDAY is not null");
            return (Criteria) this;
        }

        public Criteria andIntervalWorkdayEqualTo(Integer value) {
            addCriterion("INTERVAL_WORKDAY =", value, "intervalWorkday");
            return (Criteria) this;
        }

        public Criteria andIntervalWorkdayNotEqualTo(Integer value) {
            addCriterion("INTERVAL_WORKDAY <>", value, "intervalWorkday");
            return (Criteria) this;
        }

        public Criteria andIntervalWorkdayGreaterThan(Integer value) {
            addCriterion("INTERVAL_WORKDAY >", value, "intervalWorkday");
            return (Criteria) this;
        }

        public Criteria andIntervalWorkdayGreaterThanOrEqualTo(Integer value) {
            addCriterion("INTERVAL_WORKDAY >=", value, "intervalWorkday");
            return (Criteria) this;
        }

        public Criteria andIntervalWorkdayLessThan(Integer value) {
            addCriterion("INTERVAL_WORKDAY <", value, "intervalWorkday");
            return (Criteria) this;
        }

        public Criteria andIntervalWorkdayLessThanOrEqualTo(Integer value) {
            addCriterion("INTERVAL_WORKDAY <=", value, "intervalWorkday");
            return (Criteria) this;
        }

        public Criteria andIntervalWorkdayIn(List<Integer> values) {
            addCriterion("INTERVAL_WORKDAY in", values, "intervalWorkday");
            return (Criteria) this;
        }

        public Criteria andIntervalWorkdayNotIn(List<Integer> values) {
            addCriterion("INTERVAL_WORKDAY not in", values, "intervalWorkday");
            return (Criteria) this;
        }

        public Criteria andIntervalWorkdayBetween(Integer value1, Integer value2) {
            addCriterion("INTERVAL_WORKDAY between", value1, value2, "intervalWorkday");
            return (Criteria) this;
        }

        public Criteria andIntervalWorkdayNotBetween(Integer value1, Integer value2) {
            addCriterion("INTERVAL_WORKDAY not between", value1, value2, "intervalWorkday");
            return (Criteria) this;
        }

        public Criteria andDefiniteTimeIsNull() {
            addCriterion("DEFINITE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andDefiniteTimeIsNotNull() {
            addCriterion("DEFINITE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andDefiniteTimeEqualTo(Date value) {
            addCriterion("DEFINITE_TIME =", value, "definiteTime");
            return (Criteria) this;
        }

        public Criteria andDefiniteTimeNotEqualTo(Date value) {
            addCriterion("DEFINITE_TIME <>", value, "definiteTime");
            return (Criteria) this;
        }

        public Criteria andDefiniteTimeGreaterThan(Date value) {
            addCriterion("DEFINITE_TIME >", value, "definiteTime");
            return (Criteria) this;
        }

        public Criteria andDefiniteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("DEFINITE_TIME >=", value, "definiteTime");
            return (Criteria) this;
        }

        public Criteria andDefiniteTimeLessThan(Date value) {
            addCriterion("DEFINITE_TIME <", value, "definiteTime");
            return (Criteria) this;
        }

        public Criteria andDefiniteTimeLessThanOrEqualTo(Date value) {
            addCriterion("DEFINITE_TIME <=", value, "definiteTime");
            return (Criteria) this;
        }

        public Criteria andDefiniteTimeIn(List<Date> values) {
            addCriterion("DEFINITE_TIME in", values, "definiteTime");
            return (Criteria) this;
        }

        public Criteria andDefiniteTimeNotIn(List<Date> values) {
            addCriterion("DEFINITE_TIME not in", values, "definiteTime");
            return (Criteria) this;
        }

        public Criteria andDefiniteTimeBetween(Date value1, Date value2) {
            addCriterion("DEFINITE_TIME between", value1, value2, "definiteTime");
            return (Criteria) this;
        }

        public Criteria andDefiniteTimeNotBetween(Date value1, Date value2) {
            addCriterion("DEFINITE_TIME not between", value1, value2, "definiteTime");
            return (Criteria) this;
        }

        public Criteria andQuoteTimeIsNull() {
            addCriterion("QUOTE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andQuoteTimeIsNotNull() {
            addCriterion("QUOTE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andQuoteTimeEqualTo(Integer value) {
            addCriterion("QUOTE_TIME =", value, "quoteTime");
            return (Criteria) this;
        }

        public Criteria andQuoteTimeNotEqualTo(Integer value) {
            addCriterion("QUOTE_TIME <>", value, "quoteTime");
            return (Criteria) this;
        }

        public Criteria andQuoteTimeGreaterThan(Integer value) {
            addCriterion("QUOTE_TIME >", value, "quoteTime");
            return (Criteria) this;
        }

        public Criteria andQuoteTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("QUOTE_TIME >=", value, "quoteTime");
            return (Criteria) this;
        }

        public Criteria andQuoteTimeLessThan(Integer value) {
            addCriterion("QUOTE_TIME <", value, "quoteTime");
            return (Criteria) this;
        }

        public Criteria andQuoteTimeLessThanOrEqualTo(Integer value) {
            addCriterion("QUOTE_TIME <=", value, "quoteTime");
            return (Criteria) this;
        }

        public Criteria andQuoteTimeIn(List<Integer> values) {
            addCriterion("QUOTE_TIME in", values, "quoteTime");
            return (Criteria) this;
        }

        public Criteria andQuoteTimeNotIn(List<Integer> values) {
            addCriterion("QUOTE_TIME not in", values, "quoteTime");
            return (Criteria) this;
        }

        public Criteria andQuoteTimeBetween(Integer value1, Integer value2) {
            addCriterion("QUOTE_TIME between", value1, value2, "quoteTime");
            return (Criteria) this;
        }

        public Criteria andQuoteTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("QUOTE_TIME not between", value1, value2, "quoteTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIsNull() {
            addCriterion("CONFIRM_TIME is null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIsNotNull() {
            addCriterion("CONFIRM_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeEqualTo(Integer value) {
            addCriterion("CONFIRM_TIME =", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotEqualTo(Integer value) {
            addCriterion("CONFIRM_TIME <>", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeGreaterThan(Integer value) {
            addCriterion("CONFIRM_TIME >", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("CONFIRM_TIME >=", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeLessThan(Integer value) {
            addCriterion("CONFIRM_TIME <", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeLessThanOrEqualTo(Integer value) {
            addCriterion("CONFIRM_TIME <=", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIn(List<Integer> values) {
            addCriterion("CONFIRM_TIME in", values, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotIn(List<Integer> values) {
            addCriterion("CONFIRM_TIME not in", values, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeBetween(Integer value1, Integer value2) {
            addCriterion("CONFIRM_TIME between", value1, value2, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("CONFIRM_TIME not between", value1, value2, "confirmTime");
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

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreaterIdIsNull() {
            addCriterion("CREATER_ID is null");
            return (Criteria) this;
        }

        public Criteria andCreaterIdIsNotNull() {
            addCriterion("CREATER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCreaterIdEqualTo(String value) {
            addCriterion("CREATER_ID =", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotEqualTo(String value) {
            addCriterion("CREATER_ID <>", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdGreaterThan(String value) {
            addCriterion("CREATER_ID >", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdGreaterThanOrEqualTo(String value) {
            addCriterion("CREATER_ID >=", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdLessThan(String value) {
            addCriterion("CREATER_ID <", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdLessThanOrEqualTo(String value) {
            addCriterion("CREATER_ID <=", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdLike(String value) {
            addCriterion("CREATER_ID like", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotLike(String value) {
            addCriterion("CREATER_ID not like", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdIn(List<String> values) {
            addCriterion("CREATER_ID in", values, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotIn(List<String> values) {
            addCriterion("CREATER_ID not in", values, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdBetween(String value1, String value2) {
            addCriterion("CREATER_ID between", value1, value2, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotBetween(String value1, String value2) {
            addCriterion("CREATER_ID not between", value1, value2, "createrId");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andBidingCountIsNull() {
            addCriterion("BIDING_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andBidingCountIsNotNull() {
            addCriterion("BIDING_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andBidingCountEqualTo(Integer value) {
            addCriterion("BIDING_COUNT =", value, "bidingCount");
            return (Criteria) this;
        }

        public Criteria andBidingCountNotEqualTo(Integer value) {
            addCriterion("BIDING_COUNT <>", value, "bidingCount");
            return (Criteria) this;
        }

        public Criteria andBidingCountGreaterThan(Integer value) {
            addCriterion("BIDING_COUNT >", value, "bidingCount");
            return (Criteria) this;
        }

        public Criteria andBidingCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("BIDING_COUNT >=", value, "bidingCount");
            return (Criteria) this;
        }

        public Criteria andBidingCountLessThan(Integer value) {
            addCriterion("BIDING_COUNT <", value, "bidingCount");
            return (Criteria) this;
        }

        public Criteria andBidingCountLessThanOrEqualTo(Integer value) {
            addCriterion("BIDING_COUNT <=", value, "bidingCount");
            return (Criteria) this;
        }

        public Criteria andBidingCountIn(List<Integer> values) {
            addCriterion("BIDING_COUNT in", values, "bidingCount");
            return (Criteria) this;
        }

        public Criteria andBidingCountNotIn(List<Integer> values) {
            addCriterion("BIDING_COUNT not in", values, "bidingCount");
            return (Criteria) this;
        }

        public Criteria andBidingCountBetween(Integer value1, Integer value2) {
            addCriterion("BIDING_COUNT between", value1, value2, "bidingCount");
            return (Criteria) this;
        }

        public Criteria andBidingCountNotBetween(Integer value1, Integer value2) {
            addCriterion("BIDING_COUNT not between", value1, value2, "bidingCount");
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
            addCriterion("CREATED_AT =", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotEqualTo(Date value) {
            addCriterion("CREATED_AT <>", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThan(Date value) {
            addCriterion("CREATED_AT >", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATED_AT >=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThan(Date value) {
            addCriterion("CREATED_AT <", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThanOrEqualTo(Date value) {
            addCriterion("CREATED_AT <=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIn(List<Date> values) {
            addCriterion("CREATED_AT in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotIn(List<Date> values) {
            addCriterion("CREATED_AT not in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtBetween(Date value1, Date value2) {
            addCriterion("CREATED_AT between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotBetween(Date value1, Date value2) {
            addCriterion("CREATED_AT not between", value1, value2, "createdAt");
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
            addCriterion("UPDATED_AT =", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotEqualTo(Date value) {
            addCriterion("UPDATED_AT <>", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThan(Date value) {
            addCriterion("UPDATED_AT >", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATED_AT >=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThan(Date value) {
            addCriterion("UPDATED_AT <", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThanOrEqualTo(Date value) {
            addCriterion("UPDATED_AT <=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIn(List<Date> values) {
            addCriterion("UPDATED_AT in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotIn(List<Date> values) {
            addCriterion("UPDATED_AT not in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtBetween(Date value1, Date value2) {
            addCriterion("UPDATED_AT between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotBetween(Date value1, Date value2) {
            addCriterion("UPDATED_AT not between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("NAME like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("NAME not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("NAME in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("NAME not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeSecondIsNull() {
            addCriterion("CONFIRM_TIME_SECOND is null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeSecondIsNotNull() {
            addCriterion("CONFIRM_TIME_SECOND is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeSecondEqualTo(Integer value) {
            addCriterion("CONFIRM_TIME_SECOND =", value, "confirmTimeSecond");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeSecondNotEqualTo(Integer value) {
            addCriterion("CONFIRM_TIME_SECOND <>", value, "confirmTimeSecond");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeSecondGreaterThan(Integer value) {
            addCriterion("CONFIRM_TIME_SECOND >", value, "confirmTimeSecond");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeSecondGreaterThanOrEqualTo(Integer value) {
            addCriterion("CONFIRM_TIME_SECOND >=", value, "confirmTimeSecond");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeSecondLessThan(Integer value) {
            addCriterion("CONFIRM_TIME_SECOND <", value, "confirmTimeSecond");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeSecondLessThanOrEqualTo(Integer value) {
            addCriterion("CONFIRM_TIME_SECOND <=", value, "confirmTimeSecond");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeSecondIn(List<Integer> values) {
            addCriterion("CONFIRM_TIME_SECOND in", values, "confirmTimeSecond");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeSecondNotIn(List<Integer> values) {
            addCriterion("CONFIRM_TIME_SECOND not in", values, "confirmTimeSecond");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeSecondBetween(Integer value1, Integer value2) {
            addCriterion("CONFIRM_TIME_SECOND between", value1, value2, "confirmTimeSecond");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeSecondNotBetween(Integer value1, Integer value2) {
            addCriterion("CONFIRM_TIME_SECOND not between", value1, value2, "confirmTimeSecond");
            return (Criteria) this;
        }

        public Criteria andLeastSupplierNumIsNull() {
            addCriterion("LEAST_SUPPLIER_NUM is null");
            return (Criteria) this;
        }

        public Criteria andLeastSupplierNumIsNotNull() {
            addCriterion("LEAST_SUPPLIER_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andLeastSupplierNumEqualTo(Integer value) {
            addCriterion("LEAST_SUPPLIER_NUM =", value, "leastSupplierNum");
            return (Criteria) this;
        }

        public Criteria andLeastSupplierNumNotEqualTo(Integer value) {
            addCriterion("LEAST_SUPPLIER_NUM <>", value, "leastSupplierNum");
            return (Criteria) this;
        }

        public Criteria andLeastSupplierNumGreaterThan(Integer value) {
            addCriterion("LEAST_SUPPLIER_NUM >", value, "leastSupplierNum");
            return (Criteria) this;
        }

        public Criteria andLeastSupplierNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("LEAST_SUPPLIER_NUM >=", value, "leastSupplierNum");
            return (Criteria) this;
        }

        public Criteria andLeastSupplierNumLessThan(Integer value) {
            addCriterion("LEAST_SUPPLIER_NUM <", value, "leastSupplierNum");
            return (Criteria) this;
        }

        public Criteria andLeastSupplierNumLessThanOrEqualTo(Integer value) {
            addCriterion("LEAST_SUPPLIER_NUM <=", value, "leastSupplierNum");
            return (Criteria) this;
        }

        public Criteria andLeastSupplierNumIn(List<Integer> values) {
            addCriterion("LEAST_SUPPLIER_NUM in", values, "leastSupplierNum");
            return (Criteria) this;
        }

        public Criteria andLeastSupplierNumNotIn(List<Integer> values) {
            addCriterion("LEAST_SUPPLIER_NUM not in", values, "leastSupplierNum");
            return (Criteria) this;
        }

        public Criteria andLeastSupplierNumBetween(Integer value1, Integer value2) {
            addCriterion("LEAST_SUPPLIER_NUM between", value1, value2, "leastSupplierNum");
            return (Criteria) this;
        }

        public Criteria andLeastSupplierNumNotBetween(Integer value1, Integer value2) {
            addCriterion("LEAST_SUPPLIER_NUM not between", value1, value2, "leastSupplierNum");
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