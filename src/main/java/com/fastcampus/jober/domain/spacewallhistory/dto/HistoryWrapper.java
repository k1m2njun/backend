package com.fastcampus.jober.domain.spacewallhistory.dto;

import com.fastcampus.jober.domain.componenthistory.dto.ComponentHistoryRequest;
import com.fastcampus.jober.domain.componenthistory.dto.ComponentHistoryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class HistoryWrapper {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HistoryRequestDTOWrapper {

        SpaceWallHistoryRequest.SpaceWallHistoryRequestDTO spaceWallHistoryRequestDTO;
        List<ComponentHistoryRequest.ComponentHistoryRequestDTO> componentHistoryRequestDTOs;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HistoryResponseDTOWrapper {

        SpaceWallHistoryResponse.SpaceWallHistoryResponseDTO spaceWallHistoryResponseDTO;
        List<ComponentHistoryResponse.ComponentHistoryResponseDTO> componentHistoryResponseDTOs;
    }

}