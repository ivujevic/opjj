/**
 * 
 */
package hr.fer.zemris.java.tecaj_06.rays;


/**
 * Metoda koja implementira metoda potrebne za rad sa sferom.
 * 
 * @author Ivan
 * 
 */
public class Sphere extends GraphicalObject {

	Point3D center;
	double radius;
	double kdr;
	double kdg;
	double kdb;
	double krr;
	double krg;
	double krb;
	double krn;
	boolean outer = false;

	/**
	 * Konstruktor koji stvara novu sferu.
	 * 
	 * @param center
	 * @param radius
	 * @param kdr
	 * @param kdg
	 * @param kdb
	 * @param krr
	 * @param krg
	 * @param krb
	 * @param krn
	 */
	public Sphere(final Point3D center, final double radius, final double kdr, final double kdg, final double kdb, final double krr, final double krg,
			final double krb, final double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public final RayIntersection findClosestRayIntersection(final Ray ray) {

		double b = (ray.start.sub(this.center).scalarProduct(ray.direction));
		double c = ray.start.sub(this.center).scalarProduct(ray.start.sub(this.center)) - this.radius
				* this.radius;

		double disc = Math.sqrt(b * b - c);

		if (disc < 0) {
			this.outer = true;
			return null;
		}

		double d1 = -b + disc;
		double d2 = -b - disc;
		
		if(d1<0 && d2 <0) {
			return null;
		}
		double minDistance=d1;
		if(d1 <0 || d1>d2 && d2>0) {
			minDistance = d2;
		}
		
		Point3D point = ray.start.add(ray.direction.scalarMultiply(minDistance));


		return new RayIntersection(point, minDistance, outer) {

			@Override
			public Point3D getNormal() {
				return getPoint().sub(getCenter()).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

	/**
	 * Metoda koja provjerava nalazi li se točka izvan sfere.
	 * 
	 * @return the outer
	 */
	public final boolean isOuter() {
		return this.outer;
	}

	/**
	 * Metoda koja vraća točku u kojoj je centar sfere
	 * 
	 * @return the center
	 */
	public final Point3D getCenter() {
		return center;
	}
}
