import java.util.*;

public class TestBox {
	public static void main(String[] args) {
		//create a box with 500 particles
		Box pandora3D = new Box(500);
		int collisionNow = pandora3D.totalCollision();
		
		for (int i = 1; i <= 1000; i++) {
			Movement m = pandora3D.move(0.01);
			if (pandora3D.totalCollision()>collisionNow) {
				pandora3D.move(m.reverse());
			}
			else {
				collisionNow = pandora3D.totalCollision();
			}
			System.out.println(pandora3D.totalCollision()); 
		}

		System.out.println(pandora3D);
	}	
}
