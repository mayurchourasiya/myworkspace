package com.sangria.profile.service;

import com.sangria.profile.bean.Resume;
import com.sangria.profile.dao.ResumeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResumeService {

    @Autowired
    private ResumeDao resumeDao;

    public Integer createResume(Resume resume) {
        int profileID = resumeDao.createProfile(resume.getProfile());
        return resumeDao.createResume(resume,profileID);
    }

}
