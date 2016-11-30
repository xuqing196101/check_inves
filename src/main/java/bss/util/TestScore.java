package bss.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import bss.model.ppms.ScoreModel;
import bss.model.ppms.SupplyMark;

public class TestScore {
    
    public void testOne() {//测试正常
        ScoreModel scoreModel = new ScoreModel();
        //标准分值standardScore
        scoreModel.setStandardScore("2");
        //不满足0 满足1  
        Integer flag=1;
        double score=ScoreModelUtil.getScoreByModelOne(scoreModel, flag);
        System.out.println(score);
    }
    
    public void testTwo() {//修改后测试正常
        ScoreModel scoreModel = new ScoreModel();
        //0加分1减分
        scoreModel.setAddSubtractTypeName("1");
        //加分的起始分值为0  减分起始分值为2
        scoreModel.setReviewStandScore("2");
        //最高分
        scoreModel.setMaxScore("2");
        //最低分
        scoreModel.setMinScore("0");
        //每单位分值
        scoreModel.setUnitScore("0.5");
        //满足项数量
        Integer number=1;
        double score=ScoreModelUtil.getScoreByModelTwo(scoreModel, number);
        System.out.println(score);
    }
    
    public void testFive() { //模型图片上面有个乘以2  这个有点疑惑 不过问题不是很大  测试正常
        ArrayList<SupplyMark> supplyMarkList = new ArrayList<SupplyMark>();
        SupplyMark sm = new SupplyMark();
        // 基准参数
        sm.setPrarm(3);
        supplyMarkList.add(sm);
        
        SupplyMark sm1 = new SupplyMark();
        sm1.setPrarm(2);
        supplyMarkList.add(sm1);
        
        SupplyMark sm2 = new SupplyMark();
        sm2.setPrarm(1);
        supplyMarkList.add(sm2);
        
        SupplyMark sm4 = new SupplyMark();
        sm4.setPrarm(1);
        supplyMarkList.add(sm4);
        
        ScoreModel scoreModel = new ScoreModel();
        //标准分值standardScore
        scoreModel.setStandardScore("30");
        
        List<SupplyMark> smList = ScoreModelUtil.getScoreByModelFive(scoreModel, supplyMarkList,sm);
        for (SupplyMark supplyMark : smList) {
            System.out.println(supplyMark.getPrarm()+"-"+supplyMark.getScore());
        }
    }
    
    @Test
    public void testSeven() {
        ScoreModel scoreModel = new ScoreModel();
        //评审基准数
        scoreModel.setReviewStandScore("10");
        //0等额区间    1不等额区间
        scoreModel.setIntervalTypeName("0");
        //加减分类型  0 加分 1减分
        scoreModel.setAddSubtractTypeName("0");
        //每个区间之间的差额，用于等额区间模型
        scoreModel.setIntervalNumber("0.5");
        //截止分数
        scoreModel.setDeadlineNumber("5");
        //最高分
        scoreModel.setMaxScore("10");
        //最低分
        scoreModel.setMinScore("0");
        Integer number=6;
        double score = ScoreModelUtil.getScoreByModelSeven(scoreModel,number);
        System.out.println(score);
        
    }
    
    public void testSix() { //精度损失  修改完毕
        ArrayList<SupplyMark> supplyMarkList = new ArrayList<SupplyMark>();
        SupplyMark sm = new SupplyMark();
        // 基准参数
        sm.setPrarm(3);
        supplyMarkList.add(sm);
        
        SupplyMark sm1 = new SupplyMark();
        sm1.setPrarm(2);
        supplyMarkList.add(sm1);
        
        SupplyMark sm2 = new SupplyMark();
        sm2.setPrarm(1);
        supplyMarkList.add(sm2);
        
        SupplyMark sm4 = new SupplyMark();
        sm4.setPrarm(1);
        supplyMarkList.add(sm4);
        
        ScoreModel scoreModel = new ScoreModel();
        //标准分值standardScore
        scoreModel.setStandardScore("30");
        
        List<SupplyMark> smList = ScoreModelUtil.getScoreByModelSix(scoreModel, supplyMarkList,sm4);
        for (SupplyMark supplyMark : smList) {
            System.out.println(supplyMark.getPrarm()+"-"+supplyMark.getScore());
        }
    }
    
    public void testThree() {//测试正常
        ScoreModel scoreModel = new ScoreModel();
        //分差   每个区间的分值差，加  加多少分   减  减多少分
        scoreModel.setUnitScore("0.15");
        //加减分类型 0加分 1减分----这个方法是减分方法  加分方法是getScoreByModelFour
        scoreModel.setAddSubtractTypeName("1");
        scoreModel.setMaxScore("1");
        scoreModel.setMinScore("0");
        
        ArrayList<SupplyMark> supplyMarkList = new ArrayList<SupplyMark>(7);
        SupplyMark sm = new SupplyMark();
        // 基准参数
        sm.setPrarm(3);
        supplyMarkList.add(sm);
        
        SupplyMark sm1 = new SupplyMark();
        sm1.setPrarm(2);
        supplyMarkList.add(sm1);
        
        SupplyMark sm2 = new SupplyMark();
        sm2.setPrarm(1);
        supplyMarkList.add(sm2);
        
        List<SupplyMark> smList = ScoreModelUtil.getScoreByModelThree(scoreModel, supplyMarkList);
        for (SupplyMark supplyMark : smList) {
            System.out.println(supplyMark.getPrarm()+"-"+supplyMark.getScore());
        }
    }
    
    public void testFour() {//测试正常
        ScoreModel scoreModel = new ScoreModel();
        //分差   每个区间的分值差，加  加多少分   减  减多少分
        scoreModel.setUnitScore("0.15");
        //加减分类型 0加分 1减分----这个方法是加分方法  减分方法是getScoreByModelThree
        scoreModel.setAddSubtractTypeName("0");
        scoreModel.setMaxScore("1");
        scoreModel.setMinScore("0");
        
        ArrayList<SupplyMark> supplyMarkList = new ArrayList<SupplyMark>(7);
        SupplyMark sm = new SupplyMark();
        // 基准参数
        sm.setPrarm(3);
        supplyMarkList.add(sm);
        
        SupplyMark sm1 = new SupplyMark();
        sm1.setPrarm(2);
        supplyMarkList.add(sm1);
        
        SupplyMark sm2 = new SupplyMark();
        sm2.setPrarm(1);
        supplyMarkList.add(sm2);
        
        List<SupplyMark> smList = ScoreModelUtil.getScoreByModelFour(scoreModel, supplyMarkList);
        for (SupplyMark supplyMark : smList) {
            System.out.println(supplyMark.getPrarm()+"-"+supplyMark.getScore());
        }
    }
}
