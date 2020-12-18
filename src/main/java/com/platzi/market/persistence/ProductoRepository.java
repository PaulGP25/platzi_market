package com.platzi.market.persistence;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.repository.ProductRepository;
import com.platzi.market.persistence.crud.ProductoCrudRepository;
import com.platzi.market.persistence.entity.Producto;
import com.platzi.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //Indica que es la clase que se encarga de interactuar con la BD, asi tambien se puede usar @Component pero es mas generalizado
public class ProductoRepository implements ProductRepository { //Se implementan los metodos de ProductRepository

    @Autowired //Objetos que reciban esta anotacion se le ceden el control para que cree las instancias
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    private ProductMapper mapper; //se agrega el atributo ProductMapper para poder hacer la convierte el Producto a Product

    @Override
    public List<Product> getAll(){ //Aqui debe retornar una lista de Product, mas no de Productos
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll(); //Una lista de productos recuperdos de la BD
        return mapper.toProducts(productos); //que retorne el mapper que convierte una lista de productos en products
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderOrderByNombreAsc(categoryId);
        return Optional.of(mapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        //Al no tener un mapeador que me convierta una lista de opcionales, entonces a los productos se le realiza un map
        //La flechita recibe los productos que tiene adentro, los convierte en products y los retorna
        return productos.map(prods -> mapper.toProducts(prods));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        Optional<Producto> productos = productoCrudRepository.findById(productId);
        return productos.map(prod -> mapper.toProduct(prod));
    }

    @Override
    public Product save(Product product) {
        //El save al no esperar un product, sino un producto, se le realiza la conversión inversa
        Producto producto = mapper.toProducto(product); //Se mapea al revez para que al return le llegue el producto y no el product
        return mapper.toProduct(productoCrudRepository.save(producto));
    }

    @Override
    public void delete(int productoId){
        productoCrudRepository.deleteById(productoId);
    }

}
