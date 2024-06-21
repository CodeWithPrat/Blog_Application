package com.blog.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private Integer categoryId;
    
    @NotBlank
    @Size(min = 4, message = "min size of the category title is 4 ")
    @JsonProperty("title")
    private String categoryTitle;
    
    @NotBlank
    @Size(min = 10, message = "min size of the category description is 10 ")
    @JsonProperty("description")
    private String categoryDescription;
}
