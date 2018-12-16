import java.util.*;

public class TestBox {
	public static void main(String[] args) {
		//create a box with 500 particles
		Box pandora3D = new Box(500);
		System.out.println(pandora3D.totalCollision());
		System.out.println(pandora3D);
	}	
}
