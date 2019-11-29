package com.cn.wanxi.mall.controller.brand;

import com.cn.wanxi.entity.brand.BrandEntity;
import com.cn.wanxi.entity.brand.ByPage;
import com.cn.wanxi.entity.brand.PageList;
import com.cn.wanxi.service.brand.IBrandService;
import com.cn.wanxi.utils.utils.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * 【品牌管理模块】
 * <p>
 * 数据表： wx_tab_brand	品牌表
 *
 * @author 2019/11/16,Create by yaodan
 */

/**
 * @CrossOrigin 解决跨域问题
 */
@CrossOrigin
@Api(value = "品牌管理模块的接口")
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private IBrandService iBrandService;

    @Value("${spring.resources.static-locations}")
    private String path;

    @Value("${spring.mvc.static-path-pattern}")
    private String imageFileName;

    /**
     * 【添加品牌信息】
     *
     * @return
     */
    @PostMapping(value = "/add")
    public Msg add(String letter, String name, Integer seq, MultipartFile imageFile) {
        BrandEntity brandEntity = getBrandEntity(letter, name, seq, imageFile);
        Msg msg;
        if ((null != name && name.trim() != "") && (null != seq)) {
            msg = iBrandService.add(brandEntity, path, imageFileName);
            String image = brandEntity.getImage();
            if (0 == msg.getCode()) {
                if (image == null) {
                    msg = Msg.success().messageData(brandEntity);
                } else {

                    msg = Msg.success().messageData(image);
                }
            } else {
                return msg;
            }
        } else {
            msg = Msg.fail().messageData("名字和seq不能为空,图片可传可不传");
        }
        return msg;
    }


    /**
     * 【展示所有品牌信息】
     *
     * @return
     */
    @PostMapping("/findAll")
    public Msg findAll() {
        Msg msg;
        List<Map<String, Object>> list = iBrandService.findAll();
        //判断集合是否有数据，如果没有数据返回失败
        if (list.isEmpty()) {
            msg = Msg.fail().messageData("数据库中没有数据");
        } else {
            msg = Msg.success().messageData(list);
        }
        return msg;
    }

    /**
     * 【根据品牌id查询信息】
     * 前端传的json数据可以用 map或者对象接收
     * 该方法用map接收json对象数据
     *
     * @param
     * @return
     * @RequestParam(required = true) int id  提示必须输入id
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/findById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Msg findById(@RequestBody Map<String, Integer> param) {
        Msg msg = null;
        int id = param.get("id");
        if (!StringUtils.isEmpty(id) && id > 0) {
            BrandEntity byId = iBrandService.findById(id);
            //判断是否有返回的数据
            if (!ObjectUtils.isEmpty(byId)) {
                msg = Msg.success().messageData(byId);
            } else {
                msg = Msg.fail().messageData("该品牌不存在");
            }
        }
        return msg;
    }

    /**
     * 【修改品牌信息】根据id查询
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Msg update(Integer id, String letter, String name, Integer seq, MultipartFile imageFile) {
        Msg msg = null;
        if (id > 0) {
            BrandEntity brandEntity = getBrandEntity(letter, name, seq, imageFile);
            brandEntity.setId(id);
            //根据id查询数据
            BrandEntity byId = iBrandService.findById(id);
            //判断是否查询到该品牌信息
            if (!ObjectUtils.isEmpty(byId)) {
                Msg update = iBrandService.update(brandEntity, path, imageFileName);
                if (update.getCode() == 0) {
                    msg = Msg.success().messageData(brandEntity.getImage());
                }
            } else {
                msg = Msg.fail().messageData("该品牌不存在");
            }
        } else {
            msg = Msg.fail().messageData("请输入id");
        }
        return msg;
    }

    /**
     * 封装实体类 实体类接收前台的数据
     *
     * @param letter
     * @param name
     * @param seq
     * @param
     * @return
     */

    private BrandEntity getBrandEntity(String letter, String name, Integer seq, MultipartFile imageFlle) {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName(name);
        brandEntity.setLetter(letter);
        brandEntity.setImageFile(imageFlle);
        brandEntity.setSeq(seq);
        return brandEntity;
    }

    /**
     * 【根据id删除】
     * 前端传的json数据可以用 map或者对象接收
     * 该方法用对象接收json对象数据
     *
     * @param
     * @return
     * @RequestParam(required = true) int id  提示必须输入id
     */
    @PostMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据id删除数据")
    public Msg deleteById(@RequestBody BrandEntity brandEntity) {
        Msg msg = null;
        int id = brandEntity.getId();
        if (id > 0) {
            int i = iBrandService.deleteById(id);
            if (i > 0) {
                msg = Msg.success().messageData("删除成功");
            } else {
                msg = Msg.fail().messageData("删除失败,该用户不存在");
            }
        } else {
            msg = Msg.fail().messageData("请输入id");
        }
        return msg;
    }

    /**
     * 【条件查询】
     *
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Msg findList(@RequestBody BrandEntity brandEntity) {
        Msg msg;
        List<Map<String, Object>> list = iBrandService.findList(brandEntity);
        if (list.isEmpty()) {
            msg = Msg.fail().messageData("该品牌不存在");
        } else {
            msg = Msg.success().messageData(list);
        }
        return msg;
    }

    /**
     * 【分页查询】
     *
     * @param param page 当前页码 size 当前页记录数
     * @return
     */
    @RequestMapping(value = "/findPage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Msg findAllbyPager(@RequestBody Map<String, Integer> param) {
        Msg msg;
        //实例化 分页实体类
        int page = param.get("page");
        int size = param.get("size");
        PageList pageList = new PageList();

        //处理int类型的变量为空时
        if (page == 0) {
            page = 1;
        }
        if (size == 0) {
            page = 30;
        }
        //根据页数，每页记录数查询
        List<Map<String, Object>> list = iBrandService.findAllbyPage(page, size);
        //把查询出来的对象封装在分页实体类中
        pageList.setList(list);
        if (null == list && list.isEmpty()) {
            msg = Msg.fail().messageData("品牌信息不存在");
        } else {

            //统计所有数据的总行数
            int TotalRows = iBrandService.countAll();

            //把页数封装在分页实体类中
            pageList.setPage(page);
            pageList.setTotal(list.size());
            //查询出来的总行数封装在分页实体类中
            pageList.setTotalRows(TotalRows);
            msg = getPages(size, pageList, TotalRows);
        }
        return msg;
    }

    /**
     * 【根据条件分页查询】
     *
     * @return
     */
    @RequestMapping(value = "/findPageCon", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Msg findByConditionPage(@RequestBody ByPage byPage) {
        Msg msg;
        int page = byPage.getPage();
        int size = byPage.getSize();
        BrandEntity brandEntity = new BrandEntity();
        Object name = byPage.getSearchMap().get("name");
        if (name != null) {
            brandEntity.setName(name.toString());
        }
        Object letter = byPage.getSearchMap().get("letter");
        if (letter != null) {
            brandEntity.setLetter(letter.toString());
        }

        //实例化 分页实体类
        PageList pageList = new PageList();
        //根据页数，每页记录数查询
        List<Map<String, Object>> list = iBrandService.findListAndPage(brandEntity, page, size);
        //把查询出来的对象封装在分页实体类中
        pageList.setList(list);
        //统计所有数据的总行数
        int TotalRows = iBrandService.countAll();
        //把页数封装在分页实体类中
        pageList.setPage(page);
        pageList.setTotal(list.size());
        //查询出来的总行数封装在分页实体类中
        pageList.setTotalRows(TotalRows);
        if (list.isEmpty()) {
            msg = Msg.fail().messageData("品牌信息不存在");
        } else {
            msg = getPages(size, pageList, TotalRows);
        }
        return msg;
    }

    /**
     * 提取公共方法
     *
     * @param size
     * @param pageList
     * @param totalRows 总记录数
     * @return
     */
    private Msg getPages(int size, PageList pageList, int totalRows) {
        Msg msg;
        int pages = 0;
        if (totalRows % size == 0) {
            pages = totalRows / size;
        } else {
            pages = totalRows / size + 1;
        }
        System.out.println("目前分页的总页数是" + pages);
        //总页数
        pageList.setPages(pages);
        msg = Msg.success().messageData(pageList);
        return msg;
    }
}
