/**
1 * Paket sadrži sve razrede potrebne za reprokukciju slike
 */
package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Razred koji je implementacija RasterView Crta na raster i određuje vrste
 * znakova
 */
public class SimpleRasterView implements RasterView {

	/**
	 * Varijabla u koju se sprema znak koji predstavlja upaljeni piksel
	 */
	char znakUpaljeno;

	/**
	 * Varijabla u koju se sprema znak koji predstavlja izgašeni piksel
	 */
	char znakUgaseno;

	/**
	 * Kreira novi izgled po željama korisnika
	 * 
	 * @param znakUpaljeno
	 *            Znak koji se koristi za upaljen piksel
	 * @param znakUgaseno
	 *            Znak koji se koristi za izgašen piksel
	 */
	public SimpleRasterView(char znakUpaljeno, char znakUgaseno) {
		this.znakUgaseno = znakUgaseno;
		this.znakUpaljeno = znakUpaljeno;
	}

	/**
	 * Zadani izgled ako korisnik ne želi svoje znakove
	 */
	public SimpleRasterView() {
		this.znakUpaljeno = '*';
		this.znakUgaseno = '.';
	}

	public void produce(BWRaster raster) {
		int visina = raster.getHeight();
		int sirina = raster.getWidth();
		for (int y = 0; y < visina; y++) {
			for (int x = 0; x < sirina; x++) {
				if (raster.isTurnedOn(x, y)) {
					System.out.print(this.znakUpaljeno);
				} else {
					System.out.print(this.znakUgaseno);
				}
			}
			System.out.println();
		}
	}

}
