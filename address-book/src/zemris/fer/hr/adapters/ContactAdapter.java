package zemris.fer.hr.adapters;

import java.util.List;

import zemris.fer.hr.models.Contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Razred koji predstavlja adapter za rad s listom kontakata. Vraæa broj
 * elementa u listu, vraæa odabrani kontakt u listi, briše kontakt iz liste.
 * Element liste se prikazuje na naèin da je u prvom redu ispisano ime kontakta,
 * dok je u drugom redu ispisan broj telefona te email adresa odvojeni zarezom
 * 
 */
public class ContactAdapter extends ArrayAdapter<Contact> {

	/**
	 * Referenca na litu u kojoj su pohranjeni kontakti.
	 */
	private static List<Contact> contacts;

	public ContactAdapter(Context context, List<Contact> contacts) {
		super(context, android.R.layout.simple_list_item_2, contacts);
		this.contacts = contacts;
	}

	@Override
	public int getCount() {
		return contacts.size();
	}

	@Override
	public Contact getItem(int position) {
		return contacts.get(position);
	}

	@Override
	public void remove(Contact object) {
		contacts.remove(object);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(android.R.layout.simple_list_item_2,
					parent, false);
		}

		// sadržaj prvog i drugug retka elementa liste.
		TextView firstRow = (TextView) convertView
				.findViewById(android.R.id.text1);
		TextView secondRow = (TextView) convertView
				.findViewById(android.R.id.text2);

		firstRow.setText(getItem(position).getName());
		secondRow.setText(getItem(position).getPhone() + ","
				+ getItem(position).getEmail());

		return convertView;
	}
}
