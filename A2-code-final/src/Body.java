import java.io.IOException;
import java.util.LinkedList;

public class Body implements Observer {

    private double muscleMass; //所有肌肉纤维大小的总和

    private MusclePatch[][] patches; //视图上的每个格子

    private LinkedList<MusclePatch> patchList; //用一个链表把patch串起来，表示按时间顺序model的变化

    private Activity dailyActivity; //Body需要和Activity联系起来

    private static long ticksCount; //计数ticks

    private double avgAnabolic;
    private double avgCatabolic;


    public Body() {
        patchList = new LinkedList<>();
        dailyActivity = new Activity();
        muscleMass = 0;
        ticksCount = 0L;
    }


    public void init(){
        //定义patches概况
        patches = new MusclePatch[Params.Height][Params.Width];

        //初始化patches
        for(int y = 0; y < Params.Height; y++) {
            for(int x = 0; x < Params.Width; x++) {
                //坐标值作为参数传入
                MusclePatch musclePatch = new MusclePatch(this, x, y);
                patches[y][x] = musclePatch;
                //每个patch都加入链表中
                patchList.offer(patches[y][x]);
            }
        }
        calculateMuscleMass();

        System.out.println("Hello Muscle Development!");
        System.out.println("The initial Muscle Mass is " + muscleMass);

        try {
            FileUtil.createCSV("dataOutput.csv", "ticks, muscleMass, averageAnabolic, averageCatabolic");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*
        for(int y = 0; y < Params.Height; y++) {
            for(int x = 0; x < Params.Width; x++){
                //System.out.println("Initial FibreSize: " + patches[y][x].getFibreSize());
                System.out.println("Initial AnabolicHormone: " + patches[y][x].getAnabolicHormone());
                System.out.println("Initial CatabolicHormone: " + patches[y][x].getCatabolicHormone());
            }
        }
        */

    }

    //对应netlogo里的“to go”
    public void run(){

        while(ticksCount < Params.ticksMax) {

                    dailyActivity.performDailyActivity(this);

                    if (Params.isLift && ticksCount % Params.daysBetweenWorkouts == 0) {
                    dailyActivity.liftWeights(this);
                    }

                    dailyActivity.sleep(this);


            for (int y = 0; y < Params.Height; y++) {
                for (int x = 0; x < Params.Width; x++) {
                    patches[y][x].anabolicHormoneDiffuse();
                    patches[y][x].catabolicHormoneDiffuse();
                }
            }


            for (int y = 0; y < Params.Height; y++) {
                for (int x = 0; x < Params.Width; x++) {
                    patches[y][x].regulateHormones();
                }
            }

                for (int y = 0; y < Params.Height; y++) {
                    for (int x = 0; x < Params.Width; x++) {
                    patches[y][x].developMuscle();
                }
            }
            ticksCount++;
            calculateMuscleMass();
            //System.out.println("The day " + ticksCount +" Muscle Mass is " + muscleMass);
            System.out.println(muscleMass);
            System.out.println(averageAnabolic() + "\t" + averageCatabolic());
            avgAnabolic = averageAnabolic();
            avgCatabolic = averageCatabolic();
            try {
                FileUtil.updateCSV(ticksCount, muscleMass, avgAnabolic, avgCatabolic);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public double averageAnabolic(){
        double average = 0;
        for(int y = 0; y < Params.Height; y++) {
            for(int x = 0; x < Params.Width; x++){
                average += patches[y][x].getAnabolicHormone();
            }
        }
        int numFibres = patches.length * patches[0].length;
        average = average / numFibres;
        return average;
    }

    public double averageCatabolic(){
        double average = 0;
        for(int y = 0; y < Params.Height; y++) {
            for(int x = 0; x < Params.Width; x++){
                average += patches[y][x].getCatabolicHormone();
            }
        }
        int numFibres = patches.length * patches[0].length;
        average = average / numFibres;
        return average;
    }

    public void end(){

/*
        for(int y = 0; y < Params.Height; y++) {
            for(int x = 0; x < Params.Width; x++){
                System.out.println("AnabolicHormone: " + patches[y][x].getAnabolicHormone());
                System.out.println("CatabolicHormone: " + patches[y][x].getCatabolicHormone());
            }
        }
        */



            //some output info here
            System.out.println("Bye Muscle Development!");
            System.out.println("The final Muscle Mass is " + muscleMass);
        try {
            FileUtil.closeCSV();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void calculateMuscleMass(){
        double newMuscleMass = 0;
        for(int y = 0; y < Params.Height; y++) {
            for(int x = 0; x < Params.Width; x++) {
                newMuscleMass += patches[y][x].getFibreSize();
            }
        }
        muscleMass = newMuscleMass;
    }


    public MusclePatch[][] getPatch(){
        return patches;
    }
}