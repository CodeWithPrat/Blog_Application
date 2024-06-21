package com.blog.payload;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
	
	private List<PostDTO> content;
	private int PageNumber;
	private int PageSize;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
	

}
