import java.util.*;

public class HardSphereSmall {
	public static void main(String[] args) {
		//create a box with 500 particles
		int n = 500;
		double diam = 0.0914156;//for phi = 0.20
		double[][] coordinates = cubeLattice(8,1.0);
		Box pandora3D = new Box(n,diam,coordinates);
		int collisionNow = pandora3D.totalCollision();		

		for (int i = 1; i <= 100000; i++) {
			Movement m = pandora3D.move(0.005);
			Particle particleMoved = m.getParticle();
			if (pandora3D.collisionChecker(particleMoved,n) > 1) {
				pandora3D.move(m.reverse());
			}
			else {
				collisionNow = pandora3D.totalCollision();
			}
			//System.out.println(pandora3D.totalCollision()); 
		}
		System.out.println(pandora3D);//this is the equil. config.

		double thickness = 0.010;
		System.out.println("thickness = " + thickness);
		System.out.println("diam = " + diam);
                double[][] g2 = RadialStat.g2Table(pandora3D,thickness);
                for (int i = 0; i < g2.length; i++) {
                        System.out.println(g2[i][0] + " " + g2[i][1]);
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


