package dev.lottery.tms.controller;

import dev.lottery.tms.dto.request.GetCommentsRequest;
import dev.lottery.tms.dto.response.CommentsResponse;
import dev.lottery.tms.dto.response.ErrorResponse;
import dev.lottery.tms.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@Tag(name = "Comment Management", description = "APIs for managing comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    @Operation(summary = "Get comments", description = "Retrieves a paginated list of comments with optional filtering and sorting.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully", content = @Content(schema = @Schema(implementation = CommentsResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CommentsResponse getComments(@ParameterObject @ModelAttribute GetCommentsRequest request) {
        return commentService.getComments(request);
    }
}
