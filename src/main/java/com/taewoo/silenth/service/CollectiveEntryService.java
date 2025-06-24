package com.taewoo.silenth.service;

import com.taewoo.silenth.repository.CollectiveEntryRepository;
import com.taewoo.silenth.web.dto.CollectiveEntryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CollectiveEntryService {

    private final CollectiveEntryRepository collectiveEntryRepository;

    public Page<CollectiveEntryResponse> getTimeline(int year, int month, Pageable pageable) {
        return collectiveEntryRepository
                .findByEraYearAndEraMonthOrderByOriginalCreatedAtDesc(year, month, pageable)
                .map(CollectiveEntryResponse::new);
    }
}
