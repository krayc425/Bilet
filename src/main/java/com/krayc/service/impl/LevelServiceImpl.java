package com.krayc.service.impl;

import com.krayc.model.LevelEntity;
import com.krayc.repository.LevelRepository;
import com.krayc.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelServiceImpl implements LevelService {

    @Autowired
    private LevelRepository levelRepository;

    public LevelEntity findLevelEntityWithPoint(int point) {
        List<LevelEntity> entityList = levelRepository.findAll();
        for (int i = 0; i < entityList.size() - 1; i++) {
            if (point >= entityList.get(i).getMinimumPoint() && point < entityList.get(i + 1).getMinimumPoint()) {
                return entityList.get(i);
            }
        }
        return entityList.get(entityList.size() - 1);
    }

}
