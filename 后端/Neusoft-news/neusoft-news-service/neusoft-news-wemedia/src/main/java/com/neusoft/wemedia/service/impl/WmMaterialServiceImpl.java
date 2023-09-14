package com.neusoft.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neusoft.common.exception.CustomException;
import com.neusoft.file.service.FileStorageService;
import com.neusoft.model.common.dtos.PageRequestDto;
import com.neusoft.model.common.dtos.PageResponseResult;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.common.enums.AppHttpCodeEnum;
import com.neusoft.model.wemedia.dtos.WmMaterialDto;
import com.neusoft.model.wemedia.pojos.WmMaterial;
import com.neusoft.model.wemedia.pojos.WmNews;
import com.neusoft.model.wemedia.pojos.WmNewsMaterial;
import com.neusoft.utils.thread.WmThreadLocalUtil;
import com.neusoft.wemedia.mapper.WmMaterialMapper;
import com.neusoft.wemedia.mapper.WmNewsMapper;
import com.neusoft.wemedia.mapper.WmNewsMaterialMapper;
import com.neusoft.wemedia.service.WmMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    @Autowired
    private WmNewsMapper wmNewsMapper;

    /**
     * 上传图片
     * @param multipartFile
     * @return
     */
    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {

        //1.检查参数
        if (multipartFile == null || multipartFile.getSize() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.上传图片到minIO中
        String filename = UUID.randomUUID().toString().replace("-", "");
        //aa.jpg
        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileId = null;
        try {
            fileId = fileStorageService.uploadImgFile("",filename + postfix, multipartFile.getInputStream());
            log.info("上传图片到MinIo中， filedId:{}",fileId);
        } catch (IOException e) {
            log.error("WmMaterialServiceImpl-上传文件失败");
            throw new RuntimeException(e);
        }

        //3.保存到数据库中
        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setUserId(WmThreadLocalUtil.getUser().getId());
        wmMaterial.setUrl(fileId);
        wmMaterial.setIsCollection((short)0);
        wmMaterial.setType((short)0);
        wmMaterial.setCreatedTime(new Date());
        save(wmMaterial);

        //4.返回结果
        return ResponseResult.okResult(wmMaterial);

    }

    /**
     * 查询分页
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findList(PageRequestDto dto) {
        //1.检查参数
        dto.checkParam();

        //2.分页查询
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //是否收藏
        if (dto.getIsCollection() == 1){
            lambdaQueryWrapper.eq(WmMaterial::getIsCollection, dto.getIsCollection());
        }

        //按照用户查询
        lambdaQueryWrapper.eq(WmMaterial::getUserId,WmThreadLocalUtil.getUser().getId());

        //按照时间倒序
        lambdaQueryWrapper.orderByDesc(WmMaterial::getCreatedTime);
        page = page(page,lambdaQueryWrapper);

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }

    /**
     *删除图片
     * @param id
     * @return
     */
    @Override
    public ResponseResult deletePicture(Integer id) {
        if (id == null){
            //参数失效
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        WmMaterial wmMaterial = getOne(Wrappers.<WmMaterial>lambdaQuery().eq(WmMaterial::getId, id));
        if (wmMaterial == null){
            //数据不存在
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //查询是否与文章有关联
        Integer count = wmNewsMaterialMapper.selectCount(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getMaterialId, id));
        if (count > 0){
            //文件删除失败,素材与文章有关联
            return ResponseResult.errorResult(AppHttpCodeEnum.MATERIAL_ARTICLE_RELEVANCE);
        }
        //查询是否有文章存入该图片
        List<WmNews> wmNews = wmNewsMapper.selectList(Wrappers.<WmNews>lambdaQuery().eq(WmNews::getUserId, wmMaterial.getUserId()));
        List<String> imagesList = wmNews.stream().map(WmNews::getImages).collect(Collectors.toList());
        String images = StringUtils.join(imagesList, ",");
        boolean contains = images.contains(wmMaterial.getUrl());
        if (contains){
            //文件删除失败,素材与文章有关联
            return ResponseResult.errorResult(AppHttpCodeEnum.MATERIAL_ARTICLE_RELEVANCE);
        }

        //删除数据库素材与上传到minIO中的素材图片
        boolean result = removeById(id);
        fileStorageService.delete(wmMaterial.getUrl());
        if (result){
            //操作成功
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
        }else {
            //文件删除失败
            throw new CustomException(AppHttpCodeEnum.FILE_DELETION_FAILURE);
        }
    }

    /**
     * 素材是否收藏
     * @param type
     * @param id
     * @return
     */
    @Override
    public ResponseResult updateCollect(Integer id, Short type) {
        //判断id是否为空
        if (id == null) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        WmMaterial wmMaterial = getById(id);
        if (wmMaterial == null) return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        boolean result = update(Wrappers.<WmMaterial>lambdaUpdate().
                eq(WmMaterial::getId, id).set(WmMaterial::getIsCollection, type));
        if (result){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
        }
    }


}
