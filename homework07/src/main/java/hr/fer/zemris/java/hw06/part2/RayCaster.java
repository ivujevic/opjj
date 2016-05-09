/**
 * 
 */
package hr.fer.zemris.java.hw06.part2;

import java.util.List;

import hr.fer.zemris.java.tecaj_06.rays.GraphicalObject;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerProducer;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerResultObserver;
import hr.fer.zemris.java.tecaj_06.rays.LightSource;
import hr.fer.zemris.java.tecaj_06.rays.Point3D;
import hr.fer.zemris.java.tecaj_06.rays.Ray;
import hr.fer.zemris.java.tecaj_06.rays.RayIntersection;
import hr.fer.zemris.java.tecaj_06.rays.RayTracerViewer;
import hr.fer.zemris.java.tecaj_06.rays.Scene;

/**
 * Program u kojem se crta 3D sfera .
 * 
 * @author Ivan
 * 
 */
public class RayCaster {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Metoda koja postavlja početne vrijednosti potrebno za crtanje objekata.
	 * 
	 * @return
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(final Point3D eye, final Point3D view, final Point3D viewUp, final double horizontal,
					final double vertical, final int width, final int height, final long requestNo, final IRayTracerResultObserver observer) {

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D eyeView = view.sub(eye).modifyNormalize();

				Point3D yAxis = viewUp.normalize()
						.sub(eyeView.scalarMultiply(viewUp.normalize().scalarProduct(eyeView))).normalize();
				Point3D xAxis = eyeView.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2.0)).add(
						yAxis.scalarMultiply(vertical / 2.0));

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(
								xAxis.scalarMultiply(x / (width - 1.0) * horizontal)).sub(
								yAxis.scalarMultiply(y / (height - 1.0) * vertical));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};

	}

	/**
	 * @param scene
	 *            Scena
	 * @param ray
	 *            Zraka od oka do točke
	 * @param rgb
	 *            Polje boja
	 */
	protected static void tracer(final Scene scene, final Ray ray, final short[] rgb) {

		List<GraphicalObject> objects = scene.getObjects();

		RayIntersection closestIntersection = null;
		double distance = Double.MAX_VALUE;

		for (GraphicalObject object : objects) {

			RayIntersection intersection = object.findClosestRayIntersection(ray);

			if (intersection != null && intersection.getDistance() < distance) {
				distance = intersection.getDistance();
				closestIntersection = intersection;
			}
		}

		if (closestIntersection == null) {
			rgb[0] = rgb[1] = rgb[2] = 0;
		}
		else {
			determineColorFor(closestIntersection, scene, rgb,ray);
		}

	}

	/**
	 * Metoda koja određuje boju.
	 * 
	 * @param closestIntersection
	 *            Najbliže sjecište
	 * @param scene
	 *            Scena
	 * @param rgb
	 *            Polje boja
	 * @param ray 
	 */
	private static void determineColorFor(final RayIntersection intersection, final Scene scene, final short[] rgb, final Ray ray) {

		rgb[0] = rgb[1] = rgb[2] = 15;

		List<LightSource> lights = scene.getLights();
		List<GraphicalObject> objects = scene.getObjects();

		for (LightSource light : lights) {

			RayIntersection closestIntersection = null;
			Ray rayLight = Ray.fromPoints(light.getPoint(), intersection.getPoint());
			double distance = Double.MAX_VALUE;

			for (GraphicalObject object : objects) {
				RayIntersection intersection1 = object.findClosestRayIntersection(rayLight);
				if (intersection1 != null && intersection1.getDistance() < distance) {
					distance = intersection1.getDistance();
					closestIntersection = intersection1;
				}
			}

			if (closestIntersection != null
					&& (closestIntersection.getPoint().sub(light.getPoint()).norm() + 0.001 <= intersection
							.getPoint().sub(light.getPoint()).norm())) {
				continue;
			}
			else {
				addDifuseComponent(rgb, light, closestIntersection);
				addReflectiveComponent(rayLight,ray , rgb, closestIntersection,light);
			}
		}
	}

	/**
	 * Metoda koja dodaje boju nastalu zbog difuzne zrake.
	 * 
	 * @param rgb
	 *            Polje boja
	 * @param light
	 *            Osvjetljenje.
	 * @param intersection
	 *            Sjecište s objektom.
	 */
	private static void addDifuseComponent(final short[] rgb, final LightSource light, final RayIntersection intersection) {

		double[] tempRgb = new double[3];
		tempRgb[0] = light.getR()
				* intersection.getKdr()
				* Math.max(
						light.getPoint().sub(intersection.getPoint()).modifyNormalize()
								.scalarProduct(intersection.getNormal().normalize()), 0);
		tempRgb[1] = light.getG()
				* intersection.getKdg()
				* Math.max(
						light.getPoint().sub(intersection.getPoint()).modifyNormalize()
								.scalarProduct(intersection.getNormal().normalize()), 0);
		tempRgb[2] = light.getB()
				* intersection.getKdb()
				* Math.max(
						light.getPoint().sub(intersection.getPoint()).modifyNormalize()
								.scalarProduct(intersection.getNormal().normalize()), 0);
		rgb[0] += (short) tempRgb[0];
		rgb[1] += (short) tempRgb[1];
		rgb[2] += (short) tempRgb[2];
	}

	/**
	 * Metoda koja dodaje boju nastalu zbog reflektirane zrake.
	 * 
	 * @param rayLight
	 *            Osvjetljenje
	 * @param ray
	 *            Zraka od osvjetljenja do sjecišta
	 * @param rgb
	 *            Polje boja
	 * @param intersection
	 *            Sjecište
	 * @param light 
	 */
	private static void addReflectiveComponent(final Ray rayLight, final Ray ray, final short[] rgb,
			final RayIntersection intersection, final LightSource light) {

		
		Point3D l = rayLight.direction.negate().normalize();
		Point3D n = intersection.getNormal().normalize();
		
		Point3D r = n.scalarMultiply(2.0*l.scalarProduct(n)).sub(l).normalize();
		Point3D v = ray.start.sub(intersection.getPoint()).normalize();
		double cos = v.scalarProduct(r);
		if (Double.compare(cos, 0) >= 0) {
			cos = Math.pow(cos, intersection.getKrn());

			double[] tempRgb = new double[3];
			tempRgb[0] = light.getR() * intersection.getKrr() * cos;
			tempRgb[1] = light.getG() * intersection.getKrg() * cos;
			tempRgb[2] = light.getB() * intersection.getKrb() * cos;
			rgb[0] += (short) tempRgb[0];
			rgb[1] += (short) tempRgb[1];
			rgb[2] += (short) tempRgb[2];
		}
	}
}
