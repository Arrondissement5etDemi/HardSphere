import java.util.*;

public class Particle {
        //data
	private double x,y,z;
	//the small particle for phi = 0.2
	public static double diam = 0.0914156;
	//the large particle for phi = 0.45
	//public static double diam = 0.11978836;
	//ideal gas
	//public static final double diam = 0.0;

	//constructor
        public Particle(double xx, double yy, double zz) {
		x = xx;
                y = yy;
		z = zz;
        }

        //gets the coordinates
        public double getx() {
		return x;
	}
    
	public double gety() {
		return y;
	}

	public double getz() {
		return z;
	}

	//modifiers
	public void setX(double xx) {
		x = xx;
	}

	public void setY(double yy) {
		y = yy;
	}

	public void setZ(double zz) {
		z = zz;
	}

	//distance to another city
	public double distanceto(Particle another) {
		double anotherX = another.getx();
                double anotherY = another.gety();
		double anotherZ = another.getz();
                return Math.sqrt(Math.pow((x - anotherX),2) + Math.pow((y - anotherY),2) +  Math.pow((z - anotherZ),2));
	}

	public boolean equals(Particle another) {
		return (x == another.getx() && y == another.gety() && z == another.getz());
	}

	/**decides if two particles collide (penetrate) each other
 	@param another ParticleSmall, another small particle
	@return whether the two particles collide */
	public boolean collide(Particle another) {
		return this.distanceto(another) < diam;
	}

	public String toString() {
		return "(" + Double.toString(x) + "," + Double.toString(y) + ',' + Double.toString(z) + ")";
	}
}
