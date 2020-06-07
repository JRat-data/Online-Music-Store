package cs636.music.domain;

import java.io.Serializable;
import java.util.Date;

import cs636.music.domain.User;

/**
 * Download 
 * Like Murach, pg. 649 except:
 * --instead of productCode, has ref to the specific Track
 * --exposes download id as a property, in case we want to use it.
 * This class has setters, as a convenience for creating objects,
 * but these objects, once created, never change.
 */
public class Download implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private User user;
	private Track track;

	private Date downloadDate;

	public Download() {
		user = null;
		downloadDate = new Date();
	}

	public long getDownloadId() {
		return id;
	}

	public void setDownloadId(long download_id) {
		this.id = download_id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User u) {
		user = u;
	}
	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public Date getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(Date download_date) {
		this.downloadDate = download_date;
	}

}
