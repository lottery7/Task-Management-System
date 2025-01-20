package dev.lottery.tms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetCommentsRequest {

    @Schema(
            description = "Page number (zero-based)",
            example = "0",
            defaultValue = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer page;

    @Schema(
            description = "Number of items per page",
            example = "10",
            defaultValue = "10",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer size;

    @Schema(
            description = "Sorting field.",
            example = "id",
            defaultValue = "id",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String sortBy;

    @Schema(
            description = "Filter by task ID (optional)",
            example = "123",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Long taskId;

    @Schema(
            description = "Filter by author ID (optional)",
            example = "456",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Long authorId;

    // Геттеры и сеттеры
}
