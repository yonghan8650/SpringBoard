package com.itwillbs.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BoardVO {
	private int bno;
	private String tilte;
	private String content;
	private String writer;
	private int viewcnt;
	private Timestamp regdate;
}
