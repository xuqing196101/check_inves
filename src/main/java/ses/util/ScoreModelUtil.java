package ses.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.util.FloatUtil;
import bss.model.ppms.ParamInterval;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.SupplyMark;
import bss.service.ppms.ParamIntervalService;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>根据模型计算评分
 * <详细描述>
 * @author   tiankf
 * @version  
 * @since
 * @see
 */
@Component
public class ScoreModelUtil {
    
    @Autowired
    private ParamIntervalService paramIntervalService;
    
    private static ScoreModelUtil scoreModelUtil;
   
    public void setParamIntervalService(ParamIntervalService paramIntervalService) {
        this.paramIntervalService = paramIntervalService;
    }

    @PostConstruct 
    public void init() {  
        scoreModelUtil = this;  
        scoreModelUtil.paramIntervalService = this.paramIntervalService;  
    }  
    
    public static double getQuantizateScore(ScoreModel scoreModel,Double number,Double flag){
        double score = 0 ;
        if(scoreModel.getTypeName()!=null && !scoreModel.getTypeName().equals("") && scoreModel.getTypeName().equals("0")){
            score = getScoreByModelOne(scoreModel,flag);
        }else if (scoreModel.getTypeName().equals("1")) {
            score = getScoreByModelTwo(scoreModel,number);
        }else if (scoreModel.getTypeName().equals("6")){
            score  = getScoreByModelSeven(scoreModel, number);
        }else if (scoreModel.getTypeName().equals("7")){
            score  = getScoreByModelEight(scoreModel, number);
        }else if (scoreModel.getTypeName().equals("8")){
          score  = number;
        }
        return score;
    }
    /**
     * 
     * @Title: getQuantizateScore
     * @author: Tian Kunfeng
     * @date: 2016-11-7 下午5:09:27
     * @Description: 是否判断类型
     * @param: @param scoreModel
     * @param: @param 
     * @param: @param flag 0 不满足条件  0分   1 满足条件  的标准分值
     * @param: @return
     * @return: int
     */
    public static double getScoreByModelOne(ScoreModel scoreModel,Double flag){
        double sc = 0 ;
        if(flag!=null && flag == 1.0 && scoreModel.getStandardScore()!=null && !scoreModel.getStandardScore().equals("")){
            sc = FloatUtil.round(Double.parseDouble(scoreModel.getStandardScore()), 4);
        }
        return sc;
    }
    
    /**
     * 
     * @Title: getScoreByModelTwo
     * @author: Tian Kunfeng
     * @date: 2016-11-8 下午3:28:13
     * @Description: 模型2  按项加减分        计算公式   
     * 0    加分类型    分值= 起始分值 + 满足项树 * 每项得分      最高不超过最高分    最低不低于最低分   
     * 1 减分类型        分值  = 基准分值 -  满足项树 * 每项得分      最高不超过最高分    最低不低于最低分    
     * @param: @param scoreModel 模型
     * @param: @param number  满足项树
     * @param: @return
     * @return: double
     */
    public static double getScoreByModelTwo(ScoreModel scoreModel,Double number){
        double sc = 0 ;
        double unitScore = (scoreModel.getUnitScore()!=null && !scoreModel.getUnitScore().equals("") )? Double.parseDouble(scoreModel.getUnitScore()):0;
        // 0加分      1减分
        if(number==null){
            return sc;
        }
          if(scoreModel.getAddSubtractTypeName()!=null && scoreModel.getAddSubtractTypeName().equals("0")){
            if(number!=null){
                  sc = number * (new Double(unitScore));
                  if (sc >= (new Double(scoreModel.getMaxScore()))) {
                      sc = new Double(scoreModel.getMaxScore());
                  }
              }
              
          }else if (scoreModel.getAddSubtractTypeName().equals("1")) {
            if(scoreModel.getScoreType()!=0){
              if(number!=null){
                sc = new Double(scoreModel.getMaxScore()) - number * unitScore;
                if (sc <= (new Double(scoreModel.getMinScore()))) {
                    sc = new Double(scoreModel.getMinScore());
                }
              }
            }else{
              sc = new Double(scoreModel.getMaxScore()) - number * unitScore;
            }
          }
       
        return sc;
    }
    
