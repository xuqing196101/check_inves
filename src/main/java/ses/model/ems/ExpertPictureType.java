package ses.model.ems;

/**
 * Created by yggc on 2017/2/24.
 */
public  enum ExpertPictureType {
    //缴纳社保凭证
    SOCIAL_SECURITY_PROOF {
        @Override
        public Integer getSign() {

            return 1;
        }

    },
    //退休证书或退休凭证
    RETIRE_PROOF {
        @Override
        public Integer getSign() {
            return 2;
        }
    },
    //身份证复印件凭证
    IDENTITY_CARD_PROOF {
        @Override
        public Integer getSign() {
            return 3;
        }
    },

    //技术职称凭证
    TECHNOLOGY_PROOF {
        @Override
        public Integer getSign() {
            return 4;
        }
    },
    //毕业证书凭证
    GRADUATE_PROOF {
        @Override
        public Integer getSign() {
            return 5;
        }
    },
    //学位证书凭证
    QUALIFICATIONS_PROOF {
        @Override
        public Integer getSign() {
            return 6;
        }
    },
    //获奖证书凭证
    AWARD_PROOF {
        @Override
        public Integer getSign() {
            return 7;
        }
    },
    //推荐信凭证
    RECOMMENDATION_PROOF {
        @Override
        public Integer getSign() {
            return 8;
        }
    },
    //执业资格证书凭证
    PRACTICING_REQUIREMENTS_PROOF {
        @Override
        public Integer getSign() {
            return 9;
        }
    },
    //专家申请表凭证
    APPLY_PROOF {
        @Override
        public Integer getSign() {
            return 10;
        }
    },
    //专家承诺书凭证
    ACCEPTANCE_PROOF {
        @Override
        public Integer getSign() {
            return 11;
        }
    },
    //军队人员的身份证
    ARMY_PROOF {
        @Override
        public Integer getSign() {
            return 12;
        }
    },
    //专家申请表
    APPLICATION_PROOF {
        @Override
        public Integer getSign() {
            return 13;
        }
    },
    //专家承诺书
    COMMITMENT_PROOF {
        @Override
        public Integer getSign() {
            return 14;
        }
    };


    public abstract Integer getSign();
}
