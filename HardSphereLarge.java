import java.util.*;

public class HardSphereLarge {
	public static void main(String[] args) {
		//create a box with 500 particles
		int n = 500;
		double diam = 0.11978836;//for phi = 0.45
		double[][] coordinates = cubeLattice(8,1.0);
		double thickness = 0.008;
                System.out.println("thickness = " + thickness);
                System.out.println("diam = " + diam);

		Box pandora3D = new Box(n,diam,coordinates);
		//int collisionNow = pandora3D.totalCollision();		

		for (int i = 1; i <= 25000000; i++) {
			Movement m = pandora3D.move(0.005);
			Particle particleMoved = m.getParticle();
			if (pandora3D.collisionChecker(particleMoved,n) > 1) {
				pandora3D.move(m.reverse());
			}
			if (i%5000000 == 0) {//get the config and g2 every 1000000 moves	 
				System.out.println(pandora3D);//this is the equil. config.
                		double[][] g2 = RadialStat.g2Table(pandora3D,thickness);
                		for (int j = 0; j < g2.length; j++) {
                        		System.out.println(g2[j][0] + " " + g2[j][1]);
                		}
			}
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