    /**
     * 
     * @Title: getScoreByModelThree
     * @author: Songbiaowei
     * @date: 2016-11-8 下午3:28:55
     * @Description: 模型三    评审数额最高递减 计算公式   
     *              减分类型：    评审参数最高的最高分  依次递减每单位得分     最低不能低于最低分
     * @param: @param scoreModel
     * @param: @param number
     * @param: @return
     * @return: List<SupplyMark>
     */
    @SuppressWarnings("unchecked")
    public static List<SupplyMark> getScoreByModelThree(ScoreModel scoreModel, ArrayList<SupplyMark> supplyMarkList){
        if(supplyMarkList!=null && supplyMarkList.size()>0){
            Collections.sort(supplyMarkList, new SortByParam());
            int num = 0;
            double maxScore = (scoreModel.getMaxScore()!=null &&!scoreModel.getMaxScore().equals("") ) ?Double.parseDouble(scoreModel.getMaxScore()) :0;
            Integer isHave = scoreModel.getIsHave();
            Integer relation = scoreModel.getRelation();
            String type = scoreModel.getAddSubtractTypeName();
            String standScore = scoreModel.getStandScores();
            maxScore = FloatUtil.round(maxScore, 4);
            for(int i=0 ;i<supplyMarkList.size();i++){
               if ("1".equals(type)) {
                   /**有基准数额 且 减分  与基准数关系大于等于*/
                   if (isHave == 0 && relation == 0) {
                       if (standScore != null) {
                           if (new Double(supplyMarkList.get(i).getPrarm()) == 0 ){
                               supplyMarkList.get(i).setScore(0);
                           }else if (new Double(supplyMarkList.get(i).getPrarm()) >= (new Double(standScore))){
                               supplyMarkList.get(i).setScore(new Double(scoreModel.getMaxScore()));
                               num ++;
                           }else {
                               //看看比标准分低几个档次，
                               double s = 0 ;
                               if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                                   s = supplyMarkList.get(i - 1).getScore();
                                   num ++;
                               } else {
                                   s = new Double(scoreModel.getMaxScore()) - (i - num + 1)*(new Double(scoreModel.getUnitScore()));
                               }
                               if (s >= 0) {
                                   supplyMarkList.get(i).setScore(s);
                               } else {
                                   supplyMarkList.get(i).setScore(new Double(scoreModel.getMinScore()));
                               }
                          }
                       } 
                   } else{
                       /**有基准数额 且 减分  与基准数关系小于等于*/
                        if (standScore != null) {
                            if (new Double(supplyMarkList.get(i).getPrarm()) == 0 ){
                                supplyMarkList.get(i).setScore(0);
                            }else if (new Double(supplyMarkList.get(i).getPrarm()) <= (new Double(standScore))){
                              supplyMarkList.get(i).setScore(new Double(scoreModel.getMinScore()));
                              num ++;
                            }else {
                              double s = 0 ;
                              if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                                  s = supplyMarkList.get(i - 1).getScore();
                                  num ++;
                              } else {
                                  s = new Double(scoreModel.getMaxScore()) - (i - num + 1)*(new Double(scoreModel.getUnitScore()));
                              }
                              if (s >= 0) {
                                  supplyMarkList.get(i).setScore(s);
                              } else {
                                  supplyMarkList.get(i).setScore(new Double(scoreModel.getMinScore()));
                              }
                         }
                        } else {
                            //模型三减分  最高分到最低分
                            if (supplyMarkList.get(i).getPrarm() == 0) {
                                supplyMarkList.get(i).setScore(0);
                            } else {
                               /**当两个分数一样的时候 的一样得分-- 模型三减分的情况下  最高分2分   参数是 3 3 1 1 得分是 2 2 1.9 1.9--num的作用就是避免2 2 1.8 1.8*/
                                double s = 0 ;
                                if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                                    s = supplyMarkList.get(i - 1).getScore();
                                    num ++;
                                } else {
                                    s = new Double(scoreModel.getMaxScore()) - (i - num)*(new Double(scoreModel.getUnitScore()));
                                }
                                if (s >= 0) {
                                    supplyMarkList.get(i).setScore(s);
                                } else {
                                    supplyMarkList.get(i).setScore(new Double(scoreModel.getMinScore()));
                                }
                            }
                           
                        }
                   } 
               } else {
                   /**有基准数额 且 加分  与基准数关系小于等于*/
                   if (isHave == 0 && relation == 1 ) {
                       if (standScore != null) {
                           if (new Double(supplyMarkList.get(i).getPrarm()) == 0 ){
                               supplyMarkList.get(i).setScore(0);
                           } else if (new Double(supplyMarkList.get(i).getPrarm()) <= (new Double(standScore))){
                               supplyMarkList.get(i).setScore(new Double(scoreModel.getMaxScore()));
                               num ++;
                           }else {
                               double s = 0;
                               if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                                   s = supplyMarkList.get(i - 1).getScore();
                                   num ++;
                               } else {
                                   s = (i - num + 1)*(new Double(scoreModel.getUnitScore()));
                               }
                               if (s >= new Double(scoreModel.getMaxScore())) {
                                   supplyMarkList.get(i).setScore(new Double(scoreModel.getMaxScore()));
                               } else {
                                   supplyMarkList.get(i).setScore(s);
                               }
                          }
                         } 
                   } else {
                       /**有基准数额 且 加分  与基准数关系大于等于*/
                       if (standScore != null) {
                           if (new Double(supplyMarkList.get(i).getPrarm()) == 0 ){
                               supplyMarkList.get(i).setScore(0);
                           } else if (new Double(supplyMarkList.get(i).getPrarm()) >= (new Double(standScore))){
                               supplyMarkList.get(i).setScore(new Double(scoreModel.getMinScore()));
                               num ++;
                           }else {
                               double s = 0;
                               if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                                   s = supplyMarkList.get(i - 1).getScore();
                                   num ++;
                               } else {
                                   s = (i - num + 1)*(new Double(scoreModel.getUnitScore()));
                               }
                               if (s >= new Double(scoreModel.getMaxScore())) {
                                   supplyMarkList.get(i).setScore(new Double(scoreModel.getMaxScore()));
                               } else {
                                   supplyMarkList.get(i).setScore(s);
                               }
                          }
                       } else {
                           //模型三加分 最低分到最高分
                           if (supplyMarkList.get(i).getPrarm() == 0) {
                               supplyMarkList.get(i).setScore(0);
                           } else {
                               /**当两个分数一样的时候 的一样得分-- 模型三加分的情况下  最高分2分   参数是1 1 2 2 得分是 0.1 0.1 0.2 0.2 --num的作用就是避免0.1 0.1 0.3 0.3*/
                               double s = 0;
                               if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                                   s = supplyMarkList.get(i - 1).getScore();
                                   num ++;
                               } else {
                                   s = (i - num)*(new Double(scoreModel.getUnitScore()));
                               }
                               if (s >= new Double(scoreModel.getMaxScore())) {
                                   supplyMarkList.get(i).setScore(new Double(scoreModel.getMaxScore()));
                               } else {
                                   supplyMarkList.get(i).setScore(s);
                               }
                           }
                       }
                   }
               }
                       
            }
            return supplyMarkList;
        }
        return null;
        
    }
    /**
     * 
     * @Title: getScoreByModelThree
     * @author: Tian Kunfeng
     * @date: 2016-11-8 下午3:28:55
     * @Description: 模型4    评审数额最低递增计算公式   
     *              加分类型：    评审参数最高的最低分  依次递加每单位得分     最高不高于最高分
     *              降序排列
     *              排名相同  得分一样
     * @param: @param scoreModel
     * @param: @param number
     * @param: @return
     * @return: List<SupplyMark>
     */
    @SuppressWarnings("unchecked")
    public static List<SupplyMark> getScoreByModelFour(ScoreModel scoreModel,ArrayList<SupplyMark> supplyMarkList){
        if(supplyMarkList!=null && supplyMarkList.size()>0){
            Collections.sort(supplyMarkList, new SortByParam());
            Collections.reverse(supplyMarkList);
            int num = 0;
            double minScore = ( scoreModel.getMinScore()!=null && !scoreModel.getMinScore().equals("") ) ?Double.parseDouble(scoreModel.getMinScore()) :0;
            minScore = FloatUtil.round(minScore, 4);
            Integer isHave = scoreModel.getIsHave();
            Integer relation = scoreModel.getRelation();
            String type = scoreModel.getAddSubtractTypeName();
            String standScore = scoreModel.getStandScores();
            for(int i=0 ;i<supplyMarkList.size();i++){
                if ("1".equals(type)) {
                    /**有基准数额 且 减分  与基准数关系大于等于*/
                    if (isHave == 0 && relation == 0) {
                        if (standScore != null) {
                            if (new Double(supplyMarkList.get(i).getPrarm()) >= (new Double(standScore))){
                                supplyMarkList.get(i).setScore(new Double(scoreModel.getMinScore()));
                                num ++;
                            }else {
                                //看看比标准分低几个档次，
                                double s = 0 ;
                                if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                                    s = supplyMarkList.get(i - 1).getScore();
                                    num ++;
                                } else {
                                    s = new Double(scoreModel.getMaxScore()) - (i - num + 1)*(new Double(scoreModel.getUnitScore()));
                                }
                                if (s >= 0) {
                                    supplyMarkList.get(i).setScore(s);
                                } else {
                                    supplyMarkList.get(i).setScore(new Double(scoreModel.getMinScore()));
                                }
                           }
                        } 
                    } else{
                        /**有基准数额 且 减分  与基准数关系小于等于*/
                         if (standScore != null) {
                             if (new Double(supplyMarkList.get(i).getPrarm()) <= (new Double(standScore))){
                               supplyMarkList.get(i).setScore(new Double(scoreModel.getMaxScore()));
                               num ++;
                             }else {
                               double s = 0 ;
                               if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                                   s = supplyMarkList.get(i - 1).getScore();
                                   num ++;
                               } else {
                                   s = new Double(scoreModel.getMaxScore()) - (i - num + 1)*(new Double(scoreModel.getUnitScore()));
                               }
                               if (s >= 0) {
                                   supplyMarkList.get(i).setScore(s);
                               } else {
                                   supplyMarkList.get(i).setScore(new Double(scoreModel.getMinScore()));
                               }
                          }
                         } else {
                             //模型三减分  最高分到最低分
                                /**当两个分数一样的时候 的一样得分-- 模型三减分的情况下  最高分2分   参数是 3 3 1 1 得分是 2 2 1.9 1.9--num的作用就是避免2 2 1.8 1.8*/
                                 double s = 0 ;
                                 if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                                     s = supplyMarkList.get(i - 1).getScore();
                                     num ++;
                                 } else {
                                     s = new Double(scoreModel.getMaxScore()) - (i - num)*(new Double(scoreModel.getUnitScore()));
                                 }
                                 if (s >= 0) {
                                     supplyMarkList.get(i).setScore(s);
                                 } else {
                                     supplyMarkList.get(i).setScore(new Double(scoreModel.getMinScore()));
                                 }  
                         }
                    } 
                } else {
                    /**有基准数额 且 加分  与基准数关系小于等于*/
                    if (isHave == 0 && relation == 1 ) {
                        if (standScore != null) {
                           if (new Double(supplyMarkList.get(i).getPrarm()) <= (new Double(standScore))){
                                supplyMarkList.get(i).setScore(new Double(scoreModel.getMinScore()));
                                num ++;
                            }else {
                                double s = 0;
                                if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                                    s = supplyMarkList.get(i - 1).getScore();
                                    num ++;
                                } else {
                                    s = (i - num + 1)*(new Double(scoreModel.getUnitScore()));
                                }
                                if (s >= new Double(scoreModel.getMaxScore())) {
                                    supplyMarkList.get(i).setScore(new Double(scoreModel.getMaxScore()));
                                } else {
                                    supplyMarkList.get(i).setScore(s);
                                }
                           }
                          } 
                    } else {
                        /**有基准数额 且 加分  与基准数关系大于等于*/
                        if (standScore != null) {
                            if (new Double(supplyMarkList.get(i).getPrarm()) >= (new Double(standScore))){
                                supplyMarkList.get(i).setScore(new Double(scoreModel.getMaxScore()));
                                num ++;
                            }else {
                                double s = 0;
                                if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                                    s = supplyMarkList.get(i - 1).getScore();
                                    num ++;
                                } else {
                                    s = (i - num + 1)*(new Double(scoreModel.getUnitScore()));
                                }
                                if (s >= new Double(scoreModel.getMaxScore())) {
                                    supplyMarkList.get(i).setScore(new Double(scoreModel.getMaxScore()));
                                } else {
                                    supplyMarkList.get(i).setScore(s);
                                }
                           }
                        } else {
                            //模型三加分 最低分到最高分
                            /**当两个分数一样的时候 的一样得分-- 模型三加分的情况下  最高分2分   参数是1 1 2 2 得分是 0.1 0.1 0.2 0.2 --num的作用就是避免0.1 0.1 0.3 0.3*/
                            double s = 0;
                            if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                                s = supplyMarkList.get(i - 1).getScore();
                                num ++;
                            } else {
                                s = (i - num)*(new Double(scoreModel.getUnitScore()));
                            }
                            if (s >= new Double(scoreModel.getMaxScore())) {
                                supplyMarkList.get(i).setScore(new Double(scoreModel.getMaxScore()));
                            } else {
                                supplyMarkList.get(i).setScore(s);
                            }
                        }
                    }
                }
                        
             }
             return supplyMarkList;
         }
         return null;
    }
    
    
    @SuppressWarnings("unchecked")
    public static List<SupplyMark> getScoreByModelFourB(ScoreModel scoreModel,ArrayList<SupplyMark> supplyMarkList){
        if(supplyMarkList!=null && supplyMarkList.size()>0){
            Collections.sort(supplyMarkList, new SortByParam());
            Collections.reverse(supplyMarkList);
            double minScore = ( scoreModel.getMinScore()!=null && !scoreModel.getMinScore().equals("") ) ?Double.parseDouble(scoreModel.getMinScore()) :0;
            minScore = FloatUtil.round(minScore, 4);
            String type = scoreModel.getAddSubtractTypeName();
            int num = 0;
            for(int i=0 ;i<supplyMarkList.size();i++){
                if ("1".equals(type)) {
                        /*double s = new Double(scoreModel.getMaxScore()) - i*(new Double(scoreModel.getUnitScore()));
                        if (new Double(supplyMarkList.get(i).getPrarm()) == 0 ){
                            supplyMarkList.get(i).setScore(0);
                        } else if (s >= 0) {
                            supplyMarkList.get(i).setScore(s);
                        } else {
                            supplyMarkList.get(i).setScore(new Double(scoreModel.getMinScore()));
                        }*/
                        double s = 0 ;
                        if (new Double(supplyMarkList.get(i).getPrarm()) == 0 ){
                            supplyMarkList.get(i).setScore(0);
                            num ++;
                        } else if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                            s = supplyMarkList.get(i - 1).getScore();
                            num ++;
                        } else {
                            s = new Double(scoreModel.getMaxScore()) - (i - num)*(new Double(scoreModel.getUnitScore()));
                        }
                        if (s >= 0) {
                            supplyMarkList.get(i).setScore(s);
                        } else {
                            supplyMarkList.get(i).setScore(new Double(scoreModel.getMinScore()));
                        }  
                } else {
                        /*double s = i*(new Double(scoreModel.getUnitScore()));
                        if (new Double(supplyMarkList.get(i).getPrarm()) == 0 ){
                            supplyMarkList.get(i).setScore(0);
                        } else if (s >= new Double(scoreModel.getMaxScore())) {
                            supplyMarkList.get(i).setScore(new Double(scoreModel.getMaxScore()));
                        } else {
                            supplyMarkList.get(i).setScore(s);
                        }*/
                        double s = 0;
                        if (new Double(supplyMarkList.get(i).getPrarm()) == 0 ){
                            supplyMarkList.get(i).setScore(0);
                            num++;
                        } else if (i > 0 && supplyMarkList.get(i - 1).getPrarm() == supplyMarkList.get(i).getPrarm()) {
                            s = supplyMarkList.get(i - 1).getScore();
                            num ++;
                        } else {
                            s = (i - num)*(new Double(scoreModel.getUnitScore()));
                        }
                        if (s >= new Double(scoreModel.getMaxScore())) {
                            supplyMarkList.get(i).setScore(new Double(scoreModel.getMaxScore()));
                        } else {
                            supplyMarkList.get(i).setScore(s);
                        }
                }
            }
            return supplyMarkList;
        }
        return null;
    }
    /**
     * 
     * @Title: getScoreByModelFive
     * @author: Tian Kunfeng
     * @date: 2016-11-8 下午7:07:07
     * @Description: 模型5 评审数额高计算
     *  计算公式    评审参数最高为基准数    得分= (评审参数/基准数 ) * 标准分值
     * @param: @param scoreModel
     * @param: @param supplyMarkList
     * @param: @param sm   评审参数最高的
     * @param: @return
     * @return: List<SupplyMark>
     */
    @SuppressWarnings("unchecked")
    public static List<SupplyMark> getScoreByModelFive(ScoreModel scoreModel,ArrayList<SupplyMark> supplyMarkList,SupplyMark sm){
        double reviewScore = sm.getPrarm();// 基准分值 最高分
        reviewScore = FloatUtil.round(reviewScore, 4);
        int zero = new Double(reviewScore).compareTo(new Double(0));
        if(supplyMarkList!=null && supplyMarkList.size()>0 && zero>0){
            //Collections.sort(supplyMarkList, new SortByParam());//降序排列   第一个为最高分
            double standardScore = (scoreModel.getStandardScore()!=null && !scoreModel.getStandardScore().equals(""))? Double.parseDouble(scoreModel.getStandardScore()):0;
            for(int i=0 ;i<supplyMarkList.size();i++){
                if(new Double(supplyMarkList.get(i).getPrarm()).compareTo(new Double(reviewScore))==0){
                    supplyMarkList.get(i).setScore(Double.parseDouble(scoreModel.getStandardScore()));
                    continue;
                }
                double score = FloatUtil.div(FloatUtil.mul(supplyMarkList.get(i).getPrarm(), standardScore), reviewScore) ;
                score = FloatUtil.round(score, 4);
                supplyMarkList.get(i).setScore(score);
            }
        }
        return supplyMarkList;
    }
    /**
     * 
     * @Title: getScoreByModelSix
     * @author: Tian Kunfeng
     * @date: 2016-11-8 下午8:11:16
     * @Description:  模型6  评审数额低计算
     * 计算公式    评审参数最低为基准数    得分= (基准数/评审参数 ) * 标准分值
     * @param: @param scoreModel
     * @param: @param supplyMarkList
     * @param:  sm   评审参数最低的
     * @param: @return
     * @return: List<SupplyMark>
     */
    @SuppressWarnings("unchecked")
    public static List<SupplyMark> getScoreByModelSix(ScoreModel scoreModel,ArrayList<SupplyMark> supplyMarkList,SupplyMark sm){
        double reviewScore = sm.getPrarm();//最低为基准分值
        reviewScore = FloatUtil.round(reviewScore, 4);
        if(supplyMarkList!=null && supplyMarkList.size()>0){
            //Collections.sort(supplyMarkList, new SortByParam());//降序排列   第一个为最高分
            double standardScore = (scoreModel.getStandardScore()!=null && !scoreModel.getStandardScore().equals(""))? Double.parseDouble(scoreModel.getStandardScore()):0;
            for(int i=0 ;i<supplyMarkList.size();i++){
                double score = FloatUtil.div(FloatUtil.mul(reviewScore, standardScore), supplyMarkList.get(i).getPrarm() );
                score = FloatUtil.round(score, 4);
                supplyMarkList.get(i).setScore(score);
            }
        }
        return supplyMarkList;
    }
    /***
     * 
     * @Title: getScoreByModelSeven
     * @author: Tian Kunfeng
     * @date: 2016-11-9 上午10:59:21
     * @Description: 模型7  高计算   左开右闭区间   第一个不包括          基准数值  < 评审截止数
     * 等额   1 加分区间  区间内取值          低于评审基准数值为最低分0分，区间内  按计算规则递增(]  高于评审截止数   的最高分
     *     2 减分区间   区间内取值      低于评审基准数的最高分，区间内  等额递减计算    高于评审截止数   得分最低分0分
                                                                       
     * @param: @param scoreModel
     * @param: @param number
     * @param: @return
     * @return: double
     */
    public static double getScoreByModelSeven(ScoreModel scoreModel,double number){
        double sc =0 ;
        //评审基准数
        double reviewStandScore = (scoreModel.getReviewStandScore()!=null &&! scoreModel.getReviewStandScore().equals(""))?Double.parseDouble(scoreModel.getReviewStandScore()):0;
        //0等额区间    1不等额区间
        String intervalTypeName = scoreModel.getIntervalTypeName();
        //加减分类型  0 加分 1减分
        String addSubtractTypeName = scoreModel.getAddSubtractTypeName();
        //每个区间之间的差额，用于等额区间模型
        double intervalNumber = (scoreModel.getIntervalNumber()!=null && !scoreModel.getIntervalNumber().equals(""))?Double.parseDouble(scoreModel.getIntervalNumber()) : 0 ;
        //加减分分值
        double score = (scoreModel.getUnitScore()!=null &&! scoreModel.equals(""))?Double.parseDouble(scoreModel.getUnitScore()):0;
        //如果加分，高于截止数为满分，如果减分，低于截止数为0分
        double deadlineNumber = (scoreModel.getDeadlineNumber()!=null &&!scoreModel.getDeadlineNumber().equals(""))?Double.parseDouble(scoreModel.getDeadlineNumber()): 0 ;
        double maxScore = (scoreModel.getMaxScore()!=null && !scoreModel.getMaxScore().equals(""))?Double.parseDouble(scoreModel.getMaxScore()):0;
        double minScore = (scoreModel.getMinScore()!=null && !scoreModel.getMinScore().equals(""))?Double.parseDouble(scoreModel.getMinScore()):0;
        if(intervalTypeName!=null && !intervalTypeName.equals("") && intervalTypeName.equals("0")){
            //jiafen
            if(addSubtractTypeName!=null &&! addSubtractTypeName.equals("") && addSubtractTypeName.equals("0")){
                if(new Double(number).compareTo(new Double(reviewStandScore)) <0){
                    sc = FloatUtil.round(0, 4);
                }else if (new Double(number).compareTo(new Double(deadlineNumber)) >0) {
                    sc = FloatUtil.round(maxScore, 4);
                }else {
                    int floor = (int) ((deadlineNumber-reviewStandScore)/intervalNumber) +1;
                    Double dNumber = new Double(number);
                    Double dDeadlineNumber = new Double(deadlineNumber);
                    Double dReviewStandScore = new Double(reviewStandScore);
                    if( dNumber.compareTo(dReviewStandScore) >=0 && dNumber.compareTo(dDeadlineNumber)<=0){
                        for(int i=0;i<floor;i++){
                            if(dNumber.compareTo(new Double(FloatUtil.add(reviewStandScore, FloatUtil.mul(i+1, intervalNumber)))) <0){
                                sc = FloatUtil.mul(i, score);
                                sc = getDeadlineScore(sc, maxScore, 0);
                                sc = FloatUtil.add(0, sc);
                                break;
                            }
                        }
                    }
                }
            }else if (addSubtractTypeName.equals("1")) {//jianfen
                if(new Double(number).compareTo(new Double(reviewStandScore)) <0){
                    sc = FloatUtil.round(maxScore, 4);
                }else if (new Double(number).compareTo(new Double(deadlineNumber)) >0) {
                    sc = FloatUtil.round(0, 4);
                }else {
                    int floor = (int) ((deadlineNumber-reviewStandScore)/intervalNumber) +1;
                    Double dNumber = new Double(number);
                    Double dDeadlineNumber = new Double(deadlineNumber);
                    Double dReviewStandScore = new Double(reviewStandScore);
                    if( dNumber.compareTo(dReviewStandScore) >=0 && dNumber.compareTo(dDeadlineNumber)<=0){
                        for(int i=0;i<floor;i++){
                            if(dNumber.compareTo(new Double(FloatUtil.add(reviewStandScore, FloatUtil.mul(i+1, intervalNumber)))) <0){
                                sc = FloatUtil.mul(i, score);
                                sc = getDeadlineScore(sc, minScore, 1);
                                sc = FloatUtil.sub(maxScore, sc);
                                if (sc < 0) {
                                    sc = 0;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }else if (intervalTypeName.equals("1")) {
            ParamInterval paramInterval = new ParamInterval();
            paramInterval.setScoreModelId(scoreModel.getId());
            List<ParamInterval> paramIntervalList = scoreModelUtil.paramIntervalService.findListByParamInterval(paramInterval);
            //List<ParamInterval> paramIntervalList = new ArrayList<ParamInterval>();
            Double num = new Double(number);
            for(ParamInterval p:paramIntervalList){
                Double  startParam = new Double(p.getStartParam());
                Double endParam = new Double(p.getEndParam());
                String spRelation = p.getStartRelation();
                String epRelation = p.getEndRelation();
                if("0".equals(spRelation) && "0".equals(epRelation)) {
                    if(num.compareTo(startParam) >0 && num.compareTo(endParam) <0){
                        sc = Double.parseDouble(p.getScore());
                        break;
                    }
                }
                if("0".equals(spRelation) && "1".equals(epRelation)) {
                    if(num.compareTo(startParam) >0 && num.compareTo(endParam) <=0){
                        sc = Double.parseDouble(p.getScore());
                        break;
                    }
                } 
                if("1".equals(spRelation) && "0".equals(epRelation)) {
                    if(num.compareTo(startParam) >=0 && num.compareTo(endParam) <0){
                        sc = Double.parseDouble(p.getScore());
                        break;
                    }             
                }
                if("1".equals(spRelation) && "1".equals(epRelation)) {
                    if(num.compareTo(startParam) >=0 && num.compareTo(endParam) <=0){
                        sc = Double.parseDouble(p.getScore());
                        break;
                    }             
                }
            }
        }
        return sc;
    }
    /**
     * 
     * @Title: getScoreByModelEight
     * @author: Tian Kunfeng
     * @date: 2016-11-11 上午11:32:30
     * @Description: 模型8  低计算   左开右闭区间   第一个不包括          基准数值  > 评审截止数
     * 等额   1 加分区间  区间内取值          高于评审基准数值为最低分0分，区间内  按计算规则递增(]  低于评审截止数   的最高分
     *     2 减分区间   区间内取值      高于评审基准数的最高分，区间内  等额递减计算    低于评审截止数   得分最低分0分
                
     * @param: @param scoreModel
     * @param: @param number
     * @param: @return
     * @return: double
     */
    public static double getScoreByModelEight(ScoreModel scoreModel,double number){

        double sc =0 ;
        //评审基准数
        double reviewStandScore = (scoreModel.getReviewStandScore()!=null &&! scoreModel.getReviewStandScore().equals(""))?Double.parseDouble(scoreModel.getReviewStandScore()):0;
        //0等额区间    1不等额区间
        String intervalTypeName = scoreModel.getIntervalTypeName();
        //加减分类型  0 加分 1减分
        String addSubtractTypeName = scoreModel.getAddSubtractTypeName();
        //每个区间之间的差额，用于等额区间模型
        double intervalNumber = (scoreModel.getIntervalNumber()!=null && !scoreModel.getIntervalNumber().equals(""))?Double.parseDouble(scoreModel.getIntervalNumber()) : 0 ;
        //加减分分值
        double score = (scoreModel.getUnitScore()!=null &&! scoreModel.equals(""))?Double.parseDouble(scoreModel.getUnitScore()):0;
        //如果加分，高于截止数为满分，如果减分，低于截止数为0分
        double deadlineNumber = (scoreModel.getDeadlineNumber()!=null &&!scoreModel.getDeadlineNumber().equals(""))?Double.parseDouble(scoreModel.getDeadlineNumber()): 0 ;
        double maxScore = (scoreModel.getMaxScore()!=null && !scoreModel.getMaxScore().equals(""))?Double.parseDouble(scoreModel.getMaxScore()):0;
        double minScore = (scoreModel.getMinScore()!=null && !scoreModel.getMinScore().equals(""))?Double.parseDouble(scoreModel.getMinScore()):0;
        if(intervalTypeName!=null && !intervalTypeName.equals("") && intervalTypeName.equals("0")){
            //jiafen
            if(addSubtractTypeName!=null &&! addSubtractTypeName.equals("") && addSubtractTypeName.equals("0")){
                //模型八加法没问题了
                if(number == 0){
                    sc = 0;
                } else if (new Double(number).compareTo(new Double(deadlineNumber)) <=0){
                    sc = FloatUtil.round(maxScore, 4);
                }else if (new Double(number).compareTo(new Double(reviewStandScore)) >=0) {
                    sc = FloatUtil.round(0, 4);
                }else {
                    int floor = (int) ((reviewStandScore - deadlineNumber)/intervalNumber) +1;
                    Double dNumber = new Double(number);
                    Double dDeadlineNumber = new Double(deadlineNumber);
                    Double dReviewStandScore = new Double(reviewStandScore);
                    if( dNumber.compareTo(dDeadlineNumber) >=0 && dNumber.compareTo(dReviewStandScore)<=0){
                        for(int i=0;i<floor;i++){
                            if(dNumber.compareTo(new Double(FloatUtil.sub(reviewStandScore, FloatUtil.mul(i+1, intervalNumber)))) >0){
                                sc = FloatUtil.mul(i, score);
                                sc = getDeadlineScore(sc, maxScore, 0);
                                sc = FloatUtil.add(0, sc);
                                break;
                            }
                        }
                    }
                }
            }else if (addSubtractTypeName.equals("1")) {//jianfen
                //模型八减法没问题了
                if(number == 0){
                    sc = 0;
                } else if(new Double(number).compareTo(new Double(reviewStandScore)) >=0){
                    sc = FloatUtil.round(maxScore, 4);
                }else if (new Double(number).compareTo(new Double(deadlineNumber)) <0) {
                    sc = FloatUtil.round(0, 4);
                }else {
                    int floor = (int) ((reviewStandScore-deadlineNumber)/intervalNumber) +1;
                    Double dNumber = new Double(number);
                    Double dDeadlineNumber = new Double(deadlineNumber);
                    Double dReviewStandScore = new Double(reviewStandScore);
                    if( dNumber.compareTo(dDeadlineNumber) >=0 && dNumber.compareTo(dReviewStandScore)<=0){
                        for(int i=0;i<floor;i++){
                            if(dNumber.compareTo(new Double(FloatUtil.sub(reviewStandScore, FloatUtil.mul(i+1, intervalNumber)))) >=0){
                                sc = FloatUtil.mul(i+1, score);
                                sc = getDeadlineScore(sc, minScore, 1);
                                sc = FloatUtil.sub(maxScore, sc);
                                if (sc < 0) {
                                    sc = 0;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }else if (intervalTypeName.equals("1")) {
            ParamInterval paramInterval = new ParamInterval();
            paramInterval.setScoreModelId(scoreModel.getId());
            List<ParamInterval> paramIntervalList = scoreModelUtil.paramIntervalService.findListByParamInterval(paramInterval);
            Double num = new Double(number);
            for(ParamInterval p:paramIntervalList){
                Double  startParam = new Double(p.getStartParam());
                Double endParam = new Double(p.getEndParam());
                String spRelation = p.getStartRelation();
                String epRelation = p.getEndRelation();
                if("0".equals(spRelation) && "0".equals(epRelation)) {
                    if(num.compareTo(startParam) >0 && num.compareTo(endParam) <0){
                        sc = Double.parseDouble(p.getScore());
                        break;
                    }
                }
                if("0".equals(spRelation) && "1".equals(epRelation)) {
                    if(num.compareTo(startParam) >0 && num.compareTo(endParam) <=0){
                        sc = Double.parseDouble(p.getScore());
                        break;
                    }
                } 
                if("1".equals(spRelation) && "0".equals(epRelation)) {
                    if(num.compareTo(startParam) >=0 && num.compareTo(endParam) <0){
                        sc = Double.parseDouble(p.getScore());
                        break;
                    }             
                }
                if("1".equals(spRelation) && "1".equals(epRelation)) {
                    if(num.compareTo(startParam) >=0 && num.compareTo(endParam) <=0){
                        sc = Double.parseDouble(p.getScore());
                        break;
                    }             
                }
            }
        }
        return sc;
    
    }
    //
    /**
     * 
     * @Title: isNumber
     * @author: Tian Kunfeng
     * @date: 2016-11-7 下午6:16:41
     * @Description: 判断是否是数字字符串
     * @param: @param str
     * @param: @return
     * @return: boolean
     */
    public static boolean isNumber(String str) {
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("[0-9]*");
        java.util.regex.Matcher match=pattern.matcher(str);
        if(match.matches()==false)
        {
             return false;
        }
        else
        {
             return true;
        }
    }
    /**
     * 
     * @Title: getDeadlineScore
     * @author: Tian Kunfeng
     * @date: 2016-11-7 下午6:20:56
     * @Description: 最高不可以高于最高分   最低不可低于最低分
     * @param: @param cuurScore 当前得分
     * @param: @param deadlineScoe  截止数
     * @param: @param type 0 加分  1减分
     * @param: @return
     * @return: int
     */
    public static double getDeadlineScore(double cuurScore,double deadlineScoe,Integer type){
        //double score=0;
        if(type!=null && type.equals(0)){
            if(FloatUtil.sub(cuurScore, deadlineScoe)>0){
            	cuurScore = deadlineScoe;
            }
        }else if (type.equals(1)) {
            if (FloatUtil.sub(cuurScore, deadlineScoe)<0) {
                cuurScore = deadlineScoe;
            }
        }
        return cuurScore;
    }
    /**
     * 模型一C算法
     * @param model
     * 按照正负偏离程度进行排名计算。若满足基本指标数量，则该项得0分。
     * 正偏离评分规则：按偏离程度由高到低排名， 正偏离最高的视为第一名，
     * 依次往后排名。若共有1家正偏离的，得分为标准分值；若共有2家正偏离的，
     * 第一名得标准分值，第二名得正标准分值的50%；若共有3家正偏离的，
     * 排名第一得标准分值，排名第二得标准分值的66%，排名第三得标准分值的33%；
     * 若共有4家及以上正偏离的，第一名得标准分值，第二名得标准分值的75%，，
     * 第三名得标准分值50%，第四名得标准分值25%。
     * （三分之二以上技术专家认定正偏离对产品性能没有实质意义的指标，可以不再排名，得分一致，但须备注：
     * 正偏离无实质意义）负偏离评分规则：按偏离程度由高到低排名，负偏离最高的视为第一名，
     * 依次往后排名。若共有1家负偏离的，得分为负标准分值；若共有2家负偏离的，
     * 第一名得负标准分值，第二名得负标准分值的50%；若共有3家负偏离的，排名第一名得负标准分值，
     * 第二名得负标准分值的66%，第三名得标准分值的负33%；若共有4家及以上负偏离的，第一名得负标准分值，
     * 第二名得负标准分值的75%，第三名得负标准分值50%，第四名得负标准分值25%。
     * @param supplyMarks
     * @return
     */
    public static List<SupplyMark> getScoreByModelEleven(ScoreModel model,ArrayList<SupplyMark> supplyMarks,Double deviation){
      Double standardScore =Double.parseDouble(model.getStandardScore());//标准分值
      final Double maxScore = Double.parseDouble(model.getMaxScore());//基本技术指标数量，如果专家评审超过此数量则为正偏离，否则为负偏离
      Collections.sort(supplyMarks,new Comparator<SupplyMark>() {//从高到低排序
        @Override
        public int compare(SupplyMark o1, SupplyMark o2) {
          double score1=o1.getPrarm()-maxScore;
          double score2=o2.getPrarm()-maxScore;
          return (int) (score2-score1);
        }
      });
      List<SupplyMark> positiveList=new ArrayList<SupplyMark>();//正偏离
      Set<Double> positiveSet=new HashSet<Double>();
      List<SupplyMark> negativeList=new ArrayList<SupplyMark>();//负偏离
      Set<Double> negativeSet=new HashSet<Double>();
      List<SupplyMark> list=new ArrayList<SupplyMark>();
       for (SupplyMark supplyMark : supplyMarks) {
        if(supplyMark.getPrarm()-maxScore>=0){
          positiveList.add(supplyMark);
          positiveSet.add(supplyMark.getPrarm());
        }else{
          negativeList.add(supplyMark);
          negativeSet.add(supplyMark.getPrarm());
        }
      }
       
      if(positiveList!=null){//正偏离计算
        scoreList(positiveList,"+",standardScore,positiveSet);
      }
      if(negativeList!=null){//负偏离计算
        Collections.sort(negativeList,new Comparator<SupplyMark>() {
          @Override
          public int compare(SupplyMark o1, SupplyMark o2) {
            double score1=o1.getPrarm()-maxScore;
            double score2=o2.getPrarm()-maxScore;
            return (int) (score1-score2);
          }
        });
        scoreList(negativeList,"-",standardScore,negativeSet);
      }
      list.addAll(positiveList);
      list.addAll(negativeList);
      return list;
    }

    private static void scoreList(List<SupplyMark> positiveList,String type,Double standardScore,Set<Double> sets) {
      Double[] two={1.0,0.5};
      Double[] three={1.0,0.66,0.33};
      Double[] four={1.0,0.75,0.5,0.25};
      Integer number=0;
      if(sets.size()==1){
        for (int i=0;i<positiveList.size();i++) {
          if("+".equals(type)){
            positiveList.get(i).setScore(standardScore);
          }else{
            positiveList.get(i).setScore(-standardScore);
          }
        }
      }if(sets.size()==2){
        number = scoreNumber(positiveList, type, standardScore, two, number);
      }else if(sets.size()==3){
        number = scoreNumber(positiveList, type, standardScore, three, number);
      }else if(sets.size()>=4){
        for (int i=0;i<positiveList.size();i++) {
          if(number<=3){
            if("+".equals(type)){
              positiveList.get(i).setScore(standardScore*four[number]);
              if(i!=positiveList.size()-1){
                if(positiveList.get(i).getPrarm()!=positiveList.get(i+1).getPrarm()){
                  number++;
                }
              }
            }else{
              positiveList.get(i).setScore(-standardScore*four[number]);
              if(i!=positiveList.size()-1){
                if(positiveList.get(i).getPrarm()!=positiveList.get(i+1).getPrarm()){
                  number++;
                }
              }
            }
          }else{
            positiveList.get(i).setScore(0);
          }
          
        }
      }
          
        
        
      
    }

    private static Integer scoreNumber(List<SupplyMark> positiveList,
        String type, Double standardScore, Double[] two, Integer number) {
      for (int i=0;i<positiveList.size();i++) {
        if("+".equals(type)){
          positiveList.get(i).setScore(standardScore*two[number]);
          if(i!=positiveList.size()-1){
            if(positiveList.get(i).getPrarm()!=positiveList.get(i+1).getPrarm()){
              number++;
            }
          }
        }else{
          positiveList.get(i).setScore(-standardScore*two[number]);
          if(i!=positiveList.size()-1){
            if(positiveList.get(i).getPrarm()!=positiveList.get(i+1).getPrarm()){
              number++;
            }
          }
        }
      }
      return number;
    }
}
/**
 * 
 * @Title: SortByParam
 * @Description: 内部类   集合排序
 * @author: Tian Kunfeng
 * @date: 2016-11-8下午4:33:53
 */
@SuppressWarnings("rawtypes")
class SortByParam implements Comparator {
    public int compare(Object o1, Object o2) {
        SupplyMark s1 = (SupplyMark) o1;
        SupplyMark s2 = (SupplyMark) o2;
        /*//升序排列
        if (new Double(s1.getPrarm()).compareTo(new Double(s2.getPrarm())) >0){
            return 1;
        }else if (new Double(s1.getPrarm()).compareTo(new Double(s2.getPrarm())) <0) {
            return -1;
        }*/
        //降序排列
        if (new Double(s1.getPrarm()).compareTo(new Double(s2.getPrarm())) >0){
            return -1;
        }else if (new Double(s1.getPrarm()).compareTo(new Double(s2.getPrarm())) <0) {
            return 1;
        }
        return 0;
    }
    
    
}