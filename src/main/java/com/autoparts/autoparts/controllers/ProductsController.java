// Authors: Liplan Lekipising and catherine Muthoni
package com.autoparts.autoparts.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import javax.servlet.ServletContext;


//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.autoparts.autoparts.classes.OrderProduct;
import com.autoparts.autoparts.classes.Products;
import com.autoparts.autoparts.repository.ProductsRepository;
import com.autoparts.autoparts.services.ProductsService;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    ServletContext servletContext;

    // GET - all products- view all - products page
    @GetMapping("/shop")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productsService.getAllProducts());
        return "shop";
    }

    // show add product form
    @GetMapping(path = "/newproduct")
    public String showAddForm(Model model) {
        model.addAttribute("products", new Products());
        return "addproduct";
    }

    // show more about product form
    @GetMapping(path = "/{id}")
    public String showMore(@PathVariable("id") Long id, Model model) {

        model.addAttribute("products", productsService.getOneProduct(id));
        model.addAttribute("orderProduct", new OrderProduct());
        return "productview";
    }

    // Add Product
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    public String saveProductSubmission(@ModelAttribute("products") @Valid Products product, Model model,
            BindingResult bindingResult, @RequestParam("studentPhoto") MultipartFile studentPhoto) throws IOException {
        if (bindingResult.hasErrors()) {
            System.out.println("Error during add product!");
            return "addproduct";
        }
        productsService.addProduct(product);
        String extension = FilenameUtils.getExtension(studentPhoto.getOriginalFilename());
        String nameP = product.getProductId() + "." + extension;
        product.setPhoto(nameP);

        productsService.addProduct(product);

        // AWS S3
//        AWSCredentials credentials = new BasicAWSCredentials("AKIAXCMAX5TCHECNIJGH",
//                "fMcpM2NXnOZUi9dF/iWk4nmV9H0JP0kSk/q1xHDx");
//        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();
//
//        File file = convertMultiPartToFile(studentPhoto);
//        s3client.putObject("autoparts250", nameP, file);

        return "redirect:/shop";
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    // Show update form
    @GetMapping("/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") long id) {
        ModelAndView mav = new ModelAndView("updateproduct");

        Products product = productsService.getOneProduct(id);        
        mav.addObject("product", product);
        return mav;
    }

    // UPDATE A PRODUCT
    @RequestMapping(value = "/updateproduct", method = RequestMethod.POST)
    public String updateProductSubmission(@ModelAttribute("product") Products product, BindingResult bindingResult,
            Model model, @RequestParam("studentPhoto") MultipartFile studentPhoto) throws IOException {

        if (bindingResult.hasErrors()) {
            return "updateproduct";
        }
        // photo
        productsService.addProduct(product);
        String extension = FilenameUtils.getExtension(studentPhoto.getOriginalFilename());
        String nameP = product.getProductId() + "." + extension;
        product.setPhoto(nameP);

        // productsService.addProduct(product);

        // FileUploadUtil.saveFile("http://s3.amazonaws.com/autoparts250", nameP, studentPhoto);

//        // AWS S3
//        AWSCredentials credentials = new BasicAWSCredentials("AKIAXCMAX5TCBFYGLRGH",
//                "CKbqWUr8iIhR/gorR7OYIKCgFWya/r1BDIa9UQV8");
//        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();
//
//        File file = convertMultiPartToFile(studentPhoto);
//        s3client.putObject("autoparts250", nameP, file);


        // photo

        // get previous photo
        // MultipartFile previousPhoto = productsService.getOneProduct(product.getProductId()).getStudentPhoto();

        productsService.addProduct(product);

        return "redirect:/shop";
    }

    // DELETE - delete a product
    @GetMapping("/delete/{productId}")
    public String delProduct(@PathVariable("productId") Long productId) {
        productsService.delProduct(productId);

        return "redirect:/shop";

    }
}