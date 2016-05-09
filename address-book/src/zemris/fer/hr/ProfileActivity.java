package zemris.fer.hr;

import zemris.fer.hr.models.Contact;
import hr.infinum.fer.iv46885.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Razred koji implementira korisnièko suèelje u kojem se prikazuje informacije
 * o odabranom kontaktu. Klikom na sliku otvara se kamera te se nova slika
 * postavlja za sliku kontakta. Pritiskom na broj telefona korisniku se nudi
 * moguænost slanja poruke ili poziva na taj broj. Pritiskom na email adresu
 * korisniku se omoguæuje slanje email poruke na navedenu adresu. Klikom na link
 * na facebook profil otvara se navedeni facebook profil. Na dnu ekrana se
 * dodaje dugme za zvanje kontakta te dugme za posjet facebook profilu.
 * 
 */
public class ProfileActivity extends Activity {

	/**
	 * Konstata pod kojom se sprema pozicija kontakta koji je odabran u listi.
	 */
	public static final String EXTRAS_POSITION = "contactPostion";

	/**
	 * Konstanta pod kojem se šalje slika uslikana kamerom.
	 */
	public static final int PICTURE_CODE = 1;

	/**
	 * Referenca na kontakt.
	 */
	private Contact contact;

	/**
	 * Ime kontakta.
	 */
	private TextView name;

	/**
	 * Broj telefona kontakta.
	 */
	private TextView phone;

	/**
	 * Email adresa kontakta.
	 */
	private TextView email;

	/**
	 * Bilješka kontakta.
	 */
	private TextView note;

	/**
	 * Facebook profil kontakta.
	 */
	private TextView facebook;

	/**
	 * Slika kontakta.
	 */
	private ImageView image;

	/**
	 * Zvanje kontakta.
	 */
	private Button btnCall;

	/**
	 * Posjet facebook profilu kontakta.
	 */
	private Button btnFacebook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		// stvori reference
		name = (TextView) findViewById(R.id.name);
		phone = (TextView) findViewById(R.id.phone);
		email = (TextView) findViewById(R.id.email);
		facebook = (TextView) findViewById(R.id.facebook);
		note = (TextView) findViewById(R.id.note);
		image = (ImageView) findViewById(R.id.picture);
		btnCall = (Button) findViewById(R.id.btnCall);
		btnFacebook = (Button) findViewById(R.id.btnFacebook);

		// dohvati kontakt s pozije koja je predana.
		Bundle b = getIntent().getExtras();
		if (b != null) {
			contact = HomeActivity.contacts.get(b.getInt(EXTRAS_POSITION));
		}

		// postavi sve informacije
		name.setText(contact.getName());
		phone.setText(contact.getPhone());
		email.setText(contact.getEmail());
		facebook.setText(contact.getFacebookLink());
		note.setText(contact.getNote());
		image.setImageBitmap(contact.getImage());

		// akcija za zvanje
		btnCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ contact.getPhone()));
				startActivity(i);
			}
		});

		// akcija za posjet facebook profilu.
		btnFacebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(contact
						.getFacebookLink()));
				startActivity(i);
			}
		});

		// akcija za otvaranje kamere.
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(i, PICTURE_CODE);

			}
		});

		// akcija za pritisak na broj telefona.
		phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new Builder(ProfileActivity.this);
				builder.setTitle("Poziv/SMS");
				builder.setMessage("Odabreite što želite napraviti!");
				builder.setPositiveButton("Zovi",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent i = new Intent(Intent.ACTION_CALL, Uri
										.parse("tel:" + contact.getPhone()));
								startActivity(i);

							}
						});

				builder.setNeutralButton("SMS",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								Intent i = new Intent(Intent.ACTION_VIEW, Uri
										.parse("sms:" + contact.getPhone()));
								startActivity(i);
							}
						});

				builder.setNegativeButton("Odustani",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();

							}
						});
				builder.show();
			}
		});

		// akcija za pritisak na email adresu.
		email.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(Intent.ACTION_SENDTO);
				i.setType("text/plain");
				i.setData(Uri.parse("mailto:" + contact.getEmail()));
				startActivity(i);
			}
		});

		// akcija za pritisak na facebook link
		facebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(contact
						.getFacebookLink()));
				startActivity(i);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ako rezultat izvoðenja nije uspješan iziði
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		// ako je rezultat nastao slikavanjem dohvati sliku i postavi je za
		// sliku kontakta.
		case PICTURE_CODE:
			Bitmap picture = (Bitmap) data.getExtras().get("data");
			contact.setImage(picture);
			image.setImageBitmap(picture);
		}
	}
}
