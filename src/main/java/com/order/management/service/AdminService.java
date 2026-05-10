package com.order.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.management.DTO.AddProductDTO;
import com.order.management.DTO.ProductResponseDTO;
import com.order.management.entity.Product;
import com.order.management.entity.Unit;
import com.order.management.entity.UnitProductMapping;
import com.order.management.repository.ProductRepository;
import com.order.management.repository.UnitProductMappingRepository;
import com.order.management.repository.UnitRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitProductMappingRepository unitProductMappingRepository;


    @Transactional
    public ProductResponseDTO addProduct(AddProductDTO addProductDTO){
        try {
            Product product = new Product();
            product.setName(addProductDTO.getName());
            product.setPrice(addProductDTO.getPrice());
            product.setStock(addProductDTO.getUnits());
            Product savedProduct = productRepository.save(product);

            String productName = addProductDTO.getName();
            String prefix = productName.substring(0, 3).toUpperCase();

            // for(int i = 0; i < addProductDTO.getUnits(); i++){
            //     Unit unit = new Unit();
            //     String modelNo = prefix + "-" + savedProduct.getId() + "-" + String.format("%03d", i + 1);
            //     unit.setName(modelNo);
            //     unit.setAvailable(true);
            //     Unit savedUnit = unitRepository.save(unit);

            //     UnitProductMapping unitProductMapping = new UnitProductMapping();
            //     unitProductMapping.setProduct(savedProduct);
            //     unitProductMapping.setUnit(savedUnit);
            //     unitProductMappingRepository.save(unitProductMapping);
            // }

            List<Unit> units = new ArrayList<>();
            List<UnitProductMapping> mappings = new ArrayList<>();

            for(int i = 0; i < addProductDTO.getUnits(); i++){
                Unit unit = new Unit();
                String modelNo = prefix + "-" + savedProduct.getId() + "-" + String.format("%03d", i + 1);
                unit.setName(modelNo);
                unit.setAvailable(true);
                units.add(unit);
            }

            List<Unit> savedUnits = unitRepository.saveAll(units);

            for(Unit savedUnit : savedUnits){
                UnitProductMapping mapping = new UnitProductMapping();
                mapping.setProduct(savedProduct);
                mapping.setUnit(savedUnit);
                mappings.add(mapping);
            }
            unitProductMappingRepository.saveAll(mappings);




            ProductResponseDTO response = new ProductResponseDTO();

            response.setProductId(savedProduct.getId());
            response.setName(savedProduct.getName());
            response.setPrice(savedProduct.getPrice());
            response.setTotalUnits(addProductDTO.getUnits());
            response.setMessage("Product added successfully");
            return response;
        } catch (Exception e) {
             ProductResponseDTO response = new ProductResponseDTO();

        response.setMessage("Failed to add product : " + e.getMessage());

        return response;
        }
    }
}
