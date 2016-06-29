package com.im.test

/**
 * Created by ttnd on 29/6/16.
 */
class ProductSpec extends spock.lang.Specification{

    def "Testing product sorting based on price"(){
        setup:
        List<Product> products = [new Product(price: 250),new Product(price: 500),new Product(price: 280)]
        when:
        List<Product> sortedProducts = Product.getSortedPricesOfAllProducts(products)
        then:
        sortedProducts == [250,280,500]
    }

    def "Testing product sorting by name and then salary"() {

        setup:
        List<Product> products = [new Product(name: "A",price: 100),new Product(name: "C","price":200),new Product(name: "B","price" : 300)]
        when:
        List<Product> productsSortedByNameAndSalary = Product.getProductsSortedByNameAndSalary(products)
        then:

        productsSortedByNameAndSalary.get(0).name == "A"
        and:
        productsSortedByNameAndSalary.get(1).name == "B"
        productsSortedByNameAndSalary.get(2).name == "C"
    }

    def "Testing Employee grouping by Price"() {
        setup:
        List<Product> products = [new Product(price: 3300),new Product(price:8500),new Product(price : 70000)]
        when:
        Map<String,List<Product>> employeeGroupToProduct = Product.getEmployeesGroupedByPrice(products)
        then:
        employeeGroupToProduct.entrySet().size() == 3
    }
}
