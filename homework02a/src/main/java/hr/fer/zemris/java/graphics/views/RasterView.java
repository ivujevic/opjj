/**
 * Paket sadrži sve razrede potrebne za reprokukciju slike
 */
package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * 
 * Sučelje RasterView
 * 
 */
public interface RasterView {

	/**
	 * Metoda za crtanje lika na <code>raster</code>
	 */
	public void produce(BWRaster raster);
}
