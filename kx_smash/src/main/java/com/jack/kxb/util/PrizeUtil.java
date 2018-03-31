package com.jack.kxb.util;

import java.util.ArrayList;
import java.util.List;

import com.jack.kxb.model.KxPrize;

public class PrizeUtil {
	public static int getPrizeIndex(List<KxPrize> list) {
        int random = -1;
        try{
            //计算总权重
            double sumWeight = 0;
            for(KxPrize p : list){
                sumWeight += p.getPrizeWeight();
            }

            //产生随机数
            double randomNumber;
            randomNumber = Math.random();

            //根据随机数在所有奖品分布的区域并确定所抽奖品
            double d1 = 0;
            double d2 = 0;          
            for(int i=0;i<list.size();i++){
                d2 += Double.parseDouble(String.valueOf(list.get(i).getPrizeWeight()))/sumWeight;
                if(i==0){
                    d1 = 0;
                }else{
                    d1 +=Double.parseDouble(String.valueOf(list.get(i-1).getPrizeWeight()))/sumWeight;
                }
                if(randomNumber >= d1 && randomNumber <= d2){
                    random = i;
                    break;
                }
            }
        }catch(Exception e){
            System.out.println("生成抽奖随机数出错，出错原因：" +e.getMessage());
        }
        return random;
	}
	
	public static void main(String[] agrs) {
        int i = 0;
        //PrizeMathRandom a = new PrizeMathRandom();
        int[] result=new int[4];
        List<KxPrize> prizes = new ArrayList<KxPrize>();

        KxPrize p1 = new KxPrize();
        p1.setPrizeName("范冰冰海报");
        p1.setPrizeWeight(2);//奖品的权重设置成1
        prizes.add(p1);

        KxPrize p2 = new KxPrize();
        p2.setPrizeName("上海紫园1号别墅");
        p2.setPrizeWeight(6);//奖品的权重设置成2
        prizes.add(p2);

        KxPrize p3 = new KxPrize();
        p3.setPrizeName("奥迪a9");
        p3.setPrizeWeight(10);//奖品的权重设置成3
        prizes.add(p3);

        KxPrize p4 = new KxPrize();
        p4.setPrizeName("双色球彩票");
        p4.setPrizeWeight(988);//奖品的权重设置成4
        prizes.add(p4);

        System.out.println("抽奖开始");
        for (i = 0; i < 10000; i++)// 打印100个测试概率的准确性
        {
            int selected=getPrizeIndex(prizes);
            System.out.println("第"+i+"次抽中的奖品为："+prizes.get(selected).getPrizeName());
            result[selected]++;
            System.out.println("--------------------------------");
        }
        System.out.println("抽奖结束");
        System.out.println("每种奖品抽到的数量为：");
        System.out.println("一等奖："+result[0]);
        System.out.println("二等奖："+result[1]);
        System.out.println("三等奖："+result[2]);
        System.out.println("四等奖："+result[3]);       
    }
}
