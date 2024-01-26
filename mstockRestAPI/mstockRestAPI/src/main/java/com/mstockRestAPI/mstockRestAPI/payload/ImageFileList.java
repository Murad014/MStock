package com.mstockRestAPI.mstockRestAPI.payload;

import com.mstockRestAPI.mstockRestAPI.validation.ValidImageFile;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class ImageFileList {
    @ValidImageFile
    private List<MultipartFile> imageFiles;

}
