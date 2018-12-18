import java.util.*;

public class TestRadialStat {
	public static void main(String[] args) {
		System.out.println(Particle.diam);
		Box b = new Box(500);
		Scanner sc = new Scanner(System.in);
		//System.out.println("Enter r, thickness:");
		//double r = sc.nextDouble();
		//double thickness = sc.nextDouble();
		double thickness = 0.05;
		for (double r = 0.04; r <= 0.5; r = r + thickness) {
			int count = RadialStat.countInShell(b,r-thickness/2,r+thickness/2);
			double g2r = RadialStat.g2ForShell(b,r,thickness);
			System.out.println(r+" "+count+" "+g2r);
		}
	}
}
