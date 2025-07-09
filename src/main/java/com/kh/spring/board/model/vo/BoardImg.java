package com.kh.spring.board.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BoardImg {
	private int boardImgNo;
	private String originName; // 파일의 원본 이름
	private String changeName; // 파일의 수정된 이름
	private int refBno; // 연관 게시글 번호, BoardNo의 외래키
	private int imgLevel;
}
