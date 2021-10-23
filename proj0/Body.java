public class Body {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static double gConstant = 6.67 * Math.pow(10, -11);

	public Body(double xP, double yP, double xV,
		double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	public Body(Body b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}
	public double calcDistance(Body obj2) {
		double xDist = this.xxPos - obj2.xxPos;
		double yDist = this.yyPos - obj2.yyPos;
		return Math.sqrt((xDist * xDist) + (yDist * yDist));
	}
	public double calcForceExertedBy(Body obj2) {
		double distance = this.calcDistance(obj2);
		return (gConstant*this.mass*obj2.mass)/(distance*distance);
	}
	public double calcForceExertedByX(Body obj2) {
		double dx = obj2.xxPos - this.xxPos;
		double dist = this.calcDistance(obj2);
		double exertedForce = this.calcForceExertedBy(obj2);
		double force = (exertedForce*dx)/dist;
		return force;
	}
	public double calcForceExertedByY(Body obj2) {
		double dy = obj2.yyPos - this.yyPos;
		double dist = this.calcDistance(obj2);
		double exertedForce = this.calcForceExertedBy(obj2);
		double force = (exertedForce*dy)/dist;
		return force;
	}
	public double calcNetForceExertedByX(Body[] array) {
		double force = 0;
		for (Body obj2: array) {
			if (this.equals(obj2)) {
				continue;
			}
			force = force + this.calcForceExertedByX(obj2);
		}
		return force;
	}
	public double calcNetForceExertedByY(Body[] array) {
		double force = 0;
		for (Body obj2: array) {
			if (this.equals(obj2)) {
				continue;
			}
			force = force + this.calcForceExertedByY(obj2);
		}
		return force;
	}
	public void update(double dt, double fX, double fY) {
		double aNetx = fX / this.mass;
		double aNety = fY / this.mass;
		this.xxVel = this.xxVel + dt * aNetx;
		this.yyVel = this.yyVel + dt * aNety;
		this.xxPos = this.xxPos + dt * this.xxVel;
		this.yyPos = this.yyPos + dt * this.yyVel;
	}
	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
}