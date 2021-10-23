public class NBody {
	public static double readRadius(String file) {
		In in = new In(file);
		int firstItem = in.readInt();
		double secondItem = in.readDouble();
		return secondItem;
	}
	public static Body[] readBodies(String file) {
		In in = new In(file);
		int numPlanets = in.readInt();
		double radius = in.readDouble();
		Body[] planetArr = new Body[numPlanets];
		for (int i = 0; i < numPlanets; i = i + 1) {
			double xPos = in.readDouble();
			double yPos = in.readDouble();
			double xVel = in.readDouble();
			double yVel = in.readDouble();
			double mass = in.readDouble();
			String name = in.readString();
			Body planet = new Body(xPos, yPos, xVel, yVel, mass, name);
			planetArr[i] = planet;
		}
		return planetArr;
	}
	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		int tInt = (int) Math.round(T);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radi = readRadius(filename);
		Body[] redBodies = readBodies(filename);
		StdDraw.setScale(-radi, radi);
		StdDraw.clear();
		StdDraw.picture(0,0, "images/starfield.jpg");
		for (Body buddy : redBodies) {
			buddy.draw();
		}
		StdDraw.enableDoubleBuffering();

		double time = 0;
		while (time <= T) {
			double[] xForces = new double[redBodies.length];
			double [] yForces = new double[redBodies.length];
			for (int i = 0; i < redBodies.length; i = i + 1) {
				xForces[i] = redBodies[i].calcNetForceExertedByX(redBodies);
				yForces[i] = redBodies[i].calcNetForceExertedByY(redBodies);
			if (i == redBodies.length -1) {
				for (int j = 0; j < redBodies.length; j = j + 1) {
					redBodies[j].update(dt, xForces[j], yForces[j]);
				}
			}
		}
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for (Body bodd: redBodies) {
			bodd.draw();
		}
		StdDraw.show();
		StdDraw.pause(10);
		time = time + dt;
	}
		StdOut.printf("%d\n", redBodies.length);
		StdOut.printf("%.2e\n", radi);
		for (int i = 0; i < redBodies.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  	redBodies[i].xxPos, redBodies[i].yyPos, redBodies[i].xxVel,
                  	redBodies[i].yyVel, redBodies[i].mass, redBodies[i].imgFileName);   
	}

}
}