package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.Category;
import com.platzi.market.persistence.entity.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

//Convierte la categoria a category
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    //Se usa para indicar como voy a traducir los objetos
    @Mappings({
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "descripcion", target = "category"),
            @Mapping(source = "estado", target = "active")
    })
    Category toCategory(Categoria categoria);

    //indica a mappstruct que es lo inverso a la conversión
    @InheritInverseConfiguration
    @Mapping(target = "productos", ignore = true) //indica que productos no va ir en nuestro mapeo con relacion a la clase
    Categoria toCategoria(Category category);


}
