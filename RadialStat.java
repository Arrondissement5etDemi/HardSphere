import java.util.*;

/** A class with functions to sample radial distribution function */
public class RadialStat {
	
	private static double pi = 3.14159265359;

	/**counts the number of particles in a specified shell centered at a Particle, assume PBC
 * 	@param b Box, the Particle configuration that we consider
 * 	@param lowerRad double, the lower radius of the shell
 * 	@param upperRad double, the upper radius of the shell
 * 	@return int, the number of particles in the shell */
	public static int countInShell(Box b, double lowerRad, double upperRad) {
		//get the particle configuration, number of particles, box dimension
		Particle[] partiArr = b.toArray();
		int n = b.getN();
		double d = b.getD();
		//get the position of the first particle, which we take to be the center
		Particle center = partiArr[0];
		//get the range of unit cells that we consider counting
		int bound = (int) Math.ceil(upperRad / d);
		//consider all particles in all the cells considerable, see if they are in the shell
		int count = 0;
		for (int i = 0; i < n; i++) {
			Particle thatParti = partiArr[i];
			double thatX = thatParti.getx();
			double thatY = thatParti.gety();
			double thatZ = thatParti.getz();
			for (int j = -bound; j <= bound; j++) {
				for (int k = -bound; k <= bound; k++) {
					for (int l = -bound; l <= bound; l++) {
						Particle dupli 
						= new Particle(thatX + j*d, thatY + k*d, thatZ + l*d);
						double distToDupli = center.distanceto(dupli);
						if (distToDupli > lowerRad && distToDupli <= upperRad) {
							count = count + 1;
						} 
					}
				}
			}
		}
		return count;
	}
	
	/**computes the radial distribution function g2(r) at radius r given a shell thickness 
 * 	@param b Box, the Particle configuration that we consider
 * 	@param r double, the radius where g2 is estimated
 * 	@param thickness double, the thickness of the shell in which we count particles
 * 	@return double, an estimation of g2(r) */
	public static double g2ForShell(Box b, double r, double thickness) {
		if (thickness/2 > r) {
			throw new IllegalArgumentException("thickness/2 must be < r");
		}
		//compute number density rho
		double rho = ((double)b.getN())/Math.pow(b.getD(),3);
		//computes the volume of the shell
		double lowerRad = r - thickness/2;
		double upperRad = r + thickness/2;
		//double shellVolume = 4/3*pi*(Math.pow(upperRad,3)-Math.pow(lowerRad,3));
		double shellVolume = 4*pi*r*r*thickness;
		//count sphere centers in the shell
		int count = countInShell(b,lowerRad,upperRad);
		//compute g2(r)
		double result = ((double)count)/(rho*shellVolume);
		return result; 
	}
}
