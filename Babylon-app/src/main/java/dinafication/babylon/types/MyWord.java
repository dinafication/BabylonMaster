package dinafication.babylon.types;

public class MyWord {
	
	public MyWord(String word, String note, String id) {
		this.word = word;
		this.note = note;
		this.id = id;
	}
	
	private String word;
	private String note;
	private String id;
	
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
