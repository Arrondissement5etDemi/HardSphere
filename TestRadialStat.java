import java.util.*;

public class TestRadialStat {
	public static void main(String[] args) {
		Box b = new Box(500);
		System.out.println(b.toArray()[0].getDiam());
		//Scanner sc = new Scanner(System.in);
		//System.out.println("Enter thickness:");
		//double thickness = sc.nextDouble();
		double thickness = 0.003;
		double[][] myG2 = RadialStat.g2Table(b,thickness);
		for (int i = 0; i < myG2.length; i++) {
			System.out.println(myG2[i][0] + " " + myG2[i][1]);
		}
	}
}
