package me.fullstackgabe.domain.controller;

import me.fullstackgabe.domain.model.Product;
import me.fullstackgabe.domain.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    //injeção de dependência do repository no controlador
    private final ProductRepository repository;

    //método construtor
    ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    //fazendo um get para retornar todos os produtos
    @GetMapping("/products")
    List<Product> all() {
        return repository.findAll();
    }

    //fazendo um post para criar um produto
    @PostMapping("/products")
    Product newProduct(@RequestBody Product newProduct) {
        return repository.save(newProduct);
    }

    //buscar apenas um produto e com exceção
    @GetMapping("/products/{id}")
    Product one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    //atualizar produto
    @PutMapping("/products/{id}")
    Product replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) {

        return repository.findById(id)
                .map(product -> {
                    product.getName(newProduct.getName());
                    return repository.save(product);
                })
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return repository.save(newProduct);
                });
    }

    //deletar produto
    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
