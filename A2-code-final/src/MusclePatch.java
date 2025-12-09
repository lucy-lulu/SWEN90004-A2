import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//MusclePatch表示视图中的每个格子，在本model中即单个muscle fibre细胞
public class MusclePatch implements Patch {

    //该patch中fibre的大小
    private double muscleFibreSize;

    //该patch中fibre可取的最大值
    private double muscleFibreMaxSize;

    private double anabolicHormone; //合成代谢激素的值

    private double catabolicHormone; //分解代谢激素的值


    //当前人进行的活动
    //private Activity act = new Activity();

    //当前的人的身体
    private Body body = new Body();

    //当前patch的坐标
    private Coordinate coordinate;

    private int neighbourCount;


    //每个musclepatch的初始大小由一下算法生成
    MusclePatch(Body currentBody, int x, int y) {

        this.body = currentBody;
        anabolicHormone = Params.anabolicHormoneInit;
        catabolicHormone = Params.catabolicHormoneInit;
        coordinate = new Coordinate(x, y);

        Random random = new Random();
        muscleFibreMaxSize = 4;

        //原代码里的意思是 slowTwitchFibersRate 会影响 肌肉上限 算法如下
        for (int i = 0; i < 20; i++) {
            double randomNumber1 = random.nextDouble() * 100;
            if (randomNumber1 > Params.slowTwitchFibersRate) {
                muscleFibreMaxSize++;
            }
        }
        //randomPercent = r.nextInt(99) + 1;
        double randomNumber2 = random.nextDouble() * 0.4;
        muscleFibreSize = (0.2 + randomNumber2) * muscleFibreMaxSize;
        regulateFibre();


        //System.out.println(muscleFibreSize);
        //System.out.println(muscleFibreMaxSize);
    }


    public void developMuscle() {
        muscleFibreSize = muscleFibreSize - 0.2 * Math.log10(catabolicHormone);
        double min = Math.min(Math.log10(anabolicHormone), 1.05 * Math.log10(catabolicHormone));
        muscleFibreSize = muscleFibreSize + 0.2 * min;
        regulateFibre();
    }

    //修正fibre
    public void regulateFibre() {
        if (muscleFibreSize < 1) {
            muscleFibreSize = 1;
        }
        if (muscleFibreSize > muscleFibreMaxSize) {
            muscleFibreSize = muscleFibreMaxSize;
        }
    }

    //修正hormones
    public void regulateHormones() {
        //diffuse anabolic-hormone hormone-diffuse-rate
        //diffuse catabolic-hormone hormone-diffuse-rate
        //anabolicHormoneDiffuse();
        //catabolicHormoneDiffuse();

        if (anabolicHormone < Params.anabolicHormoneMin) {
            anabolicHormone = Params.anabolicHormoneMin;
        }

        if (anabolicHormone > Params.anabolicHormoneMax) {
            anabolicHormone = Params.anabolicHormoneMax;
        }

        if (catabolicHormone < Params.catabolicHormoneMin) {
            catabolicHormone = Params.catabolicHormoneMin;
        }

        if (catabolicHormone > Params.catabolicHormoneMax) {
            catabolicHormone = Params.catabolicHormoneMax;
        }
    }

    //合成激素扩散，扩散到周围的格子
    public void anabolicHormoneDiffuse() {
        double anabolicDiffused = anabolicHormone * Params.hormoneDiffuseRate;
        //anabolicHormone -= anabolicDiffused;

        List<Patch> neighbours = getNeighbours();
        anabolicHormone -= neighbourCount * Params.diffuseShareRate * anabolicDiffused;

        for(Patch patch : neighbours){
            MusclePatch muscle_patch = (MusclePatch)patch;
            muscle_patch.setAnabolicHormone(muscle_patch.anabolicHormone + Params.diffuseShareRate * anabolicDiffused);
        }
    }

    //分解激素扩散, 扩散到周围的格子
    public void catabolicHormoneDiffuse() {
        double catabolicDiffused = catabolicHormone * Params.hormoneDiffuseRate;
        //catabolicHormone-= catabolicDiffused;

        List<Patch> neighbours = getNeighbours();
        catabolicHormone -= neighbourCount * Params.diffuseShareRate * catabolicDiffused;

        for(Patch patch : neighbours){
            MusclePatch muscle_patch = (MusclePatch) patch;
            muscle_patch.setCatabolicHormone(muscle_patch.catabolicHormone + Params.diffuseShareRate * catabolicDiffused);
        }
    }

    public double getFibreSize() {
        return muscleFibreSize;
    }


    //获取patch的临近坐标，用于计算diffuse过程
    public List<Patch> getNeighbours() {
        neighbourCount = 0;

        List<Patch> neighbours = new LinkedList<>();
        int hostX = coordinate.getXcor();
        int hostY = coordinate.getYcor();

        boolean hasLeftNeighbour = hostX > 0;
        boolean hasRightNeighbour = hostX < Params.Width - 1;
        boolean hasTopNeighbour = hostY > 0;
        boolean hasBottomNeighbour = hostY < Params.Height - 1;
        // left
        if (hasLeftNeighbour) {
            neighbours.add(body.getPatch()[hostY][hostX - 1]);
            neighbourCount++;
        }
        // right
        if (hasRightNeighbour) {
            neighbours.add(body.getPatch()[hostY][hostX + 1]);
            neighbourCount++;
        }
        // top
        if (hasTopNeighbour) {
            neighbours.add(body.getPatch()[hostY - 1][hostX]);
            neighbourCount++;
        }
        // bottom
        if (hasBottomNeighbour) {
            neighbours.add(body.getPatch()[hostY + 1][hostX]);
            neighbourCount++;
        }

        // top-left
        if (hasLeftNeighbour && hasTopNeighbour) {
            neighbours.add(body.getPatch()[hostY - 1][hostX - 1]);
            neighbourCount++;
        }
        // top-right
        if (hasRightNeighbour && hasTopNeighbour) {
            neighbours.add(body.getPatch()[hostY - 1][hostX + 1]);
            neighbourCount++;
        }
        // bottom-left
        if (hasLeftNeighbour && hasBottomNeighbour) {
            neighbours.add(body.getPatch()[hostY + 1][hostX - 1]);
            neighbourCount++;
        }
        // bottom-right
        if (hasRightNeighbour && hasBottomNeighbour) {
            neighbours.add(body.getPatch()[hostY + 1][hostX + 1]);
            neighbourCount++;
        }
        return neighbours;
    }

    public double getAnabolicHormone() {
        return anabolicHormone;
    }

    public double getCatabolicHormone() {
        return catabolicHormone;
    }

    public void setAnabolicHormone(double anabolic) {
        this.anabolicHormone = anabolic;
    }

    public void setCatabolicHormone(double catabolic) {
        this.catabolicHormone = catabolic;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }
}