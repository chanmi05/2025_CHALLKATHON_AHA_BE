package com.taewoo.silenth.controller;

import com.taewoo.silenth.service.ArchivingService;
import com.taewoo.silenth.web.dto.CollectiveEntryResponse;
import com.taewoo.silenth.web.dto.commonResponse.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Collective Timeline", description = "공감연대기 API")
@RestController
@RequestMapping("/api/v1/timeline")
@RequiredArgsConstructor
public class CollectiveEntryController {

    private final ArchivingService archivingService;

    @GetMapping
    @Operation(summary = "아카이빙된 기록 조회", description = "특정 연도의 아카이빙된 기록들을 조회합니다.")
    public ResponseEntity<ApiResponse<Page<CollectiveEntryResponse>>> getArchivedPosts(
            @RequestParam int year,
            Pageable pageable
    ) {
        Page<CollectiveEntryResponse> response = archivingService.getArchivedPostsByYear(year, pageable);
        return ResponseEntity.ok(ApiResponse.onSuccessWithData(response));
    }
}
