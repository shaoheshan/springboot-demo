package com.heshan.springboot.web.platform.common.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.heshan.springboot.web.myApp.modules.sys.entity.TreeJson;

/**
 * 
 * @ClassName: TreeUtil
 * @Description: 树形结构封装类
 * @author wanghe
 * @date 2016年11月12日 上午11:34:46
 *
 */
public class TreeUtil {

    public static List<TreeJson> getTree(List<TreeJson> resource, String rootId) {
        // 默认root为0
        if (rootId == null) {
            rootId = "0";
        }
        List<TreeJson> treeJson = new LinkedList<TreeJson>();
        String parentId = null;
        TreeJson tree = null;
        for (TreeJson treeNode : resource) {
            parentId = treeNode.getPid();
            if (rootId.equals(parentId)) {
                tree = new TreeJson();

                String id = treeNode.getId();
                tree.setId(id);
                tree.setPid(treeNode.getPid());
                tree.setLabel(treeNode.getLabel());
                tree.setUrl(treeNode.getUrl());
                tree.setChecked(treeNode.isChecked());
                tree.setImgUrl(treeNode.getImgUrl());
                tree.setIsLeaf(treeNode.getIsLeaf());
                // tree.setNocheck(treeNode.isNocheck());
                tree.setParent(treeNode.isParent());
                tree.setMaterial(treeNode.isMaterial());
                tree.setChildren(getChildren(id, resource));
                treeJson.add(tree);
            }
        }
        // for(TreeJson t : treeJson){
        // int noc = 0;
        // int s = t.getChildren().size();
        // int c = 0;
        // int h=0;
        // for(TreeJson j : t.getChildren()){
        // if(j.isChecked()){
        // c++;
        // }
        // if(j.isNocheck()){
        // noc++;
        // }
        // if(j.getHalfCheck()){
        // h++;
        // }
        // }
        // if(s == c){
        // t.setChecked(true);
        // }else if(c>0){
        // t.setChecked(true);
        // t.setHalfCheck(true);
        // }else if(s == noc && t.isParent() && t.getIsLeaf().equals("0")){
        // t.setNocheck(true);
        // }
        // if(h>0){
        // t.setChecked(true);
        // t.setHalfCheck(true);
        // }
        // }
        return treeJson;
    }

    private static List<TreeJson> getChildren(String parentId, List<TreeJson> resource) {
        List<TreeJson> list;
        List<TreeJson> treeJson = new ArrayList<TreeJson>();
        for (TreeJson treeNode : resource) {
            String parent = treeNode.getPid();
            if (parentId.equals(parent)) {
                TreeJson tree = new TreeJson();
                String id = treeNode.getId();
                tree.setId(id);
                tree.setPid(treeNode.getPid());
                tree.setLabel(treeNode.getLabel());
                tree.setUrl(treeNode.getUrl());
                tree.setChecked(treeNode.isChecked());
                tree.setImgUrl(treeNode.getImgUrl());
                // tree.setIconCls(tree.getIconCls());
                tree.setIsLeaf(treeNode.getIsLeaf());
                // tree.setNocheck(false);
                tree.setParent(treeNode.isParent());
                tree.setMaterial(treeNode.isMaterial());
                list = getChildren(id, resource);
                // if(list.size() == 0 && treeNode.isParent() && treeNode.getIsLeaf().equals("0")){
                // tree.setNocheck(true);
                // }
                // int size = list.size();
                // if(size > 0){
                // int noc = 0;
                // int s = list.size();
                // int c = 0;
                // int hh=0;
                // for(TreeJson j : list){
                // if(j.isChecked()){
                // c++;
                // }
                // if(j.isNocheck()){
                // noc++;
                // }
                // if(j.getHalfCheck()){
                // hh++;
                // }
                // }
                // if(s == c){
                // tree.setChecked(true);
                // }else if(c>0){
                // tree.setChecked(true);
                // tree.setHalfCheck(true);
                // }else if(s == noc && tree.isParent() && tree.getIsLeaf().equals("0")){
                // tree.setNocheck(true);
                // }
                // if(hh>0){
                // tree.setChecked(true);
                // tree.setHalfCheck(true);
                // }
                // }
                tree.setChildren(list);
                treeJson.add(tree);
            }
        }
        return treeJson;
    }

}
