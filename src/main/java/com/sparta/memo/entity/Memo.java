package com.sparta.memo.entity;

import com.sparta.memo.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Memo {
	private Long id;
	private String title;
	private String contents;

	public void update(RequestDto requestDto) {
		this.title = requestDto.getTitle();
		this.contents = requestDto.getContents();
	}

	public void updateTitle(RequestDto requestDto) {
		this.title = requestDto.getTitle();
	}
}
