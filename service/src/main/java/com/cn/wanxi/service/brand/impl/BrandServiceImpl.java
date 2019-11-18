package com.cn.wanxi.service.brand.impl;

import com.cn.wanxi.dao.brand.BrandDao;
import com.cn.wanxi.entity.brand.BrandEntity;
import com.cn.wanxi.service.brand.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yaodan
 */
@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandDao brandDao;

    /**
     * 【添加品牌信息】
     *
     * @param brand
     * @return
     */
    @Override
    public int add(BrandEntity brand) {
        return brandDao.insert(brand);
    }

    /**
     * 【查询所有品牌信息】
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> findAll() {
        return brandDao.queryAll();
    }

    /**
     * 【根据id查询】
     *
     * @param id
     * @return
     */
    @Override
    public BrandEntity findById(int id) {
        return brandDao.findById(id);
    }

    /**
     * 【根据id修改】
     *
     * @param brandEntity
     * @return
     */
    @Override
    public int update(BrandEntity brandEntity) {
        int result = 0;
        //先根据id查询，当前数据是否存在
        int id = brandEntity.getId();
        BrandEntity byId = brandDao.findById(id);
        //如果查询当前数据存在，则修改
        if (byId != null) {
            int up = brandDao.update(brandEntity);
            //如果修改成功，返回true
            if (up > 0) {
                result = up;
            }
        }
        return result;
    }


    /**
     * 【根据id删除】
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(int id) {
        return brandDao.deleteById(id);
    }
}
