package com.hefu.zookeeper.controller;

import com.hefu.common.entity.Result;
import com.hefu.common.utils.StringUtils;
import com.hefu.zookeeper.service.ZookeeperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * NOTE:这里测试由于不清楚怎么从swaggere传递路径过来，于是在后端自己拼凑。实际可以和前端沟通
 *
 *
 * @author Administrator
 */
@Api(tags = "Zookeeper数据管理")
@RestController
@Slf4j
@RequestMapping("zookeeper")
public class ZookeeperController {

    @Autowired
    private ZookeeperService zookeeperService ;

    @ApiOperation(value="查询节点数据")
    @GetMapping("/{path}")
    public Result getNodeData (@PathVariable String path) {
        path = assemblePath(path);
        if(!zookeeperService.isExistNode(path)){
            return Result.error("该节点不存在");
        }
        return Result.success((Object) zookeeperService.getNodeData(path));
    }

    @ApiOperation(value="判断节点是否存在")
    @GetMapping("/isExistNode/{path}")
    public Result isExistNode (@PathVariable String path){
        path = assemblePath(path);
        if(zookeeperService.isExistNode(path)){
            return Result.success(true);
        }
        return Result.success(false);
    }

    @ApiOperation(value="创建节点")
    @PostMapping("/{path}")
    public Result createNode (@RequestBody CreateMode mode, @PathVariable String path ){
        path = assemblePath(path);
        boolean node = zookeeperService.createNode(mode, path);
        if(node){
            return Result.success("创建节点成功");
        }
        return Result.error("创建节点失败");
    }

    @ApiOperation(value="设置节点数据")
    @PutMapping("/{path}/{nodeData}")
    public Result setNodeData (@PathVariable String path, @PathVariable String nodeData) {
        path = assemblePath(path);
        boolean b = zookeeperService.setNodeData(path, nodeData);
        if(b){
            return Result.error("设置节点数据成功");
        }
        return Result.success("设置节点数据失败");
    }

    @ApiOperation(value="创建并设置节点数据")
    @PostMapping("/{path}/{nodeData}")
    public Result createNodeAndData (@RequestBody CreateMode mode, @PathVariable String path , @PathVariable String nodeData){
        path = assemblePath(path);
        boolean nodeAndData = zookeeperService.createNodeAndData(mode, path, nodeData);
        if(nodeAndData){
            return Result.error("创建并设置节点数据成功");
        }
        return Result.success("创建并设置节点数据失败");
    }

    @ApiOperation(value="递归获取节点数据")
    @GetMapping("/getNodeChild/{path}")
    public Result getNodeChild (@PathVariable String path) {
        path = assemblePath(path);
        List<String> nodeChild = zookeeperService.getNodeChild(path);
        return Result.success(nodeChild);
    }

    @ApiOperation(value="是否递归删除节点")
    @DeleteMapping("/{path}")
    public Result deleteNode (@PathVariable String path, @RequestParam Boolean recursive) {
        path = assemblePath(path);
        boolean b = zookeeperService.deleteNode(path, recursive);
        if(b){
            return Result.error("是否递归删除节点成功");
        }
        return Result.success("是否递归删除节点失败");
    }

    /**
     * 将参数组装成路径形式
     * @param path
     * @return
     */
    public String assemblePath(String path){
        if(StringUtils.isNotBlank(path)){
            path = "/" + path;
            path = path.replaceAll("_", "/");
        }
        return path;
    }
}
