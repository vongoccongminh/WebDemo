package webdemo.beans;

import java.sql.Blob;

public class Attachment {

	private Long id;
	private String fileName;
	private Blob fileData;
	private String description;
	
	
	public Attachment(Long id, String fileName, Blob fileData, String description) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fileData = fileData;
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Blob getFileData() {
		return fileData;
	}
	public void setFileData(Blob fileData) {
		this.fileData = fileData;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
