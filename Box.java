import java.util.*;


//this is a 1*1*1 cubic box with n Particles in the periodic boundary condition.
public class Box {
	private int n; //the number of particles
	private static final double d = 1.0; //the dimension of the box
	private double diam;//the diameter of the particles
	private Particle[] partiArr;
	private static final int bound = 1; //the cutoff distance for calculating the energy at a particle
	
	/** constructs a box with randomized hard sphere distribution, with period boundary conditions
 * 	@param nGiven int, the given numer of particles
 * 	@return Box, the constructed box */
	public Box(int nGiven, double diamGiven) {
		n = nGiven;
		diam = diamGiven;
		partiArr = new Particle[n];
		int i = 0;
		while (i < n) {
			//generate a candidate particle p
			double x = getRandomNumberInRange(0,d);
			double y = getRandomNumberInRange(0,d);
			double z = getRandomNumberInRange(0,d);
			Particle p = new Particle(x,y,z,diam);	
			//check if the particle p collides with already generated particles
			//if (collisionChecker(p,i)==0) {	
				partiArr[i] = p;
				i++;
			//}
			//else {
			//	System.out.println(collisionChecker(p,i));
			//}
		}
	}

	/** a box whose particle positions are predetermined
 *      @param nGiven int, the given numer of particles
 *      @param coordinates double[][] the predetermined particle positions
 *      @return Box, the constructed box */
	public Box(int nGiven, double diamGiven, double[][] coordinates) {
		if (nGiven > coordinates.length) {
			throw new  IllegalArgumentException("nGiven must be <= coordinates.length");
		}
		n = nGiven;
		diam = diamGiven;
		partiArr = new Particle[n];
		for (int i = 0; i < n; i++) {
			double x = coordinates[i][0];
			double y = coordinates[i][1];
			double z = coordinates[i][2];
			Particle p = new Particle(x,y,z,diamGiven);
			partiArr[i] = p;
		}
	}

	/** gets d, the dimension of the box
 * 	@return double */
	public double getD() {
		return d;
	}

	/** gets n, the number of particles
 *      @return int */
        public int getN() {
                return n;
        }

	/**get diam, the diameter of the particles
 * 	@return double */
	public double getDiam() {
		return diam;
	}

	/**computes the total number of collisions of all particles in the box
 * 	@return int, the number of collisions */
	public int totalCollision() {
		int sum = 0;
		for (int i=0; i < n; i++) {
			sum = sum + partiCollision(i);
		}
		return (sum-n)/2; //delete the self-collisions
	}

	/**gets the number of collisions of a single particle in the box
 * 	@param ind integer, the index of the particle in partiArr 
 * 	@return int, the collisions of the particle */
	private int partiCollision(int ind) {
		//get the particle in question
		if (ind >= n) {
			throw new IllegalArgumentException("input index must be < the number of particles");
		}
		Particle thisParti = partiArr[ind];
		return collisionChecker(thisParti, n);
	}

	/**checks how many times a particle collides with any of the first m particles in partiArr
 * 	@param thisParti Particle, the particle we are checking
 * 	@param m int, we are checking collisions with the first m particles in partiArr
 * 	@return int, the number of collisions */
	public int collisionChecker(Particle thisParti, int m) {
		//for every of the first k particles in the box, compute their collision to ThisParti
		int collisions = 0;
		double diam = thisParti.getDiam();
                for (int i = 0; i < m ; i++) {
                        Particle thatParti = partiArr[i];
			if (minDist(thisParti,thatParti) < diam) {
				collisions ++;
			}
		}
		return collisions;
	}

	/**the minimum distance between two particles in PBC
 * 	@param thisP Particle
 * 	@param another Particle, another particle 
 * 	@return the minimum distance to that particle among all its replicates */
	public double minDist(Particle thisP, Particle another) {
                double thatX = another.getx();
                double thatY = another.gety();
                double thatZ = another.getz();
                double miniDx = minIn3(thatX,thatX+d,thatX-d,thisP.getx());
                double miniDy = minIn3(thatY,thatY+d,thatY-d,thisP.gety());
                double miniDz = minIn3(thatZ,thatZ+d,thatZ-d,thisP.getz());
                return Math.sqrt(miniDx*miniDx + miniDy*miniDy + miniDz*miniDz);
        }

	public static double minIn3(double a, double b, double c, double center) {
		return Math.min(Math.abs(a-center),Math.min(Math.abs(b-center),Math.abs(c-center)));
	}

	/**moves a random particle in partiArr in a random direction by a random distance between 0 and maxDist
 *	@param maxDist double, the maximum distance to move for a movement in each dimension */
	
	public void move(double maxDist) {
		//randomly choose a particle to move
		int ind = 0;
		Particle thisParti = partiArr[0];
		//randomly choose distances to move in x, y, z respectively
		boolean hasCollision = true;
		double distX = 0;
		double distY = 0;
		double distZ = 0;
		while (hasCollision) {
			ind = (int) Math.floor(getRandomNumberInRange(0.0,(double)n));
			thisParti = partiArr[ind];
			distX = getRandomNumberInRange(-maxDist,maxDist);
			distY = getRandomNumberInRange(-maxDist,maxDist);
			distZ = getRandomNumberInRange(-maxDist,maxDist);
			Movement m = new Movement(thisParti, distX, distY, distZ);
			move(m);
			if (partiCollision(ind) <= 1) {
				hasCollision = false;
			}
			else {
				move(m.reverse());
			}
		}
	}

	/**moves a Particle with a Movement
 * 	@param m, the Movement */
	public void move(Movement m) {
		Particle p = m.getParticle();
		double x = p.getx();
		double y = p.gety();
		double z = p.getz();
		p.setX(positiveModulo(x + m.getDx(),d));
		p.setY(positiveModulo(y + m.getDy(),d));
		p.setZ(positiveModulo(z + m.getDz(),d));
	}
	

	/** returns the array of particles
 * 	@return a newly constructed array of particles that is a deep copy of partiArr */
	public Particle[] toArray() {
		Particle[] partiArrCopy = new Particle[n];
		for (int i = 0; i < n; i++) {
			double x = partiArr[i].getx();
			double y = partiArr[i].gety();
			double z = partiArr[i].getz();
			Particle pCopy = new Particle(x,y,z,diam);
			partiArrCopy[i] = pCopy;
		}
		return partiArrCopy;
	}

	/** returns the contant of partiArray as a string
 * 	@return the content of partiArr */
	public String toString() {
		String result = "";
		for (int i = 0; i < n; i++) {
			result = result + partiArr[i].toString() + "\n";
		}
		return result;
	}

	//auxillary functions
	private static double getRandomNumberInRange(double min, double max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
	        }		
		Random r = new Random();	
       		return r.nextDouble()*(max - min) + min;
	}
	
		
	private static double positiveModulo(double x, double d) {
		double result = x%d;
		if (result < 0) {
			result = result + d;
		}
		return result;
	}

}
