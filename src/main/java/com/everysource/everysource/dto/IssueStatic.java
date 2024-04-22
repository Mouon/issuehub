package com.everysource.everysource.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IssueStatic {
    private String owner;
    private String repo;
    private int count;



}
