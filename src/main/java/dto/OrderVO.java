package dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderVO {
	private int odseq;
	private int oseq;
	private String id;
	private Date indate;
	private String mname;
	private String zipnum;
	private String address;
	private String phone;
	private int pseq;
	private String pname;
	private int quantity;
	private int price2;
	private String result;
}

