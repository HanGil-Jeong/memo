package com.sparta.memo.controller;

import com.sparta.memo.dto.RequestDto;
import com.sparta.memo.dto.ResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/memos")
public class MemoController {

	// 지료구조가 DB 역할 수행
	private final Map<Long, Memo> memoList = new HashMap<>();

	@PostMapping
	public ResponseEntity<ResponseDto> createMemo(@RequestBody RequestDto requestDto
	) {
        // 식별자가 1씩 증가 하도록 만듦
		Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;

		// 요청받은 데이터로 Memo 객체 생성
		Memo memo = new Memo(memoId, requestDto.getTitle(), requestDto.getContents());

		// InMemory DB에 Memo 저장
		memoList.put(memoId, memo);

		return new ResponseEntity<>(new ResponseDto(memo), HttpStatus.CREATED);
	}

	@GetMapping()
	public List<ResponseDto> findAllMemo() {

		// init List
		List<ResponseDto> responseList = new ArrayList<>();

		// HashMap<Memo> To List<ResponseDto> (V1)
		for (Memo memo : memoList.values()) {
			ResponseDto responseDto = new ResponseDto(memo);
			responseList.add(responseDto);
		}

		// Map To List (V2)
//		 responseList = memoList.values()
//		 .stream()
//		 .map(ResponseDto::new)
//		 .toList();

		return responseList;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDto> findMemoById(@PathVariable Long id
	) {

		Memo memo = memoList.get(id);

		if (memo == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ResponseDto(memo), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDto> updateMemoById(@PathVariable Long id,
									  @RequestBody RequestDto requestDto
	) {
		Memo memo = memoList.get(id);

		// NPE 방지
		if (memo == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// 필수값 검증
		if (requestDto.getTitle() == null || requestDto.getContents() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		// 메모 수정
		memo.update(requestDto);

		return new ResponseEntity<>(new ResponseDto(memo), HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ResponseDto> updateTitle(@PathVariable Long id,
	                                                  @RequestBody RequestDto requestDto
	) {
		Memo memo = memoList.get(id);

		// NPE 방지
		if (memo == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// 필수값 검증
		if (requestDto.getTitle() == null || requestDto.getContents() != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		// 메모 제목 수정
		memo.updateTitle(requestDto);

		return new ResponseEntity<>(new ResponseDto(memo), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMemoById(@PathVariable Long id
	) {
		// memoList의 Key값에 id를 포함하고 있는 경우
		if (memoList.containsKey(id)) {

			// key가 id인 value 삭제
			memoList.remove(id);

			return new ResponseEntity<>(HttpStatus.OK);
		}

		// memoList의 Key값에 id를 포함하고 있지 않은 경우
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
