package zemris.fer.hr;

import hr.infinum.fer.iv46885.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zemris.fer.hr.models.Contact;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Razred u kojem se implementira korisnièko suèelje koje æe korisniku omoguæiti
 * unošenje novog kontakta. Može se unijeti ime, broj, email adresa, bilješka te
 * linnk na facebook profil. Korisniku se nudi i moguænost odabira slike
 * pritiskom na dugme <code>Odaberi sliku</code>. Unos korisnik potvrðuje
 * pritiskom na dugme <code>Spremi</code>, a odustaje pritiskom na dugme
 * <code>Odustani</code>
 * 
 * 
 */
public class AddContactActivity extends Activity {

	/**
	 * Referenca na okvir za unos imena.
	 */
	private TextView name;

	/**
	 * Referenca na okvir za unos broja telefona.
	 */
	private TextView phone;

	/**
	 * Referenca na okvir za unost email adrese.
	 */
	private TextView email;

	/**
	 * Referenca na okvir za unos linka na facebook profil.
	 */
	private TextView facebookLink;

	/**
	 * Referenca na okvir za unos bilješke.
	 */
	private TextView note;

	/**
	 * Referenca na dugme koji otvara dijalog i nudi moguænost odabira slike.
	 */
	private Button btnChooseImage;

	/**
	 * Referenca na dugme koji sprema kontakt.
	 */
	private Button btnSave;

	/**
	 * Referenca na dugme kojim se odustaje od kreiranja novog kontakta.
	 */
	private Button btnCancel;

	/**
	 * Referenca na kontakt koji se stvara.
	 */
	Contact contact = new Contact();

	/**
	 * Referenca na asset.
	 */
	private AssetManager assetManager;

	/**
	 * Lista u koju se spremaju imena slika iz asset-a.
	 */
	private List<String> assetImages = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);

		// stvori reference.
		name = (TextView) findViewById(R.id.inputName);
		phone = (TextView) findViewById(R.id.inputPhone);
		email = (TextView) findViewById(R.id.inputEmail);
		facebookLink = (TextView) findViewById(R.id.inputFacebookLink);
		note = (TextView) findViewById(R.id.inputNote);

		btnChooseImage = (Button) findViewById(R.id.btnChooseImage);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnCancel = (Button) findViewById(R.id.btnCancel);

		assetManager = this.getAssets();
		String[] assetsFiles = null;

		// Dohvati sve datoteke iz assets-a
		try {
			assetsFiles = assetManager.list("");
		} catch (IOException e) {

		}

		// dodaj slike u listu
		for (String a : assetsFiles) {
			if (a.endsWith("png")) {
				assetImages.add(a);
			}
		}

		// akcija za odabir slike.
		btnChooseImage.setOnClickListener(new OnClickListener() {

			Dialog dialog;

			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new Builder(
						AddContactActivity.this);
				builder.setTitle("Odaberi sliku");

				ListView list = new ListView(AddContactActivity.this);

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						AddContactActivity.this,
						android.R.layout.simple_list_item_1,
						android.R.id.text1, assetImages);
				list.setAdapter(adapter);

				// akcija za odabranu sliku u listi
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						try {
							contact.setImage(BitmapFactory
									.decodeStream(assetManager.open(assetImages
											.get(arg2))));
							dialog.dismiss();
						} catch (IOException e) {

						}

					}
				});
				builder.setView(list);
				dialog = builder.create();
				dialog.show();

			}
		});

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				contact.setName(name.getText().toString());
				contact.setPhone(phone.getText().toString());
				contact.setEmail(email.getText().toString());
				contact.setFacebookLink(facebookLink.getText().toString());
				contact.setNote(note.getText().toString());
				HomeActivity.contacts.add(contact);
				finish();
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
