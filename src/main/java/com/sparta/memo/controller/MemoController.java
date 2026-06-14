package com.sparta.memo.controller;

import com.sparta.memo.dto.RequestDto;
import com.sparta.memo.dto.ResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/memos")
public class MemoController {

	// 지료구조가 DB 역할 수행
	private final Map<Long, Memo> memoList = new HashMap<>();

	@PostMapping
	public ResponseDto createMemo(@RequestBody RequestDto requestDto
	) {
        // 식별자가 1씩 증가 하도록 만듦
		Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;

		// 요청받은 데이터로 Memo 객체 생성
		Memo memo = new Memo(memoId, requestDto.getTitle(), requestDto.getContents());

		// InMemory DB에 Memo 저장
		memoList.put(memoId, memo);

		return new ResponseDto(memo);
	}

	@GetMapping("/{id}")
	public ResponseDto findMemoById(@PathVariable Long id
	) {
		Memo memo = memoList.get(id);

		return new ResponseDto(memo);
	}

	@PutMapping("/{id}")
	public ResponseDto updateMemoById(@PathVariable Long id,
									  @RequestBody RequestDto requestDto
	) {
		Memo memo = memoList.get(id);

		memo.update(requestDto);

		return new ResponseDto(memo);
	}

	@DeleteMapping("/{id}")
	public void deleteMemoById(@PathVariable Long id
	) {
		memoList.remove(id);
	}
}
