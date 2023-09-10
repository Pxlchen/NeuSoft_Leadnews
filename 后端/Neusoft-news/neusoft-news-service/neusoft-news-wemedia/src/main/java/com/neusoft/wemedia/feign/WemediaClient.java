package com.neusoft.wemedia.feign;

import com.neusoft.apis.wemedia.IWemediaClient;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.wemedia.service.WmChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WemediaClient implements IWemediaClient {

    @Autowired
    private WmChannelService wmChannelService;


    /**
     * @return
     */
    @GetMapping("/api/v1/channel/list")
    @Override
    public ResponseResult getChannels() {
        return wmChannelService.findAll();
    }
}
