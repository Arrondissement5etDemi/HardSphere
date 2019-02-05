import java.util.*;

/**generates g2 for hard sphere system with packing fraction phi = 0.20 */
public class HardSphereLarge {
	
	public static void main(String[] args) {
		//create a box with 500 particles
		int n = 500;
		double phi = 0.20;
		double diam = Math.pow(6.0*phi/(Math.PI*(double)n),1.0/3.0);//for phi = 0.45
		int numBinsPerDiam = 60;
		double upperRad = 4.0;

		double[][] coordinates = cubeLattice(8,1.0);
                System.out.println("thickness = 1/"+numBinsPerDiam);
                System.out.println("diam = " + diam);

		Box pandora3D = new Box(n,diam,coordinates);
		//int collisionNow = pandora3D.totalCollision();		

		double[][] g2Final = RadialStat.g2(pandora3D,numBinsPerDiam,upperRad);
		for (int i = 0; i < g2Final.length; i++) {
			g2Final[i][1] = 0.0;
		}
		int relaxTime =  100000;
		int totalTime = 10000000;
		int numSample = totalTime/relaxTime;

		for (int i = 1; i <= totalTime; i++) {
			Movement m = pandora3D.move(0.005);
			Particle particleMoved = m.getParticle();
			if (pandora3D.collisionChecker(particleMoved,n) > 1) {
				pandora3D.move(m.reverse());
			}
			if (i%relaxTime == 0) {//sample the config and g2 after every relaxation
                		double[][] g2 = RadialStat.g2(pandora3D,numBinsPerDiam,upperRad);
				for (int j = 0; j < g2.length; j++) {
					g2Final[j][1] = g2Final[j][1] + g2[j][1];
				}
			}
		}

		for (int i = 0; i < g2Final.length; i++) {
			g2Final[i][1] = g2Final[i][1]/(double)numSample;
			System.out.println(g2Final[i][0] + " " + g2Final[i][1]);
		}
	}	

	/**generates the coordinates of a square lattice in 3D 
*	@param partiPerSide int, particles per side
*	@param d double, side length of the unit cell
*	@return double[][] the array of the coordinates of the particles */
	private static double[][] cubeLattice(int partiPerSide, double d) {
		double increment = d/(double)partiPerSide;
		int numPoints = (int)Math.pow(partiPerSide,3);
		double[][] coordinates = new double[numPoints][3];
		int index = 0;
		for (int i = 0; i < partiPerSide; i++) {
			for (int j = 0; j < partiPerSide; j++) {
				for (int k = 0; k < partiPerSide; k++) {
					coordinates[index][0] = ((double)i) * increment;
					coordinates[index][1] = ((double)j) * increment;
					coordinates[index][2] = ((double)k) * increment;
					index++;
				} 
			}
		}
		return coordinates;
	}
}


