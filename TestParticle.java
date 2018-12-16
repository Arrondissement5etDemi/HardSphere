import java.util.*;

public class TestParticle {
	public static void main(String[] args) {
		//construct a complex number
		ParticleSmall p1 = new ParticleSmall(1.0,1.0,0.0);
		ParticleSmall p2 = new ParticleSmall(1.0,1.001,0.0);
		ParticleSmall p3 = new ParticleSmall(1.0,2.0,0.0);
		System.out.println(p1.collide(p2));//true
		System.out.println(p2.collide(p3));//false
		System.out.println(p1);
	}
}
