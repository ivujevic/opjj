package zemris.fer.hr;

import hr.infinum.fer.iv46885.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zemris.fer.hr.adapters.ContactAdapter;
import zemris.fer.hr.models.Contact;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * Razred koji predstavlja poèetni ekran aplikacije. Korisniku se nudi dugme za
 * dodavanje novih kontakata te je ispod njega lista postojeæih kontakata.
 * Pritiskom na neki element liste otvaraju se informacije o tom kontaktu, dok
 * se dugim klikom na element liste otvara dijalog koji korisnika pita želi li
 * izbrisati taj kontakt.
 */
public class HomeActivity extends Activity {

	/**
	 * Dugme koje otvara novi prozor u kojem se dodaje novi kontakt.
	 */
	private Button btnAdd;

	/**
	 * Referenca na listView u kojem æe se prikazati postojeæi kontakti.
	 */
	private ListView list;

	/**
	 * Lista u kojoj su prikazani postojeæi kontakti.
	 */
	public static List<Contact> contacts = new ArrayList<Contact>();

	/**
	 * Adapter koji omoguæuje rad s listom kontakata.
	 */
	private static ContactAdapter contactAdapter;

	/**
	 * Datoteka u kojoj su zapisani podatci o kontaktima.
	 */
	private static final String CONTACTS_FILE = "people.json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		btnAdd = (Button) findViewById(R.id.btnAdd);
		list = (ListView) findViewById(R.id.list);

		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeActivity.this,
						AddContactActivity.class);
				startActivityForResult(i, 100);
			}
		});

		// stvori i prikaži listu kontakata
		createList();
	}

	private void createList() {

		// ako do sada nije uèitana, uèitaj listu.
		if (contacts.isEmpty()) {
			getContacts();
		}

		contactAdapter = new ContactAdapter(this, contacts);
		list.setAdapter(contactAdapter);

		// listener koji reagira na klik na element liste.
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(HomeActivity.this, ProfileActivity.class);

				// sljedeæem prozoru predaj poziciju odabradnog elementa koja je
				// zapisana u parametru arg2
				i.putExtra(ProfileActivity.EXTRAS_POSITION, arg2);
				startActivity(i);
			}
		});

		/*
		 * listener koji reagira na dugi klik na element liste. Nudi dijalog
		 * gdje korisnik odluèuje želi li ili ne želi izbrisati element.
		 */
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {

				AlertDialog.Builder builder = new Builder(HomeActivity.this);

				builder.setTitle("Brisanje kontakta");
				builder.setMessage("Jeste li sigurni da želite izbrisati kontakt\n "
						+ contacts.get(arg2).getName());

				builder.setPositiveButton("DA",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								contactAdapter.remove(contacts.get(arg2));
								dialog.dismiss();
							}
						});

				builder.setNegativeButton("NE", null);
				builder.show();
				return false;
			}
		});
	}

	/**
	 * Metoda koja èita datoteku u kojoj su zapisana imena kontakata i stvorene
	 * kontakte sprema u listu.
	 */
	private void getContacts() {

		AssetManager asm = this.getAssets();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					asm.open(CONTACTS_FILE)));
			String filesPrompt = "";
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;

				filesPrompt += line;

			}

			JSONObject contacts = new JSONObject(filesPrompt);
			JSONArray contactsArray = contacts.getJSONArray("people");

			for (int i = 0; i < contactsArray.length(); i++) {
				JSONObject contactInformation = contactsArray.getJSONObject(i)
						.getJSONObject("person");

				// stvori novi kontakt i dodaj ga u listu.
				createNewContact(contactInformation, asm);

			}

		} catch (IOException e) {
			Log.d("HomeActivity", "Ne mogu èitati datoteku");
		} catch (JSONException e) {
			Log.d("HomeActivity", "Json");
		}
	}

	/**
	 * Metoda koja stvara novi kontakt iz JSON objekta te ga dodaje u listu
	 * kontakata.
	 * 
	 * @param contactInformation
	 *            JSON objekt koji sadrži informacije o kontaktu.
	 * @param asm
	 *            AssetManager iz kojeg se uèitava slika za kontakt.
	 */
	private void createNewContact(JSONObject contactInformation,
			AssetManager asm) {

		Contact contact = new Contact();
		try {
			contact.setName(contactInformation.getString("name"));
			contact.setPhone(contactInformation.getString("phone"));
			contact.setEmail(contactInformation.getString("email"));
			contact.setFacebookLink(contactInformation
					.getString("facebook_profile"));
			contact.setNote(contactInformation.getString("note"));

			contact.setImage(BitmapFactory.decodeStream(asm
					.open(contactInformation.getString("image"))));

		} catch (JSONException e) {

		} catch (IOException e) {

		}

		// dodaj kontakt u listu
		contacts.add(contact);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		contactAdapter.notifyDataSetChanged();
	}

}
