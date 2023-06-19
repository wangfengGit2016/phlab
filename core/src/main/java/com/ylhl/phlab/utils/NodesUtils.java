package com.ylhl.phlab.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zhengyq
 * @Date:2021/12/13 7:08
 */
public class NodesUtils {

    /**
     *
     * @param nodes 节点列表
     * @param parentName 父节点名称
     * @param nodeId 节点id名称
     * @param rootId 根节点名称
     * @return 该节点数据
     */
    public static List<JSONObject> getChildNodes(List<JSONObject> nodes,String parentName,String nodeId,String rootId){
        List<JSONObject> list = new ArrayList<>();
        for (JSONObject item : nodes) {
            if(Objects.equals(item.getString(parentName), rootId)){
                list.add(item);
            }
        }
        //防止死循环
        nodes.removeAll(list);
        for (JSONObject item : list) {
            List<JSONObject> children = getChildNodes(nodes,parentName,nodeId,item.getString(nodeId));
            if(children.size()>0){
                item.put("children",children);
            }
        }
        return list;
    }

    /**
     *
     * @param nodes 节点列表
     * @param nodeId 根节点名称
     *               缺省节点名 id
     *               缺省父节点名 parentId
     * @return
     */
    public static List<JSONObject> getChildNodes(List<JSONObject> nodes,String nodeId){
        return getChildNodes(nodes,"parentId","id",nodeId);

    }

    public static List<JSONObject> toList(List<JSONObject> nodes,String rootId,List<JSONObject> all){
        List<JSONObject> list = new ArrayList<>();
        for (JSONObject item : nodes) {
            if(Objects.equals(item.getString("parentId"), rootId)){
                list.add(item);
            }
        }
        //防止死循环
        nodes.removeAll(list);
        for (JSONObject item : list) {
            List<JSONObject> children = toList(nodes,item.getString("id"),all);
            if(children.size()>0){
                all.addAll(children);
            }
        }
        return list;
    }


}
