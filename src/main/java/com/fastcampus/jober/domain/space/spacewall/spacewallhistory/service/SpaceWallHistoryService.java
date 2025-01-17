package com.fastcampus.jober.domain.space.spacewall.spacewallhistory.service;

import com.fastcampus.jober.domain.space.component.componenthistory.domain.ComponentHistory;
import com.fastcampus.jober.domain.space.component.componenthistory.dto.ComponentHistoryRequest.ComponentHistoryRequestDTO;
import com.fastcampus.jober.domain.space.component.componenthistory.dto.ComponentHistoryResponse;
import com.fastcampus.jober.domain.space.component.componenthistory.repository.ComponentHistoryRepository;
import com.fastcampus.jober.domain.member.repository.MemberRepository;
import com.fastcampus.jober.domain.space.spacewall.spacewallhistory.repository.SpaceWallHistoryRepository;
import com.fastcampus.jober.domain.space.spacewall.spacewallhistory.domain.SpaceWallHistory;
import com.fastcampus.jober.global.security.auth.session.MemberDetails;
import com.fastcampus.jober.global.error.exception.SpaceWallBadRequestException;
import com.fastcampus.jober.global.error.exception.SpaceWallNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.fastcampus.jober.domain.space.spacewall.spacewallhistory.dto.HistoryWrapper.*;
import static com.fastcampus.jober.domain.space.spacewall.spacewallhistory.dto.SpaceWallHistoryRequest.*;
import static com.fastcampus.jober.domain.space.spacewall.spacewallhistory.dto.SpaceWallHistoryResponse.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpaceWallHistoryService {

    private final SpaceWallHistoryRepository spaceWallHistoryRepository;
    private final ComponentHistoryRepository componentHistoryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public HistoryResponseDTOWrapper addSpaceWallHistory(HistoryRequestDTOWrapper request) {

        MemberDetails memberDetails =
                (MemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentMemberId = memberDetails.getMemberId();
        SpaceWallHistoryRequestDTO spaceWallHistoryRequest = request.getSpaceWallHistoryRequestDTO();

        SpaceWallHistory spaceWallHistory = SpaceWallHistory.builder()
                .spaceWallId(spaceWallHistoryRequest.getSpaceWallId())
                .url(spaceWallHistoryRequest.getUrl())
                .title(spaceWallHistoryRequest.getTitle())
                .description(spaceWallHistoryRequest.getDescription())
                .profileImageUrl(spaceWallHistoryRequest.getProfileImageUrl())
                .backgroundImageUrl(spaceWallHistoryRequest.getBackgroundImageUrl())
                .pathIds(spaceWallHistoryRequest.getPathIds())
                .authorized(spaceWallHistoryRequest.isAuthorized())
                .sequence(spaceWallHistoryRequest.getSequence())
                .createMemberId(currentMemberId)
                .parentSpaceWallId(spaceWallHistoryRequest.getParentSpaceWallId())
                .build();

        SpaceWallHistoryResponseDTO spaceWallHistoryResponse =
                new SpaceWallHistoryResponseDTO(spaceWallHistoryRepository.save(spaceWallHistory));

        List<ComponentHistoryResponse.ComponentHistoryResponseDTO> componentHistoriesResponse = new ArrayList<>();
        for (ComponentHistoryRequestDTO componentHistoryRequest : request.getComponentHistoryRequestDTOs()) {
            ComponentHistory componentHistory = ComponentHistory.builder()
                    .templateId(componentHistoryRequest.getTemplateId())
                    .spaceWallHistoryId(spaceWallHistoryResponse.getId())
                    .thisSpaceWallId(componentHistoryRequest.getThisSpaceWallId())
                    .type(componentHistoryRequest.getType())
                    .visible(componentHistoryRequest.isVisible())
                    .title(componentHistoryRequest.getTitle())
                    .content(componentHistoryRequest.getContent())
                    .sequence(componentHistoryRequest.getSequence())
                    .parentSpaceWallId(componentHistoryRequest.getParentSpaceWallId())
                    .build();

            componentHistoriesResponse.add(
                    new ComponentHistoryResponse
                            .ComponentHistoryResponseDTO(componentHistoryRepository.save(componentHistory))
            );
        }

        return new HistoryResponseDTOWrapper(spaceWallHistoryResponse, componentHistoriesResponse);
    }
    @Transactional(readOnly = true)
    public List<SpaceWallHistoryResponseDTO> findRecentHistoryByMemberId(Long memberId) {
        List<SpaceWallHistory> histories =
                spaceWallHistoryRepository.findTop5ByCreateMemberIdOrderByCreatedAtDesc(memberId);
        List<SpaceWallHistoryResponseDTO> response = new ArrayList<>();

        for (SpaceWallHistory history : histories) {
            response.add(new SpaceWallHistoryResponseDTO(history));
        }

        return response;
    }

    @Transactional(readOnly = true)
    public SpaceWallHistoryResponseDTO findHistoryByMemberIdAndHistoryId(Long memberId, Long historyId) {
        SpaceWallHistory history = spaceWallHistoryRepository.findById(historyId)
                .orElseThrow(() -> new SpaceWallNotFoundException("히스토리를 찾을 수 없습니다."));

        if (!history.getCreateMemberId().equals(memberId)) {
            throw new SpaceWallBadRequestException("잘못된 접근입니다.");
        }

        return new SpaceWallHistoryResponseDTO(history);
    }

    @Transactional(readOnly = true)
    public boolean checkMemberIdExists(Long memberId) {
        return memberRepository.existsById(memberId);
    }

    @Transactional(readOnly = true)
    public boolean checkHistoryIdExists(Long historyId) {
        return spaceWallHistoryRepository.existsById(historyId);
    }
}