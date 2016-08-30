package com.autodesk.shejijia.consumer.codecorationBase.studio.fragment.entity;


import java.util.List;


public class DesignersSearchRsp implements java.io.Serializable {

    /**
     * @Fields count 设计师总数
     */
    private int count;

    /**
     * @Fields offset 偏移量
     */
    private int offset;

    /**
     * @Fields limit 每页显示总数
     */
    private int limit;

    /**
     * @Fields designerList 设计师详情
     */
    private List<DesignerRetrieveRsp> designer_list;

    /**
     * 获取总数<br>
     *
     * @return TODO返回结果描述 @return int 返回值类型 @throws
     */
    public int getCount() {
        return count;
    }

    /**
     * 设置总数<br>
     *
     * @param count TODO返回结果描述 @return void 返回值类型 @throws
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 获取偏移量<br>
     *
     * @return TODO返回结果描述 @return int 返回值类型 @throws
     */
    public int getOffset() {
        return offset;
    }

    /**
     * TODO 方法功能描述<br>
     *
     * @param offset TODO返回结果描述 @return void 返回值类型 @throws
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * 获取每页显示总数<br>
     *
     * @return TODO返回结果描述 @return int 返回值类型 @throws
     */
    public int getLimit() {
        return limit;
    }

    /**
     * 设置每页最大总量<br>
     *
     * @param limit TODO返回结果描述 @return void 返回值类型 @throws
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 获取设计师列表<br>
     *
     * @return TODO返回结果描述 @return List<DesignerRetrieveRsp> 返回值类型 @throws
     */
    public List<DesignerRetrieveRsp> getDesignerList() {
        return designer_list;
    }

    public List<DesignerRetrieveRsp> getDesigner_list() {
        return designer_list;
    }

    public void setDesigner_list(List<DesignerRetrieveRsp> designer_list) {
        this.designer_list = designer_list;
    }
}
