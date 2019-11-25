package com.cn.wanxi.dao.spuAndSku.impl;

import com.cn.wanxi.dao.spuAndSku.ISpuDao;
import com.cn.wanxi.entity.spuAndSku.WxTabSpu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 【商品信息管理】
 * 描述：SPU ：Standard Product Unit （标准产品单位）
 *      SKU：stock keeping unit(库存量单位)
 *
 * 数据表： wx_tab_spu 表--标准产品单元
 *          wx_tab_sku 表--库存量单元
 *          wx_tab_category_brand表--商品分类与品牌对应关系
 *
 * 2019/11/18,Create by yaodan
 */
@Repository
public class SpuDaoImpl implements ISpuDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insert(WxTabSpu wxTabSpu) {
        String exeSQL = "INSERT INTO wx_tab_spu(sn,name,caption,brand_id,category1_id,category2_id,category3_id,template_id,freight_id," +
        "image,images,sale_service,introduction,spec_items,para_items,sale_num,comment_num,is_marketable,is_enable_pec,is_delete,status) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object args[] = {wxTabSpu.getSn(),wxTabSpu.getName(),wxTabSpu.getCaption(),wxTabSpu.getBrandId(),wxTabSpu.getCategory1Id(),wxTabSpu.getCategory2Id(),wxTabSpu.getCategory3Id(),
        wxTabSpu.getTemplateId(),wxTabSpu.getFreightId(),wxTabSpu.getImage(),wxTabSpu.getImages(),wxTabSpu.getSaleService(),wxTabSpu.getIntroduction(),wxTabSpu.getSpecItems(),wxTabSpu.getParaItems(),wxTabSpu.getSaleNum(),
        wxTabSpu.getCommentNum(),wxTabSpu.getIsMarkeTable(),wxTabSpu.getIsEnablePec(),wxTabSpu.getIsDelete(),wxTabSpu.getStatus()};
        int temp = jdbcTemplate.update(exeSQL, args);
        return temp;
    }

    @Override
    public List<Map<String, Object>> queryAll() {
        String exeSQL = "select id, sn, name, caption, brand_id as brandId, category1_id as category1Id, category2_id as category2Id, category3_id as category3Id, template_id as templateId, freight_id as freightId, image, images, sale_service as saleService, introduction, spec_items as specItems, para_items as paraItms, sale_num as saleNum, comment_num as commentNum, is_marketable as isMakeTable, is_enable_pec as isEnablePec, is_delete as isDelete, status from wx_tab_spu";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(exeSQL);
        return list;
    }


    @Override
    public WxTabSpu findById(int id) {

        WxTabSpu wxTabSpu = null;
        String exeSQL = "select id, sn, name, caption, brand_id as brandId, category1_id as category1Id, category2_id as category2Id, category3_id as category3Id, template_id as templateId, freight_id as freightId, image, images, sale_service as saleService, introduction, spec_items as specItems, para_items as paraItms, sale_num as saleNum, comment_num as commentNum, is_marketable as isMakeTable, is_enable_pec as isEnablePec, is_delete as isDelete, status from wx_tab_spu where id=?";
        List<WxTabSpu> wxTabSpuu = jdbcTemplate.query(exeSQL, new Object[]{id}, new BeanPropertyRowMapper<WxTabSpu>(WxTabSpu.class));
        if (null != wxTabSpuu && wxTabSpuu.size() > 0) {
            wxTabSpu = wxTabSpuu.get(0);
        }
        return wxTabSpu;
    }

    @Override
    public List<Map<String, Object>> daishenheliebiao() {
        String exeSQL = "select id, sn, name, caption, brand_id as brandId, category1_id as category1Id, category2_id as category2Id, category3_id as category3Id, template_id as templateId, freight_id as freightId, image, images, sale_service as saleService, introduction, spec_items as specItems, para_items as paraItms, sale_num as saleNum, comment_num as commentNum, is_marketable as isMakeTable, is_enable_pec as isEnablePec, is_delete as isDelete, status from wx_tab_spu where status='0'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(exeSQL);
        return list;
    }

    @Override
    public WxTabSpu findByName(String name) {
        WxTabSpu wxTabSpu = null;
        String exeSQL = "select id, sn, name, caption, brand_id as brandId, category1_id as category1Id, category2_id as category2Id, category3_id as category3Id, template_id as templateId, freight_id as freightId, image, images, sale_service as saleService, introduction, spec_items as specItems, para_items as paraItms, sale_num as saleNum, comment_num as commentNum, is_marketable as isMakeTable, is_enable_pec as isEnablePec, is_delete as isDelete, status from wx_tab_spu where name=?";
        List<WxTabSpu> wxTabSpuu = jdbcTemplate.query(exeSQL, new Object[]{name}, new BeanPropertyRowMapper<WxTabSpu>(WxTabSpu.class));
        if (null != wxTabSpuu && wxTabSpuu.size() > 0) {
            wxTabSpu = wxTabSpuu.get(0);
        }
        return wxTabSpu;
    }

    @Override
    public int update(WxTabSpu wxTabSpu) {
        String exeSQL = "update wx_tab_spu set sn=?,name=?,caption=?,brand_id=?,category1_id=?,category2_id=?,category3_id=?,template_id=?,freight_id=?,image=?,images=?,sale_service=?,introduction=?,spec_items=?,para_items=?,sale_num=?,comment_num=?,is_marketable=?,is_enable_pec=?,is_delete=?,status=? WHERE id=?";
        Object args[] = {wxTabSpu.getSn(),wxTabSpu.getName(),wxTabSpu.getCaption(),wxTabSpu.getBrandId(),wxTabSpu.getCategory1Id(),wxTabSpu.getCategory2Id(),wxTabSpu.getCategory3Id(),
        wxTabSpu.getTemplateId(),wxTabSpu.getFreightId(),wxTabSpu.getImage(),wxTabSpu.getImages(),wxTabSpu.getSaleService(),wxTabSpu.getIntroduction(),wxTabSpu.getSpecItems(),wxTabSpu.getParaItems(),wxTabSpu.getSaleNum(),  wxTabSpu.getCommentNum(),wxTabSpu.getIsMarkeTable(),wxTabSpu.getIsEnablePec(),wxTabSpu.getIsDelete(),wxTabSpu.getStatus()};
        int temp = jdbcTemplate.update(exeSQL, args);
        return temp;
    }

    @Override
    public int deleteById(int id) {
        String exeSQL = "DELETE FROM wx_tab_spu WHERE id=?";
         int temp = jdbcTemplate.update(exeSQL, id);
        return temp;
    }


    @Override
    public int tijiaoshenhe(WxTabSpu wxTabSpu) {
        String exeSQL = "update wx_tab_spu set status='9' WHERE id=?";
        Object args[] = {wxTabSpu.getId()};
        int temp = jdbcTemplate.update(exeSQL, args);
        return temp;
    }

    @Override
    public int shenhechenggong(WxTabSpu wxTabSpu) {
        String exeSQL = "update wx_tab_spu set  is_marketable='1' , status='1'   WHERE id=?";
        Object args[] = {wxTabSpu.getId()};
        int temp = jdbcTemplate.update(exeSQL, args);
        return temp;
    }

    @Override
    public List<Map<String, Object>> fenye(int page, int size) {
        String exeSQL = "select id, sn, name, caption, brand_id as brandId, category1_id as category1Id, category2_id as category2Id, category3_id as category3Id, template_id as templateId, freight_id as freightId, image, images, sale_service as saleService, introduction, spec_items as specItems, para_items as paraItms, sale_num as saleNum, comment_num as commentNum, is_marketable as isMakeTable, is_enable_pec as isEnablePec, is_delete as isDelete, status from wx_tab_spu limit "+(page-1)*size+","+size;
        List<Map<String, Object>> list = jdbcTemplate.queryForList(exeSQL);
        return list;
    }

    @Override
    public int zong() {
        String exeSQL = "select count(*) from wx_tab_spu";
        int conut = jdbcTemplate.queryForObject(exeSQL, Integer.class);
        return conut;
    }
}

