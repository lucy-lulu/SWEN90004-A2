import java.util.LinkedList;

public class Params {
    public static long ticksMax = 1000L; //计数ticket

    public static int Width = 17; //视图的宽

    public static int Height = 17; //视图的高


    public static double hormoneDiffuseRate = 0.75; //激素消散的概率

    public static double diffuseShareRate = 1.0/8.0; //扩散概率 常数

    public static double anabolicHormoneMax = 200; //合成代谢激素最大值

    public static double anabolicHormoneMin = 50; //合成代谢激素最小值

    public static double anabolicHormoneInit = 50; //合成代谢激素初始值

    public static double catabolicHormoneMax = 250; //分解代谢激素最大值

    public static double catabolicHormoneMin = 52; //分解代谢激素最小值

    public static double catabolicHormoneInit = 52; //合成代谢激素初始值

    public static int daysBetweenWorkouts = 5; //休息间隔时间


    public static double slowTwitchFibersRate = 80; //慢肌纤维的比例

    public static boolean isLift = true; //是否举重

    public static double intensity = 75;

    public static int hoursOfSleep = 8;
}