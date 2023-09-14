package com.neusoft.wemedia.controller.v1;


import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.wemedia.dtos.ChannelSaveDto;
import com.neusoft.model.wemedia.dtos.WmChannelDto;
import com.neusoft.model.wemedia.pojos.WmChannel;
import com.neusoft.wemedia.service.WmChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/channel")
public class WmchannelController {

    @Autowired
    private WmChannelService channelService;

    /**
     * 查看频道列表
     * @return
     */
    @GetMapping("/channels")
    public ResponseResult findAll(){
        return channelService.findAll();
    }

    /**
     * 删除频道
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public ResponseResult deleteById(@PathVariable Integer id){
        return channelService.deleteById(id);
    }

    /**
     * 根据名字模糊查询频道
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody WmChannelDto dto){
        return channelService.findList(dto);
    }

    /**
     * 新增频道
     * @param dto
     * @return
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody ChannelSaveDto dto){
        return channelService.saveChannel(dto);
    }

    /**
     * 修改频道
     * @param dto
     * @return
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody ChannelSaveDto dto){
        return channelService.updateChannel(dto);
    }

}
