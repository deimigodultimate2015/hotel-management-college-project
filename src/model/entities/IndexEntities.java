package model.entities;

import java.util.Date;

public class IndexEntities {
	private String maKH ;
	private int maDP;
	private String roomName;
	private String roomType;
	private Date CIn;
	private Date COut;
	private boolean invoice;
	
	public IndexEntities() {
		
	}

	public IndexEntities(String maKH, int maDP, String roomName, String roomType, Date cIn, Date cOut,
			boolean invoice) {
		super();
		this.maKH = maKH;
		this.maDP = maDP;
		this.roomName = roomName;
		this.roomType = roomType;
		CIn = cIn;
		COut = cOut;
		this.invoice = invoice;
	}

	public boolean isInvoice() {
		return invoice;
	}

	public void setInvoice(boolean invoice) {
		this.invoice = invoice;
	}

	@Override
	public String toString() {
		return "IndexEntities [maKH=" + maKH + ", maDP=" + maDP + ", roomName=" + roomName + ", roomType=" + roomType
				+ ", CIn=" + CIn + ", COut=" + COut + "]";
	}

	public int getMaDP() {
		return maDP;
	}

	public void setMaDP(int maDP) {
		this.maDP = maDP;
	}

	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public Date getCIn() {
		return CIn;
	}
	public void setCIn(Date cIn) {
		CIn = cIn;
	}
	public Date getCOut() {
		return COut;
	}
	public void setCOut(Date cOut) {
		COut = cOut;
	}
	
	public String getInvoiceAF() {
		return invoice == true?"Đã xuất":"Chưa xuất";
	}
}
