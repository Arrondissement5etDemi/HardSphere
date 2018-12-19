import java.util.*;

public class HardSphere {
	public static void main(String[] args) {
		//create a box with 500 particles
		int n = 500;
		Box pandora3D = new Box(n);
		int collisionNow = pandora3D.totalCollision();		

		for (int i = 1; i <= 1000000; i++) {
			Movement m = pandora3D.move(0.02);
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

		double thickness = 0.008;
		System.out.println("thickness = " + thickness);
                double[][] g2 = RadialStat.g2Table(pandora3D,thickness);
                for (int i = 0; i < g2.length; i++) {
                        System.out.println(g2[i][0] + " " + g2[i][1]);
                }
	
	}	
}
