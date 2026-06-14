package com.sparta.memo.dto;

import com.sparta.memo.entity.Memo;
import lombok.Getter;

@Getter
public class ResponseDto {
	private Long id;
	private String title;
	private String contents;

	public ResponseDto(Memo memo) {
		this.id = memo.getId();
		this.title = memo.getTitle();
		this.contents = memo.getContents();
	}
}
