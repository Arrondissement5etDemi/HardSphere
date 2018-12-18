public class Movement {
	private Particle p;
	private double deltaX, deltaY, deltaZ;

	public Movement(Particle pa, double x, double y, double z) {
		p = pa;
		deltaX = x;
		deltaY = y;
		deltaZ = z;		
	}

	public Particle getParticle() {
		return p;
	}

	public double getDx() {
		return deltaX;
	} 

	public double getDy() {
                return deltaY;
        }

	public double getDz() {
                return deltaZ;
        }

	public Movement reverse() {
		Movement rev = new Movement(p, -deltaX, -deltaY, -deltaZ);
		return rev;
	}

}
