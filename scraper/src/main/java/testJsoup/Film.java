package testJsoup;
public class Film{
    //data csv
    int id;
    String link;
    String title;
    double score;
    String[]genres;
    String poster;
    String summary;
    //
    String[]actors;
    int year;
    

    public Film(int id, String link, String title, double score, String[] genres, String poster) {
		super();
		this.id = id;
		this.link = link;
		this.title = title;
		this.score = score;
		this.genres = genres;
		this.poster = poster;
	}


	public Film(){

    }

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public double getScore() {
		return score;
	}


	public void setScore(double score) {
		this.score = score;
	}


	public String[] getGenres() {
		return genres;
	}


	public void setGenres(String[] genres) {
		this.genres = genres;
	}


	public String getPoster() {
		return poster;
	}


	public void setPoster(String poster) {
		this.poster = poster;
	}


	public String[] getActors() {
		return actors;
	}


	public void setActors(String[] actors) {
		this.actors = actors;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "Film{" +
				"id=" + id +
				", link='" + link + '\'' +
				", title='" + title + '\'' +
				", score=" + score +
				", genres=" + java.util.Arrays.toString(genres) +
				", poster='" + poster + '\'' +
				", actors=" + java.util.Arrays.toString(actors) +
				", year=" + year +
				'}';
	}
}