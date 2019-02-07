import java.util.*;

public class TestBox {
	public static void main(String[] args) {
		//create a box with 500 particles
		int n = 500;
		Box pandora3D = new Box(n,0.11);
		//int collisionNow = pandora3D.totalCollision();
		
		//for (int i = 1; i <= 20000; i++) {
		//	Movement m = pandora3D.move(0.01);
		//	Particle particleMoved = m.getParticle();
		//	if (pandora3D.collisionChecker(particleMoved,n) > 1) {
		//		pandora3D.move(m.reverse());
		//	}
		//	else {
		//		collisionNow = pandora3D.totalCollision();
		//	}
			//System.out.println(pandora3D.totalCollision()); 
		//}
		//System.out.println(pandora3D);//this is the equil. config.

		//double thickness = 0.009;
                double[][] g2 = RadialStat.g2(pandora3D,60,3.0);
		for (int i = 0; i < 100; i++) {
			Box pandora3D2 = new Box(n,0.11);
			double[][] newG2 = RadialStat.g2(pandora3D2,60,3);
			for (int j = 0; j < g2.length; j++) {
                        	g2[j][1] = g2[j][1] + newG2[j][1];
                	}
			System.out.println(i);	
		}
                for (int i = 0; i < g2.length; i++) {
                        System.out.println(g2[i][0] + " " + g2[i][1]/101);
                }
	
	}	
}
