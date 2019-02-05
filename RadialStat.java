import java.util.*;

/** A class with functions to sample radial distribution function */
public class RadialStat {
	
	private static double pi = Math.PI;

	/**counts the number of particles in different shells centered at a Particle, assume PBC
 * 	@param b Box, the Particle configuration that we consider
 * 	@param center Particle, the Particle we take as center
 * 	@param upperRad double, the upper radius in multiples of diam of the range we consider in binning
 * 	@param numBinsPerDiam int, how many bins are there in the diameter of the particles
 * 	@return double[], the number of particles in each bin */
	public static double[] binning(Box b, Particle center, double upperRad, int numBinsPerDiam) {
		//get the particle configuration, number and diam of particles, box dimension
		Particle[] partiArr = b.toArray();
		int n = b.getN();
		double d = b.getD();
		double diam = b.getDiam();
		//get the range of unit cells that we consider counting
		double upperRadReal = diam * upperRad;
		int bound = (int) Math.ceil(upperRadReal / d);
		//get the number of bins needed for g2 plot
		int numBins = (int) (numBinsPerDiam * upperRad);
		double[] result = new double[numBins];
		//consider all particles in all the cells considered, see if they are in the shell
		for (int i = 0; i < n; i++) {
			Particle thatParti = partiArr[i];
			double thatX = thatParti.getx();
			double thatY = thatParti.gety();
			double thatZ = thatParti.getz();
			for (int j = -bound; j <= bound; j++) {
			for (int k = -bound; k <= bound; k++) {
			for (int l = -bound; l <= bound; l++) {
				Particle dupli = new Particle(thatX + j*d, thatY + k*d, thatZ + l*d, diam);
				double distToDupli = center.distanceto(dupli);
				double distScaled = distToDupli/diam;//distance in multiples of diam
				int binToEnter = (int)Math.floor(distScaled * numBinsPerDiam);	
					if (binToEnter < numBins) {
						result[binToEnter]++;
					} 
			}
			}
			}
		}
		return result;
	}

	/**computes g2 using binning
 * 	@param b Box, the Particle configuration that we consider
 * 	@param numBinsPerDiam int, how many bins are there in the diameter of the particles
 * 	@param upperRad double, the upper radius in multiples of diam of the range we consider in binning
 * 	@return double[][], the g2 function */
	public static double[][] g2(Box b, int numBinsPerDiam, double upperRad) {
		Particle[] partiArr = b.toArray();
                int n = b.getN();
                double d = b.getD();
                double diam = b.getDiam();
		double rho = ((double)n)/Math.pow(d,3);//number density

		int numBins = (int) (numBinsPerDiam * upperRad);
		double[] binningResult = new double[numBins];

                for (int i = 0; i < n; i++) {
                        Particle center = partiArr[i];
			double[] localBinning = binning(b, center, upperRad, numBinsPerDiam);
                        for (int j = 0; j < numBins; j++) {
				binningResult[j] = binningResult[j] + localBinning[j];
			}
                }

		double[][] result = new double[numBins][2];
		double thickness = (1.0/(int)numBinsPerDiam)*diam;
		for (int i = 0; i < numBins; i++) {
			result[i][0] = (i + 0.5) * (1.0/(double)numBinsPerDiam);
			double r = result[i][0]*diam;
			double shellVolume = 4*pi*r*r*thickness;
			result[i][1] = binningResult[i]/((double)n*rho*shellVolume);
		}
		return result;
	}
	
