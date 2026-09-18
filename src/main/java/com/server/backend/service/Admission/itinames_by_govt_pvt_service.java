package com.server.backend.service.Admission;

import java.util.List;

import com.server.backend.entity.Iti;

public interface itinames_by_govt_pvt_service {

    List<Iti> getItiNamesByGovt(String govt);

}