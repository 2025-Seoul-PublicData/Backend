package com.example.seoulpublicdata2025backend.global.swagger.annotations.story;

import com.example.seoulpublicdata2025backend.domain.story.dto.StoryDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "스토리 상세 조회",
        description = "스토리 ID를 기반으로 상세 정보를 조회합니다."
)
@Parameters({
        @Parameter(name = "storyId", description = "조회할 스토리 ID", example = "1", required = true)
})
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "스토리 상세 조회 성공",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = StoryDetailDto.class),
                        examples = @ExampleObject(value = """
                        {
                          "storyId": 1,
                          "storyTitle": "스포츠사회적기업 한국스포츠컨설팅협회, 스포츠안전 모델 구축 '눈길'",
                          "storyTime": "2025-05-06",
                          "storyDetail": "한국스포츠컨설팅협회가 최근 사회적기업 진흥원에서...",
                          "storyLikes": 0,
                          "imageUrl": "https://www.kukinews.com/data/kuk/image/2025/05/06/kuk20250506000026.643x.0.png",
                          "source": "https://www.kukinews.com/article/view/kuk202505060018"
                        }
                        """)
                )
        ),
        @ApiResponse(responseCode = "404", description = "스토리를 찾을 수 없음")
})
public @interface GetStoryDetailDocs {
}