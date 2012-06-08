package au.com.dius.shop

class Product {

    def String sku
    def String name
    def Double price

    Product(String sku, String name, String price)
    {
        this.sku = sku
        this.name = name
        this.price = new Double(price.trim().substring(1))
    }
}
