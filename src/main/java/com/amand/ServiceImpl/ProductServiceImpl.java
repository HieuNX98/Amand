package com.amand.ServiceImpl;

import com.amand.Utils.FileUtils;
import com.amand.constant.SystemConstant;
import com.amand.converter.ProductConverter;
import com.amand.dto.ProductDto;
import com.amand.entity.CategoryEntity;
import com.amand.entity.ColorEntity;
import com.amand.entity.ProductEntity;
import com.amand.entity.SizeEntity;
import com.amand.form.ProductForm;
import com.amand.repository.CategoryRepository;
import com.amand.repository.ColorRepository;
import com.amand.repository.ProductRepository;
import com.amand.repository.SizeRepository;
import com.amand.service.IProductService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductDto save(ProductForm productForm)  {
        ProductEntity productEntity;
        if (productForm.getId() != null) {
            Optional<ProductEntity> oldProDuctEntity = productRepository.findById(productForm.getId());
            productEntity = productConverter.toEntity(oldProDuctEntity.get(), productForm);
            String image = "";

             if (productForm.getImage1() != null && !productForm.getImage1().isEmpty()) {
                image = fileUtils.uploadFile(productForm.getImage1());
                productEntity.setImage1(image);
            }

            if (productForm.getImage2() != null && !productForm.getImage2().isEmpty()) {
                image = fileUtils.uploadFile(productForm.getImage2());
                productEntity.setImage2(image);
            }

            if (productForm.getImage3() !=null && !productForm.getImage3().isEmpty()) {
                image = fileUtils.uploadFile(productForm.getImage3());
                productEntity.setImage3(image);
            }

            if (productForm.getImage4() != null && !productForm.getImage4().isEmpty()) {
                image = fileUtils.uploadFile(productForm.getImage4());
                productEntity.setImage4(image);
            }

        } else {
            productEntity = productConverter.toEntity(productForm);
            String image = fileUtils.uploadFile(productForm.getImage1());
            productEntity.setImage1(image);
            image = fileUtils.uploadFile(productForm.getImage2());
            productEntity.setImage2(image);
            image = fileUtils.uploadFile(productForm.getImage3());
            productEntity.setImage3(image);
            image = fileUtils.uploadFile(productForm.getImage4());
            productEntity.setImage4(image);
            productEntity.setStatus(SystemConstant.ACTIVE_STATUS);
        }
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(productForm.getCategoryId());
        productEntity.setCategory(categoryEntity.get());
        productEntity.setColors(colorRepository.findAllByIds(productForm.getColorIds()));
        productEntity.setSizes(sizeRepository.findAllByIds(productForm.getSizeIds()));
        productEntity = productRepository.save(productEntity);
        return productConverter.toDto(productEntity);
    }

    @Override
    public Map<String, String> validate(ProductForm productForm, boolean isRegister) {
        Map<String, String> results = new HashMap<>();
        if (productForm.getCategoryId() == null) {
            results.put("MessageCategory", "Bạn không được để trống thông tin thể loại sản phẩm");
        }
        if (Strings.isNotBlank(productForm.getName())) {
            boolean isExistName = StringUtils.hasLength(productRepository.findOneNameByName(productForm.getName()));
            boolean isItsName = productForm.getName().equalsIgnoreCase(productRepository.findOneNameById(productForm.getId()));
            if (isRegister && isExistName) {
                results.put("MessageName", "Tên sản phẩm đã tồn tại");
            }

            if (!isRegister && isExistName && !isItsName) {
                results.put("MessageName", "Tên sản phẩm đã tồn tại");
            }
        } else {
            results.put("MessageName", "Bạn không được để trống thông tin tên sản phẩm");
        }

        if (productForm.getOldPrice() == null) {
            results.put("MessageOldPrice", "Bạn không được để trống thông tin giá sản phẩm");
        }

        if (productForm.getAmount() == null) {
            results.put("MessageAmount", "Bạn không được để trống thông tin số lượng sản phẩm");
        }

        if (Strings.isBlank(productForm.getSeason())) {
            results.put("MessageSeason", "Bạn không được để trống thông tin mùa sản phẩm");
        }

        if (CollectionUtils.isEmpty(productForm.getColorIds())) {
            results.put("MessageColor", "Bạn không được để trống thông tin màu sản phẩm");
        }

        if (CollectionUtils.isEmpty(productForm.getSizeIds())) {
            results.put("MessageSize", "Bạn không được để trống thông tin kích thước sản phẩm");
        }

        String errorImage1 = getErrorImage(productForm.getImage1(), isRegister);
        if (Strings.isNotBlank(errorImage1)) {
            results.put("MessageImage1", errorImage1);
        }

        String errorImage2 = getErrorImage(productForm.getImage2(), isRegister);
        if (Strings.isNotBlank(errorImage2)) {
            results.put("MessageImage2", errorImage2);
        }

        String errorImage3 = getErrorImage(productForm.getImage3(), isRegister);
        if (Strings.isNotBlank(errorImage3)) {
            results.put("MessageImage3", errorImage3);
        }

        String errorImage4 = getErrorImage(productForm.getImage4(), isRegister);
        if (Strings.isNotBlank(errorImage4)) {
            results.put("MessageImage4", errorImage4);
        }
        return results;
    }

    @Override
    public List<ProductDto> findAll(Pageable pageable, Integer status) {
        List<ProductDto> productDtos = new ArrayList<>();
        List<ProductEntity> productEntities = productRepository.findAllByStatus(pageable, status);
        for (ProductEntity productEntity : productEntities) {
            ProductDto productDto = productConverter.toDto(productEntity);
            Optional<CategoryEntity> categoryEntity = categoryRepository.findById(productEntity.getCategory().getId());
            productDto.setCategoryName(categoryEntity.get().getName());
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Override
    public int getTotalItem(Integer status) {
        return productRepository.countByStatus(status);
    }

    @Override
    public int getTotalItemBySearch(String name, String season, Integer categoryId, Boolean salePrice) {
        return productRepository.countBySearch(name, season, categoryId, salePrice);
    }

    @Override
    public ProductDto findOneById(Integer id) {
        ProductEntity productEntity = productRepository.findOneByIdAndStatus(id);
        ProductDto productDto =  productConverter.toDto(productEntity);
        List<ColorEntity> colorEntities = productEntity.getColors();
        List<Integer> colorIds = new ArrayList<>();
        for (ColorEntity colorEntity : colorEntities) {
            int colorId = colorEntity.getId();
            colorIds.add(colorId);
        }
        productDto.setColorIds(colorIds);

        List<SizeEntity> sizeEntities = productEntity.getSizes();
        List<Integer> sizeIds = new ArrayList<>();
        for (SizeEntity sizeEntity : sizeEntities) {
            int sizeId = sizeEntity.getId();
            sizeIds.add(sizeId);
        }
        productDto.setSizeIds(sizeIds);

        CategoryEntity categoryEntity = productEntity.getCategory();
        productDto.setCategoryName(categoryEntity.getName());
        productDto.setCategoryId(categoryEntity.getId());
        return productDto;
    }

    @Override
    @Transactional
    public void updateStatus(List<Integer> ids, Integer status) {
        productRepository.updateStatusByIds(ids, status);

    }

    @Override
    @Transactional
    public void deleteProduct(List<Integer> ids) {
        List<ProductEntity> productEntities = productRepository.findAllByIds(ids);
        for (ProductEntity product : productEntities) {
            product.setSizes(Collections.emptyList());
            product.setColors(Collections.emptyList());
        }
        productRepository.deleteAllById(ids);
    }

    @Override
    public ProductDto outstandingProduct(Integer status, Integer limit) {
        ProductEntity productEntity = productRepository.findProductByRecentlyCreatedAndStatus(status, limit);
        return productConverter.toDto(productEntity);
    }

    @Override
    public List<ProductDto> search(Pageable pageable, String name, String season, Integer categoryId, Boolean salePrice) {
        List<ProductDto> productDtos = new ArrayList<>();
        List<ProductEntity> productEntities = productRepository.findAllByProductNameAndSeasonAndCategory(
                name,
                season,
                categoryId,
                salePrice,
                pageable);
        for (ProductEntity productEntity : productEntities) {
            ProductDto productDto = productConverter.toDto(productEntity);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    private String getErrorImage(MultipartFile image, boolean isRegister) {
        if (!isRegister && (image == null || image.isEmpty())) {
            return "";
        }
        if (isRegister && (image == null || image.isEmpty())) {
            return "Bạn không được để trống thông tin ảnh sản phẩm";
        }
        String fileName = image.getOriginalFilename() == null ? "" : image.getOriginalFilename().toLowerCase(Locale.ROOT);
        if (!fileName.endsWith(".png") && !fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg")) {
            return "Vui lòng upfile thuộc các định dạng sau 'png', 'jpg', 'jpeg'";
        }
        if (image.getSize() > SystemConstant.IMAGE_MAX_SIZE) {
            return "Vui lòng upfile nhỏ hơn 6MB";
        }
        return "";
    }

}
