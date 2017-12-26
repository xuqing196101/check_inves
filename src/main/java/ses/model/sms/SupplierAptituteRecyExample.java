package ses.model.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SupplierAptituteRecyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SupplierAptituteRecyExample() {
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

        public Criteria andMatEngIdIsNull() {
            addCriterion("MAT_ENG_ID is null");
            return (Criteria) this;
        }

        public Criteria andMatEngIdIsNotNull() {
            addCriterion("MAT_ENG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMatEngIdEqualTo(String value) {
            addCriterion("MAT_ENG_ID =", value, "matEngId");
            return (Criteria) this;
        }

        public Criteria andMatEngIdNotEqualTo(String value) {
            addCriterion("MAT_ENG_ID <>", value, "matEngId");
            return (Criteria) this;
        }

        public Criteria andMatEngIdGreaterThan(String value) {
            addCriterion("MAT_ENG_ID >", value, "matEngId");
            return (Criteria) this;
        }

        public Criteria andMatEngIdGreaterThanOrEqualTo(String value) {
            addCriterion("MAT_ENG_ID >=", value, "matEngId");
            return (Criteria) this;
        }

        public Criteria andMatEngIdLessThan(String value) {
            addCriterion("MAT_ENG_ID <", value, "matEngId");
            return (Criteria) this;
        }

        public Criteria andMatEngIdLessThanOrEqualTo(String value) {
            addCriterion("MAT_ENG_ID <=", value, "matEngId");
            return (Criteria) this;
        }

        public Criteria andMatEngIdLike(String value) {
            addCriterion("MAT_ENG_ID like", value, "matEngId");
            return (Criteria) this;
        }

        public Criteria andMatEngIdNotLike(String value) {
            addCriterion("MAT_ENG_ID not like", value, "matEngId");
            return (Criteria) this;
        }

        public Criteria andMatEngIdIn(List<String> values) {
            addCriterion("MAT_ENG_ID in", values, "matEngId");
            return (Criteria) this;
        }

        public Criteria andMatEngIdNotIn(List<String> values) {
            addCriterion("MAT_ENG_ID not in", values, "matEngId");
            return (Criteria) this;
        }

        public Criteria andMatEngIdBetween(String value1, String value2) {
            addCriterion("MAT_ENG_ID between", value1, value2, "matEngId");
            return (Criteria) this;
        }

        public Criteria andMatEngIdNotBetween(String value1, String value2) {
            addCriterion("MAT_ENG_ID not between", value1, value2, "matEngId");
            return (Criteria) this;
        }

        public Criteria andCertTypeIsNull() {
            addCriterion("CERT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCertTypeIsNotNull() {
            addCriterion("CERT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCertTypeEqualTo(String value) {
            addCriterion("CERT_TYPE =", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotEqualTo(String value) {
            addCriterion("CERT_TYPE <>", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeGreaterThan(String value) {
            addCriterion("CERT_TYPE >", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeGreaterThanOrEqualTo(String value) {
            addCriterion("CERT_TYPE >=", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeLessThan(String value) {
            addCriterion("CERT_TYPE <", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeLessThanOrEqualTo(String value) {
            addCriterion("CERT_TYPE <=", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeLike(String value) {
            addCriterion("CERT_TYPE like", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotLike(String value) {
            addCriterion("CERT_TYPE not like", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeIn(List<String> values) {
            addCriterion("CERT_TYPE in", values, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotIn(List<String> values) {
            addCriterion("CERT_TYPE not in", values, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeBetween(String value1, String value2) {
            addCriterion("CERT_TYPE between", value1, value2, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotBetween(String value1, String value2) {
            addCriterion("CERT_TYPE not between", value1, value2, "certType");
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

        public Criteria andAptituteSequenceIsNull() {
            addCriterion("APTITUTE_SEQUENCE is null");
            return (Criteria) this;
        }

        public Criteria andAptituteSequenceIsNotNull() {
            addCriterion("APTITUTE_SEQUENCE is not null");
            return (Criteria) this;
        }

        public Criteria andAptituteSequenceEqualTo(String value) {
            addCriterion("APTITUTE_SEQUENCE =", value, "aptituteSequence");
            return (Criteria) this;
        }

        public Criteria andAptituteSequenceNotEqualTo(String value) {
            addCriterion("APTITUTE_SEQUENCE <>", value, "aptituteSequence");
            return (Criteria) this;
        }

        public Criteria andAptituteSequenceGreaterThan(String value) {
            addCriterion("APTITUTE_SEQUENCE >", value, "aptituteSequence");
            return (Criteria) this;
        }

        public Criteria andAptituteSequenceGreaterThanOrEqualTo(String value) {
            addCriterion("APTITUTE_SEQUENCE >=", value, "aptituteSequence");
            return (Criteria) this;
        }

        public Criteria andAptituteSequenceLessThan(String value) {
            addCriterion("APTITUTE_SEQUENCE <", value, "aptituteSequence");
            return (Criteria) this;
        }

        public Criteria andAptituteSequenceLessThanOrEqualTo(String value) {
            addCriterion("APTITUTE_SEQUENCE <=", value, "aptituteSequence");
            return (Criteria) this;
        }

        public Criteria andAptituteSequenceLike(String value) {
            addCriterion("APTITUTE_SEQUENCE like", value, "aptituteSequence");
            return (Criteria) this;
        }

        public Criteria andAptituteSequenceNotLike(String value) {
            addCriterion("APTITUTE_SEQUENCE not like", value, "aptituteSequence");
            return (Criteria) this;
        }

        public Criteria andAptituteSequenceIn(List<String> values) {
            addCriterion("APTITUTE_SEQUENCE in", values, "aptituteSequence");
            return (Criteria) this;
        }

        public Criteria andAptituteSequenceNotIn(List<String> values) {
            addCriterion("APTITUTE_SEQUENCE not in", values, "aptituteSequence");
            return (Criteria) this;
        }

        public Criteria andAptituteSequenceBetween(String value1, String value2) {
            addCriterion("APTITUTE_SEQUENCE between", value1, value2, "aptituteSequence");
            return (Criteria) this;
        }

        public Criteria andAptituteSequenceNotBetween(String value1, String value2) {
            addCriterion("APTITUTE_SEQUENCE not between", value1, value2, "aptituteSequence");
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

        public Criteria andIsMajorFundIsNull() {
            addCriterion("IS_MAJOR_FUND is null");
            return (Criteria) this;
        }

        public Criteria andIsMajorFundIsNotNull() {
            addCriterion("IS_MAJOR_FUND is not null");
            return (Criteria) this;
        }

        public Criteria andIsMajorFundEqualTo(Short value) {
            addCriterion("IS_MAJOR_FUND =", value, "isMajorFund");
            return (Criteria) this;
        }

        public Criteria andIsMajorFundNotEqualTo(Short value) {
            addCriterion("IS_MAJOR_FUND <>", value, "isMajorFund");
            return (Criteria) this;
        }

        public Criteria andIsMajorFundGreaterThan(Short value) {
            addCriterion("IS_MAJOR_FUND >", value, "isMajorFund");
            return (Criteria) this;
        }

        public Criteria andIsMajorFundGreaterThanOrEqualTo(Short value) {
            addCriterion("IS_MAJOR_FUND >=", value, "isMajorFund");
            return (Criteria) this;
        }

        public Criteria andIsMajorFundLessThan(Short value) {
            addCriterion("IS_MAJOR_FUND <", value, "isMajorFund");
            return (Criteria) this;
        }

        public Criteria andIsMajorFundLessThanOrEqualTo(Short value) {
            addCriterion("IS_MAJOR_FUND <=", value, "isMajorFund");
            return (Criteria) this;
        }

        public Criteria andIsMajorFundIn(List<Short> values) {
            addCriterion("IS_MAJOR_FUND in", values, "isMajorFund");
            return (Criteria) this;
        }

        public Criteria andIsMajorFundNotIn(List<Short> values) {
            addCriterion("IS_MAJOR_FUND not in", values, "isMajorFund");
            return (Criteria) this;
        }

        public Criteria andIsMajorFundBetween(Short value1, Short value2) {
            addCriterion("IS_MAJOR_FUND between", value1, value2, "isMajorFund");
            return (Criteria) this;
        }

        public Criteria andIsMajorFundNotBetween(Short value1, Short value2) {
            addCriterion("IS_MAJOR_FUND not between", value1, value2, "isMajorFund");
            return (Criteria) this;
        }

        public Criteria andAptituteContentIsNull() {
            addCriterion("APTITUTE_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andAptituteContentIsNotNull() {
            addCriterion("APTITUTE_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andAptituteContentEqualTo(String value) {
            addCriterion("APTITUTE_CONTENT =", value, "aptituteContent");
            return (Criteria) this;
        }

        public Criteria andAptituteContentNotEqualTo(String value) {
            addCriterion("APTITUTE_CONTENT <>", value, "aptituteContent");
            return (Criteria) this;
        }

        public Criteria andAptituteContentGreaterThan(String value) {
            addCriterion("APTITUTE_CONTENT >", value, "aptituteContent");
            return (Criteria) this;
        }

        public Criteria andAptituteContentGreaterThanOrEqualTo(String value) {
            addCriterion("APTITUTE_CONTENT >=", value, "aptituteContent");
            return (Criteria) this;
        }

        public Criteria andAptituteContentLessThan(String value) {
            addCriterion("APTITUTE_CONTENT <", value, "aptituteContent");
            return (Criteria) this;
        }

        public Criteria andAptituteContentLessThanOrEqualTo(String value) {
            addCriterion("APTITUTE_CONTENT <=", value, "aptituteContent");
            return (Criteria) this;
        }

        public Criteria andAptituteContentLike(String value) {
            addCriterion("APTITUTE_CONTENT like", value, "aptituteContent");
            return (Criteria) this;
        }

        public Criteria andAptituteContentNotLike(String value) {
            addCriterion("APTITUTE_CONTENT not like", value, "aptituteContent");
            return (Criteria) this;
        }

        public Criteria andAptituteContentIn(List<String> values) {
            addCriterion("APTITUTE_CONTENT in", values, "aptituteContent");
            return (Criteria) this;
        }

        public Criteria andAptituteContentNotIn(List<String> values) {
            addCriterion("APTITUTE_CONTENT not in", values, "aptituteContent");
            return (Criteria) this;
        }

        public Criteria andAptituteContentBetween(String value1, String value2) {
            addCriterion("APTITUTE_CONTENT between", value1, value2, "aptituteContent");
            return (Criteria) this;
        }

        public Criteria andAptituteContentNotBetween(String value1, String value2) {
            addCriterion("APTITUTE_CONTENT not between", value1, value2, "aptituteContent");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeIsNull() {
            addCriterion("APTITUTE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeIsNotNull() {
            addCriterion("APTITUTE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeEqualTo(String value) {
            addCriterion("APTITUTE_CODE =", value, "aptituteCode");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeNotEqualTo(String value) {
            addCriterion("APTITUTE_CODE <>", value, "aptituteCode");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeGreaterThan(String value) {
            addCriterion("APTITUTE_CODE >", value, "aptituteCode");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeGreaterThanOrEqualTo(String value) {
            addCriterion("APTITUTE_CODE >=", value, "aptituteCode");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeLessThan(String value) {
            addCriterion("APTITUTE_CODE <", value, "aptituteCode");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeLessThanOrEqualTo(String value) {
            addCriterion("APTITUTE_CODE <=", value, "aptituteCode");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeLike(String value) {
            addCriterion("APTITUTE_CODE like", value, "aptituteCode");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeNotLike(String value) {
            addCriterion("APTITUTE_CODE not like", value, "aptituteCode");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeIn(List<String> values) {
            addCriterion("APTITUTE_CODE in", values, "aptituteCode");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeNotIn(List<String> values) {
            addCriterion("APTITUTE_CODE not in", values, "aptituteCode");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeBetween(String value1, String value2) {
            addCriterion("APTITUTE_CODE between", value1, value2, "aptituteCode");
            return (Criteria) this;
        }

        public Criteria andAptituteCodeNotBetween(String value1, String value2) {
            addCriterion("APTITUTE_CODE not between", value1, value2, "aptituteCode");
            return (Criteria) this;
        }

        public Criteria andAptituteDateIsNull() {
            addCriterion("APTITUTE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andAptituteDateIsNotNull() {
            addCriterion("APTITUTE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andAptituteDateEqualTo(Date value) {
            addCriterionForJDBCDate("APTITUTE_DATE =", value, "aptituteDate");
            return (Criteria) this;
        }

        public Criteria andAptituteDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("APTITUTE_DATE <>", value, "aptituteDate");
            return (Criteria) this;
        }

        public Criteria andAptituteDateGreaterThan(Date value) {
            addCriterionForJDBCDate("APTITUTE_DATE >", value, "aptituteDate");
            return (Criteria) this;
        }

        public Criteria andAptituteDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("APTITUTE_DATE >=", value, "aptituteDate");
            return (Criteria) this;
        }

        public Criteria andAptituteDateLessThan(Date value) {
            addCriterionForJDBCDate("APTITUTE_DATE <", value, "aptituteDate");
            return (Criteria) this;
        }

        public Criteria andAptituteDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("APTITUTE_DATE <=", value, "aptituteDate");
            return (Criteria) this;
        }

        public Criteria andAptituteDateIn(List<Date> values) {
            addCriterionForJDBCDate("APTITUTE_DATE in", values, "aptituteDate");
            return (Criteria) this;
        }

        public Criteria andAptituteDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("APTITUTE_DATE not in", values, "aptituteDate");
            return (Criteria) this;
        }

        public Criteria andAptituteDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("APTITUTE_DATE between", value1, value2, "aptituteDate");
            return (Criteria) this;
        }

        public Criteria andAptituteDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("APTITUTE_DATE not between", value1, value2, "aptituteDate");
            return (Criteria) this;
        }

        public Criteria andAptituteWayIsNull() {
            addCriterion("APTITUTE_WAY is null");
            return (Criteria) this;
        }

        public Criteria andAptituteWayIsNotNull() {
            addCriterion("APTITUTE_WAY is not null");
            return (Criteria) this;
        }

        public Criteria andAptituteWayEqualTo(String value) {
            addCriterion("APTITUTE_WAY =", value, "aptituteWay");
            return (Criteria) this;
        }

        public Criteria andAptituteWayNotEqualTo(String value) {
            addCriterion("APTITUTE_WAY <>", value, "aptituteWay");
            return (Criteria) this;
        }

        public Criteria andAptituteWayGreaterThan(String value) {
            addCriterion("APTITUTE_WAY >", value, "aptituteWay");
            return (Criteria) this;
        }

        public Criteria andAptituteWayGreaterThanOrEqualTo(String value) {
            addCriterion("APTITUTE_WAY >=", value, "aptituteWay");
            return (Criteria) this;
        }

        public Criteria andAptituteWayLessThan(String value) {
            addCriterion("APTITUTE_WAY <", value, "aptituteWay");
            return (Criteria) this;
        }

        public Criteria andAptituteWayLessThanOrEqualTo(String value) {
            addCriterion("APTITUTE_WAY <=", value, "aptituteWay");
            return (Criteria) this;
        }

        public Criteria andAptituteWayLike(String value) {
            addCriterion("APTITUTE_WAY like", value, "aptituteWay");
            return (Criteria) this;
        }

        public Criteria andAptituteWayNotLike(String value) {
            addCriterion("APTITUTE_WAY not like", value, "aptituteWay");
            return (Criteria) this;
        }

        public Criteria andAptituteWayIn(List<String> values) {
            addCriterion("APTITUTE_WAY in", values, "aptituteWay");
            return (Criteria) this;
        }

        public Criteria andAptituteWayNotIn(List<String> values) {
            addCriterion("APTITUTE_WAY not in", values, "aptituteWay");
            return (Criteria) this;
        }

        public Criteria andAptituteWayBetween(String value1, String value2) {
            addCriterion("APTITUTE_WAY between", value1, value2, "aptituteWay");
            return (Criteria) this;
        }

        public Criteria andAptituteWayNotBetween(String value1, String value2) {
            addCriterion("APTITUTE_WAY not between", value1, value2, "aptituteWay");
            return (Criteria) this;
        }

        public Criteria andAptituteStatusIsNull() {
            addCriterion("APTITUTE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andAptituteStatusIsNotNull() {
            addCriterion("APTITUTE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andAptituteStatusEqualTo(Short value) {
            addCriterion("APTITUTE_STATUS =", value, "aptituteStatus");
            return (Criteria) this;
        }

        public Criteria andAptituteStatusNotEqualTo(Short value) {
            addCriterion("APTITUTE_STATUS <>", value, "aptituteStatus");
            return (Criteria) this;
        }

        public Criteria andAptituteStatusGreaterThan(Short value) {
            addCriterion("APTITUTE_STATUS >", value, "aptituteStatus");
            return (Criteria) this;
        }

        public Criteria andAptituteStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("APTITUTE_STATUS >=", value, "aptituteStatus");
            return (Criteria) this;
        }

        public Criteria andAptituteStatusLessThan(Short value) {
            addCriterion("APTITUTE_STATUS <", value, "aptituteStatus");
            return (Criteria) this;
        }

        public Criteria andAptituteStatusLessThanOrEqualTo(Short value) {
            addCriterion("APTITUTE_STATUS <=", value, "aptituteStatus");
            return (Criteria) this;
        }

        public Criteria andAptituteStatusIn(List<Short> values) {
            addCriterion("APTITUTE_STATUS in", values, "aptituteStatus");
            return (Criteria) this;
        }

        public Criteria andAptituteStatusNotIn(List<Short> values) {
            addCriterion("APTITUTE_STATUS not in", values, "aptituteStatus");
            return (Criteria) this;
        }

        public Criteria andAptituteStatusBetween(Short value1, Short value2) {
            addCriterion("APTITUTE_STATUS between", value1, value2, "aptituteStatus");
            return (Criteria) this;
        }

        public Criteria andAptituteStatusNotBetween(Short value1, Short value2) {
            addCriterion("APTITUTE_STATUS not between", value1, value2, "aptituteStatus");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeAtIsNull() {
            addCriterion("APTITUTE_CHANGE_AT is null");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeAtIsNotNull() {
            addCriterion("APTITUTE_CHANGE_AT is not null");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeAtEqualTo(Date value) {
            addCriterionForJDBCDate("APTITUTE_CHANGE_AT =", value, "aptituteChangeAt");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeAtNotEqualTo(Date value) {
            addCriterionForJDBCDate("APTITUTE_CHANGE_AT <>", value, "aptituteChangeAt");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeAtGreaterThan(Date value) {
            addCriterionForJDBCDate("APTITUTE_CHANGE_AT >", value, "aptituteChangeAt");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeAtGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("APTITUTE_CHANGE_AT >=", value, "aptituteChangeAt");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeAtLessThan(Date value) {
            addCriterionForJDBCDate("APTITUTE_CHANGE_AT <", value, "aptituteChangeAt");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeAtLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("APTITUTE_CHANGE_AT <=", value, "aptituteChangeAt");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeAtIn(List<Date> values) {
            addCriterionForJDBCDate("APTITUTE_CHANGE_AT in", values, "aptituteChangeAt");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeAtNotIn(List<Date> values) {
            addCriterionForJDBCDate("APTITUTE_CHANGE_AT not in", values, "aptituteChangeAt");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeAtBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("APTITUTE_CHANGE_AT between", value1, value2, "aptituteChangeAt");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeAtNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("APTITUTE_CHANGE_AT not between", value1, value2, "aptituteChangeAt");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonIsNull() {
            addCriterion("APTITUTE_CHANGE_REASON is null");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonIsNotNull() {
            addCriterion("APTITUTE_CHANGE_REASON is not null");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonEqualTo(String value) {
            addCriterion("APTITUTE_CHANGE_REASON =", value, "aptituteChangeReason");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonNotEqualTo(String value) {
            addCriterion("APTITUTE_CHANGE_REASON <>", value, "aptituteChangeReason");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonGreaterThan(String value) {
            addCriterion("APTITUTE_CHANGE_REASON >", value, "aptituteChangeReason");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonGreaterThanOrEqualTo(String value) {
            addCriterion("APTITUTE_CHANGE_REASON >=", value, "aptituteChangeReason");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonLessThan(String value) {
            addCriterion("APTITUTE_CHANGE_REASON <", value, "aptituteChangeReason");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonLessThanOrEqualTo(String value) {
            addCriterion("APTITUTE_CHANGE_REASON <=", value, "aptituteChangeReason");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonLike(String value) {
            addCriterion("APTITUTE_CHANGE_REASON like", value, "aptituteChangeReason");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonNotLike(String value) {
            addCriterion("APTITUTE_CHANGE_REASON not like", value, "aptituteChangeReason");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonIn(List<String> values) {
            addCriterion("APTITUTE_CHANGE_REASON in", values, "aptituteChangeReason");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonNotIn(List<String> values) {
            addCriterion("APTITUTE_CHANGE_REASON not in", values, "aptituteChangeReason");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonBetween(String value1, String value2) {
            addCriterion("APTITUTE_CHANGE_REASON between", value1, value2, "aptituteChangeReason");
            return (Criteria) this;
        }

        public Criteria andAptituteChangeReasonNotBetween(String value1, String value2) {
            addCriterion("APTITUTE_CHANGE_REASON not between", value1, value2, "aptituteChangeReason");
            return (Criteria) this;
        }

        public Criteria andAttachCertIsNull() {
            addCriterion("ATTACH_CERT is null");
            return (Criteria) this;
        }

        public Criteria andAttachCertIsNotNull() {
            addCriterion("ATTACH_CERT is not null");
            return (Criteria) this;
        }

        public Criteria andAttachCertEqualTo(String value) {
            addCriterion("ATTACH_CERT =", value, "attachCert");
            return (Criteria) this;
        }

        public Criteria andAttachCertNotEqualTo(String value) {
            addCriterion("ATTACH_CERT <>", value, "attachCert");
            return (Criteria) this;
        }

        public Criteria andAttachCertGreaterThan(String value) {
            addCriterion("ATTACH_CERT >", value, "attachCert");
            return (Criteria) this;
        }

        public Criteria andAttachCertGreaterThanOrEqualTo(String value) {
            addCriterion("ATTACH_CERT >=", value, "attachCert");
            return (Criteria) this;
        }

        public Criteria andAttachCertLessThan(String value) {
            addCriterion("ATTACH_CERT <", value, "attachCert");
            return (Criteria) this;
        }

        public Criteria andAttachCertLessThanOrEqualTo(String value) {
            addCriterion("ATTACH_CERT <=", value, "attachCert");
            return (Criteria) this;
        }

        public Criteria andAttachCertLike(String value) {
            addCriterion("ATTACH_CERT like", value, "attachCert");
            return (Criteria) this;
        }

        public Criteria andAttachCertNotLike(String value) {
            addCriterion("ATTACH_CERT not like", value, "attachCert");
            return (Criteria) this;
        }

        public Criteria andAttachCertIn(List<String> values) {
            addCriterion("ATTACH_CERT in", values, "attachCert");
            return (Criteria) this;
        }

        public Criteria andAttachCertNotIn(List<String> values) {
            addCriterion("ATTACH_CERT not in", values, "attachCert");
            return (Criteria) this;
        }

        public Criteria andAttachCertBetween(String value1, String value2) {
            addCriterion("ATTACH_CERT between", value1, value2, "attachCert");
            return (Criteria) this;
        }

        public Criteria andAttachCertNotBetween(String value1, String value2) {
            addCriterion("ATTACH_CERT not between", value1, value2, "attachCert");
            return (Criteria) this;
        }

        public Criteria andCertNameIsNull() {
            addCriterion("CERT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCertNameIsNotNull() {
            addCriterion("CERT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCertNameEqualTo(String value) {
            addCriterion("CERT_NAME =", value, "certName");
            return (Criteria) this;
        }

        public Criteria andCertNameNotEqualTo(String value) {
            addCriterion("CERT_NAME <>", value, "certName");
            return (Criteria) this;
        }

        public Criteria andCertNameGreaterThan(String value) {
            addCriterion("CERT_NAME >", value, "certName");
            return (Criteria) this;
        }

        public Criteria andCertNameGreaterThanOrEqualTo(String value) {
            addCriterion("CERT_NAME >=", value, "certName");
            return (Criteria) this;
        }

        public Criteria andCertNameLessThan(String value) {
            addCriterion("CERT_NAME <", value, "certName");
            return (Criteria) this;
        }

        public Criteria andCertNameLessThanOrEqualTo(String value) {
            addCriterion("CERT_NAME <=", value, "certName");
            return (Criteria) this;
        }

        public Criteria andCertNameLike(String value) {
            addCriterion("CERT_NAME like", value, "certName");
            return (Criteria) this;
        }

        public Criteria andCertNameNotLike(String value) {
            addCriterion("CERT_NAME not like", value, "certName");
            return (Criteria) this;
        }

        public Criteria andCertNameIn(List<String> values) {
            addCriterion("CERT_NAME in", values, "certName");
            return (Criteria) this;
        }

        public Criteria andCertNameNotIn(List<String> values) {
            addCriterion("CERT_NAME not in", values, "certName");
            return (Criteria) this;
        }

        public Criteria andCertNameBetween(String value1, String value2) {
            addCriterion("CERT_NAME between", value1, value2, "certName");
            return (Criteria) this;
        }

        public Criteria andCertNameNotBetween(String value1, String value2) {
            addCriterion("CERT_NAME not between", value1, value2, "certName");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelIsNull() {
            addCriterion("\"APTITUTE_ LEVEL\" is null");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelIsNotNull() {
            addCriterion("\"APTITUTE_ LEVEL\" is not null");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelEqualTo(String value) {
            addCriterion("\"APTITUTE_ LEVEL\" =", value, "aptituteLevel");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelNotEqualTo(String value) {
            addCriterion("\"APTITUTE_ LEVEL\" <>", value, "aptituteLevel");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelGreaterThan(String value) {
            addCriterion("\"APTITUTE_ LEVEL\" >", value, "aptituteLevel");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelGreaterThanOrEqualTo(String value) {
            addCriterion("\"APTITUTE_ LEVEL\" >=", value, "aptituteLevel");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelLessThan(String value) {
            addCriterion("\"APTITUTE_ LEVEL\" <", value, "aptituteLevel");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelLessThanOrEqualTo(String value) {
            addCriterion("\"APTITUTE_ LEVEL\" <=", value, "aptituteLevel");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelLike(String value) {
            addCriterion("\"APTITUTE_ LEVEL\" like", value, "aptituteLevel");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelNotLike(String value) {
            addCriterion("\"APTITUTE_ LEVEL\" not like", value, "aptituteLevel");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelIn(List<String> values) {
            addCriterion("\"APTITUTE_ LEVEL\" in", values, "aptituteLevel");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelNotIn(List<String> values) {
            addCriterion("\"APTITUTE_ LEVEL\" not in", values, "aptituteLevel");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelBetween(String value1, String value2) {
            addCriterion("\"APTITUTE_ LEVEL\" between", value1, value2, "aptituteLevel");
            return (Criteria) this;
        }

        public Criteria andAptituteLevelNotBetween(String value1, String value2) {
            addCriterion("\"APTITUTE_ LEVEL\" not between", value1, value2, "aptituteLevel");
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