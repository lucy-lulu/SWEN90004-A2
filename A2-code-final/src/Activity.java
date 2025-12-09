
//Activity表示进行的活动


public class Activity {

    //日常活动对激素的作用
    public void performDailyActivity(Body body){
        MusclePatch[][] patch = body.getPatch();
        for(int y = 0; y < Params.Height; y++) {
            for (int x = 0; x < Params.Width; x++) {
                double currentAnabolic = patch[y][x].getAnabolicHormone();
                double currentCatabolic = patch[y][x].getCatabolicHormone();
                double size = patch[y][x].getFibreSize();
                //System.out.println("Now size" + size);
                //System.out.println("Now ana " + currentAnabolic);

                patch[y][x].setCatabolicHormone(currentCatabolic + 2.0 * Math.log10(size));
                patch[y][x].setAnabolicHormone(currentAnabolic + 2.5 * Math.log10(size));

            }
        }

    }

    //举重对激素的作用
    public void liftWeights(Body body){
        //System.out.println("Lifting!");
        MusclePatch[][] patch = body.getPatch();
        for(int y = 0; y < Params.Height; y++) {
            for (int x = 0; x < Params.Width; x++) {
                if (Math.random() < Params.intensity / 100 * Params.intensity / 100) {
                    double currentAnabolic = patch[y][x].getAnabolicHormone();
                    double currentCatabolic = patch[y][x].getCatabolicHormone();
                    double size = patch[y][x].getFibreSize();
                   // System.out.println("Now size" + size);

                    patch[y][x].setAnabolicHormone(currentAnabolic + Math.log10(size) * 55);
                    patch[y][x].setCatabolicHormone(currentCatabolic + Math.log10(size) * 44);
                }
            }
        }
    }

    //睡眠对激素的作用
    public void sleep(Body body) {
        MusclePatch[][] patch = body.getPatch();
        for (int y = 0; y < Params.Height; y++) {
            for (int x = 0; x < Params.Width; x++) {

                double currentAnabolic = patch[y][x].getAnabolicHormone();
                double currentCatabolic = patch[y][x].getCatabolicHormone();
                //System.out.println("Now size");

                patch[y][x].setCatabolicHormone(currentCatabolic - 0.5 * Math.log10(currentCatabolic) * Params.hoursOfSleep);
                patch[y][x].setAnabolicHormone(currentAnabolic - 0.48 * Math.log10(currentAnabolic) * Params.hoursOfSleep);
            }
        }
    }
}