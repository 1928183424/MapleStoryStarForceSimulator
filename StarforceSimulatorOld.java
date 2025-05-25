package com.tuan.testcode;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

// 改版前上星模拟
public class StarforceSimulatorOld {
    public static void main(String[] args) {
        // 模拟参数
        int totalSimulations = 100000000;
        int startingStar = 17;
        int targetStar = 25;

        // 从表格中提取的概率
        double[] successRate = {
                0.30, // 17->18
                0.30, // 18->19
                0.30, // 19->20
                0.30, // 20->21
                0.30, // 21->22
                0.03, // 22->23
                0.02, // 23->24
                0.01, // 24->25
        };

        double[] keepRate = {
                0.679, // 17->18 失败但保持
                0.672, // 18->19 失败且下降
                0.672, // 19->20 失败且下降
                0.630, // 20->21 失败但保持
                0.630, // 21->22 失败且下降
                0.776, // 22->23 失败且下降
                0.686, // 23->24 失败且下降
                0.594, // 24->25 失败且下降
        };

        // 记录每个星级达成的次数
        int[] reachedStars = new int[targetStar + 1];
        // 记录每个星级达成的所使用的装备数量
        int[] equipmentUsed = new int[targetStar + 1];
        // 统计数据

        Random random = new Random();
        DecimalFormat df = new DecimalFormat("#.##");

        for(int target = 18; target <= targetStar; target++){
            equipmentUsed[target] = 1;
            int currentStars = startingStar;
            int continuousDescentTime = 0;
            // 运行模拟
            for (int sim = 0; sim < totalSimulations; sim++) {
                int starIndex = currentStars - startingStar;
                double roll = random.nextDouble();
                if (roll < successRate[starIndex] || continuousDescentTime == 2) {
                    // 成功
                    currentStars++;
                    if(currentStars == target){
                        reachedStars[target]++;
                        equipmentUsed[target]++;
                        currentStars = startingStar;
                    }
                    continuousDescentTime = 0;
                } else if (roll < successRate[starIndex] + keepRate[starIndex]) {
                    // 失败且降级
                    if(!(currentStars == 17 || currentStars == 20)){
                        currentStars--;
                        continuousDescentTime++;
                    }
                } else {
                    // 装备损坏
                    equipmentUsed[target]++;
                    currentStars = startingStar;
                }
            }
        }


        // 打印结果
        System.out.println("星力模拟报告 (" + totalSimulations + " 次模拟)");
        System.out.println("从 " + startingStar + "星 升级到 " + targetStar + "星");
        System.out.println("===================================");

        System.out.println("星级\t达成次数\t平均所需装备数量");
        System.out.println("-----------------------------------");

        for (int i = startingStar + 1; i <= targetStar; i++) {
            System.out.println(i + "星\t" + reachedStars[i] + "\t\t" + df.format(equipmentUsed[i] / (double) reachedStars[i]));
        }

        System.out.println("每个目标星级使用的装备数量：" + Arrays.toString(equipmentUsed));

    }
}
