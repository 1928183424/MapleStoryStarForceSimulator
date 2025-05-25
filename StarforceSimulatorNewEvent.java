import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

// 21星以下装备破坏率降低30%活动
public class StarforceSimulatorNewEvent {
    public static void main(String[] args) {
        // 模拟参数
        int totalSimulations = 100000000;
        int startingStar = 18;
        int targetStar = 30;

        // 上星概率
        // 成功概率
        double[] successRate = {
                0.15, // 18->19
                0.15, // 19->20
                0.30, // 20->21
                0.15, // 21->22
                0.15, // 22->23
                0.10, // 23->24
                0.10, // 24->25
                0.10, // 25->26
                0.07, // 26->27
                0.05, // 27->28
                0.03, // 28->29
                0.01  // 29->30
        };

        // 失败概率
        double[] keepRate = {
                0.8024, // 18->19 失败但保持
                0.7905, // 19->20 失败但保持
                0.6265, // 20->21 失败但保持
                0.76075, // 21->22 失败但保持
                0.68, // 22->23 失败但保持
                0.72, // 23->24 失败但保持
                0.72, // 24->25 失败但保持
                0.72, // 25->26 失败但保持
                0.744, // 26->27 失败但保持
                0.76, // 27->28 失败但保持
                0.776, // 28->29 失败但保持
                0.792  // 29->30 失败但保持
        };

        // 记录每个星级达成的次数
        int[] reachedStars = new int[targetStar + 1];
        // 记录每个星级达成的所使用的装备数量
        int[] equipmentUsed = new int[targetStar + 1];
        // 统计数据

        Random random = new Random();
        DecimalFormat df = new DecimalFormat("#.##");

        for(int target = 19; target <= targetStar; target++){
            equipmentUsed[target] = 1;
            int currentStars = startingStar;
            // 运行模拟
            for (int sim = 0; sim < totalSimulations; sim++) {
                int starIndex = currentStars - startingStar;
                double roll = random.nextDouble();
                if (roll < successRate[starIndex]) {
                    // 成功
                    currentStars++;
                    if(currentStars == target){
                        reachedStars[target]++;
                        equipmentUsed[target]++;
                        currentStars = startingStar;
                    }
                } else if (roll > successRate[starIndex] + keepRate[starIndex]) {
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
