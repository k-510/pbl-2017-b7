package jp.enpit.lama.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Users {

	@XmlElement(name = "users")
	private List<User> list = new ArrayList<>();

	public Users() {
	}

	public Users(List<User> Userlist) {
		list.addAll(Userlist);
	}

	public List<User> user() {
		return Collections.unmodifiableList(list);
	}

	public int size() {
		return list.size();
	}

	public Iterator<User> iterator() {
		return list.iterator();
	}

	public Users[] toArray() {
		return list.toArray(new Users[size()]);
	}

}
