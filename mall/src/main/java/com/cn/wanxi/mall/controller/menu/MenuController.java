package com.cn.wanxi.mall.controller.menu;

import com.cn.wanxi.entity.brand.ByPage;
import com.cn.wanxi.entity.brand.PageList;
import com.cn.wanxi.entity.menu.MenuEntity;
import com.cn.wanxi.service.menu.IMenuService;
import com.cn.wanxi.utils.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 【菜单管理】 菜单为三级菜单
 * 数据表： wx_tab_menu （菜单表）
 *
 * 2019/11/18,Create by zhoushiling
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService iMenuService;

    /**
     * 【添加菜品信息】
     *
     * @return
     */
    @PostMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public Msg add(@RequestBody MenuEntity menuEntity) {
        Msg msg = null;
        if (null != menuEntity.getName() && menuEntity.getName().trim() != "") {
            int result = iMenuService.add(menuEntity);
            if (0 != result) {
                msg = Msg.success().messageData(menuEntity);
            }
        } else {
            msg = Msg.fail().messageData("名字不能为空");
        }
        return msg;
    }

    /**
     * 【查询所有菜品信息】
     *
     * @return
     */

    @PostMapping("/findAll")
    public Msg findAll(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Msg msg = null;
        List<Map<String, Object>> list = iMenuService.findAll();
        //判断集合是否有数据，如果没有数据返回失败
        if (list.isEmpty()) {
            msg = Msg.fail().messageData("数据库没有数据");
        } else {
            msg = Msg.success().messageData(list);
        }
        return msg;
    }
    /**
     * 【通过id查询菜品信息】
     *
     * @return
     */


    @RequestMapping(value = "/findById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Msg findById(@RequestBody Map<String, Integer> param) {
        Msg msg = null;
        int id = param.get("id");
        MenuEntity byId = iMenuService.findById(id);
        if (byId != null) {
            msg = Msg.success().messageData(byId);
        } else {
            msg = Msg.fail();
        }
        return msg;
    }

    /**
     * 【通过name查询菜品信息】
     *
     * @return
     */
    @RequestMapping(value = "/findAuthMenu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Msg findByName(@RequestBody Map<String,String> param) {
        Msg msg = null;
        String name = param.get("name");
        MenuEntity byName = iMenuService.findByName(name);
        if (byName != null) {
            msg = Msg.success().messageData(byName);
        } else {
            msg = Msg.fail();
        }
        return msg;
    }

    /**
     * 【修改菜品信息】
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Msg updateInfo(@RequestBody MenuEntity menuEntity) {
        Msg msg = null;
        int up = iMenuService.update(menuEntity);
        if (up > 0) {
            msg = Msg.success().messageData(menuEntity);
        } else {
            msg = Msg.fail();
        }
        return msg;
    }

    /**
     * 【删除菜品信息】
     *
     * @return
     */
    @PostMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public Msg deleteById(@RequestBody Map<String, Integer> param,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Msg msg = null;
        int id = param.get("id");
        int i = iMenuService.deleteById(id);
        if (i > 0) {
            msg = Msg.success();
        } else {
            msg = Msg.fail();
        }
        return msg;
    }

    /**
     * 【获取条件查询菜品信息】
     *
     * @return
     */
    @RequestMapping(value = "/findCondPage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Msg findByConditionPage(@RequestBody ByPage byPage) {
        Msg msg;
        int page = byPage.getPage();
        int size = byPage.getSize();
        MenuEntity menuEntity = new MenuEntity();
//        Object icon = byPage.getSearchMap().get("icon");
//        if (icon != null) {
//            menuEntity.setIcon(icon.toString());
//        }
//        Object url = byPage.getSearchMap().get("url");
//        if (url != null) {
//            menuEntity.setUrl(url.toString());
//        }

        //实例化 分页实体类
        PageList pageList = new PageList();
        //根据页数，每页记录数查询
        List<Map<String, Object>> list = iMenuService.findListAndPage(menuEntity, page, size);
        //把查询出来的对象封装在分页实体类中
        pageList.setList(list);
        //统计所有数据的总行数
        int TotalRows = iMenuService.countAll();
        //把页数封装在分页实体类中
        pageList.setPage(page);
        pageList.setTotal(list.size());
        //查询出来的总行数封装在分页实体类中
        pageList.setTotalRows(TotalRows);
        if (list.isEmpty()) {
            msg = Msg.fail().messageData("菜单信息不存在");
        } else {
            msg = getPages(size, pageList, TotalRows);
        }
        return msg;
    }

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
