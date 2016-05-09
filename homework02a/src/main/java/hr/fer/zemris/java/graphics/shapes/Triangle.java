package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

public class Triangle extends GeometricShape {
	/**
	 * X koordinate prve unesene točke
	 */
	private int x1;

	/**
	 * Y koordinata prve unesene točke
	 */
	private int y1;

	/**
	 * X koordinata druge unesene točke
	 */
	private int x2;

	/**
	 * Y koordinata druge unesene točke
	 */
	private int y2;

	/**
	 * X koordinata treće unesene točke
	 */
	private int x3;

	/**
	 * Y koordinata treće unesene točke
	 */
	private int y3;

	/**
	 * Kreira novi trokut predhodno provjerivši je li dane točke mogu činiti
	 * trokut
	 * 
	 * @param x1
	 *            Pozicija prve točke na x osi
	 * @param y1
	 *            Pozicija prve točke na y osi
	 * @param x2
	 *            Pozicija druge točke na x osi
	 * @param y2
	 *            Pozicija druge točke na y osi
	 * @param x3
	 *            Pozicija treće točke na x osi
	 * @param y3
	 *            Pozicija treće točke na y osi
	 */
	public Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
		double d1;
		double d2;
		double d3;

		d1 = udaljenost(x1, y1, x2, y2);
		d2 = udaljenost(x1, y1, x3, y3);
		d3 = udaljenost(x2, y2, x3, y3);

		if (!((d1 + d2 > d3) && (d2 + d3 > d1) && (d1 + d3 > d2))) {
			throw new IllegalArgumentException("Dane točke ne čine trokut");
		}

		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.y1 = y1;
		this.y2 = y2;
		this.y3 = y3;
	}

	/**
	 * Ako je točka T unutar trokuta onda je površina svih trokuta manja od
	 * zadanog trokuta
	 * 
	 * @param x
	 *            Pozicija na x osi
	 * @param y
	 *            Pozicija na y osi
	 * @return <code>true</code> ako je točka unutar trokuta, inače
	 *         <code>false</code>
	 * @Override
	 */
	public boolean containsPoint(int x, int y) {
		double povrsinaZadanog = povrsinaTrokuta(this.x1, this.y1, this.x2,
				this.y2, this.x3, this.y3);
		double povrsinaABT = povrsinaTrokuta(this.x1, this.y1, this.x2,
				this.y2, x, y);
		double povrsinaBCT = povrsinaTrokuta(this.x2, this.y2, this.x3,
				this.y3, x, y);
		double povrsinaACT = povrsinaTrokuta(this.x1, this.y1, this.x3,
				this.y3, x, y);
		if (povrsinaABT + povrsinaACT + povrsinaBCT == povrsinaZadanog) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param x1
	 *            Pozicija prve točke na x osi
	 * @param y1
	 *            Pozicija prve točke na y osi
	 * @param x2
	 *            Pozicija druge točke na x osi
	 * @param y2
	 *            Pozicija druge točke na y osi
	 * @param x
	 *            Pozicija treće točke na x osi
	 * @param y
	 *            Pozicija treće točke na y osi
	 * @return <code>false</code> ako je točka izvan dužine , inače
	 *         <code>false</code>
	 */
	private boolean provjeriTriTocke(int x1, int y1, int x2, int y2, int x,
			int y) {
		if (udaljenost(x1, y1, x, y) > udaljenost(x1, y1, x2, y2)
				|| udaljenost(x2, y2, x, y) > udaljenost(x1, y1, x2, y2)) {
			return false;
		}
		return true;
	}

	private double udaljenost(double x1, double y1, double x2, double y2) {
		return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
	}

	/**
	 * Računa površinu trokuta
	 * 
	 * @param x1
	 *            Pozicija prve točke na x osi
	 * @param y1
	 *            Pozicija prve točke na y osi
	 * @param x2
	 *            Pozicija druge točke na x osi
	 * @param y2
	 *            Pozicija druge točke na y osi
	 * @param x3
	 *            Pozicija treće točke na x osi
	 * @param y3
	 *            Pozicija treće točke na y osi
	 * @return Površina trokuta
	 */
	private double povrsinaTrokuta(int x1, int y1, int x2, int y2, int x3,
			int y3) {
		double ab = y1 - y2;
		double bc = y2 - y3;
		double ca = y3 - y1;
		return Math.abs((x1 * bc + x2 * ca + x3 * ab)) / 2;
	}

	/**
	 * 
	 * @return x koordinata prve točke
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * 
	 * @return x koordinata druge točke
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * 
	 * @return x koordinata treće točke
	 */
	public int getX3() {
		return x3;
	}

	/**
	 * Postavlja x koordinatu prve točke
	 * 
	 * @param x1
	 *            Vrijednost na koju se postavlja koordinata
	 */
	public void setX1(int x1) {
		this.x1 = x1;
	}

	/**
	 * Postavlja x koordinatu druge točke
	 * 
	 * @param x2
	 *            Vrijednost na koju se postavlja koordinata
	 */
	public void setX2(int x2) {
		this.x2 = x2;
	}

	/**
	 * Postavlja x koordinatu treće točke
	 * 
	 * @param x3
	 *            Vrijednost na koju se postavlja koordinata
	 */
	public void setX3(int x3) {
		this.x3 = x3;
	}

	/**
	 * 
	 * @return y koordinata prve točke
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * y koordinata druge točke
	 * 
	 * @return y koordinata druge točke
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * 
	 * @return y koordinata treće točke
	 */
	public int getY3() {
		return y3;
	}

	/**
	 * Postavlja y koordinatu prve točke
	 * 
	 * @param y1
	 *            Vrijednost na koju se postavlja koordinata
	 */
	public void setY1(int y1) {
		this.y1 = y1;
	}

	/**
	 * Postavlja y koordinatu druge točke
	 * 
	 * @param y2
	 *            Vrijednost na koju se postavlja koordinata
	 */
	public void setY2(int y2) {
		this.y2 = y2;
	}

	/**
	 * Postavlja y koordinatu treće točke
	 * 
	 * @param y3
	 *            Vrijednost na koju se postavlja koordinata
	 */
	public void setY3(int y3) {
		this.y3 = y3;
	}

	@Override
	public void draw(BWRaster r) {
		int minX = Math.min(this.x1, Math.min(this.x2, this.x3));
		int maxX = Math.max(this.x1, Math.max(this.x2, this.x3));
		int minY = Math.min(this.y1, Math.min(this.y2, this.y3));
		int maxY = Math.max(this.y1, Math.max(this.y2, this.y3));

		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x <= maxX; x++) {
				if (this.containsPoint(x, y)) {
					r.turnOn(x, y);
				}
			}
		}
	}
}
